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

# Release Notes &#x2013; Maven 3.6.3

The Apache Maven team would like to announce the release of Maven 3.6.3.

Maven 3.6.3 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.6.3/](/ref/3.6.3/)

## Reporters and Contributors of this release

We really value the contributions of these non committers, so this section is
focused on those individuals. Descriptions of the issues fixed can be found at
the [end of these release notes](#Details).

Issue Reporters of this release: Jonathan Chen, Charles Oliver Nutter, Lucas Ludueño, Stig Rohde Døssing, Vladimir Sitnikov

Contributors of this release: Stuart McCulloch, Mickael Istria, Peter Lynch, Christian Wansart, Dezhi Cai, Anatoly Zaretsky, Stig Rohde Døssing 
 
Many thanks to all reporters and contributors for their time and support.

(Please send an email to the dev list if we missed anyone).

## Overview about the changes 

- This is a regression release to fix some critical issues shipped with 3.6.2.

- Some license issues on binary distribution have been fixed.

- This Maven distribution is now Reproducible: if you [download Maven source archive](/download.cgi) (`apache-maven-3.6.3-src.zip` or `.tar.gz`), build it on Windows with JDK 8 using following command:  
```
mvn -DbuildNumber=cecedd343002696d0abb50b32b541b8a6ba2883f package
```
  you'll get bit-by-bit identical output (`apache-maven-3.6.3-bin.zip` and `.tar.gz` in `apache-maven/target/`) that you can check with sha512 fingerprints against official release.  
  If you're building on any Unix system, you'll need to add "`-Dline.separator=$'\r\n'`".  
  See the [Maven - Guide to Configuring for Reproducible Builds](/guides/mini/guide-reproducible-builds.html) for more details.

## The detailed issue list[](#Details)

Sub-task

    [MNG-6779] - fix jcl-over-slf4j license: Apache 2.0 instead of MIT

Bug

    [MNG-6584] - Maven version 3.6.0 does not show ReasonPhrase anymore
    [MNG-6759] - [REGRESSION] Maven fails to use <repositories> section from dependency when resolving transitive dependencies in some cases
    [MNG-6760] - [REGRESSION] ExclusionArtifactFilter result invalid when wildcard exclusion is followed by other exclusions
    [MNG-6765] - [REGRESSION] tycho pom-less builds fails with 3.6.2
    [MNG-6771] - Fix license issues on binary distribution

Improvement

    [MNG-6778] - Use https for schemaLocations
    [MNG-6799] - avoid model interpolation instability risk: ensure StringVisitorModelInterpolator replaces StringSearchModelInterpolator

Task

    [MNG-6777] - Remove duplicate resolveFile methods
    [MNG-6789] - Make Maven distribution build Reproducible

The full list of changes can be found in our [issue management system][4].

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12346152
[5]: ../../docs/history.html

