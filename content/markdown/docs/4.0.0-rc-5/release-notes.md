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

# Apache Maven 4.0.0-rc-5 Release Notes

Apache Maven 4.0.0-rc-5 is available for download.

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.

The core release is independent of plugin releases. Further releases of plugins will be made separately.

If you have any questions, please consult:

- the website: https://maven.apache.org/
- the maven-user mailing list: https://maven.apache.org/mailing-lists.html
- the reference documentation: https://maven.apache.org/ref/4.0.0-rc-5/

## Maven 4.x

Maven 4.x is a major release that includes significant improvements and changes.
Please refer to the [Maven 4.x documentation](https://maven.apache.org/ref/4.0.0-rc-5/) for detailed information about new features and migration guidance.

### Important Notes

- This is a release candidate version intended for testing and feedback
- Please report any issues to GitHub: https://github.com/apache/maven/issues
- For production use, consider the stability and compatibility requirements of your project

### Upgrading from Maven 3

Maven 4 introduces significant changes to enhance functionality and performance. While we’ve prioritized compatibility with Maven 3.x, some POM files may require updates to build successfully with Maven 4.

To identify and resolve common issues, use the Maven upgrade tool:
- Run `mvnup check` to detect potential problems in your project.
- Run `mvnup apply` to automatically apply recommended fixes.

If your build relies on Maven extensions not supported by the upgrade tool, contact the extension developers to confirm their plans for Maven 4 compatibility.

If your build still fails, run it with the `-e` option to generate a detailed stack trace. Then, file an issue including:
- The full stack trace.
- A link to the exact branch of your open-source project, or a minimal reproducer for proprietary projects.

### Upgrading from Maven 4.0.0-rc-4

We reverted the flatenning of the consumer POM in rc-5 which could cause some problems when computing the dependency tree.

### Known issues

#### Bean configuration bug

A bug has been found in the bean configuration system where field accessibility state is cached globally. This can cause plugin configuration injection to fail when the same configuration field is accessed multiple times or in different contexts during a build. This particularly affects the plugin unit tests.

This will be fixed by [#11433]](https://github.com/apache/maven/pull/11433) in the next release.

#### Concurrency issue in the v4 API

A concurrency issue has been found in the Maven 4 API (still in preview mode) and will be fixed by [#11428](https://github.com/apache/maven/pull/11428) in the next release.

#### BOM packaging

Another bug has been found in how BOM projects are processed. When a project uses BOM packaging, the consumer POM is not being properly converted to standard POM packaging, and dependency versions could be lost in some cases.

This will be fixed by [#11427](https://github.com/apache/maven/pull/11427) in the next release.

#### macOS: JLine native library may be blocked by Gatekeeper on first use

On macOS (especially Apple Silicon), the first invocation of mvn may fail to load the JLine native terminal library with an error such as:
```
java.lang.UnsatisfiedLinkError: .../libjline-native/Mac/arm64/libjlinenative.jnilib: dlopen(...): code signature ... not valid for use in process: library load disallowed by system policy
```
This occurs when the binary distribution is downloaded via a web browser, which applies the `com.apple.quarantine extended` attribute.

Workaround (one-time fix):
```bash
xattr -r -d com.apple.quarantine /path/to/apache-maven-4.0.0-rc-5/lib/jline-native
```
Recommended download method (avoids the issue entirely):
```bash
curl -L -O https://archive.apache.org/dist/maven/maven-4/4.0.0-rc-5/binaries/apache-maven-4.0.0-rc-5-bin.tar.gz
tar -xzf apache-maven-4.0.0-rc-5-bin.tar.gz
```
This is a known issue [#10747](https://github.com/apache/maven/pull/10747) and will be addressed in a future release.

## Changelog

Maven 4.0.0-rc-5 aims at being gathering feedback before releasing Maven 4.0.0 GA. We'll focus on fixing critical bugs raised before GA.
Worth mentioning is the new upgrade tool which can be used to fix your `pom.xml` files and make them usable in Maven 4.

## 💥 Breaking changes

- Disable consumer POM flattening by default and add an opt-in feature (#11347) ([#11370](https://github.com/apache/maven/pull/11370)) @gnodet

## 🚀 New features and improvements

- Disable consumer POM flattening by default and add an opt-in feature (#11347) ([#11370](https://github.com/apache/maven/pull/11370)) @gnodet
- Make config files use UTF8 (#11263) ([#11265](https://github.com/apache/maven/pull/11265)) @cstamas
- Simplify prefix resolution (#11072) ([#11073](https://github.com/apache/maven/pull/11073)) @cstamas
- Add PathMatcherFactory.includesAll() ([#11008](https://github.com/apache/maven/pull/11008)) @desruisseaux
- Add skipMavenRc to ExecutorRequest and use it in ITs ([#10944](https://github.com/apache/maven/pull/10944)) @slawekjaranowski
- Add PathMatcherFactory service with directory filtering optimization (#10923) ([#10926](https://github.com/apache/maven/pull/10926)) @gnodet
- Allow configurable repository selection for version range resolution (backport) ([#10890](https://github.com/apache/maven/pull/10890)) @cstamas
- Switch resolver to use rwlock-local locks (#2546) ([#2555](https://github.com/apache/maven/pull/2555)) @gnodet

## 🐛 Bug Fixes

- Fix resource targetPath resolution to be relative to output directory (fixes #11381) (#11394) ([#11406](https://github.com/apache/maven/pull/11406)) @gnodet
- Fix MavenStaxReader location reporting for properties (#11402) ([#11404](https://github.com/apache/maven/pull/11404)) @gnodet
- Fix false parent cycle detection with flatten-maven-plugin ([#11400](https://github.com/apache/maven/pull/11400)) @gnodet
- Resolve property before model reflection to avoid recursion (#11385, fixes #11384) ([#11390](https://github.com/apache/maven/pull/11390)) @gnodet
- Explicitly register jdk ToolchainFactory for Maven 3 plugins (#11318) ([#11369](https://github.com/apache/maven/pull/11369)) @gnodet
- Fix -itr option not honored (#11359) ([#11361](https://github.com/apache/maven/pull/11361)) @gnodet
- Do not include invalid transitive repositories (#11357) ([#11362](https://github.com/apache/maven/pull/11362)) @gnodet
- Prevent infinite loop in RootLocator when .mvn directory exists in subdirectory (fixes #11321) (#11323) ([#11350](https://github.com/apache/maven/pull/11350)) @gnodet
- Fix [unknown project] messages in error output (#11324) ([#11349](https://github.com/apache/maven/pull/11349)) @gnodet
- Restore compatibility in maven-embedder (#11320) ([#11340](https://github.com/apache/maven/pull/11340)) @gnodet
- Add backward compatibility dependencies to maven-compat (#11301) ([#11339](https://github.com/apache/maven/pull/11339)) @gnodet
- Relative `targetPath`>` are resolved against the wrong directory ([#11325](https://github.com/apache/maven/pull/11325)) @desruisseaux
- Bug: when raw-streams are used, ensure system streams are set up (#11303) ([#11310](https://github.com/apache/maven/pull/11310)) @cstamas
- Fix plugin prefix resolution when metadata is not available from repository (#11287) ([#11288](https://github.com/apache/maven/pull/11288)) @gnodet
- Maven model 4.1.0 should not allow non-pom packaging for aggregators (#11279) ([#11285](https://github.com/apache/maven/pull/11285)) @gnodet
- Fix exception caused by duplicate dependencies in consumer pom (#11283) ([#11286](https://github.com/apache/maven/pull/11286)) @gnodet
- Remove use of toRealPath (#11250) ([#11257](https://github.com/apache/maven/pull/11257)) @cstamas
- Bugfix: fix CLI graceful death (#11239) ([#11246](https://github.com/apache/maven/pull/11246)) @cstamas
- Introduce RepositoryAwareRequest interface to consolidate repository handling (#11238) ([#11244](https://github.com/apache/maven/pull/11244)) @gnodet
- Fix repository ID interpolation in Maven 4 (#11224) ([#11241](https://github.com/apache/maven/pull/11241)) @gnodet
- Fix dependency groupId inference for Maven 4.1.0 model version (#11228) ([#11240](https://github.com/apache/maven/pull/11240)) @gnodet
- Consumer POM should keep only transitive dependencies, fixes #11162 (#11163) ([#11235](https://github.com/apache/maven/pull/11235)) @gnodet
- Fix StackOverflowError in parent POM resolution (backport #11106) ([#11234](https://github.com/apache/maven/pull/11234)) @gnodet
- Fix CI-friendly version processing with profile properties (fix #11196) ([#11225](https://github.com/apache/maven/pull/11225)) @gnodet
- Add phase upgrade support for Maven 4.1.0 model upgrades ([#11226](https://github.com/apache/maven/pull/11226)) @gnodet
- Fix GH-11199: Maven 4.0.0-rc-4 ignores defaultLogLevel ([#11227](https://github.com/apache/maven/pull/11227)) @gnodet
- Validate metaversions and detect extension conflicts (fixes #11181) ([#11216](https://github.com/apache/maven/pull/11216)) @cstamas
- Allow repository URL interpolation with improved validation (#11140) ([#11210](https://github.com/apache/maven/pull/11210)) @gnodet
- Improve mvn usage message (#11211) ([#11213](https://github.com/apache/maven/pull/11213)) @gnodet
- Enable the search for `module-info.class` file in the `META-INF/versions/` sub-directories of a JAR file. (#11153) ([#11206](https://github.com/apache/maven/pull/11206)) @gnodet
- Fix #10939: DefaultModelXmlFactory: make location tracking opt-in—disabled by def… ([#11092](https://github.com/apache/maven/pull/11092)) @arturobernalg
- Fix #11000: fix help default text ([#11099](https://github.com/apache/maven/pull/11099)) @arturobernalg
- GH-10210: fix too eager decrypt of legacy passwords (#11138) ([#11158](https://github.com/apache/maven/pull/11158)) @cstamas
- #11055: Inject all services into mojos and enable easy real-session mojo testing (#11103) ([#11139](https://github.com/apache/maven/pull/11139)) @gnodet
- Fix ReactorReader to prefer consumer POMs over build POMs (#11107) ([#11131](https://github.com/apache/maven/pull/11131)) @gnodet
- model-builder: simplify subproject auto-discovery decision (#11124) ([#11132](https://github.com/apache/maven/pull/11132)) @gnodet
- Add missing equals and hashCode methods in modular Java path type. ([#11130](https://github.com/apache/maven/pull/11130)) @desruisseaux
- fix: include extension in equals/hashCode of DefaultArtifactCoordinates ([#11101](https://github.com/apache/maven/pull/11101)) @arturobernalg
- Fix #11127: enforce non-null keys for InputLocation lookups and document behavior ([#11128](https://github.com/apache/maven/pull/11128)) @gnodet
- Bug: bad cache isolation between two sessions (#11083) ([#11085](https://github.com/apache/maven/pull/11085)) @cstamas
- Fix targetPath parameter ignored in resource bundles (fixes #11062) (#11063) ([#11080](https://github.com/apache/maven/pull/11080)) @gnodet
- Maven Upgrade Tool: remove unused --force and --yes options (Fixes #11001) (#11066) ([#11079](https://github.com/apache/maven/pull/11079)) @gnodet
- Fix XMLReader#getURL and enable the unit test (#11069) ([#11078](https://github.com/apache/maven/pull/11078)) @gnodet
- [#11048]  Fix race condition in MessageUtils (#11049) ([#11077](https://github.com/apache/maven/pull/11077)) @gnodet
- Uninterpolated repositories from parent POMs during model building (backport) ([#11039](https://github.com/apache/maven/pull/11039)) @cstamas
- Fix maven.mainClass property missing for external tools (#10998) ([#11007](https://github.com/apache/maven/pull/11007)) @gnodet
- Set Guice class loading to CHILD - avoid using terminally deprecated methods ([#11002](https://github.com/apache/maven/pull/11002)) @slawekjaranowski
- Avoid parsing MAVEN_OPTS (master/4.x) (#10970) ([#10993](https://github.com/apache/maven/pull/10993)) @gnodet
- Port the bug fixes identified when using that class in Maven clean and compiler plugin (#10935) ([#10936](https://github.com/apache/maven/pull/10936)) @gnodet
- Fix XmlNode.equals returning false between two different node implementations ([#10942](https://github.com/apache/maven/pull/10942)) @gnodet
- perf: optimize CompositeBeanHelper with reflection caching ([#10927](https://github.com/apache/maven/pull/10927)) @gnodet
- Expand value interning optimization and add configurable session property (#2495) ([#10932](https://github.com/apache/maven/pull/10932)) @gnodet
- Optimize validation performance with lazy SourceHint evaluation (#2518) ([#10919](https://github.com/apache/maven/pull/10919)) @gnodet
- Refactor setupContainer to validate ExtensionContext, test class and instance, and throw clear IllegalStateExceptions (#10901, fixes #10428) ([#10918](https://github.com/apache/maven/pull/10918)) @gnodet
- Bug fix in the default directory computed by `DefaultSourceRoot`. (#10912) ([#10917](https://github.com/apache/maven/pull/10917)) @gnodet
- Optimize XmlPlexusConfiguration for performance and thread safety (#2527) ([#10916](https://github.com/apache/maven/pull/10916)) @gnodet
- Fix mvnup tool issues #7934-#7938 (#9311) ([#10915](https://github.com/apache/maven/pull/10915)) @gnodet
- Fix #2486: Make Resource.addInclude() persist in project model (#2534) ([#2565](https://github.com/apache/maven/pull/2565)) @gnodet
- Fix MavenProject#getPlugin(String) performances (#2530) ([#2573](https://github.com/apache/maven/pull/2573)) @gnodet
- bug: fix duplicate dependency in effective model (fixes #2532) (#2554) ([#2556](https://github.com/apache/maven/pull/2556)) @gnodet
- Split system and user properties from maven.properties ([#2547](https://github.com/apache/maven/pull/2547)) @gnodet
- Fix ReactorReader incorrect warnings and logic (fixes #2497, #2498) ([#2536](https://github.com/apache/maven/pull/2536)) @gnodet
- Avoid double flush (#2478) ([#2537](https://github.com/apache/maven/pull/2537)) @gnodet
- Deduplicate filtered dependency graph ([#2493](https://github.com/apache/maven/pull/2493)) @alzimmermsft

## 👻 Maintenance

- Change IntelliJ icon to new oak leaf ([#11407](https://github.com/apache/maven/pull/11407)) @Bukama
- Fix IT isolation for MNG-6256 IT (#11395) ([#11396](https://github.com/apache/maven/pull/11396)) @cstamas
- Maven 4.0.x proper isolation ([#11393](https://github.com/apache/maven/pull/11393)) @cstamas
- [4.0.x] Consolidate caches ([#11379](https://github.com/apache/maven/pull/11379)) @cstamas
- Fix ITs (#11371) ([#11372](https://github.com/apache/maven/pull/11372)) @gnodet
- Mimir Cache-Purge w Pre-seed (#11315) ([#11348](https://github.com/apache/maven/pull/11348)) @cstamas
- Missed parts for Mimir update (#11312) ([#11313](https://github.com/apache/maven/pull/11313)) @cstamas
- Mimir 0.10.3 (#11291) ([#11311](https://github.com/apache/maven/pull/11311)) @cstamas
- Upgrade Mimir (#11274) ([#11282](https://github.com/apache/maven/pull/11282)) @cstamas
- Upgrade to spotless 3.0.0 and palantir 2.80.0 (#11275) ([#11277](https://github.com/apache/maven/pull/11277)) @gnodet
- Tidy up executor UTs (#11249) ([#11262](https://github.com/apache/maven/pull/11262)) @cstamas
- Sync GH workflow with master ([#11221](https://github.com/apache/maven/pull/11221)) @cstamas
- IT fixes ([#11217](https://github.com/apache/maven/pull/11217)) @cstamas
- Maven 4.0.x backport mimir ([#11180](https://github.com/apache/maven/pull/11180)) @cstamas
- commons-cli deprecations (#11170) ([#11176](https://github.com/apache/maven/pull/11176)) @cstamas
- Mimir updates (#11161) ([#11166](https://github.com/apache/maven/pull/11166)) @cstamas
- [[[MNG-8696]](https://issues.apache.org/jira/browse/MNG-8696) - ](https://issues.apache.org/jira/browse/MNG-8696) - Hide the cache from DefaultDependencyResolverResult constructor ([#11154](https://github.com/apache/maven/pull/11154)) @desruisseaux
- Generating configuration documentation during site build ([#10979](https://github.com/apache/maven/pull/10979)) @slawekjaranowski
- Improvements in ITs executing - provide default local repo ([#10963](https://github.com/apache/maven/pull/10963)) @slawekjaranowski
- Backport: Fix build and Jenkinsfile  (#10904) ([#10905](https://github.com/apache/maven/pull/10905)) @cstamas
- chore: remove unused managed dependency (#2570) ([#2572](https://github.com/apache/maven/pull/2572)) @gnodet
- Cleanups duplicate configs with new parent ([#2567](https://github.com/apache/maven/pull/2567)) @gnodet
- Update Maven version to 4.0.0-SNAPSHOT ([#2513](https://github.com/apache/maven/pull/2513)) @gnodet
- Update branch name for release-drafter in maven-4.0.x ([#2503](https://github.com/apache/maven/pull/2503)) @slawekjaranowski
- Execute GitHub action - Java CI on maven-4.0.x branch ([#2504](https://github.com/apache/maven/pull/2504)) @slawekjaranowski

## 🔧 Build

- Bump actions/upload-artifact from 4.6.2 to 5.0.0 ([#11332](https://github.com/apache/maven/pull/11332)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump actions/download-artifact from 5.0.0 to 6.0.0 ([#11333](https://github.com/apache/maven/pull/11333)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Allow single build per branch or pull request ([#11045](https://github.com/apache/maven/pull/11045)) @slawekjaranowski
- Pin GitHub action versions by hash ([#10902](https://github.com/apache/maven/pull/10902)) @slawekjaranowski

## 📦 Dependency updates

- Bump net.sourceforge.pmd:pmd-core from 7.17.0 to 7.18.0 ([#11377](https://github.com/apache/maven/pull/11377)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-testing from 1.7.0 to 2.0.1 ([#11344](https://github.com/apache/maven/pull/11344)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.mojo:extra-enforcer-rules from 1.10.0 to 1.11.0 ([#11352](https://github.com/apache/maven/pull/11352)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump io.github.olamy.maven.plugins:jacoco-aggregator-maven-plugin from 1.0.3 to 1.0.4 ([#11345](https://github.com/apache/maven/pull/11345)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.apache.maven.plugin-tools:maven-plugin-annotations from 3.15.1 to 3.15.2 ([#11337](https://github.com/apache/maven/pull/11337)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.apache.maven.plugin-tools:maven-plugin-tools-java from 3.15.1 to 3.15.2 ([#11338](https://github.com/apache/maven/pull/11338)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump xmlunitVersion from 2.10.4 to 2.11.0 ([#11334](https://github.com/apache/maven/pull/11334)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump ch.qos.logback:logback-classic from 1.5.19 to 1.5.20 ([#11296](https://github.com/apache/maven/pull/11296)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.mojo:exec-maven-plugin from 3.6.1 to 3.6.2 ([#11297](https://github.com/apache/maven/pull/11297)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-velocity from 2.2.1 to 2.3.0 ([#11261](https://github.com/apache/maven/pull/11261)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.github.siom79.japicmp:japicmp-maven-plugin from 0.24.1 to 0.24.2 ([#11271](https://github.com/apache/maven/pull/11271)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-testing from 1.6.1 to 1.7.0 ([#11270](https://github.com/apache/maven/pull/11270)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-testing from 1.6.0 to 1.6.1 ([#11260](https://github.com/apache/maven/pull/11260)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.apache.maven:maven-archiver from 3.6.4 to 3.6.5 ([#11232](https://github.com/apache/maven/pull/11232)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump eu.maveniverse.maven.mimir:testing from 0.9.3 to 0.9.4 ([#11233](https://github.com/apache/maven/pull/11233)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.jacoco:jacoco-maven-plugin from 0.8.13 to 0.8.14 ([#11254](https://github.com/apache/maven/pull/11254)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Resolver 2.0.13 (#11137) ([#11248](https://github.com/apache/maven/pull/11248)) @cstamas
- Bump net.bytebuddy:byte-buddy from 1.17.7 to 1.17.8 ([#11243](https://github.com/apache/maven/pull/11243)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.github.siom79.japicmp:japicmp-maven-plugin from 0.23.1 to 0.24.1 ([#11208](https://github.com/apache/maven/pull/11208)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump asmVersion from 9.8 to 9.9 ([#11204](https://github.com/apache/maven/pull/11204)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.mojo:exec-maven-plugin from 3.5.1 to 3.6.1 ([#11205](https://github.com/apache/maven/pull/11205)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump ch.qos.logback:logback-classic from 1.5.18 to 1.5.19 ([#11192](https://github.com/apache/maven/pull/11192)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump actions/cache from 4.2.4 to 4.3.0 ([#11173](https://github.com/apache/maven/pull/11173)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.assertj:assertj-core from 3.27.5 to 3.27.6 ([#11164](https://github.com/apache/maven/pull/11164)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump mockitoVersion from 5.19.0 to 5.20.0 ([#11156](https://github.com/apache/maven/pull/11156)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.assertj:assertj-core from 3.27.4 to 3.27.5 ([#11149](https://github.com/apache/maven/pull/11149)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.google.guava:guava from 33.4.8-jre to 33.5.0-jre ([#11144](https://github.com/apache/maven/pull/11144)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.sourceforge.pmd:pmd-core from 7.16.0 to 7.17.0 ([#11123](https://github.com/apache/maven/pull/11123)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump xmlunitVersion from 2.10.3 to 2.10.4 ([#11122](https://github.com/apache/maven/pull/11122)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Resolver 2.0.11 (#11043) ([#11115](https://github.com/apache/maven/pull/11115)) @cstamas
- Bump jlineVersion from 3.30.5 to 3.30.6 ([#11113](https://github.com/apache/maven/pull/11113)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump eu.maveniverse.maven.plugins:bom-builder3 from 1.2.1 to 1.3.0 ([#11097](https://github.com/apache/maven/pull/11097)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump mockitoVersion from 5.18.0 to 5.19.0 ([#11051](https://github.com/apache/maven/pull/11051)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump eu.maveniverse.maven.plugins:bom-builder3 from 1.2.0 to 1.2.1 ([#11064](https://github.com/apache/maven/pull/11064)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-testing from 1.5.0 to 1.6.0 ([#11057](https://github.com/apache/maven/pull/11057)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.bytebuddy:byte-buddy from 1.17.6 to 1.17.7 ([#11053](https://github.com/apache/maven/pull/11053)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.apache.maven:maven-archiver from 3.6.3 to 3.6.4 ([#11036](https://github.com/apache/maven/pull/11036)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump actions/cache from 4.2.3 to 4.2.4 ([#11030](https://github.com/apache/maven/pull/11030)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.assertj:assertj-core from 3.27.3 to 3.27.4 ([#11034](https://github.com/apache/maven/pull/11034)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump jlineVersion from 3.30.4 to 3.30.5 ([#11026](https://github.com/apache/maven/pull/11026)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump commons-cli:commons-cli from 1.9.0 to 1.10.0 ([#11020](https://github.com/apache/maven/pull/11020)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump eu.maveniverse.maven.plugins:bom-builder3 from 1.1.1 to 1.2.0 ([#11014](https://github.com/apache/maven/pull/11014)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.sourceforge.pmd:pmd-core from 7.15.0 to 7.16.0 ([#11005](https://github.com/apache/maven/pull/11005)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit:junit-bom from 5.13.3 to 5.13.4 ([#10989](https://github.com/apache/maven/pull/10989)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit.jupiter:junit-jupiter from 5.13.3 to 5.13.4 ([#10988](https://github.com/apache/maven/pull/10988)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump commons-io:commons-io from 2.19.0 to 2.20.0 ([#10968](https://github.com/apache/maven/pull/10968)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump io.github.olamy.maven.plugins:jacoco-aggregator-maven-plugin from 1.0.2 to 1.0.3 ([#10933](https://github.com/apache/maven/pull/10933)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-utils from 3.0.24 to 3.6.0 ([#2563](https://github.com/apache/maven/pull/2563)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-component-annotations from 2.1.0 to 2.2.0 ([#2560](https://github.com/apache/maven/pull/2560)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.google.jimfs:jimfs from 1.3.0 to 1.3.1 ([#10911](https://github.com/apache/maven/pull/10911)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit.jupiter:junit-jupiter from 5.13.2 to 5.13.3 ([#8716](https://github.com/apache/maven/pull/8716)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit:junit-bom from 5.13.2 to 5.13.3 ([#8717](https://github.com/apache/maven/pull/8717)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit.jupiter:junit-jupiter from 5.13.1 to 5.13.2 ([#2561](https://github.com/apache/maven/pull/2561)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.apache.maven:maven-parent from 44 to 45 ([#2552](https://github.com/apache/maven/pull/2552)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.sourceforge.pmd:pmd-core from 7.14.0 to 7.15.0 ([#2553](https://github.com/apache/maven/pull/2553)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit:junit-bom from 5.13.1 to 5.13.2 ([#2549](https://github.com/apache/maven/pull/2549)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.bytebuddy:byte-buddy from 1.17.5 to 1.17.6 ([#2543](https://github.com/apache/maven/pull/2543)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump xmlunitVersion from 2.10.2 to 2.10.3 ([#2542](https://github.com/apache/maven/pull/2542)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump resolverVersion from 2.0.9 to 2.0.10 ([#2541](https://github.com/apache/maven/pull/2541)) @[dependabot[bot]](https://github.com/apps/dependabot)

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-4.0.0-rc-5).
