---
title: Maven Central Repository
author:
  - Brett Porter
  - Hervé Boutemy
---

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

# Maven Central Repository

This documentation is for those that need to use or contribute to the Maven `central` repository.
This includes those that need dependencies for their own build or projects that wish to have their releases added to the
Maven `central` repository, even if they don't use Maven as their build tool.

![Warning](../images/icon_warning_sml.gif) Be aware of those announcements:

* [**Discontinuing support for TLSv1.1** and below as of **June 15th 2018**](https://central.sonatype.org/faq/tls-info/)
* [<b>Discontinuing support for HTTP</b> as of <b>January 15th 2020</b>](https://central.sonatype.org/news/20190715_http_deprecation_update)

## Content

* [Maintaining your Metadata](../project-faq.html) - Information for third-party projects
* [Guide to uploading artifacts](./guide-central-repository-upload.html) - How to get things uploaded to the central
  repository
* [Fixing Central Metadata](./central-metadata.html) - How to fix issues in content already uploaded

<img src="maven-central-repository.png" border="0" alt="How artifacts reach the central repository from staging repositories, and where central publishes them: the CDN, mirrors, the archetype catalog and the index" />

The diagram shows how releases reach the central repository through Apache's and Sonatype's repositories, and
where central publishes them. These pages explain each part:

* [Introduction to Repositories](/guides/introduction/introduction-to-repositories.html)
* [Guide to Uploading Artifacts to the Central Repository](./guide-central-repository-upload.html)
* [Publishing through the Central Portal](https://central.sonatype.org/publish/publish-portal-guide/)
* [Apache's public repository](https://repository.apache.org/content/groups/public/)
* [The Central Index](./central-index.html)
* [The Archetype Catalog](/archetype/archetype-models/archetype-catalog/archetype-catalog.html)
* [repo.maven.apache.org](https://repo.maven.apache.org/maven2/)
* [Google's mirror of the central repository](https://storage-download.googleapis.com/maven-central/index.html)
