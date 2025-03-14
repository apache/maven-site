# Maven Daemon

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

The Maven Daemon (mvnd) is a daemon infrastructure for Maven that helps to reduce the build time by:
- Keeping the JVM running between builds
- Managing a pool of Maven processes
- Reusing the JVM and Maven processes across builds

## Features

- Significantly faster builds compared to regular Maven
- Compatible with existing Maven plugins and extensions
- Daemon process management
- Intelligent memory management
- Native executable available

## Installation

You can download Maven Daemon from our [download page](/download.html#Maven_Daemon).

For more detailed information and documentation, visit the [Maven Daemon GitHub repository](https://github.com/apache/maven-mvnd).

## Usage

Instead of using the `mvn` command, use `mvnd`:

```bash
mvnd clean install
```

The daemon will stay alive in the background, ready to process subsequent builds much faster.