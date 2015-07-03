# Release Notes &#x2013; Maven 3.2.2

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

## Maven 3.2.2

The Apache Maven team would like to announce the release of Maven 3.2.2.

Maven 3.2.2 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model
(POM), Maven can manage a project's build, reporting and documentation from a central place.

Maven 3 aims to ensure backward compatibility with Maven 2, improve usability, increase performance, allow safe embedding, and pave the way to implement many highly demanded features.

The core release is independent of the plugins available. Further releases of plugins will be made separately.
See the [PluginList][1] for more information.

We hope you enjoy using Maven! If you have any questions, please consult:

- the web site: [http://maven.apache.org/](http://maven.apache.org/)
- the maven-user mailing list: [http://maven.apache.org/mail-lists.html](http://maven.apache.org/mail-lists.html)
- the reference documentation: [http://maven.apache.org/ref/3.2.2/](http://maven.apache.org/ref/3.2.2/)

The full list of changes can be found in our [issue management system][4].

## Highlights

### Support version ranges in parent elements ([MNG-2199][MNG-2199])

Parent elements can now use bounded ranges in the version specification. You can now consistently use ranges for all intra-project dependencies, of which parents are a special case but still considered a dependency of projects that inherit from them. The following is now permissible:

```
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache</groupId>
    <artifactId>apache</artifactId>
    <version>[3.0,4.0)</version>
  </parent>
  <groupId>org.apache.maven.its.mng2199</groupId>
  <artifactId>valid</artifactId>
  <version>1</version>
  <packaging>pom</packaging>
</project>

```

Note this requires Maven 3.2.2 so if you use this new feature it is encouraged you add a Maven Enforcer rule to your build to ensure the use of Maven 3.2.2+.

### Requiring multiple profile activation conditions to be true does not work ([MNG-4565][MNG-4565])

Multiple profile activation conditions are now ANDed instead of ORed. We believe this is the correct behaviour as prior to this change activating a profile where multiple conditions are required was impossible. If you do need OR behaviour then you can create multiple profiles with a single condition. Profiles certainly have limitations and can likely be addressed without requiring conditional syntax in the POM by using a single custom activator that employs an expression language like [MVEL][mvel].

### Support resolution of Import Scope POMs from Repo that contains a ${parameter} ([MNG-5639][MNG-5639])

This feature helps support the pattern where many streams of development are setup with an individually sandboxed repository holding specific version of several shared components. A repository configuration might use the pattern ${nexus.baseurl}/content/groups/${stream.name} where the properties are set in settings.xml file. 

### Update maven-plugin-plugin:descriptor default binding from generate-resources phase to process-classes ([MNG-5346][MNG-5346])

Now when you use create plugins that strictly use annotation processing to generate the descriptor, you can avoid the confusing configuration previously required. This is what you typically needed to include in order to run the descriptor generator on compiled classes and avoid errors.

```
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-plugin-plugin</artifactId>
          <version>${mavenPluginPluginVersion}</version>
          <configuration>
            <skipErrorNoDescriptorsFound>true</skipErrorNoDescriptorsFound>
          </configuration>
          <executions>
            <execution>
              <id>mojo-descriptor</id>
              <phase>process-classes</phase>
              <goals>
                <goal>descriptor</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
```

This is no longer required and can be omitted when you use version 3.2+ of the maven-plugin-plugin.

### ${maven.build.timestamp} should use UTC instead of local timezone (or be configurable) ([MNG-5452][MNG-5452])

This provides a standard way creating a build time stamp, and allows better interoperability with systems that also employ UTC for timestamps like Eclipse Tycho.

### ${maven.build.timestamp} uses incorrect ISO datetime separator ([MNG-5647][MNG-5647])

The default format has been changed to standard ISO 8601 (`yyyy-MM-dd'T'HH:mm:ss'Z'`) to make it predictable for parsers.

## Complete Release Notes

See [complete release notes for all versions][5]

[0]: ../../download.html
[1]: ../../plugins/index.html
[2]: http://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&amp;version=12330186
[5]: ../../docs/history.html
[MNG-2199]: https://issues.apache.org/jira/browse/MNG-2199
[MNG-4565]: https://issues.apache.org/jira/browse/MNG-4565
[MNG-5346]: https://issues.apache.org/jira/browse/MNG-5346
[MNG-5452]: https://issues.apache.org/jira/browse/MNG-5452
[MNG-5639]: https://issues.apache.org/jira/browse/MNG-5639
[MNG-5647]: https://issues.apache.org/jira/browse/MNG-5647

[mvel]: http://mvel.codehaus.org
