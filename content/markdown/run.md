# Running Apache Maven

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

The syntax for running Maven is as follows:

        mvn [options] [<goal(s)>] [<phase(s)>]

All available options are documented in the built-in help that you can access with

        mvn -h

The typical invocation for building a Maven project uses a Maven lifecycle phase. E.g.

        mvn verify

The built-in lifecycles and their most used phases, in order, are:

* clean - `clean`

* default - `validate`, `compile`, `test`, `package`, `verify`, `install`, `deploy`

* site - `site`, `site-deploy`

A fresh build of a project generating all packaged outputs and the documentation site
and deploying it to a repository manager could be done with

        mvn clean deploy site-deploy

Just creating the package and installing it in the local repository for re-use from other projects can be done with

        mvn install

And if you don't expect to re-use from other projects, use

        mvn verify

This is the most common build invocation for a Maven project.

When not working with a project, and in some other use cases, you might want to invoke
a specific task implemented by a part of Maven - this is called a **goal** of a plugin.
E.g.:

        mvn archetype:generate

or

        mvn checkstyle:check

There are many different [plugins available](/plugins/) and they all implement different goals.

Further resources:

* [Building a Project with Maven](./run-maven/index.html)

