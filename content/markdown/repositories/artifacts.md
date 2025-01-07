# Maven Artifacts

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

An artifact is a file (more precisely, a sequence of bytes) that can be addressed using its coordinates which Maven downloads, installs, or
deploys for you. Most artifacts are POMs and JARs, but
an artifact can be really any file. A very important thing about artifacts is that they have coordinates,
so they are not "just files", but they are files that are in some way addressable by Maven.

Artifact coordinates are most often represented as `groupId:artifactId:version`, or GAV in short.
(Please note that artifact coordinates have more fields, but for brevity we still call the
coordinates "GAV", not "GAVCE"). The artifact coordinates uniquely identify an artifact,
but do not specify anything about its source. It is up to Maven to figure out (or you to tell Maven
how to figure out) how and from where to retrieve the artifact.

A word about uniqueness: as stated above, GAV coordinates uniquely identify an artifact, but only **within one repository**.
Different repositories can contain artifacts with the same GAV. (This is normal with
mirror repositories.) If those files are not identical, it can cause severe
issues without you noticing it. In short, these cases should be avoided.

## Artifact Properties

The artifacts that Maven uses internally have following properties:

|    Name     |                   Description                   |
|-------------|-------------------------------------------------|
| groupId     | The artifact group                              |
| artifactId  | The artifact id                                 |
| version     | The artifact version (linked with baseVersion)  |
| baseVersion | The artifact base version (linked with version) |
| classifier  | The artifact classifier (optional)              |
| extension   | The artifact extension (default: "jar")         |

One property worth explaining is a bit of special one: `baseVersion` is derived from/linked to
`version` (or the other way around, depending on the context). For release artifacts, it has the same value as
`version`, whereas for snapshot artifacts, it has the "non-timestamped snapshot version". For example,
for snapshot version "1.0-20220119.164608-1", the `baseVersion` would have the value "1.0-SNAPSHOT".
So, `version` and `baseVersion` are linked, derived from each other, but **they have different values only in the
case of snapshots**.

## But where do I set the Artifact extension?

In short, nowhere. Or maybe "you rarely have to". The Maven POM (where you declare your project, parent project,
dependencies, plugins and other items), maps those elements onto artifact extensions with some extra logic.

In case of "project" and "parent project" POMs (after the POM is made into an effective POM, that is, parent values have been inherited):

| Artifact Property | Project POM (pom.xml) |  POM Artifact  |
|-------------------|-----------------------|----------------|
| groupId           | `project/groupId`     | -> groupId     |
| artifactId        | `project/artifactId`  | -> artifactId  |
| version           | `project/version`     | -> version     |
| classifier        | -                     | "" (always)    |
| extension         | -                     | `pom` (always) |

In the case of "build plugins" and "build extensions", as they are JARs, this is how corresponding elements are mapped
(for build extension change the XML path prefix to `project/build/extensions/extension[x]`):

| Artifact Property |            Plugin in Project POM             | Plugin/Extension Artifact |
|-------------------|----------------------------------------------|---------------------------|
| groupId           | `project/build/plugins/plugin[x]/groupId`    | -> groupId                |
| artifactId        | `project/build/plugins/plugin[x]/artifactId` | -> artifactId             |
| version           | `project/build/plugins/plugin[x]/version`    | -> version                |
| classifier        | -                                            | -> "" (always)            |
| extension         | -                                            | -> `jar` (always)         |

And finally, in the case of "dependencies", this is the mapping (no, scope is NOT part of artifact coordinates):

| Artifact Property |            Dependency in Project POM            |            Dependency Artifact            |
|-------------------|-------------------------------------------------|-------------------------------------------|
| groupId           | `project/dependencies/dependency[x]/groupId`    | -> groupId                                |
| artifactId        | `project/dependencies/dependency[x]/artifactId` | -> artifactId                             |
| version           | `project/dependencies/dependency[x]/version`    | -> version                                |
| classifier        | `project/dependencies/dependency[x]/classifier` | -> classifier                             |
| extension         | `project/dependencies/dependency[x]/type`       | -> type handler provided, or same as type |

Here, we need to make a short detour to explain "dependency type" and how it becomes an artifact extension.

A dependency type describes what that dependency is. For example, should it be added to classpath? Plugins and extensions may define new types. This is usually requiredfor plugins introducing
a "packaging" (lifecycle mapping) by providing `ArtifactHandler` components with a name corresponding to type name.

Out of the box Maven Core defines the [following "types" (handled by the same named `ArtifactHandler` components)](/ref/current/maven-core/artifact-handlers.html):

|  Type Name   | Extension |  Classifier  |
|--------------|-----------|--------------|
| pom          | `pom`     |              |
| jar          | `jar`     |              |
| maven-plugin | `jar`     |              |
| ear          | `ear`     |              |
| ejb          | `jar`     |              |
| ejb-client   | `jar`     | `ejb-client` |
| javadoc      | `jar`     | `javadoc`    |
| java-source  | `jar`     | `sources`    |
| rar          | `rar`     |              |
| test-jar     | `jar`     | `tests`      |
| war          | `war`     |              |
| **any**      | any       |              |

From the table above, we can see that if we define the dependency type as "war", we will hit the "war" handler. That will
result in using the `war` extension (which may not be obvious, as the type and extension we end up with are the same, but internally this
indirection does happen). The "test-jar" is more obvious, as it translates to `jar` extension. Finally, the **any**
last row will be used if none of the above match. Hence in that case the "type" is used as the "extension". For example.
if the dependency type is `<type>tar.gz</type>`, the extension will also be `tar.gz`.
This table may be extended by plugins and extensions used in the build.

Also, this has "interesting" consequences. Consider the artifact
`org.project:reusable-test-support:1.0:tests:jar`. With the type handlers above, maybe surprisingly, the dependency to
this very same artifact can be described in two ways:

```xml
<dependency>
  <groupId>org.project</groupId>
  <artifactId>reusable-test-support</artifactId>
  <version>1.0</version>
  <classifier>tests</classifier>
</dependency>
```

and the equivalent dependency would be:

```xml
<dependency>
  <groupId>org.project</groupId>
  <artifactId>reusable-test-support</artifactId>
  <version>1.0</version>
  <type>test-jar</type>
</dependency>
```

The obvious difference is presence of `classifier` in first case, while in second lack of it but presence of `type` "test-jar",
that in the other hand, implies a classifier of "tests". In both cases, the extension is "jar". The first it uses the default value for this property, while the second type defines it.

Note: In this very case, the first way is somewhat "explicit", and is recommended. Not so for the
cases when type handler carries some important extra information (like some custom packaging), where using `type`
is more appropriate. Simply put, in this case the type "test-jar" is like an alias for ordinary JARs with the "tests"
classifier.

## Summary

In short, this is how various Maven bits like "project", "parent project", "plugin", "extension" and "dependency"
have artifact coordinates mapped from POM elements. Using this knowledge, we can always deduce the artifact coordinate
of these POM elements.
