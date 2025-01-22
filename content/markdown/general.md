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

# Frequently Asked Technical Questions

<!--MACRO{toc|fromDepth=2}-->

## How do I prevent "[WARNING] Using platform encoding (Cp1252 actually) to copy filtered resources, i.e. build is platform dependent!"

This or a similar warning is emitted by a plugin that processes plain text files but has not been configured to
use a specific file encoding.
So eliminating the warning is simply a matter of finding out which plugin emits it and how to configure the file
encoding for it.

This is as easy as adding the following property to your POM (or one of its parent POMs):

```xml

<project>
  <!-- ... -->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>
  <!-- ... -->
</project>
```

## How do I prevent including JARs in `WEB-INF/lib`? I need a "compile only" scope!

The scope you should use for this is `provided`.
This indicates to Maven that the dependency will be provided at run time by its container or the JDK, for example.
Dependencies with this scope will not be passed on transitively, nor will they be bundled in a package such
as a WAR, or included in the runtime classpath.

## How do I list available plugins?

The [Available Plugins](/plugins/) page lists them and provides additional information.

## How do I determine what version of a plugin I am using?

You can use the [Maven Help Plugin](/plugins/maven-help-plugin/)'s [
`describe`](/plugins/maven-help-plugin/describe-mojo.html) goal.

For example, to find out the version of the install plugin:

> mvn -Dplugin=install help:describe

Note that you must give the plugin prefix as the argument to plugin, not it's artifact ID.

## How can I use Ant tasks in a Maven build?

There are currently two alternatives:

- For use in a plugin written in Java, Beanshell or other Java-like scripting language, you can construct
  the Ant tasks using the [instructions given in the Ant documentation](https://ant.apache.org/manual/antexternal.html).
- If you have very small amounts of Ant script specific to your project, you can use
  the [AntRun plugin](/plugins/maven-antrun-plugin/index.html).

## How can I use Maven features in an Ant build?

The [Maven Ant Tasks](/ant-tasks/index.html) allow many of the features of Maven, such as dependency management and
repository deployment, to be used in an Ant build.

## How do I set up Maven so it will compile with a target and source JVM of my choice?

You must configure the source and target parameters in your pom. For example, to set the source and
target JVM to 7, you should have in your pom:

```xml

<project>
  <!-- ... -->
  <properties>
    <maven.compiler.source>1.7</maven.compiler.source>
    <maven.compiler.target>1.7</maven.compiler.target>
  </properties>
  <!-- ... -->
</project>
```

Or if a parent pom overrides for compiler plugin default values, and you can't fix it,
you'll have to explicitly force the values in the compiler plugin configuration:

```xml

<project>
  <!-- ... -->
  <build>
    <!-- ... -->
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.3</version>
        <configuration>
          <source>1.7</source>
          <target>1.7</target>
        </configuration>
      </plugin>
    </plugins>
    <!-- ... -->
  </build>
  <!-- ... -->
</project>
```

## Is it possible to create my own directory structure?

Absolutely yes!

By configuring `<sourceDirectory>`, `<resources>` and other elements of the `<build>` section.
In addition, you may need to change the plugin configuration if you are not using plugin defaults for their
files/directories.

## Where is the source code? I couldn't seem to find a link anywhere on the Maven site

The source code can be found in [our Subversion and Git repositories](./scm.html).
For more information, see [Building Maven](/guides/development/guide-building-maven.html).

## Maven can't seem to download the dependencies. Is my installation correct?

You most probably need to configure Maven to use a proxy.
Please see the information on [Configuring a proxy](/guides/mini/guide-proxies.html) for information on how to configure
your proxy for Maven.

## I have a jar that I want to put into my local repository. How can I copy it in?

If you understand the layout of the Maven repository, you can copy the jar directly into where it is meant to go.
Maven will find this file next time it is run.
If you are not confident about the layout of the Maven repository, then you can adapt the following command to load in
your jar file, all on one line.

```
mvn install:install-file
  -Dfile=<path-to-file>
  -DgroupId=<group-id>
  -DartifactId=<artifact-id>
  -Dversion=<version>
  -Dpackaging=<packaging>
  -DgeneratePom=true

Where:
   <path-to-file>  the path to the file to load
   <group-id>      the group that the file should be registered under
   <artifact-id>   the artifact name for the file
   <version>       the version of the file
   <packaging>     the packaging of the file e.g. jar  
```

This should load in the file into the Maven repository, renaming it as needed.

## How do I unsubscribe from Maven mailing lists?

To unsubscribe from a Maven mailing list you simply send a message to `[mailing-list]-unsubscribe@maven.apache.org`.
So, if you have subscribed to `users@maven.apache.org` then you would send a message to
`users-unsubscribe@maven.apache.org` in order to get off the list.

People tend to have problems when they subscribe with one address and attempt to unsubscribe with another.
So make sure that you are using the same address when unsubscribing that you used to subscribe before asking for help.

If you find you still cannot get off a list then send a message to `[mailing-list]-help@maven.apache.org`.
These instructions are also appended to every message sent out on a maven mailing list...

## How do I skip the tests?

Add the parameter `-Dmaven.test.skip=true` or `-DskipTests=true` in the command line, depending on whether you want to
skip test compilation and execution or only execution.
See the example [Skipping Tests](/plugins/maven-surefire-plugin/examples/skipping-tests.html) in
the [Surefire Plugin's documentation](/plugins/maven-surefire-plugin/) for more details.

## How can I run a single unit test?

Use the parameter `-Dtest=MyTest` at the command line.
Do not specify the entire package (`org.apache.x.y.MyTest`).

## Handle special characters in site

Configure your ide to use the correct encoding.
With Eclipse, add `-Dfile.encoding=ISO-8859-1` in `eclipse.ini` file.

Configure the reporting output encoding in your pom

```xml

<project>
  <!-- ... -->
  <properties>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
  </properties>
  <!-- ... -->
</project>
```

or if default encoding is overridden in a parent pom that you can't change, configure the site plugin explicitly

```xml

<project>
  <!-- ... -->
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-site-plugin</artifactId>
    <version>3.6</version>
    <configuration>
      <outputEncoding>UTF-8</outputEncoding>
    </configuration>
  </plugin>
  <!-- ... -->
</project>
```

Configure the file encoding used by mvn and add the encoding to MAVEN_OPTS (same as the ide).
This can be made with adding `MAVEN_OPTS="-Dfile.encoding=ISO-8859-1"` in `$HOME/.profile`.

## Maven compiles my test classes but doesn't run them?

Tests are run by the [surefire plugin](/surefire/maven-surefire-plugin/).
The surefire plugin can be configured to run certain test classes, and you may have unintentionally done so by
specifying a value to `${test}`.
Check your `settings.xml` and `pom.xml` for a property named `test` which would like this:

```xml

<project>
  <!-- ... -->
  <properties>
    <property>
      <name>test</name>
      <value>some-value</value>
    </property>
  </properties>
  <!-- ... -->
</project>
```

or

```xml

<project>
  <!-- ... -->
  <properties>
    <test>some-value</test>
  </properties>
  <!-- ... -->
</project>
```

## Where are Maven SNAPSHOT artifacts?

If you are trying to build a development version of Maven or plugins, you may need to access the Maven snapshot
repositories.
You need to update your `settings.xml` file using
the [Guide to Plugin Snapshot Repositories](/guides/development/guide-testing-development-plugins.html).

## Where are the Maven XSD schemas?

See the following links:

- [Maven XSD](/xsd/maven-4.0.0.xsd)
- [Maven Settings XSD](/xsd/settings-1.0.0.xsd)

Your favorite IDE probably supports XSD schema's for `pom.xml` and `settings.xml` editing.
You need to specify the following:

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      https://maven.apache.org/xsd/maven-4.0.0.xsd">

  <!-- ... -->
</project>
```

```xml

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      https://maven.apache.org/xsd/settings-1.0.0.xsd">

  <!-- ... -->
</settings>
```

## Maven doesn't work, how do I get help?

We have compiled a list of available resources on the [getting help page](/users/getting-help.html).

## How to produce execution debug output or error messages?

You could call Maven with `-X` parameter or `-e` parameter.
For more information, run: `mvn --help`.

## What is a Mojo?

A mojo is a **M**aven plain **O**ld **J**ava **O**bject.
Each mojo is an executable *goal* in Maven, and a Maven plugin is a distribution of one or more related mojos.

## How to find dependencies on public Maven repositories?

You could use the following search engines:

- [https://search.maven.org](https://search.maven.org/)
- [https://repository.apache.org](https://repository.apache.org)
- [https://mvnrepository.com](https://mvnrepository.com)

## Why is my Javadoc JAR built twice during release?

With MNG-5940 the release profile goal of the Maven Javadoc Plugin has been changed to `jar-no-fork`.
Revise your configuration to avoid duplicate JAR upload.

