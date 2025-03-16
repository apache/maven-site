---

title: Retirement Plan for Plugins
author: 
- Dennis Lundberg
date: 2013-07-26
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

# Retirement Plan for Plugins

## Decide to retire

Propose a vote on the dev-list to retire a plugin. The vote should be open for the standard 72 hours to allow people to voice their opinions. Send a cc to the users-list. Standard Apache voting rules apply. Only PMC votes are binding.

The vote must contain one or more options on how to retire the plugin. There are multiple scenarios available. Here are a couple that have been suggested:

1. Repository will be archived \(read only\)
2. Move to another Apache project
3. Move to www.mojohaus.org or another forge

Here&apos;s a template for scenario A that can be used for the vote email:

```
To: "Maven Developers List" <dev@maven.apache.org>
Cc: "Maven Users List" <users@maven.apache.org>
Subject: [VOTE] Retire Maven Foo Plugin

Hi,

A paragraph giving the reasons why the plugin should be retired. Make a note of
how long it has been since the latest release.

I therefore propose that we retire maven-foo-plugin.

If this vote is successful I will make one final release of the plugin, making
it clear on the plugin site that it has been retired. After that the source code
repository will get archived, meaning it's read only in the future.

The process for retiring a plugin is described here:
https://maven.apache.org/developers/retirement-plan-plugins.html

The vote is open for 72 hours.

[ ] +1 Yes, it's about time
[ ] -1 No, because...
```

If the vote is successful, post the result to the dev list and cc the PMC and users list. For instance:

```
To: "Maven Developers List" <dev@maven.apache.org>
Cc: "Maven Users List" <users@maven.apache.org>
CC: "Maven Project Management Committee List" <private@maven.apache.org>
Subject: [RESULT] [VOTE] Retire Maven Foo Plugin

Hi,

The vote has passed with the following result:

+1 (binding): <<list of names>>
+1 (non binding): <<list of names>>

I will continue with the steps required to retire this plugin.
```

If the vote passes, make one final release of the plugin \(with its own standard 72h vote on source release\) before it is retired. This allows us to make a clean break. The person who wants to retire a plugin is the one who does the final release. Below you will find the extra steps that you need to follow when retiring a plugin, in addition to [our standard release process](./release/maven-project-release-procedure.html).

## Make the final release

1. Create an issue in JIRA with the issue type &quot;Task&quot; and the summary &quot;Retire this plugin&quot;, and schedule it for the final release. If the plugin includes a JIRA report in the generated site, you will need to close this issue before you make the release.
2. Add the description &quot;This is the final version of this plugin. It has been retired.&quot; to the final version in JIRA.
3. Add a prominent notice on the front page of the plugin&apos;s site, informing that the plugin is retired. Suggested text:

   ```
   Note: This plugin is retired. It is no longer maintained.
   ```

   If the plugin is moved elsewhere, that should also be added to the plugin&apos;s site. Suggested text:

   ```
   Note: This plugin has retired from the Apache Maven project,
   but has moved to the <Organization> <Project> project.
   ```
4. Add &quot; \(RETIRED\)&quot; at the end of `<project>`/`<name>` in the plugin&apos;s `pom.xml`. This will show up on every page of the generated site.
5. Go ahead with [the standard release process](./release/maven-project-release-procedure.html), making sure that you follow the exceptions mentioned above regarding the site deployment.
6. When updating the plugins page, move Maven Foo Plugin to under the &quot;Retired&quot; heading. Remove the SVN/Github and JIRA/Github Issues links and add the date of retirement.
7. When updating the version in JIRA, do not add Y.Z+1 and make sure you remove any future versions. This needs to be done by PMC members.

## Clean up after the release

1. Remove the `.Jenkinsfile` from all branches. This will remove the project from https://builds.apache.org/job/maven-box/
2. Remove the `.github` directory from default branch. This will remove GitHub Actions
3. Ask INFRA for archive git repos \(gitbox + github\)
4. Republish documentation, postfix project name with \(RETIRED\)
5. When relevant update summary pages for [plugins](https://maven.apache.org/plugins/index.html) or [shared components](https://maven.apache.org/shared/index.html)
6. Add &quot; \(RETIRED\)&quot; at the end of the project name in JIRA. This needs to be done by PMC members.
7. Put the JIRA project in read-only mode in case of standalone project \(own Jira key\): apply Maven Retired Project Permissions Scheme. \(Requires JIRA admin karma: e.g. ask Brian Fox\)
8. Comment the [dist-tool configuration](https://ci-maven.apache.org/job/Maven/job/maven-box/job/maven-dist-tool/job/master/site/dist-tool.conf.html) entry. Also comment the plugin&apos;s entry in the `GetPrerequisites.java` and `ListBranchesReport` files.
9. Remove from `Source Repository`
   1. [https://maven.apache.org/scm.html](https://maven.apache.org/scm.html) \(includes the plantUML diagram\)
   2. [https://github.com/apache/maven-sources](https://github.com/apache/maven-sources)
10. Remove distribution from current [dist area](https://dist.apache.org/repos/dist/release/maven/) \(history remains available in [archive](https://archive.apache.org/dist/maven/)\). This needs to be done by PMC members.
11. Update board report. This needs to be done by PMC members.
12. Announce the fact that the plugin has been retired/moved on the announce and users mailing lists. Explain to people what they should do if they would like to continue development of the plugin. Example for retirement email:

    ```
    To: announce@maven.apache.org, users@maven.apache.org
    Subject: [ANN] Retirement Maven Foo Plugin

    Hi,
    the Maven Foo Plugin has been retired and is no longer maintained.
    We retired it, because there was very little interest by and interaction with users over the past years.

    The retirement vote results can be seen here: [link to mail in mailing list archive]

    If you would like to continue the development of the plugin, you can fork the plugin using another groupId and artifactId.
    You also need to rename the plugin, for example from "maven-foo-plugin" to "foo-maven-plugin".
    Feel free to inform others that you continue working on the plugin in a different place.
    You can write a mail to the users mailing list for that.

    -The Apache Maven team
    ```

