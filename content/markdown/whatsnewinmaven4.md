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

<head>
   <title>What's new in Maven 4?</title>
   <meta name="author" content="Matthias Bünger" />
</head>

# What's new in Maven 4?

Maven is over 20 years old, and is one of the most used build tools in the Java world.
Throughout the years, one important rule has been maintaining the highest backward compatibility possible, especially of
its [POM file][2] with Model version 4.0.0.

The POM file fulfills two elementary needs.
First, the POM holds all the information and configuration, which are only needed to build the artifact.
After the artifact is created, this build information is not relevant anymore.
Second, the POM also contains information, e.g. dependencies, which are needed by projects which want to use the
artifact.
These dependent projects are called the "consumers" (of an artifact).

This made the Maven more than a tool; it became a whole ecosystem with many dependencies on the POM, especially the
Maven Central repository, other build tools, and IDEs.
This results in the situation that any change in the POM's schema forces each participant of the ecosystem to either
adopt the change or drop support.
Thus, the Maven POM syntax became fixed, unable to change.

> "With the Maven build schema preserved in amber, we can’t evolve much: we’ll stay forever with Maven 3 minor releases,
>
>> unable to implement improvements that we imagine will require seriously updating the POM schema…"
>> &mdash; <cite>[Hervé Boutemy (in Javaadvent 2021)][1]</cite>

But Maven should be able to advance.
For this, one important thing that's needed is to separate the information needed for the build from the information
needed by the consumers, but without breaking the ecosystem.
Maven 4 prepares for this and more.

This article presents and explains major changes brought by Maven 4, grouped into several topics.

## POM Changes

### Build-POM and Consumer-POM

Maven 3 uses Model version 4.0.0 not only by the build but also by consumers of the artifact.
However, several parts of the POM are only necessary for the build while others, like the dependencies, are also
needed by the consumers.
Maven 4 therefore differentiates between a "Build-POM" and a "Consumer-POM".
As the names suggest, the "Build-POM" contains all information needed to build the artifact, e.g., applied plugins
and their configuration, while the "Consumer-POM", which is created during the Maven build, only contains what is
necessary to use an artifact as a dependency.
This POM will only keep what is really needed to use the artifact, e.g., dependency information.

**Note**: See below for a comparison of the content of both POMs.

### Model version 4.1.0

Maven 4 introduces a new POM version 4.1.0.
With two types of POM, Maven 4 can make additions to the Build-POM as it is only be used by Maven.
Version 4.1.0 adds some new elements and attributes, while others are marked as deprecated.
To not break the ecosystem, this version is only available for the Build-POM, while the Consumer-POM will still use
version 4.0.0.
Maven generates the Consumer-POM during the build from the build POM.

**Note**: Maven 4 will continue to build your model version 4.0.0 project.
There is no need to update your POMs to 4.1.0 as long as you don't want to make use of the new features.

### Modules are now subprojects

A Maven project is any Java project build with Maven.
The [directory][33] of a Maven project contains at least the source folder, the test source folder and the project's POM
file.
A Maven project may have other Maven projects within subdirectories.
Each project within a subdirectories is called "subproject".

Example: A project A contains a subdirectory which contains its own POM file for project B.
We say that B is a subproject of A.

However, Maven does not build subprojects as long as they are not linked as such in the POM file.
In the past this was done by listing each subproject in the `<modules>` section of the parent's POM.
The inconsistent wording led to the situation that many people calling subprojects "modules".
Projects which have at least one subproject are often called "multi-module project", while those which don't have any
are "single-module project".
Since the introduction of the [Java Platform Module System][3] in Java 9, the term "module" has raised additional
confusion inside the Maven community.

Maven 4 gets rid of this by renaming modules to projects and submodules to subprojects.
Model version 4.1.0 contains a new `<subprojects>` element analogous to the now deprecated, but still usable,
`<modules>` element.

**Note**: Use the terms `multi-project setup` and `single-project setup` to differentiate between a Maven project with
or without subprojects.

### New packaging type: bom

Maven 4 introduces a dedicated packaging type to provide a [Bill of Materials BOM][4] called "bom" to differentiate more
precisely between "parent POMs" and dependency-managing BOMs.
The new type is only available as a Build-POM in Model Version 4.1.0 and later, but Maven generates a full Maven 3
compatible Consumer-POM during the build.
For an example, see the link above or
the [live coding by Maven maintainer Karl Heinz Marbaise at IntelliJ IDEA Conf 2024][5].

