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

# Release Notes &#x2013; Maven 3.3.4

The Apache Maven team would like to announce the release of Maven 3.3.4.

Maven 3.3.4 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model
(POM), Maven can manage a project's build, reporting and documentation from a central place.

Maven 3 aims to ensure backward compatibility with Maven 2, improve usability, increase performance, allow safe embedding, and pave the way to implement many highly demanded features.

The core release is independent of the plugins available. Further releases of plugins will be made separately.
See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [http://maven.apache.org/](http://maven.apache.org/)
- the maven-user mailing list: [http://maven.apache.org/mail-lists.html](http://maven.apache.org/mail-lists.html)
- the reference documentation: [http://maven.apache.org/ref/3.3.4/](http://maven.apache.org/ref/3.3.4/)

Bugs
----

 * Since Maven 3.3.1 it is possible to have configurations stored on a per project base in the 
   `${maven.projectBasedir}/.mvn` folder of the project. There you can use the `maven.config` 
   file to store command line options instead of repeating them every time you call maven.
   In cases where the given file has been empty Maven ended with a failure. This has been fixed
   with [MNG-5816][MNG-5816].

 * The new scripts to call Maven had introduced a bug related to the handling of the
   `MAVEN_OPTS` and debugging options which has been fixed by [MNG-5813][MNG-5813].

 * The Cygwin Shell related handling of the `MAVEN_PROJECTBASEDIR` has been fixed
   with [MNG-5812][MNG-5812].

 * The handling of the relativePath in a parent has been fixed related to the case
   that the parent has the same groupId:artifactId but a different version. In this
   case the resolution must be done against the repository.
   This has been fixed by [MNG-5840][MNG-5840].

Improvement
-----------

 * Maven 3.3.4 has been enhanced to support mapping phases to goals with custom configurations.


The full list of changes can be found in our [issue management system][4].

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: http://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12332076
[5]: ../../docs/history.html
[MNG-5812]: https://issues.apache.org/jira/browse/MNG-5812
[MNG-5813]: https://issues.apache.org/jira/browse/MNG-5813
[MNG-5816]: https://issues.apache.org/jira/browse/MNG-5816
[MNG-5840]: https://issues.apache.org/jira/browse/MNG-5840
