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

Maven since versions 3.9.x and 4.x introduced `Plugin Validation` 
in order to help Maven users and Maven Plugin developers maintain theirs projects.

## Internal Plugins Validation issues

Internal Plugins Validation issues (project local) are issues discovered in Maven project configuration, like:
 
 - using deprecated plugins goals
 - using deprecated plugins parameters
 - using read only plugins parameters

In such cases users can fix their project by fixing configuration by editing their POMs.
Users should consult actual plugin documentation or try to update plugin to newer version.

## External Plugins Validation issues

External Plugins Validation issues (non-configuration) are issues detected in plugin itself, like:

 - using old, deprecated Maven Api by plugin
 - declaring dependencies for Maven Core artifacts in wrong scope in plugin project

External Plugins issues can only be fix by plugin authors.

In such cases users can try to update plugin to newer version. 
If the newest version of plugin still has an issue users should report problem to plugin authors.

## Manage Plugin Validation verbosity

In order to manage Plugin Validation verbosity a property `maven.plugin.validation` can be used.

Allowed values are:

 - `NONE` - mute Plugin Validation completely, nothing will be reported
 - `INLINE` - report only `Internal` issues in place where occur 
 - `BRIEF` - report only `Internal` issues in place where occur and list of plugins with `External` issues at the and of build 
 - `SUMMARY` - report list of plugins with `Internal` and `External` issues at the end of build
 - `VERBOSE` - report `Internal` and `External` issues at the end of build in verbose mode

Configuration values for `maven.plugin.validation`  are case insensitive, can be used on command line, like:

```
mvn -Dmaven.plugin.validation=verbose ...
```

Can be added to `MAVEN_OPTS` or `MAVEN_ARGS` environment variables, 
can also be added to `.mvn/maven.config` file in order to configure per project.

Users can add such property to their `settings.xml` file to change configuration globally.

*NOTICE* as `maven.plugin.validation` is configuration item for Maven itself, it can not be used in project pom.xml

Please consult: 
 - [Configuring Apache Maven](/configure.html)
 - [Settings reference](/settings.html)


