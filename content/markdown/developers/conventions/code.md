---
title: Maven Code Style And Code Conventions
author: 
  - Vincent Siveton
date: 2008-07-05
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

# Maven Code Style And Code Conventions

This document describes the rules for how the sources should be formatted in order to improve consistency, readability and maintainability\.

As the formatting is automatically enforced or even applied with [spotless\-maven\-plugin](https://github\.com/diffplug/spotless/tree/main/plugin\-maven) for all projects using [Maven Project Parent POM 38 or newer](/pom/maven/index\.html) developers usually don&apos;t need to care and the following sections are just for informational purposes\.

Optionally you can still import the code style formatter for your IDE from [shared\-resources](https://gitbox\.apache\.org/repos/asf?p=maven\-shared\-resources\.git;a=tree;f=src/main/resources/config;hb=HEAD)

- [Generic Code Style And Convention](Generic\_Code\_Style\_And\_Convention)
- [Java](Java)
    - [Java Code Style](Java\_Code\_Style)
    - [Java Code Convention](Java\_Code\_Convention)
    - [Java Code Convention \- import layouts](Java\_Code\_Convention\_\-\_import\_layouts)
    - [JavaDoc Convention](JavaDoc\_Convention)
- [XML](XML)
    - [XML Code Style](XML\_Code\_Style)
    - [Generic XML Code Convention](Generic\_XML\_Code\_Convention)
    - [POM Code Convention](POM\_Code\_Convention)
    - [XDOC Code Convention](XDOC\_Code\_Convention)
    - [FML Code Convention](FML\_Code\_Convention)
## Generic Code Style And Convention

All working files \(java, xml, others\) should respect the following conventions:

- **License Header**: Always add the current [ASF license header](https://www\.apache\.org/legal/src\-headers\.html\#headers) in all files checked into the source code repository\.
- **Trailing Whitespace**: No trailing whitespaces allowed\. 

and the following style:

- **Indentation**: **Never** use tabs\!
- **Line wrapping**: Always use a 120\-column line width\.

**Note**: The specific styles and conventions, listed in the next sections, can override these generic rules\.

## Java

### Java Code Style

Maven adopts the [palantir Java format](https://github\.com/palantir/palantir\-java\-format)\.

### Java Code Convention

For consistency reasons, our Java code convention is mainly:

- **Naming**: Constants \(i\.e\. static final members\) should always be in upper case\. Use short, descriptive names for classes and methods\.
- **Organization**: Avoid using public inner classes\. Prefer interfaces instead of default implementation\.
- **Modifier**: Avoid using final modifier on all fields and arguments\. Prefer using private or protected fields instead of public fields\.
- **Exceptions**: Throw meaningful exceptions to make debugging and testing easier\.
- **Documentation**: Document public interfaces well, i\.e\. all non\-trivial public and protected functions should include Javadoc that indicates what they do\.
- **Testing**: All non\-trivial public classes should have corresponding unit or integration tests\.
### Java Code Convention \- import layouts

For consistency reasons, Java imports should be organized as:

- import **javax\.\***
- import **java\.\***
- blank line
- import **all other imports**

all imports in each group should be sorted alphabetically\.

To ensure a package import order consistent with the layout described above, download [`maven-eclipse-importorder.txt`](https://gitbox\.apache\.org/repos/asf?p=maven\-shared\-resources\.git;a=blob\_plain;f=src/main/resources/config/maven\-eclipse\-importorder\.txt;hb=HEAD), select _Window &gt; Preferences_ and navigate to _Java &gt; Code Style &gt; Organize Imports_\. Click on _Import\.\.\._ and select the downloaded file to change the import order\.

### JavaDoc Convention

TO BE DISCUSSED

## XML

### XML Code Style

The Maven style for XML files is mainly:

- **Indentation**: Always use 2 space indents, unless you&apos;re wrapping a new XML tags line in which case you should indent 4 spaces\.
- **Line Breaks**: Always use a new line with indentation for complex XML types and no line break for simple XML types\. Always use a new line to separate XML sections or blocks, for instance:

    ```
    <aTag>
      <simpleType>This is a simple type</simpleType>
    
      <complexType>
        <simpleType>This is a complex type</simpleType>
      </complexType>
    </aTag>
    ```

    In some cases, adding comments could improve the readability of blocks, for instance:

    ```
        <!-- Simple XML documentation                                               -->
    ```

    or

    ```
        <!-- ====================================================================== -->
        <!-- Block documentation                                                    -->
        <!-- ====================================================================== -->
    ```

### Generic XML Code Convention

No generic code convention exists yet for XML files\.

### POM Code Convention

The team has [voted](https://lists\.apache\.org/thread/h8px5t6f3q59cnkzpj1t02wsoynr3ygk) during the end of June 2008 to follow a specific POM convention to ordering POM elements\. The consequence of this vote is that the [Maven project descriptor](https://maven\.apache\.org/ref/current/maven\-model/maven\.html) is **no more** considered as the reference for the ordering\.

The following is the recommended ordering for all Maven POM files:

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion/>

  <parent/>

  <groupId/>
  <artifactId/>
  <version/>
  <packaging/>

  <name/>
  <description/>
  <url/>
  <inceptionYear/>
  <organization/>
  <licenses/>

  <developers/>
  <contributors/>

  <mailingLists/>

  <prerequisites/>

  <modules/>

  <scm/>
  <issueManagement/>
  <ciManagement/>
  <distributionManagement/>

  <properties/>

  <dependencyManagement/>
  <dependencies/>

  <repositories/>
  <pluginRepositories/>

  <build/>

  <reporting/>

  <profiles/>
</project>
```

**Comments**:

1. The &lt;project/&gt; element is always on one line\.
1. The blocks are voluntary separated by a new line to improve the readingness\.
1. The dependencies in &lt;dependencies/&gt; and &lt;dependencyManagement/&gt; tags have no specific ordering\. Developers are free to choose the ordering, but grouping dependencies by topics \(like groupId i\.e\. `org.apache.maven`\) is a good practice\.
### XDOC Code Convention

For consistency and readability reasons, XDOC files should respect:

- **Metadata**: Always specify metadata in the &lt;properties/&gt; tag\.
- **Sections**: Always use a new line with indentation for &lt;section/&gt; tags\.
### FML Code Convention

For readability reasons, FML files should respect:

- **FAQ**: Always use a new line with indentation for &lt;faq/&gt; tags\.
<!-- * {APT} Do we need any specific APT style/convention?-->
