title: Deploy Maven Main Website
author: Barrie Treloar, Hervé Boutemy
date: 2015-03-30

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

## Introduction


 This document gives step-by-step instructions for deploying the main Maven [https://maven.apache.org](/) website.


 **Obsolete** this documentation is obsolete: with the migration of site source to Git, Apache CMS is not used any more. It is replaced by Git edit (eventually through GitHub with the "edit" link accessible from breadcrumb) followed by Jenkins job to build and commit to svnpubsub.


 See [Maven website introduction](./index.html) for instructions on the whole website publication.



## Overview


 Since December 2012, the overall website uses svnpubsub mechanism and the main website uses Apache CMS:

<img src="main-website.png" />Main website mechanisms overview


## How main website publication works


 Maven main website ([https://maven.apache.org](https://maven.apache.org)) is generated with [maven-site-plugin](/plugins/maven-site-plugin) from a source tree stored in svn: [https://svn.apache.org/repos/asf/maven/site/trunk](https://svn.apache.org/repos/asf/maven/site/trunk).


### Edit source content


 You can edit source content in 2 ways:



 1 use [the CMS UI](https://cms.apache.org/maven/) through your web browser: 

  - Go to [https://cms.apache.org/maven/](https://cms.apache.org/maven/).

  - Click link "Get Maven Working Copy".

  - Navigate to the content you want to modify.

  - Once you have modified the content, commit with the button "Submit".



 1 checkout the source content locally, modify it with your favorite text editor, eventually test the result (`mvn site`), then check-in source modifications.


 After source tree is modified in svn, [a Buildbot job](http://ci.apache.org/builders/maven-site-staging) is triggered: 



 1 it builds the HTML site using [maven-site-plugin](/plugins/maven-site-plugin): `mvn site`,

 1 it publishes generated HTML content to [CMS staging svn area](https://svn.apache.org/repos/infra/websites/staging/maven/trunk/content/),

 1 svnpubsub mecanism transfers svn CMS staging content to live CMS staging site: [http://maven.staging.apache.org](http://maven.staging.apache.org),



### Publish site content


 If everything is good, **publish modifications** using [CMS publish](https://cms.apache.org/maven/publish) action.


 Under the hood:



 1 CMS copies CMS staging svn area content to [website production svn area](https://svn.apache.org/repos/infra/websites/production/maven/content/),

 1 svnpubsub mecanism transfers svn production content to live production site: [http://maven.apache.org](http://maven.apache.org).




## How Doxia website publication works


 Doxia uses the exact same mecanisms:



 - you can edit [svn source tree](https://svn.apache.org/repos/asf/maven/doxia/site/trunk) either locally or through [CMS UI](https://cms.apache.org/maven-doxia/),

 - [a Buildbot job](http://ci.apache.org/builders/maven-doxia-site-staging) builds the site and updates [website staging svn area](https://svn.apache.org/repos/infra/websites/staging/maven-doxia/trunk/content/),

 - svnpubsub published to [live staging site](http://maven-doxia.staging.apache.org),

 - if everything is good, **publish modifications** using [CMS publish](https://cms.apache.org/maven-doxia/publish) action,

 - CMS copies CMS staging svn area content to [website production svn area](https://svn.apache.org/repos/infra/websites/production/maven-doxia/content/),

 - svnpubsub mecanism transfers svn production content to live production site: [http://maven.apache.org/doxia](http://maven.apache.org/doxia), with its [`extpaths.txt`](/doxia/extpaths.txt)


