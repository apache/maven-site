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
 http://maven.apache.org/doxia/references/apt-format.html
-->

# Release Notes &#x2013; Maven 3.5.2

The Apache Maven team would like to announce the release of Maven 3.5.2.

Maven 3.5.2 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central place.

The core release is independent of the plugins available. Further releases of plugins will be made separately. See the [plugins list][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html][3]
- the reference documentation: [https://maven.apache.org/ref/3.5.2/][4]

## Reporters and Contributors of this release

Bugs:

* Marcel Schutte (reporter)
* Mario Krizmanic (reporter, contributor)
* Charles Gould (reporter)
* Brian Oxley (reporter)
* Dejan Stojadinovic (contributor)

Improvements:

* Anton Tanasenko (reporter, contributor)
* Gregor B. Rosenauer (reporter)
* Sylwester Lachiewicz (reporter)
* Stefan Eicher (reporter, contributor)
* Manuel Ryan (reporter)

Many thanks to all reporters and contributors and for their time and support.

## Testers

The following members of the Maven community provided valuable feedback during the release process:

* Mark Derricutt
* Dejan Stojadinovic
* Thomas Collignon
* Grzegorz Grzybek
* Petar Tahchiev
* jieryn
* Petr Široký

Thank you for your time and feedback.

## Improvements

The full list of changes as well as detailed descriptions of same can be found in our [issue management system][6].

### Bugs

- [MNG-5935][] - Optional true getting lost in managed dependencies when transitive
- [MNG-6127][] - Fix plugin execution configuration interference
- [MNG-6148][] - Can't package and assemble with JDK9/Jigsaw
- [MNG-6149][] - MetadataResolutionResult#getGraph() never resolves request type 'test'
- [MNG-6205][] - Non-ascii chars in name element are displayed as question marks in Win CLI output (regression)
- [MNG-6210][] - can't load @SessionScoped/@MojoExecutionScoped components from .mvn/extensions.xml
- [MNG-6223][] - mvn -f outputs invalid error when specifying POM directory
- [MNG-6224][] - Regression 6182a208: library.jansi.path does not point to proper directory
- [MNG-6233][] - maven-resolver-provider mixes JRS 330 and Plexus annotations
- [MNG-6240][] - Duplicate components in plugin extension realm when plugin depends on maven-aether-resolver
- [MNG-6242][] - No color for maven on Cygwin

### Sub-tasks
- [MNG-6186][] - switch to improved HawtJNI
- [MNG-6280][] - ArrayIndexOutOfBoundsException caused by pom.xml with process instructions

### Improvements
- [MNG-5457][] - Show repository id when downloading or uploading from/to a remote repository
- [MNG-6025][] - Add a ProjectArtifactsCache similar to PluginArtifactsCache
- [MNG-6123][] - detect self references in POM and fail fast
- [MNG-6174][] - Clean Up Maven Model
- [MNG-6196][] - Update slf4j and simplify its color integration
- [MNG-6203][] - Minor cleanup in MavenCli.java
- [MNG-6206][] - We should produce a WARNING by using RELEASE, LATEST as versions
- [MNG-6207][] - Create WARNINGs in case of using system scope
- [MNG-6228][] - Optionality not displayed in dependency tree when run in debug mode

### New Features
- [MNG-6084][] - Support JSR 250 annotations
- [MNG-6220][] - Add CLI options to control color output

### Tasks
- [MNG-6167][] - Clean up dependency mess (reported by dependency:analyze)
- [MNG-6258][] - Upgrade to Maven Resolver 1.1.0

## Complete Release Notes

See [complete release notes for all versions][7]

[0]: /download.html
[1]: /plugins/index.html
[2]: https://maven.apache.org/
[3]: /mailing-lists.html
[4]: /ref/3.5.2/
[6]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12338964&amp;styleName=Text
[7]: /docs/history.html
[MNG-5457]: https://issues.apache.org/jira/browse/MNG-5457
[MNG-5935]: https://issues.apache.org/jira/browse/MNG-5935
[MNG-6025]: https://issues.apache.org/jira/browse/MNG-6025
[MNG-6084]: https://issues.apache.org/jira/browse/MNG-6084
[MNG-6123]: https://issues.apache.org/jira/browse/MNG-6123
[MNG-6127]: https://issues.apache.org/jira/browse/MNG-6127
[MNG-6148]: https://issues.apache.org/jira/browse/MNG-6148
[MNG-6149]: https://issues.apache.org/jira/browse/MNG-6149
[MNG-6167]: https://issues.apache.org/jira/browse/MNG-6167
[MNG-6174]: https://issues.apache.org/jira/browse/MNG-6174
[MNG-6186]: https://issues.apache.org/jira/browse/MNG-6186
[MNG-6196]: https://issues.apache.org/jira/browse/MNG-6196
[MNG-6203]: https://issues.apache.org/jira/browse/MNG-6203
[MNG-6205]: https://issues.apache.org/jira/browse/MNG-6205
[MNG-6206]: https://issues.apache.org/jira/browse/MNG-6206
[MNG-6207]: https://issues.apache.org/jira/browse/MNG-6207
[MNG-6210]: https://issues.apache.org/jira/browse/MNG-6210
[MNG-6220]: https://issues.apache.org/jira/browse/MNG-6220
[MNG-6223]: https://issues.apache.org/jira/browse/MNG-6223
[MNG-6224]: https://issues.apache.org/jira/browse/MNG-6224
[MNG-6228]: https://issues.apache.org/jira/browse/MNG-6228
[MNG-6233]: https://issues.apache.org/jira/browse/MNG-6233
[MNG-6240]: https://issues.apache.org/jira/browse/MNG-6240
[MNG-6242]: https://issues.apache.org/jira/browse/MNG-6242
[MNG-6258]: https://issues.apache.org/jira/browse/MNG-6258
[MNG-6280]: https://issues.apache.org/jira/browse/MNG-6280
