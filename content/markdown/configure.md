# Configuring Apache Maven

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

The configuration for Apache Maven itself and projects built with it resides
in a number of places:

## `MAVEN_OPTS` environment variable:

This variable contains parameters used to start up the JVM running Maven and
can be used to supply additional options to it. E.g. JVM memory
settings can be defined with the value `-Xms256m -Xmx512m`.

## `MAVEN_ARGS` environment variable:

Starting with Maven 3.9.0, this variable contains arguments passed to Maven before
CLI arguments. E.g., options and goals could be defined with the value
`-B -V checkstyle:checkstyle`.

## `settings.xml` file:

Located in USER_HOME/.m2, the settings files is designed to contain any
configuration for Maven usage across projects.

## `.mvn` directory:

Located within the project's **top level directory**, the files

- `maven.config`
- `jvm.config`
- `extensions.xml`

contain project specific configuration for running Maven.

This directory is part of the project and may be checked into version control.

### `.mvn/extensions.xml` file:

The old way (up to Maven 3.2.5) was to create a jar (must be shaded if you have other dependencies) which contains the extension and put
it manually into the `${MAVEN_HOME}/lib/ext` directory. This means you had to change the Maven installation. The consequence was that everyone
who likes to use this needed to change its installation and on-boarding a developer was more inconvenient. The other
option was to give the path to the jar on command line via `mvn -Dmaven.ext.class.path=extension.jar`. This has the drawback giving those
options to your Maven build every time you call Maven. Not very convenient as well.

From now on this can be done much more simpler and more Maven like way. Define an `.mvn/extensions.xml` file which looks like the following:

```xml
<extensions xmlns="http://maven.apache.org/EXTENSIONS/1.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/EXTENSIONS/1.1.0 https://maven.apache.org/xsd/core-extensions-1.0.0.xsd">
  <extension>
    <groupId/>
    <artifactId/>
    <version/>
  </extension>
</extensions>
```

Now you can simply use an extension by defining the usual maven coordinates groupId, artifactId, version as any other artifact. Furthermore, all transitive dependencies of those extensions will automatically being downloaded from your repository. So no need to create a shaded artifact anymore.

### `.mvn/maven.config` file:

It’s really hard to define a general set of options for calling the maven command line. Starting with Maven 3.3.1+, this could be solved by
putting these
options into a script, but this can now simply be done by defining `.mvn/maven.config` file which contains the
configuration options for the `mvn` command line.

For example things like `-T3 -U --fail-at-end`. So you only have to call Maven by using `mvn 
clean package` instead of `mvn -T3 -U --fail-at-end clean package` and not to miss the `-T3 -U --fail-at-end` options on every call.
The `.mvn/maven.config` is located in the project's top level `.mvn` directory and also works in the root of a multi module build.

**NOTICE** starting with Maven **3.9.0** each single argument must be put on a new line, so for the mentioned example your file will have content like:

```
-T3
-U 
--fail-at-end
```

### `.mvn/jvm.config` file:

Starting with Maven 3.3.1+ you can define JVM configuration via `.mvn/jvm.config` file which means you can define the options for your build on a per project base.
This file will become part of your project and will be checked in along with your project.
So no need anymore for `MAVEN_OPTS`, `.mavenrc` files.

So for example if you put the following JVM options into the `.mvn/jvm.config` file

```
-Xmx2048m -Xms1024m -XX:MaxPermSize=512m -Djava.awt.headless=true
```

You don't need to use these options in `MAVEN_OPTS` or switch between different configurations.

## Other guides

The following guides contain further information to specific configuration aspects:

* [Recommended Best Practice - Using a Repository Manager](./repository-management.html)
* [Documentation for settings.xml](./settings.html)
* [Configuring the logging](./maven-logging.html)
* [Configuring a HTTP Proxy](./guides/mini/guide-proxies.html)
* [Configuring a repository mirror](./guides/mini/guide-mirror-settings.html)
* [Various Tips for Configuring Maven](./guides/mini/guide-configuring-maven.html)
* [Password Encryption](./guides/mini/guide-encryption.html)

