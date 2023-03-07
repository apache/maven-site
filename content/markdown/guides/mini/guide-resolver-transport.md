## Guide for Resolver Transport
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

Starting with Maven 3.9.0 release, the "default transport" (the default transport used by Maven Resolver)
changed from ancient Wagon to modern resolver "native HTTP" transport.

## Switching between transports

The transport used by resolver can be controlled using `-Dmaven.resolver.transport` user property, where accepted
values are `native` (the default), `wagon` (falls back to legacy Wagon) and `auto` (delegates to resolver to sort
out defaults).

TBD