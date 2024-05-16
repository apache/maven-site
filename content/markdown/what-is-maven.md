# Introduction
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

Maven, a [Yiddish word](https://en.wikipedia.org/wiki/Maven) meaning
*accumulator of knowledge*, began as an attempt to
simplify the build processes in the Jakarta Turbine project. There were
several projects, each with their own Ant build files, that were all
slightly different. JARs were checked into CVS. We wanted a standard
way to build the projects, a clear definition of what the project
consisted of, an easy way to publish project information, and a way to
share JARs across several projects.

The result is a tool that can now be used for building and managing any
Java-based project. We hope that we have created something that will
make the day-to-day work of Java developers easier and generally help
with the comprehension of any Java-based project.

## Maven's Objectives

Maven's primary goal is to allow a developer to comprehend the complete
state of a development effort in the shortest period of time. In order
to attain this goal, Maven deals with several areas of concern:

- Making the build process easy
- Providing a uniform build system
- Providing quality project information
- Encouraging better development practices

### Making the build process easy

While using Maven doesn't eliminate the need to know about the
underlying mechanisms, Maven does shield developers from many details.

### Providing a uniform build system

Maven builds a project using its project object model (POM) and
a set of plugins. Once you familiarize yourself with one Maven
project, you know how all Maven projects build.
This saves time when navigating many projects.

### Providing quality project information

Maven provides useful project information that is in part
taken from your POM and in part generated from your project's sources.
For example, Maven can provide:

-   Change log created directly from source control
-   Cross referenced sources
-   Mailing lists managed by the project
-   Dependencies used by the project
-   Unit test reports including coverage

Third party code analysis products also provide Maven plugins that add their
reports to the standard information given by
Maven.

### Providing guidelines for best practices development

Maven aims to gather current principles for best practices development
and make it easy to guide a project in that direction.

For example, specification, execution, and reporting of unit tests are
part of the normal build cycle using Maven. Current unit testing best
practices were used as guidelines:

-   Keeping test source code in a separate, but parallel source tree
-   Using test case naming conventions to locate and execute tests
-   Having test cases setup their environment instead of
    customizing the build for test preparation

Maven also assists in project workflow such as release and issue management.

Maven also suggests some guidelines on how to layout your project's
directory structure. Once you learn the layout, you can easily
navigate other projects that use Maven.

While Maven takes an opinionated approach to project layout, some projects
may not fit with this structure for historical reasons. While Maven is
designed to be flexible to the needs of different projects,
it cannot cater to every situation without compromising its objectives.

If your project has an unusual build structure that 
cannot be reorganized, you may have to forgo some features or the use of
Maven altogether.

## What is Maven Not?

You might have heard some of the following things about Maven:

-   Maven is a site and documentation tool
-   Maven extends Ant to let you download dependencies
-   Maven is a set of reusable Ant scriptlets

While Maven does these things, as you can read above in the "What is
Maven?" section, these are not the only features Maven has, and its
objectives are quite different.

