---

title: Deploy Maven Website
author: 
- Barrie Treloar
- Hervé Boutemy
date: 2013-09-23
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

# Introduction

The Maven [https://maven.apache.org](https://maven.apache.org) website is composed from:

- a main content,
- multiple components reference documentation, published for each component release.

And [Doxia](https://maven.apache.org/doxia/) website has the same dual structure.

These contents are stored in svn, and svnpubsub/svnwcsub maintains a working copy on the webservers in `/www/maven.apache.org/content` \(see [`svnwcsub` configured in infra Puppet](https://github.com/apache/infrastructure-puppet/blob/deployment/modules/svnwcsub/files/svnwcsub.conf#L123)\):

- `/` comes from [https://svn.apache.org/repos/asf/maven/website/content/](https://svn.apache.org/viewvc/maven/website/content/)
- [`/components`](https://maven.apache.org/components) comes from [https://svn.apache.org/repos/asf/maven/website/components/](https://svn.apache.org/repos/asf/maven/website/components/)
- `/doxia` comes from [https://svn.apache.org/repos/asf/maven/doxia/website/content/](https://svn.apache.org/viewvc/maven/doxia/website/content/)
- [`/doxia/components`](https://maven.apache.org/doxia/components) comes from [https://svn.apache.org/repos/asf/maven/doxia/website/components/](https://svn.apache.org/repos/asf/maven/doxia/website/components/)

and the link between main content and components reference documentation \(for example from `/plugins/maven-xxx-plugin` to internal `/components/plugins/maven-xxx-plugin`\) is done with symbolic links. These links are configured in `components.links` files in `content/resources/` and subdirectories, for example [plugins/components.links](https://github.com/apache/maven-site/blob/master/content/resources/plugins/components.links).

# How website publication works

Instructions on how to publish website content are split in separate documents:

- on every main content source commit \([maven-site.git](https://github.com/apache/maven-site) and [maven-doxia-site.git](https://github.com/apache/maven-doxia-site)\), main content rebuild and publish is triggered through Jenkins jobs \( [maven-site job](https://ci-maven.apache.org/job/Maven/job/maven-box/job/maven-site/) and [doxia-site job](https://ci-maven.apache.org/job/Maven/job/maven-box/job/maven-doxia-site/)\), which basically run `mvn site-deploy` \(it can be run locally if CI is off...\),
- on every Maven component release, release manager follows &quot;[deploying Maven components reference documentation](./deploy-component-reference-documentation.html)&quot;, eventually using [Component Reference Documentation Helper](./component-reference-documentation-helper.html) to easily prepare `svnmucc` command line.

# Analytics

As part of Privacy enhancements, the whole Maven site is moving to [Apache&apos;s Analytics infrastructure](https://privacy.apache.org/matomo/): see [Maven statistics](https://analytics.apache.org/index.php?module=CoreHome&action=index&date=yesterday&period=day&idSite=3).

