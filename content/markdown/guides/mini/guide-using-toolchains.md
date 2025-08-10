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

# Guide to Using Toolchains

## What are Toolchains?

Maven Toolchains provide a way for a project to specify the JDK (or other tools) used to build the project, without needing to configure this in each plugin or in every `pom.xml`.

When Maven Toolchains are used to specify the JDK, a project can be built by a specific version of the JDK independent of the one Maven is running with. This is similar to how JDK versions can be set in IDEs like IDEA, NetBeans and Eclipse.

### Prerequisites

For more details about Toolchains' design and implementation, see [Toolchains](https://cwiki.apache.org/confluence/display/MAVENOLD/Toolchains).

Below are some plugins which are toolchain-aware, with the toolchain-type used:

| Toolchain type | **Plugin**                                                                                                 | **Starting with**   | **Hosted at** |
|:--------------:|:-----------------------------------------------------------------------------------------------------------|:--------------------|:--------------|
|      jdk       | [`maven-compiler-plugin`](/plugins/maven-compiler-plugin/)                                                 | 2.1                 | Apache Maven  |
|      jdk       | [`maven-jarsigner-plugin`](/plugins/maven-jarsigner-plugin/)                                               | 1.3                 | Apache Maven  |
|      jdk       | [`maven-javadoc-plugin`](/plugins/maven-javadoc-plugin/)                                                   | 2.5                 | Apache Maven  |
|      jdk       | [`maven-pmd-plugin`](/plugins/maven-pmd-plugin/)                                                           | 3.14.0              | Apache Maven  |
|      jdk       | [`maven-surefire-plugin`](/plugins/maven-surefire-plugin/)                                                 | 2.5                 | Apache Maven  |
|      jdk       | [`maven-failsafe-plugin`](/plugins/maven-failsafe-plugin/)                                                 | 2.5                 | Apache Maven  |
|      jdk       | [`animal-sniffer-maven-plugin`](https://www.mojohaus.org/animal-sniffer/animal-sniffer-maven-plugin/)      | 1.3                 | MojoHaus      |
|      jdk       | [`cassandra-maven-plugin`](https://www.mojohaus.org/cassandra-maven-plugin/)                               | 0.7.0-1             | MojoHaus      |
|      jdk       | [`exec-maven-plugin`](https://www.mojohaus.org/exec-maven-plugin/)                                         | 1.1.1               | MojoHaus      |
|      jdk       | [`jdiff-maven-plugin`](https://www.mojohaus.org/jdiff-maven-plugin/)                                       | 1.0-beta-1-SNAPSHOT | MojoHaus      |
|      jdk       | [`keytool-maven-plugin`](https://www.mojohaus.org/keytool/keytool-maven-plugin/)                           | 1.4                 | MojoHaus      |
|    protobuf    | [`protobuf-maven-plugin`](https://www.xolstice.org/protobuf-maven-plugin/examples/protobuf-toolchain.html) | 0.6.1               | github        |

## Using Toolchains in Your Project

There are two essential components that you need to configure in order to use toolchains:

1. the [`maven-toolchains-plugin`](/plugins/maven-toolchains-plugin/) in your project POM,
2. the [`toolchains.xml`](/ref/current/maven-core/toolchains.html) file on the building machine.

The `maven-toolchains-plugin` is the one that sets the toolchain to be used by the toolchain-aware plugins in your project.

For example, you want to use a different JDK version to build your project than the version used to run Maven, you can configure the version you want to use via this plugin as shown in the `pom.xml` below:

```xml
<plugins>
 ...
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
  </plugin>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-toolchains-plugin</artifactId>
    <version>1.1</version>
    <executions>
      <execution>
        <goals>
          <goal>toolchain</goal>
        </goals>
      </execution>
    </executions>
    <configuration>
      <toolchains>
        <jdk>
          <version>1.8</version>
          <vendor>openjdk</vendor>
        </jdk>
      </toolchains>
    </configuration>
  </plugin>
  ...
</plugins>
```

As you can see in the example above, a JDK toolchain with `<version>` "1.8" and `<vendor>` "openjdk" is to be used. Now how does the plugin know where this JDK is installed? This is where the `toolchains.xml` file comes in.

The `toolchains.xml` file (see below) is the configuration file where you set the installation paths of your toolchains. This file should be put in the `${user.home}/.m2` directory. When the `maven-toolchains-plugin` executes, it looks for the `toolchains.xml` file, reads it and looks for a toolchain matching the toolchains requirements configured in the plugin. In this example, that is a JDK toolchain with `<version>` "1.8" and `<vendor>` "openjdk". Once a match is found, the plugin then stores the toolchain to be used in the MavenSession. As you can see in the `toolchains.xml` below, there is indeed a JDK toolchain with `<version>` "1.8" and `<vendor>` "openjdk" configured. So when the `maven-compiler-plugin` configured in the `pom.xml` above executes, it sees that a JDK toolchain is set in the MavenSession and will use that toolchain (that would be the JDK installed at `/path/to/jdk/1.8` in this example) to compile the sources.

Starting with [Maven 3.3.1](/docs/3.3.1/release-notes.html) you can put the `toolchains.xml` file wherever you like by using the `--global-toolchains file` option, but it is recommended to locate it into `${user.home}/.m2/`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<toolchains>
  <!-- JDK toolchains -->
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>1.8</version>
      <vendor>openjdk</vendor>
    </provides>
    <configuration>
      <jdkHome>/path/to/jdk/1.8</jdkHome>
    </configuration>
  </toolchain>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>17</version>
      <vendor>azul</vendor>
    </provides>
    <configuration>
      <jdkHome>/path/to/jdk/17</jdkHome>
    </configuration>
  </toolchain>

  <!-- other toolchains -->
  <toolchain>
    <type>netbeans</type>
    <provides>
      <version>5.5</version>
    </provides>
    <configuration>
      <installDir>/path/to/netbeans/5.5</installDir>
    </configuration>
  </toolchain>
</toolchains>
```

You can configure as many toolchains as you want in your `toolchains.xml` file.
