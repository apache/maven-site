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

# Apache Maven 4.0.0-rc-6 Release Notes

Apache Maven 4.0.0-rc-6 is available for download.

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.

The core release is independent of plugin releases. Further releases of plugins will be made separately.

If you have any questions, please consult:

- the website: https://maven.apache.org/
- the maven-user mailing list: https://maven.apache.org/mailing-lists.html
- the reference documentation: https://maven.apache.org/ref/4.0.0-rc-6/

## Maven 4.x

Maven 4.x is a major release that includes significant improvements and changes.
Please refer to the [Maven 4.x documentation](https://maven.apache.org/ref/4.0.0-rc-6/) for detailed information about new features and migration guidance.

### Important Notes

- This is a release candidate version intended for testing and feedback
- Please report any issues to GitHub: https://github.com/apache/maven/issues
- For production use, consider the stability and compatibility requirements of your project

### Upgrading from Maven 3

Maven 4 introduces significant changes to enhance functionality and performance. While we've prioritized compatibility with Maven 3.x, some POM files may require updates to build successfully with Maven 4.

To identify and resolve common issues, use the Maven upgrade tool:
- Run `mvnup check` to detect potential problems in your project.
- Run `mvnup apply` to automatically apply recommended fixes.

If your build relies on Maven extensions not supported by the upgrade tool, contact the extension developers to confirm their plans for Maven 4 compatibility.

If your build still fails, run it with the `-e` option to generate a detailed stack trace. Then, file an issue including:
- The full stack trace.
- A link to the exact branch of your open-source project, or a minimal reproducer for proprietary projects.

### Upgrading from Maven 4.0.0-rc-5

All known issues reported in the RC-5 release notes have been fixed:

- **Bean configuration bug** — field accessibility state was cached globally, causing plugin configuration injection failures. Fixed in [#11433](https://github.com/apache/maven/pull/11433).
- **Concurrency issue in the v4 API** — a `ConcurrentModificationException` was fixed in [#11429](https://github.com/apache/maven/pull/11429).
- **BOM packaging** — consumer POM conversion for BOM projects now correctly preserves dependency versions. Fixed in [#11464](https://github.com/apache/maven/pull/11464).
- **macOS Gatekeeper** — the extracted JLine native binaries that triggered Gatekeeper have been removed from the distribution ([#11997](https://github.com/apache/maven/pull/11997)). The `xattr` workaround from RC-5 is no longer needed.

### Known issues

#### BOM consumer POM property references

A bug has been found where BOM consumer POMs can leave property references unresolved.

This will be fixed by [#12641](https://github.com/apache/maven/pull/12641) in the next release.

## Changelog

Maven 4.0.0-rc-6 continues gathering feedback before releasing Maven 4.0.0 GA. This release includes a large number of bug fixes and improvements, with a focus on stability and compatibility.

## 🚀 New features and improvements

- Backport the use of hardlink instead of file copy ([#11564](https://github.com/apache/maven/pull/11564)) @desruisseaux
- Accept Java module names as attached artifactId even if they differ from the project's artifactId ([#11573](https://github.com/apache/maven/pull/11573)) @desruisseaux
- Add module-aware resource handling for modular sources ([#11700](https://github.com/apache/maven/pull/11700)) @desruisseaux
- [[MNG-8507]](https://issues.apache.org/jira/browse/MNG-8507) - Reduce allocation pressure in model building pipeline ([#12540](https://github.com/apache/maven/pull/12540)) @gnodet
- Fix #12530: add mvnup upgrade strategies for Maven 4 known compatibility issues ([#12560](https://github.com/apache/maven/pull/12560)) @gnodet
- Introduce validation control ([#12548](https://github.com/apache/maven/pull/12548)) @cstamas
- Backport #12505: mvnup: widen exact Maven version pins to allow Maven 4 ([#12508](https://github.com/apache/maven/pull/12508)) @gnodet
- [[MNG-5913]](https://issues.apache.org/jira/browse/MNG-5913) - Allow defining aliases for existing server configurations in settings.xml ([#12473](https://github.com/apache/maven/pull/12473)) @slawekjaranowski
- Backport #12454: mvnup upgrade strategies and compatibility improvements ([#12467](https://github.com/apache/maven/pull/12467)) @gnodet
- In failed build limit reactor summary to only failed modules ([#12469](https://github.com/apache/maven/pull/12469)) @slawekjaranowski
- Add mvnup SourceStrategy for migrating to `<source>` elements ([#12357](https://github.com/apache/maven/pull/12357)) @gnodet
- Add jaxb2-maven-plugin to mvnup plugin upgrade list ([#12356](https://github.com/apache/maven/pull/12356)) @gnodet
- Switch default resolver transport from JDK/methanol to Apache HttpClient ([#12341](https://github.com/apache/maven/pull/12341)) @gnodet
- Pull out maven-executor into its own project ([#12186](https://github.com/apache/maven/pull/12186)) @cstamas
- Add maven-surefire-report-plugin to PluginUpgradeStrategy ([#12114](https://github.com/apache/maven/pull/12114)) @gnodet
- Fix #12087: add surefire and failsafe plugins to PluginUpgradeStrategy ([#12109](https://github.com/apache/maven/pull/12109)) @gnodet
- Promote java version in JavaToolchain ([#11971](https://github.com/apache/maven/pull/11971)) @slawekjaranowski
- Add time zone to Maven startup banner ([#11781](https://github.com/apache/maven/pull/11781)) @slawekjaranowski
- Update formatting of prerequisites-requirements error to improve readability ([#11525](https://github.com/apache/maven/pull/11525)) @slawekjaranowski

## 🐛 Bug Fixes

- Remove an optimization on PathSelector producing false negatives ([#12623](https://github.com/apache/maven/pull/12623)) @desruisseaux
- Remove erroneous path normalization optimization + regression test ([#12621](https://github.com/apache/maven/pull/12621)) @gnodet
- Fix #12583: Inverted file existence check in DefaultTransport.put() ([#12619](https://github.com/apache/maven/pull/12619)) @gnodet @elharo
- [[MNG-8507]](https://issues.apache.org/jira/browse/MNG-8507) mvnup: skip dedup inside plugin `<configuration>` elements ([#12582](https://github.com/apache/maven/pull/12582)) @gnodet
- Fix BOM consumer POM leaving property references unresolved ([#12627](https://github.com/apache/maven/pull/12627)) @gnodet
- [MNG-8425] Fix mvnenc init saving invalid master source configuration ([#12564](https://github.com/apache/maven/pull/12564)) @gnodet
- Consumer POM of multi-module project should exclude `<build>` and `<dependencies>` elements ([#11764](https://github.com/apache/maven/pull/11764)) @desruisseaux
- Fix #12045: fix mvnup plugin upgrade strategy for inherited plugins from remote parent POMs ([#12054](https://github.com/apache/maven/pull/12054)) @gnodet
- Handle Ctrl+C on Windows terminals ([#12550](https://github.com/apache/maven/pull/12550)) @gnodet
- Fix #12531: filter NO_REPOSITORY sentinel from mapped exceptions in ArtifactResolverResult ([#12561](https://github.com/apache/maven/pull/12561)) @gnodet
- Fix #12534: Wire up @After annotation processing in Maven core ([#12566](https://github.com/apache/maven/pull/12566)) @gnodet
- Fix #12427: Reject path-traversal segments in coordinate ids and versions ([#12565](https://github.com/apache/maven/pull/12565)) @gnodet
- [[MNG-11147]](https://issues.apache.org/jira/browse/MNG-11147) - Fix BOM version inference for sibling modules in dependencyManagement ([#12569](https://github.com/apache/maven/pull/12569)) @gnodet
- Preserve unresolved ${...} in CLI -D values ([#12524](https://github.com/apache/maven/pull/12524)) @ascheman
- Avoid IllegalStateException on duplicate profile ids in DefaultModelBuilder ([#12506](https://github.com/apache/maven/pull/12506)) @ascheman
- Fix deadlock in AbstractRequestCache ([#12468](https://github.com/apache/maven/pull/12468)) @gnodet
- Fix #12464: Skip MAVEN_ARGS for non-default main classes ([#12466](https://github.com/apache/maven/pull/12466)) @gnodet
- Bugfix: use GAV and not GAPV in source labels for profiles ([#12424](https://github.com/apache/maven/pull/12424)) @cstamas
- Install JUL-to-SLF4J bridge to route java.util.logging through Maven logging ([#12345](https://github.com/apache/maven/pull/12345)) @gnodet
- Fix CI-friendly ${revision} not interpolated for non-build POM reads ([#12322](https://github.com/apache/maven/pull/12322)) @gnodet
- Fix mvn.cmd jvm.config read failing silently on Windows CI ([#12379](https://github.com/apache/maven/pull/12379)) @gnodet
- Fix mvnup spurious pluginManagement injection for remote parent plugins ([#12351](https://github.com/apache/maven/pull/12351)) @gnodet
- [[MNG-8650]](https://issues.apache.org/jira/browse/MNG-8650) - Fix MAVEN_ARGS backslash stripping on Windows ([#12349](https://github.com/apache/maven/pull/12349)) @gnodet
- Do not force metadata download for plugin prefix resolution ([#12343](https://github.com/apache/maven/pull/12343)) @gnodet
- Fix NPE in DefaultLookup.lookupOptional() when container returns null ([#12340](https://github.com/apache/maven/pull/12340)) @gnodet
- Use stack-passed Set for activeModelReads cycle detection ([#12325](https://github.com/apache/maven/pull/12325)) @gnodet
- Sync DefaultTypeProvider and dependency-types docs across impl and compat ([#12326](https://github.com/apache/maven/pull/12326)) @gnodet
- Fix StackOverflowError with internal parent and CI-friendly revision ([#12314](https://github.com/apache/maven/pull/12314)) @gnodet
- Add -P !profile deactivation regression guard ([#12315](https://github.com/apache/maven/pull/12315)) @gnodet
- Fix #12305: filter uninterpolated deps in ArtifactDescriptorReaderDelegate ([#12309](https://github.com/apache/maven/pull/12309)) @gnodet
- Fix #12304: replace deprecated property expressions in mvnup ([#12308](https://github.com/apache/maven/pull/12308)) @gnodet
- Fix #12306: normalize targetPath in DefaultSourceRoot ([#12307](https://github.com/apache/maven/pull/12307)) @gnodet
- Pass settings.xml profile properties to LRM ([#12299](https://github.com/apache/maven/pull/12299)) @gnodet
- Fix thread-safety in DefaultModelValidator ([#12284](https://github.com/apache/maven/pull/12284)) @gnodet
- Fix MojoExtension.beforeEach to use merged model instead of raw parsed model ([#12287](https://github.com/apache/maven/pull/12287)) @gnodet
- Fix BUILD_CONSUMER profile activation for locally-resolved parent POMs ([#12286](https://github.com/apache/maven/pull/12286)) @gnodet
- Fix NPE in DefaultModelBuilder when POM resolved from repository ([#12285](https://github.com/apache/maven/pull/12285)) @gnodet
- Tokenize arithmetic operators as delimiters in ConditionParser ([#12275](https://github.com/apache/maven/pull/12275)) @gnodet
- Support sealed parameter implementation hints ([#12258](https://github.com/apache/maven/pull/12258)) @gnodet
- Fix ConditionParser to handle newlines before && operator ([#12259](https://github.com/apache/maven/pull/12259)) @gnodet
- Handle missing package metadata in model ids ([#12257](https://github.com/apache/maven/pull/12257)) @gnodet
- Fix #11715: preserve 4.1.0 namespace/schema in help:effective-pom ([#12255](https://github.com/apache/maven/pull/12255)) @gnodet
- Add XmlService classloader fallback for ServiceLoader discovery ([#12254](https://github.com/apache/maven/pull/12254)) @gnodet
- Avoid reflective InputSource modelId mutation ([#12147](https://github.com/apache/maven/pull/12147)) @Will-thom
- Fix reportSet inheritance in Maven 4 model building ([#12245](https://github.com/apache/maven/pull/12245)) @hboutemy
- Fail-fast consumer POM validation for non-4.0.0 model versions ([#12236](https://github.com/apache/maven/pull/12236)) @gnodet
- Fix Source.targetPath incorrectly aligned to basedir ([#12235](https://github.com/apache/maven/pull/12235)) @gnodet
- Fix #11856: Improve error message for prefix-based remote repository filtering errors ([#12234](https://github.com/apache/maven/pull/12234)) @gnodet
- Fix #11796: Preserve default-phases bindings for standard lifecycle phases ([#12233](https://github.com/apache/maven/pull/12233)) @gnodet
- Fix @PreDestroy ClassNotFoundException from premature ClassRealm disposal ([#12232](https://github.com/apache/maven/pull/12232)) @gnodet
- Re-enable integration test for nested import scope repository override ([#12231](https://github.com/apache/maven/pull/12231)) @gnodet
- Fix deadlocks in request cache ([#12166](https://github.com/apache/maven/pull/12166)) @gnodet
- Fix logging setup/teardown order ([#12137](https://github.com/apache/maven/pull/12137)) @gnodet
- Fix domtrip API breakage after 1.5.1 upgrade ([#12138](https://github.com/apache/maven/pull/12138)) @gnodet
- Update binary distribution LICENSE with complete Apache License 2.0 text ([#12116](https://github.com/apache/maven/pull/12116)) @slawekjaranowski
- Fix #11899: Default addLocationInformation to false in Settings and Toolchains XML writers ([#12123](https://github.com/apache/maven/pull/12123)) @gnodet
- Fix mvn script expanding ${...} in CLI arguments ([#12095](https://github.com/apache/maven/pull/12095)) @gnodet
- Fix consumer POM serialization of prefixed XML attributes ([#12110](https://github.com/apache/maven/pull/12110)) @gnodet
- Fix #11885: Disable ANSI colors when stdout is piped on JDK 22+ ([#12111](https://github.com/apache/maven/pull/12111)) @gnodet
- Fix #12085: regression tests for version inheritance from remote parent ([#12101](https://github.com/apache/maven/pull/12101)) @gnodet
- Fix #12074: prevent false parent cycle with shade plugin's dependency-reduced-pom.xml ([#12079](https://github.com/apache/maven/pull/12079)) @gnodet
- Fix #12075: skip expression validation for distributionManagement repository IDs ([#12077](https://github.com/apache/maven/pull/12077)) @gnodet
- Restore buildConfiguration() callback in deprecated build() methods ([#12094](https://github.com/apache/maven/pull/12094)) @gnodet
- Propagate addResource() to model Build for Maven 3 compat ([#12093](https://github.com/apache/maven/pull/12093)) @gnodet
- Filter transitive repositories with uninterpolated IDs ([#12070](https://github.com/apache/maven/pull/12070)) @gnodet
- Downgrade plexus-classworlds from 2.11.0 to 2.9.0 ([#12092](https://github.com/apache/maven/pull/12092)) @gnodet
- Fix #11920: skip expression validation for profile repository URLs ([#12055](https://github.com/apache/maven/pull/12055)) @gnodet
- Remove extracted Mac OS JLine binaries from Maven distro ([#11997](https://github.com/apache/maven/pull/11997)) @kwin
- Fix issue #11827 - Maven DI crashes if the file org.apache.maven.api.di.Inject contains empty lines ([#11830](https://github.com/apache/maven/pull/11830)) @slawekjaranowski
- Backport the simplification and fixes of PathSelector ([#11565](https://github.com/apache/maven/pull/11565)) @desruisseaux
- Fix special characters in .mvn/jvm.config ([#11537](https://github.com/apache/maven/pull/11537)) @gnodet
- Improve DefaultModelProcessor error reporting for alternative parsers ([#11529](https://github.com/apache/maven/pull/11529)) @gnodet
- Allow ${project.basedir} in profile activation.condition ([#11528](https://github.com/apache/maven/pull/11528)) @gnodet
- Fix profile source tracking in multi-module projects ([#11466](https://github.com/apache/maven/pull/11466)) @gnodet
- Fix BOM packaging in consumer POMs ([#11464](https://github.com/apache/maven/pull/11464)) @gnodet
- Fix field accessibility leak in EnhancedCompositeBeanHelper ([#11433](https://github.com/apache/maven/pull/11433)) @gnodet
- Fix a `ConcurrentModificationException` ([#11429](https://github.com/apache/maven/pull/11429)) @desruisseaux

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-4.0.0-rc-6).
