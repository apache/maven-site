# End Of Life Apache Maven 2.x
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
Apache Maven 2.x has reached its end of life and is no longer supported.

Apache Maven 2.x had its last release - version 2.2.1 - in November 2009.

This means:

- security vulnerability reports will not be checked against the 2.x branch
- the 2.x download pages will be removed
- the latest 2.x release will be removed from the mirror system 
- the links to any 2.x specific documentation will be moved to [Maven 2.x Archives](https://maven.apache.org/archives/maven-2.x/)


## List Of Last Plugins Versions Which Support Maven 2.x

This list contains the list of plugins with the last version that
supports Maven 2.x, which means if you have to use Maven 2, this list is
the reference to the last version of plugins you can use.

NOTE: If you find plugins which are not mentioned or issues please
report on the users mailing list.

|Plugin (current docs)|Type\*|Version|Release Date|Description|
|---|---|---|---|---|
|**Core plugins**||||**Plugins corresponding to default core phases (ie. clean, compile). They may have multiple goals as well**|
|[clean](/plugins/maven-clean-plugin/)|B|[2.6.1](/plugins-archives/maven-clean-plugin-2.6.1)|2014-10-26|Clean up after the build|
|[compiler](/plugins/maven-compiler-plugin)|B|[3.3](/plugins-archives/maven-compiler-plugin-3.3)|2015-03-26|Compiles Java sources|
|[deploy](/plugins/maven-deploy-plugin)|B|[2.8.2](/plugins-archives/maven-deploy-plugin-2.8.2)|2014-08-27|Deploy the built artifact to the remote repository|
|[failsafe](/surefire/maven-failsafe-plugin)|B|[2.19.1](/surefire-archives/surefire-2.19.1/maven-failsafe-plugin)|2016-01-03|Run the JUnit integration tests in an isolated classloader|
|[install](/plugins/maven-install-plugin)|B|[2.5.2](/plugins-archives/maven-install-plugin-2.5.2)|2014-08-27|Install the built artifact into the local repository|
|[resources](/plugins/maven-resources-plugin)|B|[2.7](/plugins-archives/maven-resources-plugin-2.7)|2014-09-29|Copy the resources to the output directory for including in the JAR|
|[site](/plugins/maven-site-plugin)|B|[2.4](/plugins-archives/maven-site-plugin-2.4)|2012-04-03|Generate a site for the current project|
|[surefire](/surefire/maven-surefire-plugin)|B|[2.19.1](/surefire-archives/surefire-2.19.1/maven-surefire-plugin)|2016-01-03|Run the JUnit unit tests in an isolated classloader|
|[verifier](/plugins/maven-verifier-plugin)|B|[1.1](/plugins-archives/maven-verifier-plugin-1.1)|2015-04-14|Useful for integration tests - verifies the existence of certain conditions|
|**Packaging types/tools**||||**These plugins relate to packaging respective artifact types**|
|[ear](/plugins/maven-ear-plugin)|B|[2.10.1](/plugins-archives/maven-ear-plugin-2.10.1)|2015-06-27|Generate an EAR from the current project|
|[ejb](/plugins/maven-ejb-plugin)|B|[2.5.1](/plugins-archives/maven-ejb-plugin-2.5.1)|2015-06-20|Build an EJB (and optional client) from the current project|
|[jar](/plugins/maven-jar-plugin)|B|[2.6](/plugins-archives/maven-jar-plugin-2.6)|2015-03-09|Build a JAR from the current project|
|[rar](/plugins/maven-rar-plugin)|B|[2.4](/plugins-archives/maven-rar-plugin-2.4)|2014-09-08|Build a RAR from the current project|
|[war](/plugins/maven-war-plugin)|B|[2.6](/plugins-archives/maven-war-plugin-2.6)|2015-01-08|Build a WAR from the current project|
|[app-client/acr](/plugins/maven-acr-plugin)|B|[1.1](/plugins-archives/maven-acr-plugin-1.1)|2014-09-02|Build a JavaEE application client from the current project|
|[shade](/plugins/maven-shade-plugin)|B|[1.7.1](/plugins-archives/maven-shade-plugin-1.7.1)|2012-06-27|Build an Uber-JAR from the current project, including dependencies|
|[source](/plugins/maven-source-plugin)|B|[2.4](/plugins-archives/maven-source-plugin-2.4)|2014-10-07|Build a source-JAR from the current project|
|**Reporting plugins**||||**Plugins which generate reports, are configured as reports in the POM and run under the site generation lifecycle**|
|[changelog](/plugins/maven-changelog-plugin)|R|[2.3](/plugins-archives/maven-changelog-plugin-2.3)|2014-06-24|Generate a list of recent changes from your SCM|
|[changes](/plugins/maven-changes-plugin)|B+R|[2.11](/plugins-archives/maven-changes-plugin-2.11)|2014-09-28|Generate a report from an issue tracker or a change document|
|[checkstyle](/plugins/maven-checkstyle-plugin)|B+R|[2.15](/plugins-archives/maven-checkstyle-plugin-2.14)|2015-03-20|Generate a Checkstyle report|
|[doap](/plugins/maven-doap-plugin)|B|[1.2](/plugins-archives/maven-doap-plugin-1.2)|2015-03-17|Generate a Description of a Project (DOAP) file from a POM|
|[docck](/plugins/maven-docck-plugin)|B|[1.1](/plugins-archives/maven-docck-plugin-1.1)|2015-04-03|Documentation checker plugin|
|[javadoc](/plugins/maven-javadoc-plugin)|B+R|[2.10.3](/plugins-archives/maven-javadoc-plugin-2.10.3)|2015-04-14|Generate Javadoc for the project|
|[jxr](/jxr/maven-jxr-plugin)|R|[2.5](/jxr-archives/jxr-2.5/maven-jxr-plugin)|2014-11-02|Generate a source cross reference|
|[linkcheck](/plugins/maven-linkcheck-plugin)|R|[1.2](/plugins-archives/maven-linkcheck-plugin-1.2)|2014-10-08|Generate a Linkcheck report of your project’s documentation|
|[pmd](/plugins/maven-pmd-plugin)|B+R|[3.4](/plugins-archives/maven-pmd-plugin-3.4)|2015-02-03|Generate a PMD report|
|[project-info-reports](/plugins/maven-project-info-reports-plugin)|R|[2.8](/plugins-archives/maven-project-info-reports-plugin-2.8)|2015-01-05|Generate standard project reports|
|[surefire-report](/surefire/maven-surefire-report-plugin)|R|[2.19.1](/surefire-archives/surefire-2.19.1/maven-surefire-report-plugin)|2016-01-03|Generate a report based on the results of unit tests|
|**Tools**||||**These are miscellaneous tools available through Maven by default**|
|[ant](/plugins/maven-ant-plugin)|B|[2.4](/plugins-archives/maven-ant-plugin-2.4)|2014-12-15|Generate an Ant build file for the project|
|[antrun](/plugins/maven-antrun-plugin)|B|[1.8](/plugins-archives/maven-antrun-plugin-1.8)|2014-12-26|Run a set of ant tasks from a phase of the build|
|[archetype](/archetype/maven-archetype-plugin)|B|[2.3](/archetype-archives/archetype-2.3/maven-archetype-plugin)|2015-03-05|Generate a skeleton project structure from an archetype|
|[assembly](/plugins/maven-assembly-plugin)|B|[2.5.4](/plugins-archives/maven-assembly-plugin-2.5.4)|2015-04-27|Build an assembly (distribution) of sources and/or binaries|
|[dependency](/plugins/maven-dependency-plugin)|B+R|[2.10](/plugins-archives/maven-dependency-plugin-2.10)|2015-01-27|Dependency manipulation (copy, unpack) and analysis|
|[enforcer](/enforcer/maven-enforcer-plugin)|B|[1.4.1](/enforcer-archives/enforcer-1.4.1/maven-enforcer-plugin)|2015-08-23|Environmental constraint checking (Maven Version, JDK etc), User Custom Rule Execution|
|[gpg](/plugins/maven-gpg-plugin)|B|[1.6](/plugins-archives/maven-gpg-plugin-1.6)|2015-01-19|Create signatures for the artifacts and poms|
|[help](/plugins/maven-help-plugin)|B|[2.2](/plugins-archives/maven-help-plugin-2.2)|2013-02-23|Get information about the working environment for the project|
|[invoker](/plugins/maven-invoker-plugin)|B+R|[1.10](/plugins-archives/maven-invoker-plugin-1.10)|2015-04-03|Run a set of Maven projects and verify the output|
|[jarsigner](/plugins/maven-jarsigner-plugin)|B|[1.4](/plugins-archives/maven-jarsigner-plugin-1.4)|2015-01-21|Signs or verifies project artifacts|
|[patch](/plugins/maven-patch-plugin)|B|[1.2](/plugins-archives/maven-patch-plugin-1.2)|2015-03-09|Use the gnu patch tool to apply patch files to source code|
|[pdf](/plugins/maven-pdf-plugin)|B|[1.3](/plugins-archives/maven-pdf-plugin-1.3)|2015-02-16|Generate a PDF version of your project’s documentation|
|[plugin](/plugin-tools/maven-plugin-plugin)|B+R|[3.4](/plugins-archives/maven-plugin-plugin-3.4)|2015-01-04|Create a Maven plugin descriptor for any mojos found in the source tree, to include in the JAR|
|[release](/plugins/maven-release-plugin)|B|[2.5.2](/maven-release-archives/maven-release-2.5.2/maven-release-plugin)|2015-04-18|Release the current project - updating the POM and tagging in the SCM|
|[remote-resources](/plugins/maven-remote-resources-plugin)|B|[1.5](/plugins-archives/maven-remote-resources-plugin-1.5)|2013-08-14|Copy remote resources to the output directory for inclusion in the artifact|
|[repository](/plugins/maven-repository-plugin)|B|[2.4](/plugins-archives/maven-repository-plugin-2.4)|2015-02-22|Plugin to help with repository-based tasks|
|[scm](/scm/maven-scm-plugin)|B|[1.9.4](/scm-archives/scm-1.9.4/maven-scm-plugin)|2015-04-01|Execute SCM commands for the current project|
|[scm-publish](/plugins/maven-scm-publish-plugin)|B|[1.0-beta-2](/plugins-archives/maven-scm-publish-plugin-1.0-beta-2)|2012-11-01|Publish your Maven website to a scm location|
|[stage](/plugins/maven-stage-plugin)|B|[1.0](/plugins-archives/maven-stage-plugin-1.0)|2015-03-03|Assists with release staging and promotion|
|[toolchains](/plugins/maven-toolchains-plugin)|B|[1.1](/plugins-archives/maven-toolchains-plugin-1.1)|2014-11-11|Allows to share configuration across plugins|
|**IDEs**||||**Plugins that simplify integration with integrated developer environments**|
|[eclipse](/plugins/maven-eclipse-plugin)|B|[2.10](/plugins-archives/maven-eclipse-plugin-2.10)|2015-05-28|Generate an Eclipse project file for the current project|

\* **B**uild or **R**eporting plugin
