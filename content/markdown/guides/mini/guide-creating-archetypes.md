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

# Guide to Creating Archetypes

Creating an archetype is a pretty straight forward process. An archetype is a very simple artifact, that contains the project prototype you wish to create. An archetype is made up of:

- an [archetype descriptor](/archetype/archetype-models/archetype-descriptor/archetype-descriptor.html) (`archetype-metadata.xml` in directory: `src/main/resources/META-INF/maven/`). It lists all the files that will be contained in the archetype and categorizes them so they can be processed correctly by the archetype generation mechanism.
- the prototype files that are copied by the archetype plugin (directory: `src/main/resources/archetype-resources/`)
- the prototype pom (`pom.xml` in: `src/main/resources/archetype-resources`)
- a pom for the archetype (`pom.xml` in the archetype's root directory).

To create an archetype follow these steps:

## 1. Create a new project and pom.xml for the archetype artifact

An example `pom.xml` for an archetype artifact looks as follows:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>my.groupId</groupId>
  <artifactId>my-archetype-id</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>maven-archetype</packaging>

  <build>
    <extensions>
      <extension>
        <groupId>org.apache.maven.archetype</groupId>
        <artifactId>archetype-packaging</artifactId>
        <version>3.4.1</version>
      </extension>
    </extensions>
  </build>
</project>
```

All you need to specify is a `groupId`, `artifactId` and `version`. These three parameters will be needed later for invoking the archetype via `archetype:generate` from the commandline.

## 2. Create the archetype descriptor

The [archetype descriptor](/archetype/archetype-models/archetype-descriptor/archetype-descriptor.html) is a file called `archetype-metadata.xml` which must be located in the `src/main/resources/META-INF/maven/` directory. An example of an archetype descriptor can be found in the quickstart archetype:

```xml
<archetype-descriptor
        xmlns="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.2.0 https://maven.apache.org/xsd/archetype-descriptor-1.2.0.xsd"
        name="quickstart">
    <fileSets>
        <fileSet filtered="true" packaged="true">
            <directory>src/main/java</directory>
        </fileSet>
        <fileSet>
            <directory>src/test/java</directory>
        </fileSet>
    </fileSets>
</archetype-descriptor>
```

The attribute `name` tag should be the same as the `artifactId` in the archetype `pom.xml`.

The boolean attribute `partial` show if this archetype is representing a full Maven project or only parts.

The `requiredProperties`, `fileSets` and `modules` tags represent the differents parts of the project:

- `<requiredProperties>` : List of required properties to generate a project from this archetype
- `<fileSets>` : File sets definition
- `<modules>` : Modules definition

At this point one can only specify individual files to be created but not empty directories.

Thus the quickstart archetype shown above defines the following directory structure:

```
archetype
|-- pom.xml
`-- src
    `-- main
        `-- resources
            |-- META-INF
            |   `-- maven
            |       `--archetype-metadata.xml
            `-- archetype-resources
                |-- pom.xml
                `-- src
                    |-- main
                    |   `-- java
                    |       `-- App.java
                    `-- test
                        `-- java
                            `-- AppTest.java
```

## 3. Create the prototype files and the prototype pom.xml

The next component of the archetype to be created is the prototype `pom.xml`. Any `pom.xml` will do, just don't forget to the set `artifactId` and `groupId` as variables ( `${artifactId}` / `${groupId}` ). Both variables will be initialized from the commandline when calling `archetype:generate`.

An example for a prototype `pom.xml` is:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <packaging>jar</packaging>

    <name>${artifactId}</name>
    <url>http://www.myorganization.org</url>

    <dependencies>
        <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                 <version>4.12</version>
                <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

## 4. Install the archetype and run the archetype plugin

Now you are ready to install the archetype:

```
mvn install
```

Now that you have created an archetype, you can try it on your local system by using the following command. In this command, you need to specify the full information about the archetype you want to use (its `groupId`, its `artifactId`, its `version`) and the information about the new project you want to create (`artifactId` and `groupId`). Don't forget to include the version of your archetype (if you don't include the version, you archetype creation may fail with a message that version:RELEASE was not found)

```
mvn archetype:generate                                  \
  -DarchetypeGroupId=<archetype-groupId>                \
  -DarchetypeArtifactId=<archetype-artifactId>          \
  -DarchetypeVersion=<archetype-version>                \
  -DgroupId=<my.groupid>                                \
  -DartifactId=<my-artifactId>
```

Once you are happy with the state of your archetype, you can deploy (or submit it to [Maven Central](/guides/mini/guide-central-repository-upload.html)) it as any other artifact and the archetype will then be available to any user of Maven.

## Alternative way to start creating your Archetype

Instead of manually creating the directory structure needed for an archetype, simply use

```
mvn archetype:generate
  -DgroupId=[your project's group id]
  -DartifactId=[your project's artifact id]
  -DarchetypeGroupId=org.apache.maven.archetypes
  -DarchetypeArtifactId=maven-archetype-archetype
```

Afterwhich, you can now customize the contents of the `archetype-resources` directory, and `archetype-metadata.xml`, then, proceed to Step#4 (Install the archetype and run the archetype plugin).
