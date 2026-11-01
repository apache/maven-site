/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.site.check;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Keeps every address the site has published working: each file, and each anchor on a page.
 *
 * <p>The addresses are listed in {@code src/published-addresses.txt}, one file per line, followed by
 * the ids on that page that a link could point at. The list only grows: an address, once
 * published, stays on it.
 *
 * <ul>
 * <li>{@code check <site> <list>} follows every listed address through the {@code .htaccess} files
 *     of the rendered site, as Apache httpd would, and fails when one leads nowhere, or to a page
 *     without the anchor, and says how to fix it: for a page that moved as a whole, which it
 *     recognises by the anchors it took along, it writes the {@code Redirect} line. It also reports
 *     the pages and anchors of the build that are not on the list yet. Runs after
 *     {@code site:site}; see the pom.</li>
 * <li>{@code record <site> <list>} adds the files and anchors of the rendered site to the list, except
 *     a file that a redirect hides and sends nowhere, then checks like {@code check}.</li>
 * </ul>
 *
 * <p>Of {@code .htaccess} it reads {@code Redirect}, {@code RedirectMatch}, {@code RedirectPermanent}
 * and {@code RedirectTemp}. Like mod_alias, the rules of a directory come before those of its
 * parents, and the first rule that matches wins, also over a file that exists. A redirect to a part
 * of maven.apache.org that other builds write is not followed further: the component sites that
 * the {@code components.links} files link in (the plugins, {@code /ref/} and the others), and
 * {@code download.cgi}. An address that a {@code Redirect gone} rule matches was retired on purpose:
 * it is listed, not counted as broken.
 */
public class Addresses {
    private static final String HOST = "maven.apache.org";
    private static final String HTACCESS = "content/filtered-resources/.htaccess";
    private static final String LIST = "src/published-addresses.txt";
    private static final String HOWTO = "https://maven.apache.org/developers/website/move-a-page.html";
    private static final Pattern ABSOLUTE = Pattern.compile("^https?://([^/]+)(/.*)?$");
    private static final Pattern GROUP = Pattern.compile("\\$(\\d)");
    private static final int MAX_HOPS = 10;
    private static final List<String> HEADER = List.of(
            "# Every address maven.apache.org has published from this repository: a file, and the ids on",
            "# that page a link could point at. The list only grows. `mvn site` checks that each address",
            "# still leads to a page with the id, through the redirects in the .htaccess files.",
            "# Add the addresses of new pages with -Daddresses=record, which keeps these comment lines and",
            "# sorts the rest; see " + HOWTO);

    /** A Redirect or RedirectMatch line. */
    private static final class Rule {
        Pattern regex;
        String prefix;
        String target;
        boolean gone;
        String source;

        /** The address this rule sends path to, or null when it does not match. */
        String apply(String path) {
            if (regex != null) {
                Matcher m = regex.matcher(path);
                if (!m.find()) {
                    return null;
                }
                if (gone) {
                    return "";
                }
                Matcher g = GROUP.matcher(target);
                StringBuilder sb = new StringBuilder();
                while (g.find()) {
                    int n = Integer.parseInt(g.group(1));
                    String value = n <= m.groupCount() && m.group(n) != null ? m.group(n) : "";
                    g.appendReplacement(sb, Matcher.quoteReplacement(value));
                }
                g.appendTail(sb);
                return sb.toString();
            }
            boolean matches = path.equals(prefix)
                    || path.startsWith(prefix) && (prefix.endsWith("/") || path.charAt(prefix.length()) == '/');
            if (!matches) {
                return null;
            }
            return gone ? "" : target + path.substring(prefix.length());
        }
    }

    /** Where an address ends up. */
    private static final class Outcome {
        String path;
        /** The anchor a redirect sends to; a browser uses it instead of the anchor of the link. */
        String anchor;

        boolean outside;
        boolean gone;
        String problem;
        final List<String> hops = new ArrayList<>();
    }

    private final Path site;
    private final Map<String, List<Rule>> rules = new TreeMap<>();
    /** The parts of the server that other builds write: /plugins/maven-jar-plugin, /ref, ... */
    private final Set<String> outsideRoots = new TreeSet<>();
    /** Every file of the build, with the ids of the content of its page. */
    private final Map<String, Set<String>> published = new TreeMap<>();
    /** The ids of each page of the build, of its frame too, that a link could point at. */
    private final Map<String, Set<String>> allIds = new TreeMap<>();

