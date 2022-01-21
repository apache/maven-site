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

Artifact is "anything" that Maven downloads, installs or deploys for you. Most of them are POMs and JARs but 
the artifact can be really anything. A very important thing about artifacts is that they have coordinates,
so they are not "just files", but they are files that are in some way addressable by Maven.

Artifact coordinates, are most often represented as `groupId:artifactId:version`, or GAV in short or when 
informally used (please note that Artifact coordinates has more fields, but for brevity we still call the 
coordinates "GAV", not "GAVCE"). The artifact coordinate uniquely describe the artifact you are referring to, 
but does not tell anything about its source (or origin). It is up to Maven to figure out (or you to tell Maven 
how to figure it out).

While Maven internally uses notion of "artifact" thoroughly (just look at sources!), end user may never hit this name.
That's due the fact, that while for Maven, "everything is artifact" (internally), Maven end users actually speak about
"projects", "parent projects", "dependencies", "build plugins", "build extensions" and so on.

## Artifact properties

The artifacts that Maven (internally) uses has following (for our topic related) properties:

| Name       | Description                                       |
|------------|---------------------------------------------------|
| groupId    | The artifact group                                |
| artifactId | The artifact name                                 |
| version    | The artifact version                              |
| classifier | The artifact distinguishing classifier (optional) |
| extension  | The artifact extension                            |

Some more properties worth mention, is a bit of special one: `baseVersion` that is actually derived from version 
(or other way around, depends on context): for release artifacts holds same value as `version`, for snapshot artifacts 
holds "non-timestamped snapshot version". For example, for snapshot `version` "1.0-20220119.164608-1" value of the 
`baseVersion` would have value "1.0-SNAPSHOT". So, `version` and `baseVersion` are linked, derived from each other, 
but they have different values only in case of snapshots.

## But where do I set Artifact extension?

In short, nowhere. Or maybe "you rarely have to". Maven POM (where you declare your project, parent project,
dependencies, plugins and other), maps those elements onto artifact coordinates with some extra logic. In case
of "project" and "parent project" aka POMs:

| Project    | Artifact      |
|------------|---------------|
| groupId    | -> groupId    |
| artifactId | -> artifactId |
| version    | -> version    |
| classifier | ->            |
| extension  | -> `pom`      |

In case of "build plugins" and "build extensions", as they are JARs, this is how corresponding elements are mapped:

| Plugin     | Artifact      |
|------------|---------------|
| groupId    | -> groupId    |
| artifactId | -> artifactId |
| version    | -> version    |
| classifier | ->            |
| extension  | -> `jar`      |

And finally, in case of "dependencies", this is the mapping (no, scope is NOT part of artifact coordinates, is used in
dependency resolution only):

| Dependency | Artifact                                  |
|------------|-------------------------------------------|
| groupId    | -> groupId                                |
| artifactId | -> artifactId                             |
| version    | -> version                                |
| classifier | -> classifier                             |
| type       | -> type handler provided, or same as type |

Here, we need to make a short detour to explain "type" (of a dependency) and how it becomes artifact extension.

Maven for dependencies defines "type", that describes what that dependency is (should it be added to classpath and
many other things). Plugins and extensions may define new types, that is usually a must for plugins introducing
a "packaging" (lifecycle mapping) by providing ArtifactHandlers components with name corresponding to type name.

Maven Core out of the box defines following "types" (ArtifactHandlers):

| Name         | Extension | Classifier   |
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

So, from table above, we can see that if we define dependency type as "war", we will hit the "war" handler, that will
result in the `war` extension (may not be obvious, as type and extension we end up is same, but internally this 
indirection does happen). More obvious is type "test-jar", where we end up with extension `jar`. Finally, the **any** 
last row will be used if none above matches, hence in that case your "type" is used just as "extension", for example 
you can write `<type>tar.gz</type>` for dependency, and you will end up with extension `tar.gz` (all this happens 
because as there is no artifact handler named "tar.gz" in table above). Still, you should be aware that this table 
above may be extended by various plugins and extensions you use in your build!

In short, this is how various Maven bits like "project", "parent project", "plugin", "extension" and "dependency"
have artifact coordinates mapped from POM elements. Using this knowledge, we can always deduce the artifact coordinate 
of these POM elements.
