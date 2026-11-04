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

# Configuring for Reproducible Builds

## What are Reproducible Builds?

[Reproducible builds](https://reproducible-builds.org/) are a set of software development practices that create an independently-verifiable path from source to binary code. A build is **reproducible** if given the same source code, build environment and build instructions, any party can recreate **bit-by-bit** identical copies of all specified artifacts.

[Reproducible Central](https://github.com/jvm-repo-rebuild/reproducible-central) lists projects releases that have been checked as reproducible by rebuilding independently from the reference build published in Central Repository.

## How do I configure my Maven build?

There is no Maven version prerequisite. Everything happens at plugin level:

1. Upgrade your plugins to reproducible versions: to easily detect [required upgrades](/plugins/maven-artifact-plugin/plugin-issues.html), run

   ```
   mvn artifact:check-buildplan
   ```
2. Enable Reproducible Builds mode for plugins, by adding [`project.build.outputTimestamp` property](https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=74682318#Reproducible/VerifiableBuilds-OutputArchiveEntriesTimestamp) to the project's `pom.xml`:

   ```xml
   <properties>
     <project.build.outputTimestamp>2023-01-01T00:00:00Z</project.build.outputTimestamp>
   </properties>
   ```

Notice: starting with Maven 3.10.0 and 4.0.0-beta-5, Reproducible Builds mode is active by default (see [MNG-8258](https://issues.apache.org/jira/browse/MNG-8258)), without modifying project's `pom.xml`.
Setting a value in your `pom.xml` remains useful if you want to override the inherited static default value.
See FAQ for opt-out.

You have the basics configured. The output should be mostly reproducible now.

## How to test my Maven build reproducibility?

Using [`maven-artifact-plugin`'s `compare` goal](/plugins/maven-artifact-plugin/compare-mojo.html), you can easily check that the second build of your project produce the same output than an initial build:

1. build and `install` your project (don't hesitate to customize arguments to better match your project):

   ```
   mvn clean install 
   ```
2. rebuild (`verify` only, without installing) and check against the previous install:

   ```
   mvn clean verify artifact:compare
   ```

Notice that this local test does NOT really prove that your build is yet reproducible by a third party, because it may still suffer from environment leaks (username, current directory, ...). But it is easy to do, and prevents basic non-reproducible issues like timestamps. Really checking reproducibility requires to rebuild from a completely different setup: this is harder to do, even if containers may ease the task.

### Staging

If the first build is done with \`deploy\` to a private remote repository, the rebuild and its verification can be done by someone else referencing the staging location, like for example at Apache during votes on staged releases candidates:

```
mvn verify artifact:compare -Dreference.repo=https://repository.apache.org/content/repositories/staging/
```

## How to fix my Maven build reproducibility?

If something is still not reproducible after initial setup and [automatic check from `artifact:check-buildplan`](/plugins/maven-artifact-plugin/plugin-issues.html):

1. Use [diffoscope](https://diffoscope.org/) to find the unstable output between builds. The `artifact:buildinfo` goal proposes a command with path to files: just copy/paste to launch.
2. Find the plugin that generated this output.
3. Check if a reproducible version of the plugin is available. If not, please open an issue to help plugin maintainers improving Reproducible Builds support at every plugin level.

### Reading the artifact:compare output

If `artifact:compare` reports that the current build output is not the same as the reference, the log shows where to look. This example rebuilds Doxia 1.9.1 against the release published on Central:

```
$ mvn -Papache-release clean verify -Dgpg.skip artifact:compare
[INFO] Scanning for projects...
...
[INFO] --- maven-artifact-plugin:3.7.0:buildinfo (default-cli) @ doxia-module-markdown ---
[INFO] Saved aggregate info on build to /tmp/doxia-1.9.1/doxia-modules/doxia-module-markdown/target/doxia-module-markdown-1.9.1.buildinfo
[INFO] Checking against reference build from central...
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/doxia/doxia-module-markdown/1.9.1/doxia-module-markdown-1.9.1.buildinfo
[WARNING] Reference buildinfo file not found: it will be generated from downloaded reference artifacts
[INFO] Reference build java.version: 1.8 (from MANIFEST.MF Build-Jdk-Spec)
[INFO] Reference build java.version: 1.8 (from MANIFEST.MF Build-Jdk-Spec)
[ERROR] Current build java.version: 1.7 (from MANIFEST.MF Build-Jdk-Spec)
[INFO] Reference build os.name: Unix (from pom.properties newline)
[INFO] Minimal buildinfo generated from downloaded artifacts: /tmp/doxia-1.9.1/target/reference/doxia-module-markdown-1.9.1.buildinfo
[WARNING] size mismatch doxia-logging-api-1.9.1.jar: investigate with diffoscope target/reference/doxia-logging-api-1.9.1.jar doxia-logging-api/target/doxia-logging-api-1.9.1.jar
...
[WARNING] size mismatch doxia-module-markdown-1.9.1.jar: investigate with diffoscope target/reference/doxia-module-markdown-1.9.1.jar doxia-modules/doxia-module-markdown/target/doxia-module-markdown-1.9.1.jar
[WARNING] Reproducible Build output summary: 39 files ok, 18 different
[WARNING] see diff target/reference/doxia-module-markdown-1.9.1.buildinfo doxia-modules/doxia-module-markdown/target/doxia-module-markdown-1.9.1.buildinfo
[WARNING] see also https://maven.apache.org/guides/mini/guide-reproducible-builds.html
[INFO] Reproducible Build output comparison saved to /tmp/doxia-1.9.1/doxia-modules/doxia-module-markdown/target/doxia-module-markdown-1.9.1.buildcompare
[INFO] Aggregate buildcompare copied to /tmp/doxia-1.9.1/target/doxia-1.9.1.buildcompare
...
```

The log has several parts worth reading:

- the summary: `[WARNING] Reproducible Build output summary: 39 files ok, 18 different`
- key information on reference build environment:  
    `[INFO] Reference build java.version: 1.8 (from MANIFEST.MF Build-Jdk-Spec)`  
    `[INFO] Reference build os.name: Unix (from pom.properties newline)`  
    which are the key requirements (JDK major version and Operating System) for the build environment to reproduce the reference build
- eventual mismatch with current build environment:  
    `[ERROR] Current build java.version: 1.7 (from MANIFEST.MF Build-Jdk-Spec)`
- on each artifact different from reference, a command is provided to investigate differences using [diffoscope tool](https://diffoscope.org/), a diff tool working inside archives (jar, zip, tar, ...):  
    simply copy paste provided command like `diffoscope target/reference/doxia-logging-api-1.9.1.jar doxia-logging-api/target/doxia-logging-api-1.9.1.jar` and you'll see where differences are:

    ```shell
    $ diffoscope target/reference/doxia-logging-api-1.9.1.jar doxia-logging-api/target/doxia-logging-api-1.9.1.jar
    --- target/reference/doxia-logging-api-1.9.1.jar
    +++ doxia-logging-api/target/doxia-logging-api-1.9.1.jar
    ├── zipinfo /dev/stdin
    │ @@ -1,8 +1,8 @@
    │ -Zip file size: 11516 bytes, number of entries: 19
    │ +Zip file size: 11497 bytes, number of entries: 19
    │  -rw-r--r--  2.0 unx      337 b- defN 20-Feb-13 21:11 META-INF/MANIFEST.MF
    │  drwxr-xr-x  2.0 unx        0 b- stor 20-Feb-13 21:11 META-INF/
    │  drwxr-xr-x  2.0 unx        0 b- stor 20-Feb-13 21:11 org/
    │  drwxr-xr-x  2.0 unx        0 b- stor 20-Feb-13 21:11 org/apache/
    │  drwxr-xr-x  2.0 unx        0 b- stor 20-Feb-13 21:11 org/apache/maven/
    │  drwxr-xr-x  2.0 unx        0 b- stor 20-Feb-13 21:11 org/apache/maven/doxia/
    │  drwxr-xr-x  2.0 unx        0 b- stor 20-Feb-13 21:11 org/apache/maven/doxia/logging/
    │ @@ -11,11 +11,11 @@
    │  drwxr-xr-x  2.0 unx        0 b- stor 20-Feb-13 21:11 META-INF/maven/org.apache.maven.doxia/doxia-logging-api/
    │  -rw-r--r--  2.0 unx     1641 b- defN 20-Feb-13 21:11 META-INF/DEPENDENCIES
    │  -rw-r--r--  2.0 unx    11358 b- defN 20-Feb-13 21:11 META-INF/LICENSE
    │  -rw-r--r--  2.0 unx      177 b- defN 20-Feb-13 21:11 META-INF/NOTICE
    │  -rw-r--r--  2.0 unx      716 b- defN 20-Feb-13 21:11 org/apache/maven/doxia/logging/Log.class
    │  -rw-r--r--  2.0 unx      198 b- defN 20-Feb-13 21:11 org/apache/maven/doxia/logging/LogEnabled.class
    │  -rw-r--r--  2.0 unx     2840 b- defN 20-Feb-13 21:11 org/apache/maven/doxia/logging/PlexusLoggerWrapper.class
    │ --rw-r--r--  2.0 unx     4208 b- defN 20-Feb-13 21:11 org/apache/maven/doxia/logging/SystemStreamLog.class
    │ +-rw-r--r--  2.0 unx     4192 b- defN 20-Feb-13 21:11 org/apache/maven/doxia/logging/SystemStreamLog.class
    │  -rw-r--r--  2.0 unx     1539 b- defN 20-Feb-13 21:11 META-INF/maven/org.apache.maven.doxia/doxia-logging-api/pom.xml
    │  -rw-r--r--  2.0 unx       74 b- defN 20-Feb-13 21:11 META-INF/maven/org.apache.maven.doxia/doxia-logging-api/pom.properties
    │ -19 files, 23088 bytes uncompressed, 8842 bytes compressed:  61.7%
    │ +19 files, 23072 bytes uncompressed, 8823 bytes compressed:  61.8%
    ├── META-INF/MANIFEST.MF
    │ @@ -1,10 +1,10 @@
    │  Manifest-Version: 1.0
    │ +Implementation-Vendor: The Apache Software Foundation
    │  Implementation-Title: Doxia :: Logging API
    │  Implementation-Version: 1.9.1
    │ +Build-Jdk-Spec: 1.7
    │  Specification-Vendor: The Apache Software Foundation
    │ -Specification-Title: Doxia :: Logging API
    │ -Build-Jdk-Spec: 1.8
    │  Created-By: Maven Jar Plugin 3.2.0
    │ +Specification-Title: Doxia :: Logging API
    │  Specification-Version: 1.9
    │ -Implementation-Vendor: The Apache Software Foundation
    ├── org/apache/maven/doxia/logging/PlexusLoggerWrapper.class
    ...
    ```

In this example, reference build was done with JDK 8 (see `│ -Build-Jdk-Spec: 1.8`), but the current build was done with JDK 7 (see `│ +Build-Jdk-Spec: 1.7`): this explains both difference in content of `META-INF/MANIFEST.MF` of every .jar file, but also of some bytecode in `.class` files.

When general environment issues have been fixed, find for each remaining issue which Maven plugin generated the content, and check whether an update of that plugin generates reproducible output: the [required upgrades list](/plugins/maven-artifact-plugin/plugin-issues.html) is a starting point.

## More Details

Reproducible Builds for Maven:

- Require **no version ranges** in dependencies (not strictly true with [Maven 3.10 and `-Dmaven.session.versionFilter`](https://maveniverse.eu/blog/2026/04/17/taming-maven-version-ranges/)),
- Generally give **different results on Windows and Unix** because of different newlines. (carriage return linefeed on Windows, linefeed on Unixes)
- Generally depend on the **major version only of the JDK** used to compile. (Even with source/target defined, each major JDK version changes the generated bytecode, but minor usually not, nor vendor)

For detailed explanations, see [Maven "Reproducible/Verifiable Builds" Wiki page](https://s.apache.org/reproducible-builds).

## FAQ

- Q. Can the `project.build.outputTimestamp` property in `pom.xml` be updated automatically at release time?

  A. Yes.

  Details depend on your release process tooling:

  - if you use [maven-release-plugin](/plugins/maven-release-plugin/), you'll need **version 3.0.0-M1 or later**: it will automatically update the timestamp value in `pom.xml` during the release in the same commit that updates version,
  - if you have a custom release process tooling, you'll need to add the feature to your release tooling. Notice that if you're using `versions-maven-plugin` in custom release scripts, starting with release 2.9.0, [`versions:set` goal updates the property](https://github.com/mojohaus/versions/issues/453).
  - instead of explicitly writing a timestamp in their `pom.xml`, some people tend to prefer using last Git commit timestamp, like `${git.commit.time}` from [git-commit-id-maven-plugin](https://github.com/git-commit-id/git-commit-id-maven-plugin) (with `<dateFormatTimeZone>UTC</dateFormatTimeZone>` to be independent from user's timezone).

Don't hesitate to share your questions or solutions on [user mailing-list](/mailing-lists.html).

- Q. Which additional plugins need to be updated for Reproducible Builds?

  A. See the list of [required upgrades](/plugins/maven-artifact-plugin/plugin-issues.html)

- Q. Reproducible Builds is active by default on my project (from a parent POM, or using Maven 3.10+), how can I opt-out and disable it?

  A. We'd love to understand why you want to disable Reproducible Builds,
but any single character value (non-numeric) will disable Reproducible Builds: `<project.build.outputTimestamp>x</project.build.outputTimestamp>`
