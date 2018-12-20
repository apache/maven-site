# Maven CI Friendly Versions

Starting with Maven 3.5.0-beta-1 you can use the `${revision}`, `${sha1}` 
and/or `${changelist}` as placeholders for the version in your pom file. 

## Single Project Setup

This can look like this:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache</groupId>
    <artifactId>apache</artifactId>
    <version>18</version>
  </parent>
  <groupId>org.apache.maven.ci</groupId>
  <artifactId>ci-parent</artifactId>
  <name>First CI Friendly</name>
  <version>${revision}</version>
  ...
</project>
```

  This is of course a simple situation where we use only `${revision}` for brevity
  to show the general course.


  Based on the above pom you can build your project using:

```
mvn clean package
```

  But wait there is a problem? Which version will the artifacts have? So you need
  to define the version for your artifacts. The first possibility is to use the command
  line like this:

```
mvn -Drevision=1.0.0-SNAPSHOT clean package
```

  This will become cumbersome over the time. So the other solution for this is
  to simply use a property inside the pom file which looks like this:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache</groupId>
    <artifactId>apache</artifactId>
    <version>18</version>
  </parent>
  <groupId>org.apache.maven.ci</groupId>
  <artifactId>ci-parent</artifactId>
  <name>First CI Friendly</name>
  <version>${revision}</version>
  ...
  <properties>
    <revision>1.0.0-SNAPSHOT</revision>
  </properties>
</project>
```

  So now you can simply call Maven as usual like `mvn clean package`.

  You can of course change the version via the command line like this:

```
mvn -Drevision=2.0.0-SNAPSHOT clean package
```
 
  Of course you can use the `.mvn/maven.config` file for this.

  A note about the used properties. You can only use those named
  `${revision}`, `${sha1}` and/or `${changelist}` and not other
  named properties like this:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache</groupId>
    <artifactId>apache</artifactId>
    <version>18</version>
  </parent>
  <groupId>org.apache.maven.ci</groupId>
  <artifactId>ci-parent</artifactId>
  <name>First CI Friendly</name>
  <version>${revision}</version>
  ...
  <properties>
    <revision>1.0.0-${buildNumber}-SNAPSHOT</revision>
  </properties>
</project>
```

  The above example will *not work as expected*. If you like
  to have more flexibility you can use a combination of the
  different properties like this:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache</groupId>
    <artifactId>apache</artifactId>
    <version>18</version>
  </parent>
  <groupId>org.apache.maven.ci</groupId>
  <artifactId>ci-parent</artifactId>
  <name>First CI Friendly</name>
  <version>${revision}${sha1}${changelist}</version>
  ...
  <properties>
    <revision>1.3.1</revision>
    <changelist>-SNAPSHOT</changelist>
    <sha1/>
  </properties>
</project>
```

  If you like to make a version `2.0.0-SNAPSHOT` this can
  simply being achieved by using this:

```
mvn -Drevision=2.0.0 clean package
```

  Another usage example can be to make a release which can be
  done via (version 1.3.1):

```
mvn -Dchangelist= clean package
```

  Or if you like to make a release with another version:

```
mvn -Drevision=2.7.8 -Dchangelist= clean package
```

  Please read until the end of this documentation!

## Multi Module Setup

  So now let us take a look into a situation where we have a multi module
  build. We have a parent pom and one or more children.
  The parent pom will look like this:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache</groupId>
    <artifactId>apache</artifactId>
    <version>18</version>
  </parent>
  <groupId>org.apache.maven.ci</groupId>
  <artifactId>ci-parent</artifactId>
  <name>First CI Friendly</name>
  <version>${revision}</version>
  ...
  <properties>
    <revision>1.0.0-SNAPSHOT</revision>
  </properties>
  <modules>
    <module>child1</module>
    ..
  </modules>
</project>
```

  The child will look like this:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache.maven.ci</groupId>
    <artifactId>ci-parent</artifactId>
    <version>${revision}</version>
  </parent>
  <groupId>org.apache.maven.ci</groupId>
  <artifactId>ci-child</artifactId>
   ...
</project>
```

  A multi module build can of course build the same way as the single project setup. You should
  define the version either via property in the parent or use the `.mvn/maven.config` file.


## Install / Deploy

  If you like to install or deploy artifacts by using the above setup you **have to use**
  the [flatten-maven-plugin][flatten-maven-plugin] otherwise you will install/deploy artifacts 
  in your repository which will not be consumable by Maven anymore. Such kind of setup
  will look like this:


```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache</groupId>
    <artifactId>apache</artifactId>
    <version>18</version>
  </parent>
  <groupId>org.apache.maven.ci</groupId>
  <artifactId>ci-parent</artifactId>
  <name>First CI Friendly</name>
  <version>${revision}</version>
  ...
  <properties>
    <revision>1.0.0-SNAPSHOT</revision>
  </properties>

 <build>
  <plugins>
    <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>flatten-maven-plugin</artifactId>
      <version>1.0.0</version>
      <configuration>
        <updatePomFile>true</updatePomFile>
      </configuration>
      <executions>
        <execution>
          <id>flatten</id>
          <phase>process-resources</phase>
          <goals>
            <goal>flatten</goal>
          </goals>
        </execution>
        <execution>
          <id>flatten.clean</id>
          <phase>clean</phase>
          <goals>
            <goal>clean</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
  </build>
  <modules>
    <module>child1</module>
    ..
  </modules>
</project>
```



[flatten-maven-plugin]: https://www.mojohaus.org/flatten-maven-plugin/ 

