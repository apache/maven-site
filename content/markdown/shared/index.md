title: Shared Components
author: Dennis Lundberg, John Casey, Karl Heinz Marbaise
date: 2017-05-03

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
## Shared Components


 The shared components are currently under transition to Maven 3.x only components.


| **Shared Component** | **Version** | **Release Date** | **Description** | **Source Repository** | **Issue Tracking** | 
| -------------------- | ----------- | ---------------- | --------------- | --------------------- | ----------------- | 
| [`file-management`](/shared/file-management/)                             | 3.1.0 | 2022-06-29 | API to collect files from a given directory using several include/exclude rules. | [Git](https://gitbox.apache.org/repos/asf/maven-file-management.git) / [GitHub](https://github.com/apache/maven-file-management/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=file-management) | 
| [`maven-archiver`](/shared/maven-archiver/)                               | 3.6.0 | 2022-06-23 | Is mainly used by plugins to handle packaging. | [Git](https://gitbox.apache.org/repos/asf/maven-archiver.git) / [GitHub](https://github.com/apache/maven-archiver/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-archiver) | 
| [`maven-artifact-transfer`](/shared/maven-artifact-transfer/)             | 0.13.1 | 2020-12-26 | An API to install, deploy and resolve artifacts with Maven | [Git](https://gitbox.apache.org/repos/asf/maven-artifact-transfer.git) / [GitHub](https://github.com/apache/maven-artifact-transfer/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-artifact-transfer) | 
| [`maven-common-artifact-filters`](/shared/maven-common-artifact-filters/) | 3.3.2 | 2022-09-12 | Filters lists of Artifact instances. | [Git](https://gitbox.apache.org/repos/asf/maven-common-artifact-filters.git) / [GitHub](https://github.com/apache/maven-common-artifact-filters/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-common-artifact-filters) | 
| [`maven-dependency-analyzer`](/shared/maven-dependency-analyzer/)         | 1.13.0 | 2022-08-20 | Maven Dependency Analyzer component. | [Git](https://gitbox.apache.org/repos/asf/maven-dependency-analyzer.git) / [GitHub](https://github.com/apache/maven-dependency-analyzer/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-dependency-analyzer) | 
| [`maven-dependency-tree`](/shared/maven-dependency-tree/)                 | 3.2.1 | 2022-11-16 | Maven Dependency Tree constructs a tree model of a Maven project's dependencies. | [Git](https://gitbox.apache.org/repos/asf/maven-dependency-tree.git) / [GitHub](https://github.com/apache/maven-dependency-tree/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-dependency-tree) | 
| [`maven-doxia-tools`](/doxia/doxia-sitetools/doxia-integration-tools/)    |       |            | moved to [`doxia-integration-tools`](/doxia/doxia-sitetools/doxia-integration-tools/) |  |  | 
| [`maven-filtering`](/shared/maven-filtering/)                             | 3.3.0 | 2022-06-14 | Components for filtering resources. | [Git](https://gitbox.apache.org/repos/asf/maven-filtering.git) / [GitHub](https://github.com/apache/maven-filtering/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-filtering) | 
| [`maven-invoker`](/shared/maven-invoker/)                                 | 3.2.0 | 2022-04-05 | Fires up a Maven build in a new JVM. | [Git](https://gitbox.apache.org/repos/asf/maven-invoker.git) / [GitHub](https://github.com/apache/maven-invoker/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-invoker) | 
| [`maven-jarsigner`](/shared/maven-jarsigner/)                             | 3.0.0 | 2018-10-31 | This component provides some utilities to sign/verify jars/files in your Mojos. | [Git](https://gitbox.apache.org/repos/asf/maven-jarsigner.git) / [GitHub](https://github.com/apache/maven-jarsigner/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-jarsigner) | 
| [`maven-mapping`](/shared/maven-mapping/)                                 | 3.0.0 | 2015-11-19 | A shared component for all plugins that need to do mapping. | [Git](https://gitbox.apache.org/repos/asf/maven-mapping.git) / [GitHub](https://github.com/apache/maven-mapping/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-mapping) | 
| [`maven-reporting-api`](/shared/maven-reporting-api/)                     | 4.0.0-M4 | 2023-01-21 | API to manage report generation. | [Git](https://gitbox.apache.org/repos/asf/maven-reporting-api.git) / [GitHub](https://github.com/apache/maven-reporting-api/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-reporting-api) | 
| [`maven-reporting-exec`](/shared/maven-reporting-exec/)                   | 2.0.0-M4 | 2023-01-31 | API to manage report plugins preparation with Maven 3. | [Git](https://gitbox.apache.org/repos/asf/maven-reporting-exec.git) / [GitHub](https://github.com/apache/maven-reporting-exec/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-reporting-exec) | 
| [`maven-reporting-impl`](/shared/maven-reporting-impl/)                   | 4.0.0-M4 | 2023-02-11 | Abstract classes to manage report generation. | [Git](https://gitbox.apache.org/repos/asf/maven-reporting-impl.git) / [GitHub](https://github.com/apache/maven-reporting-impl/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-reporting-impl) | 
| [`maven-script-interpreter`](/shared/maven-script-interpreter/)           | 1.4 | 2022-12-20 | Utilities to interpret/execute some scripts for various implementations: groovy or beanshell. | [Git](https://gitbox.apache.org/repos/asf/maven-script-interpreter.git) / [GitHub](https://github.com/apache/maven-script-interpreter/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-script-interpreter) | 
| [`maven-shared-incremental`](/shared/maven-shared-incremental/)            | 1.1 | 2013-04-08 | Various utility classes and plexus components for supporting incremental build functionality in Maven plugins. | [Git](https://gitbox.apache.org/repos/asf/maven-shared-incremental.git) / [GitHub](https://github.com/apache/maven-shared-incremental/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-shared-incremental) | 
| [`maven-shared-jar`](/shared/maven-shared-jar/)                            | 1.2 | 2015-12-31 | Utilities which help identify the contents of a JAR, including Java class analysis and Maven metadata analysis. | [Git](https://gitbox.apache.org/repos/asf/maven-shared-jar.git) / [GitHub](https://github.com/apache/maven-shared-jar/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-shared-jar) | 
| [`maven-shared-resources`](/shared/maven-shared-resources/)                | 5 | 2022-11-21 | This is a collection of templates that are specific to the Maven project. | [Git](https://gitbox.apache.org/repos/asf/maven-shared-resources.git) / [GitHub](https://github.com/apache/maven-shared-resources/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-shared-resources) | 
| [`maven-shared-utils`](/shared/maven-shared-utils/)                        | 3.3.4 | 2021-04-30 | Utilities functions for use within Maven. | [Git](https://gitbox.apache.org/repos/asf/maven-shared-utils.git) / [GitHub](https://github.com/apache/maven-shared-utils/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-shared-utils) | 
| [`maven-shared-io`](/shared/maven-shared-io/)                              | 3.0.0 | 2015-12-23 | API for I/O support like logging, download or file scanning. | [Git](https://gitbox.apache.org/repos/asf/maven-shared-io.git) / [GitHub](https://github.com/apache/maven-shared-io/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-shared-io) | 
| [`maven-verifier`](/shared/maven-verifier/)                                | 2.0.0-M1 | 2022-09-22 | Used to run Maven builds as part of tests. | [Git](https://gitbox.apache.org/repos/asf/maven-verifier.git) / [GitHub](https://github.com/apache/maven-verifier/) | [JIRA](https://issues.apache.org/jira/issues/?jql=project=MSHARED%20AND%20status!=Closed%20AND%20component=maven-verifier) | 
| [`maven-scm`](/scm/)                                                       | 2.0.0-M1 | 2022-01-08 | Generic API to SCM systems. | [Git](https://gitbox.apache.org/repos/asf/maven-scm.git) / [GitHub](https://github.com/apache/maven-scm/) | [JIRA](https://issues.apache.org/jira/browse/SCM) | 

 Archived version of shared libraries reference documentations are [located here](../shared-archives/).


