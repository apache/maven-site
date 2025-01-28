# Maven DI

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

## What is Maven DI?

Maven DI is the dependency injection framework of Maven, that is introduced in Maven 4.

It is the successor for Plexus DI (used in Maven 2) and JSR 330/Eclipse Sisu (used in Maven 3) in Maven. 
It based on [Google Guice][guice]. (?)





## How to use Maven DI

When you use Maven DI in Maven plugins or extensions, you want to have the dependency to `maven-api-di`, so you can use the `@Inject`, `@Named`, and `@Singleton` annotations in your plugins and extensions.
The annotations of Maven DI are similiar to JSR 330.

### Implementation Details

TBD (need help)

## How to use Maven DI in plugins

Let's take a look at an example plugin: If you want to look at this example project, you can find the code [in Maven Core ITs][maven-di-plugin].

The POM is setup for Maven DI as previously mentioned, with the `maven-api-di` dependency

In addition, we add a Maven plugin dependency `maven-api-core` to implement the interface `Mojo` and use the Java  Plugin Tools Annotations with associated `maven-plugin-plugin` (see [Maven Plugin Tools](/plugin-tools/)).


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.1.0 https://maven.apache.org/xsd/maven-4.1.0.xsd">
    <modelVersion>4.1.0</modelVersion>

    <groupId>org.apache.maven.its</groupId>

    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>mavendi-maven-plugin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>maven-plugin</packaging>

    <name>mavendi-maven-plugin Maven Plugin</name>
    <url>http://maven.apache.org</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <javaVersion>17</javaVersion>
        <mavenVersion>4.0.0-beta-5</mavenVersion>
        <mavenPluginPluginVersion>4.0.0-beta-1</mavenPluginPluginVersion>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-api-core</artifactId>
            <version>${mavenVersion}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-api-di</artifactId>
            <version>${mavenVersion}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-api-meta</artifactId>
            <version>${mavenVersion}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-plugin-plugin</artifactId>
                <version>${mavenPluginPluginVersion}</version>
                <configuration>
                    <extractors>
                        <extractor>java-annotations</extractor>
                    </extractors>
                    <skipErrorNoDescriptorsFound>true</skipErrorNoDescriptorsFound>
                </configuration>
                <executions>
                    <execution>
                        <id>default-descriptor</id>
                        <phase>process-classes</phase>
                        <configuration>
                            <internalJavadocBaseUrl>./apidocs/</internalJavadocBaseUrl>
                        </configuration>
                    </execution>
                    <execution>
                        <id>generate-helpmojo</id>
                        <goals>
                            <goal>helpmojo</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>

```


Now let's take a look at the plugin code.
We are using field injection to add the `MavenDIComponent` to our Mojo.

```java
import org.apache.maven.api.Lifecycle.Phase;
import org.apache.maven.api.di.Inject;
import org.apache.maven.api.plugin.Log;
import org.apache.maven.api.plugin.annotations.Mojo;
import org.apache.maven.api.plugin.annotations.Parameter;

@Mojo(name = "hello", defaultPhase = Phase.VALIDATE, projectRequired = false)
public class HelloMojo implements org.apache.maven.api.plugin.Mojo {

    @Inject
    private MavenDIComponent component;

    @Inject
    private Log logger;

    @Parameter(property = "name")
    private String name;

    public void execute() {
        //
        // Say hello to the world, my little constructor injected component!
        //
        String message = component.hello(name);
        logger.info(message);
    }
}
```

The component that should be injected is annotated with `@Named` and `@Singleton`.

```java
import org.apache.maven.api.di.Named;
import org.apache.maven.api.di.Singleton;

@Named
@Singleton
public class MavenDIComponent {

    public String hello(String name) {
        return "Hello " + name + "! I am a component that is being used via field injection!";
    }
}
```

The next question is how to write a unit test for our Mojo.
Maven DI introduces new helper classes for testing.
They are packaged in [Maven Plugin Testing Harness](maven-plugin-testing-harness)
They have a good integration to de facto standard testing framework [JUnit Jupiter](junit-jupiter).

So the next step is to add all dependencies that are needed for testing.

For the helper classes, we are adding `maven-plugin-testing-harness`.
The dependencies to `maven-core`, `maven-resolver-api`, `maven-api-impl` and `guice` are only needed for the test runtime.
`junit-jupiter` is the used test framework, `mockito-*` is the used mock framework and `assertj` is the used annotation lib. 
The last two mention dependencies are optional, but helpful.

```xml

<!-- ... -->
  <properties>
    <!-- ... -->
    <mavenPluginTestingVersion>4.0.0-beta-2</mavenPluginTestingVersion>
    <guiceVersion>6.0.0</guiceVersion>
    <mavenResolverVersion>2.0.2</mavenResolverVersion>
  </properties>

  <dependencies>
  <!-- ... -->

      <dependency>
          <groupId>org.apache.maven.plugin-testing</groupId>
          <artifactId>maven-plugin-testing-harness</artifactId>
          <version>${mavenPluginTestingVersion}</version>
          <scope>test</scope>
      </dependency>


      <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-core</artifactId>
            <version>${mavenVersion}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.maven.resolver</groupId>        
            <artifactId>maven-resolver-api</artifactId>
            <version>${mavenResolverVersion}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-api-impl</artifactId>
            <version>${mavenVersion}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
            <version>${guiceVersion}</version>
            <scope>test</scope>
        </dependency>

      <dependency>
          <groupId>org.junit.jupiter</groupId>
          <artifactId>junit-jupiter-api</artifactId>
          <scope>test</scope>
      </dependency>
      <dependency>
          <groupId>org.mockito</groupId>
          <artifactId>mockito-core</artifactId>
          <scope>test</scope>
      </dependency>
      <dependency>
          <groupId>org.mockito</groupId>
          <artifactId>mockito-junit-jupiter</artifactId>
          <scope>test</scope>
      </dependency>
      <dependency>
          <groupId>org.assertj</groupId>
          <artifactId>assertj-core</artifactId>
          <version>3.27.2</version>
          <scope>test</scope>
      </dependency>
    </dependencies>

<!-- ... -->


```

After you configure the test dependencies, you can write the first unit test for you mojo.

```java

import org.apache.maven.api.di.Inject;
import org.apache.maven.api.di.Priority;
import org.apache.maven.api.di.Provides;
import org.apache.maven.api.di.Singleton;
import org.apache.maven.api.plugin.testing.InjectMojo;
import org.apache.maven.api.plugin.testing.MojoParameter;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@MojoTest
public class HelloMojoTest {

    @Inject
    private MavenDIComponent componentMock;

    @Test
    @InjectMojo(goal = "hello")
    @MojoParameter(name = "name", value = "World")
    public void testHello(HelloMojo mojoUnderTest) {

        mojoUnderTest.execute();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(componentMock).hello(captor.capture());
        assertThat(captor.getValue()).isEqualTo("World");
    }

    @Singleton
    @Provides
    @Priority(10)
    private MavenDIComponent createMavenDIComponent() {
        return mock(MavenDIComponent.class);
    }
}

```

If you want to mock your injected commponent, you have to write a method that create the mock for this component.
This method has to be annotated with `@Singleton`, `@Provides` and `@Priority(10)`, so that the DI framework selected it before the real component.



[maven-di]: tbd
[guice]: tbd
[maven-di-plugin]: tbd
[maven-plugin-testing-harness]: TBD
[junit-jupiter]: tbd
