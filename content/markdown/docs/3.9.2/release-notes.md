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

# Release Notes &#x2013; Maven 3.9.2

The Apache Maven team would like to announce the release of Maven 3.9.2.

Maven 3.9.2 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.9.2/](/ref/3.9.2/)

## Overview About the Changes

* Regression fixes from Maven 3.9.1
* General performance and other fixes

The full list of changes can be found in our [issue management system][4].

### Notable New Features

* Dependency upgrade [MNG-7769](https://issues.apache.org/jira/browse/MNG-7769) lifted Resolver to version 1.9.10. Maven 3.9.1 used Resolver 1.9.7. For
  changes since Resolver 1.9.7 see release notes for Resolver [1.9.8](https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12320628&version=12352986),
  [1.9.9](https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12320628&version=12353151) and
  [1.9.10](https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12320628&version=12353177).
* Task [MNG-7774](https://issues.apache.org/jira/browse/MNG-7774) implemented interpolation for configuration and command line, but also provides two new
  properties usable in configuration interpolation: `session.topDirectory` (reactor top directory) and `session.rootDirectory` (project root directory, usually where `.mvn`
  directory reside). It is recommended to create `.mvn` directory in project root directory, as presence of this directory is used to
  detect root directory location. If `.mvn` directory does not exists, root directory will not be detected, and in such case attempted use of expression `session.rootDirectory` in interpolation will make Maven refuse to start (will report error).
  It is important to mention, that those two new properties, are only working within the `maven.config` file and
  via the command line. They are **NOT** usable as configuration properties within your plugin configuration in your `pom.xml` file.
* Plugin validation warnings change: they are not littered in console log anymore, but are collected and reported at the build end. Moreover, the validation checks
  have been extended, more warnings are to be expected if build contains plugins that may not work with upcoming Maven 4. The build end validation report
  verbosity can be controlled by `maven.plugin.validation` property values "brief", "default" and "verbose". The validation report cannot be turned off,
  only by not having validation issues in the build, when the report is not shown. See [MNG-7712](https://issues.apache.org/jira/browse/MNG-7712),
  [MNG-7754](https://issues.apache.org/jira/browse/MNG-7754) and [MNG-7767](https://issues.apache.org/jira/browse/MNG-7767). Almost all of the ASF Maven plugins
  have been released with fixes to get rid of warnings, updating them is recommended. See [Available Plugins](https://maven.apache.org/plugins/) page for current
  plugin versions. For non-ASF plugins, contact plugin maintainers to apply required changes.

### Potentially Breaking Core Changes (if migrating from 3.8.x)

* The Maven Resolver transport has changed from Wagon to "native HTTP", see [Resolver Transport guide](/guides/mini/guide-resolver-transport.html).
* Maven 2.x was auto-injecting an ancient version of `plexus-utils` dependency into the plugin classpath, and Maven 3.x continued doing this to preserve backward compatibility. Starting with Maven 3.9, it does not happen anymore. This change may lead to plugin breakage. The fix for affected plugin maintainers is to explicitly declare a dependency on `plexus-utils`. The workaround for affected plugin users is to add this dependency to plugin dependencies until issue is fixed by the affected plugin maintainer. See [MNG-6965](https://issues.apache.org/jira/browse/MNG-6965).
* Mojos are prevented to boostrap new instance of `RepositorySystem` (for example by using deprecated `ServiceLocator`), they should reuse `RepositorySystem` instance provided by Maven instead. See [MNG-7471](https://issues.apache.org/jira/browse/MNG-7471).
* Each line in `.mvn/maven.config` is now interpreted as a single argument. That is, if the file contains multiple arguments, these must now be placed on separate lines, see [MNG-7684](https://issues.apache.org/jira/browse/MNG-7684).
* System and user properties handling cleanup, see [MNG-7556](https://issues.apache.org/jira/browse/MNG-7556). As a consequence, this may introduce breakage in environments where the user properties were used to set system properties or other way around, for example see [MNG-7887](https://issues.apache.org/jira/projects/MNG/issues/MNG-7887).

## Known Issues

None.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12352958
[5]: ../../docs/history.html

