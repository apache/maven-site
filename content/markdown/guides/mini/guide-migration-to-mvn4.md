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

Maven 4 introduces several upgrades and improvements (see ["What's new in Maven 4?"](/whatsnewinmaven4.html)).
Some of them are backwards compatible or optional, but some require changes of your build environment or Maven project.
This page offers help for users who want to build their projects with Maven 4.

In short, the general suggestion for the migration is to do it in three steps:

1. Meet the prerequisites with latest Maven 3
2. Build also with Maven 4 and fix errors
3. Introduce optional Maven 4 features

If you run into any issues, please don't hesitate to contact the [users mailing list](/mailing-lists.html).
The Maven team is interested in all your migration experiences - either good or bad!

**Notes**:

* This page will constantly be updated  to contain changes, but also to integrate feedback of the community and their experience when upgrading to Maven 4.
  This means it will still receive updates after Maven 4 has been released.
* Maven 4.0.0-rc-4 provides a migration tool (see [MNG-8765](https://issues.apache.org/jira/projects/MNG/issues/MNG-8765)), doing most of the steps needed to migrate a project.
* This guide does not contain information about how to update plugins to use the new Maven 4 plugin API.
  We plan to create a separate guide for this.

## Table of content

<!--MACRO{toc|fromDepth=2|toDepth=4}-->

## Prerequisites

This guide assumes that your environment and project meets the following prerequisites.
If they don't, please make sure they do before continuing with this guide.

### Use latest version of Maven 3.9

This guide assumes that the project to be migrated was successfully built using the [latest version of Maven 3](/docs/history.html).

### Upgrade plugins to their latest Maven 3 version

Similar to the previous prerequisite, the guides assumes that you are using the latest Maven 3 plugin version of all your plugins.

Do not upgrade yet to a plugin version which requires Maven 4!
Be aware of this, if you use tools that can automate updates.

You can use the [versions-maven-plugin's `display-plugin-updates` goal][versionpluginupdate] to see which are the latest available versions of the plugins you use.

The following example shows the output of the Versions Maven Plugin in a project using the outdated version 3.12.0 of the Maven Compiler Plugin.
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

**Note**: Maven 3 can also be executed with Java 17.
So you should prepare your build environment as soon as possible to have a solid, project independent base for further migration.

### Install and use Maven 4

After you have upgraded your build environment to support Java 17, you also need to provide Maven 4.
See the information [where to download](/download.cgi) and [how to install](/install.html) Maven 4 for further information.

Places where you might have to configure Maven 4 usage aside installation:
* [Maven Wrapper](/tools/wrapper/index.html)'s `maven-wrapper.properties` file.
* [Required Maven version](/enforcer/enforcer-rules/requireMavenVersion.html) rule of the Maven enforcer plugin.
* Your own CI/CD configuration scripts or pipelines.

### Update plugins to Maven 4 version

If there is a dedicated Maven 4 version for used plugins, you should update to such a version.
You can use the [Versions Maven Plugin's `display-plugin-updates` goal][versionpluginupdate] to check for updates.

**Note**: While Maven 4.0.0 aims to support all Maven 3.9 compatible plugins, this can not be guaranteed for plugins which use outdated Maven 2 or 3 plugin API methods.

## Troubleshooting and required changes if situation applies

The following changes are in general not required by all projects, but might apply to yours.

### Fix plugin misconfigurations

There are several misconfigurations in the project's POM declaration that result in warnings when using Maven 3.9.
While your build should not throw any warnings at all, the following ones needs to be fixed as they will fail the build in Maven 4.

**Notes**:
* The [plugin configuration guide](/guides/mini/guide-configuring-plugins.html) contains general information, how plugins are correctly declared and configured.
* For analysing wrong build behavior, it might help to use the new `--fail-on-severity` parameter, paired with `WARN` as an argument, to fail your build when a warning occurs.

#### Duplicate plugin declaration

A plugin must only be declared once.
Defining it multiple times results the following log message.

> 'build.plugins.plugin.(groupId:artifactId)' must be unique but found duplicate declaration of plugin [plugin-name] ...

To fix this, remove the duplicated configuration.

### Replace removed directory properties

Maven 4 introduces new public properties dedicated to point to the root and top directory of (multi-subproject) project.
You can now use `${project.rootDirectory}`, `${session.topDirectory}`, `${session.rootDirectory}` when declaring paths.
See the [Maven Model Builder interpolation reference][modelbuilderinterpolation] for more details.

At the same time, several (especially internal) properties are deprecated or removed.
This includes the following properties:
* `executionRootDirectory`
* `multiModuleProjectDirectory`

### Replace conceptional `pre-` and `post-` lifecycle phases

In Maven 3, `integration-test` [lifecycle phase](/ref/3-LATEST/maven-core/lifecycles.html) had conceptional `pre-` and `post-` phases, but this was the only phase with these
In Maven 4 those phases are removed in favor of new `before:` and `after:` phases, available for [all lifecycle phases](/ref/4-LATEST/maven-impl-modules/maven-core/lifecycles.html).

Update your plugin executions that bind to a `pre-integration-test` or `post-integration-test` phase with the corresponding `before:integration-test` or `after:integration-test` phases.

## Optional changes and features

### Changed default values

#### Install/deploy at end of build (breaking change)

Both, the Maven Install Plugin and the Maven Deploy Plugin, have changed default values for their behavior, about when to install respectively deploy.
The default values for `installAtEnd` and `deployAtEnd` are set to `true` in Maven 4.
They now reflect the desired behavior for the majority of the Maven builds.
If you manually set these values to `true` before, you can remove the explicit declaration and inherit the same behavior from the new default values now.

If your build relies on the old default value (`false`), you have to add it to your plugin configuration now.
But in most cases, a build that requires the values to be `false` means that these settings hide a configuration error.
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
   From Maven's perspective, the directory can be empty, but some version control systems do not check in an empty directory.
   Therefore, placing an empty file inside (or a Maven [configuration files](https://maven.apache.org/configure.html)) works around this.
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

#### Automatic versioning in multi subprojects setups

Maven 4 contains a lot of improvements for projects which contain subprojects.
Those include automatic version detection of the parent and project own dependencies.

Using Maven 3 with model version 4.0.0 you need to declare those.
While the parent's version always must be hardcoded, you can use the `${project.version` property to declare another subproject as a dependency.
Using Maven 4 and model version 4.1.0 those definitions are optional.

The following example of a pom.xml shows the associated part for a subproject called "SubprojectB".
This subproject declares its parent and the dependency to "SubprojectA", which is also a subproject in the same multi subproject setup.

In Maven 3 with model version 4.0.0 such a declaration looks like the following.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
   
   <modelVersion>4.0.0</modelVersion>
   <artifactId>SubprojectB</artifactId>
   
   <parent>
      <groupId>demo.maven</groupId>
      <artifactId>TheParentProjecct</artifactId>
      <!-- The parents version must be "hardcoded" -->
      <version>0.0.1-SNAPSHOT</version>
   </parent>
   
   <dependencies>
      <dependency>
         <groupId>demo.maven</groupId>
         <artifactId>SubprojectA</artifactId>
         <!-- The subproject dependency version declaration can make use of the project.version property-->
         <version>${project.version}</version>
      </dependency>
   </dependencies>
   
   <!-- other declarations -->
</project>
```

In Maven 4 and model version 4.1.0 the version declaration is not needed anymore.

```xml
<project xmlns="http://maven.apache.org/POM/4.1.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.1.0 http://maven.apache.org/xsd/maven-4.1.0.xsd">
   
   <modelVersion>4.1.0</modelVersion>
   <artifactId>SubprojectB</artifactId>
   
   <parent>
      <groupId>demo.maven</groupId>
      <artifactId>TheParentProjecct</artifactId>
   </parent>
   
   <dependencies>
      <dependency>
         <groupId>demo.maven</groupId>
         <artifactId>SubprojectA</artifactId>
      </dependency>
   </dependencies>
   
   <!-- other declarations -->
</project>
```

#### CI-friendly variables without flatten-maven-plugin

Maven partially supports [CI-friendly variables][cifriendlyguide] (like `${revision}`) since version 3.5.0.
Thanks to the improved dependency resolution of project own dependencies in multi subprojects setups they are now fully supported if you rely on the automatic versioning as described in the section above.
This also means that you don't need the [flatten-maven-plugin][flattenmavenplugin] to `install` and `deploy` anymore.

#### Using BOM packaging

Maven 4 introduces a dedicated `bom` packaging type to provide a [Bill of Materials BOM POM][bompomguide].
This now differentiates between parent POMs and dependency-managing BOM POMs.
The new type is only available as a build POM in Model Version 4.1.0 and later, but Maven generates a full Maven 3
compatible consumer POM during the build.

The following code snippet shows an example for a BOM POM using the new `<packaging>` type.

```xml
<project xmlns="http://maven.apache.org/POM/4.1.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.1.0 http://maven.apache.org/xsd/maven-4.1.0.xsd">

    <modelVersion>4.1.0</modelVersion>
    <groupId>demo.maven</groupId>
    <artifactId>Maven4-example-bom</artifactId>
    <version>1.0.0</version>

    <packaging>bom</packaging>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>dev.some.group</groupId>
                <artifactId>someArtifact</artifactId>
                <version>1.2.3</version>
            </dependency>
            <dependency>
                <groupId>dev.some.other</groupId>
                <artifactId>somethingElse</artifactId>
                <version>3.1.5</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

### Use the new `all` and `each` life cycle phases

Maven 4 introduces several new [lifecycle phases](/ref/4-LATEST/maven-impl-modules/maven-core/lifecycles.html) — `all`, `each`, `before:all`, `after:all`, `before:each`, and `after:each`.
They give users finer control over plugin execution, particularly in multi-project and concurrent builds.
If you want to execute a plugin before/after all or each of your (sub-)projects, consider to use them.

[versionpluginupdate]: https://www.mojohaus.org/versions/versions-maven-plugin/examples/display-plugin-updates.html
[modelbuilderinterpolation]: https://maven.apache.org/ref/4-LATEST/maven-compat-modules/maven-model-builder/#model-interpolation
[cifriendlyguide]: https://maven.apache.org/guides/mini/guide-maven-ci-friendly.html
[flattenmavenplugin]: https://www.mojohaus.org/flatten-maven-plugin/
[bompomguide]: https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms

