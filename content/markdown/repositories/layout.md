# Maven Repository Layout

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

The layout is responsible to translate the artifact coordinates into generic URI (path, URL, it depends on context). 
The transformation rule is quite simple for that matter:

| Source coordinate | Trasformation | Result example |
|-------------------|---------------|----------------|
| Group ID          | Replace "." (dot) characters with "/" (dash) character | `org.apache.maven` -> `org/apache/maven` |
| Artifact ID       | none          | `apache-maven` -> `apache-maven` |
| Version           | none          | `3.8.4` -> `3.8.4` |


