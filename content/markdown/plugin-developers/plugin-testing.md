---
title: Developers centre - Testing Plugins Strategies
author: 
  - Vincent Siveton
date: 2008-01-01  
2015-06-16
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
# Introduction

Currently, Maven only supports unit testing out of the box\. This document is intended to help Maven Developers test plugins with unit tests, integration tests, and functional tests\.

<!--  <<Note: There are a lot of different ways to test a Maven plugin.>>  For a review of different strategies and tools, please refer to {{{http://docs.codehaus.org/display/MAVENUSER/Review+of+Plugin+Testing+Strategies}Review of Plugin Testing Strategies}}-->
# Testing Styles: Unit Testing vs\. Functional/Integration Testing

A unit test attempts to verify a mojo as an isolated unit, by mocking out the rest of the Maven environment\. A mojo unit test does not attempt to run your plugin in the context of a real Maven build\. Unit tests are designed to be fast\.

A functional/integration test attempts to use a mojo in a real Maven build, by launching a real instance of Maven in a real project\. Normally this requires you to construct special dummy Maven projects with real POM files\. Often this requires you to have already installed your plugin into your local repository so it can be used in a real Maven build\. Functional tests run much more slowly than unit tests, but they can catch bugs that you may not catch with unit tests\.

The general wisdom is that your code should be mostly tested with unit tests, but should also have some functional tests\.

# Unit Tests

## Using JUnit alone

In principle, you can write a unit test of a plugin Mojo the same way you&apos;d write any other JUnit test case, by writing a class that `extends TestCase`\.

However, most mojos need more information to work properly\. For example, you&apos;ll probably need to inject a reference to a MavenProject, so your mojo can query project variables\.

## Using PlexusTestCase

Mojo variables are injected using Plexus, and many Mojos are written to take specific advantage of the Plexus container \(by executing a lifecycle or having various injected dependencies\)\.

If all you need are Plexus container services, you can write your class with `extends PlexusTestCase` instead of TestCase\.

With that said, if you need to inject Maven objects into your mojo, you&apos;ll probably prefer to use the maven\-plugin\-testing\-harness\.

## maven\-plugin\-testing\-harness

The [maven\-plugin\-testing\-harness](/plugin\-testing/maven\-plugin\-testing\-harness/) is explicitly intended to test the `org.apache.maven.reporting.AbstractMavenReport#execute()` implementation\.

In general, you need to include `maven-plugin-testing-harness` as a dependency, and create a \*MojoTest \(by convention\) class which `extends AbstractMojoTestCase`\.

```
...
  <dependencies>
    ...
    <dependency>
      <groupId>org.apache.maven.plugin-testing</groupId>
      <artifactId>maven-plugin-testing-harness</artifactId>
      <version>3.3.0</version>
      <scope>test</scope>
    </dependency>
    ...
  </dependencies>
...
```

```
public class YourMojoTest
    extends AbstractMojoTestCase
{
    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        // required for mojo lookups to work
        super.setUp();
    }

    /**
     * @throws Exception
     */
    public void testMojoGoal() throws Exception
    {
        File testPom = new File( getBasedir(),
          "src/test/resources/unit/basic-test/basic-test-plugin-config.xml" );

        YourMojo mojo = (YourMojo) lookupMojo( "yourGoal", testPom );

        assertNotNull( mojo );
    }
}
```

For more information, refer to [Maven Plugin Harness Wiki](http://cwiki\.apache\.org/confluence/display/MAVENOLD/Maven\+Plugin\+Harness)

# Integration/Functional testing

## maven\-verifier

maven\-verifier tests are run using JUnit or TestNG, and provide a simple class allowing you to launch Maven and assert on its log file and built artifacts\. It also provides a ResourceExtractor, which extracts a Maven project from your src/test/resources directory into a temporary working directory where you can do tricky stuff with it\. Follow the [Getting Started](/shared/maven\-verifier/getting\-started\.html) guide to learn more about creating maven\-verifier tests\.

Maven itself uses maven\-verifier to run its core integration tests\. For more information, please refer to [Creating a Maven Integration Test](https://cwiki\.apache\.org/confluence/display/MAVEN/Creating\+a\+Maven\+Integration\+Test)\.

**Note**: maven\-verifier and maven\-verifier\-plugin sound similar, but are totally different unrelated pieces of code\. maven\-verifier\-plugin simply verifies the existence/absence of files on the filesystem\. You could use it for functional testing, but you may need more features than maven\-verifier\-plugin provides\.

## maven\-invoker\-plugin

You can use [maven\-invoker\-plugin](https://maven\.apache\.org/plugins/maven\-invoker\-plugin/) to invoke Maven and to provide some BeanShell/Groovy tests\. Tests written in this way don&apos;t run under JUnit/TestNG; instead, they&apos;re run by Maven itself\.

You can take a look at the [maven\-install\-plugin](https://svn\.apache\.org/repos/asf/maven/plugins/trunk/maven\-install\-plugin/src/it/) how there are integration tests are written\.

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-invoker-plugin</artifactId>
        <version>1.10</version>
        <configuration>
          <projectsDirectory>src/it</projectsDirectory>
          <pomIncludes>
            <pomInclude>**/pom.xml</pomInclude>
          </pomIncludes>
          <postBuildHookScript>verify</postBuildHookScript>
        </configuration>
        <executions>
          <execution>
            <goals>
              <goal>run</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      ...
    </plugins>
  </build>
  ...
</project>
```

