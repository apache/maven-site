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

# Release Notes &#x2013; Maven 4.0.0-rc-3

The Apache Maven team would like to announce the release of Maven 4.0.0-rc-3.

Maven 4 release **requires Java 17 for runtime**.

This is release candidate release, **is not suitable for production**.

Maven 4.0.0-rc-3 is [available for download](https://dlcdn.apache.org/maven/maven-4/4.0.0-rc-3/).

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting, and documentation from a central place.

The core release is independent of plugin releases. Further releases of plugins will be made separately. See the [PluginList][1] for more information.

If you have any questions, please consult:

- the website: [https://maven.apache.org/](https://maven.apache.org/)
- the maven-user mailing list: [https://maven.apache.org/mailing-lists.html](/mailing-lists.html)
- the reference documentation: [https://maven.apache.org/ref/4.0.0-rc-3/](/ref/4.0.0-rc-3/)

# Upgrading from Maven 3

Maven 4 brings a tons of changes.  We've tried hard to maximise compatibility with Maven 3.x, but in order to have your build work with Maven 4, you will need to upgrade some plugins (such as `maven-enforcer-plugin`, `maven-remote-resources-plugin`, `maven-shade-plugin`, etc...) to their most recent versions.

If your build is leveraging Maven extensions, you may very well expect some breakage. Some extensions may need to be updated:
* the useful, but unmaintained, [`os-maven-plugin`](https://github.com/trustin/os-maven-plugin/) extension has been replaced with at [`nisse`](https://github.com/maveniverse/nisse) extension.

Contact the extensions' developers team to know their plans regarding supporting to Maven 4.

# Upgrading from Maven 4.0.0-rc-2

**Important note about breaking changes:** Maven 4.0.0-rc-3 includes changes in the new API that will require updates in plugin targeting the new API, especially relating to [[MNG-8395](https://issues.apache.org/jira/browse/MNG-8395)] which adds a new `<source>` element to the model and redirects the `<sourceDirectory>` element. Plugin developers will need to update their code to work with these changes.

# Changelog

Release Notes - Maven - Version 4.0.0-rc-3

## Bug

* [[MNG-8431](https://issues.apache.org/jira/browse/MNG-8431)] - Lifecycle doc broken
* [[MNG-8436](https://issues.apache.org/jira/browse/MNG-8436)] - Transfer speeds are off
* [[MNG-8439](https://issues.apache.org/jira/browse/MNG-8439)] - mvnenc init is busted
* [[MNG-8454](https://issues.apache.org/jira/browse/MNG-8454)] - Maven RC3-Build: Maven CLI subproject fails to build
* [[MNG-8460](https://issues.apache.org/jira/browse/MNG-8460)] - All Prompter use seems busted
* [[MNG-8461](https://issues.apache.org/jira/browse/MNG-8461)] - SettingsBuilderRequest not emitted
* [[MNG-8469](https://issues.apache.org/jira/browse/MNG-8469)] - Regression: Maven 4-rc-2 fails to build Apache Shiro (Karaf issue)
* [[MNG-8477](https://issues.apache.org/jira/browse/MNG-8477)] - File-activated profile not activated when maven.modelBuilder.parallelism=1
* [[MNG-8478](https://issues.apache.org/jira/browse/MNG-8478)] - Setting org.slf4j.simpleLogger.dateTimeFormat fails on Maven 4.0.0-rc-2
* [[MNG-8479](https://issues.apache.org/jira/browse/MNG-8479)] - Multi-module, intra-reactor artifact resolution doesn't work for 'ejb' packaged types like with 'jar' packaged types and requires compile
* [[MNG-8483](https://issues.apache.org/jira/browse/MNG-8483)] - BaseRequest.nonNull should be removed
* [[MNG-8484](https://issues.apache.org/jira/browse/MNG-8484)] - Perhaps remove BaseRequest.unmodifiable
* [[MNG-8487](https://issues.apache.org/jira/browse/MNG-8487)] - maven-cli testing is not fully isolated
* [[MNG-8508](https://issues.apache.org/jira/browse/MNG-8508)] - org.apache.maven.internal.impl is a split package
* [[MNG-8512](https://issues.apache.org/jira/browse/MNG-8512)] - org.slf4j.simpleLogger.defaultLogLevel Configuration conflict
* [[MNG-8517](https://issues.apache.org/jira/browse/MNG-8517)] - Cleanup dependencies
* [[MNG-8518](https://issues.apache.org/jira/browse/MNG-8518)] - Unexpected dependency resolution events with Maven 4
* [[MNG-8519](https://issues.apache.org/jira/browse/MNG-8519)] - Loss of context/trace in events
* [[MNG-8520](https://issues.apache.org/jira/browse/MNG-8520)] - Multiple resolve events for same artifact
* [[MNG-8523](https://issues.apache.org/jira/browse/MNG-8523)] - User properties should override model properties in the model
* [[MNG-8530](https://issues.apache.org/jira/browse/MNG-8530)] - Maven 4 prompter broken
* [[MNG-8535](https://issues.apache.org/jira/browse/MNG-8535)] - Embedded launcher can't capture Maven 4 output
* [[MNG-8544](https://issues.apache.org/jira/browse/MNG-8544)] - Conflicting extensions are silently ignored; extension is not loaded
* [[MNG-8545](https://issues.apache.org/jira/browse/MNG-8545)] - Maven4 API RemoteRepository are used in keys but they have no hash/equals defined
* [[MNG-8554](https://issues.apache.org/jira/browse/MNG-8554)] - After b2b0bbeda6d77d5cfd5b163f855a24f0a169eaf3 master is not buildable with mvn3
* [[MNG-8561](https://issues.apache.org/jira/browse/MNG-8561)] - SourceRoot should be more lenient wrt duplicates
* [[MNG-8564](https://issues.apache.org/jira/browse/MNG-8564)] - Move LegacyPlexusInteractivity to maven-compat
* [[MNG-8565](https://issues.apache.org/jira/browse/MNG-8565)] - Thread leaks in maven-cli
* [[MNG-8567](https://issues.apache.org/jira/browse/MNG-8567)] - Resumption is broken
* [[MNG-8579](https://issues.apache.org/jira/browse/MNG-8579)] - stdout/stderr consumer erroneously uses double newlines
* [[MNG-8580](https://issues.apache.org/jira/browse/MNG-8580)] - Central requirements are not fulfilled with consumer POM
* [[MNG-8582](https://issues.apache.org/jira/browse/MNG-8582)] - mvnsh sysout and syserr are clogged after mvn invoked
* [[MNG-8583](https://issues.apache.org/jira/browse/MNG-8583)] - Missing SISU bean definitions from maven-resolver-provider
* [[MNG-8588](https://issues.apache.org/jira/browse/MNG-8588)] - Add a way to get the activated profiles from the Project interface
* [[MNG-8591](https://issues.apache.org/jira/browse/MNG-8591)] - Fix default value for plugin resolution (which has always been null)
* [[MNG-8600](https://issues.apache.org/jira/browse/MNG-8600)] - Conflicting requirement for Terminal and RawStreams (mvn vs mvnd)
* [[MNG-8602](https://issues.apache.org/jira/browse/MNG-8602)] - Maven 4 BOM applies main version to all dependencies

## New Feature

* [[MNG-6113](https://issues.apache.org/jira/browse/MNG-6113)] - Rename the 'Central Repository' to 'Maven Central Repository' in the 4.0.0 super POM
* [[MNG-8437](https://issues.apache.org/jira/browse/MNG-8437)] - New tool: mvnsh
* [[MNG-8555](https://issues.apache.org/jira/browse/MNG-8555)] - Support externalising secret values in settings.xml

## Improvement

* [[MNG-7592](https://issues.apache.org/jira/browse/MNG-7592)] - String deduplication in model building
* [[MNG-8447](https://issues.apache.org/jira/browse/MNG-8447)] - Lossy problem collector
* [[MNG-8465](https://issues.apache.org/jira/browse/MNG-8465)] - Support ${project.rootDirectory} in repository url
* [[MNG-8482](https://issues.apache.org/jira/browse/MNG-8482)] - Use instanceof assignments to get rid of casting expressions
* [[MNG-8502](https://issues.apache.org/jira/browse/MNG-8502)] - The "embedded" executor does not obey MAVEN_ARGS env variable
* [[MNG-8524](https://issues.apache.org/jira/browse/MNG-8524)] - DefaultInterpolator should be used by injection
* [[MNG-8525](https://issues.apache.org/jira/browse/MNG-8525)] - Add an integration test with Maven 4 Plugin Sample
* [[MNG-8540](https://issues.apache.org/jira/browse/MNG-8540)] - Add cache to API requests
* [[MNG-8541](https://issues.apache.org/jira/browse/MNG-8541)] - Support throwing Exception from Mojo#execute
* [[MNG-8562](https://issues.apache.org/jira/browse/MNG-8562)] - Maven4 consumer pom : remove "child" attribs from `<scm>`
* [[MNG-8594](https://issues.apache.org/jira/browse/MNG-8594)] - Ability to feed options and goals from ad-hoc created file to Maven

## Task

* [[MNG-8395](https://issues.apache.org/jira/browse/MNG-8395)] - Add a `<source>` element in the model.
* [[MNG-8433](https://issues.apache.org/jira/browse/MNG-8433)] - Use the switch expressions syntax
* [[MNG-8438](https://issues.apache.org/jira/browse/MNG-8438)] - maven-jline: Migrate to Maven DI (off javax.inject)
* [[MNG-8444](https://issues.apache.org/jira/browse/MNG-8444)] - Exclude maven-xml and maven-xml-impl (the artifact has been renamed)
* [[MNG-8446](https://issues.apache.org/jira/browse/MNG-8446)] - Project cannot start due to too many warnings
* [[MNG-8463](https://issues.apache.org/jira/browse/MNG-8463)] - Update lifecycle plugins
* [[MNG-8466](https://issues.apache.org/jira/browse/MNG-8466)] - Followup to MNG-8461
* [[MNG-8473](https://issues.apache.org/jira/browse/MNG-8473)] - Site and XSD/doco generation fixed
* [[MNG-8475](https://issues.apache.org/jira/browse/MNG-8475)] - In the loop scenario, StringBuilder is used instead of concatenation
* [[MNG-8493](https://issues.apache.org/jira/browse/MNG-8493)] - 'serialVersionUID' can be annotated with '@Serial' annotation
* [[MNG-8494](https://issues.apache.org/jira/browse/MNG-8494)] - Restore Maven 3 compatibility
* [[MNG-8503](https://issues.apache.org/jira/browse/MNG-8503)] - Configure logging using maven.logger.* properties rather than org.slf4j.simpleLogger.*
* [[MNG-8513](https://issues.apache.org/jira/browse/MNG-8513)] - Be stricter about xml combination modes
* [[MNG-8514](https://issues.apache.org/jira/browse/MNG-8514)] - Improve VersionRangeResolverResult
* [[MNG-8515](https://issues.apache.org/jira/browse/MNG-8515)] - Use internal interpolator
* [[MNG-8522](https://issues.apache.org/jira/browse/MNG-8522)] - Deprecate maven-builder-support artifact and its classes
* [[MNG-8527](https://issues.apache.org/jira/browse/MNG-8527)] - Re-enable consumer POM
* [[MNG-8551](https://issues.apache.org/jira/browse/MNG-8551)] - Improve early error reporting
* [[MNG-8558](https://issues.apache.org/jira/browse/MNG-8558)] - Switch DefaultToolchainManager to Api V4
* [[MNG-8570](https://issues.apache.org/jira/browse/MNG-8570)] - Investigate new cache setups; mvnsh and mvnd
* [[MNG-8571](https://issues.apache.org/jira/browse/MNG-8571)] - Sisu @Priority annotation is not honoured
* [[MNG-8573](https://issues.apache.org/jira/browse/MNG-8573)] - Implement service alike to p-u OS helper class
* [[MNG-8577](https://issues.apache.org/jira/browse/MNG-8577)] - Project POM hierarchy cleanup
* [[MNG-8586](https://issues.apache.org/jira/browse/MNG-8586)] - Maven core should provide property maven.version.major
* [[MNG-8587](https://issues.apache.org/jira/browse/MNG-8587)] - mvnsh: implement navigation (cd)
* [[MNG-8590](https://issues.apache.org/jira/browse/MNG-8590)] - Make sure maven-resolver-provider does not depend on maven-impl
* [[MNG-8592](https://issues.apache.org/jira/browse/MNG-8592)] - Subproject maven-resolver-provider lost Sisu index; give it back
* [[MNG-8599](https://issues.apache.org/jira/browse/MNG-8599)] - Generated source files should be included in the sources jars
* [[MNG-8601](https://issues.apache.org/jira/browse/MNG-8601)] - Add type test-java-source similar to type java-source
* [[MNG-8604](https://issues.apache.org/jira/browse/MNG-8604)] - Use soft references for cache
* [[MNG-8605](https://issues.apache.org/jira/browse/MNG-8605)] - The log file should be appended to
* [[MNG-8610](https://issues.apache.org/jira/browse/MNG-8610)] - Add javadoc for all o.a.m.api packages

## Dependency upgrade

* [[MNG-8435](https://issues.apache.org/jira/browse/MNG-8435)] - (test) Bump net.bytebuddy:byte-buddy from 1.15.10 to 1.15.11
* [[MNG-8440](https://issues.apache.org/jira/browse/MNG-8440)] - Bump org.junit.jupiter:junit-jupiter from 5.11.3 to 5.11.4
* [[MNG-8441](https://issues.apache.org/jira/browse/MNG-8441)] - Bump org.junit:junit-bom from 5.11.3 to 5.11.4
* [[MNG-8442](https://issues.apache.org/jira/browse/MNG-8442)] - (build) Bump eu.maveniverse.maven.plugins:bom-builder3 from 1.0.2 to 1.0.3
* [[MNG-8448](https://issues.apache.org/jira/browse/MNG-8448)] - Bump com.google.guava:guava from 33.3.1-jre to 33.4.0-jre
* [[MNG-8452](https://issues.apache.org/jira/browse/MNG-8452)] - Bump ch.qos.logback:logback-classic from 1.5.12 to 1.5.13
* [[MNG-8457](https://issues.apache.org/jira/browse/MNG-8457)] - Bump ch.qos.logback:logback-classic from 1.5.13 to 1.5.14
* [[MNG-8458](https://issues.apache.org/jira/browse/MNG-8458)] - Bump org.assertj:assertj-core from 3.26.3 to 3.27.0
* [[MNG-8468](https://issues.apache.org/jira/browse/MNG-8468)] - Bump ch.qos.logback:logback-classic from 1.5.14 to 1.5.15
* [[MNG-8497](https://issues.apache.org/jira/browse/MNG-8497)] - Bump ch.qos.logback:logback-classic from 1.5.15 to 1.5.16
* [[MNG-8498](https://issues.apache.org/jira/browse/MNG-8498)] - Bump org.assertj:assertj-core from 3.27.0 to 3.27.2
* [[MNG-8499](https://issues.apache.org/jira/browse/MNG-8499)] - Bump mockitoVersion from 5.14.2 to 5.15.2
* [[MNG-8506](https://issues.apache.org/jira/browse/MNG-8506)] - Bump com.github.siom79.japicmp:japicmp-maven-plugin from 0.23.0 to 0.23.1 (build)
* [[MNG-8528](https://issues.apache.org/jira/browse/MNG-8528)] - Bump org.assertj:assertj-core from 3.27.2 to 3.27.3
* [[MNG-8529](https://issues.apache.org/jira/browse/MNG-8529)] - Bump net.bytebuddy:byte-buddy from 1.15.11 to 1.16.1
* [[MNG-8549](https://issues.apache.org/jira/browse/MNG-8549)] - (test) Bump net.bytebuddy:byte-buddy from 1.16.1 to 1.17.0
* [[MNG-8550](https://issues.apache.org/jira/browse/MNG-8550)] - Update JLine3 to 3.29.0
* [[MNG-8553](https://issues.apache.org/jira/browse/MNG-8553)] - Resolver 2.0.6
* [[MNG-8585](https://issues.apache.org/jira/browse/MNG-8585)] - (test) Bump net.bytebuddy:byte-buddy from 1.17.0 to 1.17.1
* [[MNG-8589](https://issues.apache.org/jira/browse/MNG-8589)] - Bump org.junit:junit-bom from 5.11.4 to 5.12.0
* [[MNG-8593](https://issues.apache.org/jira/browse/MNG-8593)] - Resolver 2.0.7
* [[MNG-8595](https://issues.apache.org/jira/browse/MNG-8595)] - Bump slf4jVersion from 2.0.16 to 2.0.17
* [[MNG-8596](https://issues.apache.org/jira/browse/MNG-8596)] - Bump ch.qos.logback:logback-classic from 1.5.16 to 1.5.17
* [[MNG-8606](https://issues.apache.org/jira/browse/MNG-8606)] - Bump mockitoVersion from 5.15.2 to 5.16.0
* [[MNG-8607](https://issues.apache.org/jira/browse/MNG-8607)] - Bump net.bytebuddy:byte-buddy from 1.17.1 to 1.17.2

