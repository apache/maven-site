# Maven Upgrade Tool

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

The Maven Upgrade Tool (`mvnup`) helps you to upgrade your project's (`pom.xml`) files to Maven 4.

*Notes*:

- The article does not cover Maven 4 features.
  For a full list of those, please see the [What's new in Maven 4?](./whatsnewinmaven4.html) article.
- This article assumes that the reader is aware of the difference between a Build POM and a Consumer POM.
  The Maven Upgrade Tool only affects the Build POM of your project.
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

The tool itself is called using the `mvnup` command.
In general the Maven Upgrade Tool needs three information to execute successfully:

1. The desired execution mode,
2. the target model version, and
3. the part(s) of the `pom.xml` that should be upgraded.

The tool provides two execution modes (goals):
The first one is a dry check to see if an upgrade of the `pom.xml` files is possible and which parts would be changed.
The second mode updates the files and applies applicable upgrades provided by the tool.
To execute a dry run pass `--check` to the tool, for an upgrade use `--apply`.

With the `--model-version` argument you control if your project should be upgraded to model version 4.0.0 or the new 4.1.0 version.
Model version 4.0.0 is fully compatible with Maven 3, while projects with a model version 4.1.0 can only be built, using Maven 4.
If not specified the tool will target model version 4.0.0.

The tool allows you to update all or only certain parts of your Build POM, by passing upgrade options to the execution.
In most cases you want it to check / update all parts.
This is achieved by either passing `-all` or no upgrade option, making it the default behavior.
To specify one or multiple upgrades the following upgrade options can be used:

- `--model`: Only upgrades those parts of the `pom.xml` that are incompatible with Maven 4, for example XML elements or expressions.
- `--plugins`: Only plugin and plugin management section are updated.
- `--infer`: Remove duplicate dependency and plugin information that can be inferred by Maven.

The tool will provide a detailed output, which includes much information about the passed execution mode, upgrade option, and how the different parts of your Build POM were affected by the tool's execution.
Please see the following example section of this article for an output example.

## More arguments

### Specify project directory

Similar to a standard Maven build, the Maven Upgrade Tool searches for a project in the same folder in which the tool got executed.
To specify another directory, the `--directory` argument comes to your help.

Example:

```
mvnup check --model-version 4.1.0 --directory /path/to/project
```

### Help

A short help about the command line arguments is available using the `--help` argument.

## Examples

This section contains several examples how to use the Maven Upgrade Tool.

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

### Output

The following example shows the output of an execution to fully update a project to model version 4.1.0.

**UPDATE WITH CURRENT Code, when build is fixed to include fixes of** https://github.com/apache/maven/pull/10915

```
D:\Github\mpmd386\PMD_314\SingleModuleRoot>mvnup apply --model-version 4.1.0 --all
[INFO]
[INFO] 1 problem was encountered while building the effective settings (use -e to see details)
[INFO]
[INFO] Maven Upgrade Tool - Apply
[INFO]
[INFO] Discovering POM files...
[INFO] Found 1 POM file(s)
[INFO]
[INFO] Maven Upgrade Tool
[INFO] → Upgrade options:
[INFO]   • --all (enables all upgrade options)
[INFO]
[INFO]   → Executing strategy: Upgrading POM model version
[INFO]     Upgrading POM model version
[INFO]       D:\Github\mpmd386\PMD_314\SingleModuleRoot\pom.xml (current: 4.1.0)
[INFO]         ✓ Already at target version 4.1.0
[INFO]
[INFO]       Upgrading POM model version Summary:
[INFO]         0 POM(s) modified
[INFO]         1 POM(s) needed no changes
[INFO]     ✓ Strategy completed successfully
[INFO]
[INFO]   → Executing strategy: Applying Maven inference optimizations
[INFO]     Applying Maven inference optimizations
[INFO]       Computing GAVs for inference from 1 POM(s)...
[INFO]       Computed 0 unique GAV(s) for inference
[INFO]       D:\Github\mpmd386\PMD_314\SingleModuleRoot\pom.xml (current: 4.1.0)
[INFO]         ✓ No inference optimizations needed
[INFO]
[INFO]       Applying Maven inference optimizations Summary:
[INFO]         0 POM(s) modified
[INFO]         1 POM(s) needed no changes
[INFO]     ✓ Strategy completed successfully
[INFO]
[INFO]   → Executing strategy: Applying Maven 4 compatibility fixes
[INFO]     Applying Maven 4 compatibility fixes
[INFO]       D:\Github\mpmd386\PMD_314\SingleModuleRoot\pom.xml (checking for Maven 4 compatibility issues)
[INFO]         ✓ No Maven 4 compatibility issues found
[INFO]
[INFO]       Applying Maven 4 compatibility fixes Summary:
[INFO]         0 POM(s) modified
[INFO]         1 POM(s) needed no changes
[INFO]     ✓ Strategy completed successfully
[INFO]
[INFO]   → Executing strategy: Upgrading Maven plugins to recommended versions
[INFO]     Upgrading Maven plugins to recommended versions
[INFO]       D:\Github\mpmd386\PMD_314\SingleModuleRoot\pom.xml (checking for plugin upgrades)
[INFO]         ✓ No plugin upgrades needed
[INFO]
[INFO]       Upgrading Maven plugins to recommended versions Summary:
[INFO]         0 POM(s) modified
[INFO]         1 POM(s) needed no changes
[INFO]     ✓ Strategy completed successfully
[INFO]
[INFO] Overall Upgrade Summary:
[INFO]   1 POM(s) processed
[INFO]   0 POM(s) modified
[INFO]   1 POM(s) needed no changes
[INFO]   0 error(s) encountered
[INFO]
[INFO] Executed Strategies:
[INFO]   • Upgrading POM model version
[INFO]   • Applying Maven inference optimizations
[INFO]   • Applying Maven 4 compatibility fixes
[INFO]   • Upgrading Maven plugins to recommended versions
[INFO] ✓ No upgrades needed - all POMs are up to date
[INFO]
[INFO] Saving modified POMs...

```

