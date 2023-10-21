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

# Release Notes &#x2013; Maven 4.0.0-alpha-8

The Apache Maven team would like to announce the release of Maven 4.0.0-alpha-8.

This is alpha release, not suitable for production.

Maven 4.0.0-alpha-8 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/4.0.0-alpha-8/](/ref/4.0.0-alpha-8/)

## Overview About the Changes

This alpha release provides new cornerstone features for the future Maven evolution.  
In particular, the POM model which was set in stone to a 4.0.0 version since Maven 2.0, is now able to evolve. For modules that have a packaging which is not POM, the flattened consumer POM is now installed/deployed instead of the main POM, eventually translated back into a 4.0.0 model version for consumer compatibility.  The build POM is also installed / deployed unchanged with `build` classifier. This allows the introduction of the 4.1.0 model which already brings a few improvements.

The full list of changes can be found in our [issue management system][4]. Among those are:
 - switch from [Plexus XML](https://codehaus-plexus.github.io/plexus-xml/) to StAX / [Woodstox](https://github.com/FasterXML/woodstox) for XML parsing
 - support for alternative POM syntaxes
 - introduce a 4.1.0 model version
 - add flexible goal ordering (in 4.1.0 model)
 - allow glob patterns in dependency exclusions
 - support proxy port interpolation in `settings.xml`
 - add support for TRACE logging level (with style "bold,magenta")
 - add model version analysis and downgrade
 - attach the build POM (with `build` clasifier) and simplify the build/consumer implementation
 - add a new BOM packaging
 - trim down the consumer POM
 - expose the rootDirectory for model processing
 - support configuration in `extensions.xml` / `\<extension>` elements (in 4.1.0 model)
 - generic `.uri` suffix to get the URI representation of any file property
 - allow registering Maven core extension in `.m2` directory instead of `MAVEN_HOME`
 - colorize transfer messages
 - the `root` attribute introduced in alpha-7 has been moved to the new 4.1.0 model

## Known Issues

If you find any incompatibility with latest versions of plugins, do not hesitate to report those.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: https://dlcdn.apache.org/maven/maven-4/4.0.0-alpha-8/
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12353356
[5]: ../../docs/history.html

