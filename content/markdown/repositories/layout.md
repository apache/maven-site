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
Due simplicity, we will cover current layout (aka "maven2" or "default"), as since Maven 3.x release, the deprecated
"Maven1 layout" is not supported anymore.

This above implies following: if the repository contains a file that is "not on layout" (does not obey layout 
transformation rules discussed below), that file is "not addressable" by Maven coordinates, you cannot make Maven
to download it using GAV coordinates!

The original premise of layout was simplicity: from historical perspective, a remote repository was expected to be run
by some computer with file storage (where artifacts were laid down) and served by a HTTP server, essentially publishing 
the files on file paths for consumption (mainly for HTTP GET requests). Actually, the reason of layout change between
Maven1 and Maven2 (the today's "default") was exactly that: Maven1 layout was stressing the underlying file system 
way too much, it was not scaling in this setup.

The transformation rule is quite simple for that matter:

| Source coordinate | Transformation                                          | Result example |
|-------------------|---------------------------------------------------------|----------------|
| Group ID          | Replace "." (dot) characters with "/" (slash) character | `org.apache.maven` -> `org/apache/maven` |
| Artifact ID       | none                                                    | `apache-maven` -> `apache-maven` |
| Version           | none                                                    | `3.8.4` -> `3.8.4` |


