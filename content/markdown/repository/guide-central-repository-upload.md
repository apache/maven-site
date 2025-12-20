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

# Guide to uploading artifacts to the Central Repository

In order for Maven users to depend on your project, you must deploy the artifact, the POM, and their PGP signatures to a remote repository. The most common public shared repository is [Maven Central](/repository/).

# Requirements

1. **releases**: Only _releases_ can be uploaded to the Central Repository. A release can only depend on other files already released and available in the repository. The Central Repository will not change or replace a release after it is published.
2. **javadoc and sources** for IDE lookup
3. **PGP signature**
4. **minimum POM information**: Maven Central requires that a POM contain certain minimal information before it will publish a release. See ["Why do we have Requirements"](https://central.sonatype.org/pages/requirements.html)
5. **coordinates**: Picking the appropriate coordinates for your project is important. See [the guidelines](https://central.sonatype.org/pages/choosing-your-coordinates.html), particularly the details about group ID and domain ownership.

**[ The updated list of requirements](https://central.sonatype.org/publish/requirements/)** can be found at Sonatype.

## A basic sample:

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.apache.maven</groupId>
  <artifactId>maven</artifactId>
  <version>2.0</version>
  <packaging>jar</packaging>

  <name>Maven core</name>
  <description>The maven main core project description</description>
  <url>https://maven.apache.org</url>

  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>

  <scm>
    <url>https://svn.apache.org/viewvc/maven</url>
  </scm>

  <dependencies>
    <dependency>
      <groupId>...</groupId>
      <artifactId>...</artifactId>
      <version>...</version>
    </dependency>
    ...
  </dependencies>

  <!--
  NOT RECOMMENDED: (see FAQ)
  <repositories></repositories>
  <pluginRepositories></pluginRepositories>
  -->
</project>

```

## PGP Signature

When people download artifacts from the Central Repository, they should verify these artifacts' PGP signatures against a public key server. If there are no signatures, then users have no guarantee that they are downloading the original artifact.

The Central Repository requires PGP signatures for all artifacts (all files except checksums) with a public key available from a key server like [https://pgp.mit.edu](https://pgp.mit.edu). Read [Working with PGP Signatures](https://central.sonatype.org/publish/requirements/gpg/) for more information.

## FAQ and common mistakes

- I have other `repositories` or `pluginRepositories` listed in my POM. Is that a problem?

  At present, this won't preclude your project from being included, but we do strongly encourage making sure all your dependencies are included in the Central Repository. If you rely on sketchy repositories that have junk in them or disappear, it creates havok for downstream users. Try to keep your dependencies among reliable repos like Central, JBoss, etc.

- What about artifacts that can't be distributed because of their license?

  In that case only the POM for that dependency is required, listing where the dependency can be downloaded from. [See an example](https://repo.maven.apache.org/maven2/javax/activation/activation/1.0.2/activation-1.0.2.pom).

- I have a patched version of the foo project developed at foo.com, what `groupId` should I use?

  When you modify a third party project, that patched version becomes your project and therefore should be only be publicly distributed under a `groupId` you control, never under `com.foo`. (If you're only distributing to a private repository inside your organization, do whatevcer's convenient.) When changing the group ID, you should also change the Java package to avoid classpath conflicts and split package issues. See [JLBP 6](https://jlbp.dev/JLBP-6).

- My project is hosted at a project hosting service like SourceForge or Github. What should I use as groupId?

  If your project name is `foo` at SourceForge, you can use `net.sf.foo`. If your username is `bar` on Github, you can use `com.github.bar`. You can also use another reversed domain name you control. The group ID does not have to reflect the project host.

# Publishing your artifacts to the Central Repository

## Approved Repository Hosting

We encourage projects to use an approved repository hosting location.

Currently approved repository hosting locations:

- [Apache Software Foundation](https://repository.apache.org/) (for all Apache projects)

## Other Projects

The easiest way to upload another project is to use the [Sonatype Central Portal](https://central.sonatype.org/register/central-portal/), which is an approved repository provided by Sonatype for _any_ OSS Project that wants to get its artifacts into the Central Repository.

