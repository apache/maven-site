title: What's new in Maven 4?
author: Matthias Bünger

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

# What's new in Maven 4?

**Note**: This article will continuously be updated at least until Maven 4.0.0 is released.

Maven is over 20 years old, and is the most widely used build tool in the Java world.
Throughout the years, Maven has maintained backward compatibility, especially of its [POM file][2] with Model version
4.0.0.

The POM file fulfills two needs.
First, the POM holds all the information and configuration, which are needed to build the artifact.
After the artifact is created, this build information is not relevant anymore.
Second, the POM also contains information, for example dependencies, which are needed by projects which depend on the
artifact.
These dependent projects are called the "consumers".

This made Maven more than a tool; it became a whole ecosystem with many dependencies on the POM, especially the
Maven Central repository, other build tools, and IDEs.
Thus, any change in the POM's schema forces each participant of the ecosystem to either adopt the change or drop
support.
Thus, the Maven POM syntax became fixed, unable to change.

> "With the Maven build schema preserved in amber, we can’t evolve much: we’ll stay forever with Maven 3 minor releases, unable to implement improvements that we imagine will require seriously updating the POM schema…"
>
> &mdash; <cite>[Hervé Boutemy (in Javaadvent 2021)][1]</cite>

In order for Maven to evolve, it's necessary to separate the information needed for the build from the information
needed by the consumers, but without breaking the ecosystem.
Maven 4 prepares for this and more.

This article presents and explains major changes brought by Maven 4, grouped into several topics.

<!-- MACRO{toc|section=1|fromDepth=2} -->

## Required Java version

Maven 4 requires Java 17.
This allows Maven (and its maintainers) to make use of newer language features and improvements.

**Important note**: Java 17 is only needed to **run Maven**!
You can still compile against older Java versions using the same [compiler plugin configuration][6] as before.
If you need to compile against or use another JDK, see the [Guide to Using Toolchains][7] (or the
article [Introduction to Maven Toolchains][8] by Maven maintainer Maarten Mulders).

## POM Changes

### Consumer POM

Maven 4 can generate a stripped down consumer POM that removes build information not needed by consumers, and deploys this
to the remote repository.
It does not deploy the `pom.xml` used to build the project.

The consumer POM is a **flattened** version of the build POM, meaning:

* It contains no parent POM references (all inherited elements are resolved and included directly)
* BOM imports are flattened into the dependency list
* Only transitive dependencies are kept (compile and runtime scopes)
* Managed dependencies are kept only if they are actually used by the project

This flattening ensures that consumers of your artifact have all the information they need without requiring access to
parent POMs or understanding the internal structure of your multi-project build.

The flatten feature is disabled by default to avoid unexpected behavior and better control of listing (transitive) dependencies in the consumer POM.
To publish a flattened consumer POM instead of the full build POM, the user property `maven.consumer.pom.flatten` must be set to `true`.
User properties can be controlled using `${session.rootDirectory}/.mvn/maven-user.properties` for a per-reactor configuration.

### Model version 4.1.0

Maven 4 updates the POM version to 4.1.0 which defines the namespace `http://maven.apache.org/POM/4.1.0`.
Version 4.1.0 adds some new elements and attributes, while others are marked as deprecated.
To not break the ecosystem, this version is only available for the build POM, while the consumer POM will still use
version 4.0.0.
Maven generates the consumer POM during the build from the build POM.

**Note**: Maven 4 will continue to build your model version 4.0.0 project.
There is no need to update your POMs to 4.1.0 if you don't need to use the new features.

### Modules are now subprojects

A Maven project is any Java project built with Maven.
The [top level directory][32] of a Maven project contains at least the source folder `src` and the project's POM file
`pom.xml`.
A Maven project may have other Maven projects within subdirectories, each with its own `pom.xml` file.
Each project within a subdirectory is called a "subproject".

Example: Project A contains a subdirectory which contains its own POM file for project B.
We say that B is a subproject of A.

However, Maven does not build subprojects unless they are linked from the root POM file.
In the past this was done by listing each subproject in the `<modules>` section of the parent's POM.
Projects which have at least one subproject are called "multi-module projects", while those which do not have any
subprojects are called "single module projects".
Since the introduction of the [Java Platform Module System][3] in Java 9, the term "module" has raised additional
confusion inside the Maven community.

