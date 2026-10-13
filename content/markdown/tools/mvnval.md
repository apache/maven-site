# Maven POM Validation Tool

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

The Maven POM Validation Tool (`mvnval`) validates POM files and reports all problems without
running a build. It is a built-in tool, shipped with Maven 4.1.0.

<!--MACRO{toc|fromDepth=2}-->

## Why Not `mvn validate`

`mvn validate` needs a fully resolvable project: it stops at the first thing it cannot resolve
and reports only the first error it encounters. `mvnval` reads one or more POMs and reports every
problem it finds across all of them, with a single exit code for the batch. `--mode raw` does not
reach a repository at all, making it usable offline and on POMs whose parent has not yet been
published.

## Modes

`mvnval` has two validation modes, controlled by `--mode`:

- **`effective`** *(default)* — Runs the raw pass, then resolves parents and imported BOMs and
  checks what needs them. Reads from the network using the configured repositories and caches what
  it fetches. Use this to validate a POM as Maven would see it during a build.

- **`raw`** — Reads the file and raw models only. Reaches no repository, so it works offline and
  on POMs whose parent is not yet published. Cannot see things that only appear after inheritance
  is applied, such as a dependency version that lives in a parent's `dependencyManagement`.

When more than one POM is given in effective mode, members of the set that reference each other as
parents or BOM imports are resolved from disk rather than from a repository, so the verdict does
not depend on publication order.

## Usage

The tool is called using the `mvnval` command. It accepts any number of POM files; when none are
given it defaults to `./pom.xml`.

```
mvnval [options] [<pom> ...]
```

### Basic examples

Validate the POM in the current directory:

```
mvnval
```

Validate a specific POM file:

```
mvnval path/to/pom.xml
```

Validate several POMs at once:

```
mvnval parent/pom.xml child/pom.xml
```

Validate in raw mode (offline, no repository access):

```
mvnval --mode raw pom.xml
```

### Output format

By default `mvnval` writes human-readable text output with XML context lines around each problem:

```
pom.xml:
  ERROR 'dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique:
        junit:junit:jar -> version 4.13.2 vs 4.12 @ line 12, column 5
     10 |     <dependency>
     11 |       <groupId>junit</groupId>
  >  12 |       <artifactId>junit</artifactId>
     13 |       <version>4.12</version>
     14 |     </dependency>
```

Pass `--format json` to get a machine-readable JSON document instead. Log output is suppressed in
JSON mode so that standard output stays a valid document; pass `-e` or `-X` to re-enable it.

### Context lines

The `-C N` / `--context N` option controls how many source lines are shown before and after each
problem location in text output (like `grep -C`). The default is `2`; pass `0` to suppress the
context entirely. Has no effect in JSON mode, which always reports raw line and column numbers.

### Local repository options

These options are only meaningful in effective mode, which resolves parents and BOM imports:

| Option                    | Description                                                                                       |
|:--------------------------|:--------------------------------------------------------------------------------------------------|
| `--local-repository <path>` | Resolve into this directory instead of the configured local repository.                       |
| `--temp-local-repository` | Resolve into a temporary directory that is deleted when the run ends, leaving the configured local repository untouched. |

`--temp-local-repository` combined with `-o` (offline) proves that the named POMs are fully
self-contained: if any parent or BOM is missing the run fails immediately.

`maven.repo.local` works as it does for a regular Maven build when neither option is given.

Note: `-s`, `-ps` and `-is` (settings file redirects) are not supported, as the settings file
is read during bootstrap before those flags can take effect.

## Exit codes

| Code | Meaning                                           |
|:-----|:--------------------------------------------------|
| `0`  | No problems reported                              |
| `1`  | At least one error was reported                   |
| `2`  | Bad usage (unknown option, invalid argument, etc.)|
| `3`  | Interrupted (Ctrl+C)                              |
| `4`  | Only warnings were reported (no errors)           |

## Integration

`mvnval` is also reachable as `mvn --val` and from inside [`mvnsh`](./mvnsh.html).

## See also

- [MNG-8650](https://issues.apache.org/jira/browse/MNG-8650) — original issue
- [Maven Shell](./mvnsh.html)
- [Maven Upgrade Tool](./mvnup.html)
