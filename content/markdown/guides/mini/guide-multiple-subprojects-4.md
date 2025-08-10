# Guide to Working with Multiple Subprojects in Maven 4

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

(If you're working with Maven 3, please refer to the [Maven 3 edition of this guide](./guide-multiple-modules.html))

As seen in the introduction to the POM, Maven supports project aggregation in addition to project inheritance.
This section outlines how Maven processes [projects](/glossary.html#Project) with multiple subprojects, and how you can
work with them more effectively.

## The Reactor

The mechanism in Maven that handles projects with multiple subprojects is referred to as the _reactor_.
This part of the Maven core does the following:

* Collects all the available subprojects to build
* Sorts the subprojects into the correct build order
* Selects which subprojects to build
* Builds the selected subprojects in order

### Collecting subprojects

Subproject collection starts from one aggregator project.
That project defines the subprojects of which it consists using the `<subproject>` element.
This is a recursive process, so aggregators can have child subprojects which are aggregators themselves.

For this process to work, it does not matter which POM you start with.
Maven attempts to find the root of a multi-project setup, by traversing upwards in the directory structure until it
either finds a POM with a `.mvn` sibling directory or a POM with the `root` attribute set to true.
This allows Maven to resolve dependencies on subprojects from the same project, regardless of the location of
the starting POM.
When Maven fails to find the root, it assumes that the starting POM is the root.
For consistent behaviour, create a `.mvn` directory in the root directory of the project or set the `root` attribute in
the POM of the root project to true.

There are two ways to point at a starting POM.
By default, Maven reads the `pom.xml` file in the working directory.
Using the file parameter (`--file` or `-f`), you can point to another POM.

### Sorting subprojects

Because subprojects within a multi-project build can depend on each other, it is important that the reactor sorts
all the projects in a way that guarantees each project is built before it is required.

The following relationships are honoured when sorting projects:

* a project dependency on another subproject in the build
* a plugin declaration where the plugin is another subproject in the build
* a plugin dependency on another subproject in the build
* a build extension declaration on another subproject in the build
* the order declared in the `<subprojects>` element (if no other rule applies)

Note that only "instantiated" references are used - `dependencyManagement` and `pluginManagement` elements do not cause
a change to the reactor sort order.

### Selecting subprojects

By default, Maven builds all subprojects it has collected.
However, you can select a subset of these subprojects to build using command line flags.
These flags come in three categories:

* Inclusion and exclusion
* Relationships between subprojects
* Dealing with failures

This section ends with how these flags relate to each other.

#### Inclusion and exclusion

Using `--projects` you can specify which subprojects to build.
You can do this by specifying a comma-delimited list of project selectors.
A project selector is a string that is composed of the `groupId:artifactId`, only `:artifactId` or the relative path to
a subproject.

A subproject can be selected (default), or excluded from the build.
You exclude a subproject by prefixing the selector with a `!` or `-`.
To explicitly select a subproject, prefix it with a `+`.

When a selector does not resolve to an existing subproject, Maven fails the build.
You can prevent this by adding the `?` prefix.
This prefix should always go after the other prefixes.

#### Relationships between subprojects

Subprojects inside a project can have two types of relationships: parent/child and dependency/dependent.

When selecting a parent (aggregator), Maven automatically selects the child subprojects as well.
Similarly, Maven excludes child subprojects of an excluded parent (aggregator).
To prevent this recursive behaviour, combine `--projects` with `--non-recursive`.

Maven knows about the dependencies between subprojects inside the multi-project setup.
Using `--also-make`, Maven includes all dependencies of the selected projects in the build.
Similarly, `--also-make-dependents` lets Maven include all subprojects which depend on the selected projects.

#### Dealing with failures

There are several ways to customize how the reactor deals with failures.

* `--fail-at-end` fails the build after building as many subprojects as possible.
  In this case, subprojects that do not depend on a failed subproject will still be built.
* `--fail-fast`, in contrast, fails the build as soon as one subproject has failed.
  This is the default behaviour.
* `--fail-never` ignores build failures.

When a build has failed, and you want to start it again, you can skip building the subprojects that were previously
built successfully using `--resume` (or `-r`).
To resume a build from a specific subproject, you can use `--resume-from <selector>`.

#### Bringing it together

As said, Maven includes all subprojects in the reactor, even when the starting POM is not the root of the project.
The reactor then selects which subprojects to build using the following steps:

1. Reduce the full list of subprojects to the subproject of the starting POM and its children.
2. Further reduce this list to all subprojects included with `--projects` and, if `--resume` is given, the remaining
   subprojects
   of a previously failed build.
3. Further reduce this list by removing all subprojects that would have been built before the subproject selected by
   `--resume-from`.
4. Finally, further reduce this list by removing all subprojects that are excluded with `--projects` (using the `!` or
   `-`
   prefixes).

Each of the steps 1, 2 and 3 honor the `--also-make` and `--also-make-dependents` flags, if they are given.

## Command Line Options

No special configuration is required to take advantage of the reactor, however it is possible to customize its behavior.

The following command line switches are available:

|           Long           | Short  |                                                                    Summary                                                                     |                                                           Details                                                            |
|--------------------------|--------|------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| `--file`                 | `-f`   | Selects an alternative POM file or directory containing a POM file.                                                                            | [Collecting subprojects](#collecting-subprojects)                                                                            |
| `--non-recursive`        | `-N`   | Ignores any child subprojects that may be present in the starting POM. When combined with `-pl`, ignores the children of selected subprojects. | [Collecting subprojects](#collecting-subprojects) and [Relationships between subprojects](relationships-between-subprojects) |
| `--projects`             | `-pl`  | Specifies the subprojects to include or exclude from a build.                                                                                  | [Inclusion and exclusion](#inclusion-and-exclusion)                                                                          |
| `--also-make`            | `-am`  | Builds the specified subprojects, and any of their dependencies in the reactor.                                                                | [Relationships between subprojects](#relationships-between-subprojects)                                                      |
| `--also-make-dependents` | `-amd` | Builds the specified subprojects, and any that depend on them.                                                                                 | [Relationships between subprojects](#relationships-between-subprojects)                                                      |
| `--resume-from`          | `-rf`  | Resumes a reactor from the specified project (e.g. when it fails in the middle).                                                               | [Dealing with failures](#dealing-with-failures)                                                                              |
| `--resume`               | `-r`   | Resumes a reactor from the subproject where the previous build failed.                                                                         | [Dealing with failures](#dealing-with-failures)                                                                              |
| `--fail-fast`            | `-ff`  | Stops the overall build immediately whenever a subproject build fails. This is the default behavior.                                           | [Dealing with failures](#dealing-with-failures)                                                                              |
| `--fail-at-end`          | `-fae` | Continues the rest of the reactor if a particular subproject build fails and report all failed subprojects at the end instead.                 | [Dealing with failures](#dealing-with-failures)                                                                              |
| `--fail-never`           | `-fn`  | Never fails the build, regardless of the project result.                                                                                       | [Dealing with failures](#dealing-with-failures)                                                                              |

## Differences between Maven 3 and 4

![Sample multi-subproject setup](../../multi-subproject.svg)

The table below illustrates multiple scenarios which have changed between Maven 3 and 4. They assume a project structure
as depicted above.

|                              Scenario                              |                        Outcome (in order)                        |                            Maven 3                             |                            Maven 4                            |
|--------------------------------------------------------------------|------------------------------------------------------------------|----------------------------------------------------------------|---------------------------------------------------------------|
| Build an aggregator and its children                               | subproject-c, subproject-c-1, subproject-c-2                     | `mvn compile -pl subproject-c, subproject-c-1, subproject-c-2` | `mvn compile -pl subproject-c`                                |
| Build an aggregator and ignore its children                        | subproject-c, subproject-c-1, subproject-c-2                     | `mvn compile -pl subproject-c`                                 | `mvn compile -pl subproject-c -N`                             |
| Also make dependencies outside of current scope                    | parent, subproject-a, subproject-b, subproject-c, subproject-c-2 | N/A                                                            | `cd subproject-c/subproject-c-2 && mvn compile -am`           |
| Also make dependents outside of current scope                      | subproject-a, subproject-b, subproject-c-2                       | N/A                                                            | `cd subproject-a && mvn compile -amd`                         |
| Resuming from a subproject and build all dependencies              | parent, subproject-a, subproject-b, subproject-c, subproject-c-2 | N/A                                                            | `mvn compile -rf :subproject-c-2 -am` or `mvn compile -r -am` |
| Run specific goal on one subproject with dependencies from project | subproject-c-2                                                   | `mvn install && mvn jetty:run -f subproject-c/subproject-c-2`  | `mvn compile && mvn jetty:run -f subproject-c/subproject-c-2` |

## More information

* [Chapter 6. A Multi-module Project (Maven by Example)](http://books.sonatype.com/mvnex-book/reference/multimodule.html) -
  does not include Maven 4 changes!
