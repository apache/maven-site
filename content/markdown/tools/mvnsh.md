# Maven Shell

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

The Maven Shell (`mvnsh`) is a built-in CLI tool for faster builds during active development.
It's available since Maven 4.0.0.

## Description

Each time you run an `mvn` command, the entire process chain is executed: booting Java, starting Maven, loading the
configuration, performing the task, tearing down, and exiting — **every single time**.

Maven Shell eliminates some tasks of this chain as it keeps a single Maven process running for as long as the shell remains open.
This means you avoid booting Java and Maven every time you start a build.

# Usage

To use Maven Shell you only need to open a command line and call `mvnsh`.
You will see that a Maven process is loaded, and you are now using Maven Shell, visible by the line prefix `maven mvnsh>` as shown in the example below.

```
D:\Github\Maven\maven>mvnsh

░▒▓██████████████▓▒░ ░▒▓█▓▒░░▒▓█▓▒░░▒▓███████▓▒░  ░▒▓███████▓▒░░▒▓█▓▒░░▒▓█▓▒░
░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░       ░▒▓█▓▒░░▒▓█▓▒░
░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ ░▒▓█▓▒▒▓█▓▒░ ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░       ░▒▓█▓▒░░▒▓█▓▒░
░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ ░▒▓█▓▒▒▓█▓▒░ ░▒▓█▓▒░░▒▓█▓▒░ ░▒▓██████▓▒░ ░▒▓████████▓▒░
░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░  ░▒▓█▓▓█▓▒░  ░▒▓█▓▒░░▒▓█▓▒░       ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░
░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░  ░▒▓█▓▓█▓▒░  ░▒▓█▓▒░░▒▓█▓▒░       ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░
░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░   ░▒▓██▓▒░   ░▒▓█▓▒░░▒▓█▓▒░░▒▓███████▓▒░ ░▒▓█▓▒░░▒▓█▓▒░
4.0.0-SNAPSHOT

maven mvnsh>
```

When you now run your regular Maven (`mvn`) commands, you will notice that they are executed immediately - without a delay to boot Java and Maven.

```
maven mvnsh> mvn verify
[INFO] Scanning for projects...
[INFO] --------------------------------------------------------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO]

[...]
```

To exit the shell and getting back to your regular command line, just type `exit`.

```
maven mvnsh>
maven mvnsh> exit

D:\Github\Maven\maven>
```

## See also

To improve performance and reduce build times even more, you can use the external [Maven Daemon](./mvnd.html), which manages a pool of
resident Maven processes.
