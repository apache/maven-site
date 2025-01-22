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

<head>
   <title>Mojo API Specification</title>
   <meta name="author" content="John Casey" />
</head>

# Mojo API Specification (Maven 3)

**NOTE**: This documentation covers Mojo API introduced in Maven 2 and used by Maven 3.
It does not cover the [new Maven 4 API](https://maven.apache.org/ref/4-LATEST/api/index.html)!

## Introduction

Maven plugins can be written in Java or any of a number of scripting languages. Plugins consists of one or more Mojos,
each one being the implementation for one of the plugin's **goals**.

Although the requirements on Mojos are minimal by design, there are still a very few requirements that Mojo developers
must keep in mind:

1. a Mojo must have a method named `execute` which declares no parameters, and has a void return type.
   If this method throws an exception, that exception must either be a derivative of
   `java.lang.RuntimeException`, or a derivative of [
   `org.apache.maven.plugin.MojoExecutionException`](/ref/current/maven-plugin-api/apidocs/org/apache/maven/plugin/MojoExecutionException.html).
   It goes without saying that in the latter case, the execute method must declare that it throws this exception.
2. Additionally, Mojos must declare a field for each goal **parameter** they specify, and these parameter fields will be
   populated before `execute()` is called.
3. Finally, all Mojos must be accompanied by metadata in `META-INF/maven/plugin.xml` describing parameters, lifecycle
   bindings, etc.

## Mojo Code

Basically, these Mojo requirements are embodied by the [
`org.apache.maven.plugin.Mojo` interface](/ref/current/maven-plugin-api/apidocs/org/apache/maven/plugin/Mojo.html),
which the Mojo must implement.
Usually this is done by extending its [abstract base class counterpart
`org.apache.maven.plugin.AbstractMojo`](/ref/current/maven-plugin-api/apidocs/org/apache/maven/plugin/AbstractMojo.html).
The Mojo will have access to the standard Maven user-feedback mechanism, [
`org.apache.maven.plugin.logging.Log`](/ref/current/maven-plugin-api/apidocs/org/apache/maven/plugin/logging/Log.html),
so the Mojo can communicate important events to the console or other log sink.
This descriptor will be covered in more detail below.

## The Descriptor and Annotations

As mentioned before, each plugin - or packaged set of Mojos - must provide a [
`META-INF/maven/plugin.xml` descriptor file](/ref/current/maven-plugin-api/plugin.html) inside the plugin's jar file.
Fortunately, Maven also provides a set of Java annotations (
named [Maven Plugin Tools Java5 Annotations](/plugin-tools/maven-plugin-tools-annotations/index.html) and tools (
named [Plugin Tools](/plugin-tools/) to generate this descriptor, so developers don't have to worry about directly
authoring or maintaining a separate XML metadata file.

## Project Descriptor (POM) Requirements

The POM must declare a packaging element which describes this project as a Maven plugin project:
`<packaging>maven-plugin</packaging>`.

## Resources

This section simply gives a listing of pointers for more
information.

* [Maven Plugin API](/ref/current/maven-plugin-api/apidocs/index.html)
* [MojoDescriptor API](/ref/current/maven-plugin-api/apidocs/org/apache/maven/plugin/descriptor/MojoDescriptor.html)

