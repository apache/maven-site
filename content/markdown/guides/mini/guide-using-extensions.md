title: Guide to using Extensions
author: Jason van Zyl, Konrad Windszus
date: 2022-11-16

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
## Using Extensions

 Extensions are a way to add classes to either the [Core Classloader](./guide-maven-classloading.html#core-classloader) (Core Extensions) or the [Project Classloader](./guide-maven-classloading.html#Project_Classloaders) (Build Extensions). This is necessary for adjusting Maven in a way that affects more than just one plug-in.

 The mechanism allows extensions to either replace default [Sisu components](https://www.eclipse.org/sisu/) with custom ones or add new components which are used at run time. In addition one could also expose additional packages from the Core Classloader.

 Extensions are typically used to enable [Wagon providers](../../wagon/wagon-providers/), used for the transport of artifact between repositories, and plug-ins which [provide lifecycle enhancements](../../examples/maven-3-lifecycle-extensions.html).

### Loading Extensions

 There are different means of loading extensions depending on the type. There are _core extensions_ which are loaded **early** and build extensions which are loaded **late**. Some extensions require early loading as they fundamentally change Maven behaviour. An extension's documentation should indicate whether it provides a core or a build extension.

#### Core Extension

- Registered via extension jar in `${maven.home}/lib/ext`

- Registered via CLI argument `mvn -Dmaven.ext.class.path=extension.jar`

- Registered via [`.mvn/extensions.xml` file](../../configure.html#mvn-extensions-xml-file)

#### Build Extension

- Registered via [`project-\>build-\>plugins-\>plugin`](../../pom.html#plugins) with element `extensions` being set to `true`. This is useful for regular plug-ins carrying some extensions.

   Example:

```

<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.felix</groupId>
        <artifactId>maven-bundle-plugin</artifactId>
        <extensions>true</extensions>
        <configuration>
          ...
        </configuration>
      </plugin>
    </plugins>
  </build>
  ...
</project>

```

- Registered as build extension in [`project-\>build-\>extensions-\>extension`](../../pom.html#extensions)

   Example:

```

<project>
  ...
  <build>
    <extensions>
      <extension>
        <groupId>org.apache.maven.wagon</groupId>
         <artifactId>wagon-ftp</artifactId>
         <version>2.10</version>
      </extension>
    </extensions>
  </build>
  ...
</project>

```
