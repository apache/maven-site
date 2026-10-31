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

# Guide to Working with Manifests

The archive-producing plugins (`jar`, `war`, `ejb`, `ear` and others) build the manifest of their archive with [Maven Archiver](/shared/maven-archiver/index.html). You adjust it in the `archive` element of the plugin configuration, see [Guide to Configuring Archive Plugins](./guide-archive-configuration.html). The examples below use `maven-jar-plugin`, but they work in every plugin that uses Maven Archiver. The [Maven Archiver reference](/shared/maven-archiver/index.html) lists every `archive` and `manifest` parameter.

## Default Manifest

Without any configuration the manifest contains these entries:

```
Manifest-Version: 1.0
Created-By: Maven JAR Plugin 3.5.1
Java-Version: 17
Build-Jdk-Spec: 21
```

- `Created-By` names the plugin that built the archive, with its version.
- `Java-Version` is the `release` (or else `target`) of `maven-compiler-plugin`, such as the `maven.compiler.release` property. It is left out when neither is set.
- `Build-Jdk-Spec` is the specification version of the JDK running Maven, here 21. It does not take [toolchains](./guide-using-toolchains.html) into account.

Set `<addDefaultEntries>false</addDefaultEntries>` in `<manifest>` to leave out `Created-By`, `Java-Version` and `Build-Jdk-Spec`.

## Adding Implementation And Specification Details

Implementation and specification entries are not added by default. Ask for them in the `manifest` element:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jar-plugin</artifactId>
  <version>3.5.1</version>
  <configuration>
    <archive>
      <manifest>
        <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
        <addDefaultSpecificationEntries>true</addDefaultSpecificationEntries>
      </manifest>
    </archive>
  </configuration>
</plugin>
```

For a project named `Demo`, version `1.2.3`, whose `<organization>` is named `Acme`, the manifest gets these entries on top of the default ones:

```
Specification-Title: Demo
Specification-Version: 1.2
Specification-Vendor: Acme
Implementation-Title: Demo
Implementation-Version: 1.2.3
Implementation-Vendor: Acme
```

`Specification-Version` is the major and minor version of the project. If the POM has no `<organization>`/`<name>`, the two `Vendor` entries are left out.

To record the build environment, add `<addBuildEnvironmentEntries>true</addBuildEnvironmentEntries>` to `<manifest>`. It adds `Build-Tool` (the Maven version), `Build-Jdk` (JDK version and vendor) and `Build-Os` (operating system name, version and architecture). Those values differ between machines, so they make a [reproducible build](./guide-reproducible-builds.html) harder to achieve.

## Manifest Entries

If the other options do not cover what you need, add your own entries with `<manifestEntries>`. Values can be literals or POM expressions:

```xml
<project>
  <url>http://some.url.org/</url>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.5.1</version>
        <configuration>
          <archive>
            <manifestEntries>
              <mode>development</mode>
              <url>${project.url}</url>
            </manifestEntries>
          </archive>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

The manifest then contains:

```
mode: development
url: http://some.url.org/
```

If the POM has no `<url>`, the expression cannot be resolved and the `url` entry is left out.

## Manifest Sections

`<manifestSections>` adds named sections to the manifest. Each `<manifestSection>` has a `<name>` and its own `<manifestEntries>`:

```xml
<archive>
  <manifestSections>
    <manifestSection>
      <name>foo</name>
      <manifestEntries>
        <id>nice foo</id>
      </manifestEntries>
    </manifestSection>
    <manifestSection>
      <name>bar</name>
      <manifestEntries>
        <id>nice bar</id>
      </manifestEntries>
    </manifestSection>
  </manifestSections>
</archive>
```

Each section follows the main section after a blank line:

```
Name: foo
id: nice foo

Name: bar
id: nice bar
```

## Using Your Own Manifest File

To start from a hand-written manifest, point `<manifestFile>` at it:

```xml
<archive>
  <manifestFile>src/main/resources/META-INF/MANIFEST.MF</manifestFile>
</archive>
```

The entries of your file are merged with the ones Maven Archiver creates, and an entry in your file overrides the generated one with the same name.

## Setting Up the Classpath

The examples in this section assume a project with these dependencies:

```xml
<dependencies>
  <dependency>
    <groupId>commons-lang</groupId>
    <artifactId>commons-lang</artifactId>
    <version>2.1</version>
  </dependency>
  <dependency>
    <groupId>org.codehaus.plexus</groupId>
    <artifactId>plexus-utils</artifactId>
    <version>1.1</version>
  </dependency>
</dependencies>
```

