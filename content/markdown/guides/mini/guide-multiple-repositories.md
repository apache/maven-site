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

# Setting up Multiple Repositories

There are two different ways that you can specify the use of multiple repositories. The first way is to specify in a POM which repositories you want to use. That is supported both inside and outside of build profiles:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
...
  <repositories>
    <repository>
      <id>my-repo1</id>
      <name>your custom repo</name>
      <url>http://jarsm2.dyndns.dk</url>
    </repository>
    <repository>
      <id>my-repo2</id>
      <name>your custom repo</name>
      <url>http://jarsm2.dyndns.dk</url>
    </repository>
  </repositories>
...
</project>
```

**NOTE:** You will also get the standard set of repositories as defined in the [Super POM](../introduction/introduction-to-the-pom.html#Super_POM).

The other way you can specify multiple repositories is by creating a profile in the `${user.home}/.m2/settings.xml` or `${maven.home}/conf/settings.xml` file like the following:

```xml
<settings>
 ...
 <profiles>
   ...
   <profile>
     <id>myprofile</id>
     <repositories>
       <repository>
         <id>my-repo2</id>
         <name>your custom repo</name>
         <url>http://jarsm2.dyndns.dk</url>
       </repository>
     </repositories>
   </profile>
   ...
 </profiles>

 <activeProfiles>
   <activeProfile>myprofile</activeProfile>
 </activeProfiles>
 ...
</settings>
```

If you specify repositories in profiles you must remember to activate that particular profile! As you can see above we do this by registering a profile to be active in the `activeProfiles` element.

You could also activate this profile on the command like by executing the following command:

```
mvn -Pmyprofile ...
```

In fact the `-P` option will take a CSV list of profiles to activate if you wish to activate multiple profiles simultaneously.

**Note**: The settings descriptor documentation can be found on the [Maven Local Settings Model Website](../../maven-settings/settings.html).

## Repository Order

Remote repository URLs are queried in the following order for artifacts until one returns a valid result:

1. effective settings:
   1. Global `settings.xml`
   2. User `settings.xml`
2. local effective build POM:
   1. Local `pom.xml`
   2. Parent POMs, recursively
   3. Super POM
3. effective POMs from dependency path to the artifact.

For each of these locations, the repositories within the profiles are queried first in the order outlined at [Introduction to build profiles](../introduction/introduction-to-profiles.html).

Before downloading from a repository, [mirrors configuration](./guide-mirror-settings.html) is applied.

Effective settings and local build POM, with profile taken into account, can easily be reviewed to see their repositories order with `mvn help:effective-settings` and `mvn help:effective-pom -Dverbose`.

## Repository IDs

Each repository must have a **unique ID**. Clashing repository IDs within either effective settings or effective POMs lead to build failures. However, repositories from POM get overwritten by repositories with the same ID from effective settings. Repository IDs are also used in the [local repository metadata](https://maven.apache.org/ref/3-LATEST/maven-repository-metadata/).
