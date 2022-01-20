## Maven2 Repository Layout
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

Maven2 repository layout format is the default layout used since Maven 2, superceding old Maven 1 layout:

```
Repository root
`-- ${groupId as directory}
    |-- maven-medatada.xml
    |--                   .${checksums}
    `-- ${artifactId}
        |-- maven-medatada.xml
        |--                   .${checksums}
        `-- ${version}
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

## SNAPSHOT

In case of a SNAPSHOT version, version directory uses base version, i.e. version ending in `-SNAPSHOT`, for example `3.8.4-SNAPSHOT`.

For artifact files, there are 2 options available:

1. unique SNAPSHOT artifacts, using the same base version,
2. multiple SNAPSHOT artifacts for one base version, each deployment will have an effective version where `SNAPSHOT` is replaced with `YYYYMMDD.HHMMSS-${counter}`.

## Protocol

Repository can be accessed through many file-oriented protocols, both for read and write: most classical are `file://`, `http://` and `https://` (`GET` and `PUT`).
Older (now unused) protocols are FTP(S), SCP, SSH, ...
Older (now generally unused) protocols are FTP(S), SCP, SSH, ...

## Extensions and Classifiers

Obviously, `${groupId}`, `${artifactId}` and `${version}` are defined in `pom.xml`. But what about `${classifier}` and `${extension}`, how are they defined?

At artifact publication time, extensions and classifiers are defined by plugins that create the artifacts and attach them for publication.
Some plugins provide configuration parameters to be able to override some default values. For example, [Maven JAR Plugin's `jar:jar` goal](/plugins/maven-jar-plugin/jar-mojo.html)
by default produces an artifact with empty classifier and `jar` extension, and the `classifier` goal parameter can be used to define another classifier (no parameter to override extension).

At artifact usage, usage happens through dependencies definition. Defining [`<dependency>`'s `<type>`](/ref/current/maven-model/maven.html#class_dependency) is what defines the initial extensions and classifier (by default, Maven considers a dependency type to be `jar`): see [default artifact handlers](/ref/current/maven-core/artifact-handlers.html) to see what default types exist and how they are bound to extensions and classifiers values. After that, you can also define `<dependency>`'s `<classifier>` to override default classifier defined by the type.
