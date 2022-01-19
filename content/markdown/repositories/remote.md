# Maven Remote Repositories

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

Remote repositories usually refer to repositories like [Maven Central Repository](/repository/index.html). These
repositories are holding the artifacts to be consumed by Maven builds.

The two most important properties of remote repositories are:
* baseURL
* repository policy

In essence, Maven using [Layout](layout.md) produces relative paths for [Artifacts](artifacts.md), that in turn are
resolved against baseURL of Remote Repositories ending in absolute URLs. The policy decides should a release or snapshot
artifact looked for in given repository.

## The `baseVersion` Artifact Property

In case of snapshot artifacts (hence Snapshot Remote Repositories implied) we already hinted that some
sort of "version transformation" happens. We can distinguish two cases:

### Deploying Snapshot Artifact

Your POM locally usually contains snapshot versions in a form of a string that ends with "SNAPSHOT" constant string 
(for example "1.0-SNAPSHOT"). But, in case of deploy, this version is being transformed to a "timestamped snapshot"
on the fly (by Maven) and when you check the deployed result, you will see that artifact file does not end up with 
"SNAPSHOT" anymore, but a timestamp and build number. Also, during deploy, Maven will deploy required Maven V Level Metadata
as well that will describe for consumers of this snapshot how to "reverse" this process.

So, in case of snapshot deploy, version transformation happens in form of:
```
1.0-SNAPSHOT -> Maven (on the fly) -> 1.0-${YYYYMMDD.HHMMSS}-${counter}
```
Where the date in `Etc/UTC` timezone (used in `YYYYMMDD.HHMMSS` format) is constant across deploy from same session (is time when Maven 
Session was created), and counter is increased counter from previously deployed metadata (or if no 
remote metadata exists, is initialized with 1).

### Consuming Snapshot Artifacts

If your project depends on SNAPSHOT dependencies, the POM of your project usually contains `version` value that ends
with "SNAPSHOT" (for example "1.0-SNAPSHOT"), still, as we see above, remote repositories do NOT contain such versions, 
but only timestamped ones.  Maven snapshots are "moving targets", hence, during resolution Maven 
will use the deployed metadata first, to figure out `baseVersion` -> `version` (timestamped) mapping, and only then 
will fetch the required files.

In case of consuming snapshot, the following transformation happens:
```
1.0-SNAPSHOT -> Remote V Level Repository Metadata -> 1.0-YYYYMMDD.HHMMS-X
```

Due this indirection (the real filename of artifact, the version part) is figured from deployed maven repository metadata,
the snapshot artifacts are "moving target": each snapshot deploy as above will deploy new and new metadata, hence, will
alter this transformation here (Maven will download different and different snapshot artifact).

Note: it is possible to "lock down" snapshot artifacts, by using timestamped version in version field of a dependency,
and it will ensure Maven downloads always same artifact (is not "moving target" anymore), but **Maven will still consider
that dependency as snapshot**, and all the fine print applies (for example, release plugin will refuse to release such
a project).
