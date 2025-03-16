---

title: Example: Injecting POM Properties via Settings.xml
author: 
- John Casey
date: 2006-04-20
----------------

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

# Example: Injecting POM Properties via Settings.xml

## Impetus

You have a plugin parameter that should contain a user-specific value. This parameter has a common format \(relative directory structure\), but depends on knowing the directory of the installed application or something.

## Plugin Configuration

```unknown
<project>
  [...]
  <build>
    <plugins>
      <plugin>
        <groupId>org.myproject.plugins</groupId>
        <artifactId>my-cool-maven-plugin</artifactId>
        <version>1.0</version>
        <configuration>
          <deploymentDirectory>${application-home}/deploy</deploymentDirectory>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

## `settings.xml`

```unknown
<settings>
  [...]
  <profiles>
    <profile>
      <id>inject-application-home</id>
      <properties>
        <application-home>/path/to/application</application-home>
      </properties>
    </profile>
  </profiles>

  <activeProfiles>
    <activeProfile>inject-application-home</activeProfile>
  </activeProfiles>
</settings>
```

## Explanation

When Maven loads the project&apos;s POM, it will pickup the activated profiles from the `activeProfiles` section of the `settings.xml` file, and inject the properties declared within the profile. When the POM is interpolated, the `application-home` property will already have been injected, so will allow the plugin&apos;s parameter value to be resolved.

