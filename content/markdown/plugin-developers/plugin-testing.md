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
# Developers Centre - Testing Plugins Strategies

## Introduction

Currently, Maven only supports unit testing out of the box.
This document is intended to help Maven Developers test plugins with unit tests, integration tests, and functional tests.

## Testing Styles: Unit Testing vs. Functional/Integration Testing

A unit test attempts to verify a mojo as an isolated unit, by mocking out the rest of the Maven environment.
A mojo unit test does not attempt to run your plugin in the context of a real Maven build. Unit tests are designed to be fast.

A functional/integration test attempts to use a mojo in a real Maven build, by launching a real instance of Maven in a real project.
Normally this requires you to construct special dummy Maven projects with real POM files.
Often this requires you to have already installed your plugin into your local repository so it can be used in a real Maven build.
Functional tests run much more slowly than unit tests, but they can catch bugs that you may not catch with unit tests.

The general wisdom is that your code should be mostly tested with unit tests, but should also have some functional tests.

## Unit Tests

### Using JUnit alone

In principle, you can write a unit test of a plugin Mojo the same way you’d write any other JUnit test case.

However, many mojo methods need more information to work properly.
For example, you’ll probably need to inject or mock a reference to a `MavenProject`, so your mojo can query project variables.

### Using PlexusTestCase

Mojo variables are injected by Guice (an open-source software framework for the Java platform), sometimes with a Codehaus Plexus (a collection of components used by Apache Maven) adapter to support the legacy `@Component` annotation. 
Currently some mojos are fully guicified with constructor injection, while others that have not yet been converted use Plexus field injection.

Both Guice-based and Plexus-based mojos rely on the Guice Plexus adapter to inject dependencies by having the test class extend `PlexusTestCase` and calling the **lookup()** method to instantiate the mojo.
Tests for fully Guicified mojos can also inject dependencies directly into the constructor without extending `PlexusTestCase`.
These dependencies can be Mockito mocks or instances of the actual model classes.
If a particular test does not access the injected field — that is, it’s only injected to fulfill the constructor signature — you can usually also pass null as the value of that argument. 

With that said, if you need to inject Maven objects into your mojo, you’ll probably prefer to use the maven-plugin-testing-harness.

### Using the maven-plugin-testing-harness

The [maven-plugin-testing-harness](/plugin-testing/maven-plugin-testing-harness/) is explicitly intended to test the implementation of the `org.apache.maven.reporting.AbstractMavenReport#execute()` method.

In general, you need to include `maven-plugin-testing-harness` as a test-scoped dependency.

```xml
...
  <dependencies>
    ...
    <dependency>
      <groupId>org.apache.maven.plugin-testing</groupId>
      <artifactId>maven-plugin-testing-harness</artifactId>
      <!-- use the latest version of the maven-plugin-testing-harness, >= 3.4.0 -->
      <version>3.4.0</version>
      <scope>test</scope>
    </dependency>
    ...
  </dependencies>
...
```

#### JUnit Jupiter (JUnit 5) style tests

JUnit Jupiter uses an extension framework for which the `MojoExtension` is provided by the `maven-plugin-testing-harness`. 
You can annotate your JUnit Jupiter test with `@MojoTest` and with that leverage the `MojoExtension` to inject the Mojo under test.
This functionality was introduced in version `3.4.0` of the `maven-plugin-testing-harness`.
Below is an example:

```java
@MojoTest
public class YourMojoTest {

    private static final String POM = "src/test/resources/unit/basic-test/basic-test-plugin-config.xml";

    @Test
    @InjectMojo(goal = "generate", pom = POM)
    void simpleMojo(YourMojo mojo) {
        assertNotNull( mojo );
    }
}
```

#### JUnit 4 style tests (deprecated)
There is the deprecated way to write tests using JUnit 4 style. 
This is not recommended, but you can still use it on Maven 3. 
For Maven 4 only JUnit Jupiter style tests are available and JUnit 4 is not supported there anymore.
Please consider migrating your JUnit 4 style MojoTests to JUnit Jupiter tests.
Below is an example:

```java
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

    public void testMojoGoal() throws Exception
    {
        File testPom = new File( getBasedir(),
          "src/test/resources/unit/basic-test/basic-test-plugin-config.xml" );

        YourMojo mojo = (YourMojo) lookupMojo( "yourGoal", testPom );

        assertNotNull( mojo );
    }
}
```

#### More information
For more information, please refer to the [Maven Plugin Harness Wiki](https://cwiki.apache.org/confluence/display/MAVENOLD/Maven+Plugin+Harness)

## Integration/Functional testing

### maven-verifier

maven-verifier tests are run using JUnit, and provide a simple class allowing you to launch Maven and assert on its log file and built artifacts.
It also provides a `ResourceExtractor`, which extracts a Maven project from the src/test/resources directory into a temporary working directory where you can do tricky stuff with it.
Follow the [Getting Started](/shared/maven-verifier/getting-started.html) guide to learn more about creating maven-verifier tests.

Maven itself uses maven-verifier to run its core integration tests.
For more information, see [Creating a Maven Integration Test](https://cwiki.apache.org/confluence/display/MAVEN/Creating+a+Maven+Integration+Test).

**Note**: maven-verifier and maven-verifier-plugin sound similar, but are totally different unrelated pieces of code.
maven-verifier-plugin simply verifies the existence/absence of files on the filesystem.
You could use it for functional testing, but you may need more features than maven-verifier-plugin provides.

### maven-invoker-plugin

You can use [maven-invoker-plugin](https://maven.apache.org/plugins/maven-invoker-plugin/) to invoke Maven and to provide some BeanShell/Groovy tests.
Tests written in this way don’t run under JUnit/TestNG; instead, they’re run by Maven itself.

You can take a look at the [maven-install-plugin](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-install-plugin/src/it/) to see how integration tests are written.

```xml
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
```

