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

# Skipping Mojo Executions with `maven.lifecycle.filter`

<!--MACRO{toc|fromDepth=2}-->

## Overview

Maven 4.2 introduces a **unified, generic mechanism** to skip any mojo execution at
the command line, independent of whether the plugin itself provides a skip property.

The `-Dmaven.lifecycle.filter` user property accepts a comma-separated list of
**predicates**. Any mojo execution matching at least one predicate is skipped at
runtime: Maven logs the skipped execution at `INFO` level and fires a `MojoSkipped`
execution event so that build listeners are notified.

## Motivation

Traditionally, skipping a mojo execution required a plugin-specific property such as
`-DskipTests`, `-Denforcer.skip`, or `-Dcheckstyle.skip`. This approach has several
drawbacks:

- **Inconsistent naming** — every plugin invents its own convention.
- **Not universally available** — many plugins have no skip property at all.
- **Coarse-grained** — the property skips all executions of the plugin; it cannot
  target a specific execution id or a single goal within a plugin.

`maven.lifecycle.filter` addresses all three issues with a single, predictable syntax.

## Predicate syntax

A predicate is a string that describes which mojo executions to skip. Multiple
predicates are separated by commas and are combined with **OR** semantics: a mojo
execution is skipped if it matches *any* predicate.

### Coordinate predicates

Coordinate predicates identify mojo executions by plugin coordinates, goal, and
optional execution id.  An empty segment acts as a **wildcard** that matches any value.

| Form | Matches by | Example |
|------|------------|---------|
| `*` | Every mojo execution | `*` |
| `:A` | Plugin artifactId | `:maven-surefire-plugin` |
| `G:A` | Plugin groupId and artifactId | `org.apache.maven.plugins:maven-enforcer-plugin` |
| `G:A:v:g` | Plugin groupId, artifactId, version, goal | `org.apache.maven.plugins:maven-enforcer-plugin:3.5.0:enforce` |
| `G:A:v:g@e` | Same with execution id | `org.apache.maven.plugins:maven-enforcer-plugin::enforce@default` |
| `P` | Plugin goal prefix | `surefire` |
| `P:v:g` | Plugin goal prefix, version, goal | `surefire:3.2.5:test` |
| `P:v:g@e` | Plugin goal prefix, version, goal, execution id | `surefire::test@default-test` |

**How the parser distinguishes groupId from prefix:** a first segment that contains a
`.` (e.g. `org.apache.maven.plugins`) is treated as a groupId; a segment without a
`.` is treated as a goal prefix.  Plugins whose groupId contains no `.` (rare, but
possible) must be targeted by artifactId using the `:A` form instead.

### Phase predicate

```
phase(<name>)
```

Skips every mojo execution bound to the named lifecycle phase.

| Example | Effect |
|---------|--------|
| `phase(test)` | Skip all mojos bound to the `test` phase |
| `phase(process-resources)` | Skip all mojos bound to `process-resources` |

## Common examples

```
# Skip all tests (equivalent to -DskipTests for plugins that support it,
# but works for any plugin bound to the test phase)
-Dmaven.lifecycle.filter=phase(test)

# Skip all executions of maven-enforcer-plugin
-Dmaven.lifecycle.filter=:maven-enforcer-plugin

# Skip enforcer and all test-phase mojos
-Dmaven.lifecycle.filter=:maven-enforcer-plugin,phase(test)

# Skip every mojo — essentially a dry-run skeleton
-Dmaven.lifecycle.filter=*

# Skip a specific surefire execution by id (useful when multiple executions are bound)
-Dmaven.lifecycle.filter=surefire::test@integration-tests

# Skip checkstyle by prefix (regardless of version or execution id)
-Dmaven.lifecycle.filter=checkstyle
```

## Interaction with other skip mechanisms

`maven.lifecycle.filter` operates at the `MojoExecutor` level: it intercepts
executions *after* the build plan has been computed, just before the plugin is
invoked.  It is fully orthogonal to:

- **Plugin-specific skip properties** (`-DskipTests`, etc.) — both can be active at
  the same time.
- **`-pl` / `-am` / `-amd`** — those prune the reactor; this prunes individual mojo
  executions within a module.
- **`-rf` (resume-from)** — that skips modules; this skips executions inside a module.

## Build output

When a mojo execution is skipped by the filter, Maven logs the event at `INFO` level,
showing the matched predicate:

```
[INFO] Skipping org.apache.maven.plugins:maven-surefire-plugin:3.2.5:test (default-test) [filter: phase(test)]
```

Build event listeners also receive a `MojoSkipped` execution event, allowing tools
(IDEs, CI extensions, etc.) to react to filtered executions the same way they react
to mojos skipped by other means.

## AI agent guidance

The following structured summary is provided for AI coding assistants so they can
suggest the correct predicate form without ambiguity.

**Property name:** `maven.lifecycle.filter`  
**CLI usage:** `-Dmaven.lifecycle.filter=<predicate>[,<predicate>…]`  
**Available since:** Maven 4.2.0  
**Semantics:** OR — a mojo execution is skipped if it matches *any* predicate.  
**Predicate types:**

- `*` — skip everything.
- `:A` — skip by artifactId (note the leading colon).
- `G:A` — skip by groupId (must contain `.`) and artifactId.
- `G:A:v:g` and `G:A:v:g@e` — skip by full coordinate; empty segments are wildcards.
- `P` — skip by goal prefix (no dot in first segment).
- `P:v:g` and `P:v:g@e` — skip by prefix + version + goal (+ optional execution id).
- `phase(name)` — skip all mojos bound to the named phase.

**Decision rules for agents:**

1. Use `phase(test)` to skip test execution rather than `:maven-surefire-plugin` when
   multiple plugins are bound to the test phase (e.g. both Surefire and Failsafe).
2. Use `:A` (not `G:A`) for plugins whose groupId contains no `.`.
3. Empty segments are wildcards: `surefire::test` matches any version of the surefire
   goal `test`.
4. Use `@executionId` only when the same goal is bound multiple times and only one
   binding should be suppressed.
5. Combine predicates with commas; never repeat the `-D` flag.

**Limitations:**

- GroupIds without a `.` cannot be used in `G:A` form — use `:A` instead.
- The filter applies at execution time, not plan-build time: the build plan still
  shows the mojo, but it is not invoked.
- An invalid predicate (e.g. `phase()` with an empty phase name) causes a build
  error with a clear diagnostic message.

## See also

- [JIRA MNG-12535](https://issues.apache.org/jira/browse/MNG-12535) — phase filter
- [JIRA MNG-12536](https://issues.apache.org/jira/browse/MNG-12536) — coordinate filter
- [Configuring Plug-ins](./guide-configuring-plugins.html)
- [Introduction to the Build Lifecycle](../introduction/introduction-to-the-lifecycle.html)
