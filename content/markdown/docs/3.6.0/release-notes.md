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

# Release Notes &#x2013; Maven 3.6.0

The Apache Maven team would like to announce the release of Maven 3.6.0

Maven 3.6.0 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central place.

The core release is independent of the plugins available. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.6.0/](/ref/3.6.0/)

## Reporters and Contributors of this release

We really value the contributions of these non committers, so this section is
focused on those individuals. Descriptions of the issues fixed can be found at
the [end of these release notes](#Details).

Code Contributors of this release:

 * [MNG-6383] Christoph Kunze
 * [MNG-6311] David Churcher

Issue Reporters of this release:

 * [MNG-4508] Richard van der Hoff
 * [MNG-5951] Jörg Sesterhenn
 * [MNG-6059] Andreas Sewe
 * [MNG-6311] David Churcher
 * [MNG-6358] Adam John Burley
 * [MNG-6383] Christoph Kunze
 * [MNG-6391] Alexander Griesbaum
 * [MNG-6412] Christoph Amshoff
 * [MNG-6415] Seckin Onur Selamet
 * [MNG-6475] Phillip Webb
 * [MNG-6490] John Canny
 * [MNG-6492] Hoa Phan

Many thanks to all reporters and contributors for their time and support.

## Preliminary Testers

Thanks to the following preliminary testers:

- Filipe Sousa 
- Eric Lilja
- Enrico Olivelli
- Gary Gregory
- Thomas Collignon

## Known Issues

At the time of release, there are no known regressions introduced by this release.

## Overview about the changes

- There had been issues related to the project discoverytime which has
  been increased in previous version which influenced some of our users.
  This should have been fixed [MNG-6311], [MNG-6383] and [MNG-6412].

- The output in the reactor summary has been improved [MNG-6391] 
  cause it confused people. In Maven 3.6.0 the reactor summary now
  looks like the following:

```
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for parent 5.0.4-SNAPSHOT:
[INFO]
[INFO] parent ............................................. SUCCESS [  1.559 s]
[INFO] domain ............................................. SUCCESS [  1.238 s]
[INFO] service-client ..................................... SUCCESS [  0.108 s]
[INFO] webgui ............................................. SUCCESS [  0.652 s]
[INFO] service ............................................ SUCCESS [  0.379 s]
[INFO] app ................................................ SUCCESS [  0.304 s]
[INFO] appasm ............................................. SUCCESS [  0.265 s]
[INFO] shade .............................................. SUCCESS [  0.440 s]
[INFO] assembly ........................................... SUCCESS [  1.531 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  6.824 s
[INFO] Finished at: 2018-11-01T12:20:16+01:00
[INFO] ------------------------------------------------------------------------
```

  The `parent` in the above output is the artifact name of the root module and 
  the `5.0.4-SNAPSHOT` is the versions number for all modules in this
  reactor build.

  If you have an aggregator pom which contains different modules with different
  versions each line will contain the appropriate versions which looks like this:
```
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO]
[INFO] Apache Maven ACR Plugin 3.0.1-SNAPSHOT ............. SUCCESS [  0.221 s]
[INFO] Apache Maven AntRun Plugin 3.0.0-SNAPSHOT .......... SUCCESS [  0.004 s]
[INFO] Apache Maven Changelog Plugin 2.4-SNAPSHOT ......... SUCCESS [  0.005 s]
[INFO] Apache Maven Changes Plugin 3.0.0-SNAPSHOT ......... SUCCESS [  0.487 s]
[INFO] Apache Maven Clean Plugin 3.0.1-SNAPSHOT ........... SUCCESS [  0.003 s]
[INFO] Apache Maven Compiler Plugin 3.7.1-SNAPSHOT ........ SUCCESS [  0.026 s]
[INFO] Apache Maven Deploy Plugin 3.0.0-SNAPSHOT .......... SUCCESS [  0.005 s]
[INFO] Apache Maven Documentation Checker Plugin 1.2-SNAPSHOT SUCCESS [  0.058 s]
[INFO] Apache Maven EAR Plugin 3.0.0-SNAPSHOT ............. SUCCESS [  0.003 s]
[INFO] Apache Maven EJB Plugin 3.0.1-SNAPSHOT ............. SUCCESS [  0.004 s]
...
```

- There was an issue related to the classpath ordering [MNG-6415] in Maven which 
  can cause issues which has been fixed.

## [The detailed issue list](#Details)

### Bugs:

- [MNG-6311] - Maven intolerably slow when import scope used heavily in large project
- [MNG-6358] - Maven build should not require access to apache.org
- [MNG-6383] - ProjectBuilder unnecessarily rebuilds modules with ci-friendly versions
- [MNG-6412] - Exceeding project discovery time when using CI friendly versions
- [MNG-6415] - Project Artifacts Cache does not retain the order of classpath entries.
- [MNG-6472] - Mockito cannot mock this class: interface org.eclipse.aether.impl.RepositoryEventDispatcher
- [MNG-6490] - Maven shall not fail reporting circular dependency when the dependency is a classified secondary artifact

### Improvements:

- [MNG-4508] - No way to avoid adding artifactId to site urls
- [MNG-5951] - add an option to avoid path addition to inherited URLs
- [MNG-6059] - Important use cases not covered, as child.inherit.append.path affects all children
- [MNG-6164] - Collections inconsistently immutable
- [MNG-6391] - Printout version of last built module in reactor build
- [MNG-6414] - Add more Apache license header patterns to skip downloading Apache license
- [MNG-6467] - Remove plugin definition from pom file which is inherited
- [MNG-6480] - Eclipse.org site is down, and you are unable to build Maven?
- [MNG-6492] - Minor improvement on Array construction, converson

### Task

- [MNG-6475] - Remove guava dependencies

### Dependency upgrades

- [MNG-6424] - Upgrade plexus-interpolation to 1.25
- [MNG-6449] - Upgrade parent to 32
- [MNG-6473] - Update Mockito to 2.21.0
- [MNG-6478] - Upgrade parent to 33 for sha512 checksum on release
- [MNG-6479] - Upgrade XMLUnit to 2.2.1
- [MNG-6486] - Upgrade to Wagon 3.2.0
- [MNG-6489] - Upgrade Maven Resolver to 1.3.0
- [MNG-6491] - Upgrade commons-lang3 to 3.8.1
- [MNG-6496] - Upgrade Maven Resolver to 1.3.1
- [MNG-6497] - Upgrade guice to 4.2.1

The full list of changes can be found in our [issue management system][4].

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12338966
[5]: ../../docs/history.html
[MNG-4508]: https://issues.apache.org/jira/browse/MNG-4508
[MNG-5951]: https://issues.apache.org/jira/browse/MNG-5951
[MNG-6059]: https://issues.apache.org/jira/browse/MNG-6059
[MNG-6164]: https://issues.apache.org/jira/browse/MNG-6164
[MNG-6311]: https://issues.apache.org/jira/browse/MNG-6311
[MNG-6358]: https://issues.apache.org/jira/browse/MNG-6358
[MNG-6383]: https://issues.apache.org/jira/browse/MNG-6383
[MNG-6391]: https://issues.apache.org/jira/browse/MNG-6391
[MNG-6412]: https://issues.apache.org/jira/browse/MNG-6412
[MNG-6414]: https://issues.apache.org/jira/browse/MNG-6414
[MNG-6415]: https://issues.apache.org/jira/browse/MNG-6415
[MNG-6424]: https://issues.apache.org/jira/browse/MNG-6424
[MNG-6449]: https://issues.apache.org/jira/browse/MNG-6449
[MNG-6467]: https://issues.apache.org/jira/browse/MNG-6467
[MNG-6472]: https://issues.apache.org/jira/browse/MNG-6472
[MNG-6473]: https://issues.apache.org/jira/browse/MNG-6473
[MNG-6475]: https://issues.apache.org/jira/browse/MNG-6475
[MNG-6478]: https://issues.apache.org/jira/browse/MNG-6478
[MNG-6479]: https://issues.apache.org/jira/browse/MNG-6479
[MNG-6480]: https://issues.apache.org/jira/browse/MNG-6480
[MNG-6486]: https://issues.apache.org/jira/browse/MNG-6486
[MNG-6489]: https://issues.apache.org/jira/browse/MNG-6489
[MNG-6490]: https://issues.apache.org/jira/browse/MNG-6490
[MNG-6491]: https://issues.apache.org/jira/browse/MNG-6491
[MNG-6492]: https://issues.apache.org/jira/browse/MNG-6492
[MNG-6496]: https://issues.apache.org/jira/browse/MNG-6496
[MNG-6497]: https://issues.apache.org/jira/browse/MNG-6497
