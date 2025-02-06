# Maven Dependencies

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

A dependency is a connection between a Maven project and an artifact
that Maven adds to a classpath. A dependency has both an artifact and metadata
about how the artifact is used by the project. This metadata includes
the scope in which the artifact is
added to the classpath and whether the artifact is required or optional.
Different projects can have dependencies on the same artifact with different scopes
and optionality. That is, a dependency is a property of a project, not an artifact.

A dependency is defined by a `dependency` element in the project's
pom.xml file. The child elements of the `dependency` specify the
coordinates of the artifact to depend on and the scope in which that
dependency applies. Consider this `dependency` element:

```xml
<dependency>
  <groupId>com.google.guava</groupId>
  <artifactId>guava</artifactId>
  <version>33.4.0-jre</version>
  <scope>test</scope>
</dependency>
```

This element says that the artifact com.google.guava:guava:33.4.0-jre
should be loaded from the Maven repository system and added to the
classpath used to run tests.

Occasionally, the group ID, artifact ID, and version are not enough to
uniquely identify an artifact. Sometimes you also need an extension
and/or a classifier. Classifiers are added directly to the `dependency`
element as in this dependency on the
io.netty:netty-transport-native-epoll native library for Linux 64-bit
X86 chips:

```xml
<dependency>
  <groupId>io.netty</groupId>
  <artifactId>netty-transport-native-epoll</artifactId>
  <version>4.1.192</version>
  <classifier>linux-x86_64</classifier>
</dependency>
```

Extensions are a little more complicated. The `dependency` element does
not have an `extension` child element. Instead it has a `type` element
which maps to an extension and sometimes a classifier. For example, here
is a dependency with type test-jar:

```xml
<dependency>
  <groupId>org.example</groupId>
  <artifactId>reusable-test-support</artifactId>
  <version>2.3</version>
  <type>test-jar</type>
</dependency>
```

This element says that the artifact
org.example:reusable-test-support:2.3::jar should be loaded from the
Maven repository systems. Although the type is `test-jar`, the extension
is `jar`. Dependency types do not one-to-one match artifact extensions.

The `classifier`, `type`, `optional`, and `scope` elements all have defaults and are often
omitted. The default classifier is the empty string. The default type is
jar. The default opitonality is false. The default scope is compile.
Thus this dependency element adds the artifact nu.xom:xom:1.3.9::jar to all of the
project's classpaths as a required dependency:

```xml
<dependency>
  <groupId>nu.xom</groupId>
  <artifactId>xom</artifactId>
  <version>1.3.9</version>
</dependency>
```

A dependency element does not say in which repository to look for the corresponding artifact.
That is determined by the settings.xml file and the `repositories` element in the
pom.xml file and its ancestors.

## Dependency Scopes

Every dependency has a scope that determines which classpaths the
artifact referenced by the dependency is added to. For example,
should it be added to compile-time classpath, the test classpath, or
both? The default scope when none is explicitly specified is `compile`.

Different projects may assign different scopes to the same artifact. For
instance, one project might use com.google.guava:34.4.0-jre only for
tests and thus set the scope to `test`. Another might need it at runtime
but not when the project is compiled, and thus set the scope to
`runtime`. A third project might need it for compiling, running, and
testing and thus set the scope to `compile`.

The scope does not have any effect on which artifact is loaded. Instead it determines:

1. Where the artifact is loaded from
2. Which classpaths the artifact is added to
3. Whether the artifact's own dependencies are also added to this project's classpath

Maven has six dependency scopes:

* compile - Compile scope artifacts are available in all classpaths. This is the default if no scope is provided.
* provided - Maven expects the JDK or a container to provide the artifact at runtime. It does not add it to the classpath.
* runtime - The artifact is required for execution but not for compilation. It is in the runtime and test classpaths, but not the compile classpath.
* test - The artifact is needed for tests but not by non-test code.
* system - The artifact is loaded from a specified path on the local system.
* import - Replace this dependency with the dependencies in the specified POM's `dependencyManagement` element. Only used when the type is pom.

<!-- As I write this, I'm realizing that Maven overloads dependency scope
     for multiple different purposes. In particular we've confused the scope in the
     classpath with where the artifact lives and how it's found.
     Scope should be one of compile, runtime, test, or all.
     system should be replaced by systempath. provided and import should be separate elements.
     Or perhaps we should have a separate source element that has values
     repository, system (or url),  provided, and import.
-->

Maven does not have a compileOnly scope that is available at compile time
but not at runtime. Compile scope dependencies are available in all classpaths.

## Dependency Types

Every dependency has a type that indicates the extension and the classifier
for the artifact, though the type-specified classifier can be overridden by
an explicit `classifier` element.
The type is set by the `type` element.
The default type when no `type` element is present is `jar`.
Different projects may assign different types to the same artifact.

This dependency element retrieves com.google.guava:guava:31.0.0::pom.

```xml
<dependency>
  <groupId>com.google.guava</groupId>
  <artifactId>guava</artifactId>
  <version>31.0.0</version>
  <type>pom</type>
</dependency>
```

Here the type of the dependency and the extension of the artifact are the same,
but that is not always the case.
For instance, the test-jar type selects an artifact with the extension `jar`.

Out of the box, Maven defines 11 dependency types:

|  Type Name   | Extension |  Classifier  |
|--------------|-----------|--------------|
| jar          | `jar`     |              |
| pom          | `pom`     |              |
| maven-plugin | `jar`     |              |
| ear          | `ear`     |              |
| ejb          | `jar`     |              |
| ejb-client   | `jar`     | `ejb-client` |
| javadoc      | `jar`     | `javadoc`    |
| java-source  | `jar`     | `sources`    |
| rar          | `rar`     |              |
| test-jar     | `jar`     | `tests`      |
| war          | `war`     |              |

From the table above, you can see that if the dependency type is "war", Maven retrieves
an artifact with the `war` extension. If the dependency type is "test-jar", Maven retrieves
an artifact with the `jar` extension and the classifier `tests`.

Finally, if Maven does not recognize a type, then the type becomes the extension and the
classifier is empty.  For example.
if the dependency type is `<type>tar.gz</type>`, the extension will also be `tar.gz`.
These mappings may be extended by plugins and extensions used in the build.

Consider the artifact
`org.project:reusable-test-support:1.0:tests:jar`. With the type handlers above,
maybe surprisingly, a dependency on this very same artifact can be described in two ways:

```xml
<dependency>
  <groupId>org.project</groupId>
  <artifactId>reusable-test-support</artifactId>
  <version>1.0</version>
  <classifier>tests</classifier>
</dependency>
```

and a completely equivalent dependency is:

```xml
<dependency>
  <groupId>org.project</groupId>
  <artifactId>reusable-test-support</artifactId>
  <version>1.0</version>
  <type>test-jar</type>
</dependency>
```

The obvious difference is the presence of `classifier` in the first case,
and the absence of it in the second. However, in the second case, the `type` "test-jar"
implies a classifier of "tests". In both cases, the extension is "jar".
The first uses the default value for this property, while the second infers it from the type.

The first way is more explicit and therefore preferred. In this case the type "test-jar"
serves as an alias for ordinary JARs with the "tests" classifier. If the
type handler carries important extra information such as custom packaging, using `type`
is more appropriate.

Plugins and extensions can define new dependency types. This is usually required for
plugins that introduce a "packaging" (lifecycle mapping) by providing an `ArtifactHandler`
component with a name corresponding to the type name.

