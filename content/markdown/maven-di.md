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

# Maven Dependency Injection (DI)

## What is Maven Dependency Injection (DI)?

[Maven DI](/ref/4-LATEST/api/maven-api-di/index.html) is the dependency injection framework of Maven, that is introduced in Maven 4.

It is the successor for [Plexus IoC](https://codehaus-plexus.github.io/plexus-containers/) (used in Maven 2) and [Eclipse Sisu](https://eclipse.dev/sisu/) based on [JSR 330 annotations](/maven-jsr330.html) (used in Maven 3). Sisu still ships with Maven 4 for compatibility reasons and kicks in whenever you use the injection annotations `org.eclipse.sisu.*` or `javax.inject.*`.

## How to use Maven DI

When you use Maven DI in Maven plugins or extensions, you want to have the dependency to `maven-api-di`, so you can use the `@org.apache.maven.api.di.Inject`, `@org.apache.maven.api.di.Named`, and `@org.apache.maven.api.di.Singleton` annotations in your plugins and extensions.
The annotations of Maven DI are similar to JSR 330, but they have different package name [`org.apache.maven.api.di`](https://maven.apache.org/ref/4-LATEST/api/maven-api-di/apidocs/org/apache/maven/api/di/package-summary.html).

## Interoperability of Maven DI <-> Eclipse Sisu

All Maven 4 components rely on Maven DI only. However they are also available to Eclipse Sisu (Google Guice) in order to be consumed from Maven 3 extensions/plugins. At the same time the (Maven 3) Sisu and also Plexus components (both exposed via the `org.codehaus.plexus.PlexusContainer`) are also exposed to Maven DI.
To achieve that Maven 4 ships with a bridge in [`SisuDiBridgeModule`](https://github.com/apache/maven/blob/master/impl/maven-core/src/main/java/org/apache/maven/internal/impl/SisuDiBridgeModule.java).

### Implementation Details

TBD (need help)

## How to use Maven DI in Maven 4 plugins

---

**NOTE**

If your plugin should also run with Maven 3.x, please look at [Maven & JSR-330][maven-jsr330].
Plugins that are using Maven DI are only compatible with Maven 4.

Let's take a look at an example plugin: If you want to look at this example project, you can find the code [in Maven Core ITs][maven-di-plugin].

The POM is set up for Maven DI as previously mentioned, with the `maven-api-di` dependency

In addition, we add a Maven plugin dependency `maven-api-core` to implement the interface `Mojo`.

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

For the helper classes, we are adding `maven-testing`.
The dependencies to `maven-core`, `maven-resolver-api` and `maven-api-impl`  are only needed for the test runtime.
`junit-jupiter` is the used test framework, `mockito-*` is the used mock framework and `assertj` is the used annotation lib.
The last two mention dependencies are optional but helpful.

```xml

<!-- ... -->
  <properties>
    <!-- ... -->
    <mavenPluginTestingVersion>4.0.0-beta-2</mavenPluginTestingVersion>
    <mavenResolverVersion>2.0.2</mavenResolverVersion>
  </properties>

  <dependencies>
  <!-- ... -->

      <dependency>
          <groupId>org.apache.maven</groupId>
          <artifactId>maven-testing</artifactId>
          <version>${mavenVersion}</version>
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

If you want to mock your injected component, you have to write a method that creates the mock for this component.
This method has to be annotated with `@Singleton`, `@Provides` and `@Priority(10)`, so that the DI framework selected it before the real component.

[guice]: https://github.com/google/guice
[maven-jsr330]: /maven-jsr330.html
[maven-di-plugin]: https://github.com/apache/maven/tree/master/its/core-it-suite/src/test/resources/mng-8525-maven-di-plugin
[maven-plugin-testing-harness]: /plugin-testing/maven-plugin-testing-harness/index.html
[junit-jupiter]: https://junit.org/junit5/

