---
title: Overview of the Doxia framework
author: 
  - Vincent Siveton
date: 2026-11-17
---

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


# Overview of the Doxia framework

<a id="Overview_Of_The_Doxia_Framework"></a>

Doxia turns documents into other documents. A _parser_ reads a source document and reports what it finds, a
heading, a paragraph, a table cell, as a stream of events. A _sink_ receives those events and writes them in its own
output format. Any parser can feed any sink, so adding a format means writing one parser or one sink, not one
converter per pair of formats.

The following figure shows the main components.

![Doxia Framework](images/architecture.png)

Doxia components are [JSR-330](/maven-jsr330.html) components, managed by Eclipse Sisu just like Maven's own.

## Sink API

The `Sink` interface describes a document as a sequence of events. A method such as `paragraph()` opens an element,
and the same name with a trailing underscore, `paragraph_()`, closes it:

```java
sink.paragraph();
sink.text("my text");
sink.paragraph_();
```

The HTML sink writes this as:

```html
<p>my text</p>
```

Maven report plugins use the Sink API directly to write their pages. See
[Using the Doxia Sink API](./developers/sink.html) and the
[`Sink` Javadoc](./doxia/doxia-sink-api/apidocs/org/apache/maven/doxia/sink/Sink.html).

## Doxia Core

Doxia Core holds the parser API and the shared implementations. The central method of the `Parser` interface is:

```java
void parse(Reader source, Sink sink) throws ParseException;
```

A `ParseException` carries the line and column where parsing failed, through `getLineNumber()` and
`getColumnNumber()`.

`AbstractParser` is the base class for parsers. It runs macros, which insert generated content such as a table of
contents while a document is parsed. See the [Doxia Macros Guide](./macros/index.html).

A `ParserModule` tells the site renderer which source directory and file extensions belong to a parser, for example
`src/site/markdown` and `.md` for the Markdown parser.

## Doxia Modules

A Doxia module implements one markup format. It provides a `Parser`, usually by extending `AbstractParser`, and a
`ParserModule`. Most modules also provide a `Sink` and a `SinkFactory`, so they can write the format as well as read
it. The [format references](./references/index.html) list which modules can do what, and
[Create a Doxia Module](./developers/modules.html) shows how to write one.

## Doxia Site Tools

[Doxia Sitetools](./doxia-sitetools/) builds a whole site from single documents. The site renderer, used by the
[Maven Site Plugin](/plugins/maven-site-plugin/), parses every source document with the matching module, writes it
through the HTML sink, and decorates the result with a skin, using [Apache Velocity](https://velocity.apache.org/)
templates. The site model describes the menus, breadcrumbs and other decoration set in `site.xml`.
