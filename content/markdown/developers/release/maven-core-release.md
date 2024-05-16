# Releasing Maven
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
Maven differs slightly in its release process due to several extra steps.

The goal is to commit candidate release to svn `dev` tree <https://dist.apache.org/repos/dist/dev/maven/maven-3>/`$VERSION`. Once the vote has passed, svn move to `release` <https://dist.apache.org/repos/dist/release/maven/maven-3>/`$VERSION`.

The tree directory is:

```
https://dist.apache.org/repos/dist/dev/maven/maven-3/
  `-- $VERSION/
      |-- binaries/
      |   |-- apache-maven-$VERSION-bin.tar.gz
      |   |-- apache-maven-$VERSION-bin.tar.gz.asc
      |   |-- apache-maven-$VERSION-bin.tar.gz.sha512
      |   |-- apache-maven-$VERSION-bin.zip
      |   |-- apache-maven-$VERSION-bin.zip.asc
      |   `-- apache-maven-$VERSION-bin.zip.sha512
      `-- source/ (notice: singular...)
          |-- apache-maven-$VERSION-src.tar.gz
          |-- apache-maven-$VERSION-src.tar.gz.asc
          |-- apache-maven-$VERSION-src.tar.gz.sha512
          |-- apache-maven-$VERSION-src.zip
          |-- apache-maven-$VERSION-src.zip.asc
          `-- apache-maven-$VERSION-src.zip.sha512
```

Note that the policy for failed releases is to move forward with a new
version. The tag that produced the failed released is left in place for
posterity. So if the release of, say, 3.2.4 fails then we move forward
with 3.2.5.

## Produce Release Candidates

For non-alpha/beta releases, release candidates are produced before the actual release.

Checkout https://dist.apache.org/repos/dist/dev/maven/maven-3 then create the necessary directory tree.

Copy the binaries and src-tar.gz with their sha512/asc to the created directories.

To produce a release candidate, follow the first seven steps only from the following procedure:

-   [Maven Project Common Release Procedure](./maven-project-release-procedure.html)

The version used should be the eventual version with -RC1, -RC2, etc. appended.

After producing the RC, request that the developers test the release on the list. If a regression is found, a new release candidate is rolled.

After a reasonable time without regressions found, a wider audience may be polled if the release manager desires (for example, users@).

Once happy with a release candidate, the full release is performed, with the final version in place.

## Produce the Release

To produce a final release, the same process as for standard projects is followed:

-   [Maven Project Common Release Procedure](./maven-project-release-procedure.html)

Below describes the additional steps that need to be taken at the points where the website are updated in those instructions.

### Prepare the Release Notes

Checkout Maven site source <https://github.com/apache/maven-site.git>.

Create the release notes:

- create `content/markdown/docs/$version`
- create `content/markdown/docs/$version/release-notes.md` (see other versions for an example)

### Stage the Latest Documentation

Once the release is prepared, but before the release vote, the site needs to be staged.

From the release checkout, stage the site:

```
mvn -Preporting site site:stage
mvn scm-publish:publish-scm
```

This will publish the Maven core site in [/ref/3-LATEST](/ref/3-LATEST).

## Complete the release

After a successful vote you should do the following steps to finish the release.

### Add New Version to ASF Distribution Directory

In addition to promoting the repository, the release archives should be
moved to the release svnpubsub tree:

```
svn mv https://dist.apache.org/repos/dist/dev/maven/maven-3/$VERSION https://dist.apache.org/repos/dist/release/maven/maven-3 -m "Publish Maven $VERSION Distribution Archives"
```

### Update the DOAP Information

Edit <https://github.com/apache/maven/blob/master/doap_Maven.rdf> to list the new release.

### Update the Web Site

Checkout Maven site source <https://github.com/apache/maven-site.git>.

For 3.x: update the `versions3x`, `currentStableVersion` and `currentStableVersionDetails` properties in `pom.xml`

Next, update release history `content/markdown/docs/history.md.vm`.

Only deploy the site once the release is present on the mirrors, and the reference documentation has been deployed to [/ref/](/ref).

### Deploy the Latest Documentation to Target Versioned Location

The reference documentation for Maven core source code references and API docs has been staged in a previous step: now it needs to be deployed to its dedicated directory before deploying the web site pointing to the new version.

This consists in copying in website svn tree the staging /ref/3-LATEST directory to final /ref/`$VERSION`.

```
svn cp https://svn.apache.org/repos/asf/maven/website/components/ref/3-LATEST https://svn.apache.org/repos/asf/maven/website/components/ref/$VERSION -m "Maven $VERSION released"
```

## Information on `/ref/current` mechanism

The redirection from `/ref/current` to actual Maven version reference is done through `.htaccess` published in site.

### Deploying the Release Website

Once both of the above have synced to the main site and a suitable number of mirrors, proceed to update the web site and produce the announcement.

Commit your changes and then [deploy the main Maven site](../website/deploy-maven-website.html).

### Remove Old Versions from ASF Distribution Directory

Next, any superseded releases should be removed from the above locations (after confirming that they exist in /www/archive.apache.org/dist/maven).

### Proceed with Announcement

You can now proceed with the steps outlined after deploying the website on [Maven Project Common Release Procedure](./maven-project-release-procedure.html)

