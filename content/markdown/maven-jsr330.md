# Maven & JSR-330

## Why JSR-330?

Maven has a long history of using dependency injection (DI) by way of [Plexus][plexus], so the intent of using
[JSR-330][jsr330] is to replace a custom DI mechanism with something standard. The implementation Maven
uses - since 3.0-beta-3 - is based on [Guice 3.x][guice], which directly supports JSR-330.

If you are using [Plexus annotations and APIs][plexus-container] currently,
there is no rush to switch and no big bang conversions are necessary: Plexus, JSR-330 and Guice APIs all happily
co-exist within Maven\'s core and you can choose to use JSR-330 when you wish. There are hundreds of components
written using the Plexus APIs, particularly components and plugins compatible with Maven 2,
then those APIs will be supported forever, or at least until Maven fully drops Maven 2 support. 

If you want to use JSR-330, you must understand that your code won\'t be compatible with Maven 2 or 3.0.x
but only with Maven 3.1.0+: even if JSR-330 is available in core since Maven 3.0-beta-3, it was made available to plugins and
extensions only in Maven 3.1.0 (see [MNG-5343][MNG-5343] for more details).

If you are interested the background of moving from Plexus to Guice and JSR-330, you can refer to the following articles:

- [Plexus to Guice Part 1][p2g1]
- [Plexus to Guice Part 2][p2g2]
- [Plexus to Guice Part 3][p2g3]

## How to use JSR-330

When you use JSR-330 in Maven plugins or extensions, there are two things you need to setup in your build:

1. First you want a dependency on `javax.inject` so you can use the `@Inject`, `@Named`, and `@Singleton` annotations
in your plugins and extensions.

2. Second you need to setup the [`sisu-maven-plugin`][sisu-maven-plugin] to index the JSR-330 components
you want made available to Maven. The `sisu-maven-plugin` creates its index in `META-INF/sisu/javax.inject.Named`.

If you take a look in that file with the example from the next paragraph, you will see something like the following:

```
org.apache.maven.lifecycle.profiler.LifecycleProfiler
org.apache.maven.lifecycle.profiler.internal.DefaultSessionProfileRenderer
org.apache.maven.lifecycle.profiler.internal.DefaultTimer
```

Enumerating the implementations means that no classpath scanning is required in runtime to find them, which keeps Maven\'s
startup time fast. Note that our container is configured by default to only use the index. While this keeps things fast,
if you use JSR-330 components in dependencies that do not contain an index, those implementations will currently
not be discovered. This is a compromise that is reasonable given Maven is a command-line tool where startup speed
is important.

## How to use JSR-330 in extensions
 
Let\'s take a look at an example extension. We\'ll take a look at the POM, and a little bit of the implementation
so you can get an idea of how JSR-330 extensions work. Really, it\'s just a simple JSR-330 component.
If you want to look at the full implementation, you can find it [here][tesla-profiler] on Github.

Ok, so let\'s take a look at the POM:
 
```
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.apache.maven</groupId>
  <artifactId>maven-profiler</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <name>Maven :: Profiler</name>
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>
  <dependencies>
    <dependency>
      <groupId>javax.inject</groupId>
      <artifactId>javax.inject</artifactId>
      <version>1</version>
    </dependency>
    <dependency>
      <groupId>org.apache.maven</groupId>
      <artifactId>maven-core</artifactId>
      <version>3.0.3</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.8.2</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.eclipse.sisu</groupId>
        <artifactId>sisu-maven-plugin</artifactId>
        <version>0.3.3</version>
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

So, as mentioned, we have the `javax.inject` dependency and the `sisu-maven-plugin` configured to create
the JSR-330 component index. When you build and place the extension JAR in the `${MAVEN_HOME}/lib/ext` folder,
it will automatically get picked up by Maven. In the case of this example, we have an implementation of
an `EventSpy` that times the executions of individual mojos within a phase in the lifecycle.

```
package org.apache.maven.lifecycle.profiler;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.eventspy.AbstractEventSpy;
import org.apache.maven.execution.ExecutionEvent;

@Named
@Singleton
public class LifecycleProfiler extends AbstractEventSpy {

  //
  // Components
  //
  private SessionProfileRenderer sessionProfileRenderer;
  
  //
  // Profile data
  //
  private SessionProfile sessionProfile;
  private ProjectProfile projectProfile;
  private PhaseProfile phaseProfile;
  private MojoProfile mojoProfile;  
  
