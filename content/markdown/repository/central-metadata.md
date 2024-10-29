---
title: Guide to Metadata in Central Maven Repository
author: 
  - Carlos Sanchez
date: 2005-10-31
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
# Guide to add, improve or fix metadata in the Central Maven Repository

## POM Metadata

There are artifacts in the [Central Maven repository](\./) that don&apos;t have POMs\. They come from projects with repositories that have been synced into central without extra checks \(particularly historical ones that were in _Maven 1_ format\)\. We know about the problems but can&apos;t do anything unless you provide a POM for it or you ask the project in question to add the POM when they add the artifacts\.

We don&apos;t change dependencies in POMs already in the repository anymore as builds need to be reproducible\. Same applies to POMs that don&apos;t exist\. We can only add a POM with no dependencies, because doing any other way would break previous builds that were using that artifact\.

An alternative is to create a new version with the fixes\. If the broken artifact is `org.foo/bar/1.0` you can provide a fixed POM, JAR,\.\.\. under `org.foo/bar/1.0-1` \(add a comment to the POM explaining what is being fixed and why\)\. See [Maven Repository Upload](\./guide\-central\-repository\-upload\.html) for the instructions to get this new version in the repository\.

You need to contact the original publisher of the metadata to make sure in next versions it will be fixed or improved before getting it into the repository\.

## Other Issues

For any other types of issues related to metadata in the repository \(POM related, or [`maven-metadata.xml`](/ref/current/maven\-repository\-metadata/), or anything else\), open an issue at [MVNCENTRAL](https://issues\.sonatype\.org/browse/MVNCENTRAL) with the relevant information and explain the reasons why it is an issue\.

**Important:** by default assume that we won&apos;t trust your info, so you must provide all links to the project documentation you can to convince us that your solution is right\.

