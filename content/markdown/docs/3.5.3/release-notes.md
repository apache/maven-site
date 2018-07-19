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

# Release Notes &#x2013; Maven 3.5.3

The Apache Maven team would like to announce the release of Maven 3.5.3

Maven 3.5.3 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central place.

The core release is independent of the plugins available. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.5.3/](/ref/3.5.3/)

## Reporters and Contributors of this release

We really value the contributions of these non committers, so this section is focused on those individuals. Descriptions of the issues fixed can be found at the [end of these release notes](#Details).

Bugs:

- [MNG-6323][] reporter: Ben Caradoc-Davies
- [MNG-6320][] reporter: Eugene Pliskin
- [MNG-6300][] reporter: Andreas Kurth
- [MNG-6298][] reporter: Ryan Heaton
- [MNG-6296][] reporter: Robin Müller
- [MNG-6282][] reporter: Dejan Stojadinović
- [MNG-6255][] reporter: Andrew Kennedy

Improvements:

- [MNG-6340][] reporter: Tony Guan
- [MNG-6306][] reporter: Andy Wilkinson
- [MNG-5992][] reporter: Ryan J. McDonough

Many thanks to all reporters and contributors for their time and support.

## Preliminary Testers

Thank you also for your time and feedback.

## Known Issues

One new issue was identified during the release testing. This issue affects Windows users. When running Maven with parallel threads, i.e. the `-T` command line option, Maven may output spurious ANSI escapes such as `[0m [0m` [MNG-6372][]

## Overview about the changes

- Issues have been fixed related to colorizations like to clean up the situation while interrupting the build process [MNG-6188][] and some issues related to Git Bash / Cygwin have been fixed [MNG-6282][] and the new options `-Dstyle.color` [MNG-6296][] has been fixed.

- The handling CRLF in `jvm.config` file has been fixed [MNG-6255][].

- The wrong usage of the CI friendly version was not correctly identified which has been improved [MNG-6305][].

- Wrong encoding of non-ascii filenames has been fixed [MNG-6320][].

- Deadlock in dependency resolution has been fixed [MNG-6323][].

- A regression related to parents `relativePath` verification has been fixed [MNG-6330][].

So now some more interesting things about new (small) features:

- The log output contains now some progress informations related to the number of modules which looks like this [MNG-6302][]:

  ```
  [INFO] Building parent 5.0.1-SNAPSHOT                                     [1/9]
  ```

  The number `1` is the current number which is being built where the `9` in this case is the number of modules which have to be built overall. So we are in module 1 of 9.

  Furthermore the information about the packaging type and the groupId/artifactId are now being shown during the build like the following [MNG-6308][]:

  ```
  [INFO] ------------------< com.soebes.examples.j2ee:parent >-------------------
  [INFO] Building parent 5.0.1-SNAPSHOT                                     [1/9]
  [INFO] --------------------------------[ pom ]---------------------------------
  [INFO]
  ```

  And finally we have added the version of the modules or the reactor at the end of the build [MNG-6352]:

  ```
  [INFO] ------------------------------------------------------------------------
  [INFO] Reactor Summary:
  [INFO]
  [INFO] parent 5.0.1-SNAPSHOT .............................. SUCCESS [  0.238 s]
  [INFO] domain ............................................. SUCCESS [  0.014 s]
  [INFO] service-client ..................................... SUCCESS [  0.008 s]
  [INFO] webgui ............................................. SUCCESS [  0.010 s]
  [INFO] service ............................................ SUCCESS [  0.007 s]
  [INFO] app ................................................ SUCCESS [  0.005 s]
  [INFO] appasm ............................................. SUCCESS [  0.005 s]
  [INFO] shade .............................................. SUCCESS [  0.006 s]
  [INFO] assembly 5.0.1-SNAPSHOT ............................ SUCCESS [  0.005 s]
  [INFO] ------------------------------------------------------------------------
  ```

  This can be helpful if you have a large number of modules to get the information about the version being built. This meant in the past to scroll up to the last module and look there for the version. Now this can simply being seen at the end of build. If you have a multi module build where the number is the same for all modules it will be given only on the first line and the last line. If you have an aggregator build the version will be printed out for each project.

- One more thing has been optimized. We have removed the `System.gc()` call at the end of the build because this can cause costs for example on AWS systems related to the time taken to run Garbage Collection which is not really necessary [MNG-6340][]. This means also the resulting output will change a little bit like this:

  ```
  [INFO] ------------------------------------------------------------------------
  [INFO] BUILD SUCCESS
  [INFO] ------------------------------------------------------------------------
  [INFO] Total time: 6.111 s
  [INFO] Finished at: 2018-02-25T15:34:10+01:00
  [INFO] ------------------------------------------------------------------------
  ```

  So there is no line anymore telling you about memory stuff.

- If you have used the deprecated version markers like `RELEASE` or `LATEST` this will now produce a WARNING during the build [MNG-6342][].

## [The detailed issue list](#Details)

Bugs:

- [MNG-6188][] - Console color not properly reset when interrupting build process
- [MNG-6255][] - Maven script cannot parse `jvm.config` with CRLF
- [MNG-6282][] - Console output has no colors in shell (both Git Bash and Cygwin) (regression in Jansi 1.16 / Maven 3.5.1)
- [MNG-6296][] - New option `-Dstyle.color` is not working
- [MNG-6298][] - 3.5.2: `ClassNotFoundException: javax.annotation.security.RolesAllowed`
- [MNG-6300][] - Multi module release creates empty directories in war file instead of jars
- [MNG-6305][] - Validation of CI friendly version incorrect
- [MNG-6320][] - Apparently wrong encoding of non-ascii java class filename in error messages in the maven log
- [MNG-6323][] - Deadlock in multithreaded dependency resolution
- [MNG-6330][] - (regression) Parents relativePath not verified anymore

### New Feature

- [MNG-6302][] - Provide some "progress" hints

### Improvements

- [MNG-5992][] - Git passwords are exposed as the Super POM still uses Maven Release Plugin 2.3.2
- [MNG-6306][] - Replace use of Guava in maven-resolver-provider with a lighter weight alternative
- [MNG-6308][] - display packaging & groupId:artifactId when building a module
- [MNG-6332][] - Cleaned up `mvn.cmd` Script
- [MNG-6340][] - (Performance) To make `System.gc()` call configurable in target summary code
- [MNG-6342][] - Emit a WARNING about LATEST/RELEASE in parent
- [MNG-6352][] - Printout version information at the end of the build

### Task

- [MNG-6331][] - Remove maven-bundle-pugin from build pluginManagement

### Dependency upgrade

- [MNG-6312][] - Update Maven Wagon dependency
- [MNG-6335][] - Update test framework Mockito from 1.10 to 2.12
- [MNG-6353][] - Upgrade maven-shared-utils to 3.2.1

The full list of changes can be found in our [issue management system](https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12341428).

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12341428
[5]: ../../docs/history.html
[MNG-5992]: https://issues.apache.org/jira/browse/MNG-5992
[MNG-6188]: https://issues.apache.org/jira/browse/MNG-6188
[MNG-6255]: https://issues.apache.org/jira/browse/MNG-6255
[MNG-6282]: https://issues.apache.org/jira/browse/MNG-6282
[MNG-6296]: https://issues.apache.org/jira/browse/MNG-6296
[MNG-6298]: https://issues.apache.org/jira/browse/MNG-6298
[MNG-6300]: https://issues.apache.org/jira/browse/MNG-6300
[MNG-6302]: https://issues.apache.org/jira/browse/MNG-6302
[MNG-6305]: https://issues.apache.org/jira/browse/MNG-6305
[MNG-6306]: https://issues.apache.org/jira/browse/MNG-6306
[MNG-6308]: https://issues.apache.org/jira/browse/MNG-6308
[MNG-6312]: https://issues.apache.org/jira/browse/MNG-6312
[MNG-6320]: https://issues.apache.org/jira/browse/MNG-6320
[MNG-6323]: https://issues.apache.org/jira/browse/MNG-6323
[MNG-6330]: https://issues.apache.org/jira/browse/MNG-6330
[MNG-6331]: https://issues.apache.org/jira/browse/MNG-6331
[MNG-6332]: https://issues.apache.org/jira/browse/MNG-6332
[MNG-6335]: https://issues.apache.org/jira/browse/MNG-6335
[MNG-6340]: https://issues.apache.org/jira/browse/MNG-6340
[MNG-6342]: https://issues.apache.org/jira/browse/MNG-6342
[MNG-6352]: https://issues.apache.org/jira/browse/MNG-6352
[MNG-6353]: https://issues.apache.org/jira/browse/MNG-6353
[MNG-6372]: https://issues.apache.org/jira/browse/MNG-6372
