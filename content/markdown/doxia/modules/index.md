---
title: Doxia Modules Guide
author: 
  - Vincent Siveton
date: 2009-06-15
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

# Doxia Modules Guide

Doxia has several built-in modules that support some standard markup languages, see the [References](../references/index.html) page for an overview. The following is just a collection of reference links for the individual formats.

<!-- MACRO{toc|section=1|fromDepth=2|toDepth=2} -->

## <a id="APT"></a>APT

APT (Almost Plain Text) is a legacy simple text format.

**References**:

- [Apt Reference](../references/apt-format.html)

## <a id="FML"></a>FML

FML (FAQ Markup Language) is a legacy FAQ markup language based on XHTML 1.0.

**References**:

- [FML Reference](../references/fml-format.html)

## <a id="Markdown"></a>Markdown

[Markdown](http://en.wikipedia.org/wiki/Markdown) is a widespread Markup language.

**References**:

- [Official Markdown project at Daring Fireball](https://daringfireball.net/projects/markdown/)
- [CommonMark specification](https://commonmark.org/)
- [GitHub Flavored Markdown Spec](https://github.github.com/gfm/)

## <a id="XDoc"></a>XDoc

XDoc is a legacy format for document into a styled HTML document based on XHTML 1.0.

**References**:

- [XDoc Reference](../references/xdoc-format.html)

## <a id="XHTML"></a>XHTML

[XHTML](https://html.spec.whatwg.org/multipage/xhtml.html) is a markup language with the same expressions as HTML, but also conforms to XML syntax. Doxia supports it through the `doxia-module-xhtml5` module; the earlier XHTML 1\.0 module was removed in Doxia 2\.0\.

**References**:

- [XHTML 5/Living Standard](https://html.spec.whatwg.org/multipage/xhtml.html)
- [Outdated XHTML 1\.0 Specification](https://www.w3.org/TR/xhtml1/)
