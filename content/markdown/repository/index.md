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

<head>
   <title>Maven Central Repository</title>
   <meta name="author" content="Brett Porter" />
   <meta name="author" content="Hervé Boutemy" />
</head>

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

<img src="maven-central-repository.png" border="0" usemap="#map" />

<map name="map">
 <area shape="rect" coords="0,0,189,128"     alt="standalone public artifact repositories" href="/guides/introduction/introduction-to-repositories.html" />
 <area shape="rect" coords="264,76,354,111"  alt="Apache"   href="https://repository.apache.org/content/groups/public/" />
 <area shape="rect" coords="378,76,468,111"  alt="OSSRH"    href="https://central.sonatype.org/pages/ossrh-guide.html" />
 <area shape="rect" coords="490,75,520,111"  alt="Producers" href="https://central.sonatype.org/pages/producers.html" />
 <area shape="rect" coords="329,274,426,312" alt="central index" href="./central-index.html" />
 <area shape="rect" coords="39,274,205,314"  alt="archetype" href="/archetype/archetype-models/archetype-catalog/archetype-catalog.html" />
 <area shape="rect" coords="65,348,205,383"  alt="repo"     href="https://repo.maven.apache.org/maven2/" />
 <area shape="rect" coords="292,356,365,391" alt="ibiblio"  href="http://mirrors.ibiblio.org/pub/mirrors/maven2/" />
 <area shape="rect" coords="373,356,447,391" alt="Google"   href="https://storage-download.googleapis.com/maven-central/index.html" />
 <area shape="rect" coords="100,126,424,239" alt="Central Upload"                href="./guide-central-repository-upload.html" />
</map>