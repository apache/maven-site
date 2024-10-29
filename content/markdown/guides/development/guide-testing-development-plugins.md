---
title: Guide to Testing Development Versions of Plugins
author: 
  - Brett Porter
date: 2009-08-02
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
<!-- http://maven.apache.org/doxia/references/apt-format.html-->
# Guide to Testing Development Versions of Plugins

## Why would I want to do this?

If a bug you are encountering has been reported as fixed but not yet released, you can confirm that it has been fixed for you\. Or perhaps you just like to live on the bleeding edge\.

You are highly encouraged to join the development list for the project and provide your feedback, or help promote release of the plugin in question\.

_Note:_ This is **not** recommended as an everyday or in production practice\! Snapshots are for testing purposes only and are not official releases\. For more information, see [ the Releases FAQ](http://www\.apache\.org/dev/release\.html\#what)\.

## How do I do this?

Development versions of Maven plugins are periodically published to the repository: [https://repository\.apache\.org/snapshots/](https://repository\.apache\.org/snapshots/)\.

_Note:_ Currently, this is not done automatically by our continuous integration setup\. This is coming soon\.

Other sites may publish there own \- for example, the MojoHaus project hosts theirs at [https://oss\.sonatype\.org/content/repositories/snapshots/](https://oss\.sonatype\.org/content/repositories/snapshots/)

The first step is to include this in your project:

```
<project>
  ...
  <pluginRepositories>
    <pluginRepository>
      <id>apache.snapshots</id>
      <url>https://repository.apache.org/snapshots/</url>
    </pluginRepository>
  </pluginRepositories>
  ...
</project>
```

After this is included, there are three ways to use the updated versions:

- Set the appropriate version in the plugin, eg `2.0.1-SNAPSHOT`
- If you have not specified a version, use the `-U` switch to update plugins for the given Maven run
- You can have Maven automatically check for updates on a given interval, for example:

    ```
    <project>
      ...
      <pluginRepositories>
        <pluginRepository>
          <id>apache.snapshots</id>
          <url>https://repository.apache.org/snapshots/</url>
        </pluginRepository>
      </pluginRepositories>
      ...
    </project>
    ```

_Note:_ These last two techniques mean that _every_ plugin will be updated to the latest snapshot version\.

The development version will stop being used if the `<pluginRepository>` element is removed from your POM and the version is set back to the release version\. If you are using the command line or an unspecified version, you will also need to remove the version from the local repository\.

## Using Settings without Modifying the Project

If you are using the goals from the command line on a number of projects, you should include this in your `settings.xml` file instead\.

You need to modify your `${user.home}/.m2/settings.xml` file to include two new profiles and then when you need access to the plugin snapshots use `-Papache`\. The profile only needs to be enabled once so that the plugins can be downloaded into you local repository\. Once in your local repository Maven can successfully resolve the dependencies and the profile no longer needs to be activated\.

```
<settings>
  ...
  <profiles>
    <profile>
      <id>apache</id>
      <pluginRepositories>
        <pluginRepository>
          <id>apache.snapshots</id>
          <name>Maven Plugin Snapshots</name>
          <url>https://repository.apache.org/snapshots/</url>
          <releases>
            <enabled>false</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
  ...
</settings>
```

When invoking Maven for Apache profile, do it like this:

```
mvn -Papache <phase|goal>
```

## Using a Repository Manager

In addition to the above you may want to use a repository manager so that you can retain the builds you have been using\. For information on this technique, see the [ Guide to Testing Staged Releases](\./guide\-testing\-releases\.html)\.

## How do I make changes to the source and test development versions of the plugins?

For information on this, see the [Guide to Maven Development](\./guide\-maven\-development\.html)\.

