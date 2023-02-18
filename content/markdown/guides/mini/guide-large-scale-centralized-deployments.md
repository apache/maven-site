title: Guide to Large Scale Centralized Deployments
author: Phil Clay
date: 2021-01-01

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

## Guide to Large Scale Centralized Deployments


 This guide covers a simple optimized approach to using a [repository manager](../../repository-management.html) in a large organization with hundreds/thousands of Maven projects.


 The pillars of this approach are:



 1 Use a centralized [repository manager](../../repository-management.html).

  - Maven clients should download needed artifacts from the repository manager.

  - Maven clients should upload proprietary artifacts to the repository manager.



 1 Configure the location to download/upload artifacts in Maven `settings.xml` files, rather than in `pom.xml` files.

 1 Centrally manage the `settings.xml` files, and distribute them via automation.


### Repository Manager Layout


 Repository managers generally have at least three types of repositories:



 [hosted] contains artifacts uploaded to the repository manager

 [proxy] proxies a remote repository and caches artifacts

 [virtual] aggregates several repositories into one


 The simplest way to organize repositories within a repository manager is to have a single virtual repository that aggregates:



 - a proxy repository for each public repository to mirror. (For example: Maven Central)

 - a hosted repository for releases

 - a hosted repository for snapshots

 - a hosted repository that can contain both releases and snapshots (Only needed if some projects are still using Maven Deploy Plugin < 2.8. See [Managing Uploads to the Repository Manager](#managing-uploads-to-the-repository-manager) for more info.)


 Separate hosted repositories are generally used for releases and snapshots due to the need for different artifact retention policies.


 The following sections describe how to configure Maven clients to:



 - [Download](#managing-downloads-from-the-repository-manager) artifacts from the virtual repository.

 - [Upload](#managing-uploads-to-the-repository-manager) artifacts to one of the hosted repositories.



### Managing Downloads from the Repository Manager


 All artifacts used by Maven projects in the organization should be downloaded from the single virtual repository of the repository manager.


 Maven can be instructed to download artifacts from the repository manager's virtual repository by defining a mirror in the Maven `settings.xml` file as described in the [Guide to Mirror Settings](./guide-mirror-settings.html).


 Example: To download artifacts from the corporate repository manager's `maven-virtual` repository:



```
<settings>
  ...
  <mirrors>
    <!-- Mirror all external repositories via the Corporate Repository Manager's Maven virtual repository -->
    <mirror>
      <id>corp-repository-manager</id>
      <name>Corporate Repository Manager</name>
      <mirrorOf>external:*</mirrorOf>
      <url>https://corp-repository-manager-host/maven-virtual</url>
    </mirror>
  </mirrors>
  ...
</settings>
```


### Managing Uploads to the Repository Manager


 All proprietary artifacts produced by Maven projects in the organization should be uploaded to the repository manager's hosted repositories.


 The [Maven Deploy Plugin](../../plugins/maven-deploy-plugin) can be instructed to upload artifacts to the repository manager's repositories by defining the `alt\*DeploymentRepository` properties in the Maven `settings.xml` file. When these properties are defined, the Maven Deploy Plugin's [deploy](../../plugins/maven-deploy-plugin/deploy-mojo.html) goal uses them instead of the `<distributionManagement>` section of `pom.xml` files to determine where to upload artifacts.


 Defining the upload destination of artifacts in `settings.xml` files rather than in the `<distributionManagement>` section of `pom.xml` files allows the destinations to be centrally managed, which simplifies maintenance if the destinations need to change. In other words, rather than changing a huge number of `pom.xml` files, you just need to change [relatively few](Settings_File_Locations) `settings.xml` files if/when the distribution locations need to change.


 The ability to specify separate alternate deployment repositories for releases and snapshots via the `altReleaseDeploymentRepository` and `altSnapshotDeploymentRepository` properties, respectively, was added in Maven Deploy Plugin 2.8. To get the most out of the approach defined in this document, all projects should use Maven Deploy Plugin >=2.8. If some projects are still using an older version of Maven Deploy Plugin (>=2.3 and <2.8), then specify a single alternate deployment repository via the `altDeploymentRepository` property that points to a repository capable of containing both releases and snapshots.


 Typically, only continuous integration servers are allowed to upload artifacts to the repository manager. Therefore, these settings should only be specified in `settings.xml` files on continuous integration servers, and should not be in `settings.xml` files on developer machines. Alternatively, if you want developers to be able to upload artifacts to the repository manager, then include these properties in the `settings.xml` files used by developers.


 Example: To upload artifacts to one of the corporate repository manager's hosted repositories:



```
<settings>
  ...
  <profiles>
    <profile>

      <id>corp-repository-manager</id>

      <properties>
        <!--
          For Maven Deploy Plugin >= 2.8, deploy snapshots to this repository instead of the
          distributionManagement snapshotRepository from project pom.xml files.
        -->
        <altSnapshotDeploymentRepository>corp::default::https://corp-repository-manager-host/maven-snapshots</altSnapshotDeploymentRepository>

        <!--
          For Maven Deploy Plugin >= 2.8, deploy releases to this repository instead of the
          distributionManagement repository from project pom.xml files.
        -->
        <altReleaseDeploymentRepository>corp::default::https://corp-repository-manager-host/maven-releases</altReleaseDeploymentRepository>

        <!--
          Only needed if some projects are still using Maven Deploy Plugin >=2.3 and < 2.8,
          which is the case if projects are using the default version of Maven Deploy Plugin in maven 3.x.
          For Maven Deploy Plugin >=2.3 and < 2.8, deploy both releases and snapshots to this repository
          instead of the repositories mentioned in distributionManagement from project pom.xml files.
        -->
        <altDeploymentRepository>corp::default::https://corp-repository-manager-host/maven-combined</altDeploymentRepository>
      </properties>

      <repositories>
        <repository>
          <id>corp</id>
          <!--
            This URL is overridden by the corp-repository-manager mirror above.
            Some misbehaving tools might complain if they can't resolve the host specified here.
            If you encounter this problem, use the same URL as the corp-repository-manager mirror.
          -->
          <url>https://ignored</url>
          <releases>
            <enabled>true</enabled>
            <updatePolicy>never</updatePolicy>
          </releases>
          <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </snapshots>
        </repository>
      </repositories>

      <pluginRepositories>
        <pluginRepository>
          <id>corp</id>
          <!--
            This URL is overridden by the corp-repository-manager mirror above.
            Some misbehaving tools might complain if they can't resolve the host specified here.
            If you encounter this problem, use the same URL as the corp-repository-manager mirror.
          -->
          <url>https://ignored</url>
          <releases>
            <enabled>true</enabled>
            <updatePolicy>never</updatePolicy>
          </releases>
          <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </snapshots>
        </pluginRepository>
      </pluginRepositories>

    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>corp-repository-manager</activeProfile>
  </activeProfiles>
  ...
</settings>
```


### Settings File Locations


 Maven `settings.xml` files need to be available wherever Maven builds are performed, typically:



 - on continuous integration servers, and

 - on developer machines


 Both locations should have the mirror settings mentioned in [Managing Downloads from the Repository Manager](#managing-downloads-from-the-repository-manager).


 Typically, only continuous integration servers should have the deployment repository settings mentioned in [Managing Uploads to the Repository Manager](#managing-uploads-to-the-repository-manager), because only continuous integration servers should be allowed to upload to the repository manager. Alternatively, if you want developers to be able to upload artifacts to the repository manager, then include the deployment repository properties in the `settings.xml` files used by developers.


 How the `settings.xml` files are stored and updated is beyond the scope of this document. The general recommendation is to manage a few `settings.xml` files centrally, and then use automation to distribute them to continuous integration servers and developer machines.



