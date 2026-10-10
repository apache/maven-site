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

The main driver for new generation was to align Maven 3 and 4 majors versions on same Resolver version. Hence, Maven 3.10 is now using Resolver 2.0 lineage, same one that is used by Maven 4+. On the other hand, at "Maven level", changes are kept minimal, and upgrading should be pretty straightforward. Also, 3.10 received many security and other enhancements as well. Still, most of the changes happened "under the surface", end users might even be unaware of them. The key changes are listed below.

Maven 3.10.0 requires Java 8 or above to run, same as the Maven 3.9.x lineage.

## Notable Changes in Maven 3.10.0

### New Features and Improvements

- [Maven Resolver](/resolver/) 2.x (2.0.24) is used, the same lineage as in Maven 4+, which brings version range filtering,
  improved remote repository filtering, a transitive dependency manager and user-defined relocations
- support for user-wide extensions (in `~/.m2/extensions.xml`) and installation-wide extensions (in `$MAVEN_HOME/conf/extensions.xml`) extensions,
  in addition to the usual project-specific `.mvn/extensions.xml`
- full `settings.xml` interpolation, including non-string values such as ports
- `session.topDirectory`, `session.rootDirectory` and `project.rootDirectory` are promoted to regular Maven properties,
  usable in interpolation and in profile activation
- [Reproducible Builds](/guides/mini/guide-reproducible-builds.html) are active by default: the Super POM now provides
  a default value for `project.build.outputTimestamp`
- new CLI options for fine-grained update policy control: `-UA` (force artifact updates only), `-UM` (force metadata
  updates only), `--artifacts-update-policy=<policy>`, `--metadata-update-policy=<policy>`
- a `<server>` in `settings.xml` can declare `<aliases>`, so that one set of credentials serves several server ids
  ([#7757](https://github.com/apache/maven/issues/7757))
- a `<server>` in `settings.xml` can declare `<repositoryOrigins>`, to state explicitly which repository origins its
  credentials may be used with
- the reactor summary is complete again: every module is listed, including the skipped ones, and failures are reported
  last at error level, so they are easier to spot
- a toolchain requested by a plugin, but misconfigured, now fails the build instead of silently falling back to the JDK
  running Maven
- `MAVEN_PROJECTBASEDIR` can be used in `.mvn/jvm.config`
  ([#13115](https://github.com/apache/maven/pull/13115))
- `bin/mvn` recognizes MSYS2 and converts paths for MinGW/MSYS2 as it did for Cygwin only so far
- the `site` lifecycle can be dropped from the default lifecycles with the `maven.site.lifecycle.disabled`
  system property ([#11970](https://github.com/apache/maven/pull/11970))
- the time zone is part of the Maven startup banner
- `maven.resolver.validation` provides an escape hatch for the new Resolver validation: `default` (full validation),
  `mild` (only uninterpolated placeholders) or `off` (no validation, as in Maven 3.9.x)

### Security Hardening

- credentials of a `settings.xml` server are scoped to the origin (scheme, host and port) of the repositories and
  mirrors declared with the same server id, and the server id is matched exactly, so a server entry no longer leaks
  credentials to unrelated repositories sharing an id prefix
- a model resolved from a repository (dependency, parent or imported BOM) contributes less to the build: restricted
  interpolation, profile activation limited to platform-derived criteria, recessive repository merging and rejection of
  `system` scoped dependencies. The `maven.model.dependencyInterpolation.full` property is available as an opt-out
- repositories supplied by the request or the session keep precedence over the ones declared by a resolved model
- artifact and metadata coordinates, relocation coordinates and legacy metadata file name tokens are validated before
  being used

### New API and Updates for Maven Plugins

- upgrade `SLF4J` to 2.x
- migration from JAnsi to JLine, with the `MessageBuilderFactory` service promoted for colored message support
(as a [`maven-shared-utils` styled message API](/shared/maven-shared-utils/) replacement)
- promote java version in [`JavaToolchain`](/ref/current/maven-core/apidocs/org/apache/maven/toolchain/java/JavaToolchain.html)
- model problems collected during project building are retained in `MavenSession.getModelProblems()`, so that plugins
  can inspect them and reject a build with model warnings
  ([#8485](https://github.com/apache/maven/issues/8485))
- Maven JARs carry an `Automatic-Module-Name` manifest entry
- the core is wired with JSR-330 annotations

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-3.10.0).

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
with that server id. A repository declared in a profile that is activated through `<activation>` or `-P` contributes no
origin, so its credentials have to be declared explicitly:

```xml
<server>
  <id>internal</id>
  <username>u</username>
  <password>p</password>
  <repositoryOrigins>
    <repositoryOrigin>https://repo.example.org</repositoryOrigin>
    <repositoryOrigin>https://mirror.example.org:8443</repositoryOrigin>
  </repositoryOrigins>
</server>
```

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
