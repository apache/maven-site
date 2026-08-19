---
title: Doxia Issues
author: 
  - Lukas Theussl
date: 2009-03-02
---

<!-- Licensed to the Apache Software Foundation (ASF) under one-->
<!-- or more contributor license agreements.  See the NOTICE file-->
<!-- distributed with this work for additional information-->
<!-- regarding copyright ownership.  The ASF licenses this file-->
<!-- to you under the Apache License, Version 2.0 (the-->
<!-- "License"); you may not use this file except in compliance-->
<!-- with the License.  You may obtain a copy of the License at-->
<!---->
<!--   http://www.apache.org/licenses/LICENSE-2.0-->
<!---->
<!-- Unless required by applicable law or agreed to in writing,-->
<!-- software distributed under the License is distributed on an-->
<!-- "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY-->
<!-- KIND, either express or implied.  See the License for the-->
<!-- specific language governing permissions and limitations-->
<!-- under the License.-->

# Doxia Issues &amp; Gotchas

This document collects some infos about specific issues and &apos;gotchas&apos; when working with Doxia. Please check also the [Frequently Asked Questions](../faq.html).

<!-- MACRO{toc|section=1|fromDepth=2|toDepth=2} -->
## <a id="Apt_anchors_and_links"></a>Apt anchors and links

Using **Doxia 1\.1** you may see a lot of warnings when processing old Apt source files:

```unknown
[WARNING] [Apt Parser] Ambiguous link: 'doxia-apt.html'. If this is a local link, prepend "./"!
```

and

```unknown
[WARNING] [Apt Parser] Modified invalid link: references/doxia-apt.html
```

The reason is that in Apt, links to other source documents have to start with either `./` or `../` to distinguish them from internal links. Please read the sections on [anchors](../references/doxia-apt.html#Anchors) and [links](../references/doxia-apt.html#Links) in our Apt guide. Note in particular that internal links in Apt do **not** start with &apos;\#&apos;.

**You should pay attention to these warnings since your links will most likely be broken.** Unfortunately, the warning message cannot indicate the source file with the broken link (see eg [MPDF-11](https://issues.apache.org/jira/browse/MPDF-11)), however, if you run in `DEBUG` mode, eg invoke maven with the `-X` switch, you can see which source document is being parsed when the warning is emitted.

## <a id="Figure_sink_events"></a>Figure sink events

Doxia distinguishes between figures, which are block-level elements, and images (or icons), which are in-line elements. For instance, the following sequence of sink events

```unknown
sink.figure( null );

sink.figureGraphics( "figure.png", null );

sink.figureCaption( null );
sink.text( "Figure caption", null );
sink.figureCaption_();

sink.figure_();
```

should output the equivalent of this html snippet:

```unknown
<div class="figure">
  <p><img src="figure.png"/></p>
  <p>Figure caption</p>
</div>
```

while the `figureGraphics( ... );` event alone can be used to generate an in-line image, i.e. just the `<img>` tag in case of html.

**Note** that we are using the forms that take a `SinkEventAttributeSet` above, even though we are just passing in null values. The reason is that the alternative forms (without `SinkEventAttributeSet`) have a different behavior, which is kept for backward compatibility (but the methods have been deprecated). Using the same sequence of sink events as above, but omitting the `null` method parameters, will generate

```unknown
<img src="figure.png" alt="Figure caption"/>
```

## <a id="Empty_Generated_Page"></a>Empty Generated Page

After running `mvn site` using your Maven reporting plugin, you see that the generated page is empty. Be sure that the the code calls `sink.close()`.