    Addresses(Path site) throws IOException {
        this.site = site;
        List<Path> files;
        try (Stream<Path> walk = Files.walk(site)) {
            files = walk.filter(Files::isRegularFile).sorted().collect(Collectors.toList());
        }
        for (Path f : files) {
            String name = f.getFileName().toString();
            String dir = "/" + site.relativize(f.getParent()).toString().replace('\\', '/');
            dir = dir.endsWith("/") ? dir : dir + "/";
            if (name.equals(".htaccess")) {
                // the source of the file, for the editor: the root one is filtered, the others are not
                rules.put(dir, parse(f, dir.equals("/") ? HTACCESS : "content/resources" + dir + name));
                continue;
            }
            if (name.equals("components.links")) {
                // the symlinks the build makes in place of these paths, read by Ant as properties
                Properties links = new Properties();
                try (Reader in = Files.newBufferedReader(f, StandardCharsets.UTF_8)) {
                    links.load(in);
                }
                for (String link : links.stringPropertyNames()) {
                    outsideRoots.add(dir + link);
                }
            }
            String path = dir + name;
            Set<String> ids = new TreeSet<>();
            if (name.endsWith(".html")) {
                Document doc = Jsoup.parse(f.toFile(), "UTF-8");
                Element content = doc.selectFirst("main#bodyColumn, main#content, main");
                ids = ids(content != null ? content : doc.body());
                allIds.put(path, ids(doc));
            }
            published.put(path, ids);
        }
        outsideRoots.add("/components");
    }

