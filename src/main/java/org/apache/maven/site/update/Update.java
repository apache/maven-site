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
package org.apache.maven.site.update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Updater for /plugins/index.apt release numbers against
 * effective site content.
 */
public class Update {
    public static void main(String[] args) throws IOException {
        for (String dir : args) {
            new Update().doUpdate(Paths.get("content/apt/" + dir + "/index.apt"));
            System.out.println("\r\33[2K");
        }
    }

    private void doUpdate(Path index) throws IOException {
        System.out.println("updating " + index);
        List<String> lines = Files.readAllLines(index);

        lines = lines.stream()
                .map(line -> line.startsWith("| {{{/") ? update(line) : line)
                .collect(Collectors.toList());

        Files.write(index, lines);
    }

    private String update(String line) {
        String[] cols = line.split("\\|");

        String description = cols[1];
        String url = "https://maven.apache.org/" + description.substring(5, description.indexOf('}'));
        String component = description.substring(description.indexOf("<<<") + 3, description.indexOf(">>>"));

        int column = 2;
        String versionCol = cols[2];
        String version = versionCol.trim();
        if ("".equals(version)) {
            return line;
        } else if (!Character.isDigit(version.charAt(0))) {
            // plugin index has an additional column (build or report)
            column++;
            versionCol = cols[column];
            version = versionCol.trim();
        }

        System.out.print("\r\33[2K  " + component + "  " + version + " => checking against " + url);
        String[] result = lookupRelease(url);

        if ((result != null) && (!result[0].equals(version))) {
            // found an updated version
            System.out.println("\r\33[2K  " + component + "  " + version + " => " + result[0] + " on " + result[1]
                    + " from " + url);
            cols[column] = String.format(" %-" + (cols[column].length() - 2) + "s ", result[0]);
            cols[column + 1] = String.format(" %-" + (cols[column + 1].length() - 2) + "s ", result[1]);
            line = String.join("|", cols);
        }
        return line;
    }

    /**
     * Lookup component page and extract release version and publication date
     */
    private String[] lookupRelease(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.print("\r\33[2K");
            e.printStackTrace();
            System.out.println();
            return null;
        }

        String version;
        String date;

        // Fluido skin
        Element links = doc.select("li#projectVersion").first();
        if (links != null) {
            String txt = links.text();
            version = txt.substring(txt.lastIndexOf(' ')).trim();
            txt = doc.select("li#publishDate").first().text();
            date = txt.substring(txt.lastIndexOf(' ')).trim();
        } else {
            // Stylus right or left
            links = doc.select("div.xright").first();
            if (links == null) {
                links = doc.select("div.xleft").first();
            }
            if (links == null) {
                System.out.println("could not find version in " + url);
                return null;
            }
            String txt = links.text();
            version = txt.substring(txt.lastIndexOf(' ')).trim();
            txt = txt.substring(txt.indexOf("Last Published:") + 16);
            date = txt.substring(0, 10);
        }

        if (version.endsWith("-SNAPSHOT")) {
            return null;
        }

        return new String[] {version, date};
    }
}
