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

# Guide To Maven Documentation Style

## Where did the style came from?

The documentation style guide was created to make our documentation more consistent and also to apply best practices to the documentation as well.
It grows with the suggestions made on the Maven dev mailing list and is a community consensus of how we should write our documentation.

Each rule in this guide should come with a motivation as to why it exists.
References to external sources are encouraged.

The styleguide might evolve over time.

## Voice

Write for a reader who wants to get something done. Address the reader as "you", use the active voice and the present tense, and say what Maven does rather than what it "will" do.
Start with what the reader needs; move history and background to the end of the page or to a page of its own.
Keep sentences short and avoid jokes, idioms and phrases such as "simply" or "just", which do not travel well across languages and make a reader who is stuck feel worse.

## Kinds of pages

Decide what kind of page you are writing before you write it, and keep to that kind on the page:

- a **tutorial** takes a newcomer through a complete task that works, step by step (for example [Maven in 5 Minutes](../getting-started/maven-in-five-minutes.html));
- a **how-to guide** solves one problem for a reader who already knows the basics (most pages under `guides/mini`);
- a **reference** page lists facts to look up, complete and without opinions (the [POM Reference](/pom.html), the [Settings Reference](/settings.html), plugin goal pages);
- an **explanation** says why Maven works the way it does (for example [Introduction to the Build Lifecycle](../introduction/introduction-to-the-lifecycle.html)).

A how-to guide that grows a long reference table, or a reference page that grows a tutorial, is easier to use as two pages that link to each other.
These four kinds come from [Diátaxis](https://diataxis.fr/).

## Maven versions

Say which Maven versions a page or a statement holds for whenever behavior differs between them, for example "Maven 3.9 and later" or "Maven 4 only".
The guides assume Maven 3.9.0 or later; do not describe how older versions behaved unless it still matters to readers.
When Maven 3 and Maven 4 behave differently, show both and say which is which, rather than describing only one of them.

## Check what a page shows

Every command, POM snippet and output on a page must have been run, with the Maven versions the page names, before it is published.
Copy output from a real run instead of writing it by hand, and shorten it with `...` rather than editing what remains.
Use current plugin and dependency versions in samples, and update them when a page is changed for another reason.

## Links and anchors

Pages are linked from other sites, from issues and from answers on the web, so their addresses must not break:

- when a page is moved, removed or merged into another, add a redirect for its old address to `content/filtered-resources/.htaccess` (or to the `.htaccess` of the section it is in);
- when a heading is renamed, keep its old anchor by adding an empty element with the old id above the heading, for example `<a id="The_Basics"></a>`;
- link to other pages of this site with relative links, and to an anchor only when the anchor exists.

## Code blocks

Give every code block its language, such as `xml`, `java`, `text` for program output, or `shell` for commands, so that it is highlighted correctly.
Show commands without a prompt (`mvn verify`, not `$ mvn verify`), so that they can be copied as they are.

## Images

Give every image an alternative text that says what the image shows, or what the reader should learn from it; a diagram's text needs to carry the diagram's content.
Prefer SVG for diagrams. Do not put text that the reader needs only inside an image.

## Date format

How people format a date varies around the world, sometimes making it hard for people to understand each other.
The solution to this problem comes in the form of the ISO-8601 standard.

A date in our documentation must follow this standard:

**YYYY-MM-DD**

where **YYYY** is the year in the Gregorian calendar, **MM** is the month of the year between 01 (January) and 12 (December), and **DD** is the day of the month between 01 and 31.

**Note**: All documentation meta-data should respect this convention, for instance for this given APT document:

### References

- [W3C Quality Web Tips](http://www.w3.org/QA/Tips/iso-date)
- [ISO-8601](https://www.iso.org/standard/70908.html)
- [Wikipedia](http://en.wikipedia.org/wiki/ISO_8601)

## POM Snippet

A POM file must use 2 spaces for each indentation. Because POM snippets are often used in documentation to show the user how to configure something, it is important that these snippets aren't too wide.
If they are too wide, the page is difficult to read on a smaller screen.

When you use a snippet of XML from the POM as an example in documentation, make sure that the example is properly indented.
A user should be able to copy and paste the example into their own POM without changing the indentation.

Also, you should declare all parent POM elements to improve the comprehension. You could use ellipsis (i.e. `...`) if you don't want to specify elements.

### Example

The following is an example of how the distribution management of the Maven site is configured.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
  ...
  <distributionManagement>
    <site>
      <id>apache.website</id>
      <url>scp://people.apache.org/www/maven.apache.org/</url>
    </site>
  </distributionManagement>
  ...
</project>
```

As you can see above the `<distributionManagement>` element is indented once (=2 spaces), the `<site>` element is indented twice (=4 spaces), and the `<id>` is indented three times (=6 spaces).

## Naming Documentation Files

All file names should replace space by a hyphen (`-`), for instance for this given APT document:

```
guide-documentation-style.md
```

## Updating Documentation Files

A good practice is to update the date (with the correct date format) when you are updating documentation files.

## Write Thinking

Here are some pointers about English rules when typing material:

- [Wikipedia:Manual of Style](https://en.wikipedia.org/wiki/Wikipedia:Manual_of_Style), specifically [Punctuation Part](https://en.wikipedia.org/wiki/Wikipedia:Manual_of_Style#Punctuation)

