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

An Artifact is "anything" (any file) that can be addressed using its coordinates, and Maven downloads, installs or
deploys for you. Most of them are POMs and JARs but
an artifact can be really anything. A very important thing about artifacts is that they have coordinates,
so they are not "just files", but they are files that are in some way addressable by Maven.

Artifact coordinates, are most often represented as `groupId:artifactId:version`, or GAV in short or when
informally used (please note that Artifact coordinates has more fields, but for brevity we still call the
coordinates "GAV", not "GAVCE"). The artifact coordinates uniquely describe the artifact you are referring to,
but does not tell anything about its source (or origin). It is up to Maven to figure out (or you to tell Maven
how to figure it out).

A word about uniqueness: as stated above, GAV coordinates uniquely identifies artifact, but only **within one repository**.
It is clearly possible (but discouraged) to have multiple repositories with overlapping content (so R1 and R2 both
contain artifact with same GAV). If those files are not-identical (truly, ie. hash wise), it may cause severe
issues without you noticing it. In short, these cases should be avoided.

While Maven internally uses the notion of "artifact" thoroughly (just look at sources!), end users may never hit this term.
That's due the fact, that while for Maven, "everything is artifact" (internally), Maven end users actually speak about
"projects", "parent projects", "dependencies", "build plugins", "reporting plugins", "build extensions" and so on.

## Artifact Properties

The artifacts that Maven (internally) uses has following (for our topic related) properties:

|    Name     |                    Description                    |
|-------------|---------------------------------------------------|
| groupId     | The artifact group                                |
| artifactId  | The artifact id                                   |
| version     | The artifact version (linked w/ baseVersion)      |
| baseVersion | The artifact base version (linked w/ version)     |
| classifier  | The artifact distinguishing classifier (optional) |
| extension   | The artifact extension (default: "jar")           |

One property worth explaining is a bit of special one: `baseVersion` that is actually derived/linked to
`version` (or the other way around, depending on the context): for release artifacts, it holds the same value as
`version`, whereas for snapshot artifacts, it holds the "non-timestamped snapshot version". For example,
for snapshot version "1.0-20220119.164608-1", the `baseVersion` would have the value "1.0-SNAPSHOT".
So, `version` and `baseVersion` are linked, derived from each other, but **they have different values only in
case of snapshots**.

Important note about Artifacts: the fact is an artifact a snapshot or not, should be queried with method
`Artifact#isSnapshot()`.

## But where do I set Artifact extension?

In short, nowhere. Or maybe "you rarely have to". Maven POM (where you declare your project, parent project,
dependencies, plugins and other), maps those elements onto artifact coordinates with some extra logic.

In case of "project" and "parent project" aka POMs (after POM made into effective POM, ie. parent values inherited):

| Artifact Property | Project POM (pom.xml) |  POM Artifact  |
|-------------------|-----------------------|----------------|
| groupId           | `project/groupId`     | -> groupId     |
| artifactId        | `project/artifactId`  | -> artifactId  |
| version           | `project/version`     | -> version     |
| classifier        | -                     | "" (always)    |
| extension         | -                     | `pom` (always) |

In case of "build plugins" and "build extensions", as they are JARs, this is how corresponding elements are mapped
(for build extension change the XML path prefix to `project/build/extensions/extension[x]`):

| Artifact Property |            Plugin in Project POM             | Plugin/Extension Artifact |
|-------------------|----------------------------------------------|---------------------------|
| groupId           | `project/build/plugins/plugin[x]/groupId`    | -> groupId                |
| artifactId        | `project/build/plugins/plugin[x]/artifactId` | -> artifactId             |
| version           | `project/build/plugins/plugin[x]/version`    | -> version                |
| classifier        | -                                            | -> "" (always)            |
| extension         | -                                            | -> `jar` (always)         |

And finally, in case of "dependencies", this is the mapping (no, scope is NOT part of artifact coordinates):

| Artifact Property |            Dependency in Project POM            |            Dependency Artifact            |
|-------------------|-------------------------------------------------|-------------------------------------------|
| groupId           | `project/dependencies/dependency[x]/groupId`    | -> groupId                                |
| artifactId        | `project/dependencies/dependency[x]/artifactId` | -> artifactId                             |
| version           | `project/dependencies/dependency[x]/version`    | -> version                                |
| classifier        | `project/dependencies/dependency[x]/classifier` | -> classifier                             |
| extension         | `project/dependencies/dependency[x]/type`       | -> type handler provided, or same as type |

Here, we need to make a short detour to explain "type" (of a dependency) and how it becomes artifact extension.

Maven for dependencies defines "type", that describes what that dependency is (should it be added to classpath and
many other things). Plugins and extensions may define new types, that is usually a must for plugins introducing
a "packaging" (lifecycle mapping) by providing `ArtifactHandler` components with name corresponding to type name.

Maven Core out of the box [defines following "types" (handled by same named `ArtifactHandler` components)](/ref/current/maven-core/artifact-handlers.html):

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

From table above, we can see that if we define the dependency type as "war", we will hit the "war" handler, that will
result in using the `war` extension (which may not be obvious, as the type and extension we end up with are the same, but internally this
indirection does happen). The "test-jar" is more obvious, as it translates to `jar` extension. Finally, the **any**
last row will be used if none above matches, hence in that case your "type" is used just as "extension", for example
you can write `<type>tar.gz</type>` for dependency, and you will end up with extension `tar.gz` (all this happens
because as there is no artifact handler named "tar.gz" in table above). Still, you should be aware that this table
above may be extended by various plugins and extensions you use in your build!

Also, this has "interesting" consequences, consider for example following Artifact:
`org.project:reusable-test-support:1.0:tests:jar`. With type handlers above, maybe surprisingly, the dependency to
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

Obvious difference is presence of `classifier` in first case, while in second lack of it but presence of `type` "test-jar",
that in the other hand, implies classifier of "tests". In both cases, extension is "jar" (in first it uses the default
value for this property, while in second type defines it).

Note: In this very case, using the first way is somewhat "explicit", and is recommended way. Not so for the
cases when type handler carries some important extra information (like some custom packaging), where using `type`
is more appropriate. Simply put, in this case the type "test-jar" is like an alias for ordinary JARs with "tests"
classifier.

## Summary

In short, this is how various Maven bits like "project", "parent project", "plugin", "extension" and "dependency"
have artifact coordinates mapped from POM elements. Using this knowledge, we can always deduce the artifact coordinate
of these POM elements.
