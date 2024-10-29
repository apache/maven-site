---
title: Maven Dependency Policies
author: 
  - Stephen Connolly
date: 2011-02-01
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
<!-- https://maven.apache.org/doxia/references/apt-format.html-->
# Maven Dependency Policies

## Scope

This page describes the policies around the use of dependencies by the Apache Maven Developers in the process of developing Apache Maven itself\.

This page does not apply to projects hosted outside of the Apache Maven project\. In order to remove all doubt, this page only applies to code which has a Github URL that starts with `https://github.com/apache/maven` or a Gitbox URL starting with `https://gitbox.apache.org/repos/asf?p=maven`

If you have stumbled across this page and you are working on code that does not have a Github URL starting with `https://github.com/apache/maven` then this page does not apply to you\.

## Background

The Apache Maven PMC is tasked with ensuring \(among other things\) that all legal issues are addressed and that each and every release is the product of the community as a whole\.

The Apache Maven project consists of quite a number of components\. For the purposes of this policy, we will make a distinction between the core Maven distribution and all the other components\.

The core Maven distribution is the binary and source distributions made available from the https://maven\.apache\.org/download page\. 

## Applicability

This policy applies to all changes to dependencies as and from Subversion revision 1067464\.

## Core Maven Distribution Dependencies

All dependencies which are included in the Core Maven Distribution must either:

- be licensed under a [Category A license](https://www\.apache\.org/legal/resolved\.html\#category\-a); or
- be licensed under a [Category B license](https://www\.apache\.org/legal/resolved\.html\#category\-b) and approved by a majority vote of the Apache Maven PMC\.

Votes for Category B licenses will be held on the dev@maven\.apache\.org mailing list\. A majority of the PMC must vote in favour of a Category B licensed dependency before a release can be made containing that dependency\.

## Non\-Core Dependencies

Non\-Core components may only use Category A or Category B licenses\.

