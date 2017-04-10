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

# Release Notes &#x2013; Maven 3.5.0-beta-1

The Apache Maven team would like to announce the release of Maven 3.5.0-beta-1.

<div class="alert alert-error" role="alert">
<p><b>NOTE:</b></p>
<p>This is an <i>BETA</i> release. There is the potential that features may be removed between this release and the first GA release in the 3.5.x release line.</p>
<p><i>Please consult the Known Issues section below before use</i></p>
</div>

Maven 3.5.0-beta-1 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model
(POM), Maven can manage a project's build, reporting and documentation from a central place.

The core release is independent of the plugins available. Further releases of plugins will be made separately.
See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [http://maven.apache.org/](/)
- the maven-user mailing list: [http://maven.apache.org/mail-lists.html](/mail-lists.html)
- the reference documentation: [http://maven.apache.org/ref/3.5.0-beta-1/](/ref/3.5.0-beta-1/)

## Known Issues

The following issues were identified during release testing of this _BETA_ release but have not been deemed as release blockers:

* [MNG-6190] maven-resolver-provider's `DefaultArtifactDescriptorReader` has mismatched constructor and initService methods (this issue does not affect normal usage of Maven)
* [MNG-6191] `mvn -f` complains about illegal `readlink` option under macOS
* [MNG-6192] The distribution zip file has unordered entries and some tools - most notably Maven wrapper - will fail to unzip the distribution


## Why not Maven 3.4.0?

After Maven 3.3.9 was released, the Eclipse Aether project was retired and the code base was migrated to the Apache Maven project.

The original goal for the 3.4.0 release was to replace Aether with the exact same code after migration to the Apache Maven project and then proceed with bug fixes to the resolver code as well as other areas of Maven.

The migration of the code between the two foundations took longer than expected and as a result there were other changes committed to Maven core that were outside the scope of intent for 3.4.0.

In order to refocus on the original intent for 3.4.0, the decision was taken to revert the Maven core history to the point of the 3.3.9 release and merge in the desired changes one at a time.

Because there had been a lot of communication about different features being delivered and bugs fixed in Maven 3.4.0 and the new history may not contain them in the first release, the decision was taken to forever burn the 3.4.x release line.

More detail on this decision can be read in the [mailing list archive](http://www.mail-archive.com/dev@maven.apache.org/msg112103.html).

## Reporters and Contributors of this release

Bugs:

 * [MNG-6090] reporter: Harald Wellmann
 * [MNG-6173] reporter/contributor: Christoph Böhme

Many thanks to all reporters and contributors for their time and support.

## Preliminary Testers

Thank you also for your time and feedback.

## Overview about the changes

 * The most obvious change you may encounter is that the console output
   has colors now [MNG-3507], [MNG-6093].

 * The new `project.directory` special property adds support in every calculated URLs (project, SCM, site)
   for module directory name that does not match artifactId [MNG-5878]

 * The `JAVA_HOME` discovery has been reduced to simply check if `JAVA_HOME` is set
   or not then trying to discover via `which java`, nothing more [MNG-6003].

 * The build bootstrapping support via Apache Ant has been removed. You can now only bootstrap Maven
   build with a previous version of Maven, but not with Ant any more [MNG-5904].

 * Based on problems in using `M2_HOME` related to different Maven versions installed and
   to simplify things, the usage of `M2_HOME` has been removed and is not
   supported any more [MNG-5823], [MNG-5836], [MNG-5607].

 * Important change for windows users: The usage of `%HOME%` has been replaced
   with `%USERPROFILE%` [MNG-6001]

 * Several issues have been reported and fixed related to the `mvn` script either
   for Unix/Linux/Cygwin/Solaris or for Windows
   [MNG-5815], [MNG-5852], [MNG-5963], [MNG-6022].

 * In Maven 3.3.9, we have removed bindings for maven-ejb3-plugin because it
   does not exist. We follow-up and removed the artifact handler for `ejb3̀
   and the `par` lifecycle [MNG-6014], [MNG-6017].

 * In previous Maven versions, there had been a larger problem related to
   memory usage in case of very large reactors (200-300 modules or more)
   which caused failures with out of memory exceptions or the need to increase
   the memory settings. This problem has been fixed with [MNG-6030].

 * If you have defined a property within the `.mvn/maven.config` file,
   it was not possible to overwrite the property via command line.
   This has been fixed with [MNG-6078][MNG-6078].

 * If you have are using `<prerequisites>..</prerequisites>` for a non
   maven-plugin project, you will get a WARNING which looks like this:

```
[INFO] Scanning for projects...
[WARNING] The project org.apache.maven:maven:pom:3.5.0-SNAPSHOT uses prerequisites which is only intended for maven-plugin projects but not for non maven-plugin projects. For such purposes you should use the maven-enforcer-plugin. See https://maven.apache.org/enforcer/enforcer-rules/requireMavenVersion.html
```

   This will tell you to use maven-enforcer-plugin to check the version of Maven
   you are expecting to build your project with, instead of using `prerequisites`
   [MNG-5297], [MNG-6092].

 * Replaced Eclipse Aether with [Maven Resolver][maven-resolver]
   [MNG-6110], [MNG-6140].

 * Using of CI friendly versions via `${revision}`, `${sha1}` and/or `${changelist}`
   has been fixed [MNG-6057], [MNG-6090] and [MNG-5895]. It is very important to
   know if you are using the previously named properties for a version in your
   pom you have to use [flatten-maven-plugin] if you like to do an `mvn install`
   or `mvn deploy` more details can be found at [Maven CI Friendly](/maven-ci-friendly.html).

 * The two known issues from 3.5.0-alpha-1 have been fixed [MNG-6177], [MNG-6115]

Improvements:

Bugs:

* [MNG-5895] - Problem with CI friendly usage of ${..} which is already defined via property in pom file.
* [MNG-6057] - Problem with CI friendly usage of ${..} reactor order is changed
* [MNG-6090] - CI friendly properties break submodule builds
* [MNG-6170] - NPE in cases using Multithreaded -T X versions:set -DnewVersion=1.0-SNAPSHOT
* [MNG-6173] - MavenSession.getAllProjects() should return all projects in the reactor
* [MNG-6176] - Javadoc errors prevent release with Java 8
* [MNG-6177] - The --file command line option of the Windows and Unix launchers does not work for directory names like "Spaces & Special Char"
* [MNG-6180] - groupId has plain color when goal fails
* [MNG-6181] - HttpClient produces a lot of noise at debug loglevel
* [MNG-6183] - Dependency management debug message corrections.

Improvements:

* [MNG-6078] - Can't overwrite properties which have been defined in .mvn/maven.config
* [MNG-6115] - Add Jansi native library search path to our start scripts to avoid extraction to temp file on each run
* [MNG-6179] - Remove unused prerequisites
* [MNG-6189] - WARN if maven-site-plugin configuration contains reportPlugins element

New Feature:

* [MNG-6182] - ModelResolver interface enhancement: addition of resolveModel( Dependency ) supporting version ranges

The full list of changes can be found in our [issue management system][4].

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: http://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12339664&amp;styleName=Text
[5]: ../../docs/history.html
[maven-enforcer-plugin]: /enforcer/maven-enforcer-plugin/
[maven-resources-plugin]: /enforcer/maven-resources-plugin/
[maven-aether-provider]: /ref/3.5.0-alpha-1/maven-aether-provider/
[maven-compat]: /ref/3.5.0-alpha-1/maven-compat/
[maven-resolver]: /resolver/

[MNG-3507]: https://issues.apache.org/jira/browse/MNG-3507
[MNG-5607]: https://issues.apache.org/jira/browse/MNG-5607
[MNG-5297]: https://issues.apache.org/jira/browse/MNG-5297
[MNG-5815]: https://issues.apache.org/jira/browse/MNG-5815
[MNG-5823]: https://issues.apache.org/jira/browse/MNG-5823
[MNG-5836]: https://issues.apache.org/jira/browse/MNG-5836
[MNG-5852]: https://issues.apache.org/jira/browse/MNG-5852
[MNG-5878]: https://issues.apache.org/jira/browse/MNG-5878
[MNG-5895]: https://issues.apache.org/jira/browse/MNG-5895
[MNG-5904]: https://issues.apache.org/jira/browse/MNG-5904
[MNG-5963]: https://issues.apache.org/jira/browse/MNG-5963
[MNG-6001]: https://issues.apache.org/jira/browse/MNG-6001
[MNG-6003]: https://issues.apache.org/jira/browse/MNG-6003
[MNG-6014]: https://issues.apache.org/jira/browse/MNG-6014
[MNG-6017]: https://issues.apache.org/jira/browse/MNG-6017
[MNG-6022]: https://issues.apache.org/jira/browse/MNG-6022
[MNG-6030]: https://issues.apache.org/jira/browse/MNG-6030
[MNG-6057]: https://issues.apache.org/jira/browse/MNG-6057
[MNG-6078]: https://issues.apache.org/jira/browse/MNG-6078
[MNG-6090]: https://issues.apache.org/jira/browse/MNG-6090
[MNG-6092]: https://issues.apache.org/jira/browse/MNG-6092
[MNG-6093]: https://issues.apache.org/jira/browse/MNG-6093
[MNG-6110]: https://issues.apache.org/jira/browse/MNG-6110
[MNG-6115]: https://issues.apache.org/jira/browse/MNG-6115
[MNG-6140]: https://issues.apache.org/jira/browse/MNG-6140
[MNG-6170]: https://issues.apache.org/jira/browse/MNG-6170
[MNG-6173]: https://issues.apache.org/jira/browse/MNG-6173
[MNG-6176]: https://issues.apache.org/jira/browse/MNG-6176
[MNG-6177]: https://issues.apache.org/jira/browse/MNG-6177
[MNG-6179]: https://issues.apache.org/jira/browse/MNG-6179
[MNG-6180]: https://issues.apache.org/jira/browse/MNG-6180
[MNG-6181]: https://issues.apache.org/jira/browse/MNG-6181
[MNG-6182]: https://issues.apache.org/jira/browse/MNG-6182
[MNG-6183]: https://issues.apache.org/jira/browse/MNG-6183
[MNG-6189]: https://issues.apache.org/jira/browse/MNG-6189
[MNG-6190]: https://issues.apache.org/jira/browse/MNG-6190
[MNG-6191]: https://issues.apache.org/jira/browse/MNG-6191
[MNG-6192]: https://issues.apache.org/jira/browse/MNG-6192

[flatten-maven-plugin]: http://www.mojohaus.org/flatten-maven-plugin/
