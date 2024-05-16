# Best Practice - Using a Repository Manager
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
A repository manager is a dedicated server application designed to manage
repositories of binary components.

The usage of a repository manager is 
considered an essential best practice for any significant usage of Maven.


## Purpose

A repository manager serves these essential purposes:

* act as dedicated proxy server for public Maven repositories
(see [Maven Guide to Mirror Settings](./guides/mini/guide-mirror-settings.html))
* provide repositories as a deployment destination for your Maven project 
outputs

## Benefits and Features

Using a repository manager provides the following benefits and features:

* significantly reduced number of downloads off remote repositories, saving time
and bandwidth resulting in increased build performance
* improved build stability due to reduced reliance on external repositories
* increased performance for interaction with remote SNAPSHOT repositories
* potential for control of consumed and provided artifacts
* creates a central storage and access to artifacts and meta data about them 
exposing build outputs to consumer such as other projects and developers, but 
also QA or operations teams or even customers 
* provides an effective platform for exchanging binary artifacts within 
your organization and beyond without the need for building artifact from source

## Available Repository Managers

The following list (alphabetical order) of open source and commercial repository
 managers are known to support the repository format used by Maven. Please refer to the respective linked web sites for further information about repository management in general 
and the features provided by these products.

* <a href="https://github.com/artipie" target="_blank">Artipie</a> (open source)
* <a href="https://bytesafe.dev/" target="_blank">Bytesafe</a> (commercial)
* <a href="https://www.cloudrepo.io" target="_blank">CloudRepo</a> (commercial)
* <a href="https://www.cloudsmith.io" target="_blank">Cloudsmith Package</a> (commercial)
* <a href="https://docs.gitea.com/packages/usage/packages/maven" target="_blank">Gitea</a> (open source)
* <a href="https://inedo.com/proget" target="_blank">Inedo ProGet</a> (commercial)
* <a href="https://www.jfrog.com/open-source" target="_blank">JFrog Artifactory Open Source</a> (free, not open source)
* <a href="https://www.jfrog.com/artifactory/" target="_blank">JFrog Artifactory Pro</a> (commercial)
* <a href="https://www.myget.org" target="_blank">MyGet</a> (commercial)
* <a href="https://docs.onedev.io/tutorials/package/working-with-maven" target="_blank">OneDev</a> (open source)
* <a href="https://www.sonatype.com/products/repository-oss-download" target="_blank">Sonatype Nexus OSS</a> (free, not open source)
* <a href="https://links.sonatype.com/products/nexus/pro/home" target="_blank">Sonatype Nexus Pro</a> (commercial)
* <a href="https://packagecloud.io" target="_blank">packagecloud.io</a> (commercial)
* <a href="https://reposilite.com" target="_blank">Reposilite</a> (open source)
