---
title: Doxia Developers Centre
author: 
  - Vincent Siveton
date: 2008-03-02
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

# Using the Doxia Sink API

<!-- MACRO{toc|section=1|fromDepth=2|toDepth=2} -->
## <a id="Transforming_documents"></a>Transforming documents

Doxia can be used to transform an arbitrary input document to any supported output format. The following snippet shows how to use a Doxia _Parser_ to transform an apt file to html:

```unknown
  File userDir = new File( System.getProperty ( "user.dir" ) );
  File inputFile = new File( userDir, "test.apt" );
  File outputFile = new File( userDir, "test.html" );

  SinkFactory sinkFactory = (SinkFactory) lookup( SinkFactory.ROLE, "html" ); // Plexus lookup

  Sink sink = sinkFactory.createSink( outputFile.getParentFile(), outputFile.getName() ) );

  Parser parser = (AptParser) lookup( Parser.ROLE, "apt" ); // Plexus lookup

  Reader reader = ReaderFactory.newReader( inputFile, "UTF-8" );

  parser.parse( reader, sink );
```

It is recommended that you use [Plexus](http://plexus.codehaus.org/) to look up the parser. In principle you could instantiate the parser directly ( `Parser parser = new AptParser();` ) but then some special features like macros will not be available.

You could also use the [Doxia Converter Tool](http://maven.apache.org/doxia/doxia-tools/doxia-converter/index.html) to parse a given file/dir to another file/dir.

## <a id="Generating_documents"></a>Generating documents

The snippet below gives a simple example of how to generate a document using the Doxia Sink API.

```unknown
    /**
     * Generate a simple document and emit it
     * into the specified sink. The sink is flushed but not closed.
     *
     * @param sink The sink to receive the events.
     */
    public static void generate( Sink sink )
    {
        sink.head();

        sink.title();
        sink.text( "Title" );
        sink.title_();

        sink.author();
        sink.text( "Author" );
        sink.author_();

        sink.date();
        sink.text( "Date" );
        sink.date_();

        sink.head_();


        sink.body();

        sink.paragraph();
        sink.text( "A paragraph of text." );
        sink.paragraph_();

        sink.section1();
        sink.sectionTitle1();
        sink.text( "Section title" );
        sink.sectionTitle1_();

        sink.paragraph();
        sink.text( "Paragraph in section." );
        sink.paragraph_();

        sink.section1_();

        sink.body_();

        sink.flush();
    }
```

A more complete example that also shows the &apos;canonical&apos; order of events to use when generating a document, can be found in the Doxia [SinkTestDocument](./doxia/doxia-core/xref-test/org/apache/maven/doxia/sink/SinkTestDocument.html) class.

## <a id="Passing_attributes_to_Sink_events"></a>Passing attributes to Sink events

A number of methods in the Sink API enable passing a set of attributes to many sink events. A typical use case is:

```unknown
SinkEventAttributeSet atts = new SinkEventAttributeSet();
atts.addAttribute( SinkEventAttributes.ALIGN, "center" );

sink.paragraph( atts );
```

The kind of attributes supported depends on the event and the sink implementation. The sink API specifies a list of suggested attribute names that sinks are expected to recognize and parsers are expected to use when emitting events.

## <a id="Avoid_sink.rawText.21"></a>Avoid sink.rawText!

In **Doxia 1\.0** it was a common practice to use sink.rawText() to generate elements that were not supported by the Sink API. For example, the following snippet could be used to generate a styled HTML &lt;div&gt; block:

```unknown
sink.RawText( "<div style=\"cool\">" );
sink.text( "A text with a cool style." );
sink.rawText( "</div>" );
```

This has a major drawback however: it only works if the receiving Sink is a HTML Sink. In other words, the above method will not work for target documents in any other format than HTML (think of the FO Sink to generate a pdf, or a LaTeX sink,\.\.\.).

In **Doxia 1\.1** a new method unknown() was added to the Sink API that can be used to emit an arbitrary event without making special assumptions about the receiving Sink. Depending on the parameters, a Sink may decide whether or not to process the event, emit it as raw text, as a comment, log it, etc.

The correct way to generate the above &lt;div&gt; block is now:

```unknown
SinkEventAttributeSet atts = new SinkEventAttributeSet();
atts.addAttribute( SinkEventAttributes.STYLE, "cool" );

sink.unknown( "div", new Object[]{new Integer( HtmlMarkup.TAG_TYPE_START )}, atts );
sink.text( "A text with a cool style." );
sink.unknown( "div", new Object[]{new Integer( HtmlMarkup.TAG_TYPE_END )}, null );
```

Read the javadocs of the unknown() method in the [Sink](./doxia/doxia-sink-api/apidocs/org/apache/maven/doxia/sink/Sink.html) interface and the [XhtmlbaseSink](./doxia/doxia-core/apidocs/org/apache/maven/doxia/sink/XhtmlBaseSink.html) for information on the method parameters. Note that an arbitrary sink may be expected to ignore the unknown event completely!

**In general, the rawText method should be avoided alltogether when emitting events into an arbitrary Sink.**

## <a id="How_to_inject_javascript_code_into_HTML"></a>How to inject javascript code into HTML

Related to the above, here is the correct way of injecting a javascript snippet into a Sink:

```unknown
// the javascript code is emitted within a commented CDATA section
// so we have to start with a newline and comment the CDATA closing in the end
// note that the sink will replace the newline by the system EOL
String javascriptCode = "\n function javascriptFunction() {...} \n //";

SinkEventAttributeSet atts = new SinkEventAttributeSet();
atts.addAttribute( SinkEventAttributes.TYPE, "text/javascript" );

sink.unknown( "script", new Object[]{new Integer( HtmlMarkup.TAG_TYPE_START )}, atts );
sink.unknown( "cdata", new Object[]{new Integer( HtmlMarkup.CDATA_TYPE ), javascriptCode }, null );
sink.unknown( "script", new Object[]{new Integer( HtmlMarkup.TAG_TYPE_END )}, null );
```

## <a id="References"></a>References

- [Doxia Modules Guide](../modules/index.html)
- [Doxia Macros Guide](../macros/index.html)
- [Doxia API Reference](../doxia/apidocs/index.html)
- [Doxia Sitetools API Reference](../doxia-sitetools/apidocs/index.html)