The manifest excerpts show only the entries that the example adds. In the file itself, lines are wrapped at 72 bytes.

### Adding a Class-Path Entry

`<addClasspath>` adds the runtime dependencies of the project to the manifest:

```xml
<archive>
  <manifest>
    <addClasspath>true</addClasspath>
  </manifest>
</archive>
```

```
Class-Path: commons-lang-2.1.jar plexus-utils-1.1.jar
```

### Making the Jar Executable

An executable jar also needs the main class, set with `<mainClass>`:

```xml
<archive>
  <manifest>
    <addClasspath>true</addClasspath>
    <mainClass>fully.qualified.MainClass</mainClass>
  </manifest>
</archive>
```

```
Class-Path: commons-lang-2.1.jar plexus-utils-1.1.jar
Main-Class: fully.qualified.MainClass
```

### Defining a Classpath Directory Prefix

When the dependencies live in a directory next to the archive, for example in a [skinny war](/plugins/maven-ear-plugin/examples/skinny-wars.html), add a `<classpathPrefix>`:

```xml
<archive>
  <manifest>
    <addClasspath>true</addClasspath>
    <classpathPrefix>lib/</classpathPrefix>
  </manifest>
</archive>
```

```
Class-Path: lib/commons-lang-2.1.jar lib/plexus-utils-1.1.jar
```

### Using a Maven Repository-Style Classpath

If the archive holds a Maven repository-style directory tree, set `<classpathLayoutType>` to `repository`:

```xml
<archive>
  <manifest>
    <addClasspath>true</addClasspath>
    <classpathPrefix>lib/</classpathPrefix>
    <classpathLayoutType>repository</classpathLayoutType>
  </manifest>
</archive>
```

```
Class-Path: lib/commons-lang/commons-lang/2.1/commons-lang-2.1.jar lib/org/codehaus/plexus/plexus-utils/1.1/plexus-utils-1.1.jar
```

### Using a Custom Classpath Format

For any other layout, set `<classpathLayoutType>` to `custom` and describe the layout in `<customClasspathLayout>`:

```xml
<archive>
  <manifest>
    <addClasspath>true</addClasspath>
    <classpathLayoutType>custom</classpathLayoutType>
    <customClasspathLayout>WEB-INF/lib/${artifact.groupIdPath}/${artifact.artifactId}-${artifact.version}${dashClassifier?}.${artifact.extension}</customClasspathLayout>
  </manifest>
</archive>
```

```
Class-Path: WEB-INF/lib/commons-lang/commons-lang-2.1.jar WEB-INF/lib/org/codehaus/plexus/plexus-utils-1.1.jar
```

Maven Archiver resolves each expression in the layout like this:

1. If present, it trims the prefix `artifact.` from the expression.
2. It tries the expression as a property of the artifact (`artifactId` calls `getArtifactId()`).
3. It tries the expression as a property of the artifact's `ArtifactHandler` (`extension` calls `getExtension()`).
4. It tries the expression as a key of a special set of properties:
    - `dashClassifier`: `-` followed by the classifier if the artifact has one, otherwise an empty string.
    - `dashClassifier?`: a synonym of `dashClassifier`.
    - `groupIdPath`: the group ID with each `.` replaced by `/`.

### Handling Snapshot Versions

By default, a snapshot dependency appears in the `Class-Path` with its timestamped version, such as `foo-1.0-20260101.120000-1.jar`. Which name your archive actually contains depends on how you assemble it. For example, the [Assembly Plugin](/plugins/maven-assembly-plugin/) decides this in the `<outputFileNameMapping>` of a `<dependencySet>`.

To use the `-SNAPSHOT` name with the simple or repository layout, disable `<useUniqueVersions>`:

```xml
<archive>
  <manifest>
    <addClasspath>true</addClasspath>
    <useUniqueVersions>false</useUniqueVersions>
  </manifest>
</archive>
```

With the custom layout, use `${artifact.baseVersion}` instead of `${artifact.version}`:

```xml
<customClasspathLayout>WEB-INF/lib/${artifact.groupIdPath}/${artifact.artifactId}-${artifact.baseVersion}${dashClassifier?}.${artifact.extension}</customClasspathLayout>
```
