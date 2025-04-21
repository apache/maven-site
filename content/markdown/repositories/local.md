# Maven Local Repositories

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

The local repository is a mixed bag, in sense, that it serves two purposes: it caches downloaded artifacts from
remote repositories along with locally built and installed ones.

While the local repository does reside on local filesystem, users should **never** reach for its contents directly
using plain file operations, but use the provided API instead. Reason for this strict expectation is that even
today different implementations of local repository exists. Hence, "reverse engineering" the layout and direct
access to locally cached or installed files may not only break in the future, but may also circumvent important
aspects like locking and synchronization, and so forth. Latest resolver even implements "split" local repository,
where user may configure local repository to split the installed and cached artifacts, hence to make them physically
split from each other.

All these underlying changes remain hidden from code using local repository API, and hence, the code will
be more robust and time proof.

## The `baseVersion` Artifact Property

As noted in [Layout](layout.md) page, locally built and installed (implies "to local repository") artifacts will use
`baseVersion` while calculating layout. Hence, in this case both, `baseVersion` and `version` of them will both
contain same value, the one ending with "SNAPSHOT" constant string, no transformation is applied to file name.

Snapshots pulled from remote and cached in local repository will have timestamped `version` property instead, hence
they will have the full-blown timestamped version applied to file name.
