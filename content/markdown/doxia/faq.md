---
title: Frequently Asked Questions
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

<a id="top"></a>

# Frequently Asked Questions

1. [How to handle style in the APT markup language?](#How_to_handle_style_in_the_APT_markup_language)
2. [How to export in PDF?](#How_to_export_in_PDF)
3. [Why XML based sinks don't generate nicely formatted documents?](#Why_XML_based_sinks_don_t_generate_nicely_formatted_documents)
4. [Where are the Maven Doxia XSD schemas files?](#doxia-xsd)
5. [How to define character entities in Doxia XML files with XSD?](#doxia-character-entities)

<a id="How_to_handle_style_in_the_APT_markup_language"></a>

### How to handle style in the APT markup language?

APT does not support style. If you need more control, write the page in
[Markdown](references/markdown-format.html), which accepts inline HTML, or in [Xdoc](references/xdoc-format.html).

<a id="How_to_export_in_PDF"></a>

### How to export in PDF?

Doxia no longer generates PDF. The two modules that used to do it, the iText module and the FO module, were
removed in Doxia 2.0.0-M1, see [DOXIA-630](https://issues.apache.org/jira/browse/DOXIA-630).

The Maven PDF Plugin, which built on those modules, was retired in March 2025. Its final release, 1.6.2, is
still resolvable from Maven Central, but it is not maintained and the modules it depends on only exist in the
unmaintained Doxia 1.x line.

<a id="Why_XML_based_sinks_don_t_generate_nicely_formatted_documents"></a>

### Why XML based sinks don't generate nicely formatted documents?

We decided to keep pretty printing out of the core modules. So, XML based sinks like Xdoc or XHTML are
intentionally unformatted. You could always do this after the document generation or directly by creating a
specialized end-user sink (see [DOXIA-255](https://issues.apache.org/jira/browse/DOXIA-255)).

<a id="doxia-xsd"></a>

### Where are the Maven Doxia XSD schemas files?

The Doxia XSD files are located here:

Xdoc XSD 2.0
: [https://maven.apache.org/xsd/xdoc-2.0.xsd](/xsd/xdoc-2.0.xsd)

FML XSD 1.0.1
: [https://maven.apache.org/xsd/fml-1.0.1.xsd](/xsd/fml-1.0.1.xsd)

Site XSD 2.1.0
: [https://maven.apache.org/xsd/site-2.1.0.xsd](/xsd/site-2.1.0.xsd)

Most IDEs validate and complete Xdoc and FML files once the root element declares the schema:

```xml
<document xmlns="http://maven.apache.org/XDOC/2.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/XDOC/2.0 https://maven.apache.org/xsd/xdoc-2.0.xsd">
  ...
</document>
```

```xml
<faqs xmlns="http://maven.apache.org/FML/1.0.1"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/FML/1.0.1 https://maven.apache.org/xsd/fml-1.0.1.xsd">
  ...
</faqs>
```

```xml
<site xmlns="http://maven.apache.org/SITE/2.1.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SITE/2.1.0 https://maven.apache.org/xsd/site-2.1.0.xsd">
  ...
</site>
```

The Book and Document schemas (`book-1.0.0.xsd`, `document-1.0.1.xsd`) belonged to the book and PDF renderers,
which are retired; they stay online for old documents.

**Note**: the Xdoc and FML modules bundle their schemas, so validating those files needs no network access. Other
schemas and entity sets, including the XHTML entity sets, are downloaded once per build and kept in memory.

<a id="doxia-character-entities"></a>

### How to define character entities in Doxia XML files with XSD?

Since it is not possible to define character entity references (like &amp;copy;) in XSDs (unlike DTDs), each
XML file should have a &lt;!DOCTYPE&gt; to define the character entity set.

For instance, you could add the following in your Xdoc XML files to be similar to XHTML 1.0 Transitional dtd:

```xml
<!DOCTYPE document [
  <!-- These are the entity sets for ISO Latin 1 characters for the XHTML -->
  <!ENTITY % HTMLlat1 PUBLIC "-//W3C//ENTITIES Latin 1 for XHTML//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml-lat1.ent">
  %HTMLlat1;
  <!-- These are the entity sets for special characters for the XHTML -->
  <!ENTITY % HTMLsymbol PUBLIC "-//W3C//ENTITIES Symbols for XHTML//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml-symbol.ent">
  %HTMLsymbol;
  <!-- These are the entity sets for symbol characters for the XHTML -->
  <!ENTITY % HTMLspecial PUBLIC "-//W3C//ENTITIES Special for XHTML//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml-special.ent">
  %HTMLspecial;
]>
<document xmlns="http://maven.apache.org/XDOC/2.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/XDOC/2.0 https://maven.apache.org/xsd/xdoc-2.0.xsd">
...
</document>
```

**Note**: if CDATA is used to specify entity, Doxia will replace &amp; by &amp;amp; (i.e
&quot;&lt;![CDATA[&amp;iexcl;]]&gt;&quot; becomes &quot;&amp;amp;iexcl;&quot;).
