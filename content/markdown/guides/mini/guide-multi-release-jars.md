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


# Guide to Multi-Release JARs

[JEP 238](https://openjdk.org/jeps/238) introduced multi-release JARs: one JAR that holds Java-version-dependent classes, where the runtime picks the best matching version of each class. Building one spans two plugins. The [Compiler Plugin](/plugins/maven-compiler-plugin/) compiles the sources once per Java version, and the [JAR Plugin](/plugins/maven-jar-plugin/) packages the result.

## The Problem

A library must stay compatible with earlier Java versions but would like to use new JDK features when it runs on a newer one. For example, `java.nio.file` was added in Java 7 and handles files much better, while Maven itself still required Java 6 at the time.

Without multi-release JARs there are two solutions:

1. Compile with the targeted JDK (Java 6) and use reflection. The code is guaranteed to be Java 6 compatible, but the reflective part is hard to verify.

    ```java
    if (isAtLeastJava7()) {
        Method toPathMethod = f.getClass().getMethod("toPath");
        Object toPathInstance = toPathMethod.invoke(f);
        Method isSymbolicLink = Class.forName("java.nio.file.Files").getMethod("isSymbolicLink");
        ...
    } else {
        // compare absoluteFile with canonicalFile
        ...
    }
    ```

2. Compile with the required JDK (Java 7), but with `source` and `target` set to the lowest version (1.6). The danger is that you cannot ensure that all code is Java 6 compatible. Depending on the code structure, [Animal Sniffer](https://www.mojohaus.org/animal-sniffer/) can help.

    ```java
    if (isAtLeastJava7()) {
        return Files.isSymbolicLink(f.toPath());
    } else {
        // compare absoluteFile with canonicalFile
        ...
    }
    ```

## The Solution

A multi-release JAR keeps the base classes at the root and the version-specific ones under `META-INF/versions`:

```
A.class
B.class
C.class
D.class
META-INF/MANIFEST.MF { Multi-Release: true }
         versions/9/A.class
                    B.class
                  10/A.class
                     C.class
```

With the `Multi-Release: true` attribute in the manifest, the Java runtime also looks inside `META-INF/versions` for version-specific classes. Without it, only the base classes are used. The [`maven-jar-plugin`](/plugins/maven-jar-plugin/jar-mojo.html#detectMultiReleaseJar) adds the attribute by itself when the JAR contains a `META-INF/versions` directory (`detectMultiReleaseJar`, enabled by default). To set it explicitly, add it with the archive configuration, see [Working with Manifests](/guides/mini/guide-manifest.html):

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jar-plugin</artifactId>
  <version>3.5.1</version>
  <configuration>
    <archive>
      <manifestEntries>
        <Multi-Release>true</Multi-Release>
      </manifestEntries>
    </archive>
  </configuration>
</plugin>
```

The Compiler Plugin can place the output of an execution in `META-INF/versions/${release}` for you: set [`release`](/plugins/maven-compiler-plugin/compile-mojo.html#release) together with [`multiReleaseOutput`](/plugins/maven-compiler-plugin/compile-mojo.html#multiReleaseOutput) to `true` in an additional execution.

## Challenges

The theory is simple, but in practice multi-release JARs get complex. Think twice before turning a JAR into one, because such JARs are hard to read, maintain and test. Applications rarely need this, unless the application is widely distributed and you do not control the targeted Java runtime. Libraries should ask: do I need this new Java feature? Can I make this Java version the new requirement? Would an `if`/`else` as in the first solution above be acceptable?

Keep these facts in mind:

- All classes must stay in sync. If you add a method to one class, add it to the other versions as well. Letting all multi-release classes implement an interface, and only calling the interface methods from the base code, reduces the risk. The best protection is to test the JAR with all targeted Java versions.
- The Java compiler must be called once per Java version, either with multiple Maven projects or modules, or with extra compiler executions in the POM (as older projects with a `module-info` do, see the [Compiler Plugin's `module-info` example](/plugins/maven-compiler-plugin/examples/module-info.html)).
- The `Multi-Release: true` attribute is only recognized when the classes are in a JAR, so you cannot test the classes in `target/classes/META-INF/versions/${release}/`.
- IDEs support one JDK per Maven project, whereas with multi-release JARs you want to choose it per source folder.

## Patterns

There are several patterns to build a multi-release JAR with Maven. Integration tests for each pattern, built from the same sources, are in the [Compiler Plugin's `multirelease-patterns` directory](https://github.com/apache/maven-compiler-plugin/tree/master/src/it/multirelease-patterns).

### Pattern 1: Maven Multimodule

The [first pattern](https://github.com/hboutemy/maven-jep238) came from the Maven team. The requirements were:

- one Maven call to compile, test and package the JAR,
- it must work with IDEs,
- developers should not have to change how they work, and the configuration stays simple.

The only solution that covers the first two points is to split the code into a multimodule project, where every module is a standard Maven project with close to no specific configuration. You can run it in two ways:

- Build with the highest required JDK and use `release` to make sure each module only uses matching APIs and syntax. As the version-specific code is isolated, the tests can run with a higher Java runtime.
- Use [toolchains](/guides/mini/guide-using-toolchains.html) if you really want to compile and test with the matching Java version.

The downside is that a hierarchical structure is required although the result is a single artifact.

### Pattern 2: Multi Project

[This solution](https://web.archive.org/web/2020/http://word-bits.flurg.com/multrelease-jars/) responds to the multimodule setup. The requirements are almost the same, but the project must not switch to a multimodule format, so the parts are separate Maven projects. One project holds the base code and becomes the multi-release JAR in the end.

The first build needs at least three Maven calls: compile the base, build all version-specific projects, and build the main project again, now including the version-specific classes extracted from their JARs. The setup is compact but has cyclic dependencies, which needs some tricks and complicates releasing: SNAPSHOTs must be installed in the local repository, and a release needs two releases of the base project.

### Pattern 3: Single Project

These variants keep everything in one Maven project without a multimodule layout, and need one Maven call to compile and package. Every Java version gets its own source folder and output folder, and the outputs are combined just before packaging.

- [Single Project](https://in.relation.to/2017/02/13/building-multi-release-jars-with-maven/): not covered is how to test every class.
- [Multi-Release Parent](http://www.russgold.net/sw/2018/04/easier-than-it-looks/): replaces the Ant plugin with extra executions of the `maven-compiler-plugin` and is set up as a parent POM, so other projects can reuse it. It uses toolchains to build each class with its matching Java version, so you always get the full JAR, and Surefire is called once.
- [CI server](https://github.com/codehaus-plexus/plexus-languages): only specifies executions for the sources of a specific Java version and does not use toolchains, but the JDK that runs Maven. Only the sources up to that Java version are compiled and tested, so it relies on a CI server where every targeted Java version is available. If the CI build succeeds, all classes were tested with their matching Java version.

### Pattern 4: Maven Extension and Plugin

The [`multi-release-jar-maven-plugin`](https://github.com/metlos/multi-release-jar-maven-plugin) introduces a packaging type and a plugin that takes care of the multiple executions of the `maven-compiler-plugin`, configured through its `perReleaseConfiguration`. Not covered is how to test every class.

### Summary

| | Maven Multimodule | Multi Project | Single project (runtime) | Single project (toolchains) | Maven extension and plugin |
|:---|:---:|:---:|:---:|:---:|:---:|
| Number of projects | 1 | 1 + number of Java versions | 1 | 1 | 1 |
| Builds to package | 1 | 2 + number of Java versions | 1 | 1 | 1 |
| Builds per project to test | 1 | 1 | number of Java versions | 1 | N/A (a) |
| Simple Maven project layout | No | Yes | Yes | Yes | Yes |
| Additional POM adjustments (b) | 1 (c) | number of Java versions (d) | number of Java versions (e) | unknown | number of Java versions (g) |
| Includes module descriptor | No (h) | No (h) | Yes | Yes | Yes |
| IDE support (i) | Yes | Yes | No | No | No |

- (a) The project can only run with the highest required JDK, so the code cannot be tested on all JDKs.
- (b) The number of executions added to the default lifecycle; it reflects the complexity of the POM.
- (c) The multimodule pattern uses the `maven-assembly-plugin` to assemble the multi-release JAR.
- (d) The multi project pattern uses the `maven-dependency-plugin` to unpack the version-specific dependency into its matching output directory.
- (e) A profile per required Java version holds an extra execution for that version.
- (g) The extension and plugin hides the multiple executions in `perReleaseConfiguration`.
- (h) Requires `--patch-module` on a dependency.
- (i) With IDE support, all classes are recognized and can be tested within the IDE.
