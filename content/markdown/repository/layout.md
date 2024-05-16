# Maven2 Repository Layout
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

Maven2 repository layout format is the default layout used since Maven 2 (see [Repository Layout- Final](https://cwiki.apache.org/confluence/display/MAVENOLD/Repository+Layout+-+Final) in [Maven 2.0 Design Documents](https://cwiki.apache.org/confluence/display/MAVENOLD/Maven+2.0+Design+Documents)), superceding old Maven 1 layout:

```
Repository root
|-- archetype-catalog.xml
`-- ${groupId as directory}/
    |-- maven-metadata.xml
    |--                   .${checksums}
    `-- ${artifactId}/
        |-- maven-metadata.xml
        |--                   .${checksums}
        `-- ${version}/
            |-- ${artifactId}-${version}.pom
            |--                             .asc
            |--                             .${checksums}
            |-- ${artifactId}-${version}.${extension}
            |--                                     .asc
            |--                                     .${checksums}
            |-- ${artifactId}-${version}-${classifier}.${extension}
            |--                                                    .asc
            `--                                                    .${checksums}
```

where:

- `${groupId as directory}` is the groupId with `.` replaced by `/`, for example `org/apache/maven`,
- `${artifactId}` is the artifactId,
- `${version}` is the version, with some additional possibilities on a SNAPSHOT, see SNAPSHOT paragraph,
- `${extension}` is the file extension, for example `zip` or `tar.gz`,
- `${classifier}` is the artifact classifier (when available),
- `${checksums}` is a list of checksums algorithms, `md5` and `sha1` by default.

`maven-metadata.xml` file format is defined in [Maven Repository Metadata Model](/ref/current/maven-repository-metadata/).

`.asc` file is optional (may be required in some repositories, like Central Repository) and is a PGP detached signature file.

Obviously, `${groupId}`, `${artifactId}` and `${version}` are defined in `pom.xml`. But what about `${classifier}` and `${extension}`, how are they defined?

Classifier and extension definition is completely different at artifact *publication* and *usage* times:

- **At artifact publication time**: Extension and classifier are defined by plugins that create the artifacts and attach them for publication.<br />
Some plugins provide configuration parameters to be able to override default values.
For example, [Maven JAR Plugin's `jar:jar` goal](/plugins/maven-jar-plugin/jar-mojo.html) produces by default an artifact with `jar` extension and empty classifier.
The `classifier` goal parameter can be used to define a classifier (there is no parameter to override extension).

- **At artifact usage time**: Extension and classifier are defined by [`<dependency>`'s `<type>` and `<classifier>`](/ref/current/maven-model/maven.html#class_dependency) definition in `pom.xml`:
  - `<type>` (`jar` by default) defines the extension and default classifier: <br />
    see [default artifact handlers](/ref/current/maven-core/artifact-handlers.html) to see default types and corresponding extension and default classifier. <br />
  - `<classifier>` is optional, to override default classifier defined by the dependency type.

## SNAPSHOT

In case of a SNAPSHOT version, version directory uses base version, i.e. version ending in `-SNAPSHOT`, for example `3.8.4-SNAPSHOT`.

For artifact files, there are 2 options available:

1. unique SNAPSHOT artifacts, using the same base version,
2. multiple SNAPSHOT artifacts for one base version, each deployment will have an effective version where `SNAPSHOT` is replaced with `YYYYMMDD.HHMMSS-${counter}`.

## Protocol

Repository can be accessed through many file-oriented protocols, both for read and write: most classical are `file://`, `http://` and `https://` (`GET` and `PUT`).
Older (now generally unused) protocols are FTP(S), SCP, SSH, ...

HTTP/HTTPS protocol have 2 specific characteristics:

1. there is no directory listing feature: Maven does not need to list files in a directory, a remote directory does have not provide any directory listing feature,

2. [HTTP/1.1 Reason-Phrase](https://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html) is used to provide extended message when artifact access is rejected by remote repository.
This usage of Reason-Phrase is nowadays legacy and is removed in HTTP/2, [MNG-6795](https://issues.apache.org/jira/browse/MNG-6795) is open to create a replacement.

## Archetype Catalog

`archetype-catalog.xml` file at root directory is a [catalog of all Maven Archetypes](/archetype/archetype-models/archetype-catalog/archetype-catalog.html) proposed in the repository.

## Additional Content

### Index

[`.index`](https://repo.maven.apache.org/maven2/.index/) directory at root directory is an [index](/maven-indexer/indexer-core/) of the content, done by [Maven Indexer](/maven-indexer/).

If contains index files in 2 flavours:

- full index: `nexus-maven-repository-index.gz`
- incremental index: `nexus-maven-repository-index.<n>.gz` + `nexus-maven-repository-index.properties`

### Metadata

[`.meta`](https://repo.maven.apache.org/maven2/.meta/) directory at root directory contains a few metadata files:

- `prefixes.txt`
- `repository-metadata.xml`
