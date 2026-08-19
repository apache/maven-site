---
title: Overview Of The Doxia Framework
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

# Overview Of The Doxia Framework

The following figure represents the main components of the Doxia Framework. 

![Doxia Framework](images/architecture.png) 

**Note**: Just like Maven, Doxia uses [Plexus](https://codehaus-plexus.github.io/) extensively. 

## Sink API

The _Sink_ interface is a generic markup language interface. It contains several methods that encapsulate common text syntax. A start tag is denoted by _xxxx()_ method and a end of tag by _xxxx\_()_ method. 

For instance, you could do things like: 

```unknown
sink.paragraph();
sink.text( "my text" );
sink.paragraph_(); 
```

similar to this HTML markup: 

```unknown
<p>my text</p>
```

To find out more about the Sink API, you could read the Javadoc [here](http://maven.apache.org/doxia/doxia/doxia-sink-api/apidocs/org/apache/maven/doxia/sink/Sink.html). 

## Doxia Core

The _Core_ is the API to parse a source and populate it in a _Sink_ object. The _Parser_ interface contains only one method: 

```unknown
void parse( Reader source, Sink sink )
    throws ParseException; 
```

The _ParseException_ class has the responsibility to catch all parsing exceptions. It provides an helper method, _getLineNumber()_, which helps to find where an error occurred. 

The _AbstractParser_ class is an abstract implementation of the _Parser_. It provides a macro mechanism to give dynamic functionalities for the parsing. For more information on macros, read the [Doxia Macro Guide](./macros/index.html). 

Finally, the _SiteModule_ interface is the last part of the puzzle. It provides main definitions of a given Doxia module and it is used by the _doxia-site-renderer_ site tools. 

## Doxia Modules

A Doxia module is an implementation of a given markup language like APT or Xdoc. Each module should implement these interfaces: 

- _Parser_ interface, more specifically the _AbstractParser_ class

- _SiteModule_ interface

Several modules provide also a _Sink_ implementation to handle a specific output markup language. 

For more information on modules, read the [Doxia Module Guide](./modules/index.html). 

## Doxia Site Tools

The _Site Tools_ are a collection of tools to renderer an output. The main tool used by Maven, specifically the [Maven Site Plugin](http://maven.apache.org/plugins/maven-site-plugin/), is the _doxia-site-renderer_ which renders in HTML any documents written with supported markup syntax. It uses [Velocity templates](http://velocity.apache.org/) to customize the renderer and the _site-decoration-model_ tool to decorate the renderer. This component describes the layout of the site defined in the _site.xml_ file. 

The _doxia-doc-renderer_ tool is used to renderer any document in another document. 

