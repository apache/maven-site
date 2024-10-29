---
title: Guide to using the release plugin
author: 
  - Jason van Zyl
  - Trent Rosenbaum
  - Vincent Massol
  - Karl Heinz Marbaise
date: 2006-10-07  
2015-07-31
---

<!-- Licensed to the Apache Software Foundation (ASF) under one-->
<!-- or more contributor license agreements.  See the NOTICE file-->
<!-- distributed with this work for additional information-->
<!-- regarding copyright ownership.  The ASF licenses this file-->
<!-- to you under the Apache License, Version 2.0 (the-->
<!-- "License"); you may not use this file except in compliance-->
<!-- with the License.  You may obtain a copy of the License at-->
<!---->
<!--   http://www.apache.org/licenses/LICENSE-2.0-->
<!---->
<!-- Unless required by applicable law or agreed to in writing,-->
<!-- software distributed under the License is distributed on an-->
<!-- "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY-->
<!-- KIND, either express or implied.  See the License for the-->
<!-- specific language governing permissions and limitations-->
<!-- under the License.-->
<!-- NOTE: For help with the syntax of this file, see:-->
<!-- http://maven.apache.org/doxia/references/apt-format.html-->
# Releasing

## Introduction

The main aim of the maven\-release plugin is to provide a standard mechanism to release project artifacts outside the immediate development team\. The plugin provides basic functionality to create a release and to update the project&apos;s SCM accordingly\.

To create a release the maven\-release plugin is executed through maven in 2 stages:

1. Preparing the release\.
1. Performing the release\.
## Preparing the release

The plugin will record release information into a new revision of the project&apos;s _pom\.xml_ file as well as applying SCM versioning to the project&apos;s resources\.

The `release:prepare` goal will:

1. Verify that there are no uncommitted changes in the workspace\.
1. Prompt the user for the desired tag, release and development version names\.
1. Modify and commit release information into the _pom\.xml_ file\.
1. Tag the entire project source tree with the new tag name\.

The following example shows how to run the `release:prepare` goal with a Subversion SCM\. The commandline example directs the plugin to locate a Subversion SCM on a local file system\.

```
mvn release:prepare \
        -Dproject.scm.developerConnection=scm:svn:file:///D:/subversion_data/repos/my_repo/my-app-example/trunk \
        -DtagBase=file:///D:/subversion_data/repos/my_repo/my-app-example/tags
```

When using the `release:prepare` goal, the user must supply maven with information regarding the current location of the project&apos;s SCM\. In the previous example maven was supplied with the current location of the development trunk and the new location to record tagged instances of the project\.

- **project\.scm\.developerConnection**

    The current location of the development trunk\. A valid SCM URL format appropriate to the SCM provider\. The &quot;SCM:Provider:&quot; prefix is used to determine the provider being used\.

- **tagbase**

    The new location to record a tagged release\. A valid SCM URL format appropriate to the SCM provider without the &quot;SCM:Provider:&quot; prefix\.

The previous goal parameters can be supplied while executing maven on the commandline, \(as shown in the previous example\) or they can be defined and maintained within the project&apos;s _pom\.xml_ file\. The location of the current development trunk is defined within the _pom\.xml_ file in the following form:

```
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.mycompany.app</groupId>
  <artifactId>app</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>Application</name>
  <url>http://app.mycompany.com</url>
  ...
  <scm>
    <developerConnection>scm:svn:file:///D:/subversion_data/repos/my_repo/my-app-example/trunk</developerConnection>
  </scm>
  ...
  <build>
    <plugins>
      ...
      <plugin>
        <artifactId>maven-release-plugin</artifactId>
        <version>2.5.2</version>
        <configuration>
          ...
          <tagBase>
            file:///D:/subversion_data/repos/my_repo/my-app-example/tags
          </tagBase>
          ...
        </configuration>
      </plugin>
      ...
    </plugins>
  </build>
  ...
</project> 
```

To define the tagBase parameter within the _pom\.xml_ file a tagBase element must be defined within a _plugins/plugin/configuration_ element\. The following example shows how this would look within the _pom\.xml_ file\.

```
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.mycompany.app</groupId>
  <artifactId>app</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>Application</name>
  <url>http://app.mycompany.com</url>
  ...
  <scm>
    <developerConnection>scm:svn:file:///D:/subversion_data/repos/my_repo/my-app-example/trunk</developerConnection>
  </scm>
  ...
  <build>
    <plugins>
      ...
      <plugin>
        <artifactId>maven-release-plugin</artifactId>
        <version>2.5.2</version>
        <configuration>
          ...
          <tagBase>
            file:///D:/subversion_data/repos/my_repo/my-app-example/tags
          </tagBase>
          ...
        </configuration>
      </plugin>
      ...
    </plugins>
  </build>
  ...
</project>
```

During the execution of the `release:prepare` goal maven will interact with the user to gather information about the current release\. Maven will prompt the user for the following information:

- **A Desired SCM provider tag name**\. 

    This is a SCM provider specific value, in the case of the Subversion SCM provider this value is equal to the directory name that will appear under the URL provided by the the tagBase parameter\.

