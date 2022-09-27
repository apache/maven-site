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
and artifacts you build are being stored (installed, uploaded) into repositories as well. This is one of the 
fundamental concepts of Maven since its inception: Maven command line tool and Maven Repositories were mold together
and developed since the beginning of Maven project itself.

<p align="center">
<img src="../repository/maven-repositories.png" border="0" usemap="#map" />

<map name="map">
  <area shape="rect" coords="596,326,666,363" alt="JBoss"                         href="http://repository.jboss.org/maven2/" />
  <area shape="rect" coords="0,184,460,421"   alt="central (default)"             href="../repository/index.html" />
  <area shape="rect" coords="187,593,277,629" alt="Maven"                         href="/what-is-maven.html" />
  <area shape="rect" coords="280,628,403,649" alt="local repository"              href="/guides/mini/guide-configuring-maven.html" />
  <area shape="rect" coords="364,498,570,534" alt="Repository Manager"            href="/repository-management.html" />
  <area shape="rect" coords="303,545,451,584" alt="settings.xml &lt;mirrorOf&gt;" href="/guides/mini/guide-mirror-settings.html" />
  <area shape="rect" coords="407,589,628,653" alt="pom.xml &lt;repository&gt;"    href="/guides/mini/guide-multiple-repositories.html" />
</map>

</p>

See also the [Introduction to Repositories](/guides/introduction/introduction-to-repositories.html) and [Repository Layout](../repository/layout.html).

As you may know, Maven addresses artifacts using artifact coordinates. The artifact coordinates uniquely describe the artifact 
you are referring to, but does not tell anything about its source (or origin). This is where
Maven Repositories come into picture, that holds the artifacts laid out (published) according to Maven Repository
Layout. And this is where the circle closes: artifacts, being laid out in defined layout, consumed and published
by Maven.

Sections:
* [Artifacts](artifacts.md)
* [Metadata](metadata.md)
* [Layout](layout.md)
* [Local Repositories](local.md)
* [Remote Repositories](remote.md)
