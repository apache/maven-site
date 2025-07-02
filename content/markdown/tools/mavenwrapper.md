# Maven Wrapper

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

If you want to ensure that a project is always build with a certain Maven version or JDK you can either define rules using the Maven enforcer plugin.
But this leaves the user alone with an error message when the user's environment does not match the requirements.
With the Maven Wrapper you not only ensure the correct versions, but also provide the possibility to automatically download them so project is fully encapsulated from the user's environment.

## One tool - three parts

The Maven Wrapper is divided into three parts

* The [Maven Wrapper Plugin](https://maven.apache.org/wrapper/maven-wrapper-plugin/) to set up and run the Maven Wrapper.
* The [Maven Wrapper JAR](https://maven.apache.org/wrapper/maven-wrapper/index.html) which downloads the desired versions.
* The [Maven Wrapper Distribution](https://maven.apache.org/wrapper/maven-wrapper-distribution/index.html) provides everything that has to be installed into a project wanting to use Maven Wrapper.

## Usage

To use the Maven Wrapper you first define the desired versions (and maybe URLs) in a `wrapper/maven-wrapper.properties` file inside the `.mvn` folder of your project.
The Maven Wrapper plugin picks up this property file and installs everything defined.
After that you can build the project using the Wrapper by calling `./mvnw <goals>` (or `mvnw.cmd <goals>` on Windows).

The full detailed information and documentation is available on the [Maven Wrapper documentation](https://maven.apache.org/wrapper/index.html).
