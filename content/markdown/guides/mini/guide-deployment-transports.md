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

# Deploying over HTTP, FTP and SSH

This guide shows how to point a project's `distributionManagement` at a repository that uses a specific transport, and how to give Maven the credentials. For the settings that apply to every transport, see [Security and Deployment Settings](./guide-deployment-security-settings.html). For the plugin that performs the upload, see the [Maven Deploy Plugin](/plugins/maven-deploy-plugin/).

In all cases the `id` of the `distributionManagement` repository must match the `id` of a `server` element in `settings.xml`, which holds the credentials.

<!-- MACRO{toc} -->

## HTTP(S)

Specify the HTTP(S) server in the [`distributionManagement` element of your POM](../../pom.html#Distribution_Management):

```xml
<project>
  ...
  <distributionManagement>
    <repository>
      <id>my-mrm-releases</id>
      <url>https://repo.example.com/repository/releases</url>
    </repository>
  </distributionManagement>
  ...
</project>
```

The `settings.xml` needs a `server` with the same `id`, holding the credentials in [encrypted form](./guide-encryption.html):

```xml
<settings>
  ...
  <servers>
    <server>
      <id>my-mrm-releases</id>
      <username>{encrypted-user}</username>
      <password>{encrypted-password}</password>
    </server>
  </servers>
  ...
</settings>
```

Deployment sends one HTTP `PUT` request per artifact, checksum and metadata file, with no query parameters, optionally using HTTP Basic Authentication. Make sure your [repository manager](/repository-management.html) supports this method of deployment, otherwise the Deploy Plugin cannot be used.

The [Sonatype Central Portal](/repository/guide-central-repository-upload.html) supports this only through the legacy [Portal OSSRH Staging API](https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/) for release versions. [SNAPSHOT deployments](https://central.sonatype.org/publish/publish-portal-snapshots/) to the Central Portal are still supported by the Deploy Plugin.

The HTTP client depends on the Maven version, because deployment uses the [Maven Resolver Transport](./guide-resolver-transport.html). You can override the default with the user property `maven.resolver.transport`. Maven Resolver also evaluates its other configuration properties ([Resolver 2.x](/resolver/configuration.html) for Maven 3.10 and 4, [Resolver 1.x](/resolver-archives/resolver-LATEST-1.x/configuration.html) for Maven 3.9) when set as user properties; the defaults work in most cases. If you select the Wagon transport, see [Advanced configuration to HttpClient HTTP Wagon](./guide-http-settings.html).

## FTP

To deploy over FTP, specify the FTP server in `distributionManagement` and add the FTP wagon as a build `extension`:

```xml
<project>
  ...
  <distributionManagement>
    <repository>
      <id>ftp-repository</id>
      <url>ftp://repository.mycompany.com/repository</url>
    </repository>
  </distributionManagement>

  <build>
    <extensions>
      <!-- Enabling the use of FTP -->
      <extension>
        <groupId>org.apache.maven.wagon</groupId>
        <artifactId>wagon-ftp</artifactId>
        <version>3.5.3</version>
      </extension>
    </extensions>
  </build>
  ...
</project>
```

The `settings.xml` has a `server` whose `id` matches the repository:

```xml
<settings>
  ...
  <servers>
    <server>
      <id>ftp-repository</id>
      <username>user</username>
      <password>pass</password>
    </server>
  </servers>
  ...
</settings>
```

Make sure you can log in to the FTP server by hand before you deploy with `mvn deploy`.

## SSH with external executables

The `scpexe` protocol runs the `ssh` and `scp` programs of your system, so it uses your existing SSH configuration. Specify the server and add the `wagon-ssh-external` extension:

```xml
<project>
  ...
  <distributionManagement>
    <repository>
      <id>ssh-repository</id>
      <url>scpexe://repository.mycompany.com/repository</url>
    </repository>
  </distributionManagement>

  <build>
    <extensions>
      <!-- Enabling the use of SSH -->
      <extension>
        <groupId>org.apache.maven.wagon</groupId>
        <artifactId>wagon-ssh-external</artifactId>
        <version>3.5.3</version>
      </extension>
    </extensions>
  </build>
  ...
</project>
```

On Unix, or with Cygwin installed, no further configuration is needed, because everything comes from the environment. On Windows with a tool such as `plink`, configure the executables in `settings.xml`:

```xml
<settings>
  ...
  <servers>
    <server>
      <id>ssh-repository</id>
      <username>your username in the remote system if different from local</username>
      <privateKey>/path/to/your/private/key</privateKey> <!-- not needed if using pageant -->
      <configuration>
        <sshExecutable>plink</sshExecutable>
        <scpExecutable>pscp</scpExecutable>
        <sshArgs>other arguments you may need</sshArgs>
      </configuration>
    </server>
  </servers>
  ...
</settings>
```

PuTTY expects the private key in `PPK` format. Convert an OpenSSH key with `puttygen`, or generate a new one; the tools are on the [PuTTY download page](https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html).

Make sure you can log in to the server by hand before you deploy. If the deployment fails with permission problems, set the file and directory permissions on the `server`:

```xml
<server>
  <id>ssh-repository</id>
  <filePermissions>664</filePermissions>
  <directoryPermissions>775</directoryPermissions>
  <configuration>
    <sshExecutable>plink</sshExecutable>
    <scpExecutable>pscp</scpExecutable>
  </configuration>
</server>
```

## Deploying over an unreliable network

When the connection between the build machine and the remote repository is poor, improving the network is the best fix, but not always possible. Two workarounds exist.

### Retrying

The Deploy Plugin can retry a failed deployment with the [`retryFailedDeploymentCount`](/plugins/maven-deploy-plugin/deploy-mojo.html#retryFailedDeploymentCount) parameter before it reports a failure.

### Deploying to a local staging directory

When the network is really inconsistent, deploy in two steps: first to a local directory during the build, then from that directory to the remote repository, retrying as often as needed.

The first step needs no POM change when you use the [`altDeploymentRepository`](/plugins/maven-deploy-plugin/deploy-mojo.html#altDeploymentRepository) parameter:

```
mvn deploy -DaltDeploymentRepository=local::file:./target/staging-deploy
```

For the second step, the [Wagon Maven Plugin](https://www.mojohaus.org/wagon-maven-plugin/) copies between repositories. Its [`merge-maven-repos`](https://www.mojohaus.org/wagon-maven-plugin/merge-maven-repos-mojo.html) goal merges repository metadata, and its [`upload`](https://www.mojohaus.org/wagon-maven-plugin/upload-mojo.html) goal does not; use `upload` when the target is empty, such as a staging repository of a repository manager.

```
mvn org.codehaus.mojo:wagon-maven-plugin:3.1.0:merge-maven-repos \
  -Dwagon.source=file:./target/staging-deploy \
  -Dwagon.target=https://... \
  -Dwagon.targetId=id
```

Or configure it in the POM and run `mvn wagon:merge-maven-repos`:

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>wagon-maven-plugin</artifactId>
        <version>3.1.0</version>
        <configuration>
          <source>file:./target/staging-deploy</source>
          <target>${project.distributionManagement.repository.url}</target>
          <targetId>${project.distributionManagement.repository.id}</targetId>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ...
</project>
```
