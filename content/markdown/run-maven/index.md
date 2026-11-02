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

# Building a Project with Maven

The vast majority of Maven-built projects can be built with the following command:

```
mvn verify
```

This command tells Maven to build all the subprojects, and to check if all integration tests succeeded (when any was defined).
That's it!
If you look in the `target` subdirectory, you should find the build output and the final library or application that was being built.

**Note:** Some projects have multiple subprojects, so the library or application you are looking for may be in a subproject's subdirectory.

While this will build most projects and Maven encourages this standard convention, builds can be customisable.
If this does not suffice, please consult the project's documentation.

## More than just the Build

Maven can do more than just build software - it can assist with testing, run web applications and produce reports on projects, as well as any number of other tasks provided by plug-ins.

## When Things go Wrong

The following are some common problems when building with Maven, and how to resolve them.

### Missing Dependencies

A missing dependency presents with an error like the following:

```
[WARNING] The POM for org.junit.jupitr:junit-jupiter:jar:5.11.0 is missing, no dependency information available
[INFO] ------------------------------------------------------------------------
[ERROR] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
...
[ERROR] Failed to execute goal on project my-app: Could not resolve dependencies for project com.mycompany.app:my-app:jar:1.0-SNAPSHOT
[ERROR] dependency: org.junit.jupitr:junit-jupiter:jar:5.11.0 (test)
[ERROR] 	Could not find artifact org.junit.jupitr:junit-jupiter:jar:5.11.0 in central (https://repo.maven.apache.org/maven2)
```

To resolve this issue, it depends on what the dependency is and why it is missing.
The most common cause is because it can not be redistributed from the repository and must be installed manually with `mvn install:install-file`.
This is most common with some older JARs from Sun (usually `javax.*` group IDs), and is further documented in the [Guide to Coping with Sun JARs](../guides/mini/guide-coping-with-sun-jars.html).

You can check the repositories named in the error to ensure that the expected ones are listed - it may be that the project requires an alternative repository that has not been declared properly or is not accessible with your Maven configuration.

In other cases, it may be an incorrectly declared dependency (like the typo in the example above) which the project would need to fix, like a compilation error.

### Inconsistent output

Most plugins are optimized to know if they have to execute their task.
In some cases, the output can be polluted from a previous build and the end result is not what you expected.
In such rare situations, you can call the `clean` phase which means: remove the output directory.
You can also call it as `mvn clean verify` which means: first clean up the output directory, next build the project and verify the outcome.

