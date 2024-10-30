---
title: Central Index
author: 
  - Hervé Boutemy
date: 2017-04-23
---

<!-- Licensed to the Apache Software Foundation (ASF) under one-->
<!-- or more contributor license agreements.  See the NOTICE file-->
<!-- distributed with this work for additional information-->
<!-- regarding copyright ownership.  The ASF licenses this file-->
<!-- to you under the Apache License, Version 2.0 (the-->
<!-- "License"); you may not use this file except in compliance-->
<!-- with the License.  You may obtain a copy of the License at-->
<!---->
<!--   http://www.apache.org/licenses/LICENSE-2.0-->
<!---->
<!-- Unless required by applicable law or agreed to in writing,-->
<!-- software distributed under the License is distributed on an-->
<!-- "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY-->
<!-- KIND, either express or implied.  See the License for the-->
<!-- specific language governing permissions and limitations-->
<!-- under the License.-->

# Central Index

Central repository provides [an index in `/.index`](https://repo\.maven\.apache\.org/maven2/\.index/):

- full index \(`nexus-maven-repository-index.gz`\) is updated weekly,
- incremental index \(`nexus-maven-repository-index.<n>.gz` \+ `nexus-maven-repository-index.properties`\)\.

This index is build using [Maven Indexer](/maven\-indexer/): see [indexer\-core documentation](/maven\-indexer/indexer\-core/) for more details on the fields that are available\.

You can use [Maven Indexer API](/maven\-indexer/indexer\-core/apidocs/) \(see [examples](/maven\-indexer/indexer\-examples/)\) to use this index with a dedicated API, or use [Apache Lucene](https://lucene\.apache\.org/) indexes browsers like [Luke](https://github\.com/DmitryKey/luke) or [Marple](https://github\.com/flaxsearch/marple) after unpacking the index \(see [`-u` CLI option](/maven\-indexer/indexer\-cli/)\)\.

## Digging Into Central Index with Luke

- download [the Central index: `nexus-maven-repository-index.gz`](https://repo\.maven\.apache\.org/maven2/\.index/)
- download [Maven Indexer CLI](https://repo\.maven\.apache\.org/maven2/org/apache/maven/indexer/indexer\-cli/5\.1\.1/indexer\-cli\-5\.1\.1\.jar) and [unpack](/maven\-indexer\-archives/maven\-indexer\-LATEST/indexer\-cli/) the index to raw Lucene index directory:

    ```
    java -jar indexer-cli-5.1.1.jar --unpack nexus-maven-repository-index.gz --destination central-lucene-index --type full
    ```

- download and extract [Luke binary tarball](https://github\.com/DmitryKey/luke/releases/download/luke\-4\.10\.4/luke\-with\-deps\.tar\.gz) and launch it on the Central index with Lucene format:

    ```
    luke.sh -ro -index central-lucene-index
    ```

    You need an old Luke version using an old Lucene version, since Maven Indexer 5\.5\.1 uses Lucene 3\.6\.2: for this tutorial, we chose Luke version 4\.10\.4, but you may choose another version\.

    But with more recent Maven Indexer releases, more recent Lucene version have been used: see \[Maven Indexer dependencies\]\(/maven\-indexer/indexer\-core/dependencies\.html\)\.

