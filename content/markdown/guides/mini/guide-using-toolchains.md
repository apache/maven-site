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
|      jdk       | [`keytool-maven-plugin`](https://www.mojohaus.org/keytool/)                                                 | 1.4                 | MojoHaus      |
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
    <version>3.16.0</version>
  </plugin>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-toolchains-plugin</artifactId>
    <version>3.3.0</version>
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
          <version>21</version>
          <vendor>temurin</vendor>
        </jdk>
      </toolchains>
    </configuration>
  </plugin>
  ...
</plugins>
```

As you can see in the example above, a JDK toolchain with `<version>` "21" and `<vendor>` "temurin" is to be used. Now how does the plugin know where this JDK is installed? This is where the `toolchains.xml` file comes in.

The `toolchains.xml` file (see below) is the configuration file where you set the installation paths of your toolchains. This file should be put in the `${user.home}/.m2` directory. When the `maven-toolchains-plugin` executes, it looks for the `toolchains.xml` file, reads it and looks for a toolchain matching the toolchains requirements configured in the plugin. In this example, that is a JDK toolchain with `<version>` "21" and `<vendor>` "temurin". Once a match is found, the plugin then stores the toolchain to be used in the MavenSession. As you can see in the `toolchains.xml` below, there is indeed a JDK toolchain with `<version>` "21" and `<vendor>` "temurin" configured. So when the `maven-compiler-plugin` configured in the `pom.xml` above executes, it sees that a JDK toolchain is set in the MavenSession and will use that toolchain (that would be the JDK installed at `/path/to/jdk/21` in this example) to compile the sources.

Starting with [Maven 3.3.1](/docs/3.3.1/release-notes.html) you can put the `toolchains.xml` file wherever you like by using the `-t,--toolchains` option (or `-gt,--global-toolchains` for the global file), but it is recommended to locate it into `${user.home}/.m2/`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<toolchains>
  <!-- JDK toolchains -->
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>21</version>
      <vendor>temurin</vendor>
    </provides>
    <configuration>
      <jdkHome>/path/to/jdk/21</jdkHome>
    </configuration>
  </toolchain>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>17</version>
      <vendor>temurin</vendor>
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

Instead of writing the JDK entries by hand, the Toolchains Plugin can [discover the installed JDKs](/plugins/maven-toolchains-plugin/toolchains/jdk-discovery.html) and select a matching one with the [`toolchains:select-jdk-toolchain`](/plugins/maven-toolchains-plugin/select-jdk-toolchain-mojo.html) goal, or print them in `toolchains.xml` format with `toolchains:generate-jdk-toolchains-xml`.

## JDK Toolchain

The toolchain type id for JDK is `jdk`.

Predefined `<provides>` identification tokens, for requirement matching in plugin configuration, are:

- `version` marks the version of the JDK in `toolchains.xml`. In the plugin configuration, this can be either a single version or a [version range](/enforcer/enforcer-rules/versionRanges.html).
- Other tokens, such as `vendor`, are accepted, but only exact matches are supported.

In `toolchains.xml`, the only child of the `<configuration>` element is `jdkHome`. It designates the root directory of a JDK installation.

The `toolchains.xml` above defines a toolchain with version 21 and vendor "temurin". A project can request it by specifying the type `jdk` and the version `21`. It can also use a version range that includes 21 like `[17, 25]`, or ask for the vendor `temurin`, with or without a version.

Besides the `version`, the values are opaque strings. Maven looks in `toolchains.xml` for a toolchain that provides `version` "21" and `vendor` "temurin"; it does not know or care what these strings mean. It does not concern itself with whether the `jdkHome` of the matching toolchain is really JDK 21 from the Temurin project, or JDK 17 from another vendor. The JDK toolchain only checks that the directory exists. All conditions need to be satisfied to find a toolchain.

For example, to accept any JDK of version 17 or greater:

```xml
<configuration>
  <toolchains>
    <jdk>
      <version>[17,)</version>
    </jdk>
  </toolchains>
</configuration>
```

## Setting a Toolchain for a Single Plugin

The `maven-toolchains-plugin` configures one JDK toolchain for all the toolchain-aware plugins of the build. Since `maven-compiler-plugin` 3.6, used with Maven 3.3.1 or later, the compiler plugin also accepts its own toolchain with the [`jdkToolchain`](/plugins/maven-compiler-plugin/compile-mojo.html#jdkToolchain) parameter, which overrules the one selected by the `maven-toolchains-plugin`. This is useful when different executions need different JDKs, for example when the test sources need a different compiler than the main sources:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.16.0</version>
  <configuration>
    <jdkToolchain>
      <version>17</version>
    </jdkToolchain>
  </configuration>
</plugin>
```

## Compiling Without Toolchains

Toolchains are the preferable way to use a different JDK. Outside of a toolchain, the Compiler Plugin can still be pointed at a specific `javac`. This configuration is specific to that plugin and does not affect the others. Set `fork` to `true` and give the path of the compiler in `executable`:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.16.0</version>
  <configuration>
    <fork>true</fork>
    <executable>${JAVA_17_HOME}/bin/javac</executable>
  </configuration>
</plugin>
```

To keep the build portable, each developer defines the property in [`settings.xml`](/ref/current/maven-settings/settings.html), or sets an environment variable:

```xml
<settings>
  <profiles>
    <profile>
      <id>compiler</id>
      <properties>
        <JAVA_17_HOME>/path/to/jdk/17</JAVA_17_HOME>
      </properties>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>compiler</activeProfile>
  </activeProfiles>
</settings>
```

To target an older Java version than the JDK that runs the compiler, see [Targeting a Java Version](/guides/mini/guide-java-target.html). If you build with a different JDK, you may also want to [customize the JAR manifest](/guides/mini/guide-manifest.html).

## Custom Toolchains

You can create custom toolchains to use with standard plugins (such as the `maven-compiler-plugin`) or with custom plugins.

A full working sample is included in the `maven-toolchains-plugin` integration tests, which are part of [the plugin source tree](/plugins/maven-toolchains-plugin/scm.html):

- `src/it/setup-custom-toolchain` holds the custom toolchain and plugin,
- `src/it/use-custom-toolchain` holds a sample project using the toolchain through its plugin.

### Creating a Custom Toolchain

A toolchain consists of:

- an interface extending [`org.apache.maven.toolchain.Toolchain`](/ref/current/maven-core/apidocs/org/apache/maven/toolchain/Toolchain.html),
- an implementation of this interface. Extending [`org.apache.maven.toolchain.DefaultToolchain`](/ref/current/maven-core/apidocs/org/apache/maven/toolchain/DefaultToolchain.html) is strongly encouraged, since it provides [`org.apache.maven.toolchain.ToolchainPrivate`](/ref/current/maven-core/apidocs/org/apache/maven/toolchain/ToolchainPrivate.html), which is an internal requirement,
- a [`org.apache.maven.toolchain.ToolchainFactory`](/ref/current/maven-core/apidocs/org/apache/maven/toolchain/ToolchainFactory.html), provided as [JSR-330 component](/maven-jsr330.html) annotated with `@Named("<toolchainType>")` (for example `@Named("custom")`) and `@Singleton`.

### Creating a Plugin Using a Toolchain

To find a tool, a plugin uses the [`ToolchainManager`](/ref/current/maven-core/apidocs/org/apache/maven/toolchain/ToolchainManager.html) API to load the toolchain, then uses that toolchain object to find the tool's path:

```java
@Parameter(defaultValue = "${session}", readonly = true, required = true)
private MavenSession session;

private final ToolchainManager toolchainManager;

@Inject
public MyMojo(ToolchainManager toolchainManager) {
    this.toolchainManager = toolchainManager;
}

public void execute() throws MojoExecutionException {
    // get the custom toolchain
    CustomToolchain toolchain = (CustomToolchain) toolchainManager.getToolchainFromBuildContext("custom", session);

    if (toolchain == null) {
        throw new MojoExecutionException(
                "Could not find 'custom' toolchain: please check maven-toolchains-plugin configuration.");
    }

    getLog().info("Found 'custom' toolchain in build context.");

    // get a tool from the toolchain
    String path = toolchain.findTool("tool");

    getLog().info("Found expected tool named 'tool' at following location: " + path);
}
```

This code uses [Maven Plugin Tool Annotations](/plugin-tools/maven-plugin-plugin/examples/using-annotations.html).

### Using the Custom Toolchain and its Plugin

The custom toolchain implementation needs to be shared between the toolchain-aware plugin and `maven-toolchains-plugin`. This is done using a Maven extension:

- if the toolchain is packaged with the plugin, declare the plugin as an extension:

    ```xml
    <plugin>
      <groupId>...</groupId>
      <artifactId>...</artifactId>
      <version>...</version>
      <extensions>true</extensions><!-- to share the custom toolchain with maven-toolchains-plugin -->
    </plugin>
    ```

- if the toolchain is packaged separately, to be shared by multiple plugins, declare it as a build extension:

    ```xml
    <project>
      <build>
        <extensions>
          <extension>
            <groupId>...</groupId>
            <artifactId>...</artifactId>
            <version>...</version>
          </extension>
        </extensions>
      </build>
    </project>
    ```

Packaging a toolchain in its own artifact separate from the plugin is only useful when there are multiple plugins using the toolchain. If a custom toolchain will only be used by one plugin (possibly providing multiple goals), it is simpler to package the toolchain with the plugin in a single artifact.
