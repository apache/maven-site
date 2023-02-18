title: Available Plugins
author: Brett Porter, Jason van Zyl, Dennis Lundberg, Olivier Lamy, Benson Margulies, Karl-Heinz Marbaise
date: 2017-05-05

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
## Available Plugins

 Maven is - at its heart - a plugin execution framework; all work is done by plugins. Looking for a specific goal to execute? This page lists the core plugins and others. There are the build and the reporting plugins:

- **Build plugins** will be executed during the build and they should be configured in the `<build>` element from the POM.

- **Reporting plugins** will be executed during the site generation and they should be configured in the `<reporting>` element from the POM. Because the result of a Reporting plugin is part of the generated site, Reporting plugins should be both internationalized and localized. You can read more about the [localization of our plugins](./localization.html) and how you can help.

### Supported By The Maven Project

 To see the most up-to-date list browse the Maven repository, specifically the [`org/apache/maven/plugins`](https://repo.maven.apache.org/maven2/org/apache/maven/plugins/) subdirectory. _(Plugins are organized according to a directory structure that resembles the standard Java package naming convention)_

<!--  TODO: the repository manager should be able to produce a page like this. Ensure all descriptions are in the POM of these plugins. -->
<!--  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
<!--  The release dates in this table must follow the ISO-8601 standard: yyyy-MM-dd -->
<!--  See https://maven.apache.org/guides/development/guide-documentation-style.html -->
<!--  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
| **Plugin** | **Type*** | **Version** | **Release Date** | **Description** | **Source Repository** | **Issue Tracking** |
| ---------- | --------- | ----------- | ---------------- | --------------- | --------------------- | ----------------- |
| **Core plugins** |  |  |  | **Plugins corresponding to default core phases (ie. clean, compile). They may have multiple goals as well.** |  |  |
| [`clean`](/plugins/maven-clean-plugin/)                 | B | 3.2.0 | 2022-04-01 | Clean up after the build. | [Git](https://gitbox.apache.org/repos/asf/maven-clean-plugin.git) / [GitHub](https://github.com/apache/maven-clean-plugin/) | [Jira MCLEAN](https://issues.apache.org/jira/browse/MCLEAN) |
| [`compiler`](/plugins/maven-compiler-plugin/)           | B | 3.10.1 | 2022-03-11 | Compiles Java sources. | [Git](https://gitbox.apache.org/repos/asf/maven-compiler-plugin.git) / [GitHub](https://github.com/apache/maven-compiler-plugin/) | [Jira MCOMPILER](https://issues.apache.org/jira/browse/MCOMPILER) |
| [`deploy`](/plugins/maven-deploy-plugin/)               | B | 3.1.0 | 2023-02-06 | Deploy the built artifact to the remote repository. | [Git](https://gitbox.apache.org/repos/asf/maven-deploy-plugin.git) / [GitHub](https://github.com/apache/maven-deploy-plugin/) | [Jira MDEPLOY](https://issues.apache.org/jira/browse/MDEPLOY) |
| [`failsafe`](/surefire/maven-failsafe-plugin/)          | B | 3.0.0-M9 | 2023-02-14 | Run the JUnit integration tests in an isolated classloader. | [Git](https://gitbox.apache.org/repos/asf/maven-surefire.git) / [GitHub](https://github.com/apache/maven-surefire/) | [Jira SUREFIRE](https://issues.apache.org/jira/browse/SUREFIRE) |
| [`install`](/plugins/maven-install-plugin/)             | B | 3.1.0 | 2022-11-13 | Install the built artifact into the local repository. | [Git](https://gitbox.apache.org/repos/asf/maven-install-plugin.git) / [GitHub](https://github.com/apache/maven-install-plugin/) | [Jira MINSTALL](https://issues.apache.org/jira/browse/MINSTALL) |
| [`resources`](/plugins/maven-resources-plugin/)         | B | 3.3.0 | 2022-07-23 | Copy the resources to the output directory for including in the JAR. | [Git](https://gitbox.apache.org/repos/asf/maven-resources-plugin.git) / [GitHub](https://github.com/apache/maven-resources-plugin/) | [Jira MRESOURCES](https://issues.apache.org/jira/browse/MRESOURCES) |
| [`site`](/plugins/maven-site-plugin/)                   | B | 4.0.0-M5 | 2023-02-11 | Generate a site for the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-site-plugin.git) / [GitHub](https://github.com/apache/maven-site-plugin/) | [Jira MSITE](https://issues.apache.org/jira/browse/MSITE) |
| [`surefire`](/surefire/maven-surefire-plugin/)          | B | 3.0.0-M9 | 2023-02-14 | Run the JUnit unit tests in an isolated classloader. | [Git](https://gitbox.apache.org/repos/asf/maven-surefire.git) / [GitHub](https://github.com/apache/maven-surefire/) | [Jira SUREFIRE](https://issues.apache.org/jira/browse/SUREFIRE) |
| [`verifier`](/plugins/maven-verifier-plugin/)           | B | 1.1 | 2015-04-14 | Useful for integration tests - verifies the existence of certain conditions. | [Git](https://gitbox.apache.org/repos/asf/maven-verifier-plugin.git) / [GitHub](https://github.com/apache/maven-verifier-plugin/) | [Jira MVERIFIER](https://issues.apache.org/jira/browse/MVERIFIER) |
| **Packaging types/tools** |  |  |  | **These plugins relate to packaging respective artifact types.** |  |  |
| [`ear`](/plugins/maven-ear-plugin/)                     | B | 3.3.0 | 2022-10-18 | Generate an EAR from the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-ear-plugin.git) / [GitHub](https://github.com/apache/maven-ear-plugin/) | [Jira MEAR](https://issues.apache.org/jira/browse/MEAR) |
| [`ejb`](/plugins/maven-ejb-plugin/)                     | B | 3.2.1 | 2022-04-18 | Build an EJB (and optional client) from the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-ejb-plugin.git) / [GitHub](https://github.com/apache/maven-ejb-plugin/) | [Jira MEJB](https://issues.apache.org/jira/browse/MEJB) |
| [`jar`](/plugins/maven-jar-plugin/)                     | B | 3.3.0 | 2022-09-12 | Build a JAR from the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-jar-plugin.git) / [GitHub](https://github.com/apache/maven-jar-plugin/) | [Jira MJAR](https://issues.apache.org/jira/browse/MJAR) |
| [`rar`](/plugins/maven-rar-plugin/)                     | B | 3.0.0 | 2022-07-17 | Build a RAR from the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-rar-plugin.git) / [GitHub](https://github.com/apache/maven-rar-plugin/) | [Jira MRAR](https://issues.apache.org/jira/browse/MRAR) |
| [`war`](/plugins/maven-war-plugin/)                     | B | 3.3.2 | 2021-09-10 | Build a WAR from the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-war-plugin.git) / [GitHub](https://github.com/apache/maven-war-plugin/) | [Jira MWAR](https://issues.apache.org/jira/browse/MWAR) |
| [`app-client/acr`](/plugins/maven-acr-plugin/)          | B | 3.1.0 | 2018-06-19 | Build a JavaEE application client from the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-acr-plugin.git) / [GitHub](https://github.com/apache/maven-acr-plugin/) | [Jira MACR](https://issues.apache.org/jira/browse/MACR) |
| [`shade`](/plugins/maven-shade-plugin/)                 | B | 3.4.1 | 2022-10-27 | Build an Uber-JAR from the current project, including dependencies. | [Git](https://gitbox.apache.org/repos/asf/maven-shade-plugin.git) / [GitHub](https://github.com/apache/maven-shade-plugin/) | [Jira MSHADE](https://issues.apache.org/jira/browse/MSHADE) |
| [`source`](/plugins/maven-source-plugin/)               | B | 3.2.1 | 2019-12-21 | Build a source-JAR from the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-source-plugin.git) / [GitHub](https://github.com/apache/maven-source-plugin/) | [Jira MSOURCES](https://issues.apache.org/jira/browse/MSOURCES) |
| [`jlink`](/plugins/maven-jlink-plugin/)                 | B | 3.1.0 | 2020-12-28 | Build Java Run Time Image. | [Git](https://gitbox.apache.org/repos/asf/maven-jlink-plugin.git) / [GitHub](https://github.com/apache/maven-jlink-plugin/) | [Jira MJLINK](https://issues.apache.org/jira/browse/MJLINK) |
| [`jmod`](/plugins/maven-jmod-plugin/)                   | B | 3.0.0-alpha-1 | 2017-09-17 | Build Java JMod files. | [Git](https://gitbox.apache.org/repos/asf/maven-jmod-plugin.git) / [GitHub](https://github.com/apache/maven-jmod-plugin/) | [Jira MJMOD](https://issues.apache.org/jira/browse/MJMOD) |
| **Reporting plugins** |  |  |  | **Plugins which generate reports, are configured as reports in the POM and run under the site generation lifecycle.** |  |  |
| [`changelog`](/plugins/maven-changelog-plugin/)         | R | 2.3 | 2014-06-24 | Generate a list of recent changes from your SCM. | [Git](https://gitbox.apache.org/repos/asf/maven-changelog-plugin.git) / [GitHub](https://github.com/apache/maven-changelog-plugin/) | [Jira MCHANGELOG](https://issues.apache.org/jira/browse/MCHANGELOG) |
| [`changes`](/plugins/maven-changes-plugin/)           | B+R | 2.12.1 | 2016-11-01 | Generate a report from an issue tracker or a change document. | [Git](https://gitbox.apache.org/repos/asf/maven-changes-plugin.git) / [GitHub](https://github.com/apache/maven-changes-plugin/) | [Jira MCHANGES](https://issues.apache.org/jira/browse/MCHANGES) |
| [`checkstyle`](/plugins/maven-checkstyle-plugin/)     | B+R | 3.2.1 | 2023-01-11 | Generate a Checkstyle report. | [Git](https://gitbox.apache.org/repos/asf/maven-checkstyle-plugin.git) / [GitHub](https://github.com/apache/maven-checkstyle-plugin/) | [Jira MCHECKSTYLE](https://issues.apache.org/jira/browse/MCHECKSTYLE) |
| [`doap`](/plugins/maven-doap-plugin/)                   | B | 1.2 | 2015-03-17 | Generate a Description of a Project (DOAP) file from a POM. | [Git](https://gitbox.apache.org/repos/asf/maven-doap-plugin.git) / [GitHub](https://github.com/apache/maven-doap-plugin/) | [Jira MDOAP](https://issues.apache.org/jira/browse/MDOAP) |
| [`docck`](/plugins/maven-docck-plugin/)                 | B | 1.1 | 2015-04-03 | Documentation checker plugin. | [Git](https://gitbox.apache.org/repos/asf/maven-docck-plugin.git) / [GitHub](https://github.com/apache/maven-docck-plugin/) | [Jira MDOCCK](https://issues.apache.org/jira/browse/MDOCCK) |
| [`javadoc`](/plugins/maven-javadoc-plugin/)           | B+R | 3.5.0 | 2023-02-12 | Generate Javadoc for the project. | [Git](https://gitbox.apache.org/repos/asf/maven-javadoc-plugin.git) / [GitHub](https://github.com/apache/maven-javadoc-plugin/) | [Jira MJAVADOC](https://issues.apache.org/jira/browse/MJAVADOC) |
| [`jdeps`](/plugins/maven-jdeps-plugin/)                 | B | 3.1.2 | 2019-06-12 | Run JDK's JDeps tool on the project. | [Git](https://gitbox.apache.org/repos/asf/maven-jdeps-plugin.git) / [GitHub](https://github.com/apache/maven-jdeps-plugin/) | [Jira MJDEPS](https://issues.apache.org/jira/browse/MJDEPS) |
| [`jxr`](/jxr/maven-jxr-plugin/)                         | R | 3.3.0 | 2022-08-16 | Generate a source cross reference. | [Git](https://gitbox.apache.org/repos/asf/maven-jxr.git) / [GitHub](https://github.com/apache/maven-jxr/) | [Jira JXR](https://issues.apache.org/jira/browse/JXR) |
| [`linkcheck`](/plugins/maven-linkcheck-plugin/)         | R | 1.2 | 2014-10-08 | Generate a Linkcheck report of your project's documentation. | [Git](https://gitbox.apache.org/repos/asf/maven-linkcheck-plugin.git) / [GitHub](https://github.com/apache/maven-linkcheck-plugin/) | [Jira MLINKCHECK](https://issues.apache.org/jira/browse/MLINKCHECK) |
| [`pmd`](/plugins/maven-pmd-plugin/)                   | B+R | 3.20.0 | 2022-09-11 | Generate a PMD report. | [Git](https://gitbox.apache.org/repos/asf/maven-pmd-plugin.git) / [GitHub](https://github.com/apache/maven-pmd-plugin/) | [Jira MPMD](https://issues.apache.org/jira/browse/MPMD) |
| [`project-info-reports`](/plugins/maven-project-info-reports-plugin/) | R | 3.4.2 | 2023-01-11 | Generate standard project reports. | [Git](https://gitbox.apache.org/repos/asf/maven-project-info-reports-plugin.git) / [GitHub](https://github.com/apache/maven-project-info-reports-plugin/) | [Jira MPIR](https://issues.apache.org/jira/browse/MPIR) |
| [`surefire-report`](/surefire/maven-surefire-report-plugin/) | R | 3.0.0-M9 | 2023-02-14 | Generate a report based on the results of unit tests. | [Git](https://gitbox.apache.org/repos/asf/maven-surefire.git) / [GitHub](https://github.com/apache/maven-surefire/) | [Jira SUREFIRE](https://issues.apache.org/jira/browse/SUREFIRE) |
| **Tools** |  |  |  | **These are miscellaneous tools available through Maven by default.** |  |  |
| [`antrun`](/plugins/maven-antrun-plugin/)               | B | 3.1.0 | 2022-04-18 | Run a set of ant tasks from a phase of the build. | [Git](https://gitbox.apache.org/repos/asf/maven-antrun-plugin.git) / [GitHub](https://github.com/apache/maven-antrun-plugin/) | [Jira MANTRUN](https://issues.apache.org/jira/browse/MANTRUN) |
| [`artifact`](/plugins/maven-artifact-plugin/)           | B | 3.4.0 | 2023-02-07 | Manage artifacts tasks like buildinfo. | [Git](https://gitbox.apache.org/repos/asf/maven-artifact-plugin.git) / [GitHub](https://github.com/apache/maven-artifact-plugin/) | [Jira MARTIFACT](https://issues.apache.org/jira/browse/MARTIFACT) |
| [`archetype`](/archetype/maven-archetype-plugin/)       | B | 3.2.1 | 2021-12-30 | Generate a skeleton project structure from an archetype. | [Git](https://gitbox.apache.org/repos/asf/maven-archetype.git) / [GitHub](https://github.com/apache/maven-archetype/) | [Jira ARCHETYPE](https://issues.apache.org/jira/browse/ARCHETYPE) |
| [`assembly`](/plugins/maven-assembly-plugin/)           | B | 3.4.2 | 2022-07-20 | Build an assembly (distribution) of sources and/or binaries. | [Git](https://gitbox.apache.org/repos/asf/maven-assembly-plugin.git) / [GitHub](https://github.com/apache/maven-assembly-plugin/) | [Jira MASSEMBLY](https://issues.apache.org/jira/browse/MASSEMBLY) |
| [`dependency`](/plugins/maven-dependency-plugin/)     | B+R | 3.5.0 | 2023-01-11 | Dependency manipulation (copy, unpack) and analysis. | [Git](https://gitbox.apache.org/repos/asf/maven-dependency-plugin.git) / [GitHub](https://github.com/apache/maven-dependency-plugin/) | [Jira MDEP](https://issues.apache.org/jira/browse/MDEP) |
| [`enforcer`](/enforcer/maven-enforcer-plugin/)          | B | 3.2.1 | 2023-01-28 | Environmental constraint checking (Maven Version, JDK etc), User Custom Rule Execution. | [Git](https://gitbox.apache.org/repos/asf/maven-enforcer.git) / [GitHub](https://github.com/apache/maven-enforcer/) | [Jira MENFORCER](https://issues.apache.org/jira/browse/MENFORCER) |
| [`gpg`](/plugins/maven-gpg-plugin/)                     | B | 3.0.1 | 2021-05-08 | Create signatures for the artifacts and poms. | [Git](https://gitbox.apache.org/repos/asf/maven-gpg-plugin.git) / [GitHub](https://github.com/apache/maven-gpg-plugin/) | [Jira MGPG](https://issues.apache.org/jira/browse/MGPG) |
| [`help`](/plugins/maven-help-plugin/)                   | B | 3.3.0 | 2022-08-14 | Get information about the working environment for the project. | [Git](https://gitbox.apache.org/repos/asf/maven-help-plugin.git) / [GitHub](https://github.com/apache/maven-help-plugin/) | [Jira MPH](https://issues.apache.org/jira/browse/MPH) |
| [`invoker`](/plugins/maven-invoker-plugin/)           | B+R | 3.5.0 | 2023-02-12 | Run a set of Maven projects and verify the output. | [Git](https://gitbox.apache.org/repos/asf/maven-invoker-plugin.git) / [GitHub](https://github.com/apache/maven-invoker-plugin/) | [Jira MINVOKER](https://issues.apache.org/jira/browse/MINVOKER) |
| [`jarsigner`](/plugins/maven-jarsigner-plugin/)         | B | 3.0.0 | 2018-11-06 | Signs or verifies project artifacts. | [Git](https://gitbox.apache.org/repos/asf/maven-jarsigner-plugin.git) / [GitHub](https://github.com/apache/maven-jarsigner-plugin/) | [Jira MJARSIGNER](https://issues.apache.org/jira/browse/MJARSIGNER) |
| [`jdeprscan`](/plugins/maven-jdeprscan-plugin/)         | B | 3.0.0-alpha-1 | 2017-11-15 | Run JDK's JDeprScan tool on the project. | [Git](https://gitbox.apache.org/repos/asf/maven-jdeprscan-plugin.git) / [GitHub](https://github.com/apache/maven-jdeprscan-plugin/) | [Jira MJDEPRSCAN](https://issues.apache.org/jira/browse/MJDEPRSCAN) |
| [`patch`](/plugins/maven-patch-plugin/)                 | B | 1.2 | 2015-03-09 | Use the gnu patch tool to apply patch files to source code. | [Git](https://gitbox.apache.org/repos/asf/maven-patch-plugin.git) / [GitHub](https://github.com/apache/maven-patch-plugin/) | [Jira MPATCH](https://issues.apache.org/jira/browse/MPATCH) |
| [`pdf`](/plugins/maven-pdf-plugin/)                     | B | 1.6.1 | 2022-08-16 | Generate a PDF version of your project's documentation. | [Git](https://gitbox.apache.org/repos/asf/maven-pdf-plugin.git) / [GitHub](https://github.com/apache/maven-pdf-plugin/) | [Jira MPDF](https://issues.apache.org/jira/browse/MPDF) |
| [`plugin`](/plugin-tools/maven-plugin-plugin/)        | B+R | 3.7.1 | 2023-01-13 | Create a Maven plugin descriptor for any mojos found in the source tree, to include in the JAR. | [Git](https://gitbox.apache.org/repos/asf/maven-plugin-tools.git) / [GitHub](https://github.com/apache/maven-plugin-tools/) | [Jira MPLUGIN](https://issues.apache.org/jira/browse/MPLUGIN) |
| [`release`](/plugins/maven-release-plugin/)             | B | 3.0.0-M7 | 2022-10-29 | Release the current project - updating the POM and tagging in the SCM. | [Git](https://gitbox.apache.org/repos/asf/maven-release.git) / [GitHub](https://github.com/apache/maven-release/) | [Jira MRELEASE](https://issues.apache.org/jira/browse/MRELEASE) |
| [`remote-resources`](/plugins/maven-remote-resources-plugin/) | B | 3.0.0 | 2022-07-17 | Copy remote resources to the output directory for inclusion in the artifact. | [Git](https://gitbox.apache.org/repos/asf/maven-remote-resources-plugin.git) / [GitHub](https://github.com/apache/maven-remote-resources-plugin/) | [Jira MRRESOURCES](https://issues.apache.org/jira/browse/MRRESOURCES) |
| [`scm`](/scm/maven-scm-plugin/) | B | 2.0.0-M3 | 2022-10-25 | Execute SCM commands for the current project. | [Git](https://gitbox.apache.org/repos/asf/maven-scm.git ) / [GitHub](https://github.com/apache/maven-scm/) | [Jira SCM](https://issues.apache.org/jira/browse/SCM) |
| [`scm-publish`](/plugins/maven-scm-publish-plugin/)     | B | 3.1.0 | 2020-12-26 | Publish your Maven website to a scm location. | [Git](https://gitbox.apache.org/repos/asf/maven-scm-publish-plugin.git) / [GitHub](https://github.com/apache/maven-scm-publish-plugin/) | [Jira MSCMPUB](https://issues.apache.org/jira/browse/MSCMPUB) |
| [`scripting`](/plugins/maven-scripting-plugin/)         | B | 3.0.0 | 2021-03-01 | The Maven Scripting Plugin wraps the Scripting API according to JSR223. | [Git](https://gitbox.apache.org/repos/asf/maven-scripting-plugin.git) / [GitHub](https://github.com/apache/maven-scripting-plugin/) | [Jira MSCRIPTING](https://issues.apache.org/jira/browse/MSCRIPTING) |
| [`stage`](/plugins/maven-stage-plugin/)                 | B | 1.0 | 2015-03-03 | Assists with release staging and promotion. | [Git](https://gitbox.apache.org/repos/asf/maven-stage-plugin.git) / [GitHub](https://github.com/apache/maven-stage-plugin/) | [Jira MSTAGE](https://issues.apache.org/jira/browse/MSTAGE) |
| [`toolchains`](/plugins/maven-toolchains-plugin/)       | B | 3.1.0 | 2022-06-18 | Allows to share configuration across plugins. | [Git](https://gitbox.apache.org/repos/asf/maven-toolchains-plugin.git) / [GitHub](https://github.com/apache/maven-toolchains-plugin/) | [Jira MTOOLCHAINS](https://issues.apache.org/jira/browse/MTOOLCHAINS) |
| [`wrapper`](/plugins/maven-wrapper-plugin/)             | B | 3.1.1 | 2022-05-14 | Download and unpack the maven wrapper distribution | [Git](https://gitbox.apache.org/repos/asf/maven-wrapper-plugin.git) / [GitHub](https://github.com/apache/maven-wrapper/) | [Jira MWRAPPER](https://issues.apache.org/jira/browse/MWRAPPER) |

 \* **B**uild or **R**eporting plugin

 There are also some sandbox plugins into our [source repository](https://svn.apache.org/repos/asf/maven/sandbox/trunk/plugins).

 Previous archived versions of plugins reference documentations are [located here](../plugins-archives/).

### Retired

| **Plugin** | **Type*** | **Version** | **Retired Date** | **Description** |
| ---------- | --------- | ----------- | ---------------- | --------------- |
| [`ant`](/plugins/maven-ant-plugin/)               | B | 2.4 | 2019-06-02 | Generate an Ant build file for the project. |
| [`eclipse`](/plugins/maven-eclipse-plugin/)       | B | 2.10 | 2015-10-07 | Generate an Eclipse project files for the current project. |
| [`idea`](/plugins/maven-idea-plugin/)             | B | 2.2.1 | 2013-07-26 | Create/update an IDEA workspace for the current project (individual modules are created as IDEA modules) |
| [`one`](/plugins/maven-one-plugin/)               | B | 1.3 | 2013-07-30 | A plugin for interacting with legacy Maven 1.x repositories and builds. |
| [`reactor`](/plugins/maven-reactor-plugin/)       | B | 1.1 | 2014-03-24 | Build a subset of interdependent projects in a reactor (Maven 2 only). |
| [`repository`](/plugins/maven-repository-plugin/) | B | 2.4 | 2019-04-30 | Plugin to help with repository-based tasks. |

### Outside The Maven Land

#### At MojoHaus (formerly known as codehaus.org)

 There are also [many plug-ins](https://www.mojohaus.org/plugins.html) available at the [MojoHaus](https://github.com/mojohaus) project at GitHub.

 Here are a few common ones:

| **Plugin** (see [complete list with version](https://www.mojohaus.org/plugins.html)) | **Description** |
| --- | --- |
| [`animal-sniffer`](https://www.mojohaus.org/animal-sniffer/animal-sniffer-maven-plugin/) | Build signatures of APIs (JDK for example) and checks your classes against them. |
| [`build-helper`](https://www.mojohaus.org/build-helper-maven-plugin/) | Attach extra artifacts and source directories to build. |
| [`buildplan`](https://www.mojohaus.org/buildplan-maven-plugin/) | Inspect the lifecycle of your build. |
| [`castor`](https://www.mojohaus.org/castor-maven-plugin/) | Generate sources from an XSD using Castor. |
| [`clirr`](https://www.mojohaus.org/clirr-maven-plugin/) | Compare binaries or sources for compatibility using Clirr |
| [`javacc`](https://www.mojohaus.org/javacc-maven-plugin/) | Generate sources from a JavaCC grammar. |
| [`jdepend`](https://www.mojohaus.org/jdepend-maven-plugin/) | Generate a report on code metrics using JDepend. |
| [`nar-maven-plugin`](https://maven-nar.github.io/) | Compiles C, C++, Fortran for different architectures. |
| [`native`](https://www.mojohaus.org/maven-native/native-maven-plugin/) | Compiles C and C++ code with native compilers. |
| [`sql`](https://www.mojohaus.org/sql-maven-plugin/) | Executes SQL scripts from files or inline. |
| [`taglist`](https://www.mojohaus.org/taglist-maven-plugin/) | Generate a list of tasks based on tags in your code. |
| [`versions`](https://www.mojohaus.org/versions-maven-plugin/) | Manage versions of your project, its modules, dependencies and plugins. |

#### Misc

 A number of other projects provide their own Maven plugins. This includes:

| **Plugin** | **Maintainer** | **Description** |
| --- | --- | --- |
| [`cargo`](https://codehaus-cargo.github.io/) | [Cargo Project](https://codehaus-cargo.github.io/) | Start/stop/configure J2EE containers and deploy to them. |
| [`clover`](https://confluence.atlassian.com/display/CLOVER/Clover-for-Maven+2) | [Atlassian Clover](https://www.atlassian.com/software/clover/) | Generate a Clover report. |
| [`jetty`](https://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html) | [Jetty Project](https://www.eclipse.org/jetty/) | Jetty Run a Jetty container for rapid webapp development. |
| [`jalopy`](http://www.triemax.com/products/jalopy/manual/plugin-maven.html) | [Triemax](http://www.triemax.com/) | Use Jalopy to format your source code. |
| [`rat`](https://creadur.apache.org/rat/) | [Apache Creadur Project](https://creadur.apache.org/) | Release Audit Tool (RAT) to verify files. |
| [`Genesis Plugins`](https://geronimo.apache.org/maven/genesis/plugins/tools-maven-plugin/index.html) | [Apache Geronimo Project](https://geronimo.apache.org/) | Verify legal files in artifacts. |
| [`Apache Tomcat`](https://tomcat.apache.org/maven-plugin.html) | [Apache Tomcat Project](https://tomcat.apache.org/maven-plugin.html) | Run an Apache Tomcat container for rapid webapp development. |
| [`OWASP dependency-check`](https://jeremylong.github.io/DependencyCheck/) | [OWASP Dependency-check Project](https://www.owasp.org/index.php/OWASP_Dependency_Check) | Run OWASP Dependency-Check, a utility that identifies project dependencies and checks if there are any known, publicly disclosed, vulnerabilities. |
| [`CycloneDX`](https://github.com/CycloneDX/cyclonedx-maven-plugin) | [CycloneDX Project](https://cyclonedx.org/) | Generate Software Bill of Materials (SBOM) in CycloneDX format. |
| [`pgpverify`](https://www.simplify4u.org/pgpverify-maven-plugin/) | [Simplify4U](https://www.simplify4u.org/) | Verify PGP signature of all project dependencies. |

### Resources

 1 [Guide to Configuring Plugins](../guides/mini/guide-configuring-plugins.html)