**Note**: With Maven 4, it's also possible to exclude dependencies that are declared by BOMs using the existing
`<exclusions>` element.
Also note that in Maven 4, importing BOMs with a classifier is now possible.
Therefore, the Maven team suggests that project BOMs should be generated as classified artifacts, using the
`<bomClassifier>` element.
This means that an imported BOM must **not** come from the same reactor as the current build but be available outside
the project before the build (in other words: you should import only external BOMs).
That's why Maven 4.0 will show a warning if a BOM comes from the same reactor.
In the future, this will most probably be changed to make the build fail.

### Comparing Build-POM and Consumer-POM

The following table shows a rough comparison of which content is available in which POM type when using Maven 4.

**Notes**:

* The column "Consumer-POM" does not apply to artifacts that are of type "pom", because "pom"-artifacts are designed to
  contain build information, e.g. plugin configuration!
* Some of the build-related content which is (as of now) still available in the Consumer-POM might be available only in
  the Build-POM in the future.

| Content                                    | Build-POM | Consumer-POM |
|:-------------------------------------------|:---------:|:------------:|
| Model version                              |   4.1.0   |    4.0.0     |
| 3rd party dependency information           |     ✅     |      ✅       |
| POM properties                             |     ✅     |      ❌       |
| Plugin configuration                       |     ✅     |      ❌       |
| Repository information                     |     ✅     |      ✅       |
| Project information / environment settings |     ✅     |      ✅       |
| Deployment to remote repository            |     ✅     |      ✅       |

**Warning**: There are rare situations where Maven 4 will produce a Consumer-POM based on version 4.1.0, e.g., when
condition-based profiles (see below) can't be transformed to version 4.0.0.
Maven will show a warning in such situations.

### Declaring the root directory and directory properties

Every time Maven executes a build, it has to determine the project's root to identify things like the parent project,
directory information, and so on.
To "help" Maven find the root folder, you can create a `.mvn` folder in your root directory.
This folder is intended to contain project-specific configuration to run Maven, e.g., a `maven.config` or `jvm.config`
file, and therefore was also considered as the root folder.
With Maven 4, there is a second option to clearly define the root folder.
Model version 4.1.0, usable for the Build-POM, adds a boolean attribute called `root` in the `<project>` element.
When this attribute is set to true (default is false), the directory of this POM file is considered the root directory.

Another pain point in relation to the root directory is that until Maven 4, there was no official property to make use
of the root folder in your POM files, e.g., when you want to define the path to a `checkstyle-suppressions.xml` file for
the checkstyle plugin.
Maven 4 now provides official properties to reference the root directory in your POM configuration.
The following table shows the official properties.

| Property                   |  Scope  | Definition                                                              | Always |
|:---------------------------|:-------:|:------------------------------------------------------------------------|:------:|
| `${project.rootDirectory}` | Project | `.mvn` folder or `root` attribute in pom                                |   No   |
| `${session.topDirectory}`  | Session | Current directory or `--file` argument                                  |  Yes   |
| `${session.rootDirectory}` | Session | `.mvn` folder or `root` attribute in pom for the `topDirectory` project |   No   |

As you can see, these properties differentiate by their scope, where `project` is always related to the Maven project's
definition (you could interpret this as the POM files) and `session` is the actual execution of a Maven build and is the
directory from where you start Maven.
As a consequence of the definition, it's clear that the `rootDirectory` can only contain a value when either a `.mvn`
directory is defined or the `root` attribute is set to true.
However, if defined, it should always have the same value for a given project, whereas the value of the `topDirectory`
property can change depending on the execution point.

Keep in mind that the root directory of the whole project (when considering multiple subprojects) is different from each
subproject's own base directory, which is still accessible via the `${basedir}` property for use in POM
files and will always have a value.

**Note:** In the past, some people "hacked" workarounds for the `rootDirectory` properties, mostly by using internal
properties.
Starting with Maven 4, those "hacks" will most probably not work anymore because some of those properties were removed
or at least marked as deprecated.
See JIRA issue [MNG-7038][15] and the related [Pull Request for MNG-7038][16] for more information.

### Alternate POM syntaxes

While the syntax for the 4.0.0 Consumer-POM is set in stone, the Build-POM should be able to evolve.
This includes allowing the use of alternate syntaxes by having Maven 4 provide a ModelParser SPI ([MNG-7836][24]),
which can be implemented as a core extension and allow a custom syntax.