  @Inject
  public LifecycleProfiler(SessionProfileRenderer sessionProfileRenderer) {
    this.sessionProfileRenderer = sessionProfileRenderer;
  }
  
  @Override
  public void init(Context context) throws Exception {
  }

  @Override
  public void onEvent(Object event) throws Exception {
    if (event instanceof ExecutionEvent) {
      
      
      ExecutionEvent executionEvent = (ExecutionEvent) event;
      if (executionEvent.getType() == ExecutionEvent.Type.SessionStarted) {
        //
        //
        //
        sessionProfile = new SessionProfile();
      } else if (executionEvent.getType() == ExecutionEvent.Type.SessionEnded) {
        //
        //
        //
        sessionProfile.stop();
        
        sessionProfileRenderer.render(sessionProfile);
        
      } else if (executionEvent.getType() == ExecutionEvent.Type.ProjectStarted) {
        //
        // We need to collect the mojoExecutions within each project
        //
        projectProfile = new ProjectProfile(executionEvent.getProject());
      } else if (executionEvent.getType() == ExecutionEvent.Type.ProjectSucceeded || executionEvent.getType() == ExecutionEvent.Type.ProjectFailed) {
        //
        //
        //
        projectProfile.stop();
        sessionProfile.addProjectProfile(projectProfile);
      } else if (executionEvent.getType() == ExecutionEvent.Type.MojoStarted) {
        String phase = executionEvent.getMojoExecution().getLifecyclePhase();
        //
        // Create a new phase profile if one doesn't exist or the phase has changed.
        //
        if(phaseProfile == null) {
          phaseProfile = new PhaseProfile(phase);
        } else if (!phaseProfile.getPhase().equals(phase)) {
          phaseProfile.stop();
          projectProfile.addPhaseProfile(phaseProfile);
          phaseProfile = new PhaseProfile(phase);          
        }
        mojoProfile = new MojoProfile(executionEvent.getMojoExecution());
      } else if (executionEvent.getType() == ExecutionEvent.Type.MojoSucceeded || executionEvent.getType() == ExecutionEvent.Type.MojoFailed) {
        //
        //
        //
        mojoProfile.stop();
        phaseProfile.addMojoProfile(mojoProfile);        
      }
    }
  }
}
```

## How to use JSR-330 in plugins

Let\'s take a look at an example plugin. The POM is setup in a similar way to an extension, but we add a dependency
to `maven-plugin-api` and `maven-plugin-annotations` to extend the `AbstractMojo` and use the Java 5 plugin
annotations in our example.

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jsr330-plugin</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>maven-plugin</packaging>

  <name>maven-jsr330-plugin Maven Plugin</name>
  <url>https://maven.apache.org</url>

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
    </dependency>
    <dependency>
      <groupId>org.apache.maven</groupId>
      <artifactId>maven-plugin-api</artifactId>
      <version>${mavenVersion}</version>
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
        <version>0.3.3</version>
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
to instantiate the `Mojo`. In this simple case, you can actually test this plugin without using the plugin
testing harness because you can instantiate the `Jsr330Component` and `Jsr330Mojo` directly and wire
everything up manually using the constructor. Constructor injection, which Plexus lacks, greatly simplifies testing.

```
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

If you want to look at this example project, you can find the code [in Maven Core ITs][jsr330-plugin].

[tesla-profiler]: https://github.com/tesla/tesla-profiler
[p2g1]: https://www.sonatype.com/people/2010/01/from-plexus-to-guice-1-why-guice/
[p2g2]: https://www.sonatype.com/people/2010/01/from-plexus-to-guice-2-the-guiceplexus-bridge-and-custom-bean-injection/
[p2g3]: https://www.sonatype.com/people/2010/01/from-plexus-to-guice-3-creating-a-guice-bean-extension-layer/
[jsr330]: https://www.jcp.org/en/jsr/detail?id=330
[sisu]: https://eclipse.org/sisu/
[plexus]: https://codehaus-plexus.github.io/
[plexus-container]: https://codehaus-plexus.github.io/plexus-containers/
[jsr330-plugin]: https://svn.apache.org/viewvc/maven/core-integration-testing/trunk/core-it-suite/src/test/resources/mng-5382/
[guice]: https://code.google.com/p/google-guice/
[sisu-maven-plugin]: https://eclipse.org/sisu/docs/api/org.eclipse.sisu.mojos/
[MNG-5343]: https://issues.apache.org/jira/browse/MNG-5343