Maven 4 gets rid of this by renaming modules to projects and submodules to subprojects.
Model version 4.1.0 contains a new `<subprojects>` element analogous to the now deprecated, but still usable,
`<modules>` element.

**Note**: Use the terms `multi-project setup` and `single-project setup` to differentiate between a Maven project with
or without subprojects.

### New artifact types

Maven 4 introduces new types of artifacts.
The types listed below can be used in `<dependency>` declarations of JAR files.
The `jar` type existed in Maven 3 and is still the default in Maven 4.
The other types are new:

* `jar`: for a JAR file that can be placed either on the class path or on the module path.
* `classpath-jar`: for a JAR file that is unconditionally placed on the class path.
* `modular-jar`: for a JAR file that is unconditionally placed on the module path.
* `processor`: for a JAR file that can be placed either on the annotation processor class path or module path.
* `classpath-processor`: for a JAR file to unconditionally place on the annotation processor class path.
* `modular-processor`: for a JAR file to unconditionally place on the annotation processor module path.

The `jar` and `processor` types use heuristic rules for deciding where to place the JAR file, but the result is not always what the developer wants.
The types with `classpath-` or `modular-` prefix give control to the developer.

**Limitation:** As of October 2025, only Maven Compiler Plugin (version 4.0.0-beta-3 and newer) complies with the new types.
Other plugins will be updated progressively.

### New packaging type: bom

Maven 4 introduces a dedicated `bom` packaging type to provide a [Bill of Materials BOM][4].
This now differentiates between parent POMs and dependency-managing BOMs.
The new type is only available as a build POM in Model Version 4.1.0 and later, but Maven generates a full Maven 3
compatible consumer POM during the build.
For an example, see the link above or
the [live coding by Maven maintainer Karl Heinz Marbaise at IntelliJ IDEA Conf 2024][5].

**Note**: With Maven 4, it's also possible to exclude dependencies that are declared by BOMs using the existing
`<exclusions>` element.
Also note that in Maven 4, importing BOMs with a classifier is now possible.
Therefore, the Maven team suggests that project BOMs should be generated as classified artifacts, using the
`<bomClassifier>` element.
This means that an imported BOM must **not** come from the same reactor as the current build but be available outside
the project before the build.
In other words: you should only import external BOMs.
That's why Maven 4.0 will show a warning if a BOM comes from the same reactor.
In the future, this will most probably be changed to make the build fail.

### Comparing build POM and consumer POM

The following table shows a rough comparison of which content is available in which POM type when using Maven 4.

**Notes**:

* The column "consumer POM" does not apply to artifacts that are of type "pom", because "pom"-artifacts are designed to
  contain build information, for example plugin configuration!
* Some of the build-related content which is (as of now) still available in the consumer POM might be available only in
  the build POM in the future.

| Content                                    | Build POM | Consumer POM |
|:-------------------------------------------|:---------:|:------------:|
| Model version                              |   4.1.0   |    4.0.0     |
| 3rd party dependency information           |     ✅     |      ✅       |
| POM properties                             |     ✅     |      ❌       |
| Plugin configuration                       |     ✅     |      ❌       |
| Repository information                     |     ✅     |      ✅       |
| Project information / environment settings |     ✅     |      ✅       |
| Deployment to remote repository            |     ✅     |      ✅       |

**Warning**: There are rare situations where Maven 4 will produce a consumer POM based on version 4.1.0, for example
when
condition-based profiles (see below) can't be transformed to version 4.0.0.
Maven will show a warning in such situations.

### Declaring the root directory and directory properties

Every time Maven executes a build, it has to determine the project's root to identify things like the parent project,
directory information, and so on.
To "help" Maven find the root folder, you can create a `.mvn` folder in your root directory.
This folder is intended to contain project-specific configuration to run Maven, for example a `maven.config` or
`jvm.config`
file, and therefore was also considered as the root folder.
With Maven 4, there is a second option to clearly define the root folder.
Model version 4.1.0, usable for the build POM, adds a boolean attribute called `root` in the `<project>` element.
When this attribute is set to true (default is false), the directory of this POM file is considered the root directory.

