---
title: Guide to Naming Conventions
author: 
  - Carlos Sanchez
date: 2005-11-01
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
# Guide to naming conventions on groupId, artifactId, and version

- **groupId** uniquely identifies your project across all projects\. A group ID should follow [Java&apos;s package name rules](https://docs\.oracle\.com/javase/specs/jls/se6/html/packages\.html\#7\.7)\. This means it starts with a reversed domain name you control\. For example,

    `org.apache.maven`, `org.apache.commons`

    Maven does not enforce this rule\. There are many legacy projects that do not follow this convention and instead use single word group IDs\. However, it will be difficult to get a new single word group ID approved for inclusion in the Maven Central repository\.

    You can create as many subgroups as you want\. A good way to determine the granularity of the `groupId` is to use the project structure\. That is, if the current project is a multiple module project, it should append a new identifier to the parent&apos;s `groupId`\. For example,

    `org.apache.maven`, `org.apache.maven.plugins`, `org.apache.maven.reporting`

- **artifactId** is the name of the jar without version\. If you created it, then you can choose whatever name you want with lowercase letters and no strange symbols\. If it&apos;s a third party jar, you have to take the name of the jar as it&apos;s distributed\.

    eg\. `maven`, `commons-math`

- **version** if you distribute it, then you can choose any typical version with numbers and dots \(1\.0, 1\.1, 1\.0\.1, \.\.\.\)\. Don&apos;t use dates as they are usually associated with SNAPSHOT \(nightly\) builds\. If it&apos;s a third party artifact, you have to use their version number whatever it is, and as strange as it can look\. For example,

    `2.0`, `2.0.1`, `1.3.1`

