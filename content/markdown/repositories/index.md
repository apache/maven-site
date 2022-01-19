# Maven Repositories

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

Apache Maven uses repositories to store artifacts. Your dependencies are being downloaded from repositories,
and your artifacts are being stored (installed, uploaded) into repositories as well. This is one of the 
fundamental concepts of Maven since its inception: Maven command line tool and Maven Repositories were mold together
and developed since the beginning of Maven project itself.

As you may know, Maven addresses artifacts using coordinates, that are most often represented as 
`groupId:artifactId:version`, or GAV in short or when informally used (please note that Artifact coordinates has 
more fields, but for brevity we still call the coordinates "GAV", not "GAVCE"). The artifact coordinate uniquely 
describe the artifact you are referring to, but does not tell anything about its source (or origin). It is up to 
Maven to figure out (or you to tell Maven how to figure it out).

While Maven internally uses notion of "artifact" thoroughly (just look at sources!), end user may never hit this name.
That's due the fact, that while for Maven, "everything is artifact" (internally), the end users actually speak about
"projects", "parent projects", "dependencies", "plugins", "extensions" and so on.

## Artifact properties

The artifacts that Maven (internally) uses has following (among many others, but for our topic related) properties:

| Name       | Description |
|------------|-------------|
| groupId    | The artifact group name |
| artifactId | The artifact name |
| version    | The artifact version |
| classifier | The artifact distinguishing classifier |
| extension  | The artifact extension |

And some more, a bit of special ones:

| Name | Description                                                                                                                                                                                                          |
|------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| type | The "type" of the artifact, implies extension.                                                                                                                                                                       |
| baseVersion | Is actually derived from version (or other way around, depends on context): for release artifacts same as version, for snapshot artifacts this is always "non stamped snapshot version", for example "1.0-SNAPSHOT". |

