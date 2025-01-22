# Maven Metadata

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

Repositories contain metadata (aka "repository metadata") files as well, that enables several "discovery" and "resolution"-like operations
for Maven. These metadata files are **not** Artifacts, and hence, are not addressable by Maven users. They are instead
transparently operated and handled by Maven itself in automatic manner. These files are XML files named as
`maven-metadata.xml` (are deployed with checksums just like artifacts are).

Links:
* [Reference documentation](/ref/current/maven-repository-metadata/)
* [XML Schema](/xsd/repository-metadata-1.1.0.xsd)
* [Modello model](https://gitbox.apache.org/repos/asf?p=maven.git;a=blob_plain;f=maven-repository-metadata/src/main/mdo/metadata.mdo;hb=HEAD)
and its [descriptor documentation](/ref/current/maven-repository-metadata/repository-metadata.html)

We distinguish 3 different kinds of metadata files (using GAV coordinates):
* G level metadata
* A level metadata
* V level metadata

These metadata XML files share same XML schema (model), and the reason of that is simple: one XML file may carry multiple
kinds of metadata (for different Artifacts)! Consider these two artifacts: `org.foo:bar:1.0` and `org.foo.bar:baz:1.0`.
With default layout, the repository path `org/foo/bar` is once A level for first, and G level for second artifact.
Note: this is extreme example, and such artifact naming should be avoided!

## The G Level Metadata

|            What            |              How              |
|----------------------------|-------------------------------|
| Location                   | Path corresponding to groupId |
| Repositories containing it | Release and Snapshots         |
| Artifacts related to them  | Only `maven-plugin` packaged  |

The group level metadata (found in directory of groupId) serves purpose only in case of Maven Plugins
(POM packaging: maven-plugin). This metadata contains mapping of `plugin prefix` to `artifactId`, and the XML bit
considered for this kind of metadata is `metadata/plugins/plugin[]` path only.

Example: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-metadata.xml

When user uses some plugin prefix on CLI, Maven will go through registered `pluginGroups` groupIds, download their
G level metadata, and look for prefix. If found, Maven has obtained G (from pluginGroups) and A (from metadata)
coordinates of plugin, and will go for given version, or attempt to discover "latest" version.

## The A Level Metadata

|            What            |               How                |
|----------------------------|----------------------------------|
| Location                   | Path corresponding to artifactId |
| Repositories containing it | Release and Snapshots            |
| Artifacts related to them  | All                              |

The artifactId level metadata (found in directory of artifactId) serves purpose of version discovery. This metadata
contains list of versions of given GA coordinates. The XML bit considered for this kind of metadata are only:
* `metadata/groupId`
* `metadata/artifactId`
* `metadata/versioning/latest`
* `metadata/versioning/release`
* `metadata/versioning/versions/*`
* `metadata/versioning/lastUpdated`

Example: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-jar-plugin/maven-metadata.xml

## The V Level Metadata

|            What            |                How                |
|----------------------------|-----------------------------------|
| Location                   | Path corresponding to baseVersion |
| Repositories containing it | Snapshots only                    |
| Artifacts related to them  | Only Snapshots                    |

Exists only in Snapshot Remote repositories. The version level metadata (found in directory of baseVersion) serves the
purpose of snapshot timestamped version resolution. This metadata contains mapping of all artifacts in this baseVersion
directory with corresponding timestamps. The XML bit considered for this kind of metadata are only:
* `metadata/groupId`
* `metadata/artifactId`
* `metadata/versioning/snapshot/*`
* `metadata/versioning/snapshotVersions/*`

Example: https://repository.apache.org/content/repositories/snapshots/org/apache/maven/plugins/maven-jar-plugin/3.3.0-SNAPSHOT/maven-metadata.xml
