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

# Release Notes &#x2013; Maven 3.8.4

The Apache Maven team would like to announce the release of Maven 3.8.4.

Maven 3.8.4 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.8.4/](/ref/3.8.4/)

## Overview About the Changes

* Regression fixes from Maven 3.8.3
* Upgrade to Jansi 2.4.0 which supports macOS on ARM natively

The full list of changes can be found in our [issue management system][4].

## Known Issues

- The `ThreadLocal` approach that was introduced in 3.8.2 to fix [MNG-6843][6] had to be reverted as it caused too many side-effects. [MNG-7335][7] was created to track the work needed to add a more sustainable fix to Maven 3.8.5 or 3.9.0.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12350685
[5]: ../../docs/history.html
[6]: https://issues.apache.org/jira/browse/MNG-6843
[7]: https://issues.apache.org/jira/browse/MNG-7335

