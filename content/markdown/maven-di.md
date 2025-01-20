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
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>mavendi-maven-plugin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>maven-plugin</packaging>

    <name>mavendi-maven-plugin Maven Plugin</name>
    <url>http://maven.apache.org</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <javaVersion>17</javaVersion>
        <mavenVersion>4.0.0-rc-1</mavenVersion>
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
import org.apache.maven.api.plugin.annotations.Mojo;

@Mojo(name = "hello", defaultPhase = Phase.VALIDATE, projectRequired = false)
public class HelloMojo implements org.apache.maven.api.plugin.Mojo {

    @Inject
    private MavenDIComponent component;

    public void execute() {
        component.hello();
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

    public void hello() {
        System.out.println("Hello! I am a component that is being used via field injection!");
    }
}
```
[maven-di]: tbd
[guice]: tbd
[maven-di-plugin]: tbd

