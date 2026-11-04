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

# Guide to Selecting Alternative Wagon Providers

<a id="Command-Line_Configuration"></a>
<a id="settings.xml_Configuration"></a>

Maven 2.2.1 let you choose between several Wagon implementations for one protocol, with the `maven.wagon.provider.<protocol>` property or a `<wagonProvider>` element in a `<server>` entry of `settings.xml`. Maven 3 and Maven 4 no longer support this: they look up the Wagon by the protocol alone, and they remove `<wagonProvider>` from the server configuration before passing it on.

<a id="Available_Wagon_Providers"></a>

Maven 3 and Maven 4 bundle two Wagons: `wagon-http`, based on Apache HttpClient, for `http` and `https`, and `wagon-file` for `file`. Since Maven 3.9.0, Maven by default uses the native transports of Maven Resolver for these protocols, and Wagon only for the protocols they don't cover or when you select it with `-Dmaven.resolver.transport=wagon`. For details, see the [Guide for Resolver Transport](./guide-resolver-transport.html).

Wagons for other protocols, such as `wagon-ftp` or `wagon-ssh-external`, are added to the build as build extensions; see [Deploying over HTTP, FTP and SSH](./guide-deployment-transports.html). For the configuration that `wagon-http` reads, see [Advanced configuration to HttpClient HTTP Wagon](./guide-http-settings.html).
