---

title: Introduction to Maven Plugin Development
author: 
- John Casey
date: 2005-06-24
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

# Introduction to Maven Plugin Development

Maven consists of a core engine which provides basic project-processing capabilities and build-process management, and a host of plugins which are used to execute the actual build tasks.

## What is a Plugin?

&quot;Maven&quot; is really just a core framework for a collection of Maven Plugins. In other words, plugins are where much of the real action is performed, plugins are used to: create jar files, create war files, compile code, unit test code, create project documentation, and on and on. Almost any action that you can think of performing on a project is implemented as a Maven plugin.

Plugins are the central feature of Maven that allow for the reuse of common build logic across multiple projects. They do this by executing an &quot;action&quot; \(i.e. creating a WAR file or compiling unit tests\) in the context of a project&apos;s description - the Project Object Model \(POM\). Plugin behavior can be customized through a set of unique parameters which are exposed by a description of each plugin goal \(or Mojo\).

One of the simplest plugins in Maven is the Clean Plugin. The [Maven Clean plugin](../../plugins/maven-clean-plugin/) \(maven-clean-plugin\) is responsible for removing the target directory of a Maven project. When you run &quot;mvn clean&quot;, Maven executes the &quot;clean&quot; goal as defined in the Clean plug-in, and the target directory is removed. The Clean plugin [defines a parameter](../../plugins/maven-clean-plugin/clean-mojo.html) which can be used to customize plugin behavior, this parameter is called outputDirectory and it defaults to ${project.build.directory}.

## What is a Mojo \(_And Why the H--- is it Named &apos;Mojo&apos;_\)?

A Mojo is really just a **goal** in Maven, and plug-ins consist of any number of goals \(Mojos\). Mojos can be defined as annotated Java classes or Beanshell script. A Mojo specifies metadata about a goal: a goal name, which phase of the lifecycle it fits into, and the parameters it is expecting.

MOJO is a play on POJO \(Plain-old-Java-object\), substituting &quot;Maven&quot; for &quot;Plain&quot;. Mojo is also an interesting word \(see [definition](http://www.answers.com/mojo&r=67)\). From [Wikipedia](http://www.wikipedia.org), a &quot;mojo&quot; is defined as: &quot;...a small bag worn by a person under the clothes \(also known as a mojo hand\). Such bags were thought to have supernatural powers, such as protecting from evil, bringing good luck, etc.&quot;

## What is the Build Lifecycle? \(Overview\)

The build lifecycle is a series of common **stage**s through which all project builds naturally progress. Plugin goals are bound to specific stages in the lifecycle.

# Resources

1. [Plugin Development Center](/plugin-developers/index.html)
2. [Configuring plugins](../mini/guide-configuring-plugins.html)

