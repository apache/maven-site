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

The layout is responsible to translate the artifact coordinates into generic paths, that is later used to construct some
URI (file path, URL, it depends on context). Obviously, since Maven;s inception in 2002 the layout evolved as well.
For simplicity, we will cover current layout (aka "maven2", "default"), as since Maven 3.x release, the deprecated
"Maven1 layout" (aka "legacy") is not supported anymore.

This above implies following: if the repository contains a file that is "not on layout" (does not obey layout 
transformation rules discussed below), that file is "not addressable" by Maven coordinates, you cannot make Maven
to download it using GAV coordinates!

The original premise of layout was simplicity: from historical perspective, a remote repository was expected to be run
by some computer with file storage (where artifacts were laid down) and served by a HTTP server, essentially publishing 
the files on file paths for consumption (mainly for HTTP GET requests). Actually, the reason of layout change between
Maven1 and current Maven was exactly that: Maven1 layout was stressing the underlying file system way too much, it 
was not scaling in this setup.

The transformation rule is quite simple for that matter: consider artifact properties below:

| Name       | Transformation                                          | Result example                           |
|------------|---------------------------------------------------------|------------------------------------------|
| groupId    | Replace "." (dot) characters with "/" (slash) character | `org.apache.maven` -> `org/apache/maven` |
| artifactId | none                                                    | `apache-maven` -> `apache-maven`         |
| version    | none                                                    | `3.8.4` -> `3.8.4`                       |
| classifier | none                                                    | `bin` -> `bin`                           |
| extension  | none                                                    | `tar.gz` -> `tar.gz`                     |

And using these properties transformed as above we can construct following path (if classifier not present):

```
${groupId}/${artifactId}/${version}/${artifactId}-${version}.${extension}
```

or if classifier present:

```
${groupId}/${artifactId}/${version}/${artifactId}-${version}-${classifier}.${extension}
```

So the example artifact above:

```
org.apache.maven:apache-maven:3.8.4:bin:tar.gz
```

is translated to path:

```
org/apache/maven/apache-maven/3.8.4/apache-maven-3.8.4-bin.tar.gz
```

And that is it! By applying this "algorithm" above to ANY Artifact we can, we can build up the path segment that
artifact is expected to be.

Important note: in case of locally installed artifacts (those you built locally and invoked `mvn install`) will use
Artifact baseVersion property instead of version. The full-blown "timestamped" versions are used only in
remote repositories, when the artifact is deployed.

# A word about Maven1 layout

Maven1 aka "legacy" layout **is unsupported since Maven 3.x** (Maven 2.x supported it to ease transition from 1.x to 
2.x), but is still worth a few words.

The Maven1 layout was not applying groupId transformation (hence the path, if it had dots, still contained it), and
used "type" in plural as second level directory. This is the reason why it caused FS stress, as these directories
ended up containing a LOT of files (in case of many versions existed).

| Name       | Transformation                        | Result example                           |
|------------|---------------------------------------|------------------------------------------|
| groupId    | none                                  | `org.apache.maven` -> `org.apache.maven` |
| artifactId | none                                  | `apache-maven` -> `apache-maven`         |
| version    | none                                  | `3.8.4` -> `3.8.4`                       |
| extension  | none                                  | `pom` -> `pom`                           |
| type       | derived by extension by appending 's' | `pom` -> `poms`                          |

And using these properties transformed as above we can construct following path:

```
${groupId}/${type}/${artifactId}-${version}.${extension}
```

So the example artifact above:

```
org.apache.maven:apache-maven:3.8.4:pom
```

is translated to path:

```
org.apache.maven/poms/apache-maven-3.8.4.pom
```

Easy to spot, that `org.apache.maven/poms` directory will become a file system issue, as that directory
may potentially contain way too much files.