Another pain point in relation to the root directory is that until Maven 4, there was no official property to make use
of the root folder in your POM files, for example when you want to define the path to a `checkstyle-suppressions.xml`
file for the checkstyle plugin.
Maven 4 now provides official properties to reference the root directory in your POM configuration.
The following table shows the official properties.

| Property                   |  Scope  | Definition                                                              | Always |
|:---------------------------|:-------:|:------------------------------------------------------------------------|:------:|
| `${project.rootDirectory}` | Project | `.mvn` folder or `root` attribute in pom                                |   No   |
| `${session.topDirectory}`  | Session | Current directory or `--file` argument                                  |  Yes   |
| `${session.rootDirectory}` | Session | `.mvn` folder or `root` attribute in pom for the `topDirectory` project |   No   |

As you can see, these properties differentiate by their scope, where `project` is always related to the Maven project's
definition (you could interpret this as the POM files) and `session` is the actual execution of a Maven build and is the
current working directory.
The `rootDirectory` can only contain a value when either a `.mvn` directory is defined or the `root` attribute is set to
true.
However, if defined, it should always have the same value for a given project, whereas the value of the `topDirectory`
property can change depending on the execution point.

Keep in mind that the root directory of the whole project (when considering multiple subprojects) is different from each
subproject's own base directory, which is accessible via the `${basedir}` property for use in POM
files and will always have a value.

**Note:** In the past, some people "hacked" workarounds for the `rootDirectory` properties, mostly by using internal
properties.
Starting with Maven 4 those properties were removed or marked as deprecated.
See JIRA issue [MNG-7038][15] and the related [Pull Request for MNG-7038][16] for more information.

### New way to declare source directories

Maven 3 has two explicitly named XML elements (`<sourceDirectory>` and `<testSourceDirectory>`) to declare the root directories of source code, as shown below:

```xml
<project>
  <build>
    <sourceDirectory>my-custom-dir/foo</sourceDirectory>
    <testSourceDirectory>my-custom-dir/bar</testSourceDirectory>
  </build>
</project>
```

Maven 4 introduces the new `<sources>` element for this.
The `<source>` element can be repeated, thus allowing multiple source directories without the need to resort to external plugins.
It also provides a unified way to declare include/exclude filters, makes easier to set up projects targeting multi Java releases, and enables module source hierarchy.
The documentation of the Maven Compiler Plugin gives some examples.

```xml
<project>
  <build>
    <sources>
      <source>
        <scope>main</scope>
        <directory>my-custom-dir/foo</directory>
      </source>
      <source>
        <scope>test</scope>
        <directory>my-custom-dir/bar</directory>
      </source>
    </sources>
  </build>
</project>
```

### Alternate POM syntaxes

While the syntax for the 4.0.0 consumer POM is set in stone, the build POM should be able to evolve.
This includes allowing the use of alternate syntaxes by having Maven 4 provide a ModelParser SPI ([MNG-7836][24]),
which can be implemented as a core extension and allow a custom syntax.

One of the first projects that uses this feature is the [Apache Maven Hocon Extension][25].

## Improvements for subprojects

### Automatic versioning (POM inference)

Maven 4 finally ships one of the oldest improvement requests - automatic parent versioning ([MNG-624][17], created in
July 2005 and originally planned for Maven 2)!
As expected, it's no longer required to define the parent versions in each subproject when using the new model version
4.1.0.
This also extends to dependencies of project's own subprojects and reduces the need to update POM files for new
versions even more.

#### Parent inference

When using model version 4.1.0, you can omit the version, groupId, and artifactId from the `<parent>` element by using
a relative path. Maven will automatically infer these values by looking for a `pom.xml` file in the specified directory.

You can use either of these forms:

* `<parent><relativePath>..</relativePath></parent>` - explicitly specify the relative path to the parent directory
* `<parent/>` - shorthand form that defaults to looking in the parent directory (`..`)

This makes it easier to maintain multi-project builds without duplicating version and coordinate information.

#### Subprojects discovery

