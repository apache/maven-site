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

# Release Notes &#x2013; Maven 4.0.0-alpha-2

The Apache Maven team would like to announce the release of Maven 4.0.0-alpha-2.

This in alpha release, not suitable for production.

Maven 4.0.0-alpha-2 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the website: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/4.0.0-alpha-2/](/ref/4.0.0-alpha-2/)

## Overview About the Changes

* cli improvement: `--also-make` , `--resume`, `--non-recursive`, `--fail-on-severity`
* consistent timestamps
* build/consumer POMs (automatic parent versioning, automatic versioning of reactor dependencies, automatic detection of child modules)
* new maven 4 api

The full list of changes can be found in our [issue management system][4].

## Known Issues

A few incompatibilities with maven 3.x have been discovered already, we are working on fixing those for the next alpha release.  Do not hesitate to report any problem.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: https://dlcdn.apache.org/maven/maven-4/4.0.0-alpha-2/
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?version=12351403&projectId=12316922
[5]: ../../docs/history.html

