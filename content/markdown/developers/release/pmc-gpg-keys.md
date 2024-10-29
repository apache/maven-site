---
title: Developers centre - Making GPG Keys
author: 
  - Vincent Siveton
date: 2006-10-01
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
# Introduction

**Before** a release You need to publish your Public GPG Keys in several place used by different tools for verifying release signatures\.

All Your historical Public Keys should be available for verifying historical releases, so please **don&apos;t remove** any key used sometime\.

All new **RSA** keys generated should be at least **4096** bits\. Do not generate new DSA keys\.

## Maven Project Keys

Public Keys used for signing Maven core, plugins and shared components are available for users at:  
[https://downloads\.apache\.org/maven/KEYS](https://downloads\.apache\.org/maven/KEYS)

You need edit a file and follow provided instructions in SVN at:  
[https://svn\.apache\.org/repos/asf/maven/project/KEYS](https://svn\.apache\.org/repos/asf/maven/project/KEYS)

General ASF instruction

- [Update keys on the next release](https://infra\.apache\.org/openpgp\.html\#update\-KEYS)
- [The KEYS File](https://infra\.apache\.org/release\-signing\.html\#keys\-policy)
## Distributing Your Public Keys

Your Public Keys **MUST** be available at public key server, you can use one or even all of currently common used key server

- [https://keys\.openpgp\.org](https://keys\.openpgp\.org)
- [https://keyserver\.ubuntu\.com](https://keyserver\.ubuntu\.com)
- [https://pgp\.mit\.edu](https://pgp\.mit\.edu)
## Committer public key files

You should also add Your Public Keys to [ASF Committer public key files](https://people\.apache\.org/keys/committer)

Please follow instructions at: [https://people\.apache\.org/keys](https://people\.apache\.org/keys)

## Generate a new key

Please follow ASF infrastructure instruction:

- [Generate a new key](https://infra\.apache\.org/openpgp\.html\#key\-gen\-generate\-key)
- [How to generate a strong key](https://infra\.apache\.org/openpgp\.html\#generate\-key)
