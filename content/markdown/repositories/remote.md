# Maven Remote Repositories

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

Remote repositories usually refers to repositories like [Maven Central Repository](/repository/index.html) is. These
repositories are holding the artifacts to be consumed by Maven builds.

## Example

Let's determine the full URL of following dependency:

```
    <dependency>
      <groupId>org.apache.maven</groupId>
      <artifactId>maven-plugin-api</artifactId>
      <version>3.8.1</version>
      <scope>provided</scope>
    </dependency>
```

As we have seen, if no type given for dependency, it defaults to "jar", and also scope does not takes part in layout, 
hence we have the following artifact properties:

| Project    | Artifact              |
|------------|-----------------------|
| groupId    | -> "org.apache.maven" |
| artifactId | -> "maven-plugin-api" |
| version    | -> "3.8.1"            |
| classifier | -> "" (none)          |
| extension  | -> "jar" (defaulted)  |

Applying layout, we end up with following path:

```
org/apache/maven/maven-plugin-api/3.8.1/maven-plugin-api-3.8.1.jar
```

And finally, by prepending remote repository baseUrl (as defined in repository POM section) we can calculate the
final absolute URL that this JAR can be pulled from:

https://repo.maven.apache.org/maven2/org/apache/maven/maven-plugin-api/3.8.1/maven-plugin-api-3.8.1.jar


