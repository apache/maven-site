---
title: Doxia Macros Guide
author: 
  - Vincent Siveton
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

# Doxia Macros Guide

The Doxia _Core_ includes macro mechanisms to facilitate the documentation writing.

Macros are supported by the APT, Xdoc, FML, XHTML and Markdown formats.

A macro in an APT source file is a **non-indented** line that looks like this:

```unknown
%{macro_name|param1=value1|param2=value2|...}
```

An Xdoc or FML macro has the following syntax:

```unknown
<macro name="macro_name">
  <param name="param1" value="value1"/>
  <param name="param2" value="value2"/>
  ...
</macro>
```

Since Doxia 1\.7, an XHTML or Markdown macro has the following syntax:

```unknown
<!-- MACRO{macro_name|param1=value1|param2=value2|...} -->
```

The following macros are available:

<!-- MACRO{toc|section=1|fromDepth=2|toDepth=2} -->
## <a id="Echo_Macro"></a>Echo Macro

The _Echo_ macro is a very simple macro: it prints out the key and value of any supplied parameters. For instance, in an APT file, you could write:

```unknown
%{echo|param1=value1|param2=value2}
```

Similarly, it will be for xdoc file:

```unknown
<macro name="echo">
  <param name="param1" value="value1"/>
  <param name="param2" value="value2"/>
</macro>
```

and it will output

```unknown
  param1 ---> value1
  param2 ---> value2
```

## <a id="Snippet_Macro"></a>Snippet Macro

The _Snippet_ macro is a very useful macro: it prints out the content of a file or a URL. For instance, in an APT file, you could write:

```unknown
%{snippet|id=myid|url=http://myserver/path/to/file.txt}
```

In a xdoc file, it will be:

```unknown
<macro name="snippet">
  <param name="id" value="myid"/>
  <param name="url" value="http://myserver/path/to/file.txt"/>
</macro>
```

The `id` parameter is not required if you want to include the entire file. If you want to include only a part of a file, you should add start and end demarcators: any line (typically a comment) that contains the strings &quot;`START`&quot;, &quot;`SNIPPET`&quot; and &quot;`myid`&quot; (where `myid` is the `id` of the snippet) is a start demarcator, and similarly &quot;`END SNIPPET myid`&quot; denotes the end of the snippet to include. For example:

- Start and end snippets in a Java file

    ```unknown
    public class MyClass
    {
        // START SNIPPET: myid
        public static void main( String[] args ) throws Exception
        {
            ...
        }
        // END SNIPPET: myid
    }
    ```

- Start and end snippets in a XML file

    ```unknown
    <project>
    ...
      <build>
        <plugins>
    <!-- START SNIPPET: myid -->
          <plugin>
            ...
          </plugin>
    <!-- END SNIPPET: myid -->
        </plugins>
      </build>
    </project>
    ```

|Parameter|Description|
|:---|:---|
|id|The id of the snippet to include. If omitted the whole file/url will be included (since Doxia 1\.1).|
|url|The path of the URL to include.|
|file|The path of the file to include (since doxia-1\.0-alpha-9).|
|verbatim|If the content should be output as verbatim escaped text. If this is set to `false` then the content of the snippet will not be escaped. This means that you can use it like Server-Side Includes on a webserver. Default value is `true`.|
|encoding|The encoding of the file to read (since Doxia 1\.6). If omitted the default JVM encoding will be used.|
## <a id="TOC_Macro"></a>TOC Macro

The _TOC_ macro prints a Table Of Content of a document. It is useful if you have several sections and subsections in your document. For instance, in an APT file, you could write:

```unknown
%{toc|section=2|fromDepth=2|toDepth=3}
```

This displays a TOC for the second section in the document, including all subsections (depth 2) and sub-subsections (depth 3).

**Note** that when a site is rendered, Doxia anchors each section title for you, so the entries this macro emits resolve without further work. Define an anchor explicitly (see [Enhancements to the APT format](../references/doxia-apt.html)) where a link has to survive the title being reworded.

In a xdoc file, it will be:

```unknown
<macro name="toc">
  <param name="section" value="2"/>
  <param name="fromDepth" value="0"/>
  <param name="toDepth" value="4"/>
</macro>
```

|Parameter|Description|
|:---|:---|
|section|Display a TOC for the specified section only, or all sections if 0\. Positive int, not mandatory, 0 by default.|
|fromDepth|Minimum section depth to include in the TOC (sections are depth 1, sub-sections depth 2, etc.). Positive int, not mandatory, 0 by default.|
|toDepth|Maximum section depth to include in the TOC. Positive int, not mandatory, 5 by default.|

From **Doxia 1\.1\.1** on you may also specify any of the html base attributes (_i.e._ any of `id`, `class`, `style`, `lang`, `title`) as parameters, e.g.:

```unknown
%{toc|class=myTOC}
```

This can be used for styling the TOC via css.

## <a id="Removed_Macros"></a>Removed Macros

The _SWF_ macro, which embedded Shockwave Flash assets, and the _SSI_ macro, which emitted a server
side include, were both removed in Doxia 2\.0\. A document that still invokes either one fails to
render.