One of the first projects that uses this feature is the [Apache Maven Hocon Extension][25].

## Improvements for subprojects

### Automatic versioning

Maven 4 finally ships one of the oldest improvement requests - automatic parent versioning ([MNG-624][17], created in
July 2005 and originally planned for Maven 2)!
As expected, it's no longer required to define the parent versions in each subproject when using the new model version
4.1.0.
This also extends to dependencies of project's own subprojects and reduces the need to update POM files for new
versions even more.

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

Maven 3.5.0 introduced partial support for CI-friendly variables, e.g., `${revision}`, in your POM files.
However, this still required the usage of the [Flatten Maven Plugin][20] for full functionality.
Since Maven 4, no additional plugin is needed; full built-in support is provided.
You can now use variables as versions in your configuration, e.g.,

```xml

<groupId>my.groupId</groupId>
<artifactId>my.artifactId</artifactId>
<version>${revision}</version>
```

You have to provide a value for this variable when starting the build, for example by using a `maven.config` file.
In CI pipelines it's commonly done using a parameter, e.g., `mvn verify -Drevision=4.0.1`.

Maven maintainer Karl Heinz Marbaise shows a larger example in
his [article "Maven 4 - Part I - Easier Versions" (2024)][21].

### Reactor improvements and fixes

Building a project with multiple subprojects could cause trouble when one subproject depended on one of the
others and its own build failed for whatever reason.
Maven was telling the user to fix the error and then resume the build with
`--resume-from :<nameOfTheFailingSubproject>`, which instantly fails the build again as the needed other subproject
couldn't be found (as it was not rebuilt).
Using `--also-make :<nameOfTheDependentSubproject>` was no help in the past as it was ignored due to the long-standing
bug [MNG-6863][11] - which is finally fixed with Maven 4!

**Recommendation: Do not use `mvn clean install`.
Instead, use `mvn verify` for your regular builds!**

To improve usability when resuming a failed build, you can now use `--resume` or its short parameter `-r` to resume a
build from the subproject that last failed.
So you don't have to manually pass the name of the failed subproject as the starting point to resume from.
The reactor is now also aware of successfully built subprojects when the overall build failed and will skip rebuilding
those if you resume the build.  
With Maven 4, it's also aware of subfolder builds [MNG-6118][12], which becomes pretty handy when you only want to
execute tools (e.g., Jetty) on/with certain subprojects, but not on every subproject.
See Maven maintainer Maarten Mulders's article ["What's New in Maven 4" (2020)][13] for a small example.

### Further improvements

Further improvements to subprojects will also improve daily work with them.
Thanks to [MNG-6754][14], all subprojects will now have consistent timestamps in their packaged archives, while in Maven
3, each subproject had a different one.
This should make it easier to identify the archives that belong together.
When using Maven 3, deploying a project with multiple subprojects could end up in a situation where some (successfully
built) subprojects were deployed to the (local or remote) repository, but failed subprojects were obviously not.
This was finally changed in Maven 4 to the way most users expect:
Only deploy when all subprojects are built successfully.

## Workflow, lifecycle and runtime changes

### Java 17 required to run Maven

The required Java version to run Maven 4 will be Java 17.
This allows Maven (and its maintainers) to make use of newer language features and improvements brought by the
JDK.

**Important note**: Java 17 will only be needed to **run Maven**!
You will still be able to compile against older Java versions using the same [compiler plugin configuration][6] as
before.
If this does not fit your requirements because you need to compile against or use another JDK, see
the [Guide to Using Toolchains][7] (or the article [Introduction to Maven Toolchains][8] by Maven maintainer Maarten
Mulders).

### Application maintenance

As with every major update, extensive application maintenance has occurred, including significant code, API, and
dependency updates.
For example, the "Plexus Containers" dependency injection was removed - after being deprecated since Maven 3.2 (2010)!
Code updates include not only newer Java language features but also changes to make maintenance easier and
less time-consuming.
This also includes removing features that either should never have worked or were only kept for backward compatibility
already in Maven 3, e.g., using `${pom.*}` expressions.
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
The parameter is followed by an argument specifying a log level severity, e.g., `WARN`.

### Improvements to profiles

Trying to use a nonexistent profile in a build causes the build to fail, as the following command line snippet shows:

```
> mvn compile -Pnonexistent
[ERROR] The requested profiles [nonexistent] could not be activated or deactivated because they do not exist.
```

