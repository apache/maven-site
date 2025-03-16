---

title: Guide to Maven Classloading
author: 
- Jason van Zyl
- Anders Kristian Andersen
- Konrad Windszus
date: 2022-11-16
----------------

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

# Guide to Maven Classloading

This is a description of the classloader hierarchy in Maven.

<!-- MACRO{toc|section=1|fromDepth=2} -->

## Overview

Maven uses the [Plexus Classworlds](https://codehaus-plexus.github.io/plexus-classworlds/) classloading framework to create the classloader graph. If you look in your `${maven.home}/boot` directory, you will see a single JAR which is the Classworlds JAR we use to boot the classloader graph. The Classworlds JAR is the only element of the Java `CLASSPATH`. The other classloaders are built by Classworlds \(&quot;realms&quot; in Classworlds terminology\).

Each realm exposes

1. optionally some classes imported from 0\..n other classloaders
2. optionally some classes from a directory or JAR
3. one parent classloader

The search order is always as given above.

## Platform Classloader

This is the classloader exposing all JRE classes.

## System Classloader

It contains only Plexus Classworlds and imports the platform classloader.

## Core Classloader

The second classloader down the graph contains the core requirements of Maven. **It is used by Maven internally but not by plugins**. The core classloader has the libraries in `${maven.home}/lib`. In general these are just Maven libraries. For example instances of [`MavenProject`](/ref/current/apidocs/org/apache/maven/project/MavenProject.html) belong to this classloader.

You can add elements to this classloader by the means outlined in [Core Extension](./guide-using-extensions.html). These are loaded through the same classloader as `${maven.home}/lib` and hence are available to the Maven core and all plugins for the current project \(through the API Classloader, see next paragraph\). More information is available in [Core Extension](./guide-using-extensions.html).

## API Classloader

The API classloader is a filtered view of the Core Classloader, done by exposing only the exported packages from all Core Extensions. The main API is listed in [Maven Core Extensions Reference](/ref/current/maven-core/core-extensions.html).

This has been introduced with Maven 3\.3\.1 \([MNG-5771](https://issues.apache.org/jira/browse/MNG-5771)\).

## Build Extension Classloaders

![Build Extension Class Realm](../../buildExtensionClassRealm.svg)

For every plugin which is marked with `<extensions>true</extensions>` and every [build extension](/ref/current/maven-model/maven.html#class_extension) listed in the according section of the POM, there is a dedicated classloader. Those are isolated. That is, one build extension does not have access to other build extensions. It imports everything from the API classloader. All JSR 330 or Plexus components declared in the underlying JAR are registered in the global Plexus container while creating the classloader. In addition all component references in the plugin descriptor are properly wired from the underlying Plexus container. Build extensions have limited effect as they are loaded late.

## Project Classloaders

There is one project classloader per Maven project \(identified through its coordinates\). This one imports the API Classloader. In addition it exposes all classes from all Build Extension Classloaders which are bound to the current project. This is only released with the container. During the build outside Mojo executions, the thread&apos;s context classloader is set to the project classloader.

## Plugin Classloaders

![Plugin Class Realm](../../pluginClassRealm.svg)

Each plugin \(which is not marked as build extension\) has its own classloader that imports the Project classloader.

Plugins marked with `<extensions>true</extensions>` leverage the Build Extension classloader instead of the Plugin classloader.

Users can add dependencies to this classloader by adding dependencies to a plugin in the [`plugins/plugin`](/ref/current/maven-model/maven.html#class_plugin) section of their project `pom.xml`. Here is a sample of adding `ant-nodeps` to the plugin classloader of the Antrun Plugin and hereby enabling the use of additional/optional Ant tasks:

```unknown
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-antrun-plugin</artifactId>
  <version>1.3</version>
  <dependencies>
    <dependency>
      <groupId>org.apache.ant</groupId>
      <artifactId>ant-nodeps</artifactId>
      <version>1.7.1</version>
    </dependency>
  </dependencies>
  ...
</plugin>
```

Plugins can inspect their effective runtime class path via the expressions `${plugin.artifacts}` or `${plugin.artifactMap}` to have a list or map, respectively, of resolved artifacts injected from the [`PluginDescriptor`](/ref/current/maven-plugin-api/apidocs/org/apache/maven/plugin/descriptor/PluginDescriptor.html).

Please note that the plugin classloader does neither contain the [dependencies](/ref/current/maven-model/maven.html#class_dependency) of the current project nor its build output. Instead, plugins can query the project&apos;s compile, runtime and test class path from the [`MavenProject`](/ref/current/apidocs/org/apache/maven/project/MavenProject.html) in combination with the mojo annotation `requiresDependencyResolution` from the [Mojo API Specification](/developers/mojo-api-specification.html). For instance, flagging a mojo with `@requiresDependencyResolution runtime` enables it to query the runtime class path of the current project from which it could create further classloaders.

When a build plugin is executed, the thread&apos;s context classloader is set to the plugin classloader.

## Custom Classloaders

Plugins are free to create further classloaders. For example, a plugin might want to create a classloader that combines the plugin class path and the project class path.

It is important to understand that the plugin classloader cannot load classes from any of those custom classloaders. Some factory patterns require that. Here you must add the classes to the plugin classloader as shown before.

