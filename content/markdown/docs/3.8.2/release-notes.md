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

 NOTE: For help with the syntax of this file, see:
 http://maven.apache.org/doxia/modules/index.html#Markdown
-->

# Release Notes &#x2013; Maven 3.8.2

The Apache Maven team would like to announce the release of Maven 3.8.2.

Maven 3.8.2 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/3.8.2/](/ref/3.8.2/)

## Overview About the Changes

This release mainly contains fixes and non-invasive backports from Maven 4.0.0-alpha-1.

The full list of changes can be found in our [issue management system][4].

## Known Issues

- If any of your plugin mojos spawns new threads there might be a change in class loading as a
  result of the fix for [MNG-6843][6]. See [discussion][7] with a possible fix for your plugin
  mojo as well as a potential general fix in [MNG-7212][8].
- If any or your projects relies on a Maven Core artifacts and your build is perfomed with Maven 3.8.1
  or later a transitive dependency parent of Sisu Plexus/CPI API 1.0 will inject a remote repository
  over HTTP which will be blocked by default and (might) lead to build failures. See [discussion][9]
  and [MNG-7214][10] for details.
- [MNG-7170][11] has caused a regression where non-reactor projects have basedir set and plugins as
  well as other components make false assumptions. See [discussion][12] and [MNG-7215][13] for details.
- [MNG-6886][14] has caused a regression where `plexus-cipher` with the `org.sonatype.plexus` group id was
  globally excluded, but not all affected modules were updated to depend on this dependency directly with
  the `org.codehaus.plexus` group id. See [discussion][15] for details.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12349965
[5]: ../../docs/history.html
[6]: https://issues.apache.org/jira/browse/MNG-6843
[7]: https://lists.apache.org/thread.html/r0777c9e364f93a609cb4c3da6e634139b9c400166e280856ee25ba72%40%3Cdev.maven.apache.org%3E
[8]: https://issues.apache.org/jira/browse/MNG-7212
[9]: https://lists.apache.org/thread.html/rda29028b2c8985f3b94e721d3014a948b312fbddf95ffaa4971acc03%40%3Cusers.maven.apache.org%3E
[10]: https://issues.apache.org/jira/browse/MNG-7214
[11]: https://issues.apache.org/jira/browse/MNG-7170
[12]: https://lists.apache.org/thread.html/r226ec816d20c84c532dca5eff5de66028259521c79610e3efc2b0f63%40%3Cdev.maven.apache.org%3E
[13]: https://issues.apache.org/jira/browse/MNG-7215
[14]: https://issues.apache.org/jira/browse/MNG-6886
[15]: https://lists.apache.org/thread.html/r7f5a62fd35dc6698c8f7097734f7c4acf4bb657d6c721e8a7bc76b8c%40%3Cusers.maven.apache.org%3E
