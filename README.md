<!---
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

# Maven Site

[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/apache/maven.svg?label=License)][license]
[![Jenkins Build](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci-maven.apache.org%2Fjob%2FMaven%2Fjob%2Fmaven-box%2Fjob%2Fmaven-site%2Fjob%2Fmaster)
][build]
[![Jenkins Tests](https://img.shields.io/jenkins/tests?jobUrl=https%3A%2F%2Fci-maven.apache.org%2Fjob%2FMaven%2Fjob%2Fmaven-box%2Fjob%2Fmaven-dist-tool%2Fjob%2Fmaster%2F&label=Check%20Errors)
][check-errors]

This is the Git repository for the content of <https://maven.apache.org/>.
See [website documentation](https://maven.apache.org/developers/website/) for details on the publication structure.

## Run Locally

You can run

```
$ mvn site:run
```

to run locally and see the website on <http://localhost:8080/>.

Additional Resources
--------------------

+ [Contributing patches](https://maven.apache.org/guides/development/guide-maven-development.html#Creating_and_submitting_a_patch)
+ [Contributor License Agreement][cla]
+ [General GitHub documentation](https://docs.github.com)
+ [GitHub pull request documentation](https://docs.github.com/pull-requests)
+ [Apache Maven Twitter Account](https://twitter.com/ASFMavenProject)
+ [Slack channel in ASF Workspace](https://infra.apache.org/slack.html)

[license]: https://www.apache.org/licenses/LICENSE-2.0
[ml-list]: https://maven.apache.org/mailing-lists.html
[cla]: https://www.apache.org/licenses/#clas
[build]: https://ci-maven.apache.org/job/Maven/job/maven-box/job/maven-site/job/master/
[check-errors]: https://ci-maven.apache.org/job/Maven/job/maven-box/job/maven-dist-tool/job/master/lastCompletedBuild/site/dist-tool-check-errors.html
