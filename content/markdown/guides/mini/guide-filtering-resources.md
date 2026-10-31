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

# Guide to Filtering Resources

Resource filtering replaces variables in your resource files with values known at build time, such as a project name or version.
The replacement is done by [Maven Filtering](/shared/maven-filtering/), which the [Maven Resources Plugin](/plugins/maven-resources-plugin/), the Maven WAR Plugin and the Maven EAR Plugin share.
The examples below use the resources plugin; the rules for variables, delimiters and escaping are the same wherever Maven Filtering does the work.

## Enabling filtering

Variables in a resource are written with the `${...}` or `@...@` delimiters.
They can come from the system properties, the project properties, filter files, and the command line.

For example, take a resource `src/main/resources/hello.txt` that contains

```
Hello ${name}
```

and a POM that declares the resource directory:

```xml
<project>
  ...
  <name>My Resources Plugin Practice Project</name>
  ...
  <build>
    ...
    <resources>
      <resource>
        <directory>src/main/resources</directory>
      </resource>
      ...
    </resources>
    ...
  </build>
  ...
</project>
```

Running

```
mvn resources:resources
```

copies the file to `target/classes/hello.txt` unchanged, because filtering is off by default:

```
Hello ${name}
```

Add a `<filtering>` element to the resource and set it to `true`:

```xml
<project>
  ...
  <resource>
    <directory>src/main/resources</directory>
    <filtering>true</filtering>
  </resource>
  ...
</project>
```

Now `target/classes/hello.txt` contains the project name from the POM:

```
Hello My Resources Plugin Practice Project
```

## Setting values

A value given on the command line with `-D` replaces the project's value.
To change `name` to `world`:

```
mvn resources:resources -Dname="world"
```

The result in `target/classes/hello.txt` is

```
Hello world
```

You can also define your own variables in the `<properties>` element of the POM.
If `hello.txt` uses `${your.name}`, this POM supplies the value:

```xml
<project>
  ...
  <properties>
    <your.name>world</your.name>
  </properties>
  ...
</project>
```

### Filter files

To keep the values out of the POM, put them in a properties file and list it in `<filters>`.
A filter file `my-filter-values.properties` that contains

```properties
your.name = world
```

is added like this:

```xml
<project>
  ...
  <build>
    ...
    <filters>
      <filter>my-filter-values.properties</filter>
    </filters>
    ...
  </build>
  ...
</project>
```

The `filters` parameter of the [resources:resources](/plugins/maven-resources-plugin/resources-mojo.html#filters) goal adds filter files to a single execution, and `useBuildFilters` controls whether the files in `<build><filters>` are used for that execution.

## Binary files

Do not filter files with binary content such as images, because that most likely corrupts them.

If you have both text and binary resources, keep them in two directories: the default `src/main/resources` for the files that are not filtered, and for example `src/main/resources-filtered` for the files that are:

```xml
<project>
  ...
  <build>
    ...
    <resources>
      <resource>
        <directory>src/main/resources-filtered</directory>
        <filtering>true</filtering>
      </resource>
      ...
    </resources>
    ...
  </build>
  ...
</project>
```

Alternatively, [configure the file extensions](/plugins/maven-resources-plugin/examples/binaries-filtering.html) that are not filtered. The [nonFilteredFileExtensions](/plugins/maven-resources-plugin/resources-mojo.html#nonFilteredFileExtensions) parameter adds to the built-in list.

## Escaping

To keep an expression from being replaced, precede it with an escape string.
Set the escape string with the [escapeString](/plugins/maven-resources-plugin/resources-mojo.html#escapeString) parameter:

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-resources-plugin</artifactId>
        <version>3.5.0</version>
        <configuration>
          ...
          <escapeString>\</escapeString>
          ...
        </configuration>
      </plugin>
    </plugins>
    ...
  </build>
  ...
</project>
```

With the escape string `\`, an escaped expression is not interpolated, and the escape string is removed from the output:

| In the resource | In the output |
|:---|:---|
| `\${java.home}` | `${java.home}` |

Anything else preceded by the escape string is passed through unchanged.
The same applies to expressions delimited by `@`.

## Encoding

A character encoding such as `US-ASCII`, `UTF-8` or `UTF-16` is used to read and write filtered files.
The best practice is to [define the encoding](/general.html#encoding-warning) through the `${project.build.sourceEncoding}` property, which the resources plugin uses by default for its [encoding](/plugins/maven-resources-plugin/resources-mojo.html#encoding) parameter:

```xml
<project>
  ...
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    ...
  </properties>
  ...
</project>
```

To use a different encoding for the resources only, set the `encoding` parameter in the plugin configuration:

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-resources-plugin</artifactId>
        <version>3.5.0</version>
        <configuration>
          ...
          <encoding>UTF-8</encoding>
          ...
        </configuration>
      </plugin>
    </plugins>
    ...
  </build>
  ...
</project>
```

Filtered `.properties` files can use their own encoding through the [propertiesEncoding](/plugins/maven-resources-plugin/resources-mojo.html#propertiesEncoding) parameter (since 3.2.0); it defaults to `encoding`.

## See also

* [Filtering properties files](/plugins/maven-resources-plugin/examples/filtering-properties-files.html)
* [Custom resources filters](/plugins/maven-resources-plugin/examples/custom-resource-filters.html)
* [Specifying resource directories](/plugins/maven-resources-plugin/examples/resource-directory.html)
