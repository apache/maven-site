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
-->

# Release Notes &#x2013; Maven 4.0.0-alpha-13

The Apache Maven team would like to announce the release of Maven 4.0.0-alpha-13.

This is the first Maven 4 preview release that **requires Java 17 for runtime**.

This is alpha release, not suitable for production.

Maven 4.0.0-alpha-13 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the website: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/4.0.0-alpha-13/](/ref/4.0.0-alpha-13/)

## Overview About the Changes

The full list of changes can be found in our [issue management system][4].

Notable changes include:
* **requires Java 17 as "minimum runtime Java requirement"**
* brings the latest Maven Resolver 2.0.0-alpha-8
* new features like Profile OS activation with wildcards, Profile activation by packaging, SPI for user properties
* improvements related to dependencyManagement and BOM handling, new APIs for "dependency path control"
* and many bug fixes since alpha-12

## Known Issues

If you find any incompatibility with latest versions of plugins, do not hesitate to report those.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: https://dlcdn.apache.org/maven/maven-4/4.0.0-alpha-13/
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12354062
[5]: ../../docs/history.html

