# Plugin Validation

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

Maven since versions 3.9.x and 4.x introduced **Plugin Validation**
in order to help Maven users and Maven Plugin developers discover issues with the plugins they
use or maintain that may break in the future.

These issues are displayed as WARNING either when plugin goal is executed or at the end of the build:

```
[WARNING] Plugin validation issues were detected in x plugin(s)
```

and split in two categories based on what actions should be taken:

## Internal issues

Internal Plugins Validation issues (project local) are issues discovered in Maven project configuration, like:

- project using deprecated plugin goals ([MNG-7457](https://issues.apache.org/jira/browse/MNG-7457)),
- project using deprecated plugin parameters,
- project using read only plugin parameters ([MNG-7464](https://issues.apache.org/jira/browse/MNG-7464)).

In such cases, users can fix their project by fixing configuration by editing their POMs.
Users should consult actual plugin documentation (and eventually try to update plugin to newer version).

## External issues

External Plugins Validation issues (non-configuration) are issues detected in plugin itself, like:

- plugin using old, deprecated Maven API,
- plugin declaring dependencies for Maven Core artifacts in wrong scope (should be `provided`).

External Plugins issues require to be fixed by plugin authors first.

In such cases users can try to update plugin to newer version.
If the newest version of plugin still has such an issue, users should report problem to plugin authors.

## Exclude plugins from validation

In some case we know about issues in some plugin and we want exclude it from validation.
We can do it by property `maven.plugin.validation.excludes`, eg:

```
mvn -Dmaven.plugin.validation.excludes=plugin1-goupId:plugin1-artifactId:plugin1-version,plugin2-goupId:plugin2-artifactId:plugin2-version
```

Property `maven.plugin.validation` has tha same rule as `maven.plugin.validation` - can only be used on command line (not as property in POM)

## Manage Plugin Validation verbosity

In order to manage Plugin Validation verbosity, a Maven user property `-Dmaven.plugin.validation=...` can be used on command line (or injected: see below).

Allowed values are:

- `NONE` - mute Plugin Validation completely, nothing will be reported,
- `INLINE` (default) - report only `Internal` issues in place where they occur,
- `BRIEF` - report `Internal` issues in place where they occur and list of plugins with `External` issues at the end of the build,
- `SUMMARY` - report list of plugins with `Internal` and `External` issues at the end of the build,
- `VERBOSE` - report `Internal` and `External` issues at the end of build in verbose mode.

Configuration values for `maven.plugin.validation`  are case insensitive, can only be used on command line (not as property in POM), like:

```
mvn -Dmaven.plugin.validation=verbose ...
```

As a consequence:
- it can be added to `MAVEN_OPTS` or `MAVEN_ARGS` environment variables,
- it can also be added to `.mvn/maven.config` file in order to configure per project,
- it can also be added as property in `settings.xml` file to change configuration globally.

But it can not be used as a POM property in project `pom.xml`.

Please consult:
- [Configuring Apache Maven](/configure.html)
- [Settings reference](/settings.html)

