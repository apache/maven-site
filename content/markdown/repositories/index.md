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

Apache Maven uses repositories to store build artifacts. Your dependencies are being downloaded from repositories,
and your built artifacts are being stored (installed, uploaded) into repositories as well. This is one of the 
fundamental concept of Maven since its inception: Maven command line tool and Maven Repositories were mold together
and developed since inception of Maven project itself.

Maven addresses artifacts using coordinates, that are most often represented as `groupId`:`artifactId`:`version`, 
or GAV in short (or informally). The artifact coordinates uniquely describes the artifact you are referring to, but
does not tells anything about its source (or origin). It is up to Maven to figure out (or you to tell Maven how
to figure it out).

Maven in general operates with one local repository and one or more remote repositories.