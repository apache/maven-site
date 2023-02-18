title: Developers centre - Committer Environment
author: Vincent Siveton
date: 2006-10-01

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

## Committer Environment

### Introduction

 This document describes how to set up the Maven committer environment.

### Source File Encoding

 When editing source files, make sure you use the right file encoding. For the Maven project, UTF-8 has been chosen as the default file encoding. UTF-8 is an encoding scheme for the Unicode character set that can encode all characters that Java can handle. The source files should not contain the byte order mark (BOM). There are exceptions to this general rule. For instance, properties files are encoded using ISO-8859-1 as per the JRE API, so please keep this in mind, too.

### Maven Code Style

 Refer to [Maven Code Style And Code Conventions](./conventions/code.html)

### Useful software

 The Maven Team uses assorted software. Here is a partial list:

- [Cygwin](https://www.cygwin.com/): collection of free software tools that allow various versions of Microsoft Windows to act somewhat like a Unix system

- [GnuPG](https://www.gnupg.org/): GNU Privacy Guard.
