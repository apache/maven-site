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

# Maven 3.10.0-rc-1 Release Notes

The Apache Maven team would like to announce the release of Maven 3.10.0-rc-1.

Maven 3.10.0-rc-1 is [available for download][0].

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [Plugin list][1] for more information.

If you have any questions, please consult:

- the website: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.10.0-rc-1/](/ref/3.10.0-rc-1/)

## Overview of the Changes

## Notable Changes in Maven 3.10.0-rc-1

### New Features and Improvements

- use resolver 2.x in Maven core 
- allow defining user-wide and installation-wide Maven extensions
- promote `session.topDirectory`, `session.rootDirectory` and `project.rootDirectory` properties
- version range filtering
- allow defining user relocations
- allow using transitive dependency manager
- in a failed build limit reactor summary to only failed modules

### New API and Updates for Maven Plugins

- upgrade `SLF4J` to 2.x
- promote `MessageBuilderFactory` service for colored message support
- promote java version in `JavaToolchain`

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-3.10.0-rc-1).

## Known issues

No known issues.

## Potentially Breaking Core Changes (if migrating from 3.9.x)

### Changes in the Super POM:
 
- removed deprecated `release-profile`; each project should have its own release profile
- removed plugin management; each project should have its own plugin management
- set default values for `project.build.sourceEncoding` and `project.reporting.outputEncoding` to `UTF-8`

## Complete Release Notes

See [complete release notes for all versions][3]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[3]: ../../docs/history.html
