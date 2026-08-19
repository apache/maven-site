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

# Markdown Format Reference

Markdown is the format to reach for when writing new Maven documentation. Documents live
under `src/site/markdown/` and are read by
[`doxia-module-markdown`](../doxia/doxia-modules/doxia-module-markdown/), which is included
with `maven-site-plugin` and needs no extra dependency.

This page covers what is specific to Doxia. For the language itself, read the
[CommonMark specification](https://commonmark.org/).

## Which Markdown

The module parses with [flexmark](https://github.com/vsch/flexmark-java), configured for
CommonMark plus these extensions:

| Extension | What it adds |
|---|---|
| Tables | GitHub-style pipe tables |
| Definition lists | `Term` followed by `: definition` |
| Footnotes | `[^1]` references and definitions |
| Abbreviations | `*[HTML]: HyperText Markup Language` |
| Autolink | bare URLs become links |
| Strikethrough | `~~text~~` |
| Wiki links | `[[page]]` |
| Typographic | straight quotes, `...` and `--` become their typographic forms |
| Escaped character | a backslash escapes the character after it |

The typographic extension is worth knowing about: `"quoted"` renders with curly quotes and
`...` renders as a single ellipsis character. That is normally what you want in prose, but
it is a surprise inside a literal string, so put those in a code span.

## Document metadata

A document may open with a YAML front matter block. It must be the very first thing in the
file — the parser only looks for it when the source begins with `---` — so a licence header
goes below it, not above.

```markdown
---
title: Introduction
author:
  - Jane Developer
  - jane@example.org
date: 2026-08-07
---
```

Recognised keys are `title`, `author`, `date`, `address`, `affiliation`, `copyright`,
`email`, `keywords`, `language`, `phone` and `subtitle`. The skin turns them into the
document title and its `meta` tags; each `author` entry becomes its own
`<meta name="author">`.

Without a `title`, the page's first heading is used instead. That is usually not the same
text, so a page that means to have a distinct title should say so explicitly.

## Macros

Doxia macros are written as an HTML comment:

```markdown
<!-- MACRO{toc|fromDepth=1|toDepth=2} -->

<!-- MACRO{snippet|id=example|file=src/main/resources/example.xml} -->
```

See the [macros reference](../macros/index.html) for the ones that ship with Doxia.

## Velocity

A document named `*.md.vm` is run through Velocity before Doxia parses it, which lets it
interpolate `${project.version}` and friends.

**Velocity reads `##` as a line comment.** In a `*.md.vm` document every heading below
level one is deleted before Doxia ever sees the page, and nothing reports it. Three ways
around it:

- give the page no `.vm` suffix if it does not really need Velocity;
- use a setext underline for a level two heading, with a blank line before the title:

  ```markdown
  Goals Overview
  --------------
  ```

- wrap a deeper heading, or a whole block, in Velocity's unparsed markup:

  ```markdown
  #[[### Configuring the plugin]]#
  ```

  This applies inside fenced code blocks too: Velocity does not know what a code block is,
  so a sample containing `##` needs the same treatment.

To show a reference rather than resolve it, write `${esc.d}{project.version}`. A backslash
only works when the reference happens to resolve.

## What Markdown cannot express

If you are converting an existing document, these have no Markdown equivalent:

- a table caption;
- a second header row part way down a table — it becomes an ordinary row;
- a table with no header at all: Markdown requires one, so an empty header row appears;
- `_emphasis_` directly after a word character, which Markdown does not treat as emphasis.

## Converting an existing document

[`doxia-converter`](../doxia-tools/doxia-converter/) converts APT, XDoc and XHTML sources to
Markdown. Its output is a good first draft rather than a finished page; build the site
before and after and compare the generated HTML, since the failure modes above are all
invisible in the converted source.
