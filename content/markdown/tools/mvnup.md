# Maven Update Tool

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

The Maven Update Tool (mvnup) helps you to upgrade your Maven project files (`pom.xml`) to Maven 4.

*Notes*: 

- The article does not cover Maven 4 features.
  For a full list of those, please have a look at the [What's new in Maven 4?](./whatsnewinmaven4.html) article.
- This article assumes that the reader is aware of the difference between a Build POM and a Consumer POM.
  The Maven Update Tool only affects the Build POM of your project.
  A brief description is included in the Maven 4 article mentioned in the previous bullet point.


<!--MACRO{toc|fromDepth=2}-->

## Features
 
- Shipped with Maven 4 (from 4.0.0-rc4 on), no extra installation needed
- Usable with Linux, Windows, and MacOS
- Command-line tool with check/apply workflow
- Automatic POM discovery and multi-module project support
- Supports model version 4.0.0 (compatible to be built with Maven 3) and 4.1.0 (Maven 4 only)
- Intelligent inference to remove redundant information in Maven 4.1.0+ models

### Plugin Compatibility & Upgrades
 
- Comprehensive plugin compatibility upgrades for Maven 4
- Parent POM plugin detection with proper XML formatting
- Plugin management section creation with correct element ordering
- Property-based version management support

### Maven 4 Compatibility Fixes

- Fix unsupported `combine.children` attributes (`override` → `merge`)
- Fix unsupported `combine.self` attributes (`append` → `merge`)
- Remove duplicate dependencies in `<dependencyManagement>` sections
- Remove duplicate plugins in `<pluginManagement>` sections
- Comment out repositories with unsupported expressions
- Fix incorrect `parent.relativePath` pointing to non-existent POMs
- Create `.mvn` directory for root directory detection to avoid warnings when build is executed with Maven 4

### Intelligent Model Inference

- Parent element trimming when parent is in same project
- Managed dependency removal for project artifacts
- Redundant subprojects list removal when matching direct children
- GroupId/version inference from parent when using relativePath
- Dependency inference that reverses Maven's resolution logic

### Advanced XML Processing

- Intelligent indentation detection supporting 2/4 spaces and tabs
- Document-wide formatting consistency preservation
- Proper element ordering following Maven POM schema standards
- pluginManagement placement before plugins sections
- Comprehensive JDOM-based XML manipulation with formatting preservation


## Usage

In general the Maven Update Tool needs three information to run:

1. The desired execution mode,
2. the target model version, and
3. the part(s) of the `pom.xml` that should be upgraded.

The tool provides two execution modes:
The first one is a dry check to see if an upgrade of the `pom.xml` files is possible and which parts would be changed.
The second mode actual updates the files and applies all applicable upgrades provided by the tool.

With the model version argument you control if your project should be upgraded to model version 4.0.0 or the new 4.1.0 version.
Model version 4.0.0 is fully compatible to be executed on Maven 3, while projects with a model version 4.1.0 can only be build, using Maven 4.

The tool allows you to update all or only certain parts of your Build POM.
In most cases you want it to check / update all parts, but you can also restrict it to the following:

- model: Only fixes parts effecting the model version, for example XML elements of the `pom.xml`.
- plugins: Only plugin and plugin management section are updated.
- inference: Remove duplicate dependency and plugin information. 


## More arguments

### Specify project directory
Similar to a standard Maven build, the Maven Update Tool searches for a project in the same folder in which the tool got executed.
To specify another directory, the `--directory` argument comes to your help.

Example:
```
mvnup check --model-version 4.1.0 --directory /path/to/project
```

## Examples

This section contains several examples how to use the Maven Update Tool.

### Basic upgrade workflow

Validates a full upgrade to model version 4.1.0: 
```
mvnup check --model-version 4.1.0 --all
```

Upgrade all project parts to model version 4.1.0:
```
mvnup apply --model-version 4.1.0 --all
```

### Specific upgrades

Upgrade all plugins and models to version 4.0.0

```
mvnup apply --plugins --model
```


Check for duplicate plugin and dependency declaration in combination with specifying the directory of the project
```
mvnup check --infer --directory /path/to/project
```