- **A Desired project release version**\. 

    This value is placed in the _pom\.xml_ that will define the current release\. If a development _pom\.xml_ holds a version value of 1\.0\-SNAPSHOT then the release version would be 1\.0\. This is not enforced and can be a value appropriate to yourself or a company environment\.

- **A New development version**\.

    This value is the placed in the next revision of the _pom\.xml_ file used for continuing development\. If the current release represented version 1\.0 then an appropriate value could be 2\.0\-SNAPSHOT\. The SNAPSHOT designator is required to prepare and perform future releases\. This value is then committed in the next development revision of the _pom\.xml_ file\.

After maven has been supplied with the required information the maven\-release plugin will interact with the project&apos;s SCM and define a relese to be extracted and deployed\. At the same time the project&apos;s development trunk is updated allowing developers to continue with further modifications that will be included within future releases\.

## Performing the release

The plugin will extract file revisions associated with the current release\. Maven will compile, test and package the versioned project source code into an artifact\. The final deliverable will then be released into an appropriate maven repository\.

The `release:perform` goal will:

1. Extract file revisions versioned under the new tag name\.
1. Execute the maven build lifecycle on the extracted instance of the project\.
1. Deploy the versioned artifacts to appropriate local and remote repositories\.

The following example shows how to run the `release:perform` goal from the commandline\.

```
mvn release:perform
```

The `release:perform` goal requires a file called _release\.properties_ to be present within the project root directory\. The _release\.properties_ file is constructed during the execution of the `release:prepare` goal and contains all the information needed to locate and extract the correctly tagged version of the project\. Shown below is an example of the contents that can appear within an instance of the _release\.properties_ file\.

**Note:** The location of the _release\.properties_ file is under review and could be moved to the target directory\.

```
#Generated by Release Plugin on: Sat Nov 12 11:22:33 GMT 2005
#Sat Nov 12 11:22:33 GMT 2005
maven.username=myusername
checkpoint.transformed-pom-for-release=OK
scm.tag=1.0
scm.url=scm\:svn\:file\:///D\:/subversion_data/repos/my_repo/my-app-example/trunk
scm.tag-base=file\:///D\:/subversion_data/repos/my_repo/my-app-example/tags
checkpoint.transform-pom-for-development=OK
checkpoint.local-modifications-checked=OK
checkpoint.initialized=OK
checkpoint.checked-in-release-version=OK
checkpoint.tagged-release=OK
checkpoint.prepared-release=OK
checkpoint.check-in-development-version=OK
```

The _release\.properties_ file is created while preparing the release\. After performing the release the file remains within the project root directory until the maven user deletes it\. The _release\.properties_ file can be given to any developer within the team and by simply excuting the `release:perform` goal can create and deploy a new instance of the project artifact time and again\.

During the execution of the `release:perform` goal the entire maven build lifecycle is executed on the project\. The tagged project source code is extracted, compiled, tested, documented and deployed\. An instance of the release artifact is deployed to the machine&apos;s local repository\. An another instance of the release can be deployed to a remote repository by configuring the _distributionManagement_ element within the _pom\.xml_ file\.

The following is an example of how a distributionManagement element can be configured within a project _pom\.xml_ file\.

```
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.mycompany.app</groupId>
  <artifactId>app</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>Application</name>
  <url>http://app.mycompany.com</url>
  ...
  <distributionManagement>
    <repository>
      <id>myRepoId</id>
      <name>myCompanyReporsitory</name>
      <url>ftp://repository.mycompany.com/repository</url>
    </repository>
  </distributionManagement>
  ...
</project>
```

If the distributionManagement element is not configured within the _pom\.xml_ file then the deployment of the artifact will fail\. Maven will report a failure back to the user for the execution of the maven\-deploy plugin\. Please refer maven documentationa and additional mini guides for the use of the maven\-deploy plugin\.

The following delvierables are created and deployed to local and remoted repositories after the execution of the `release:perform` goal has finished\.

- _artifact id_\-_version_\.jar

    The binaries for the current release of the project\.

- _artifact id_\-_version_\-javadoc\.jar

    The javadoc explaining the current functionality of the classes within the current release\.

- _artifact id_\-_version_\-source\.jar

    The source code revisions used to build the current release of the project\.

- _artifact id_\-_version_\.pom

    The contents of the _pom\.xml_ file used to create the current release of the project\.

## Troubleshooting

### I get a &quot;The authenticity of host &apos;_host_&apos; can&apos;t be established\.&quot; error and the build hangs

This is because your `~user/.ssh/known_hosts` file doesn&apos;t have the host listed\. You&apos;d normally get a prompt to add the host to the known host list but Maven doesn&apos;t propagate that prompt\. The solution is to add the host the `known_hosts` file before executing Maven\. On Windows, this can be done by installing an OpenSSH client \(for example [SSHWindows](http://sshwindows\.sourceforge\.net/download/)\), running `ssh <host`&gt; and accepting to add the host\.

### The site deploy goal hangs

First, this means that you have successfully deployed the artifacts to the remote repo and that it&apos;s only the site deployment that is now an issue\. Stop your build, cd to **target/checkout**&gt; and run the build again by executing `mvn site:deploy`\. You should see a prompt asking you to enter a password\. This happens if your key is not in the authorized keys on the server\.

