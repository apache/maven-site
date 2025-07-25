# Maven Update

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

The Maven Update (mvnup) tool helps you to upgrade your Maven project to Maven 4.

<!--MACRO{toc|fromDepth=2}-->

*Note*: This article does not cover Maven 4 features.
For a full list of those, please have a look at the [What's new in Maven 4?](./whatsnewinmaven4.html) article.

## Features
 
- Shipped with Maven 4 (from 4.0.0-rc4 on), no extra installation needed
- Command-line tool with check/apply workflow
- Automatic POM discovery and multi-module project support
- Supports model version 4.0.0 (compatible with Maven 3) and 4.1.0 (Maven 4 only)
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

The tool 


Instead of using the `mvn` command, use `mvnd`:

```bash
mvnd clean verify
```

The daemon will stay alive in the background, ready to process subsequent builds much faster.





