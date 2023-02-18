title: Plugin Developers Centre
author: Brett Porter
date: 2008-01-01

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
## Plugin Developers Centre

 This documentation centre is for those who are developing Maven plugins. This might be for your own build, or as an accompaniment to your third party tool.

<!-- TODO: callout -->
 **What is a Mojo?** A mojo is a **M**aven plain **O**ld **J**ava **O**bject. Each mojo is an executable _goal_ in Maven, and a plugin is a distribution of one or more related mojos.

<!-- TODO: tasks as buttons? -->

- [Introduction to Plugin Development](../guides/introduction/introduction-to-plugins.html) - Introduction to concepts

- [Your First Mojo](../guides/plugin/guide-java-plugin-development.html) - Learn how to write your first plugin

- [Your First Report Mojo](../guides/plugin/guide-java-report-plugin-development.html) - Learn how to write your first reporting plugin

- [Testing your Plugin](./plugin-testing.html) - How to write tests for your plugins

- [Documenting your Plugin](./plugin-documenting.html) - How to write documentation for your plugins

- TODO: creating and using custom packaging (like [`maven-archetype` packaging](/archetype/archetype-packaging/))

- [Common Bugs and Pitfalls](./common-bugs.html) - Overview of problematic coding patterns

<!-- TODO: trails -->
### Reference

- [Mojo API and Annotation Reference](../developers/mojo-api-specification.html)

- [Maven Plugin Tools and annotations](/plugin-tools/examples/using-annotations.html)

- [Maven API Reference](../ref/current/index.html)

- [Maven Class Loading](../guides/mini/guide-maven-classloading.html)

### Extensions

- [Maven 3 lifecycle extensions](../examples/maven-3-lifecycle-extensions.html)

- [How to upgrade from Plexus javadoc tags to Plexus Java annotations?](./cookbook/plexus-plugin-upgrade.html)

- [Using JSR-330 (instead of Plexus Java annotations)](/maven-jsr330.html)
