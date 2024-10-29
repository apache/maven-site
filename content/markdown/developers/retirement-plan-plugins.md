---
title: Retirement Plan for Plugins
author: 
  - Dennis Lundberg
date: 2013-07-26
---

<!-- Licensed to the Apache Software Foundation (ASF) under one-->
<!-- or more contributor license agreements.  See the NOTICE file-->
<!-- distributed with this work for additional information-->
<!-- regarding copyright ownership.  The ASF licenses this file-->
<!-- to you under the Apache License, Version 2.0 (the-->
<!-- "License"); you may not use this file except in compliance-->
<!-- with the License.  You may obtain a copy of the License at-->
<!---->
<!--   http://www.apache.org/licenses/LICENSE-2.0-->
<!---->
<!-- Unless required by applicable law or agreed to in writing,-->
<!-- software distributed under the License is distributed on an-->
<!-- "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY-->
<!-- KIND, either express or implied.  See the License for the-->
<!-- specific language governing permissions and limitations-->
<!-- under the License.-->
<!-- NOTE: For help with the syntax of this file, see:-->
<!-- https://maven.apache.org/doxia/references/apt-format.html-->
# Retirement Plan for Plugins

## Decide to retire

Propose a vote on the dev\-list to retire a plugin\. The vote should be open for the standard 72 hours to allow people to voice their opinions\. Send a cc to the users\-list\. Standard Apache voting rules apply\. Only PMC votes are binding\.

The vote must contain one or more options on how to retire the plugin\. There are multiple scenarios available\. Here are a couple that have been suggested:

1. Move to our retired area in svn
1. Move to another Apache project
1. Move to www\.mojohaus\.org, apache\-extras\.org or another forge

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
will be moved into the "retired" area in Subversion.

The process for retiring a plugin is described here:
https://maven.apache.org/developers/retirement-plan-plugins.html

The vote is open for 72 hours.

[ ] +1 Yes, it's about time
[ ] -1 No, because...
```

If the vote is successful, post the result to the dev list and cc the PMC and users list\. For instance:

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

If the vote passes, make one final release of the plugin \(with its own standard 72h vote on source release\) before it is retired\. This allows us to make a clean break\. The person who wants to retire a plugin is the one who does the final release\. Below you will find the extra steps that you need to follow when retiring a plugin, in addition to [our standard release process](\./release/maven\-project\-release\-procedure\.html)\.

## Make the final release

1. Create an issue in JIRA with the issue type &quot;Task&quot; and the summary &quot;Retire this plugin&quot;, and schedule it for the final release\. If the plugin includes a JIRA report in the generated site, you will need to close this issue before you make the release\.
1. Add the description &quot;This is the final version of this plugin\. It has been retired\.&quot; to the final version in JIRA\.
1. Add a prominent notice on the front page of the plugin&apos;s site, informing that the plugin is retired\. Suggested text:

    ```
    Note: This plugin is retired. It is no longer maintained.
    ```

    If the plugin is moved elsewhere, that should also be added to the plugin&apos;s site\. Suggested text:

    ```
    Note: This plugin has retired from the Apache Maven project,
    but has moved to the <Organization> <Project> project.
    ```

1. Add &quot; \(RETIRED\)&quot; at the end of `<project>`/`<name>` in the plugin&apos;s `pom.xml`\. This will show up on every page of the generated site\.
1. Go ahead with [the standard release process](\./release/maven\-project\-release\-procedure\.html), making sure that you follow the exceptions mentioned above regarding the site deployment\.
1. When updating the plugins page, move Maven Foo Plugin to under the &quot;Retired&quot; heading\. Remove the SVN and JIRA links and add the date of retirement\.
1. When updating the version in JIRA, do not add Y\.Z\+1 and make sure you remove any future versions\.
## Clean up after the release

1. Remove the `.Jenkinsfile` from all branches\. This will remove the project from https://builds\.apache\.org/job/maven\-box/
1. Remove the `.github` directory from default branch\. This will remove GitHub Actions
1. Ask INFRA for archive git repos \(gitbox \+ github\)
1. Republish documentation, postfix project name with \(RETIRED\)
1. When relevant update summary pages for [plugins](https://maven\.apache\.org/plugins/index\.html) or [shared components](https://maven\.apache\.org/shared/index\.html) 
1. Add &quot; \(RETIRED\)&quot; at the end of the project name in JIRA\.
1. Put the JIRA project in read\-only mode in case of standalone project \(own Jira key\): apply Maven Retired Project Permissions Scheme\. \(Requires JIRA admin karma: e\.g\. ask Brian Fox\)
1. Comment the [dist\-tool configuration](https://ci\-maven\.apache\.org/job/Maven/job/maven\-box/job/maven\-dist\-tool/job/master/site/dist\-tool\.conf\.html) entry\.
1. Remove from `Source Repository`
    1. [https://maven\.apache\.org/scm\.html](https://maven\.apache\.org/scm\.html)
    1. [https://github\.com/apache/maven\-sources](https://github\.com/apache/maven\-sources)
1. Remove distribution from current [dist area](https://dist\.apache\.org/repos/dist/release/maven/) \(history remains available in [archive](https://archive\.apache\.org/dist/maven/)\)\.
1. Update board report
1. Announce the fact that the plugin has been retired/moved on the announce@m\.a\.o and users@m\.a\.o mailing lists\. Explain to people what they should do if they would like to continue development of the plugin\.
<!-- Insert template for retirement email here-->
