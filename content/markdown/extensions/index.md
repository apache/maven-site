# Available Extensions

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

  Maven is - at its heart - a plugin execution framework; most work is done by plugins. However, with extensions
  it is possible to hook into Maven, e.g. to manipulate the lifecycle.
  
  * [Configure Extensions](/guides/mini/guide-using-extensions.html)
  * [Write Extensions](/examples/maven-3-lifecycle-extensions.html)
  

## Supported By The Maven Project
  
| Extension                                      | Version | Release Date | Description                                                                             | Source Repository                                                                                                   | Issue Tracker
|------------------------------------------------|---------|--------------|-----------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-----------------|
|[Enforcer](/enforcer/maven-enforcer-extension/) | 3.0.0   | 2021-07-26   | Environmental constraint checking (Maven Version, JDK etc), User Custom Rule Execution. | [Git](https://gitbox.apache.org/repos/asf/maven-enforcer.git) / [GitHub](https://github.com/apache/maven-enforcer/) | [Jira MENFORCER](https://issues.apache.org/jira/browse/MENFORCER)

## Outside The Maven Land

A number of other projects provide their own Maven extensions. This includes:

### Open Source

| Extension      | Maintainer                                                     | Description 
|----------------|----------------------------------------------------------------|-----------
| notifier       | [Jean-Christophe Gay](https://github.com/jcgay/maven-notifier) | A status notification will be send at the end of a Maven build.
| polyglot       | [Takari](https://github.com/takari/polyglot-maven)             | Polyglot for Maven is a set of extensions that allows the POM model to be written in dialects other than XML. 
| profiler       | [Jean-Christophe Gay](https://github.com/jcgay/maven-profiler) | A time execution recorder for Maven which log time taken by each mojo in your build lifecycle.
|                | [Takari](https://github.com/takari/maven-profiler)             | The Tesla profiler is a simple EventSpy implementation that gathers timing information.
| smart-builder  | [Takari](https://github.com/takari/takari-smart-builder)       | The Takari Smart Builder is a replacement scheduling projects builds in a Maven multi-module build.

### Commercial

| Extension         | Maintainer                                                         | Description
|-------------------|--------------------------------------------------------------------|-----------
| Gradle Enterprise | [Gradle Inc.](https://docs.gradle.com/enterprise/maven-extension/) | Captures Maven build insights that can be viewed for free on [scans.gradle.com](https://scans.gradle.com/). Provides local and remote build caching and distributed test execution for Maven builds connected to a Gradle Enterprise installation.
