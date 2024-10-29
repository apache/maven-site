---
title: Guide to Building Maven
author: 
  - Brett Porter
  - Jason van Zyl
date: 2015-01-04
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
<!-- NOTE: For help with the syntax of this file, see:-->
<!-- http://maven.apache.org/doxia/references/apt-format.html-->
# Building Maven

## Why would I want to build Maven?

Building Maven \(or a plugin, or any component\) yourself is for one of two reasons:

- to try out a bleeding edge feature or bugfix \(issues can be found in [ JIRA](/issue\-management\.html)\),
- to fix a problem you are having and submit a patch to the developers team\.
## Checking out the sources

All of the source code for Maven and its related libraries is in managed in the ASF source code repositories: for details, see [https://maven\.apache\.org/scm\.html](/scm\.html)\.

## Building Maven

### Building a Maven Plugin or Component

Building a Maven plugin or component is like any Maven build:

```
mvn install
```

#### Running Integration Tests

Before submitting a patch, it is advised to run the integration tests, which are available in the `run-its` profile:

```
mvn -Prun-its install
```

### Building Maven core

Until Maven 3\.3, Maven core build could be boostrapped with an Ant build\. This bootstrap has been removed in Maven 3\.5: you need a pre\-built Maven to build Maven from source\.

To do this, run from the source directory:

```
mvn install
```

The assemblies will be created in `apache-maven`, and can be manually unzipped to the location where you&apos;d like the resulting Maven installed\.

If you want to have the resulting Maven directly copied to a directory, you can use the `distributionTargetDir` property:

```
mvn -DdistributionTargetDir="$HOME/app/maven/apache-maven-SNAPSHOT" install
```

#### Running the full Maven core integration tests

Before checking in a change or submitting a patch to Maven core, it is required to run the core integration tests\. Using your local build of Maven, run:

```
mvn test -Prun-its
```

Consult [Core ITs documentation](/core\-its/) for more options\.

