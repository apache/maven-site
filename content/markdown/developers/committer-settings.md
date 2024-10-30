---
title: Developers centre - Committer Settings
author: 
  - Vincent Siveton
  - Dennis Lundberg
date: 2011-05-23
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

# Introduction

This document is intended to set up the Maven committer settings, i\.e\. the `${user.home}/.m2/settings.xml`\.

## Enable Apache Servers

Maven uses several servers configuration to deploy snapshots and releases on the Apache servers\. You need to tell to Maven what your Apache username is\.

**It is highly recommended to use Maven&apos;s [ password encryption capabilities](\.\./guides/mini/guide\-encryption\.html) for your passwords**\.

```
<settings>
  ...
  <servers>
    <!-- To publish a snapshot of some part of Maven -->
    <server>
      <id>apache.snapshots.https</id>
      <username> <!-- YOUR APACHE LDAP USERNAME --> </username>
      <password> <!-- YOUR APACHE LDAP PASSWORD --> </password>
    </server>
    <!-- To stage a release of some part of Maven -->
    <server>
      <id>apache.releases.https</id>
      <username> <!-- YOUR APACHE LDAP USERNAME --> </username>
      <password> <!-- YOUR APACHE LDAP PASSWORD --> </password>
    </server>
    ...
  </servers>
</settings>
```

## Enable sending announcement e\-mails

To be able to send out announcements of Maven releases you need to add a couple of properties to the `apache-release` profile\.

```
<settings>
  ...
  <profiles>
    <profile>
      <id>apache-release</id>
      <properties>
        <apache.availid> <!-- YOUR APACHE LDAP USERNAME --> </apache.availid>
        <smtp.host> <!-- YOUR SMTP SERVER --> </smtp.host>
      </properties>
    </profile>
    ...
  </profiles>
</settings>
```

