title: Maven Plugins Compatibility Plan
author: Hervé Boutemy
date: 2020-05-20

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

## Maven Plugins Compatibility Plan


### Scope


 This page describes the system requirements plan, which consists of:



 1 minimum **Java** runtime prerequisite for Maven plugins, which can be extended to shared components,

 1 minimum **Maven** runtime prerequisite for plugins.


 Such requirements are displayed as "System Requirements" in every plugin info report (see [this example](/plugins/maven-clean-plugin/plugin-info.html)).


 Consolidated view for all LATEST plugins release is visible in a [daily generated report](https://ci-maven.apache.org/job/Maven/job/maven-box/job/maven-dist-tool/job/master/site/dist-tool-prerequisites.html).



### Maven Plan



 - Until 2012, Maven 2.2.1 \+ Java 5 prerequisites, with plugins versions in 2.x

 - Since 2012, Maven 3.0 \+ Java 7 prerequisites, with plugins in 3.x.y

 - Since June 2020, Maven Plugin API used by plugins \>\= 3.1.0 \+ Java 8 prerequisites [Technical details](https://s.apache.org/MVN-PLUGIN-MIGRATION-3.1)



### Context



 - Maven core history with Java prerequisite is available in the [release notes](/docs/history.html)

 - JDK/JRE availability dates:

  - Java 5 (2004) is closed source, End of Public Update in 2009

  - Java 6 (2006) is Open Source, maintained at OpenJDK until 2018

  - Java 7 (2011) is Open Source, maintained [at OpenJDK](https://wiki.openjdk.java.net/display/jdk7u) at least until June 2020 

  - Java 8 (2014) is Open Source, maintained [at OpenJDK](https://wiki.openjdk.java.net/display/jdk8u) at least until September 2023

  - Java 11 (LTS, 2018) is Open Source, maintained [at OpenJDK](https://wiki.openjdk.java.net/display/JDKUpdates/JDK11u) at least until September 2025

  - see [Java Version Almanac](https://javaalmanac.io/jdk/) for updated JDK releases


 see also [Java Is Still Free](https://docs.google.com/document/d/1nFGazvrCvHMZJgFstlbzoHjpAVwv5DEdnaBr_5pKuHo) for more details





