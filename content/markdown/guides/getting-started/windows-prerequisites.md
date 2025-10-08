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

# Maven on Windows

Maven is a command-line tool for building Java (and other) programs. The Maven project provides a simple ZIP file containing a precompiled version of Maven for your convenience. There is no installer. It's up to you to set up your prerequisites and environment to run Maven on Windows.

## Prerequisites

Maven is written in Java (and primarily used to build Java programs). Thus, the major prerequisite is the Java SDK. You need to install the Java SDK (e.g. from [Oracle's download site](https://www.oracle.com/technetwork/java/javase/downloads/index.html)).

Once Java is installed, you must ensure that the commands from the Java SDK are in your PATH environment variable. Running, for example,

```
java -version
```

must show the right version number.

## Maven Unpacked

You need to unpack the Maven distribution. Don't unpack it in the middle of your source code; pick some location and unpack it there. Let's assume that the path is `${maven.home}`.

## Maven in PATH

You run Maven by invoking a command-line tool: `mvn.cmd` from the `bin` directory of the Maven. To do this conveniently, `${maven.home}\bin` must be in your PATH, just like the Java SDK commands. You can add directories to your `PATH` in the control panel; the details vary by Windows version.

## Firewalls and Anti-virus

Firewall and Anti-virus sometimes prevent Java from running properly, or Windows Firewall (and various other Firewalls) actively prevent Java.exe from reaching out to the Internet to "download stuff" which is a key part of Maven. You may need to configure the Firewall or Anti-virus to add exceptions to allow such actions.

