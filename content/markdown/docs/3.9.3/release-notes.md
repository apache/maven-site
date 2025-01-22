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

# Release Notes &#x2013; Maven 3.9.3

The Apache Maven team would like to announce the release of Maven 3.9.3.

Maven 3.9.3 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.9.3/](/ref/3.9.3/)

## Overview About the Changes

* Regression fixes and changes based on user feedback from Maven 3.9.2
* General performance and other fixes

The full list of changes can be found in our [issue management system][4].

### Notable New Features

* Huge effort of updating ASF Maven plugins is ongoing, and Maven received a ton of lifecycle bound plugin version updates since Maven 3.9.2 (that was
  released with same plugin versions as Maven 3.9.1 and 3.9.0).
* Plugin validation did shake up Maven users community, hence, on users request, they are "toned down". Validation messages are always collected (as
  before), but default display mode is again "inline" as it was in Maven 3.9.1. Moreover, by default only "project local" messages are displayed to
  user: issues that user can fix by editing the project POM. Plugin non-configuration issues, that can be fixed by corresponding plugin developer only
  (and requires a release and updating in current project POM) are NOT displayed anymore by default. To enjoy them, one needs explicitly to enable
  "verbose" mode for plugin validation. Furthermore, the precision of warnings and some badly worded messages are fixed.
* Updated Resolver brings transport and locking related fixes and improvements.
  Most notably [MNG-7819](https://issues.apache.org/jira/browse/MNG-7819) got fixed that was affecting Maven 3.9.2, but the fix have important implication:
  due file locking naming scheme changes, Maven 3.9.3 with file locking will not be able to share local repository with another Maven version that
  predates 3.9.3 (Hence, versions 3.9.0, 3.9.1 or 3.9.2), only with another 3.9.3 or newer version.
  See [Resolver configuration](https://maven.apache.org/resolver/configuration.html) and [named locks](https://maven.apache.org/resolver/maven-resolver-named-locks/)
  pages for more.

### Potentially Breaking Core Changes (if migrating from 3.8.x)

* The Maven Resolver transport has changed from Wagon to "native HTTP", see [Resolver Transport guide](/guides/mini/guide-resolver-transport.html).
* Maven 2.x was auto-injecting an ancient version of `plexus-utils` dependency into the plugin classpath, and Maven 3.x continued doing this to preserve backward compatibility. Starting with Maven 3.9, it does not happen anymore. This change may lead to plugin breakage. The fix for affected plugin maintainers is to explicitly declare a dependency on `plexus-utils`. The workaround for affected plugin users is to add this dependency to plugin dependencies until issue is fixed by the affected plugin maintainer. See [MNG-6965](https://issues.apache.org/jira/browse/MNG-6965).
* Mojos are prevented to bootstrap new instance of `RepositorySystem` (for example by using deprecated `ServiceLocator`), they should reuse `RepositorySystem` instance provided by Maven instead. See [MNG-7471](https://issues.apache.org/jira/browse/MNG-7471).
* Each line in `.mvn/maven.config` is now interpreted as a single argument. That is, if the file contains multiple arguments, these must now be placed on separate lines, see [MNG-7684](https://issues.apache.org/jira/browse/MNG-7684).
* System and user properties handling cleanup, see [MNG-7556](https://issues.apache.org/jira/browse/MNG-7556). As a consequence, this may introduce breakage in environments where the user properties were used to set system properties or other way around, for example see [MNG-7887](https://issues.apache.org/jira/projects/MNG/issues/MNG-7887).
* Plugins and extensions used by your build are checked against Maven supported APIs and conventions: this "plugin validation" may report WARNINGs at the end of your build. See [plugin validation documentation](../../guides/plugins/validation/) to better understand what to do when your build suffers from such warnings.

## Known Issues

* [MNG-7826](https://issues.apache.org/jira/browse/MNG-7826) Discovered late in release process, plugin validation will now miss some plugins (like jacoco is for example, that have transitive dependencies, that should flag plugin).

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12353255
[5]: ../../docs/history.html