Maven 4 can automatically discover subprojects in subdirectories without requiring them to be explicitly listed in the
`<subprojects>` (or deprecated `<modules>`) section. This reduces boilerplate in your POM files and makes it easier to
add new subprojects to your build.

Subprojects are automatically discovered when:

* The parent POM has `pom` packaging
* No `<subprojects>` or `<modules>` section is explicitly defined
* Direct subdirectories contain a `pom.xml` file

When these conditions are met, Maven will automatically add all subdirectories with a `pom.xml` file to the list of
subprojects to build.

#### Example

The following code snippet shows the parent and dependency definition without the version tag.

```xml

<project xmlns="http://maven.apache.org/POM/4.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.1.0 http://maven.apache.org/xsd/maven-4.1.0.xsd">
  <modelVersion>4.1.0</modelVersion>

  <parent>
    <groupId>my.parents.groupId</groupId>
    <artifactId>my.parents.artifactId</artifactId>
  </parent>

  <artifactId>myOwnSubprojectArtifactId</artifactId>

  <dependencies>
    <dependency>
      <groupId>the.dependent.subproject.groupId</groupId>
      <artifactId>the.dependent.subproject.artifactId</artifactId>
    </dependency>
  </dependencies>
</project>
```

### Full support of CI-friendly variables

Maven 3.5.0 introduced partial support for CI-friendly variables, like `${revision}`, in your POM files.
However, this still required the usage of the [Flatten Maven Plugin][20] for full functionality.
Since Maven 4, no additional plugin is needed; full built-in support is provided.
You can now use **any variable** as a version in your configuration, not just predefined ones.

Example

```xml

<groupId>my.groupId</groupId>
<artifactId>my.artifactId</artifactId>
<version>${revision}</version>
```

Variables must be defined at build time using one of the following methods:

* Maven properties defined on the command line, for example `mvn verify -Drevision=4.0.1`
* Maven properties defined in a `maven.config` file in the `.mvn` directory
* Maven properties defined in the root POM or parent POM

This flexibility allows you to use any variable name you prefer, making it easier to integrate Maven with your CI/CD
pipelines and build automation systems.

Maven maintainer Karl Heinz Marbaise shows a larger example in
his [article "Maven 4 - Part I - Easier Versions" (2024)][21].

### Reactor improvements and fixes

Building a project with multiple subprojects can cause confusion in Maven 3.
When the build of subproject B, which requires subproject A, is failing for whatever reason, Maven 3 is telling
the user to fix the error and then resume the build with `--resume-from :<nameOfTheFailingSubproject>`.
But doing this instantly fails the build again, because the required subproject A couldn't be found (as it was not
rebuilt).
Using `--also-make :<nameOfTheDependentSubproject>` was no help in the past as it was ignored due to the long-standing
bug [MNG-6863][11] - which is finally fixed with Maven 4!

**Recommendation: Do not use `mvn clean install` for your regular builds.
Instead, use `mvn verify`!**

To improve usability when resuming a failed build, you can now use `--resume` or its short parameter `-r` to resume a
build from the subproject that last failed.
So you don't have to manually pass the name of the failed subproject as the starting point to resume from.
The reactor is now also aware of successfully built subprojects when the overall build failed and will skip rebuilding
those if you resume the build.  
With Maven 4, it's also aware of subfolder builds [MNG-6118][12], which becomes pretty handy when you only want to
execute tools (for example Jetty) on/with certain subprojects, but not on every subproject.
See Maven maintainer Maarten Mulders's article ["What's New in Maven 4" (2020)][13] for a small example.

### Further improvements

Further improvements to subprojects will also improve daily work with them.
Thanks to [MNG-6754][14], all subprojects will now have consistent timestamps in their packaged archives, while in Maven
3, each subproject had a different one.
This should make it easier to identify the archives that belong together.
When using Maven 3, deploying a project with multiple subprojects could end up in a situation where successfully
built subprojects were deployed to the (local or remote) repository, but failed subprojects were not.
This was finally changed in Maven 4 to what most users expect:
Only deploy when all subprojects are built successfully.
This means that the default value of the `deployAtEnd` parameter is now `true`.

## Workflow, lifecycle and runtime changes

### Application maintenance

