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

An artifact is a file (more precisely, a sequence of bytes) that can be addressed using its coordinates
and which Maven downloads, installs, or deploys for you. Most artifacts are POMs and JARs, but
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

The artifacts that Maven uses have the following coordinate properties:

|    Name     |                   Description                   |
|-------------|-------------------------------------------------|
| groupId     | The project group                               |
| artifactId  | The artifact ID                                 |
| version     | The artifact version (linked with baseVersion)  |
| baseVersion | The artifact base version (linked with version) |
| classifier  | The artifact classifier (optional)              |
| extension   | The artifact extension (default: "jar")         |

One property worth explaining is a bit of special one: `baseVersion` is derived from/linked to
`version` (or the other way around, depending on the context). For release artifacts, it has the same value as
`version`, whereas for snapshot artifacts, it has the "non-timestamped snapshot version". For example,
snapshot version "1.0-20220119.164608-1" has the `baseVersion` "1.0-SNAPSHOT".
So, `version` and `baseVersion` are linked, derived from each other, but **they have different values only in the
case of snapshots**.

For POM artifacts that contain a project's pom.xml file, the artifact coordinates are set
as follows:

| Artifact Coordinate |     POM element      | Coordinate Value  |
|---------------------|----------------------|-------------------|
| groupId             | `project/groupId`    | -> group ID       |
| artifactId          | `project/artifactId` | -> artifact ID    |
| version             | `project/version`    | -> version string |
| classifier          | -                    | "" (always)       |
| extension           | -                    | "pom" (always)    |

Coordinate values are computed after the POM is made into an effective POM;
that is, after parent values have been inherited.

Build plugin and build extension artifacts are JARs. For build plugins,
this is how the corresponding coordinates are computed from a `plugin` element:

| Artifact Coordinate |                POM element                | Coordinate Value  |
|---------------------|-------------------------------------------|-------------------|
| groupId             | `project/build/plugins/plugin/groupId`    | -> group ID       |
| artifactId          | `project/build/plugins/plugin/artifactId` | -> artifact ID    |
| version             | `project/build/plugins/plugin/version`    | -> version string |
| classifier          | -                                         | -> "" (always)    |
| extension           | -                                         | -> "jar" (always) |

Build extensions are similarly computed from an `extension` element:

| Artifact Coordinate |                   POM element                   | Coordinate Value  |
|---------------------|-------------------------------------------------|-------------------|
| groupId             | `project/build/extensions/extension/groupId`    | -> group ID       |
| artifactId          | `project/build/extensions/extension/artifactId` | -> artifact ID    |
| version             | `project/build/extensions/extension/version`    | -> version string |
| classifier          | -                                               | -> "" (always)    |
| extension           | -                                               | -> "jar" (always) |

Finally, in the case of "dependencies", this is how artifact coordinates are calculated
from a `dependency` element:

| Artifact Coordinate |                 POM element                  |             Coordinate Value              |
|---------------------|----------------------------------------------|-------------------------------------------|
| groupId             | `project/dependencies/dependency/groupId`    | -> group ID                               |
| artifactId          | `project/dependencies/dependency/artifactId` | -> artifact ID                            |
| version             | `project/dependencies/dependency/version`    | -> version string                         |
| classifier          | `project/dependencies/dependency/classifier` | -> classifier, or type handler provided   |
| extension           | `project/dependencies/dependency/type`       | -> type handler provided, or same as type |

This also applies when the `dependency` element is a child of a `dependencyManagement` element.

Notice that there is no `extension` element. Instead there is a `type` element which is
used to derive the extension and sometimes the classifier.
Out of the box, Maven Core defines 11 "types" [(handled by the same named `ArtifactHandler` components)](/ref/current/maven-core/artifact-handlers.html):

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

From the table above, you can see that if the dependency type is "war",
the extension is also `war` and the classifier is the value of the
`classifier` element (if present) or the empty string if the `classifier` element
is not present. If the type is "test-jar", the extension is
"jar" and the classifier is "tests". If the type is not one of these 11 names, then the
value of the "type" is used as the "extension". For example, if the `type` element
is `<type>tar.gz</type>`, the extension will be `tar.gz`, and the classifier will
be set by the `classifier` element. This
table may be extended by plugins and extensions used in the build.

<!-- TODO what if an explicit classifier element conflicts with the
classifier inferred from the type? Which wins? -->

This has "interesting" consequences. Consider the artifact
`org.project:reusable-test-support:1.0:tests:jar`. Maybe surprisingly,
a dependency on this artifact can be described in two ways:

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

The obvious difference is that the first example has the `<classifier>tests</classifier>`,
while the second has the `<type>test-jar</type>`. The `type` "test-jar"
implies a classifier of "tests". In both cases, the extension is "jar".
The first uses the default value for the extension, while the second
derives it from the type.

Note: In this case, the first way is somewhat "explicit", and is
recommended. When the type handler carries important
extra information such as custom packaging, using `type` is more
appropriate. Simply put, in this example the type "test-jar" is like an
alias for ordinary JARs with the "tests" classifier.

## Summary

In short, this is how various Maven bits like "project", "parent
project", "plugin", "extension", and "dependency" derive artifact
coordinates from POM elements.
