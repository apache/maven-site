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

# Release Notes &#x2013; Maven 3.3.9

The Apache Maven team would like to announce the release of Maven 3.3.9.

Maven 3.3.9 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model
(POM), Maven can manage a project's build, reporting and documentation from a central place.

The core release is independent of the plugins available. Further releases of plugins will be made separately.
See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [http://maven.apache.org/](/)
- the maven-user mailing list: [http://maven.apache.org/mail-lists.html](/mail-lists.html)
- the reference documentation: [http://maven.apache.org/ref/3.3.9/](/ref/3.3.9/)


Reporters and Contributors of this release
------------------------------------------

Bugs:

 * [MNG-5297] - contributor: Joseph Walton
 * [MNG-5721] - reporter/contributor Martin Schäf
 * [MNG-5786] - reporter Stephan Schroevers
 * [MNG-5787] - reporter Christian Schlichtherle
 * [MNG-5796] - reporter Brandon Enochs
 * [MNG-5812] - contributor tssp
 * [MNG-5816] - contributor tssp
 * [MNG-5858] - contributor Dave Syer
 * [MNG-5877] - contributor Joseph Walton; reporter Anders Forsell
 * [MNG-5882] - contributor Ben Caradoc-Davies
 * [MNG-5884] - contributor Stephen Kitt
 * [MNG-5886] - reporter Shubham Chaurasia
 * [MNG-5891] - reporter Keith Turner
 * [MNG-5898] - reporter Jonathan Radon

Improvements:

 * [MNG-5805] - contributor Anton Tanasenko
 * [MNG-5844] - contributor Tang Xinye
 * [MNG-5871] - make url inheritance algorithm more visible
 * [MNG-5923] - reporter/contributor: Stuart McCulloch
 * [MNG-5924] - reporter/contributor: Stuart McCulloch

Many thanks to all reporters and contributors and for their time and support.

Improvements
------------

 * The `par` lifecycle has been removed from the default life cycle bindings and the maven-ejb3-plugin
   has been removed from default bindings, cause it does not exist [MNG-5892][MNG-5892], [MNG-5894][MNG-5894].

 * The default bindings defined two different versions for the [maven-resources-plugin][maven-resources-plugin]
   which has been fixed by [MNG-5893][MNG-5893].

 * Switch to official [Guice](https://github.com/google/guice/wiki/Motivation) 4.0, upgrade to
   [Eclipse/Sisu](https://www.eclipse.org/sisu/) 0.3.2 has been done with [MNG-5923][MNG-5923] and [MNG-5924][MNG-5924].
 
 * Several areas of Maven Core have been changed to use
   [Commons Lang](https://commons.apache.org/proper/commons-lang/)'s Validate to intercept invalid 
   input [MNG-5649][MNG-5649].

 * Upgrade Java minimum version prerequisite from Java 6 to Java 7 [MNG-5780][MNG-5780].

 * Custom packaging types: configuring DefaultLifecycleMapping mojo executions [MNG-5805][MNG-5805].

 * Disallow the programmatic injection of project dependencies [MNG-5818][MNG-5818].

 * Close IO streams in finally or try-with-resource statement [MNG-5844][MNG-5844].

 * Make url inheritance algorithm more visible [MNG-5871][MNG-5871].  

 * Update used [Modello](http://codehaus-plexus.github.io/modello/) version from 1.8.1 to 1.8.3 [MNG-5888][MNG-5888].  

 * Maven build does not work with Maven 2.2.1 [MNG-5905][MNG-5905].

 * Use canonical name for UTC timezone [MNG-5906][MNG-5906].  

 * Upgrade [maven-parent](/pom/maven/) to version 27 [MNG-5911][MNG-5911].

 * Upgrade [Wagon](/wagon/) version to 2.10 [MNG-5915][MNG-5915].

 * Upgraded to [plexus-component-*](http://codehaus-plexus.github.io/plexus-containers/) 1.6 that uses
   [asm](http://asm.ow2.org/) 5.x [MNG-5921][MNG-5921].

 * Upgrade [plexus-utils](http://codehaus-plexus.github.io/plexus-utils/) to 3.0.22 to support `combine.id` as configuration attribute for Map merging [MNG-5922][MNG-5922].  

 * Update [animal-sniffer-maven-plugin](http://www.mojohaus.org/animal-sniffer/animal-sniffer-maven-plugin/) to 1.14. MANIMALSNIFFER-49 required when building with JDK9 [MNG-5925][MNG-5925].  


Bugs
----

 * Moving from Maven 3.0.5 to 3.3.3 breaks plugins with some dependencies on the classpath.
   This has been fixed with [MNG-5787][MNG-5787].

 * The Cygwin Shell related handling of the `MAVEN_PROJECTBASEDIR` has been fixed
   with [MNG-5812][MNG-5812].

 * The scripts to call Maven has introduced a bug related to the handling of the
   `MAVEN_OPTS` and debugging options which has been fixed by [MNG-5813][MNG-5813].

 * Since Maven 3.3.1 it is possible to have configurations stored on a per project base in the 
   `${maven.projectBasedir}/.mvn` folder of the project. There you can use the `maven.config` 
   file to store command line options instead of repeating them every time you call Maven.
   In cases where this file has been empty Maven ended with a failure. This has been fixed
   with [MNG-5816][MNG-5816].

 * The handling of the relativePath in a parent has been fixed related to the case
   that the parent has the same groupId:artifactId but a different version. In this
   case the resolution must be done against the repository.
   This has been fixed by [MNG-5840][MNG-5840].

 * In cases where you start Maven in the root of a windows drive Maven will fail.
   This has been fixed by [MNG-5796][MNG-5796]. 

 * The `<prerequisites>` elements is intended for [buildtime checking but not for runtime checks][MNG-4840] 
   which should be left to [maven-enforcer-plugin][maven-enforcer-plugin]. 
   This has not been documented accordingly. This has been done with [MNG-5297][MNG-5297].

 * In situations like this: `mvn -Dtest=\"anton\" clean package` the trailing quote
   is stripped away which could cause problems. This has been fixed with [MNG-5681][MNG-5681].

 * Possible NullPointerException in org.apache.maven.repository.MetadataResolutionResult 
   has been fixed with [MNG-5721].

 * There had been several issues with the `mvn` script which are for example
   wrong locating the `.mvn` folder, nonportable shell constructs, wrongly setting
   'maven.multiModuleProjectDirectory' variable or directories which contain spaces. Those
   issues have been fixed [MNG-5786][MNG-5786], [MNG-5858][MNG-5858], 
   [MNG-5882][MNG-5882] and [MNG-5884][MNG-5884].

 * Broken link of 'Building Maven' in README.md has been fixed by [MNG-5886][MNG-5886].

 * [maven-aether-provider][maven-aether-provider]/[maven-compat][maven-compat] 
   does not always generate snapshot versions using Gregorian calendar year 
   fixed in [MNG-5877][MNG-5877] 

 * Log file command line option description contains an extra word has been fixed by [MNG-5891][MNG-5891] 

 * org.apache.maven.repository.internal.RemoteSnapshotMetadataTest fails to start at midnight fixed with
   [MNG-5907][MNG-5907].

 * Multi-module build with ear fails to resolve war in 3.3.3 fixed in [MNG-5898][MNG-5898].


Task
----

 * Update [Modello site url](http://codehaus-plexus.github.io/modello/) [MNG-5887][MNG-5887].


The full list of changes can be found in our [issue management system][4].

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: http://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12333074
[5]: ../../docs/history.html
[maven-enforcer-plugin]: /enforcer/maven-enforcer-plugin/
[maven-resources-plugin]: /enforcer/maven-resources-plugin/
[maven-aether-provider]: /ref/3.3.9/maven-aether-provider/
[maven-compat]: /ref/3.3.9/maven-compat/
[MNG-4840]: https://issues.apache.org/jira/browse/MNG-4840
[MNG-5297]: https://issues.apache.org/jira/browse/MNG-5297
[MNG-5649]: https://issues.apache.org/jira/browse/MNG-5649
[MNG-5681]: https://issues.apache.org/jira/browse/MNG-5681
[MNG-5721]: https://issues.apache.org/jira/browse/MNG-5721
[MNG-5780]: https://issues.apache.org/jira/browse/MNG-5780
[MNG-5786]: https://issues.apache.org/jira/browse/MNG-5786
[MNG-5787]: https://issues.apache.org/jira/browse/MNG-5787
[MNG-5796]: https://issues.apache.org/jira/browse/MNG-5796
[MNG-5805]: https://issues.apache.org/jira/browse/MNG-5805
[MNG-5812]: https://issues.apache.org/jira/browse/MNG-5812
[MNG-5813]: https://issues.apache.org/jira/browse/MNG-5813
[MNG-5816]: https://issues.apache.org/jira/browse/MNG-5816
[MNG-5818]: https://issues.apache.org/jira/browse/MNG-5818
[MNG-5840]: https://issues.apache.org/jira/browse/MNG-5840
[MNG-5844]: https://issues.apache.org/jira/browse/MNG-5844
[MNG-5858]: https://issues.apache.org/jira/browse/MNG-5858
[MNG-5871]: https://issues.apache.org/jira/browse/MNG-5871
[MNG-5877]: https://issues.apache.org/jira/browse/MNG-5877
[MNG-5882]: https://issues.apache.org/jira/browse/MNG-5882
[MNG-5884]: https://issues.apache.org/jira/browse/MNG-5884
[MNG-5886]: https://issues.apache.org/jira/browse/MNG-5886
[MNG-5887]: https://issues.apache.org/jira/browse/MNG-5887
[MNG-5888]: https://issues.apache.org/jira/browse/MNG-5888
[MNG-5891]: https://issues.apache.org/jira/browse/MNG-5891
[MNG-5892]: https://issues.apache.org/jira/browse/MNG-5892
[MNG-5893]: https://issues.apache.org/jira/browse/MNG-5893
[MNG-5894]: https://issues.apache.org/jira/browse/MNG-5894
[MNG-5898]: https://issues.apache.org/jira/browse/MNG-5898
[MNG-5905]: https://issues.apache.org/jira/browse/MNG-5905
[MNG-5906]: https://issues.apache.org/jira/browse/MNG-5906
[MNG-5907]: https://issues.apache.org/jira/browse/MNG-5907
[MNG-5911]: https://issues.apache.org/jira/browse/MNG-5911
[MNG-5915]: https://issues.apache.org/jira/browse/MNG-5915
[MNG-5921]: https://issues.apache.org/jira/browse/MNG-5921
[MNG-5922]: https://issues.apache.org/jira/browse/MNG-5922
[MNG-5923]: https://issues.apache.org/jira/browse/MNG-5923
[MNG-5924]: https://issues.apache.org/jira/browse/MNG-5924
[MNG-5925]: https://issues.apache.org/jira/browse/MNG-5925
