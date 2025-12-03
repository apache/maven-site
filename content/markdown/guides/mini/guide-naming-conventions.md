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

<head>
   <title>Naming conventions of Maven coordinates (groupId, artifactId, and version)</title>
</head>

# Naming convention of Maven coordinates

So that Maven can identify and utilise any artifact (e.g. a `.jar` file), every artifact must be identifiable through a
unique combination of three identifiers.
This combination is called the "[Maven coordinates][4]".
Maven coordinates consist of a project group identifier named `groupId`, an artifact identifier named `artifactId`, and
the version identifier named `version`.

This document defines the naming conventions of Maven coordinates.

You should follow this convention whenever you create a new artifact.

## Project group identifier

When projects of the same organization are topically related, we say they belong to the same "project group".
The `groupId` uniquely identifies a project group across all other groups.
Each `groupId` should follow [Java's package name rules][1].
This means it starts with a reversed domain name you control.

Examples

```
org.apache.maven
org.apache.commons
com.google.guava
```

There are many legacy projects that do not follow this convention and instead use single word group IDs.
However, it will be difficult to get a new single word group ID approved for inclusion in the Maven Central repository.

You can create as many subgroups as you want.
A good way to determine the granularity of the `groupId` is to look at the project group's structure.
If there are multiple projects of the same topic or type, e.g. plugins, those may be grouped in a subgroup.
Each subgroup should append a new identifier to the parent's `groupId`.

Example

```
// Parent project group
org.apache.maven

// Subgroups
org.apache.maven.plugins
org.apache.maven.reporting
```

## Artifact identifier

The `artifactId` is the name of the artifact.
The identifiers should only consist of *lowercase* letters, digits, and hyphens.

Examples

```
commons-math
maven-clean-plugin
```

## Version identifier

We recommend that the `version` follow the rules of [Semantic Versioning 1.0.0][2].
It should start with the major version, followed by the minor version and the patch version.
All three are numeric, separated by a dot.

Examples

```
1.0.0
2.3.2
3.5.42
```

You can add labels for pre-releases or build metadata after the patch version.
Avoid using dates in those labels, because they are usually associated with unstable versions.

Examples

```
// Pre-releases
1.0.0-beta
1.0.0-M1
1.0.0-rc2

// Build metadata
1.2.3+dfc0c87
2.3.4+15433
```

### Unstable versions (SNAPSHOT)

`SNAPSHOT`s are artifacts built in between releases.
They may be built from a particular commit or from code that isn't even committed to the source repository.
They are a snapshot of the project at a particular point in time, generally used for testing.
Unlike release versions, snapshot artifacts can and do change over time.

Usually the snapshot has the version of the next anticipated release followed by `-SNAPSHOT`, e.g. `1.0.1-SNAPSHOT`.

Maven treats artifacts with such versions in a special way during deployment and stores them in the `snapshotRepository`
if one is defined in the [POM file][3].

[1]:https://docs.oracle.com/javase/specs/jls/se21/html/jls-6.html#d5e8762
[2]:https://semver.org/spec/v1.0.0.html
[3]:/pom.html#Repository
[4]:/pom.html#Maven_Coordinates

