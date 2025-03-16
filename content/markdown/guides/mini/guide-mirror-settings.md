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

# Using Mirrors for Repositories

With [Repositories](/guides/introduction/introduction-to-repositories.html) you specify from which locations you want to _download_ certain artifacts, such as dependencies and maven-plugins. Repositories can be [declared inside a project](../mini/guide-multiple-repositories.html), which means that if you have your own custom repositories, those sharing your project easily get the right settings out of the box. However, you may want to use an alternative mirror for a particular repository without changing the project files.

Some reasons to use a mirror are:

- There is a synchronized mirror on the internet that is geographically closer and faster
- You want to replace a particular repository with your own internal repository which you have greater control over
- You want to run a [repository manager](../../repository-management.html) to provide a local cache to a mirror and need to use its URL instead

To configure a mirror of a given repository, you provide it in your settings file \(`${user.home}/.m2/settings.xml`\), giving the new repository its own `id` and `url`, and specify the `mirrorOf` setting that is the ID of the repository you are using a mirror of. For example, the ID of the main Maven Central repository included by default is `central`, so to use the different mirror instance, you would configure the following:

```unknown
<settings>
  ...
  <mirrors>
    <mirror>
      <id>other-mirror</id>
      <name>Other Mirror Repository</name>
      <url>https://other-mirror.repo.other-company.com/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
  ...
</settings>
```

Note that there can be at most one mirror for a given repository. In other words, you cannot map a single repository to a group of mirrors that all define the same `<mirrorOf>` value. Maven will not aggregate the mirrors but simply picks the first match. If you want to provide a combined view of several repositories, use a [repository manager](../../repository-management.html) instead.

The settings descriptor documentation can be found on the [Maven Local Settings Model Website](../../maven-settings/settings.html).

**Note**: The official Maven repository is at `https://repo.maven.apache.org/maven2` hosted by the Sonatype Company and is distributed worldwide via CDN.

A list of known mirrors is available in the [Repository Metadata](https://repo.maven.apache.org/maven2/.meta/repository-metadata.xml). These mirrors may not have the same contents and we don&apos;t support them in any way.

# Using A Single Repository

You can force Maven to use a single repository by having it mirror all repository requests. The repository must contain all of the desired artifacts, or be able to proxy the requests to other repositories. This setting is most useful when using an internal company repository with a [Maven Repository Manager](../../repository-management.html) to proxy external requests.

To achieve this, set `mirrorOf` to `*`.

**Note:** This feature is only available in Maven 2\.0\.5+.

```unknown
<settings>
  ...
  <mirrors>
    <mirror>
      <id>internal-repository</id>
      <name>Maven Repository Manager running on repo.mycompany.com</name>
      <url>http://repo.mycompany.com/proxy</url>
      <mirrorOf>*</mirrorOf>
    </mirror>
  </mirrors>
  ...
</settings>
```

# Advanced Mirror Specification

A single mirror can handle multiple repositories. This is typically used in conjunction with a repository manager, that gives easy centralised configuration of the list of repositories behind.

The syntax:

- `*` matches all repo ids.
- `external:*` matches all repositories except those using localhost or file based repositories. This is used when you want to exclude redirecting repositories that are defined for Integration Testing.
- since Maven 3\.8\.0, `external:http:*` matches all repositories using HTTP except those using localhost.
- multiple repositories may be specified using a comma as the delimiter
- an exclamation mark may be used in conjunction with one of the above wildcards to exclude a repository id

Be careful not to include extra whitespace around identifiers or wildcards in comma separated lists. For example, a mirror with `<mirrorOf`&gt; set to `!repo1, *` will not mirror anything while `!repo1,*` will mirror everything but `repo1`.

The position of wildcards within a comma separated list of repository identifiers is not important as the wildcards defer to further processing and explicit includes or excludes stop the processing, overruling any wildcard match.

When you use the advanced syntax and configure multiple mirrors, the declaration order matters. When Maven looks for a mirror of some repository, it first checks for a mirror whose `<mirrorOf>` exactly matches the repository identifier. If no direct match is found, Maven picks the first mirror declaration that matches according to the rules above \(if any\). Hence, you may influence match order by changing the order of the definitions in the `settings.xml`

Examples:

- `*` = everything
- `external:*` = everything not on the localhost and not file based.
- `repo,repo1` = repo or repo1
- `*,!repo1` = everything except repo1

```unknown
<settings>
  ...
  <mirrors>
    <mirror>
      <id>internal-repository</id>
      <name>Maven Repository Manager running on repo.mycompany.com</name>
      <url>http://repo.mycompany.com/proxy</url>
      <mirrorOf>external:*,!foo</mirrorOf>
    </mirror>
    <mirror>
      <id>foo-repository</id>
      <name>Foo</name>
      <url>http://repo.mycompany.com/foo</url>
      <mirrorOf>foo</mirrorOf>
    </mirror>
  </mirrors>
  ...
</settings>
```

# Creating Your Own Mirror

The size of the central repository is [increasing steadily](https://search.maven.org/stats) To save us bandwidth and you time, mirroring the entire central repository is not allowed. \(Doing so will get you automatically banned.\) Instead, we suggest you setup a [repository manager](../../repository-management.html) as a proxy.

