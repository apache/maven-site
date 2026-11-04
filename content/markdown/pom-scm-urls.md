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

# SCM URLs

The `<connection>`, `<developerConnection>` and `<url>` elements of the [`<scm>` section of the POM](./pom.html#SCM) tell Maven and its plugins where a project's sources live.
The connection elements hold an SCM URL, which [Maven SCM](/scm/) parses to find the provider that talks to the version control system.
This page describes the URL syntax and the SCM URL each of the standard providers expects.
The provider's own configuration is described on the [Maven SCM site](/scm/).

<!--MACRO{toc|fromDepth=2|toDepth=3}-->

## SCM URL format

The general format of an SCM URL is

```
scm:<provider id><delimiter><provider-specific part>
```

The delimiter is either a colon (`:`) or a pipe (`|`).
Use the pipe if one of the variables contains a colon, for example a Windows path.

Provider IDs may be remapped with the plugin parameter `providerImplementations`, supported by both [maven-release-plugin](/maven-release/maven-release-plugin/prepare-mojo.html#providerImplementations) and [maven-scm-plugin](/scm/maven-scm-plugin/checkout-mojo.html#providerImplementations).

## Supported SCMs

The following providers ship with Maven SCM.

| SCM | Provider ID | Provider module | Native Java |
|:---:|:---:|:---:|:---:|
| [Git](#Git) | `git` | [Git Executable Provider](/scm/maven-scm-providers/maven-scm-providers-git/maven-scm-provider-gitexe/index.html) | no |
| [Git](#Git) | `jgit` | [JGit Provider](/scm/maven-scm-providers/maven-scm-providers-git/maven-scm-provider-jgit/index.html) | yes |
| [Subversion](#Subversion) | `svn` | [SVN Executable Provider](/scm/maven-scm-providers/maven-scm-providers-svn/maven-scm-provider-svnexe/index.html) | no |
| [Mercurial](#Mercurial) | `hg` | [Mercurial (Hg) Provider](/scm/maven-scm-providers/maven-scm-provider-hg/index.html) | no |
| [Local](#Local_file_system) | `local` | [Local Provider](/scm/maven-scm-providers/maven-scm-provider-local/index.html) | yes |

A third-party provider, [maven-scm-provider-svnjava](https://github.com/olamy/maven-scm-provider-svnjava), is a native Java Subversion provider based on SVNKit.

See also the [comparison of revision control software](https://en.wikipedia.org/wiki/Comparison_of_revision_control_software) and the [common vocabulary of source code management](https://en.wikipedia.org/wiki/Source_Code_Management#Common_vocabulary).

The examples below use a colon as the delimiter.

## Git

The Git providers (`git` and `jgit`) accept the following URLs. The separator for the port is always a colon, because that part is specified in the [Git URL specification](https://git-scm.com/docs/git-clone#_git_urls).

```
scm:git:git://server_name[:port]/path_to_repository
scm:git:http://server_name[:port]/path_to_repository
scm:git:https://server_name[:port]/path_to_repository
scm:git:ssh://server_name[:port]/path_to_repository
scm:git:file://[hostname]/path_to_repository
```

The SSH protocol also has an SCP-like syntax in which the protocol is left out:

```
scm:git:[<user>@]<host>:/<path-to-git-repo>
```

GitHub proposes this form by default, without the leading slash:

```
scm:git:[<user>@]<host>:<path-to-git-repo>
```

For example:

```
scm:git:git://github.com/path_to_repository
scm:git:http://github.com/path_to_repository
scm:git:https://github.com/path_to_repository
scm:git:git@github.com:path_to_repository
scm:git:ssh://github.com/path_to_repository
scm:git:file://localhost/path_to_repository
```

### Different fetch and push URLs

Sometimes read and write operations need different URLs, for example when fetching works over HTTP but pushing requires SSH.
In that case, write both URLs into the `<developerConnection>` element, with the fetch URL prefixed by `[fetch=]` and the push URL prefixed by `[push=]`:

```xml
<developerConnection>scm:git:[fetch=]http://mywebserver.org/path_to_repository[push=]ssh://username@otherserver:8898/~/repopath.git</developerConnection>
```

Further Git provider behavior and its configuration are described on the [Maven SCM Git page](/scm/git.html).

## Subversion

The `svn` provider accepts the following URLs:

```
scm:svn:svn://[username[:password]@]server_name[:port]/path_to_repository
scm:svn:svn+ssh://[username@]server_name[:port]/path_to_repository
scm:svn:file://[hostname]/path_to_repository
scm:svn:http://[username[:password]@]server_name[:port]/path_to_repository
scm:svn:https://[username[:password]@]server_name[:port]/path_to_repository
```

For example:

```
scm:svn:file:///svn/root/module
scm:svn:file://localhost/path_to_repository
scm:svn:file://my_server/path_to_repository
scm:svn:http://svn.apache.org/svn/root/module
scm:svn:https://username@svn.apache.org/svn/root/module
scm:svn:https://username:password@svn.apache.org/svn/root/module
```

The provider configuration is described on the [Maven SCM Subversion page](/scm/subversion.html).

## Mercurial

The `hg` provider takes the path or URL of the branch location. The supported protocols are HTTP, HTTPS and FILE, as well as a local path.

```
scm:hg:url_to_repository/local_repository_directory
```

For example:

```
scm:hg:http://host/v3
scm:hg:file://C:/dev/project/v3 (windows drive)
scm:hg:file:///home/smorgrav/dev/project/v3 (linux drive)
scm:hg:/home/smorgrav/dev/project/v3 (local directory)
```

## Local file system

The `local` provider reads from a directory and has no server. Its URL has this form:

```
scm:local<delimiter>path_to_repository<delimiter>module_name
```

* `path_to_repository`: the absolute or relative path to the parent directory of your `pom.xml`.
* `module_name`: the name of the directory that contains your `pom.xml`.

For example:

```
scm:local:/usr/modules:my_module
scm:local|C:/javaprojects|my_module
```
