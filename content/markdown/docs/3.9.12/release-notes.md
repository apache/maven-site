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

# Maven 3.9.12 Release Notes

The Apache Maven team would like to announce the release of Maven 3.9.12.

Maven 3.9.12 is [available for download][0].

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [Plugin list][1] for more information.

If you have any questions, please consult:

- the website: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.9.12/](/ref/3.9.12/)

## Overview About the Changes

Regression fixes and other improvements from Maven 3.9.11. All users already on Maven 3.9.x are advised to upgrade.

The majority of fixed issues are bug and regression fixes for user reported problems.

### Notable Changes in Maven 3.9.12

- Remove usage of terminally deprecated methods in Guice to eliminate warnings in JDK 25+
- Simplify plugin prefix resolution — avoid unnecessary lookups when the prefix is already mapped to a groupId:artifactId in the metadata
- Verify the Java version requirements for plugins before execution and provide a clear error message if the requirement is not met

This release updates Resolver to version [1.9.25](https://github.com/apache/maven-resolver/releases/tag/maven-resolver-1.9.25).

### Notable Changes in Resolver 1.9.25

- Locking behavior has been refined by increasing lock-name selectivity. This enhances concurrency in parallel builds, 
  although it may result in a higher number of distinct locks being created
- Lock timeouts have been significantly increased—from 30 seconds to 15 minutes, to improve stability in complex or highly parallel build scenarios

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-3.9.12).

## Potentially Breaking Core Changes (if migrating from 3.8.x)

* The Maven Resolver transport has changed from Wagon to "native HTTP", see [Resolver Transport guide](/guides/mini/guide-resolver-transport.html).
* Maven 2.x was auto-injecting an ancient version of `plexus-utils` dependency into the plugin classpath, and Maven 3.x continued doing this to preserve backward compatibility. Starting with Maven 3.9, it does not happen anymore. This change may lead to plugin breakage. The fix for affected plugin maintainers is to explicitly declare a dependency on `plexus-utils`. The workaround for affected plugin users is to add this dependency to plugin dependencies until issue is fixed by the affected plugin maintainer. See [MNG-6965](https://issues.apache.org/jira/browse/MNG-6965).
* Mojos are prevented to bootstrap new instance of `RepositorySystem` (for example by using deprecated `ServiceLocator`), they should reuse `RepositorySystem` instance provided by Maven instead. See [MNG-7471](https://issues.apache.org/jira/browse/MNG-7471).
* Each line in `.mvn/maven.config` is now interpreted as a single argument. That is, if the file contains multiple arguments, these must now be placed on separate lines, see [MNG-7684](https://issues.apache.org/jira/browse/MNG-7684).
* System and user properties handling cleanup, see [MNG-7556](https://issues.apache.org/jira/browse/MNG-7556). As a consequence, this may introduce breakage in environments where the user properties were used to set system properties or other way around, for example see [MNG-7887](https://issues.apache.org/jira/projects/MNG/issues/MNG-7887).
* Plugins and extensions used by your build are checked against Maven supported APIs and conventions: this "plugin validation" may report WARNINGs at the end of your build. See [plugin validation documentation](../../guides/plugins/validation/) to better understand what to do when your build suffers from such warnings.

## Complete Release Notes

See [complete release notes for all versions][3]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[3]: ../../docs/history.html

