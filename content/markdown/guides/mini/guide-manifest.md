title: Guide to Working with Manifests
author: Jason van Zyl, Dennis Lundberg
date: 2010-08-19

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

## Guide to Working with Manifests

 In order to modify the manifest of the archive produced by the packaging plug-ins you need to create a configuration for it. The definitive guide for this is [the site for the Maven Archiver shared component](/shared/maven-archiver/index.html). This component is used by all our packaging plugins.

<!--  suggestion by jorg -->
<!--  it would be nice if the Specification-Version could be easily generated to be major.minor of pom.currentVersion i.e. that -->
<!--  -->
<!--  1.2 ==> 1.2 -->
<!--  1.2.1 ==> 1.2 -->
<!--  1.2-SNAPSHOT ==> 1.2 -->
<!--  for the javaapp-plugin I did something like this in Jelly ... -->
