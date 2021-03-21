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

 NOTE: For help with the syntax of this file, see:
 http://maven.apache.org/doxia/modules/index.html#Markdown
-->

# Release Notes &#x2013; Maven 3.8.0

The Apache Maven team would like to announce the release of Maven 3.8.0.

Maven 3.8.0 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.8.0/](/ref/3.8.0/)

## Reporters and Contributors of this release

## Overview about the changes 

This release covers 2 CVEs:

- CVE-2021-26291

  We received a report from Jonathan Leitschuh about a vulnerability of custom repositories in dependency poms.
  We've split this up into three separate issues:
  
  - Possible Man-The-Middle-Attack due to custom repositories using http protocol
  More and more repositories use https nowadays, but this hasn't always been the case. This means that Maven Central contains poms with custom repositories that refer to a URL over http.
  This makes it a target for a MITM attack. Since Maven Central is immutable, those poms won't be updated. At the same time, developers are probably not aware that for some downloads an
  insecure URL is being used. 
  To solve this, we extended the mirror configuration with `<blocked>`. 
  We've also improved the URL scheme to match URLs. Now it is possible to match with `external:http:*`, meaning any external URL using http. So `http://localhost` won't match this pattern.
  Decision was made to make this the new default behavior. This is done by providing by default a mirror in the `conf/settings.xml` blocking insecure external URLs.
  
  - Possible Domain Hijacking due to custom repositories using abandoned domains
  Sonatype has analyzed which domains were abandoned and has claimed these domains. 
  
  - Possible hijacking downloads by redirecting to custom repositories
  This one was the hardest to analyze and explain. The short story is: you're safe, dependencies are only downloaded from repositories within their context.
  So there are 2 main questions: what is the context and what is the order?
  The order is describes on the [Repository Order](maven.apache.org/guides/mini/guide-multiple-repositories.html#repository-order) page.
  The first group of repositories are defined in the settings.xml
  The second group of repositories are based on inheritence, with ultimately the super pom containing the URL to Maven Central.
  The third group is the most complex one but is important to understand the term context: repositories from the effective POMs from the dependency path to the artifact.
  So if a dependency was defined by another dependency or by a Maven project, it will also include their repositories.
  In the end this is not a bug, but a design feature.

- CVE-2020-13956

  Apache HttpClient is a transitive dependency of Maven Resolver via Maven Wagon, so we've updated those versions as part of this release.

  

## The detailed issue list[](#Details)

New Feature

    [MNG-7116] - Add support for mirror selector on external:http:*
    [MNG-7117] - Add support for blocking mirrors
    [MNG-7118] - Block external HTTP repositories by default

Dependency upgrade

    [MNG-7119] - Upgrade Maven Wagon to 3.4.3
    [MNG-7123] - Upgrade Maven Resolver to 1.6.2
    
The full list of changes can be found in our [issue management system][4].

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12350003
[5]: ../../docs/history.html