As with every major update, extensive application maintenance has occurred, including significant code, API, and
dependency updates.
For example, the "Plexus Containers" dependency injection was removed - after being deprecated since Maven 3.2 (2010)!
Code updates include not only newer Java language features but also changes to make maintenance easier and
less time-consuming.
This also includes removing features that either should never have worked or were only kept for backward compatibility
in Maven 3, for example using `${pom.*}` expressions.
Maven's own Super POM was also upgraded, which declares new default versions of Maven's core plugins.

**Note**: Due to upgrading the default versions of Maven plugins, your build might behave differently than before, even
if you didn't purposely change anything.
To avoid this situation, always define fixed versions of all the plugins you use.
By doing this, you are in control of your build.
Maven 4 will issue a warning if you rely on default versions defined in Maven's Super POM.

### "Fail on severity" parameter

Maven 4 introduces a "fail on severity" build parameter, which breaks the build when the severity of at least one log
message matches the given argument.

The parameter can either be used by its full name (`--fail-on-severity`) or as a short handle (`-fos`).
The parameter is followed by an argument specifying a log level severity, for example, `WARN`.

### Improvements to profiles

Trying to use a nonexistent profile in a build causes the build to fail, as the following command line snippet shows:

```
> mvn compile -Pnonexistent
[ERROR] The requested profiles [nonexistent] could not be activated or deactivated because they do not exist.
```

Maven 4 introduces the possibility to only use profiles when they exist.
To do so, the `?` argument was added to the profile parameter.
When using this, the build won't break.
Instead, an information message will be printed twice (at the start and end).
See the following snippet for an example:

```
> mvn compile -P?nonexistent
[INFO] The requested optional profiles [nonexistent] could not be activated or deactivated because they do not exist.
[...]
[INFO] BUILD SUCCESS
[INFO] ----------------------------------------------------------------------------------------------------------------
[INFO] Total time:  0.746 s
[INFO] Finished at: 2024-12-14T13:24:15+01:00
[INFO] ----------------------------------------------------------------------------------------------------------------
[INFO] The requested optional profiles [nonexistent] could not be activated or deactivated because they do not exist.
```

#### Condition-based profile activation

Maven 4 introduces more flexible ways to activate profiles by providing condition-based activation through the new
`<condition>` element in the `<activation>` section. This allows you to use expressions to determine when a profile
should be activated, providing much more control than the traditional property-based or OS-based activation.

For example, you can now activate profiles based on complex conditions like:

* Checking if a file exists or doesn't exist
* Comparing property values
* Combining multiple conditions with logical operators

See [MNG-8286][27] for more information about supported functions and the complete syntax for condition-based activation.

### Lifecycle changes

#### Lifecycle changed from graph to tree

In Maven 3 the lifecycle is an ordered list containing all phases.
This changed with Maven 4, where the lifecycle is defined as a tree of phases.
This allows for more fine-grained execution of dependent phases.
For example, the `compile` phase must execute after `compile-only` project dependencies have reached the `ready` phase.
Maven 4 also allows to skip phases (in comparison to the old graph).
For example, it's possible to `deploy` an artifact without `install`ing it to the user repository.

For compatibility, the execution behavior is not really changed by default compared to Maven 3.
In order to actually leverage the new tree-based lifecycle, the user has to enable the concurrent builder using
`-b concurrent`.
Running with the concurrent builder will allow finer grained dependency instead of the usual Maven 3 per-project
dependency.
With the usual builders and run `mvn verify`, a project will run up to the verify phase, before a dependent project can
start being built, whereas the concurrent builder will allow a dependant project to be built as soon as the dependencies
are at the `ready` phase.

#### Before- and after-phases, ordering of executions

Every lifecycle phase now has a `before:` and an `after:` phase, allowing plugins to bind themselves to those.
For example, if you want to set up test data before running your integration tests, you could execute tasks during the
`before:integration-test` phase.

If this is not enough, perhaps because you want to do multiple things inside the same phase, you can order each
execution inside a phase by using square brackets with an integer at the end of the phase name.

Example

```
before:integration-test[100]
before:integration-test[200]
```

