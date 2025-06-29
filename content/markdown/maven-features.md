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
<!-- NOTE: For help with the syntax of this file, see:-->
<!-- http://maven.apache.org/doxia/references/apt-format.html-->

# Maven features

The following are the key features of Maven in a nutshell:

* Simple project setup that follows best practices - get a new project or module started in seconds-
* Consistent usage across all projects - means no ramp up time for new developers coming onto a project-
* Superior dependency management including automatic updating, dependency closures (also known as transitive dependencies)-
* Able to easily work with multiple projects at the same time-
* A [large and growing repository of libraries and metadata](/repository/) to use out of the box, and arrangements in place with the largest Open Source projects for real-time availability of their latest releases-
* Extensible, with the ability to easily [write plugins](/plugin-developers/) in Java or scripting languages-
* Instant access to new features with little or no extra configuration.
* Ant tasks for dependency management and deployment outside of Maven-
* Model based builds:
  Maven is able to build any number of projects into predefined output types such as a JAR, WAR, or distribution based on metadata about the project, without the need to do any scripting in most cases.
* Coherent site of project information:
  Using the same metadata as for the build process, Maven is able to generate a website or PDF including any documentation you care to add, and adds to that standard reports about the state of development of the project.
  Examples of this information can be seen at the bottom of the left-hand navigation of this site under the "*Project Information*" and "*Project Reports*" submenus.
* Release management and distribution publication:
  Without much additional configuration, Maven will integrate with your source control system (such as Subversion or Git) and manage the release of a project based on a certain tag.
  It can also publish this to a distribution location for use by other projects.
  Maven is able to publish individual outputs such as a JAR, an archive including other dependencies and documentation, or as a source distribution.
* Dependency management:
  Maven encourages the use of a central repository of JARs and other dependencies.
  Maven comes with a mechanism that your project's clients can use to download any JARs required for building your project from a central JAR repository much like Perl's CPAN.
  This allows users of Maven to reuse JARs across projects and encourages communication between projects to ensure that backward compatibility issues are dealt with.

