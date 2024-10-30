---
title: Releasing A Maven Project
author: 
  - Jason van Zyl
  - Karl Heinz Marbaise
date: 2018-04-07
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
# Performing a Maven Project Release

This document covers the common release procedures used by the Maven team to perform releases\.

## Prerequisites

Be sure that:

- you have a recent Git client installed and on your shell&apos;s path\.
- you have JDK 8 installed and on your shell&apos;s path to build Maven 3\.7\.0\+\. Details about minimum JDK version to build an appropriate version can be found here: [https://maven\.apache\.org/docs/history\.html](/docs/history\.html)
- if you receive an OutOfMemoryError during the build, make sure to have set the environment variable `MAVEN_OPTS=-Xmx512m`
- you must use Maven 3\.3\.9\+\.
- follow Apache environment configuration steps outlined at: [Publishing Maven Artifacts](https://www\.apache\.org/dev/publishing\-maven\-artifacts\.html\#dev\-env)\.
- check [ your Public GPG key](\./pmc\-gpg\-keys\.html) which will be used for release\.
## Before you begin

If you started here, you may first want to review one of the following documents that cover the specifics of various types of releases we have in the Maven Project:

- [ Releasing Maven Core](\./maven\-core\-release\.html)
- [ Releasing a parent POM](\./parent\-pom\-release\.html)
## Consider updating the parent versions

If the item you are planning to release is not using the most recent version of its parent \(see [parent POMs](\.\./\.\./pom/) index\), consider taking this opportunity to update to it\.

## Consider updating the project dependencies

Generally it is a good that project use tha latest available versions of used dependencies\.

## Prepare release notes

- check all commits since latest release, all mentioned issue should be connected with release notes
- all issues should be **assigned** to responsible person, which fixed or merged fix
- all issues should be fixed and **CLOSED**
- check existing issues with **Blocker** priority, consider to fix it or change priority if reasonable
### Check release note at GitHub

If a component has a history of release notes at **GitHub**, we should to continue maintain it in order to avoid user confusing\.

We can maintain release notes at **GitHub**, by one of:

- use release\-drafter to help prepare release notes \- in such case all PRs should a proper label
- copy paste release notes from JIRA
- put link to release notes in JIRA

Draft of release notes at **GitHub** can be prepared before publishing\.

## Make sure that site compilation works

Particularly if you update the parent or if you updated your javadoc, the site compilation process may fail, or reveal a conspicuous error\. It is stressful and time\-consuming to discover this \*after\* you stage a release and then try to follow the procedure to deploy the site for review\. So you may find it more pleasant to check out the state of the site before you start:

```
mvn -Preporting site site:stage
```

## Stage the Release

1. Follow the release preparation, **staging** and **closing** the repository steps outlined in [Publishing Maven Releases](https://infra\.apache\.org/publishing\-maven\-artifacts\.html)\.

    Staging repository `MUST` be closed with message like `VOTE Maven component name x.y.z`

1. Stage the latest documentation as explained in [deploying Maven components reference documentation](\.\./website/deploy\-component\-reference\-documentation\.html)\.
## Call the vote

Propose a vote on the dev list with the closed issues, the issues left, the staging repository and the staging site\. For instance:

```
To: "Maven Developers List" <dev@maven.apache.org>
Subject: [VOTE] Release Apache Maven XXX Plugin version Y.Z

Hi,

We solved N issues:
https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=XXXXXX&version=YYYYYYY&styleName=Text

There are still a couple of issues left in JIRA:
https://issues.apache.org/jira/issues/?jql=project%20%3D%20XXXXXXXXXX%20AND%20resolution%20%3D%20Unresolved%20ORDER%20BY%20key%20DESC%2C%20priority%20DESC

Staging repo:
https://repository.apache.org/content/repositories/maven-[YOUR REPOSITORY ID]/
https://repository.apache.org/content/repositories/maven-[YOUR REPOSITORY ID]/[PATH-TO]-source-release.zip

Source release checksum(s):
[NAME-OF]-source-release.zip sha512: [SHA512SUM]

Staging site:
https://maven.apache.org/plugins-archives/maven-XXX-plugin-LATEST/

Guide to testing staged releases:
https://maven.apache.org/guides/development/guide-testing-releases.html

Vote open for at least 72 hours.

[ ] +1
[ ] +0
[ ] -1
```

To get the JIRA release notes link, browse to the plugin&apos;s or component&apos;s JIRA page, select the _Releases_ link, pick a version, and use the link to _Release Notes_ that is next to the version being released\.

To get the list of issues left in JIRA, browse to the plugin&apos;s or component&apos;s JIRA page, and from the _Preset Filters_ on the right, use the link for _Outstanding_ issues\.

The &quot;vote is open for at least 72 hours&quot; means that you need to wait _at least_ 72 hours before proceeding\. This gives others time to test your release and check that everything is good\. If after that period you have not received enough \+1 votes to reach the quorum, this doesn&apos;t mean the vote failed: it just takes a bit longer\.

Checking binary output, ideally to control that the build is reproducible, can be done by running `verify` \(NOT `install`\) followed by `artifact:compare` against the staged content:

```
mvn -Papache-release -Dgpg.skip clean verify \
    artifact:compare -Dreference.repo=https://repository.apache.org/content/repositories/staging/
```

## Check the vote results

Copied from [Votes on Package Releases](https://www\.apache\.org/foundation/voting\.html\#ReleaseVotes)\.

```
Votes on whether a package is ready to be released use majority approval
-- i.e. at least three PMC members must vote affirmatively for release,
and there must be more positive than negative votes. Releases may not be vetoed.
Generally the community will cancel the release vote if anyone identifies serious problems,
but in most cases the ultimate decision, lies with the individual serving as release manager.
The specifics of the process may vary from project to project,
but the 'minimum quorum of three +1 votes' rule is universal.
```

The list of PMC members is available at [https://people\.apache\.org/committers\-by\-project\.html\#maven\-pmc](https://people\.apache\.org/committers\-by\-project\.html\#maven\-pmc)\.

### Successful vote

Once a vote is **successful**, post the result to the `dev` list and cc the `PMC`\. For instance:

```
To: "Maven Developers List" <dev@maven.apache.org>
CC: "Maven Project Management Committee List" <private@maven.apache.org>
Subject: [RESULT] [VOTE] Release Apache Maven XXX Plugin version Y.Z

Hi,

The vote has passed with the following result:

+1 : <<list of names>>

PMC quorum: ...

I will promote the source release zip file to Apache distribution area and the artifacts to the central repo.
```

Follow the procedure to the end\.

### Unsuccessful vote

Once a vote is **unsuccessful**, post the result to the `dev` list, For instance:

```
To: "Maven Developers List" <dev@maven.apache.org>
Subject: [CANCEL] [VOTE] Release Apache Maven XXX Plugin version Y.Z

Hi,

The vote has been canceled.
```

For canceled vote the process will need to be restarted\.

Be sure to:

- drop your staging repository as described in [Drop a repository](https://www\.apache\.org/dev/publishing\-maven\-artifacts\.html)
- create new version for next round `Y.Z+1`
- assign issues from version `Y.Z` also to version `Y.Z+1`
- mark the `Y.Z` version as **archived**
- report found issues and assign them to version `Y.Z+1`
- fix found issues

Start the process for version `Y.Z+1` from the beginning\.

## Copy the source release to the Apache Distribution Area

The official Apache release is the &apos;source\-release&apos; bundle distributed in `www.apache.org/dist`, as described in [Apache Release Distribution Policy](https://www\.apache\.org/dev/release\-distribution)\. All releases for Maven must be copied to [the official Maven release area](https://www\.apache\.org/dist/maven/)\.

The release area is maintained with svnpubsub\. To deliver a release, you add it to [the subversion repository for the dist area](https://dist\.apache\.org/repos/dist/release/maven): add the release, its signature and sha512 checksum files, copying them from `target/checkout/target/` directory created during `mvn release:perform` step\. Currently this requires to be in maven\-pmc group \(see [INFRA\-5945](https://issues\.apache\.org/jira/browse/INFRA\-5945)\)\. If you are not a PMC member, drop a line to _private@maven\.apache\.org_ and ask them to do this step \(and the next one\) for you: the PMC member will get the source release bundle and its signature from Nexus staging repository and will create sha512 checksum file by hand, for example using shell:

```
for f in *.zip ; do echo -n $(sha512sum $f | cut -c -128) > $f.sha512 ; done
```

For example:

```
wagon/wagon-2.2-source-release.zip
wagon/wagon-2.2-source-release.zip.asc
wagon/wagon-2.2-source-release.zip.sha512
```

You should also run &apos;svn rm&apos; as needed to clear out older releases\. As per [Apache Release Policy](https://www\.apache\.org/legal/release\-policy\.html\#where\-do\-releases\-go), only the latest release on a branch should stay in the main dist areas\. So long as the previous release is at least a day old, the automatic archiver will have copied it to the archive\.

To check that everything is ok in the dist area, dist\-tool\-plugin has been written and run once a day to produce [&quot;Check Source Release&quot; and &quot;Check Errors&quot; reports](https://ci\-maven\.apache\.org/job/Maven/job/maven\-box/job/maven\-dist\-tool/job/master/site/)\.

## Add the release for next board report

After committing the 3 source\-release files, visit [Apache Committee Report Helper](https://reporter\.apache\.org/addrelease\.html?maven) to add your release data with the Full Version Name and Date of Release\. \(You will receive an e\-mail for it as well\)\.

If you are not a PMC member, drop a line to _private@maven\.apache\.org_ and ask them to do this step for you if they did not do the update while adding to release area\.

## Promote the release

Once the release is deemed fit for public consumption it can be transferred to a production repository where it will be available to all users\.

1. See [Promoting a Repo](https://www\.apache\.org/dev/publishing\-maven\-artifacts\.html\#promote) for details on promotion\.
1. Deploy the current website

    As above, deploy the web site if appropriate and update the project site for the new release: use [Component Reference Documentation Helper](\.\./website/component\-reference\-documentation\-helper\.html) to generate commands or see [Publishing versioned component reference documentation](\.\./website/deploy\-component\-reference\-documentation\.html\#Publishing\_versioned\_component\_reference\_documentation) for explanations\. Note that not all projects follow these conventions exactly\.

    In case there&apos;s an overview table with version \(e\.g\. [plugins](/plugins/index\.html), [shared](/shared/index\.html), [skins](/skins/index\.html)\), update the table: you can directly edit it on the github page\.

    For plugins and shared, you can also run `mvn -Pupdate package` of the site, then review and commit proposed `index.apt` updates\.

1. Update the version tracking in JIRA

    In the relevant project, go to Administration, then Versions\. Mark the `Y.Z` version as &apos;released&apos;\. Create version `Y.Z+1`, if that hasn&apos;t already been done\. You may also archive any deprecated releases \(milestones or alphas\) at this time\.

    Note: Currently this requires to be in the maven\-pmc group\. So, if you don&apos;t see the Administration option in JIRA, kindly ask _private@maven\.apache\.org_ to do this step for you\.

1. Wait for everything to sync
    1. Sync to [Maven Central](https://repo\.maven\.apache\.org/maven2/org/apache/maven/)

        The sync into central staging from repository\.apache\.org occurs every 4 hours\. There is a separate hourly schedule that runs which pushes from staging to the other central machines, and then updates the indexes\.

    1. Sync to Maven Website

        If the project you are releasing doesn&apos;t yet use svnpubsub for site deployment, the deployment of the Maven website will [take an hour or so to sync](https://www\.apache\.org/dev/release\-publishing\.html\#sync\-delay)\.

1. Create an announcement\.

    Following instructions are done for plugins: if you are releasing anything else than a plugin, you should adapt email content to match what you are releasing\.

    **Note:** You must send this email from your apache email account, e\.g\. YOUR\_APACHE\_USERNAME@apache\.org otherwise the email to `announce@maven.apache.org` will bounce\.

    ```
    From: YOUR_APACHE_USERNAME@apache.org
    To: announce@maven.apache.org, users@maven.apache.org
    Cc: dev@maven.apache.org
    Subject: [ANN] Apache Maven XXX Plugin Y.Z Released
    
    The Apache Maven team is pleased to announce the release of the Apache Maven XXX Plugin, version Y.Z
    
    This plugin (insert short description of the plugin's purpose).
    
    https://maven.apache.org/plugins/maven-XXX-plugin/
    
    You should specify the version in your project's plugin configuration:
    
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-XXX-plugin</artifactId>
      <version>Y.Z</version>
    </plugin>
    
    You can download the appropriate sources etc. from the download page:
    
    https://maven.apache.org/plugins/maven-XXX-plugin/download.cgi
    
    Release Notes - Maven XXX Plugin - Version Y.Z
    
    (Copy Here Release Notes in Text Format from JIRA)
    
    Enjoy,
    
    -The Apache Maven team
    
    ```

1. If releasing the Apache Parent POM, notify `release-discuss@apache.org`: Several projects follow this list, and should be made aware of changes to the common parent\. This might also be a step to take if other shared resources are released, or if plugin releases are of particular interest to that group\.

    If releasing Maven Core, notify `announce@apache.org`

1. Publish release notes at **GitHub** if it is maintained\.
1. Celebrate :o\)
## Check Consistency

You can also check if you \(or any previous release manager\) did not miss any step by looking at the [ Dist Tool Check Errors report](https://ci\-maven\.apache\.org/job/Maven/job/maven\-box/job/maven\-dist\-tool/job/master/site/dist\-tool\-check\-errors\.html)\.