Maven 4 introduces the possibility to only use profiles when they exist.
To do so, the `?` argument was added to the profile parameter.
When using this, the build won't break; instead, an information message will be printed twice (at the start and end).
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

Maven 4 also introduces more flexible ways to activate profiles by providing condition-based activation.
See [MNG-8286][27] for more information about supported functions.

### Lifecycle changes

#### Lifecycle changed from graph to tree

Up to Maven 3, the lifecycle was an ordered list containing all phases.
This changed with Maven 4, where the lifecycle is defined as a tree of phases.
This allows for more consistent execution of dependent phases, e.g., `compile` must execute after `compile-only` project
dependencies have reached the `ready` phase, and also allows "skipping" phases (in comparison to the old graph), e.g.,
`deploy` an artifact without `install`ing it to the user repository.

#### Pre- and post-phases, ordering of executions

Every lifecycle phase now has a `before` and `after` phase, allowing plugins to bind themselves to those by adding their
prefixes to the name of the main phase.
For example, if you want to set up test data before running your integration tests, you could execute tasks during the
`before-integration-test` phase.

If this is not enough, perhaps because you want to do multiple things inside the same phase, you can order each
execution inside a phase by using square brackets with an integer at the end of the phase name, e.g.,

```
before-integration-test[100]
before-integration-test[200]
```

**Warning**: The conceptual `pre-*` and `post-*` phases, which were only available for selected phases and had
inconsistent naming, are deprecated - don't use them!
This becomes even more important if you were binding a plugin to the `post-*` phase of a lifecycle phase because the
`pre-*` phase of the phase you really wanted to bind to did not exist, e.g., binding to `process-resources` phase
because there was no `pre-compile` phase.

## Maven plugins, security and tools

### Maven plugins

As mentioned above, Maven 4 contains significant code and API updates, resulting in breaking changes for (very) old
Maven plugins that have not been updated to use the recommended APIs.
Major changes regarding plugins include a proper immutable plugin model together with a revised plugin API.
The updated API provides hints as preparation for Maven 4.
You can enable them by passing the following argument to your build: `-Dmaven.plugin.validation=verbose`.
You should also only rely on the official Maven BOMs when developing plugins.
If a plugin still relies on long-deprecated and now removed Plexus dependency injection, it will no longer work.
It needs to be updated to use JSR-330 - see [Maven & JSR-330][26] for further information.

**Advice**: If you are maintaining a Maven plugin, you should test it with Maven 3.9.x, pay close attention to upcoming
warnings, and update the plugin accordingly.

### Improved encryption

Security is important, and storing unencrypted passwords is bad practice.
Maven 3's password encryption had several serious issues and could more accurately be called "password obfuscation".
Maven 4 instead has a completely redone encryption system, based on Maven Encryption (`mvnenc`) - a standalone CLI tool.
As of now, it provides functionally equivalent operations as Maven 3 (see ["Maven: Password Encryption"][30]) but adds
improvements like a `decrypt` functionality.
A broad overview of the problems in Maven 3 and the solution in Maven 4 can be found in the
article ["Handling sensitive data in Maven"][31] by Maven maintainer Tamás Cservenák.

### Maven Resolver

The [Maven Artifact Resolver][28] is a library for working with artifact repositories and dependency resolution.
Maven 4 will include the new 2.0 release of this library, which contains more than 150 fixes, upgrades, and
improvements, e.g., a Java native HTTP-Client - thanks to raising the Java version requirement to JDK 17!
Another major difference compared to Maven 3 is that in the new major version, the resolver is hidden behind the new
Maven API and is no longer used directly by plugins.

### Maven Shell

Each time you run an `mvn` command, the entire process chain is executed: booting Java, starting Maven, loading the
configuration, performing the task, tearing down, and exiting — **every single time**.
To improve performance and reduce build times, you can use the [Maven Daemon][29] (`mvnd`), which manages a pool of
resident Maven processes.
With Maven 4, you can also take advantage of the newly defined "Maven Shell" (`mvnsh`), which keeps a single Maven
process running for as long as the shell remains open.

## Issue overview

The Maven issue tracker provides a [full list of all resolved issues of Maven 4.0.0][22].
As of 2024-12-14, not all issues are properly linked to the final release and therefore may not be shown in that list.
If you want to see issues resolved in each individual (alpha/beta/RC) release, please see
the [Maven releases history][10], starting with the alpha versions for Maven 4.0.0.

<!--- Links -->
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
[32]: https://issues.apache.org/jira/browse/MNG-8-->
[33]: https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.h-->
