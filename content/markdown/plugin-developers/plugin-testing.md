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

# Introduction

Currently, Maven only supports unit testing out of the box. This document is intended to help Maven Developers test plugins with unit tests, integration tests, and functional tests.

<!--  <<Note: There are a lot of different ways to test a Maven plugin.>>  For a review of different strategies and tools, please refer to "Review of Plugin Testing Strategies."-->

# Testing Styles: Unit Testing vs. Functional/Integration Testing

A unit test attempts to verify a mojo as an isolated unit, by mocking out the rest of the Maven environment. A mojo unit test does not attempt to run your plugin in the context of a real Maven build. Unit tests are designed to be fast.

A functional/integration test attempts to use a mojo in a real Maven build, by launching a real instance of Maven in a real project. Normally this requires you to construct special dummy Maven projects with real POM files. Often this requires you to have already installed your plugin into your local repository so it can be used in a real Maven build. Functional tests run much more slowly than unit tests, but they can catch bugs that you may not catch with unit tests.

The general wisdom is that your code should be mostly tested with unit tests, but should also have some functional tests.

# Unit Tests

## Using JUnit alone

In principle, you can write a unit test of a plugin Mojo the same way you'd write any other JUnit test case.

When using injections in your Mojo, you can simply use a mocking framework such as Mockito to create mock instances of the injected dependencies, 
and pass them to the Mojo constructor (if using constructor injection) or set them on the Mojo instance (if using field injection).

Simple example using Mockito with constructor injection:

```java
@Mojo(name = "sayhi")
public class GreetingMojo extends AbstractMojo {
    
    private final MavenProject project;
    
    @Inject
    public GreetingMojo(MavenProject project) {
        this.project = project;
    }
    
    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Hello, world.");
    }
}
```

and the corresponding unit test:

```java
@ExtendWith(MockitoExtension.class)
class GreetingMojoTest {
    @Mock
    private MavenProject mavenProject;
    
    @InjectMocks
    private GreetingMojo mojo;
    
    @Test
    void testExecute() throws MojoExecutionException {
        // Execute the Mojo
        mojo.execute();
        
        // Verify behavior or state as needed
        // (e.g., check interactions with mock, etc.)
    }
}
```

<a id="Using_PlexusTestCase"></a>

## Using plexus-testing

Mojo variables are injected by Guice, sometimes with a Plexus adapter to support the legacy `@Component` annotation. Currently some mojos are fully guicified with constructor injection, while others that have not yet been converted use Plexus field injection.

Tests for fully Guicified mojos can inject dependencies directly into the constructor. These dependencies can be Mockito mocks or instances of the actual model classes. If a particular test does not access the injected field — that is, it's only injected to fulfill the constructor signature — you can usually also pass null as the value of that argument.

To test the components a mojo uses together with their real dependencies, annotate the test class with `@PlexusTest` from [plexus-testing](https://github.com/codehaus-plexus/plexus-testing). It starts a Plexus container for each test, injects the test class's `@Inject` fields, and lets you look components up from the injected `PlexusContainer`. It replaces the JUnit 3 base class `PlexusTestCase`.

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.junit</groupId>
      <artifactId>junit-bom</artifactId>
      <version>5.11.0</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>org.codehaus.plexus</groupId>
    <artifactId>plexus-testing</artifactId>
    <version>2.2.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.eclipse.sisu</groupId>
      <artifactId>sisu-maven-plugin</artifactId>
      <version>1.1.0</version>
      <executions>
        <execution>
          <goals>
            <goal>main-index</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

The `junit-bom` import keeps the JUnit version that plexus-testing brings in line with your other JUnit dependencies; without it, mixed versions fail with a `NoSuchMethodError`. The container finds `@Named` components through the index in `META-INF/sisu/javax.inject.Named`, which the `main-index` goal of sisu-maven-plugin writes. A plugin that inherits from the Maven parent POM gets the `junit-bom` import and the plugin's configuration from its parent, and only needs to add `sisu-maven-plugin` to its `<build><plugins>`.

For a component such as the following (its annotations come from `javax.inject`, which a plugin usually declares with `provided` scope)

```java
@Named
@Singleton
public class Greeter {
    public String greet(String name) {
        return "Hello " + name;
    }
}
```

the test looks like this:

```java
@PlexusTest
class GreeterTest {
    @Inject
    private Greeter greeter;

    @Inject
    private PlexusContainer container;

    @Test
    void injectsAndLooksUp() throws Exception {
        assertEquals("Hello Maven", greeter.greet("Maven"));
        assertEquals("Hello lookup", container.lookup(Greeter.class).greet("lookup"));
    }
}
```

With that said, if you need to inject Maven objects into your mojo, you'll probably prefer to use the maven-plugin-testing-harness.

## maven-plugin-testing-harness

The [maven-plugin-testing-harness](/plugin-testing/maven-plugin-testing-harness/) is explicitly intended to test the Maven plugin `Mojo` implementation.

In general, you need to include `maven-plugin-testing-harness` as a test-scoped dependency, and create a `MojoTest`.

```xml
...
  <dependencies>
    ...
    <dependency>
      <groupId>org.apache.maven.plugin-testing</groupId>
      <artifactId>maven-plugin-testing-harness</artifactId>
      <version>3.4.0</version>
      <scope>test</scope>
    </dependency>
    ...
  </dependencies>
...
```

```java

@MojoTest
class YourMojoTest {
    
    @Inject
    private MavenProject mavenProject; 
    
    @Provides
    private MavenProject project() {
        // Return a MavenProject instance for testing
        return mock(MavenProject.class);
    }
    
    @Test
    @InjectMojo(goal = "yourGoal", pom="src/test/resources/unit/basic-test/basic-test-plugin-config.xml")
    @MojoParameter(name = "parameter1", value = "value1")
    void testMojoGoal(YourMojo mojo) throws Exception {
        
        // adjust mock behavior as needed
        when(mavenProject.getVersion()).thenReturn("1.0.0");
        
        // execute the Mojo
        mojo.execute();

        // Verify behavior or state as needed
        verify(mavenProject).getVersion();
        verifyNoMoreInteractions(mavenProject);
    }
}
```


# Integration/Functional testing

## maven-executor

[Maven Executor](/executor/) runs Maven from JUnit tests, either forked or embedded in the test JVM, and captures its output for your assertions. Maven itself uses it to run its core integration tests. For more information, see [Creating a Maven Integration Test](https://cwiki.apache.org/confluence/display/MAVEN/Creating+a+Maven+Integration+Test).

## maven-verifier

maven-verifier is retired, and 2.0.0 is its final release. Existing tests keep working against that release, but new tests should use [Maven Executor](#maven-executor); the [migration guide](https://github.com/apache/maven-verifier/blob/master/MIGRATION.md) covers moving existing tests.

## maven-invoker-plugin

You can use [maven-invoker-plugin](https://maven.apache.org/plugins/maven-invoker-plugin/) to invoke Maven and to provide some BeanShell/Groovy tests. Tests written in this way don't run under JUnit/TestNG; instead, they're run by Maven itself.

You can take a look at the [maven-install-plugin](https://github.com/apache/maven-install-plugin/tree/master/src/it) to see how integration tests are written.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-invoker-plugin</artifactId>
        <version>3.10.1</version>
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

