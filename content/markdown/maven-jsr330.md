# Maven & JSR-330
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
## Why JSR-330?

Maven has a long history of using dependency injection (DI) by way of [Plexus][plexus], so the intent of using
[JSR-330][jsr330] is to replace a custom DI mechanism (with custom Plexus `@Component`/`@Requirement` annotations) with something standard (with `@Named`/`@Inject` annotations). The implementation Maven
uses - since 3.0-beta-3 - is named [Sisu][sisu] and is based on [Guice 3.x][guice], which directly supports JSR-330.

If you are using [Plexus annotations and APIs][plexus-annotations] currently,
there is no rush to switch and no big bang conversions are necessary: Plexus, JSR-330 and Guice APIs all happily
co-exist within Maven\'s core and you can choose to use JSR-330 when you wish. There are hundreds of components
written using the Plexus APIs.

If you want to use JSR-330, you must understand that your code won\'t be compatible with Maven 3.0.x
but only with Maven 3.1.0 and later. Even though JSR-330 has been available in core since Maven 3.0-beta-3, it was made available to plugins and
extensions only in Maven 3.1.0 (see [MNG-5343][MNG-5343] for more details).

If you are interested the background of moving from Plexus to Guice and JSR-330, you can refer to the following "Plexus to Guice" articles from 2010: [part 1][p2g1], [part 2][p2g2] and [part 3][p2g3].

If you are interested in migrating from Plexus Annotations to JSR-330, Sisu has a [Plexus Migration documentation][SisuPlexusMigration] that is done for you.

## How to use JSR-330

When you use JSR-330 in Maven plugins or extensions, there are two things you need to setup in your build:

1. First you want a dependency on `javax.inject` so you can use the `@Inject`, `@Named`, and `@Singleton` annotations
in your plugins and extensions (eventually in addition to [Plexus annotations][plexus-annotations]).

2. Second you need to setup the [`sisu-maven-plugin`][sisu-maven-plugin] to index the JSR-330 components
you want made available to Maven. The [`sisu-maven-plugin`][sisu-maven-plugin] creates its index in `META-INF/sisu/javax.inject.Named` (eventually in addition to [`plexus-component-metadata`][plexus-component-metadata]).

### Implementation Details

If you take a look in that `META-INF/sisu/javax.inject.Named` file with the example from the next paragraph, you will see a list of full class names.

Enumerating the implementations means that no classpath scanning is required in runtime to find them, which keeps Maven\'s
startup time fast. Note that our container is configured by default to only use the index. While this keeps things fast,
if you use JSR-330 components in dependencies that do not contain an index, those implementations will currently
not be discovered. This is a compromise that is reasonable given Maven is a command-line tool where startup speed
is important.


## How to use JSR-330 in plugins

Let\'s take a look at an example plugin: If you want to look at this example project, you can find the code [in Maven Core ITs][jsr330-plugin].

The POM is setup for JSR-300 as previously mentioned, with the `javax.inject` dependency and the `sisu-maven-plugin` configured to create
the JSR-330 component index.

In addition, we add classical Maven plugin dependencies
`maven-plugin-api` and `maven-plugin-annotations` to extend the `AbstractMojo` and use the Java  Plugin Tools Annotations with associated `maven-plugin-plugin` (see [Maven Plugin Tools](/plugin-tools/)).

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jsr330-plugin</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>maven-plugin</packaging>

  <name>maven-jsr330-plugin Maven Plugin</name>
  <description>As sample Maven Plugin that uses JSR-330 components</description>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <mavenVersion>3.0.4</mavenVersion>
    <mavenPluginPluginVersion>3.2</mavenPluginPluginVersion>
  </properties>

  <dependencies>
    <dependency>
      <groupId>javax.inject</groupId>
      <artifactId>javax.inject</artifactId>
      <version>1</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>org.apache.maven</groupId>
      <artifactId>maven-plugin-api</artifactId>
      <version>${mavenVersion}</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>org.apache.maven.plugin-tools</groupId>
      <artifactId>maven-plugin-annotations</artifactId>
      <version>${mavenPluginPluginVersion}</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-plugin-plugin</artifactId>
        <version>${mavenPluginPluginVersion}</version>
        <configuration>
          <skipErrorNoDescriptorsFound>true</skipErrorNoDescriptorsFound>
        </configuration>
        <executions>
          <execution>
            <id>mojo-descriptor</id>
            <goals>
              <goal>descriptor</goal>
            </goals>
          </execution>
          <execution>
            <id>help-goal</id>
            <goals>
              <goal>helpmojo</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.eclipse.sisu</groupId>
        <artifactId>sisu-maven-plugin</artifactId>
        <version>0.3.5</version>
        <executions>
          <execution>
            <id>generate-index</id>
            <goals>
              <goal>main-index</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
```

Now let\'s take a look at the plugin code. You\'ll notice that we\'re using constructor injection
which makes testing a lot easier. If you want to test your `Jsr330Component`, you do not need the container
to instantiate the `Mojo`. In this simple case, you can actually test this plugin without using the [plugin
testing harness](/plugin-testing/maven-plugin-testing-harness/index.html) because you can instantiate the `Jsr330Component` and `Jsr330Mojo` directly and wire
everything up manually using the constructor. Constructor injection, which Plexus lacks, greatly simplifies testing.

```java
package org.apache.maven.plugins;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo( name = "hello", defaultPhase = LifecyclePhase.VALIDATE, requiresProject = false )
public class Jsr330Mojo
    extends AbstractMojo
{

    private Jsr330Component component;

    @Inject
    public Jsr330Mojo( Jsr330Component component )
    {
        this.component = component;    
    }

    public void execute()
        throws MojoExecutionException
    {    
        //
        // Say hello to the world, my little constructor injected component!
        //
        component.hello();
    }
}
```

[tesla-profiler]: https://github.com/tesla/tesla-profiler
[p2g1]: https://www.sonatype.com/people/2010/01/from-plexus-to-guice-1-why-guice/
[p2g2]: https://www.sonatype.com/people/2010/01/from-plexus-to-guice-2-the-guiceplexus-bridge-and-custom-bean-injection/
[p2g3]: https://www.sonatype.com/people/2010/01/from-plexus-to-guice-3-creating-a-guice-bean-extension-layer/
[jsr330]: https://www.jcp.org/en/jsr/detail?id=330
[sisu]: https://eclipse.org/sisu/
[plexus]: https://codehaus-plexus.github.io/plexus-containers/
[plexus-component-metadata]: https://codehaus-plexus.github.io/plexus-containers/plexus-component-metadata/
[plexus-annotations]: https://codehaus-plexus.github.io/plexus-containers/plexus-component-annotations/
[jsr330-plugin]: https://github.com/apache/maven-integration-testing/tree/master/core-it-suite/src/test/resources/mng-5382
[guice]: https://code.google.com/p/google-guice/
[sisu-maven-plugin]: https://eclipse.org/sisu/docs/api/org.eclipse.sisu.mojos/
[MNG-5343]: https://issues.apache.org/jira/browse/MNG-5343
[SisuPlexusMigration]: https://eclipse-sisu.github.io/sisu-project/plexus/index.html
