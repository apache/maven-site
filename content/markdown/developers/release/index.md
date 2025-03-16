---

title: Releasing A Maven Project
author: 
- Jason van Zyl
date: 2010-07-26
----------------

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

# Releasing A Maven Project

What follows is a description of releasing a Maven project to a staging repository and its documentation, whereupon it is scrutinized by the community, approved, and transferred to a production repository.

The steps involved are similar for any Apache project, with more specifics for parent POMs and Maven itself. The steps involved, and the relevant documents for each, are listed below.

<!-- nothing specific: normal component  * {{{./maven-plugin-release.html} Releasing a Maven plugin project}}-->
<!-- nothing specific: normal component * {{{./maven-shared-release.html} Releasing a Maven shared component or subproject}}-->
- [Releasing Maven Core](./maven-core-release.html)
- [Releasing a parent POM](./parent-pom-release.html)

The above links all provide specific information for those types of releases, but they all refer back to the common documentation:

- [Maven Project Common Release procedure](./maven-project-release-procedure.html)

