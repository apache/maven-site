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

# Release Notes &#x2013; Maven 4.0.0-alpha-7

The Apache Maven team would like to announce the release of Maven 4.0.0-alpha-7.

This is alpha release, not suitable for production.

Maven 4.0.0-alpha-7 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/4.0.0-alpha-7/](/ref/4.0.0-alpha-7/)

## Overview About the Changes

This release contains an important list of new features and enhancements. The full list of changes can be found in our [issue management system][4]. Among those are:
- support for project specific settings.xml
- command line arguments interpolation
- profile-free repository support in settings
- support for exclusions when importing BOMs
- new public rootDirectory and topDirectory properties
- allow comments in .mvn/maven.config file
- plugin validation improvements (same as 3.9.x)
- infer modelVersion when using the build/consumer POM feature
- make maven-compat optional at runtime

## Known Issues

If you find any incompatibility with latest versions of plugins, do not hesitate to report those.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: https://dlcdn.apache.org/maven/maven-4/4.0.0-alpha-7/
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12353052
[5]: ../../docs/history.html

