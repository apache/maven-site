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

Apache Maven ships with two launcher commands in the `${MAVEN_HOME}/bin` directory,
which &ndash; based on several environment variables, project files and system files as described below
&ndash; constructs and runs the appropriate `java ...` command line which then invokes the Java Virtual Machine (JVM) 
that actually runs Maven.

* `mvn`: normal way to run from the command line.
* `mvnDebug`: launches `mvn` in debug mode, waiting for a Java debugger to attach to the address specified in `$MAVEN_DEBUG_ADDRESS` (default 8000).


## Environment variables

In the following the Unix syntax for environment variables is used in the text.

For Windows the syntax is slightly different, for `$A` use `%A%`.

### `$MAVEN_OPTS` / `%MAVEN_OPTS%`

The contents of this variable is placed in the `java` command _before_ the class name, and 
can therefore be used to provide additional arguments to the Java Virtual Machine (JVM) without
having to specify them on the command line every time.

Examples include garbage collector and memory configuration, but _not_ options to Maven itself

Use `java -help` and `java -X` to see what is possible in this particular JVM.

<!--
### `$MAVEN_ARGS`

Starting with Maven 4, this variable contains arguments passed to Maven before
CLI arguments. E.g., options and goals could be defined with the value
`-B -V checkstyle:checkstyle`.
-->

### `$MAVEN_BASEDIR` / `%MAVEN_BASEDIR%`

If set, this is considered the base directory of the Maven project.  If not set, 
the launcher scripts search for a `.mvn` folder towards the root of the drive, and if
found consider that the base directory.


### `$MAVEN_SKIP_RC` / `%MAVEN_SKIP_RC%`

If set, tells the launcher scripts _not_ to read the various Maven run command scripts before and after running the Maven JVM.
This is useful to get standard behaviour.

### `$JAVA_HOME` / `%JAVA_HOME%`

If set, the Java executable to be used must be found at `$JAVA_HOME/bin/java` or an error will
be reported.  If not set, the Java executable is found in the `$PATH`.

### `$MAVEN_DEBUG_OPTS` / `%MAVEN_DEBUG_OPTS%`

Additional options for the JVM if needed.  
They are put after `$MAVEN_OPTS` and before the `-classpath` argument.

## Files


### `$HOME/.m2/settings.xml` - `%USERPROFILE%\.m2\settings.xml`

This contains the user-specific Maven setup used across projects.  
Often this is used to tell Maven to use an internal repository instead of Maven Central if behind a firewall, 
various profiles, and passwords.

<!-- 

### `$MAVEN_BASEDIR/.mvn/maven.config`:

FIXME:  IS THIS STILL THE CASE? LAUNCHER SCRIPTS DOES NOT LOOK FOR IT?!?

This file contains additional command line arguments added to every invocation of Maven.

For example things like `-T3 -U --fail-at-end`. 
So you only have to call Maven just by using `mvn clean package` 
instead of `mvn -T3 -U --fail-at-end clean package`.

-->

### `$MAVEN_BASEDIR/.mvn/jvm.config`:

Allows a persistable alternative per project to `$MAVEN_OPTS` for providing 
additional arguments to the JVM before the class name on the constructed 
`java ...` command line.  Sample contents: 

        -Xmx2048m -Xms1024m -XX:MaxPermSize=512m -Djava.awt.headless=true

Word of caution:  If you for any reason need to configure memory usage or the garbage collector - which should
be considered a last resort - be absolutely certain that the configuration you use 
applies to the version of the JVM you are using, and that you understand what you are doing.


### `$MAVEN_BASEDIR/.mvn/extensions.xml`

FIXME:  WHERE IS THIS DONE?  IS THIS MAVEN 4 FUNCTIONALITY?

If you for any reason needs additional artifacts put on the classpath used by Maven
in your project, simply list them here with their usual Maven coordinates.


```xml
<extensions xmlns="http://maven.apache.org/EXTENSIONS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/EXTENSIONS/1.0.0 http://maven.apache.org/xsd/core-extensions-1.0.0.xsd">
  <extension>
    <groupId/>
    <artifactId/>
    <version/>
  </extension>
</extensions>
```

### `/usr/local/etc/mavenrc` + `/etc/mavenrc` + `$HOME/.mavenrc`

Unix-like systems only: 
Configuration files executed by the Unix launcher scripts first thing, unless
if the environment variable `$MAVEN_SKIP_RC` is set.

Typically environment variables &ndash; including `$PATH` &ndash; are set here.

### `%USERPROFILE%\mavenrc_pre.bat` + `%USERPROFILE%\mavenrc_pre.cmd`

Windows systems only:
Configuration files executed by the Windows launcher scripts first thing _before_ launching the Maven JVM, 
unless if the environment variable `%MAVEN_SKIP_RC%` is set.

This can be used to provide secrets, like the password for a keystore:

```
set MAVEN_OPTS=-Djavax.net.ssl.keyStore="<path to p12 file>" -Djavax.net.ssl.keyStoreType=pkcs12 -Djavax.net.ssl.keyStorePassword=<password>
```

IMPORTANT:  Information provided in this way may be accessible for others sharing the computer this runs on.



### `%USERPROFILE%\mavenrc_post.bat` + `%USERPROFILE%\mavenrc_post.cmd`

Windows systems only:
Configuration files executed by the Windows launcher scripts _after_ the Maven JVM itself exits, unless
if the environment variable `%MAVEN_SKIP_RC%` is set.


---


## Other guides

The following guides contain further information to specific configuration aspects:

* [Recommended Best Practice - Using a Repository Manager](./repository-management.html)
* [Documentation for settings.xml](./settings.html)
* [Configuring the logging](./maven-logging.html)
* [Configuring a HTTP Proxy](./guides/mini/guide-proxies.html)
* [Configuring a repository mirror](./guides/mini/guide-mirror-settings.html)
* [Various Tips for Configuring Maven](./guides/mini/guide-configuring-maven.html)
* [Password Encryption](./guides/mini/guide-encryption.html)
