# Migrate to Maven 4

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
<!--MACRO{toc|fromDepth=2|toDepth=4}-->

## Summary

Maven 4 introduces several upgrades and improvements (see ["What's new in Maven 4?"](/whatsnewinmaven4.html)). 
Some of them are backwards compatible or optional, but some require changes of your build environment or Maven project.
This page offers help for the migration to Maven 4.

In short, the general suggestion for the migration is to do it in three steps:

1. Meet the prerequisites
2. Use Maven 4 for your build and fix build errors
3. Introduces optional Maven 4 features

If you run into any issues, please don't hesitate to contact the [users mailing list](/mailing-lists.html).

**Notes**:

* This page will constantly be updated at least until Maven 4.0.0 is released to contain changes but also to integrate feedback of the community and their experience when upgrading to Maven 4.
* It is planed to provide a migration tool (see [MNG-8649](https://issues.apache.org/jira/projects/MNG/issues/MNG-8649)), doing at least most of the steps needed to migrate a project. 
* It is planed to create a separate guide about migrating plugins to new Maven 4 plugin API.

## Prerequisites
This guide assumes that your environment and project meets the following prerequisites.
If they don't fulfill them already, please put effort into meeting them, before continuing with this guide.

### Use latest version of Maven 3.9
This guide assumes that the project to be migrated was successfully built using the [latest version of Maven 3](/docs/history.html).

### Upgrade plugins to their latest Maven 3 version
Similar to the previous prerequisite, the guides assumes that you are using the latest Maven 3 plugin version of all your plugins.

Do not upgrade to a plugin version, which requires Maven 4!
Be aware of this, if you use tools that can automate updates.

You can use the [Versions Maven Plugin's `display-plugin-updates` goal][versionpluginupdate], to see which are the latest available versions of the plugins you use.

The following example shows the output of the Versions Maven Plugin in a project using the outdated version 3.12.0 of the maven-compiler-plugin.
As you can see, it shows that with version 3.14.0 there is a Maven 3 compatible update, while the versions 4.0.0-beta-1 and 4.0.0-beta-2 require Maven 4 versions.

```
[INFO] The following plugin updates are available:
[INFO]   maven-compiler-plugin ............................ 3.12.0 -> 3.14.0
[INFO]
[INFO] All plugins have a version specified.
[INFO]
[INFO] Project requires minimum Maven version for build of: 3.9
[INFO] Plugins require minimum Maven version of: 3.6.3
[INFO]
[INFO] No plugins require a newer version of Maven than specified by the pom.
[INFO]
[INFO] Require Maven 4.0.0-beta-3 to use the following plugin updates:
[INFO]   maven-compiler-plugin ...................... 3.12.0 -> 4.0.0-beta-1
[INFO]
[INFO] Require Maven 4.0.0-rc-2 to use the following plugin updates:
[INFO]   maven-compiler-plugin ...................... 3.12.0 -> 4.0.0-beta-2
```

## Required changes
You need to do the following steps to be able to use Maven 4.

### Upgrade build environment to support Java 17

Maven 4 requires Java 17 to be run.
This means the build environment (locally but also your CI environment) needs to provide a JDK that supports Java 17.
Either install one to your machine or use a container providing one.

**Note**: Maven 3 can also use executed with Java 17.
So you should prepare your build environment as soon as possible to have a solid, project independent base for further migration.

### Install and use Maven 4
After you have upgraded your build environment to support Java 17, you also need to provide Maven 4.
See the information [where to download](/download.cgi) and [how to install Maven 4](/install.html) for further information.

Places where you might have to configure Maven 4 usage aside installation:
* [Maven Wrapper](/wrapper/index.html)'s `maven-wrapper.properties` file.
* [Required Maven version](/enforcer/enforcer-rules/requireMavenVersion.html) rule of the Maven enforcer plugin.
* Your own CI/CD configuration scripts or pipelines.

### Update plugins to Maven 4 version
If there is a dedicated Maven 4 version for used plugins, you should update to such a version.
You can use the [Versions Maven Plugin's `display-plugin-updates` goal][versionpluginupdate] to check for updates.

**Note**: While Maven 4.0.0 aims to support all Maven 3.9 compatible plugins, this can not be guaranteed for custom plugins which use outdated plugin API methods.

## Troubleshooting and required changes if situation applies 
The following changes are in general not required by all projects, but might apply to yours.

### Fix plugin misconfigurations
There are several misconfigurations in the project's POM declaration that result in warnings when using Maven 3.9.
While your build should not throw any warnings at all, the following ones needs to be fixed as they will fail the build in Maven 4.

**Notes**:
* The [plugin configuration guide](/guides/mini/guide-configuring-plugins.html) contains general information, how plugins are correctly declared and configured.
* For analysing wrong build behavior, it might help to use the new `--fail-on-severity` parameter, paired with `WARN` as an argument, to fail your build when a warning occur.  

#### Duplicate plugin declaration
A plugin must only be declared once.
Defining it multiple times, results the following log message.

> 'build.plugins.plugin.(groupId:artifactId)' must be unique but found duplicate declaration of plugin [plugin-name] ...

To fix this, remove the duplicated configuration.

### Replace removed directory properties
Maven 4 introduces new public properties dedicated to point to the root and top directory of (multi-subproject) project.
You can now use `${project.rootDirectory}`, `${session.topDirectory}`, `${session.rootDirectory}` when declaring paths.
See the [Maven Model Builder interpolation reference][modelbuilderinterpolation] for more details.

At the same time, several (especially internal) properties are deprecated or removed.
This includes the following properties:
* `executionRootDirectory`
* `multiModuleProjectDirector`

### Replace conceptional `pre-` and `post-` lifecycle phases
In Maven 3 some lifecycle phases also had conceptional `pre-` and `post-` phases, but they are not available for all phases.
In Maven 4 those phases are removed in favor of new `before:` and `after:` phases, available for all lifecycle phases.

### Check bindings to `all` phase 
The `all` phase got fixed and slightly changed in Maven 4.
It now behaves correctly, especially when using it in project with multiple subprojects or in concurrent scenarios.

The `before-all` phase is executed before all other phases of a project.
In a project with multiple subprojects, the parent's `before-all` is executed before any children's phases.
According to that, the `after-all` phase is executed at the end of the project's build.
In a project with multiple subprojects, the children's `after-all` is executed before parent's one.

If you bind plugins to the `all` phase, please check if it still matches your desired execution, or if you need to bind to the new `each` phase.
The `before-each` phase is executed everytime before any other phase in a build, while the `after-each` phase is executed everytime after any other.

## Optional changes and features

### Changed default values

#### Install / deploy at end of build
Both, the maven-install-plugin and the maven-deploy-plugin, have changed default values for their behavior, about when to install respectively deploy.
The default values for `installAtEnd` and `deployAtEnd` are set to `true` in Maven 4.
They now reflect the desired behavior for the majority of the Maven builds.
If you manually set these values to `true` before, you can remove the explicit declaration and inherit the same behavior from the new default values now.

If your build relies on the old default value (`false`), you have to add it to your plugin configuration now. But in most cases, a build that requires the values to be `false` means that these settings hide a configuration error.
We suggest to analyse the build configuration after migration.

### Use POM model version 4.1.0
Using POM model version 4.1.0 is not required with Maven 4.0.0.
It is only required, for using new POM features like `root`-attribute, BOM packaging types, optional profiles and others.

Model version 4.1.0 (or higher) will be required in future versions of Maven.
It's suggested to migrate your project to Maven 4 without any POM changes that require model version 4.1.0 first.
After you have successfully done this, include wanted features step by step.  


#### Define project's root directory
It is highly recommended to define the project's root directory.
While the definition is not enforced with Maven 4.0.0, it might be in the future.
Some new Maven 4 features, for example the `${project.rootDirectory}`property, do require it.

When the root directory is not defined, the following warning is displayed: 

> [WARNING] Unable to find the root directory. Create a .mvn directory in the root directory or add the root="true" attribute on the root project's model to identify it.

As written in the warning there are two ways to define the root directory:

1. Create a `.mvn` directory in the root directory.
   The directory can be empty, however you can consider to place [configuration files](https://maven.apache.org/configure.html) in it.
   Using the `.mvn` directory is already possible with Maven 3.
2. Set the `root` attribute in your parent POM to `true`.
   The `root` attribute is a new attribute of model version 4.1.0 and therefore can only be used with Maven 4.
   Note: That is also the cause, why this topic is listed in the model version 4.1.0 section.

#### Use `<subprojects>` instead of `<modules>`
In Maven 4 subprojects are no longer called modules.
While the `<modules>` element is still supported, it was marked as deprecated in model version 4.1.0 and will get removed in a future version. 
So you should replace it with the new `<subprojects>` element instead.

```xml
<!-- Requires model version 4.0.0 (Maven 3, deprecated in model version 4.1.0) -->
 <modules>
     <module>ModuleA</module>
     <module>ModuleB</module>
 </modules>

<!-- Requires model version 4.1.0 (Maven 4) -->
 <subprojects>
     <subproject>ModuleA</subproject>
     <subproject>ModuleB</subproject>
 </subprojects>
```

#### Automatic versioning

#### CI-friendly variables without flatten-plugin

#### Using BOM packaging




[versionpluginupdate]: https://www.mojohaus.org/versions/versions-maven-plugin/examples/display-plugin-updates.html
[modelbuilderinterpolation]: https://maven.apache.org/ref/4-LATEST/maven-compat-modules/maven-model-builder/#model-interpolation