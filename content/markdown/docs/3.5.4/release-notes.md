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

# Release Notes &#x2013; Maven 3.5.4

The Apache Maven team would like to announce the release of Maven 3.5.4

Maven 3.5.4 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central place.

The core release is independent of the plugins available. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.5.4/](/ref/3.5.4/)

## Reporters and Contributors of this release

We really value the contributions of these non committers, so this section is focused on those individuals. Descriptions of the issues fixed can be found at the [end of these release notes](#Details).

Bugs:

- [MNG-6370][] reporter and contributor: Sylwester Lachiewicz 
- [MNG-6382][] reporter: Falko Modler
- [MNG-6388][] reporter: Mike Kelly
- [MNG-6410][] reporter and contributor: Łukasz Dywicki

Improvements:

- [MNG-5756][] reporter: Jarkko Rantavuori contributor: eis
- [MNG-5940][] contributor: Florian Brunner
- [MNG-6411][] reporter and contributor: Łukasz Dywicki

Dependency Upgrades:

- [MNG-6344][] reporter and contributor: Sylwester Lachiewicz  

Many thanks to all reporters and contributors for their time and support.

## Preliminary Testers

Thank you also for your time and feedback.

## Known Issues

At the time of release, there are no known regressions introduced by this release

## Overview about the changes

This release is primarily aimed to resolve the two regressions introduced in the 3.5.3 release, specifically [MNG-6372][] and [MNG-6388][].

There are some additional minor improvements, the most notable of which is:

- The Maven Super POM changes the default execution of the `maven-source-plugin` `jar` goal into `jar-no-fork` which should resolve some issues complex projects had running releases.

## [The detailed issue list](#Details)

### Bugs
- [MNG-6370][] `ConcurrencyDependencyGraph#getNumberOfBuilds()` does not remove finished projects from unfinished ones
- [MNG-6372][] On Windows Maven can output spurious ANSI escapes such as `[0m [0m`
- [MNG-6382][] JANSI fails frequently with `NumberFormatException` when building in parallel
- [MNG-6386][] `${project.baseUri}` is not a valid URI (according to RFC 3986)
- [MNG-6388][] Error Fetching Artifacts: "`[B cannot be cast to java.lang.String`"
- [MNG-6403][] `Artifact#VERSION_FILE_PATTERN` does not escape period between date and time
- [MNG-6410][] Add `groupId` to `--resume-from` suggestion if `artifactId` is not unique in reactor

### Improvements
- [MNG-5756][] Java home output in `mvn -v` is misleading
- [MNG-5940][] Change the `maven-source-plugin` `jar` goal into `jar-no-fork` in Maven Super POM
- [MNG-6362][] Add documentation information for GitHub
- [MNG-6363][] Remove secret thread configuration property from code
- [MNG-6364][] Enhanced `Jenkinsfile` to test Core with JDK 9
- [MNG-6411][] Improve readability of project list returned when `--resume-from` option value is invalid

### Tasks
- [MNG-6377][] switch from Git-WIP to Gitbox

### Dependency upgrades
- [MNG-6344][] Upgrade Guice to 4.2.0
- [MNG-6423][] Upgrade to Wagon 3.1.0

The full list of changes can be found in our [issue management system][4].

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12342826
[5]: ../../docs/history.html
[MNG-6370]: https://issues.apache.org/jira/browse/MNG-6370
[MNG-6372]: https://issues.apache.org/jira/browse/MNG-6372
[MNG-6382]: https://issues.apache.org/jira/browse/MNG-6382
[MNG-6386]: https://issues.apache.org/jira/browse/MNG-6386
[MNG-6388]: https://issues.apache.org/jira/browse/MNG-6388
[MNG-6403]: https://issues.apache.org/jira/browse/MNG-6403
[MNG-6410]: https://issues.apache.org/jira/browse/MNG-6410
[MNG-5756]: https://issues.apache.org/jira/browse/MNG-5756
[MNG-5940]: https://issues.apache.org/jira/browse/MNG-5940
[MNG-6362]: https://issues.apache.org/jira/browse/MNG-6362
[MNG-6363]: https://issues.apache.org/jira/browse/MNG-6363
[MNG-6364]: https://issues.apache.org/jira/browse/MNG-6364
[MNG-6411]: https://issues.apache.org/jira/browse/MNG-6411
[MNG-6377]: https://issues.apache.org/jira/browse/MNG-6377
[MNG-6344]: https://issues.apache.org/jira/browse/MNG-6344
[MNG-6423]: https://issues.apache.org/jira/browse/MNG-6423
