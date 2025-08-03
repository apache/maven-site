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

# Release Notes &#x2013; Maven 3.9.0

The Apache Maven team would like to announce the release of Maven 3.9.0.

Maven 3.9.0 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the website: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.9.0/](/ref/3.9.0/)

## Overview About the Changes

* Minimum Java version to use with Maven 3.9.0 is raised to Java 8.
* With Java 8, upgrade of several key dependencies became possible as well.
* Several backports from Maven 4.x line.
* Long outstanding issue fixes from Maven 3.x line.
* Cutting ties with Maven 2 backward compatibility, preparing grounds for Maven 4.
* General fixes and improvements.

### Potentially Breaking Core Changes (if migrating from 3.8.x)

* The Maven Resolver transport has changed from Wagon to "native HTTP", see [Resolver Transport guide](/guides/mini/guide-resolver-transport.html).
* Maven 2.x was auto-injecting an ancient version of `plexus-utils` dependency into the plugin classpath, and Maven 3.x continued doing this to preserve backward compatibility. Starting with Maven 3.9, it does not happen anymore. This change may lead to plugin breakage. The fix for affected plugin maintainers is to explicitly declare a dependency on `plexus-utils`. The workaround for affected plugin users is to add this dependency to plugin dependencies until issue is fixed by the affected plugin maintainer. See [MNG-6965](https://issues.apache.org/jira/browse/MNG-6965).
* Mojos are prevented to boostrap new instance of `RepositorySystem` (for example by using deprecated `ServiceLocator`), they should reuse `RepositorySystem` instance provided by Maven instead. See [MNG-7471](https://issues.apache.org/jira/browse/MNG-7471).
* Each line in `.mvn/maven.config` is now interpreted as a single argument. That is, if the file contains multiple arguments, these must now be placed on separate lines, see [MNG-7684](https://issues.apache.org/jira/browse/MNG-7684).
* System and user properties handling cleanup, see [MNG-7556](https://issues.apache.org/jira/browse/MNG-7556). As a consequence, this may introduce breakage in environments where the user properties were used to set system properties or other way around, for example see [MNG-7887](https://issues.apache.org/jira/projects/MNG/issues/MNG-7887).

### Notable Core Improvements

* Help with projects maintenance: Maven now warns about use of deprecated plugins, goals, parameters, etc.
* Add support for "mvn pluginPrefix:version:goal" invocation, and align console logging as well (make it copy-paste-able).
* Add profile activation by packaging.
* Maven 3.9.0 is now fully compatible with new 3.x line of install and deploy plugins (previous versions warns about this).

### Notable Resolver 1.9.x Improvements

* Shared local repository (advisory file locking, Hazelcast or Redis, see [documentation](https://maven.apache.org/resolver/local-repository.html#Shared_Access_to_Local_Repository)).
* Split local repository, plus "workspace" support for branched development (see [documentation](https://maven.apache.org/resolver/local-repository.html#Split_Local_Repository)).
* Switchable and alternative resolver transports included, with default switched to native transport.
* Pluggable checksum algorithms API (is not tied to MessageDigest anymore, see [documentation](https://maven.apache.org/resolver/about-checksums.html)).
* Choice of resolver collectors: a new BF collector (with parallel POM downloads) has been added along the existing DF one.
* Remote repository filtering (see [documentation](https://maven.apache.org/resolver/remote-repository-filtering.html)).
* Trusted checksum sources (ability to provide some or all artifact checksums ahead of time).
* Pluggable artifact resolver post-processor, with "trustedChecksums" implementation.
* Chained local repository (for IT isolation between "outer" and "inner" builds).
* Recording reverse dependency tree tracking information into local repository.

The full list of changes can be found in our [issue management system][4].

## Known Issues

* Observed roughly 10% slow-down on large builds when compared to Maven 3.8.7, tracked on [MNG-7677](https://issues.apache.org/jira/browse/MNG-7677).

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12350913
[5]: ../../docs/history.html

