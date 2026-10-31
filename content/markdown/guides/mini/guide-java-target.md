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


# Guide to Targeting a Java Version

A project often has to be built with a newer JDK than the Java version it supports at runtime. This guide shows how to tell the [Compiler Plugin](/plugins/maven-compiler-plugin/) which Java version to compile for.

## Using the release Option

Starting with JDK 9, the `javac` executable accepts the `--release` option to specify against which Java SE release you want to build the project. For example, you have JDK 21 installed and used by Maven, but you want to build the project against Java 8. The `--release` option ensures that the code is compiled following the rules of the programming language of the specified release, and that generated classes target that release as well as the public API of that release. Unlike the older `-source` and `-target` options, described [below](#Using_the_source_and_target_Options), the compiler detects and reports an error when the code uses APIs that don't exist in earlier releases of Java SE.

The option can be provided either through the `maven.compiler.release` property:

```xml
<project>
  [...]
  <properties>
    <maven.compiler.release>17</maven.compiler.release>
  </properties>
  [...]
</project>
```

or by configuring the plugin directly with its [`release`](/plugins/maven-compiler-plugin/compile-mojo.html#release) parameter:

```xml
<project>
  [...]
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.16.0</version>
        <configuration>
          <release>17</release>
        </configuration>
      </plugin>
    </plugins>
  </build>
  [...]
</project>
```

The value follows [Java's version-string scheme (JEP 223)](https://openjdk.org/jeps/223) adopted with Java 9, so the release number does not start with `1.` any more: write `8`, not `1.8`. The supported `release` values are the release of the JDK in use plus a limited number of previous releases.

### Building on JDK 8

The `--release` option is not supported by JDK 8. Since version 3.13.0 of the Compiler Plugin, with the default `javac` compiler id, the `release` parameter is only effective on Java 9 and later; on JDK 8 the plugin passes `source` and `target` to the compiler instead. A project that targets Java 8 and is built on JDK 8 as well as on JDK 9 or later can therefore set all three:

```xml
<configuration>
  <release>8</release>
  <source>8</source>
  <target>8</target>
</configuration>
```

With an older plugin version, or a `compilerId` other than `javac`, activate `maven.compiler.release` only on JDK 9 and later, with a profile:

```xml
<project>
  [...]
  <profiles>
    <profile>
      <id>set-compiler-release</id>
      <activation>
        <jdk>[9,)</jdk>
      </activation>
      <properties>
        <maven.compiler.release>8</maven.compiler.release>
      </properties>
    </profile>
  </profiles>
  [...]
</project>
```

## Using the source and target Options

Use `-source` and `-target` only when `release` is not available, for example with a plugin version older than 3.13.0 on JDK 8. The `javac` executable accepts them to compile for a different Java version than the one it runs on, and the Compiler Plugin passes them on through its [`source`](/plugins/maven-compiler-plugin/compile-mojo.html#source) and [`target`](/plugins/maven-compiler-plugin/compile-mojo.html#target) parameters. Use the version-string scheme of JEP 223 here too.

To use the Java 8 language features and produce classes that are compatible with a Java 8 JVM, add the two properties, which are the user properties of the plugin parameters:

```xml
<project>
  [...]
  <properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
  </properties>
  [...]
</project>
```

or configure the plugin directly:

```xml
<project>
  [...]
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.16.0</version>
        <configuration>
          <source>8</source>
          <target>8</target>
        </configuration>
      </plugin>
    </plugins>
  </build>
  [...]
</project>
```

Setting `target` does not guarantee that the code runs on a JRE of that version. Unintended use of APIs that only exist in later JREs makes the code fail at runtime with a linkage error. To avoid this, use the `release` option, configure the compiler's boot classpath to match the target JRE, or verify the API usage with the [Animal Sniffer Maven Plugin](https://www.mojohaus.org/animal-sniffer/animal-sniffer-maven-plugin/).

In the same way, setting `source` does not guarantee that the code compiles on a JDK of that version. To compile with a specific JDK that differs from the one running Maven, see the [Guide to Using Toolchains](/guides/mini/guide-using-toolchains.html).

## See Also

- [Guide to Using Toolchains](/guides/mini/guide-using-toolchains.html)
- [Guide to Multi-Release JARs](/guides/mini/guide-multi-release-jars.html)
