---
title: Doxia Modules Guide
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


# Doxia Modules Guide

Each markup format is implemented by one Doxia module. All five modules below are part of Doxia and are included in
the Maven Site Plugin, so you can use any of them without extra configuration. FML is deprecated. The
[format references](../references/index.html) compare them side by side, including which ones Doxia can also write
and what each format cannot express.

<!-- MACRO{toc|section=1|fromDepth=2|toDepth=2} -->

## <a id="Markdown"></a>Markdown

Use Markdown for new pages. It is the format most authors and editors already know, it renders on GitHub, and Doxia
can both read and write it. Doxia follows [CommonMark](https://commonmark.org/) and adds tables, strikethrough,
footnotes, definition lists, abbreviations and YAML front matter for the page title, author and date.

- [Markdown format reference](../references/markdown-format.html)
- [doxia-module-markdown](../doxia/doxia-modules/doxia-module-markdown/), with the full list of supported extensions
- [CommonMark specification](https://spec.commonmark.org/) and
  [GitHub Flavored Markdown specification](https://github.github.com/gfm/)

## <a id="APT"></a>APT

APT (Almost Plain Text) is the original Doxia format and predates Markdown. Existing APT pages keep working, and
[Migrating APT to Markdown](../migrating-apt-to-markdown.html) shows how to convert them.

- [APT format reference](../references/apt-format.html)
- [doxia-module-apt](../doxia/doxia-modules/doxia-module-apt/)

## <a id="XDoc"></a>XDoc

XDoc is an XML format with HTML-like body markup, long used on Apache project sites. It gives you more control over
the HTML than APT or Markdown, at the cost of verbose sources.

- [XDoc format reference](../references/xdoc-format.html)
- [doxia-module-xdoc](../doxia/doxia-modules/doxia-module-xdoc/)

## <a id="FML"></a>FML

FML (FAQ Markup Language) is an XML format for FAQ pages: it groups questions into parts and generates the list of
questions with links to the answers. Doxia can read FML but not write it.

FML is deprecated since Doxia 2.2.0 and will be removed in the next major version
([apache/maven-doxia#1101](https://github.com/apache/maven-doxia/issues/1101)). Write new FAQ pages in Markdown;
the [Doxia Converter](../doxia-tools/doxia-converter/) converts existing `.fml` files.

- [FML format reference](../references/fml-format.html)
- [doxia-module-fml](../doxia/doxia-modules/doxia-module-fml/)

## <a id="XHTML"></a>XHTML

The `doxia-module-xhtml5` module reads and writes [XHTML](https://html.spec.whatwg.org/multipage/xhtml.html), the
XML serialization of HTML5. Use it when a page needs HTML that the other formats cannot produce. The HTML5 sink of
this module also writes every page of a Maven site. The XHTML 1.0 module was removed in Doxia 2.0.

- [doxia-module-xhtml5](../doxia/doxia-modules/doxia-module-xhtml5/)
- [HTML Living Standard, XML syntax](https://html.spec.whatwg.org/multipage/xhtml.html)
