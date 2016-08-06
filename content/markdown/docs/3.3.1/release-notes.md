# Release Notes &#x2013; Maven 3.3.1

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

 NOTE: For help with the syntax of this file, see:
 http://maven.apache.org/doxia/references/apt-format.html
-->

## Overview

The Apache Maven team would like to announce the release of Maven Version 3.3.1. The new 
Maven Version is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model
(POM), Maven can manage a project's build, reporting and documentation from a central place.

Maven 3 aims to ensure backward compatibility with Maven 2, improve usability, increase performance, allow safe embedding, and pave the way to implement many highly demanded features.

The core release is independent of the plugins available. Further releases of plugins will be made separately.
See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [http://maven.apache.org/](http://maven.apache.org/)
- the maven-user mailing list: [http://maven.apache.org/mail-lists.html](http://maven.apache.org/mail-lists.html)
- the reference documentation: [http://maven.apache.org/ref/3.3.1/](http://maven.apache.org/ref/3.3.1/)

The full list of changes can be found in our [issue management system][4].

The full list of all release notes for all versions can be found on the [history page of the
Maven project][5].

## Release Notes In Detail

The new [Maven 3.3.1 Release is just out](http://mail-archives.apache.org/mod_mbox/maven-announce/201503.mbox/%3C1954448.IV3m89R0sE%40herve-desktop%3E). Let us take a deeper look into the new features/improvements:

* The first and most important thing is that [Maven 3.3.1 needs JDK 1.7][MNG-5780].

### Toolchains

* In our days it becomes more and more important to be able to use different JDK 
  to be used by Maven itself and which is used to compile/test your production code.
  This concept is know under the name [Toolchains][0] which is unfortunately not very 
  well-known.

* The handling of the [`toolchains.xml`][MNG-3891] file has been adjusted with the 
  handling of `settings.xml` which means it will be searched within the
  `${maven.home}/conf/` folder and furthermore within the `${user.home}/.m2/` folder.

* For a better understanding and as an example of the `toolchains.xml` file has been added
  to the [Maven distribution][MNG-5745].

* Maven has been improved to read the `toolchains.xml` file [during initialization][MNG-5754] instead
  of waiting till [maven-toolchains-plugin][maven-toolchains-plugin] will read it.

* Maven has a new option to handle global toolchains file `-gt file` or `--global-toolchains file`
  in the spirit of global settings file[MNG-3891][MNG-3891].

### Core Extensions

* Core Extension mechanism has [been improved][MNG-5771] to make 
  it simpler to use.

* The old way (up to Maven 3.2.5) was to create a jar (must be shaded if you have other dependencies)
  which contains the extension and put it manually into the `${MAVEN_HOME}/lib/ext` folder. 
  This means you had to change the Maven installation. The consequence was that everyone who likes 
  to use this needed to change it's installation and makes the on-boarding for a developer much 
  more inconvenient. The other option was to give the path to the jar on command line via 
  `mvn -Dmaven.ext.class.path=extension.jar`. This has the drawback giving those
  options to your Maven build every time you are calling Maven. Not very convenient as well.
 
* From now on this can be done much more simpler and in a more Maven like way. So 
  you can define an `${maven.projectBasedir}/.mvn/extensions.xml` file which looks 
  like the following:

``` xml
<extensions xmlns="http://maven.apache.org/EXTENSIONS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/EXTENSIONS/1.0.0 http://maven.apache.org/xsd/core-extensions-1.0.0.xsd">
  <extension>
    <groupId/>
    <artifactId/>
    <version/>
  </extension>
</extensions>
```

*  Now you can simply use an extension by defining the usual maven coordinates
   `groupId`, `artifactId`, `version` as any other artifact. Furthermore all
   transitive dependencies of those extensions will automatically being downloaded
   from your repository. So no need to create a shaded artifact anymore.

   An other advantage is that the `${maven.projectBasedir}/.mvn/`
   directory is located in the root of your Maven project and in conseuqence
   is part of your project which means you will check it in along with
   your project. So everyone who checks out your project automatically
   can use the extensions.

   One thing is important that the extensions will be resolved from the
   pluginRepository. This is important if you have configured the pluginRepository
   different from the repository.

### JVM and Command Line Options

* [Project specific jvm and command line otions][MNG-5767]

* It's really hard to define a general set of options for calling the maven
  command line. Usually this will be solved by putting this options to a script
  but this can now simple being done by defining
  `${maven.projectBasedir}/.mvn/maven.config` file which contains the
  configuration options for the command line. For example things like `-T3 -U
  --fail-at-end`. So you only have to call maven just by using `mvn clean
  package` instead of `mvn -T3 -U --fail-at-end clean package` and not to miss
  the `-T3 -U --fail-at-end` options. The `${maven.projectBasedir}/.mvn/maven.config` 
  is located in the `${maven.projectBasedir}/.mvn/` folder which is in the root 
  of a multi module build. This folder is part of the project and will be checked 
  in into your version control. This results in being picked by everybody who 
  checks out the project and no need to remember to call this project 
  via `mvn -T3 -U --fail-at-end clean package` instead of `mvn clean package`.

* In Maven it is not simple to define JVM configuration on a per project base.
  The existing mechanism based on an environment variable `MAVEN_OPTS` and the
  usage of `${user.home}/.mavenrc` is an other
  option with the drawback of not being part of the project.

* Starting with this release you can define JVM configuration via
  `${maven.projectBasedir}/.mvn/jvm.config` file which means you can define the
  options for your build on a per project base. This file will become part of
  your project and will be checked in along with your project. So no need anymore
  for `MAVEN_OPTS`, `.mavenrc` files. So for example if you put the following JVM
  options into the `${maven.projectBasedir}/.mvn/jvm.config` file

```
-Xmx2048m -Xms1024m -XX:MaxPermSize=512m -Djava.awt.headless=true
```

* you don't need to remember of using this options in `MAVEN_OPTS` or switching
  between different configurations.


### Plugin Goal Invocation from Command Line


 * Improvement for [Plugin Goal Invocation from command line][MNG-5768]

If you call a plugin directly from command line like the following:

```
mvn exec:java
```
The configuration which is used here can be defined in your pom by using an execution id `default-cli`.

```
<project...>

  <build>
    <plugins>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>1.3.2</version>
        <executions>
          <execution>
            <id>default-cli</id>
            <configuration>
              <mainClass>com.soebes.test.First</mainClass>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins> 
  </build>
</project>
```

Starting with this Maven release you can now define several configuration for different
executions on command like the following:

```
<project...>

  <build>
    <plugins>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>1.3.2</version>
        <executions>
          <execution>
            <id>default-cli</id>
            <configuration>
              <mainClass>com.soebes.test.First</mainClass>
            </configuration>
          </execution>
          <execution>
            <id>second-cli</id>
            <configuration>
              <mainClass>com.soebes.test.Second</mainClass>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins> 
  </build>
</project>
```

So if you like to use the configuration given with the execution id:
`second-cli` this can be done like this:

```
mvn exec:java@second-cli
```

So now you can define more than one configuration for command line executions.
   
 * The Maven team has decided to [drop support for Win9x in launch scripts](https://issues.apache.org/jira/browse/MNG-5776)
   at long last. Yeah.


The above release notes have [originally been written by Karl Heinz Marbaise 
and migrated afterwards to the Apache Maven project](http://blog.soebes.de/blog/2015/03/17/apache-maven-3-dot-3-1-features/).


[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: http://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12330193
[5]: ../../docs/history.html

[maven-toolchains-plugin]: http://maven.apache.org/plugins/maven-toolchains-plugin/
[MNG-3891]: https://issues.apache.org/jira/browse/MNG-3891
[MNG-5745]: https://issues.apache.org/jira/browse/MNG-5745
[MNG-5754]: https://issues.apache.org/jira/browse/MNG-5754
[MNG-5771]: https://issues.apache.org/jira/browse/MNG-5771
[MNG-5767]: https://issues.apache.org/jira/browse/MNG-5767
[MNG-5768]: https://issues.apache.org/jira/browse/MNG-5768
[MNG-5780]: https://issues.apache.org/jira/browse/MNG-5780
