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

## Maintained By The Maven Project

| Extension                                      | Version | Release Date | Description                                                                             | Source Repository                                                                                                   | Issue Tracker
|------------------------------------------------|---------|--------------|-----------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-----------------|
|[Build Cache](./maven-build-cache-extension/)   | 1.1.0   | 2023-11-27   | Maven Incremental Build and Cache (local and remote). | [Git](https://gitbox.apache.org/repos/asf/maven-build-cache-extension.git) / [GitHub](https://github.com/apache/maven-build-cache-extension/) | [Jira MBUILDCACHE](https://issues.apache.org/jira/browse/MBUILDCACHE)
|[Enforcer](/enforcer/maven-enforcer-extension/) | 3.4.1   | 2023-09-07   | Environmental constraint checking (Maven Version, JDK etc), User Custom Rule Execution. | [Git](https://gitbox.apache.org/repos/asf/maven-enforcer.git) / [GitHub](https://github.com/apache/maven-enforcer/) | [Jira MENFORCER](https://issues.apache.org/jira/browse/MENFORCER)

## Outside The Maven Land

A number of other projects provide their own Maven extensions. This includes:

### Open Source

| Extension                                                 | Maintainer          | Description
|-----------------------------------------------------------|---------------------|-----------
| [notifier](https://github.com/jcgay/maven-notifier)       | Jean-Christophe Gay | A status notification will be send at the end of a Maven build.
| [polyglot](https://github.com/takari/polyglot-maven)      | Takari              | Polyglot for Maven is a set of extensions that allows the POM model to be written in dialects other than XML.
| [profiler](https://github.com/jcgay/maven-profiler)       | Jean-Christophe Gay | A time execution recorder for Maven which log time taken by each mojo in your build lifecycle.
| [profiler](https://github.com/takari/maven-profiler)      | Takari              | The Tesla profiler is a simple EventSpy implementation that gathers timing information.
| [smart-builder](https://github.com/takari/takari-smart-builder) | Takari        | The Takari Smart Builder is a replacement scheduling projects builds in a Maven multi-module build.
| [opentelemetry-maven-extension](https://github.com/open-telemetry/opentelemetry-java-contrib/tree/main/maven-extension)  | The OpenTelemetry project       | The OpenTelemetry Maven Extension instruments builds to gather execution details as traces for build performance optimization and for troubleshooting. <br/>OpenTelemetry traces can be visualized in open source observability solutions such as [Jaeger Tracing](https://www.jaegertracing.io) as well as in commercial solutions.

### Commercial

| Extension                                                                | Maintainer  | Description
|--------------------------------------------------------------------------|-------------|-----------
| [Gradle Enterprise](https://docs.gradle.com/enterprise/maven-extension/) | Gradle Inc. | Captures Maven build insights that can be viewed for free on [scans.gradle.com](https://scans.gradle.com/). Provides local and remote build caching, distributed test execution and predictive test selection for Maven builds connected to a Gradle Enterprise installation.
