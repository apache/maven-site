---
title: Guide to Configuring Archive Plugins
author: 
  - Brett Porter
date: 2006-06-21
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
# Guide to Configuring Archive Plugins

Many Java archive generating plugins accept the `archive` configuration element to customize the generation of the archive\. In the standard Maven Plugins, this includes the `jar`, `war`, `ejb`, `ear` and `assembly` plugins\.

## Disabling Maven Meta Information

By default, Maven generated archives include the `META-INF/maven` directory, which contains the `pom.xml` file used to build the archive, and a `pom.properties` file that includes some basic properties in a small, easier to read format\.

To disable the generation of these files, include the following configuration for your plugin \(in this example, the WAR plugin is used\):

```
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>2.6</version>
        <configuration>
          <archive>
            <addMavenDescriptor>false</addMavenDescriptor>
          </archive>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ...
</project>
```

<!-- other things: index, compress-->
## Configuring the Manifest

The archive configuration also accepts manifest configuration\. See [Guide to Working with Manifests](\./guide\-manifest\.html) for more information\.

