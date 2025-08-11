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

# Guide to deploying 3rd party JARs to remote repository

Same concept of the [install:install-file](./guide-3rd-party-jars-local.html) goal of the maven-install-plugin where the 3rd party JAR is installed in the local repository. But this time instead to local repository the JAR will be install both in the local and remote repository.

To deploy a 3rd party JAR use the deploy:deploy-file goal under maven-deploy-plugin.

First, the wagon-provider(wagon-ftp, wagon-file, etc..) must be placed to your `${maven.home}/lib`.

Then execute the command:

```
mvn deploy:deploy-file -DgroupId=<group-id> \
  -DartifactId=<artifact-id> \
  -Dversion=<version> \
  -Dpackaging=<type-of-packaging> \
  -Dfile=<path-to-file> \
  -DrepositoryId=<id-to-map-on-server-section-of-settings.xml> \
  -Durl=<url-of-the-repository-to-deploy>
```

## Deploying a 3rd party JAR with a generic POM

By default, deploy:deploy-file generates a generic POM(.pom) to be deploy together with the 3rd party JAR. To disable this feature we should set the `generatePOM` argument to false.

```
-DgeneratePom=false
```

## Deploying a 3rd party JAR with a customized POM

If a POM is already existing for the 3rd Party JAR and you want to deploy it together with the JAR we should use the `pomFile` argument of the deploy-file goal. See sample below.

```
mvn deploy:deploy-file -DpomFile=<path-to-pom> \
  -Dfile=<path-to-file> \
  -DrepositoryId=<id-to-map-on-server-section-of-settings.xml> \
  -Durl=<url-of-the-repository-to-deploy>
```

Note that `groupId`, `artifactId`, `version` and `packaging` arguments are not included here because deploy-file goal will get these information from the given POM.

## Deploying Source Jars

<!-- TODO: Check the following, cause i don't this is true anymore. I assume packaging should be jar-->
<!--  and the classifier should be set to source.-->
To deploy a 3rd party source jar, packaging should be set to `java-source`, and generatePom should be set to `fals-->