    private static List<Rule> parse(Path file, String label) throws IOException {
        List<Rule> list = new ArrayList<>();
        for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
            String[] w = line.trim().split("\\s+");
            String directive = w[0].toLowerCase();
            if (w.length < 2
                    || !(directive.equals("redirect")
                            || directive.equals("redirectmatch")
                            || directive.equals("redirectpermanent")
                            || directive.equals("redirecttemp"))) {
                continue;
            }
            int i = 1;
            Rule r = new Rule();
            r.source = label + ": " + line.trim();
            if ((directive.equals("redirect") || directive.equals("redirectmatch"))
                    && w[i].matches("(?i)permanent|temp|seeother|gone|\\d{3}")) {
                r.gone = w[i].equalsIgnoreCase("gone") || w[i].equals("410");
                i++;
            }
            if (i >= w.length) {
                continue;
            }
            if (directive.equals("redirectmatch")) {
                r.regex = Pattern.compile(w[i]);
            } else {
                r.prefix = w[i];
            }
            r.target = i + 1 < w.length ? w[i + 1] : null;
            if (r.target == null && !r.gone) {
                continue;
            }
            list.add(r);
        }
        return list;
    }

    /** The rules that apply to path: those of its own directory first, then of each parent. */
    private List<Rule> rulesFor(String path) {
        List<Rule> list = new ArrayList<>();
        String dir = path.substring(0, path.lastIndexOf('/') + 1);
        while (true) {
            List<Rule> here = rules.get(dir);
            if (here != null) {
                list.addAll(here);
            }
            if (dir.equals("/")) {
                return list;
            }
            dir = dir.substring(0, dir.lastIndexOf('/', dir.length() - 2) + 1);
        }
    }

    Outcome follow(String path) {
        Outcome o = new Outcome();
        while (true) {
            path = normalize(path);
            String next = null;
            for (Rule r : rulesFor(path)) {
                next = r.apply(path);
                if (next != null) {
                    o.hops.add(r.source);
                    break;
                }
            }
            if (next == null) {
                break;
            }
            if (o.hops.size() > MAX_HOPS) {
                o.problem = "redirects in a loop";
                return o;
            }
            if (next.isEmpty()) {
                o.gone = true;
                return o;
            }
            // the browser keeps the anchor of the link, unless the redirect gives one of its own
            int hash = next.indexOf('#');
            if (hash >= 0) {
                o.anchor = next.substring(hash + 1);
                next = next.substring(0, hash);
            }
            int query = next.indexOf('?');
            if (query >= 0) {
                next = next.substring(0, query);
            }
            Matcher m = ABSOLUTE.matcher(next);
            if (m.matches()) {
                if (!m.group(1).equals(HOST)) {
                    o.outside = true;
                    o.path = next;
                    return o;
                }
                next = m.group(2) == null ? "/" : m.group(2);
            }
            path = next;
        }
        path = normalize(path);
        String dir = path.endsWith("/") ? path : Files.isDirectory(file(path)) ? path + "/" : null;
        if (dir != null) {
            path = dir + "index.html";
            if (!Files.exists(file(path)) && Files.isDirectory(file(dir))) {
                // the server lists a directory without an index page
                o.path = dir;
                return o;
            }
        }
        o.path = path;
        if (!Files.exists(file(path))) {
            if (isOutside(path)) {
                o.outside = true;
            } else {
                o.problem = o.hops.isEmpty()
                        ? "does not exist, and no redirect leads away from it"
                        : "leads to " + path + ", which does not exist";
            }
        } else if (o.anchor != null && path.endsWith(".html") && !idsOf(path).contains(o.anchor)) {
            o.problem = "leads to " + path + "#" + o.anchor + ", which lacks that anchor";
        }
        return o;
    }

    /**
     * The path as the server matches it: repeated slashes merged, as httpd does (MergeSlashes), and
     * the segments . and .. resolved, as the browser does.
     */
    static String normalize(String path) {
        List<String> segments = new ArrayList<>();
        String[] parts = path.split("/+", -1);
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (part.equals("..")) {
                if (!segments.isEmpty()) {
                    segments.remove(segments.size() - 1);
                }
            } else if (!part.equals(".")) {
                segments.add(part);
            }
            // a path that ends in . or .. names a directory
            if ((part.equals(".") || part.equals("..")) && i == parts.length - 1) {
                segments.add("");
            }
        }
        return "/" + String.join("/", segments);
    }

    /** True when path is in a part of the server that another build writes, or the download script. */
    private boolean isOutside(String path) {
        if (path.endsWith(".cgi")) {
            return true;
        }
        for (String root : outsideRoots) {
            if (path.equals(root) || path.startsWith(root + "/")) {
                return true;
            }
        }
        return false;
    }

    private Path file(String path) {
        return site.resolve(path.substring(1));
    }

    /** The ids of a page of the build that a link could point at, of its frame too. */
    private Set<String> idsOf(String path) {
        return allIds.getOrDefault(path, Set.of());
    }

    /** The ids under root that a link could point at: an id, or the name of an a element. */
    static Set<String> ids(Element root) {
        Set<String> ids = new TreeSet<>();
        if (root == null) {
            return ids;
        }
        for (Element e : root.select("[id], a[name]")) {
            if (e == root) {
                continue;
            }
            ids.add(e.id());
            if (e.tagName().equals("a")) {
                ids.add(e.attr("name"));
            }
        }
        ids.removeIf(id -> id.isEmpty() || id.contains(" "));
        return ids;
    }

    static Map<String, Set<String>> read(Path list) throws IOException {
        Map<String, Set<String>> addresses = new TreeMap<>();
        if (!Files.exists(list)) {
            return addresses;
        }
        for (String line : Files.readAllLines(list, StandardCharsets.UTF_8)) {
            if (line.isBlank() || line.startsWith("#")) {
                continue;
            }
            String[] w = line.trim().split("\\s+");
            Set<String> ids = addresses.computeIfAbsent(w[0], k -> new TreeSet<>());
            for (int i = 1; i < w.length; i++) {
                ids.add(w[i]);
            }
        }
        return addresses;
    }

    /** Writes the list, sorted, after the comment lines at the top of the file as it was. */
    static void write(Path list, Map<String, Set<String>> addresses) throws IOException {
        List<String> lines = new ArrayList<>();
        if (Files.exists(list)) {
            for (String line : Files.readAllLines(list, StandardCharsets.UTF_8)) {
                if (!line.startsWith("#")) {
                    break;
                }
                lines.add(line);
            }
        }
        if (lines.isEmpty()) {
            lines.addAll(HEADER);
        }
        addresses.forEach((path, ids) -> lines.add(ids.isEmpty() ? path : path + " " + String.join(" ", ids)));
        Files.write(list, lines, StandardCharsets.UTF_8);
    }

    /**
     * Adds the files and anchors of the build to the list. A file that a redirect hides and sends
     * nowhere was never reachable at its address, and is left out.
     */
    int record(Map<String, Set<String>> known, Path list) throws IOException {
        int before = known.size();
        List<String> unreachable = new ArrayList<>();
        for (Map.Entry<String, Set<String>> p : published.entrySet()) {
            if (!known.containsKey(p.getKey()) && follow(p.getKey()).problem != null) {
                unreachable.add(p.getKey());
                continue;
            }
            known.computeIfAbsent(p.getKey(), k -> new TreeSet<>()).addAll(p.getValue());
        }
        write(list, known);
        System.out.printf("Addresses: %d new, %d listed%n", known.size() - before, known.size());
        if (!unreachable.isEmpty()) {
            System.out.println("Not listed, because a redirect sends them nowhere: " + String.join(" ", unreachable));
        }
        return known.size() - before;
    }

    /**
     * The page of this build that a missing page most likely moved to: the one that has the most of
     * its anchors, at least half of them, and is served at its own address. A page that moves keeps
     * the ids of its headings. Null when no page qualifies, or two qualify equally.
     */
    String movedTo(String from, Set<String> ids) {
        String best = null;
        int most = 0;
        boolean tie = false;
        for (Map.Entry<String, Set<String>> p : published.entrySet()) {
            if (p.getKey().equals(from) || !follow(p.getKey()).hops.isEmpty()) {
                continue;
            }
            int shared = 0;
            for (String id : ids) {
                if (p.getValue().contains(id)) {
                    shared++;
                }
            }
            if (shared > most) {
                best = p.getKey();
                most = shared;
                tie = false;
            } else if (shared == most && shared > 0) {
                tie = true;
            }
        }
        return best != null && !tie && most * 2 >= ids.size() ? best : null;
    }

    private static String count(int n, String one, String more) {
        return n + " " + (n == 1 ? one : more);
    }

    int check(Map<String, Set<String>> addresses) {
        List<String> problems = new ArrayList<>();
        int redirected = 0;
        int outside = 0;
        List<String> retired = new ArrayList<>();
        String firstAnchor = null;
        for (Map.Entry<String, Set<String>> a : addresses.entrySet()) {
            Outcome o = follow(a.getKey());
            if (o.problem != null) {
                String problem = a.getKey() + " " + o.problem
                        + (o.hops.isEmpty() ? "" : " (" + String.join(" > ", new LinkedHashSet<>(o.hops)) + ")");
                boolean missingPage = o.path != null && !Files.exists(file(o.path));
                String to = a.getValue().isEmpty() || !missingPage ? null : movedTo(a.getKey(), a.getValue());
                if (to != null) {
                    Set<String> there = new TreeSet<>(a.getValue());
                    there.retainAll(published.get(to));
                    problem += String.format(
                            "%n      it moved to %s, which has %d of its %d anchors",
                            to, there.size(), a.getValue().size());
                    String line = "Redirect Permanent " + a.getKey() + " " + to;
                    if (o.hops.isEmpty()) {
                        problem += String.format(":%n      %s", line);
                    } else {
                        // the first rule that matches wins: a new line after it would never apply
                        problem += String.format(
                                "; put this line before the rule that catches it now, %s:%n      %s",
                                o.hops.get(0), line);
                    }
                }
                problems.add(problem);
                continue;
            }
            if (o.gone) {
                retired.add(a.getKey());
                continue;
            }
            if (!o.hops.isEmpty()) {
                redirected++;
            }
            if (o.outside) {
                outside++;
                continue;
            }
            // a redirect with an anchor of its own sends every link to that anchor; follow() checked it
            if (!a.getValue().isEmpty() && o.anchor == null && o.path.endsWith(".html")) {
                Set<String> missing = new LinkedHashSet<>(a.getValue());
                missing.removeAll(idsOf(o.path));
                if (!missing.isEmpty()) {
                    if (firstAnchor == null) {
                        firstAnchor = missing.iterator().next();
                    }
                    problems.add(a.getKey() + (o.path.equals(a.getKey()) ? "" : " > " + o.path) + " lacks #"
                            + String.join(" #", missing));
                }
            }
        }
        System.out.printf(
                "Addresses: %d published, %d redirected, %d to sites outside this build, %d retired, %d broken%n",
                addresses.size(), redirected, outside, retired.size(), problems.size());
        if (!retired.isEmpty()) {
            System.out.println("Retired on purpose, with Redirect gone: " + String.join(" ", retired));
        }
        problems.forEach(p -> System.out.println("  " + p));
        if (!problems.isEmpty()) {
            System.out.println("To fix a broken address, do one of these:");
            System.out.println("  - redirect it to its new place: Redirect Permanent <old> <new> in " + HTACCESS);
            System.out.println("  - put a dropped anchor back above the section that holds its content: <a id=\""
                    + (firstAnchor != null ? firstAnchor : "...") + "\"></a>");
            System.out.println("  - retire it on purpose: Redirect gone <old>, or delete the anchor from " + LIST);
            System.out.println("See " + HOWTO);
        }
        int newAddresses = 0;
        int newAnchors = 0;
        for (Map.Entry<String, Set<String>> p : published.entrySet()) {
            Set<String> known = addresses.get(p.getKey());
            if (known == null) {
                // as record does, leave out a file that a redirect hides and sends nowhere
                if (follow(p.getKey()).problem == null) {
                    newAddresses++;
                }
            } else {
                newAnchors += (int)
                        p.getValue().stream().filter(id -> !known.contains(id)).count();
            }
        }
        if (newAddresses + newAnchors > 0) {
            System.out.printf(
                    "Not on the list yet: %s and %s; add them with: mvn clean site -Daddresses=record%n",
                    count(newAddresses, "address", "addresses"), count(newAnchors, "anchor", "anchors"));
        }
        return problems.size();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: Addresses check|record <site> <list>");
            System.exit(2);
        }
        Addresses addresses = new Addresses(Paths.get(args[1]));
        Path list = Paths.get(args[2]);
        Map<String, Set<String>> known = read(list);
        switch (args[0]) {
            case "record":
                addresses.record(known, list);
                if (addresses.check(known) > 0) {
                    System.exit(1);
                }
                break;
            case "check":
                if (addresses.check(known) > 0) {
                    System.exit(1);
                }
                break;
            default:
                System.err.println("Unknown mode " + args[0]);
                System.exit(2);
        }
    }
}
