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

# Maven 3.10.1 Release Notes

The Apache Maven team would like to announce the release of Maven 3.10.1.

Maven 3.10.1 is [available for download][0].

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [Plugin list][1] for more information.

If you have any questions, please consult:

- the website: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.10.1/](/ref/3.10.1/)

## Overview About the Changes

The main driver for new generation was to align Maven 3 and 4 majors versions on same Resolver version. Hence, Maven 3.10 is now using Resolver 2.0 lineage, same one that is used by Maven 4+. On the other hand, at "Maven level", changes are kept minimal, and upgrading should be pretty straightforward. Also, 3.10 received many security and other enhancements as well. Still, most of the changes happened "under the surface", end users might even be unaware of them. The key changes are listed below.

Maven 3.10.1 requires Java 8 or above to run, same as the Maven 3.9.x lineage.

## Notable Changes in Maven 3.10.1

Maven 3.10.1 is a bugfix release for 3.10.0. It fixes regressions reported after 3.10.0, backports fixes from Maven 4, and makes startup lighter.

### New Features and Improvements

Release 3.10.1 focused on fixing all the regressions, but there are still some features being added:

- **Optional profiles with `-P ?id`.** Same syntax as Maven 4, so a single command line works for both.
- **Maven 4 `MessageBuilderFactory` for plugins.** Plugins built against `maven-api-core` 4.x can inject `org.apache.maven.api.services.MessageBuilderFactory` on 3.10 and get colored output, the same as on Maven 4.
- **JLine is gone, colors remain.** Maven no longer ships the JLine terminal stack (native libraries, a background setup thread, System.out/err replacement). Colored output is now plain ANSI escape codes, and `maven.style.*` overrides still apply. Startup overhead added in 3.10.0 is gone. JAnsi compatibility is kept through a shim.

### Regression fixes

There are way many to list them here, check out full changelog below.

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-3.10.1).

## Known issues

No known issues.

## Potentially Breaking Core Changes (if migrating from 3.9.x)

### Stricter POM validation

Maven 3.10.0 raises the POM validation level: many WARNINGs from the 3.9 lineage (in fact, from the 3.1 lineage) will
now FAIL the build. Things like duplicated dependency entries or duplicated plugin entries will not be tolerated
anymore. This aspect was not changed since Maven 3.1 lineage, and was really time to force users to clean up their
POMs. The checks promoted to errors for the project being built are the 3.1 level ones: duplicated plugins, invalid
characters in a version, malformed snapshot versions and reserved or malformed repository ids. Dependencies keep being
validated at the minimal level (and failure lead to transitive dependencies not being available). 
`relativePath` of a parent is validated as well, which no Maven 3.x version did so far.
To temporarily disable the stricter validation add `-Dmaven.resolver.validation=off` to your Maven incovation.

### Classpath ordering change

Dependency classpath ordering changed from "pre-order" (depth-first) to "level-order" (breadth-first),
aligning with Maven 4 behavior and Resolver 2.x defaults.

To restore the previous behavior, add `-Daether.system.dependencyVisitor=preOrder` to your Maven invocation.

### Changes in the Super POM

- removed deprecated `release-profile`; each project should have its own release profile
- removed plugin management; each project should have its own plugin management
- set default values for `project.build.sourceEncoding` and `project.reporting.outputEncoding` to `UTF-8`
- set a default value for `project.build.outputTimestamp`, which makes builds reproducible by default

### Artifact and metadata validation in Resolver

Resolver 2.x validates artifact and metadata coordinates, and rejects values that cannot be safely mapped to a
repository layout. A build relying on non-conformant coordinates can be unblocked with `maven.resolver.validation`
set to `mild` or `off`, but the coordinates should be fixed instead.

### Repository credentials are scoped

Credentials of a `settings.xml` server are only offered to repositories and mirrors whose origin Maven could associate
with that server id. Origins Maven cannot securely associate via id can be declared with `<repositoryOrigins>`, see
[Server Repository Origins](/settings.html#Server_Repository_Origins).

### Plugin validation

Plugins and extensions used by your build are checked against Maven supported APIs and conventions: this "plugin
validation" may report WARNINGs at the end of your build. See [plugin validation documentation](../../guides/plugins/validation/)
to better understand what to do when your build suffers from such warnings.

### Reproducible Builds

Reproducible Builds is active by default, and with a fixed timestamp value for archive entries:
if you rely on timestamp value for your consumption, updating timestamp value during release or even
disabling Reproducible Builds may be a choice: see [FAQ](/guides/mini/guide-reproducible-builds.html).

## Complete Release Notes

See [complete release notes for all versions][3]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[3]: ../../docs/history.html
