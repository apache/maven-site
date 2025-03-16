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

# Release Notes &#x2013; Maven 4.0.0-rc-2

The Apache Maven team would like to announce the release of Maven 4.0.0-rc-2.

Maven 4 release **requires Java 17 for runtime**.

This is release candidate release, **is not suitable for production**.

Maven 4.0.0-rc-2 is [available for download][0].

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the web site: [https://maven.apache.org/][2]
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/4.0.0-rc-2/](/ref/4.0.0-rc-2/)

## Overview About the Changes

The full list of changes can be found in our [issue management system][4].

Notable changes include:
* **requires Java 17 as "minimum runtime Java requirement"**
* brings the latest Maven Resolver 2.0.5
* many bug fixes and enhancements since rc-1

Maven has entered the release candidate phase and aims to be finally released in a few weeks.  Please give it a try and report errors.

# Upgrading from Maven 3

Maven 4 brings a tons of changes.  We've tried hard to maximise compatibility with Maven 3.x, but in order to have your build work with Maven 4, you will need to upgrade some plugins (such as `maven-enforcer-plugin`, `maven-remote-resources-plugin`, `maven-shade-plugin`, etc...) to their most recent versions.

If your build is leveraging Maven extensions, you may very well expect some breakage. Some extensions may need to be updated:
* the useful, but unmaintained, [`os-maven-plugin`](https://github.com/trustin/os-maven-plugin/) extension has been forked and now maintained at [`os-detector-maven-plugin`](https://github.com/tisonkun/os-detector)

Contact the extensions' developers team to know their plans regarding supporting to Maven 4.

## Known issues

* the `mvnenc` tool used to encrypt passwords in settings is broken
* download rates appearing in the console display wrong values

# Change log

## Improvements

* \[[MNG-5729](https://issues.apache.org/jira/browse/MNG-5729)\] Use monotonic time measurements by @gnodet in [#1965](https://github.com/apache/maven/pull/1965)
* \[[MNG-8394](https://issues.apache.org/jira/browse/MNG-8394)\] Event bridge and properties fix by @cstamas in [#1937](https://github.com/apache/maven/pull/1937)
* \[[MNG-8403](https://issues.apache.org/jira/browse/MNG-8403)\] Maven ITs use maven-executor by @cstamas in [#1940](https://github.com/apache/maven/pull/1940)
* \[[MNG-8407](https://issues.apache.org/jira/browse/MNG-8407)\] Add target attribute to SVG links by @kwin in [#1954](https://github.com/apache/maven/pull/1954)
* \[[MNG-8415](https://issues.apache.org/jira/browse/MNG-8415)\] Add constant for the security settings xml file by @gnodet in [#1956](https://github.com/apache/maven/pull/1956)
* \[[MNG-8419](https://issues.apache.org/jira/browse/MNG-8419)\]\[[MNG-8424](https://issues.apache.org/jira/browse/MNG-8424)\] Too aggressive warning for pre-Maven4 passwords by @cstamas in [#1970](https://github.com/apache/maven/pull/1970)

## Bug fixes

* IT: Move ITs off maven-shared-utils by @cstamas in [#1941](https://github.com/apache/maven/pull/1941)
* \[[MNG-8389](https://issues.apache.org/jira/browse/MNG-8389)\] MavenExReq lacks u/p/i settings file paths by @cstamas in [#1939](https://github.com/apache/maven/pull/1939)
* \[[MNG-8391](https://issues.apache.org/jira/browse/MNG-8391)\] Wrong effective model when conflicting values come from parents and profiles by @gnodet in [#1942](https://github.com/apache/maven/pull/1942)
* \[[MNG-8396](https://issues.apache.org/jira/browse/MNG-8396)\] Add a cache layer to the filtered dependency graph by @gnodet in [#1944](https://github.com/apache/maven/pull/1944)
* \[[MNG-8400](https://issues.apache.org/jira/browse/MNG-8400)\] Make sure base parser uses canonical maven.home by @cstamas in [#1945](https://github.com/apache/maven/pull/1945)
* \[[MNG-8402](https://issues.apache.org/jira/browse/MNG-8402)\] System properties can take precedence over builtin expressions by @gnodet in [#1947](https://github.com/apache/maven/pull/1947)
* \[[MNG-8405](https://issues.apache.org/jira/browse/MNG-8405)\] Fail On Severity, when set, is not reset (in resident instances) by @gnodet in [#1950](https://github.com/apache/maven/pull/1950)
* \[[MNG-8403](https://issues.apache.org/jira/browse/MNG-8403)\] Collapse IT utils and helpers by @cstamas in [#1949](https://github.com/apache/maven/pull/1949)
* IT: Drop dead stuff by @cstamas in [#1951](https://github.com/apache/maven/pull/1951)
* \[[MNG-8404](https://issues.apache.org/jira/browse/MNG-8404)\] ModelValidator: add unit tests and simplify a bit by @gnodet in [#1948](https://github.com/apache/maven/pull/1948)
* IT: Streamline ITs more by @cstamas in [#1952](https://github.com/apache/maven/pull/1952)
* \[[MNG-8388](https://issues.apache.org/jira/browse/MNG-8388)\] Fix escape characters being replaced to change the original configuration by @CrazyHZM in [#1946](https://github.com/apache/maven/pull/1946)
* Use https for www.apache.org/licenses/ by @slawekjaranowski in [#1955](https://github.com/apache/maven/pull/1955)
* \[[MNG-8401](https://issues.apache.org/jira/browse/MNG-8401)\] Reference global Maven download page by @kwin in [#1953](https://github.com/apache/maven/pull/1953)
* \[[MNG-8410](https://issues.apache.org/jira/browse/MNG-8410)\] API cleanup by @gnodet in [#1957](https://github.com/apache/maven/pull/1957)
* \[[MNG-8411](https://issues.apache.org/jira/browse/MNG-8411)\]\[[MNG-8412](https://issues.apache.org/jira/browse/MNG-8412)\]\[[MNG-8416](https://issues.apache.org/jira/browse/MNG-8416)\] mvnenc fixes by @cstamas in [#1959](https://github.com/apache/maven/pull/1959)
* \[[MNG-8393](https://issues.apache.org/jira/browse/MNG-8393)\] Enable consumer pom by default for 4.1.0 model  version only by @gnodet in [#1963](https://github.com/apache/maven/pull/1963)
* \[[MNG-8421](https://issues.apache.org/jira/browse/MNG-8421)\] Move all of logging setup to LookupInvoker; mvnenc IT by @cstamas in [#1964](https://github.com/apache/maven/pull/1964)
* \[[MNG-8423](https://issues.apache.org/jira/browse/MNG-8423)\] mvnenc -h by @cstamas in [#1971](https://github.com/apache/maven/pull/1971)
* \[[MNG-8406](https://issues.apache.org/jira/browse/MNG-8406)\] Proper IT isolation by @cstamas in [#1968](https://github.com/apache/maven/pull/1968)
* Add missing package infos by @gnodet in [#1980](https://github.com/apache/maven/pull/1980)
* \[[MNG-5729](https://issues.apache.org/jira/browse/MNG-5729)\] Fix possible NPE with introduction of mononic clock by @gnodet in [#1972](https://github.com/apache/maven/pull/1972)
* \[[MNG-5729](https://issues.apache.org/jira/browse/MNG-5729)\] Fix transfer rate computation by @gnodet in [#1969](https://github.com/apache/maven/pull/1969)
* \[[MNG-8244](https://issues.apache.org/jira/browse/MNG-8244)\] Using before:all / all / after:all is not triggered by @gnodet in [#1973](https://github.com/apache/maven/pull/1973)
* \[[MNG-8245](https://issues.apache.org/jira/browse/MNG-8245)\]\[[MNG-8246](https://issues.apache.org/jira/browse/MNG-8246)\] Warn when calling before: or after: phases by @gnodet in [#1974](https://github.com/apache/maven/pull/1974)
* \[[MNG-3282](https://issues.apache.org/jira/browse/MNG-3282)\] Docgen: remove property numbering, they are misleading and properties are "floating" anyway (alphabetically) by @gnodet in [#1979](https://github.com/apache/maven/pull/1979)
* \[[MNG-8414](https://issues.apache.org/jira/browse/MNG-8414)\] The consumer pom should warn if not able to downgrade the model version to 4.0.0 by @gnodet in [#1981](https://github.com/apache/maven/pull/1981)

## Dependency upgrade

* \[[MNG-8420](https://issues.apache.org/jira/browse/MNG-8420)\] Bump jlineVersion from 3.27.1 to 3.28.0 by @dependabot in [#1962](https://github.com/apache/maven/pull/1962)
* \[[MNG-8430](https://issues.apache.org/jira/browse/MNG-8430)\] Resolver 2.0.5 by @cstamas in [#1975](https://github.com/apache/maven/pull/1975)
* \[[MNG-8427](https://issues.apache.org/jira/browse/MNG-8427)\] Bump PlexusSecDispatcher to 4.0.3 by @cstamas in [#1959](https://github.com/apache/maven/pull/1959)

## Full changelog

https://github.com/apache/maven/compare/maven-4.0.0-rc-1...maven-4.0.0-rc-2

See [complete release notes for all versions][5]

[0]: https://dlcdn.apache.org/maven/maven-4/4.0.0-rc-2/
[1]: ../../plugins/index.html
[2]: https://maven.apache.org/
[4]: https://issues.apache.org/jira/secure/ReleaseNote.jspa?projectId=12316922&version=12355164
[5]: ../../docs/history.html

