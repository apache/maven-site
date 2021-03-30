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

# Release Notes &#x2013; Maven 3.8.1

The Apache Maven team would like to announce the release of Maven 3.8.1.

Maven 3.8.1 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.8.1/](/ref/3.8.1/)

## Overview about the changes 

This release covers two CVEs:

### CVE-2021-26291

  We received a report from Jonathan Leitschuh about a vulnerability of custom repositories in dependency POMs.
  We've split this up into three separate issues:
  
  - Possible Man-In-The-Middle-Attack due to custom repositories using HTTP\
  More and more repositories use HTTPS nowadays, but this hasn't always been the case. This means that Maven Central contains POMs with custom repositories that refer to a URL over HTTP.
  This makes downloads via such repository a target for a MITM attack. 
  At the same time, developers are probably not aware that for some downloads an insecure URL is being used. 
  Because uploaded POMs to Maven Central are immutable, a change for Maven was required.
  To solve this, we extended the mirror configuration with `<blocked>` parameter,
  and we added a new `external:http:*` mirror selector (like existing `external:*`), meaning "any external URL using HTTP".\
  The decision was made to block such external HTTP repositories by default: this is done by providing a mirror in the `conf/settings.xml` blocking insecure HTTP external URLs.
  
  - Possible Domain Hijacking due to custom repositories using abandoned domains\
  Sonatype has analyzed which domains were abandoned and has claimed these domains. 
  
  - Possible hijacking of downloads by redirecting to custom repositories\
  This one was the hardest to analyze and explain. The short story is: you're safe, dependencies are only downloaded from repositories within their context.
  So there are two main questions: what is the context and what is the order?
  The order is described on the [Repository Order](/guides/mini/guide-multiple-repositories.html#repository-order) page.
  The first group of repositories are defined in the settings.xml (both user and global).
  The second group of repositories are based on inheritence, with ultimately the super POM containing the URL to Maven Central.
  The third group is the most complex one but is important to understand the term context: repositories from the effective POMs from the dependency path to the artifact.
  So if a dependency was defined by another dependency or by a Maven project, it will also include their repositories.
  In the end this is not a bug, but a design feature.

### CVE-2020-13956

  Apache HttpClient is a transitive dependency of Maven Resolver via Maven Wagon, so we've updated those versions as part of this release.
  
## Why does this version have the value 3.8.1?

  - Why not 3.6.4?\
  This is not just a bugfix as it contains three features that **cause a change of default behavior** (external HTTP insecure URLs are now blocked by default):
  your builds may fail when using this new Maven release, if you use now blocked repositories. Please check and eventually fix before upgrading.
  
  - Why not 3.7.0?\
  Apache Maven 3.7.0 has been advertised in the past that it would be the first release where you could optionally activate the build/consumer feature:
  the version containing this feature has been renamed to 4.0.0.
  Reusing 3.7.0 might lead to confusion, hence we picked the next available minor version.
  
  - Why not 3.8.0?\
  With every release there's a 72h+ voting period. During the vote of 3.8.0 a bug was discovered, one that was important enough to cancel the vote.
  With Maven we burn versions, to ensure we're always talking about the same "version". This way there will be never confusion about which Maven 3.8.0 one was using.
  

## How to fix when I get a HTTP repository blocked?

  If the repository is defined in your `pom.xml`, please fix it in your source code.

  If the repository is defined in one of your dependencies POM, you'll get a message like:

```
[ERROR] Failed to execute goal on project test: Could not resolve dependencies for project xxx: Failed to collect dependencies at my.test:dependency:version -> my.test.transitive:transitive:version: Failed to read artifact descriptor for my.test.transitive:transitive:jar:version: Could not transfer artifact my.test.transitive:transitive:pom:version from/to maven-default-http-blocker (http://0.0.0.0/): Blocked mirror for repositories: [blocked-repository-id (http://blocked.repository.org, default, releases+snapshots)]

```

  Options to fix are:

  - upgrade the dependency version to a newer version that replaced the obsolete HTTP repository URL with a HTTPS one,

  - keep the dependency version but [define a mirror in your settings](/guides/mini/guide-mirror-settings.html).

## The detailed issue list[](#Details)

Bug
    [MNG-7128] - improve error message when blocked repository defined in build POM
	
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

