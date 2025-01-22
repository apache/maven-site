# Maven Repository Layout

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

The layout is responsible for translating the [artifact coordinates](artifacts.md) into generic paths, that is later used to construct some
URI (file path, URL, it depends on context). Obviously, since Maven inception in 2002 the layout evolved as well.
For simplicity, we will cover current layout (aka "maven2" or "default"), as since Maven 3.x release, the deprecated
"Maven1 layout" (aka "legacy") is not supported anymore.

This above implies following: if the repository contains a file that is "not on layout" (does not obey layout
transformation rules discussed below), that file is "not addressable" by Maven coordinates, you cannot address that file
in Maven nor make it to download it using GAV coordinates!

The original premise of layout was simplicity: from historical perspective, a remote repository was expected to be run
by some computer with file storage (where artifacts were laid down) and served by a HTTP server, essentially publishing
the files on file paths for consumption (mainly for HTTP GET requests).

The transformation rule is quite simple for that matter: consider artifact properties below:

|    Name     |                     Transformation                      |              Result example              |
|-------------|---------------------------------------------------------|------------------------------------------|
| groupId     | Replace "." (dot) characters with "/" (slash) character | `org.apache.maven` -> `org/apache/maven` |
| artifactId  | none                                                    | `apache-maven`                           |
| version     | none                                                    | `3.8.4`                                  |
| baseVersion | none                                                    | (in this example same as version)        |
| classifier  | none                                                    | `bin`                                    |
| extension   | none                                                    | `tar.gz`                                 |

And using these properties transformed as above, we can construct following path (if classifier not present):

```
${groupId as directory}/${artifactId}/${baseVersion}/${artifactId}-${version}.${extension}
```

or if classifier present:

```
${groupId as directory}/${artifactId}/${baseVersion}/${artifactId}-${version}-${classifier}.${extension}
```

So the example artifact above noted as GAV:

```
org.apache.maven:apache-maven:3.8.4:bin:tar.gz
```

is translated to path as:

```
org/apache/maven/apache-maven/3.8.4/apache-maven-3.8.4-bin.tar.gz
```

And that is it! By applying this "algorithm" above to ANY Artifact we can build up the path segment that
artifact is expected to be.

Important note: in case of locally installed artifacts (those you built locally and invoked `mvn install`) will use
Artifact baseVersion property instead of version. The full-blown "timestamped" versions are used only in
remote repositories, when the artifact is deployed.
