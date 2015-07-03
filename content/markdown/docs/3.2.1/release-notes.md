# Release Notes - Maven 3.2.1

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

## Maven 3.2.1

The Apache Maven team would like to announce the release of Maven 3.2.1.

Maven 3.2.1 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model
(POM), Maven can manage a project's build, reporting and documentation from a central place.

Maven 3 aims to ensure backward compatibility with Maven 2, improve usability, increase performance, allow safe embedding, and pave the way to implement many highly demanded features.

The core release is independent of the plugins available. Further releases of plugins will be made separately.
See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [http://maven.apache.org/](http://maven.apache.org/)
- the maven-user mailing list: [http://maven.apache.org/mail-lists.html](http://maven.apache.org/mail-lists.html)
- the reference documentation: [http://maven.apache.org/ref/3.2.1/](http://maven.apache.org/ref/3.2.1/)

The full list of changes can be found in our [issue management system][4].

## Highlights

### Transitive dependency excludes ([MNG-2315][MNG-2315])

It is sometimes useful to clip a dependency's transitive dependencies. A dependency may have incorrectly specified scopes, or dependencies that conflict with other dependencies in your project. Using wildcard excludes makes it easy to exclude all a dependency's transitive dependencies. In the case below you may be working with the maven-embedder and you want to manage the dependencies you use yourself, so you clip all the transitive dependencies:

```
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/maven-4.0.0.xsd">
  ...
  <dependencies>
    <dependency>
      <groupId>org.apache.maven</groupId>
      <artifactId>maven-embedder</artifactId>
      <version>3.1.0</version>
      <exclusions>
        <exclusion>
          <groupId>*</groupId>
          <artifactId>*</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
    ...
  </dependencies>
  ...
</project>
```

### Reactor changes

The Maven reactor is now pluggable ([MNG-5578][MNG-5578]). This feature required the introduction of a new custom Guice scope called `@SessionScoped` as we need to inject the `MavenSession` into the reactor right after the creation the `MavenSession` and before the execution of the build. The reactor behaves as a special type of artifact repository that provides the current behaviour users expect when building multi-module projects. New implementations can provide different semantics which can potentially provide new ways for multi-module projects to build, or to fix/add new features like resolving test JARs properly from the reactor. Now that the implementation is pluggable we can always default to the existing behaviour and allow people to try new behavior with a feature toggle.
  
The reactor now limits the projects that are exposed internally when `--projects` is used ([MNG-5557][MNG-5557]). What happened previously is that all the projects in the reactor would still be available for resolution even if you restricted the number of projects being built using `--projects`. This behaviour is now explicit: what you specify using `--projects` is what you will build and resolve against, if a project you need is not present in the constrained set the local repository will be consulted. There are cases where you may still want access to the projects that are normally part of the build but not currently being built, and those projects are tracked ([MNG-5582][MNG-5582]) and available in the `MavenSession`. It is also now possible to exclude projects from the reactor when using the `-am` and `-amd` options ([MNG-5230][MNG-5230]). If you select a project to build and its dependents (`-amd`) you can exclude one or more of the dependents with a command like the following: `mvn -amd --projects foo,bar,!dependent-of-bar`.

These combined changes above will allow us to make new implementation of the Maven reactor with improved/different behaviour. For example, this could potentially allow you to step into a directory and run Maven and have Maven pick up the projects surrounding the project you're building instead of going to local repository. We can experiment with different implementations and let users toggle between them until we ultimately decide on the right behaviour. Lots of user complain about the behaviour of the reactor and this allows stepwise improvement.

### AbstractMavenLifecycleParticipant changes ([MNG-5389][MNG-5389])

The `AbstractMavenLifecycleParticipant` has a new `afterSessionEnd()` method which will be executed when the build is complete. This feature was added to account for end of build deployment in a parallel build. The current logic employed in many plugins is to look for the last project in the array of projects that are in the reactor, but during a parallel build there is no guaranteed execution order and breaks in plugins which assume there is. There needs to be a way to execute logic when the build is definitively complete and `AbstractMavenLifecycleParticipant.afterSessionEnd()` provides this.

### New Builder interface ([MNG-5575][MNG-5575])

There is a new Builder interface which classes can implement to encapsulate a strategy for building projects. The existing strategies for building Maven serially and in parallel are now Builder implementations. It's now possible for others to implement additional strategies for building projects. This is a provisional interface and may change in the near future but will stabilize by Maven 4.0.0.

### Continuous delivery friendly versions ([MNG-5576][MNG-5576])

A simple change to prevent Maven from emitting warnings about versions with property expressions. Allowed property expressions in versions include `${revision}`, `${changelist}`, and `${sha1}`. These properties can be set externally, but eventually a mechanism will be created in Maven where these properties can be injected in a standard way. For example you may want to glean the current Git revision and inject that value into ${sha1}. This is by no means a complete solution for continuous delivery but is a step in the right direction.

### Custom lifecycle mapping ([MNG-5581][MNG-5581])

Implementations of the new `LifecycleMappingDelegate` interface can create custom lifecycles. The delegate has access to the existing lifecycles so it can create projections of existing lifecycles or create completely new ones. A simple example might be to create a lifecycle called `test-only` which might extract only the Surefire execution from the standard lifecycle and execute it.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: http://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12330185
[5]: ../../docs/history.html
[MNG-2315]: https://issues.apache.org/jira/browse/MNG-2315
[MNG-5578]: https://issues.apache.org/jira/browse/MNG-5578
[MNG-5582]: https://issues.apache.org/jira/browse/MNG-5582
[MNG-5230]: https://issues.apache.org/jira/browse/MNG-5230
[MNG-5389]: https://issues.apache.org/jira/browse/MNG-5389
[MNG-5578]: https://issues.apache.org/jira/browse/MNG-5578
[MNG-5530]: https://issues.apache.org/jira/browse/MNG-5530
[MNG-5549]: https://issues.apache.org/jira/browse/MNG-5549
[MNG-5575]: https://issues.apache.org/jira/browse/MNG-5575
[MNG-5576]: https://issues.apache.org/jira/browse/MNG-5576
[MNG-5581]: https://issues.apache.org/jira/browse/MNG-5581
[MNG-5557]: https://issues.apache.org/jira/browse/MNG-5557
