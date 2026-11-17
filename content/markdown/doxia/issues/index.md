---
title: Doxia Issues
author: 
  - Lukas Theussl
date: 2009-03-02
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


# Doxia Issues &amp; Gotchas

This page collects behavior that tends to surprise authors and plugin developers. See also the
[Frequently Asked Questions](../faq.html) and the [limitations of each format](../references/index.html#Limitations).

<!-- MACRO{toc|section=1|fromDepth=2|toDepth=2} -->
## <a id="Apt_anchors_and_links"></a>Apt anchors and links

In APT, a link to another document must start with `./` or `../`. Without it, Doxia treats the target as an anchor
in the same page, and an internal link must _not_ start with `#`. Read the [anchors](../references/apt-format.html#Anchors)
and [links](../references/apt-format.html#Links) sections of the APT reference.

Doxia does not warn about a link that lacks the prefix. It turns the link into an anchor in the same page, so
`{{{doxia-apt.html}text}}` becomes `<a href="#doxia-apt.html">` and `{{{references/doxia-apt.html}text}}` becomes
`<a href="#references.2Fdoxia-apt.html">`. Both are broken in the output and Doxia does not warn, so check the
links in the generated pages, or run a link checker over `target/site`.

Converting the page to [Markdown](../migrating-apt-to-markdown.html) avoids the problem, since Markdown links work
as written.

## <a id="Figure_sink_events"></a>Figure sink events

Doxia distinguishes between figures, which are block-level elements, and images, which are inline. A figure is
written as a sequence of events:

```java
sink.figure();

sink.figureGraphics("figure.png");

sink.figureCaption();
sink.text("Figure caption");
sink.figureCaption_();

sink.figure_();
```

The HTML5 sink writes this as:

```html
<figure><img src="figure.png" /><figcaption>Figure caption</figcaption></figure>
```

A `figureGraphics(...)` event on its own, outside `figure()`, writes just the inline `<img>` element. Pass the
alternative text as the `alt` attribute in a `SinkEventAttributeSet`; a caption is not used as alternative text.

## <a id="Empty_Generated_Page"></a>Empty Generated Page

If a report page comes out empty after `mvn site`, check that the report closes its sink with `sink.close()`. Reports
that extend `AbstractMavenReport` from [Maven Reporting Implementation](/shared/maven-reporting-impl/) get this
for free, including the sub-page sinks created through the `SinkFactory` passed to `generate()`. A report that
implements `MavenReport` directly, or that creates sinks of its own, must close each of them.
