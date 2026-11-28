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

The Maven Upgrade Tool (`mvnup`) helps you to upgrade your project's `pom.xml` files to Maven 4 specific features.

*Notes*:

- The article does not cover Maven 4 features.
  For a full list of those, please see the [What's new in Maven 4?](/whatsnewinmaven4.html) article.
- This article assumes the [Starting with Maven 4](/guides/mini/guide-migration-to-mvn4.html) guide has been used to prepare.

<!--MACRO{toc|fromDepth=2}-->

## Features

- Shipped with Maven 4 (from 4.0.0-rc4 on), no extra installation needed
- Usable with Linux, Windows, and MacOS
- Command-line tool with check/apply workflow
- Automatic POM discovery and multi-module/subproject project support
- Supports model version 4.0.0 (compatible to be built with Maven 3) and 4.1.0 (Maven 4 only)
- Intelligent inference to remove redundant information in Maven 4.1.0+ models
- Upgrade deprecated phase names to new Maven 4 phase names (when upgrading to model version 4.1.0)

### Plugin Compatibility & Upgrades

- Comprehensive plugin compatibility upgrades for Maven 4
- Parent POM plugin detection with proper XML formatting
- Plugin management section creation with correct element ordering
- Property-based version management support
- Fix invalid `Automatic-Module-Name` manifest entries in plugin configuration

### Maven 4 Compatibility Fixes

- Remove invalid `combine.children` attribute values (valid values are `append` and `merge`; anything else is removed)
- Remove invalid `combine.self` attribute values (valid values are `override`, `merge`, and `remove`; anything else is removed)
- Remove duplicate dependencies in `<dependencyManagement>` sections
- Remove duplicate plugins in `<pluginManagement>` sections
- Upgrade HTTP repository URLs to HTTPS in `<repositories>` and `<distributionManagement>`
- Comment out repositories with unsupported expressions
- Fix incorrect `parent.relativePath` pointing to non-existent POMs
- Create `.mvn` directory for root directory detection to avoid warnings when build is executed with Maven 4
- Replace incompatible `.mvn/extensions.xml` entries with Maven 4 compatible equivalents (e.g., `kr.motd.maven:os-maven-plugin` → `eu.maveniverse.maven.nisse:extension`)

### Intelligent Model Inference (Maven 4 only)

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
- Comprehensive XML manipulation with formatting preservation

## Usage

The tool itself is called using the `mvnup` command.
The Maven Upgrade Tool runs based on 3 aspects:

1. the desired execution mode as a goal:
   - `check` is a dry check to see if an upgrade of the `pom.xml` files is possible and which parts would be changed,
   - `apply` updates the files and applies applicable upgrades provided by the tool.
2. the target model version (`--model-version`/`-m`, defaults to `4.0.0`), and
3. the part(s) of the `pom.xml` that should be upgraded (defaults to all available updates).

With the `--model-version` (or `-m`) argument you control if your project should be kept to model version `4.0.0` or upgraded to the new `4.1.0` version.
Model version 4.0.0 is fully compatible with Maven 3, while projects with a model version `4.1.0` can only be built using Maven 4.
If not specified the tool will target model version `4.0.0`.

The tool allows you to update all or only certain parts of your `pom.xml`, by passing upgrade options to the execution.
When no upgrade option is specified, `--model` and `--plugins` and `--infer` are applied by default (targeting model version 4.0.0).
To specify one or multiple upgrades the following upgrade options can be used:

- `--model`: Only upgrades those parts of the `pom.xml` that are incompatible with Maven 4, for example XML elements or expressions.
- `--plugins`: Only plugin and plugin management section are updated.
- `--infer` (or `-i`): Remove redundant dependency, plugin, and project information that can be inferred by Maven (e.g., groupId/version inherited from parent).
- `--all` (or `-a`): Apply all upgrades, equivalent to `--model-version 4.1.0 --model --plugins --infer`.

The tool will provide a detailed output, which includes much information about the passed execution mode, upgrade option, and how the different parts of your `pom.xml` were affected by the tool's execution.
Please see the following example section of this article for an output example.

## More arguments

### Specify project directory

Similar to a standard Maven build, the Maven Upgrade Tool searches for a project in the same folder in which the tool got executed.
To specify another directory, the `--directory` (or `-d`) argument comes to your help.

Example:

```
mvnup check --model-version 4.1.0 --directory /path/to/project
```

### Help

A short help about the command line arguments is available using the `--help` argument or the `help` goal.

## Examples

This section contains several examples how to use the Maven Upgrade Tool.

### Basic upgrade workflow

Check what a full upgrade to model version 4.1.0 would change:

```
mvnup check --model-version 4.1.0 --all
```

Apply all upgrades to model version 4.1.0:

```
mvnup apply --model-version 4.1.0 --all
```

### Specific upgrades

Upgrade plugins known to fail with Maven 4 to their minimum compatible versions:

```
mvnup apply --plugins
```

Check for redundant information that can be inferred by Maven, specifying the project directory:

```
mvnup check --infer --directory /path/to/project
```

### Output

The following example shows the output of running `mvnup apply --model-version 4.1.0 --all` on a project
that is already fully up to date:

```
$ mvnup apply --model-version 4.1.0 --all
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
[INFO]       pom.xml (current: 4.1.0)
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
[INFO]       pom.xml (current: 4.1.0)
[INFO]         ✓ No inference optimizations needed
[INFO]
[INFO]       Applying Maven inference optimizations Summary:
[INFO]         0 POM(s) modified
[INFO]         1 POM(s) needed no changes
[INFO]     ✓ Strategy completed successfully
[INFO]
[INFO]   → Executing strategy: Applying Maven 4 compatibility fixes
[INFO]     Applying Maven 4 compatibility fixes
[INFO]       pom.xml (checking for Maven 4 compatibility issues)
[INFO]         ✓ No Maven 4 compatibility issues found
[INFO]
[INFO]       Applying Maven 4 compatibility fixes Summary:
[INFO]         0 POM(s) modified
[INFO]         1 POM(s) needed no changes
[INFO]     ✓ Strategy completed successfully
[INFO]
[INFO]   → Executing strategy: Upgrading Maven plugins to recommended versions
[INFO]     Upgrading Maven plugins to recommended versions
[INFO]       pom.xml (checking for plugin upgrades)
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
