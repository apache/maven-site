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

# Maven 3.10.0 Release Notes

The Apache Maven team would like to announce the release of Maven 3.10.0.

Maven 3.10.0 is [available for download][0].

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [Plugin list][1] for more information.

If you have any questions, please consult:

- the website: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.10.0/](/ref/3.10.0/)

## Overview About the Changes

The main driver for new generation was to align Maven 3.10.x with Maven 4+ versions regarding Resolver version, hence Maven 3.10.0 same major version of Resolver 2.0.x line, that is used by Maven 4+. On the other hand, at "Maven level", changes are minimal, and upgrading should be pretty straightforward.
Maven 3.10.0 received many security and other enhancements as well. Still, most of the changes happened "under the surface", so end users might even be unaware of them. some of the key changes (without completeness) are:
* use of Resolver 2.0.x
* full `settings.xml` interpolation, no Maven 3.x version allowed interpolating values like ports before
* promotion of new properties: `session.topDirectory` and `session.rootDirectory`; now Maven 3.10 and 4+ both supports use of these properties as any other property
* support for user-wide and installation-wide extensions, from now on, not only the `.mvn/extensions.xml` are observed, but also `~/.m2/extensions.xml` and `$MAVEN_HOME/conf/extensions.xml` as well.

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-3.10.0).

## Known issues

No known issues.

## Potentially Breaking Core Changes (if migrating from 3.9.x)

Maven 3.10.0 raises the POM validation level, many WARNINGs from 3.9 lineage (in fact, from 3.1 lineage) will not FAIL the build. Things like duplicated dependency entries or duplicated plugin entries will not be tolerated anymore. This aspect was not changed since Maven 3.1 lineage, and was really time to force users to clean up their POMs.

## Complete Release Notes

See [complete release notes for all versions][3]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[3]: ../../docs/history.html

