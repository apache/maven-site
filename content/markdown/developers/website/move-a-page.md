<!--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

# Moving or Removing a Page

Links to maven.apache.org live in bookmarks, answers on forums, other sites and Maven's own messages. Every page and every section anchor the site has published is listed in `src/published-addresses.txt`, and `mvn site` fails when one of them no longer leads to its page or anchor. The Verify Site workflow runs it on every pull request; `mvn site:run` does not.

Run it yourself with `mvn clean site`. Without `clean`, `target/site` keeps the pages of the previous build, and a page you moved still seems to be at its old address.

This page shows what to do for each kind of change.

## Move or rename a page

Add a line to `content/filtered-resources/.htaccess`, from the old address to the new one:

```
Redirect Permanent /guides/mini/guide-java-target.html /docs/java/target.html
```

When a page moved as a whole, the check recognises its new place by the anchors it took along, and prints this line for you. The first rule that matches an address wins, so put a rule for a single page before a rule for its whole directory. When a rule already catches the old address, the check names it, and the new line goes before it.

The anchors of the old address are checked on the new page. A browser keeps the `#anchor` of a link through the redirect.

## Rename or merge a section

The anchor of a section comes from its heading. When you change the heading, or merge the section into another one, keep the old anchor above the section that now holds the content:

```
<a id="Available_Wagon_Providers"></a>

## Wagon Providers Bundled with Maven
```

Use the id that the check reports as missing. Leave the anchor in place, even when nothing on the site links to it.

## Move a section to another page

A redirect cannot move a section, because a browser does not send the part after `#` to the server. Where the old anchor goes depends on the old page:

- When the old page redirects to the new one, put the anchor above the section on the new page.
- When the old page stays, put the anchor on the old page, above a sentence that points to the new place:

```
<a id="Proxy_Settings"></a>

Proxy settings are described in [Configuring a proxy](/guides/mini/guide-proxies.html).
```

## Remove a page

When nothing replaces a page, redirect it to the closest page that does exist. When there is none, retire the address in `content/filtered-resources/.htaccess`:

```
Redirect gone /guides/mini/guide-obsolete.html
```

The server then answers that the page is gone, and the check lists the address as retired instead of broken.

## Remove a section

When the content of a section is gone and no other section holds it, delete the anchor from its line in `src/published-addresses.txt`. A link to it then lands at the top of the page. The deletion shows in the diff of your pull request, so the reviewer can ask whether the content really has nowhere to go.

## Add a page

The check reports the pages and anchors of the build that are not on the list yet:

```
Not on the list yet: 1 address and 0 anchors; add them with: mvn clean site -Daddresses=record
```

Run that command and commit the changed list. From then on, the check protects the new page too. A file that a redirect hides, and sends to a page that does not exist, is never reachable at its address; the command leaves it out and names it.

## Read the output of the check

```
Addresses: 795 published, 2 redirected, 2 to sites outside this build, 0 retired, 2 broken
  /guides/mini/guide-java-target.html does not exist, and no redirect leads away from it
      it moved to /docs/java/target.html, which has 5 of its 5 anchors:
      Redirect Permanent /guides/mini/guide-java-target.html /docs/java/target.html
  /guides/mini/guide-wagon-providers.html lacks #Available_Wagon_Providers
```

- *does not exist, and no redirect leads away from it*: a page was moved or removed without a line in `.htaccess`. See [Move or rename a page](#Move_or_rename_a_page) and [Remove a page](#Remove_a_page).
- *it moved to*: the check found the page elsewhere; the `Redirect Permanent` line below it is ready to paste.
- *leads to …, which does not exist*: a redirect points to a page that is not there; the rules it followed are shown in brackets.
- *leads to …#…, which lacks that anchor*: a redirect points to a section of a page, and the page no longer has it.
- *lacks #…*: the page is there, but without these anchors. See [Rename or merge a section](#Rename_or_merge_a_section), [Move a section to another page](#Move_a_section_to_another_page) and [Remove a section](#Remove_a_section).
- *to sites outside this build*: redirects to plugin and component sites, `/ref/` and `download.cgi`, which this repository does not build. They are not followed further.