**Warning**: The conceptual `pre-*` and `post-*` phases, which were only available for selected phases and had inconsistent naming, are deprecated.
They only act as aliases now and might be removed in the future.
Don't use them!
This is especially important when binding a plugin to a `post-*` phase of a lifecycle phase because the corresponding `pre-*` phase of the desired phase doesn't exist — for example, binding to the `process-resources` phase due to the absence of a `pre-compile` phase.

#### Behavioral change: phase execution scope

Maven 4 introduces a behavioral change in how phases are executed.
For a given major phase, Maven 4 will always execute both (`before:` and `after:`) minor phases of it.
This differs from Maven 3 where the `post-*` (now `after:`) phase is only executed if either the next major phase of the lifecycle is executed or the `post-*` phase is explicitly called.

The following example shows that this change might affect existing builds.

Given the following plugin configuration, where a plugin is bound to the `post-clean` phase.

```xml
<plugin>
  <groupId>org.example</groupId>
  <artifactId>some-plugin</artifactId>
  <executions>
    <execution>
      <!-- In Maven 3: Does NOT run when executing 'mvn clean', only with `mvn post-clean` -->
      <!-- In Maven 4: DOES run when executing 'mvn clean' -->
      <phase>post-clean</phase>
      <goals>
        <goal>cleanup</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

Running `mvn clean` using Maven 3 would NOT execute the plugin, because the build execution stops at the main phase, not executing it `post-` phase.
To execute the plugin in Maven 3, the user has to explicitly run `mvn post-clean` as a build goal.
In Maven 4 running `mvn clean` will also execute the plugin, as both `before:` and `after:` phases are always executed and the `post-` phase is now an alias for `after:`.


#### All- and each-phases

Maven 4 introduces several new lifecycle phases — `all`, `each`, `before:all`, `after:all`, `before:each`, and `after:each` — that give users finer control over plugin execution, particularly in multi-project and concurrent builds.

Both the `all` and `each` phases are executed for every project and subproject, but their scope differs:
* The `each` phase wraps the standard lifecycle phases (`validate`, `compile`, `test`, etc.) of a single project or subproject.
It's used to define behavior that should occur around the build of an individual subproject.
* The `all` phase encompasses the entire build of a project, including its `each` phase and the `all` phases of its child subprojects.
It is ideal for logic that should run once per project, while still covering the entire subproject hierarchy.

To make this model more intuitive — especially for users familiar with testing frameworks — Maven 4 also introduces the following hook phases:
* `before:all` runs before any other phase in the current project.
In a multi-project build, the parent project's `before:all` is executed before any phases of its subprojects.
* `after:all` is executed at the very end of a project’s build.
In a multi-project build, the `after:all` phases of the subprojects run before that of the parent.
* `before:each` is executed before every standard phase in a project’s lifecycle, while `after:each` runs after `each` phase.
These are especially useful for applying consistent setup and teardown logic around every phase.

Together, these new phases provide a powerful and intuitive structure for defining and customizing build behavior in complex Maven projects.

## Maven plugins, security, and tools

### Maven plugins

As mentioned above, Maven 4 contains significant code and API updates, resulting in breaking changes for (very) old
Maven plugins that have not been updated to use the recommended APIs.
Major changes regarding plugins include a proper immutable plugin model together with a revised plugin API.
The updated API provides hints as preparation for Maven 4.
You can enable them by passing the following argument to your build: `-Dmaven.plugin.validation=verbose`.
You should also only rely on the official Maven BOMs when developing plugins.
If a plugin still relies on long-deprecated and now removed Plexus dependency injection, it will no longer work.
It needs to be updated to use JSR-330 - see [Maven & JSR-330][26] for further information.

**Advice**: If you are maintaining a Maven plugin, you should test it with Maven 3.9.x.
Pay close attention to upcoming warnings and update the plugin accordingly.

### Improved encryption

Security is important, and storing unencrypted passwords is bad practice.
Maven 3's password encryption had several serious issues and could more accurately be called "password obfuscation".
Maven 4 instead has a completely redone encryption system, based on Maven Encryption (`mvnenc`) - a standalone CLI tool.
As of now, it provides functionally equivalent operations as Maven 3 (see ["Maven: Password Encryption"][30]) but adds
improvements like a `decrypt` functionality and supports external vaults. The documentation of the new encryption can be found in ["Password Encryption (Maven 4)"][33].
A broad overview of the problems in Maven 3 and the solution in Maven 4 can be found in the
article ["Handling sensitive data in Maven"][31] by Maven maintainer Tamás Cservenák.

### Maven Resolver

The [Maven Artifact Resolver][28] is a library for working with artifact repositories and dependency resolution.
Maven 4 includes the new 2.0 release of this library, which contains more than 150 fixes, upgrades, and
improvements, for example, a Java native HTTP-Client - thanks to raising the Java version requirement to JDK 17!
Another major difference compared to Maven 3 is that in the new major version, the resolver is hidden behind the new
Maven API and is no longer used directly by plugins.

### Maven Shell

Each time you run an `mvn` command, the entire process chain is executed: booting Java, starting Maven, loading the
configuration, performing the task, tearing down, and exiting — **every single time**.
To improve performance and reduce build times, you can use the [Maven Daemon][29] (`mvnd`), which manages a pool of
resident Maven processes.
With Maven 4, you can also take advantage of the newly defined "[Maven Shell](./tools/mvnsh.html)" (`mvnsh`), which keeps a single Maven
process running for as long as the shell remains open.

### Maven Upgrade Tool

To upgrade from Maven 3 to Maven 4, you can use the [Maven Upgrade Tool](https://maven.apache.org/tools/mvnup.html).
This tool helps automate the migration process by analyzing your project and making necessary adjustments to ensure
compatibility with Maven 4.

The Maven Upgrade Tool can be used to:

* Update your POM files to use the new model version 4.1.0 (if desired)
* Identify deprecated features and suggest replacements
* Migrate from deprecated plugin configurations to their modern equivalents
* Validate that your project is ready for Maven 4

Refer to [Maven Upgrade Tool](https://maven.apache.org/tools/mvnup.html) for more information on usage and the upgrade process in general.

## Issue overview

The Maven issue tracker provides a [full list of all resolved issues of Maven 4.0.0][22].

[1]: https://www.javaadvent.com/2021/12/from-maven-3-to-maven-5.html
[2]: https://maven.apache.org/pom.html
[3]: https://en.wikipedia.org/wiki/Java_Platform_Module_System
[4]: https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms
[5]: https://www.youtube.com/watch?v=ZD_YxTmQ16Q&t=16710s
[6]: https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-release.html
[7]: https://maven.apache.org/guides/mini/guide-using-toolchains.html
[8]: https://maarten.mulders.it/2021/03/introduction-to-maven-toolchains/
[9]: https://issues.apache.org/jira/projects/MNG/issues/MNG-8061
[10]: https://maven.apache.org/docs/history.html
[11]: https://issues.apache.org/jira/browse/MNG-6863
[12]: https://issues.apache.org/jira/browse/MNG-6118
[13]: https://maarten.mulders.it/2020/11/whats-new-in-maven-4/
[14]: https://issues.apache.org/jira/browse/MNG-6754
[15]: https://issues.apache.org/jira/browse/MNG-7038
[16]: https://github.com/apache/maven/pull/1061
[17]: https://issues.apache.org/jira/browse/MNG-624
[18]: https://issues.apache.org/jira/browse/MNG-6656
[19]: https://issues.apache.org/jira/browse/MNG-7051
[20]: https://www.mojohaus.org/flatten-maven-plugin/
[21]: https://blog.soebes.io/posts/2024/03/2024-03-31-maven-4-part-i/
[22]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12346477
[23]: https://issues.apache.org/jira/browse/MNG-7879
[24]: https://issues.apache.org/jira/browse/MNG-7836
[25]: https://github.com/apache/maven-hocon-extension
[26]: https://maven.apache.org/maven-jsr330.html
[27]: https://issues.apache.org/jira/browse/MNG-8286
[28]: https://maven.apache.org/resolver/
[29]: https://github.com/apache/maven-mvnd
[30]: https://maven.apache.org/guides/mini/guide-encryption.html
[31]: https://cstamas.org/blog/2024/09/handling-sensitive-data-in-maven/
[32]: https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
[33]: https://maven.apache.org/guides/mini/guide-encryption-4.html

