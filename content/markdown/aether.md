# Aether import plan to Maven
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
## IP Clearance

Tracked on [Incubator IP Clearance](http://incubator.apache.org/ip-clearance/maven-aether.html)
and through [MNG-6007](https://issues.apache.org/jira/browse/MNG-6007).

## Code integration

| |current Eclipse|Apache Maven for Maven 3.x|
|---|---|---|
|**Name**|Aether|Maven Artifact Resolver|
|**Name**|[https://projects.eclipse.org/projects/technology.aether](https://projects.eclipse.org/projects/technology.aether)|<https://maven.apache.org/resolver/><br/><https://maven.apache.org/resolver-archives/>|
|**SCM repo**|[aether-core.git](http://git.eclipse.org/c/aether/aether-core.git/aether.html)<br/>[aether-ant.git](http://git.eclipse.org/c/aether/aether-ant.git/aether.html)<br/>[aether-demo.git](http://git.eclipse.org/c/aether/aether-demo.git/aether.html)|[https://gitbox.apache.org/repos/asf?p=maven-resolver.git](https://gitbox.apache.org/repos/asf?p=maven-resolver.git)<br/>([MNG-6008](https://issues.apache.org/jira/browse/MNG-6008))<br/>one unique git repo with 3 independant **master**, **ant-tasks** and **demo** branches|
|**central groupId**|[org.eclipse.aether](https://repo.maven.apache.org/maven2/org/eclipse/aether/)|[org.apache.maven.resolver](https://repo.maven.apache.org/maven2/org/apache/maven/resolver/)|
|**artifactIds**|aether<br/>aether-api<br/>aether-impl<br/>aether-spi<br/>aether-util<br/>aether-transport-\*|maven-resolver<br/>maven-resolver-api<br/>maven-resolver-impl<br/>maven-resolver-spi<br/>maven-resolver-util<br/>maven-resolver-transport-*|
|**OSGi Bundles**|org.eclipse.aether.api<br/>org.eclipse.aether.impl<br/>org.eclipse.aether....|no OSGi bundles in Apache Maven|
|**P2 repo**|http://download.eclipse.org/aether/aether-core/releases/<br/>http://download.eclipse.org/aether/maven-aether-provider/releases/|no P2 repo|
|**API java packages**|org.eclipse.aether...|**Keep packages in Maven 3.x to maintain compatibility for some plugins or extensions using Aether API.**|
|**Impl java packages**|org.eclipse.aether.impl.\*<br/>org.eclipse.aether.internal.\*|Same as API, even if nobody should rely on impl...|
|**SPI java packages**|org.eclipse.aether.spi.\*|Same as API (is it really used outside?)|
|**Util java packages**|org.eclipse.aether.util.\*|Same as API (is it really used outside?)|
|**Transport java packages**|org.eclipse.aether.transport.\*|Same as API (is it really used outside?)|
|**Ant Tasks**|Aether Ant Tasks|Maven Artifact Resolver Ant Tasks|
|**Maven Provider (in core)**|[Maven Aether Provider](http://maven.apache.org/ref/3.3.9/maven-aether-provider/)|[Maven Resolver Provider](https://maven.apache.org/ref/3.6.1/maven-resolver-provider/)|

## Versions history

|Aether version|[used in which Maven version](https://maven.apache.org/docs/history.html)|
|---|---|
|Sonatype Aether 1.2|2010-09-02 [3.0-beta-3](https://repo.maven.apache.org/maven2/org/apache/maven/maven-aether-provider/3.0-beta-3/)|
|Sonatype Aether 1.7|2010-10-08 [3.0](http://maven.apache.org/ref/3.0/apache-maven/dependencies.html)|
|Sonatype Aether 1.8|2010-11-26 [3.0.1](http://maven.apache.org/ref/3.0.1/apache-maven/dependencies.html)|
|Sonatype Aether 1.9|2011-01-12 [3.0.2](http://maven.apache.org/ref/3.0.2/apache-maven/dependencies.html)]|
|Sonatype Aether 1.11|2011-03-04 [3.0.3](http://maven.apache.org/ref/3.0.3/apache-maven/dependencies.html)]|
|Sonatype Aether 1.13.1|2012-01-20 [3.0.4](http://maven.apache.org/ref/3.0.4/apache-maven/dependencies.html)<br/>2013-02-23 [3.0.5](http://maven.apache.org/ref/3.0.5/apache-maven/dependencies.html)|
|Eclipse Aether 0.9.0.M2|2013-07-15 [3.1.0](http://maven.apache.org/ref/3.1.0/apache-maven/dependencies.html)<br/>2013-10-04 [3.1.1](http://maven.apache.org/ref/3.1.1/apache-maven/dependencies.html)<br/>2014-02-21 [3.2.1](http://maven.apache.org/ref/3.2.1/apache-maven/dependencies.html)<br/>2014-06-26 [3.2.2](http://maven.apache.org/ref/3.2.2/apache-maven/dependencies.html)<br/>2014-08-17 [3.2.3](http://maven.apache.org/ref/3.2.3/apache-maven/dependencies.html)|
|Eclipse Aether 1.0.0.v20140518|2014-12-20 [3.2.5](http://maven.apache.org/ref/3.2.5/apache-maven/dependencies.html)|
|Eclipse Aether 1.0.2.v20150114|2015-03-18 [3.3.1](http://maven.apache.org/ref/3.3.1/apache-maven/dependencies.html)<br/>2015-04-28 [3.3.3](http://maven.apache.org/ref/3.3.3/apache-maven/dependencies.html)<br/>2015-11-14 [3.3.9](http://maven.apache.org/ref/3.3.9/apache-maven/dependencies.html)|
