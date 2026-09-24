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

# Apache Maven 4.0.0-rc-7 Release Notes

Apache Maven 4.0.0-rc-7 is available for download.

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.

The core release is independent of plugin releases. Further releases of plugins will be made separately.

If you have any questions, please consult:

- the website: https://maven.apache.org/
- the maven-user mailing list: https://maven.apache.org/mailing-lists.html
- the reference documentation: https://maven.apache.org/ref/4.0.0-rc-7/

## Maven 4.x

Maven 4.x is a major release that includes significant improvements and changes.
Please refer to the [Maven 4.x documentation](https://maven.apache.org/ref/4.0.0-rc-7/) for detailed information about new features and migration guidance.

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

### Upgrading from Maven 4.0.0-rc-6

This release candidate is expected to be the last before the Maven 4.0.0 GA release, which we aim to publish in the coming weeks, pending community feedback.

This release contains a large number of bug fixes and improvements since rc-6.

## Changelog

Maven 4.0.0-rc-7 continues gathering feedback before releasing Maven 4.0.0 GA. This release includes a large number of bug fixes and improvements, with a focus on stability and compatibility.

## 🚀 New features and improvements

- Externalize default lifecycle plugin versions to POM properties ([#13137](https://github.com/apache/maven/pull/13137)) @gnodet
- Improve standalone ApiRunner to properly apply settings ([#13014](https://github.com/apache/maven/pull/13014)) @gnodet
- Strip executable() conditions from consumer POMs ([#12960](https://github.com/apache/maven/pull/12960)) @gnodet
- Log API enhancements and mojo MDC ([#12690](https://github.com/apache/maven/pull/12690)) @gnodet
- Emit clear error when running JDK cannot compile source level ([#12922](https://github.com/apache/maven/pull/12922)) @gnodet
- Optimize reactor sort and phase comparator performance ([#12901](https://github.com/apache/maven/pull/12901)) @gnodet
- Enable PathConflictResolver by default ([#12893](https://github.com/apache/maven/pull/12893)) @gnodet
- Add AsyncDrainWriter to eliminate PrintWriter lock contention ([#12892](https://github.com/apache/maven/pull/12892)) @gnodet
- [mvnup] Add maven-war-plugin and maven-ear-plugin to plugin upgrade list ([#12685](https://github.com/apache/maven/pull/12685)) @gnodet
- Add validation for Plexus-based plugin dependency injection ([#12649](https://github.com/apache/maven/pull/12649)) @slawekjaranowski

## 🐛 Bug Fixes

- Fix #13191: add -Dmaven.maven3Personality hint to FATAL message for wrong parent relativePath ([#13202](https://github.com/apache/maven/pull/13202)) @gnodet
- Fix #13200: publish a complete set from MavenProject.getArtifacts ([#13203](https://github.com/apache/maven/pull/13203)) @gnodet
- Fix #13190: pre-build full reactor once using BUILD_PROJECT, drop temp dir in PluginUpgradeStrategy ([#13197](https://github.com/apache/maven/pull/13197)) @gnodet
- Fix #13192: PomInlinerTransformer fails when CI-friendly property is defined in POM ([#13195](https://github.com/apache/maven/pull/13195)) @gnodet
- Fix #13189: mvnup generates invalid JDK version constraint '(,-1]' ([#13199](https://github.com/apache/maven/pull/13199)) @gnodet
- Fix mvnup compatibility: jar-plugin 3.4.2, exec-plugin submodule coverage, toolchain warning ([#13179](https://github.com/apache/maven/pull/13179)) @gnodet
- Fixes #13135, ensure completude of the reactor summary but privilege failures to be last to stay human efficient ([#13167](https://github.com/apache/maven/pull/13167)) @gnodet
- Fix glob patterns in exists()/missing() profile conditions on Windows ([#13163](https://github.com/apache/maven/pull/13163)) @gnodet
- Fix #13084: sandbox profile activation context for external model builds ([#13158](https://github.com/apache/maven/pull/13158)) @gnodet
- Fix: implement Node.getRepository() via local repository manager lookup ([#13159](https://github.com/apache/maven/pull/13159)) @gnodet
- Fix #13100: honor repositories from legitimately-active external model profiles ([#13155](https://github.com/apache/maven/pull/13155)) @gnodet
- [[MNG-5146]](https://issues.apache.org/jira/browse/MNG-5146) - Fix parent relativePath mismatch check ([#13157](https://github.com/apache/maven/pull/13157)) @gnodet
- Lower-case the OS family before matching it against the family names ([#13154](https://github.com/apache/maven/pull/13154)) @gnodet
- Normalize lazy StAX parse failures in MavenXpp3Reader ([#13149](https://github.com/apache/maven/pull/13149)) @gnodet
- Evaluate profile activation conditions lazily ([#13144](https://github.com/apache/maven/pull/13144)) @gnodet
- [[MNG-6979]](https://issues.apache.org/jira/browse/MNG-6979) - Initialize the current project from the execution root ([#13143](https://github.com/apache/maven/pull/13143)) @gnodet
- Fix NPE on a recursive property reference in a list field ([#13145](https://github.com/apache/maven/pull/13145)) @gnodet
- [[MNG-6568]](https://issues.apache.org/jira/browse/MNG-6568) - Fix comparison cycles in version qualifiers ([#13113](https://github.com/apache/maven/pull/13113)) @gnodet
- [[MNG-8678]](https://issues.apache.org/jira/browse/MNG-8678) - Concurrent executor ignores java.lang.Error subclasses ([#13067](https://github.com/apache/maven/pull/13067)) @gnodet
- Fix #13068: make PluginDependenciesResolver methods default ([#13069](https://github.com/apache/maven/pull/13069)) @ascheman
- Fix filterByScope to expand lifecycle scopes for build ordering ([#13070](https://github.com/apache/maven/pull/13070)) @gnodet
- Fix getDispatchedPaths() leaking mutable nested lists ([#13057](https://github.com/apache/maven/pull/13057)) @gnodet
- [[MNG-8174]](https://issues.apache.org/jira/browse/MNG-8174) - Fix NPE when a property references itself in a plugin configuration ([#12935](https://github.com/apache/maven/pull/12935)) @elharo
- Fix #12985: Upgrade toolchains-plugin when adding select-jdk-toolchain execution ([#13026](https://github.com/apache/maven/pull/13026)) @gnodet
- Fix ToolchainPluginStrategy to detect inherited source levels from parent POMs ([#13025](https://github.com/apache/maven/pull/13025)) @gnodet
- Fix consumer POM leaving extension-contributed user properties uninterpolated ([#13023](https://github.com/apache/maven/pull/13023)) @gnodet
- Fix #12986: Throw descriptive UnsupportedOperationException instead of NPE in MavenSession.lookup() ([#13020](https://github.com/apache/maven/pull/13020)) @gnodet
- Fix #12989: mvnup plugin upgrades should respect project JDK version ([#13024](https://github.com/apache/maven/pull/13024)) @gnodet
- [[MNG-8633]](https://issues.apache.org/jira/browse/MNG-8633) - mvnup: inject standalone Nashorn for antrun JavaScript compatibility ([#13022](https://github.com/apache/maven/pull/13022)) @gnodet
- mvnup: upgrade BND plugins to fix ConcurrentModificationException on JDK 17.0.13+ ([#13021](https://github.com/apache/maven/pull/13021)) @gnodet
- [[MNG-8765]](https://issues.apache.org/jira/browse/MNG-8765) - Pre-interpolate plugin configuration before type conversion ([#13019](https://github.com/apache/maven/pull/13019)) @gnodet
- Fix #13004: enable deterministic profile activation for BUILD_CONSUMER ([#13015](https://github.com/apache/maven/pull/13015)) @gnodet
- [[MNG-8765]](https://issues.apache.org/jira/browse/MNG-8765) - Pre-interpolate plugin configuration before type conversion ([#13017](https://github.com/apache/maven/pull/13017)) @gnodet
- Fix #12991: skip shade-plugin upgrade when custom ResourceTransformers are present ([#13016](https://github.com/apache/maven/pull/13016)) @gnodet
- [[MNG-12984]](https://issues.apache.org/jira/browse/MNG-12984) - Fix invalid Automatic-Module-Name entries in mvnup plugin upgrades ([#13013](https://github.com/apache/maven/pull/13013)) @gnodet
- Fix compatibility spelling in CLI help text ([#13012](https://github.com/apache/maven/pull/13012)) @gnodet
- [[MNG-8708]](https://issues.apache.org/jira/browse/MNG-8708) - Fix parent inference ([#13010](https://github.com/apache/maven/pull/13010)) @gnodet
- Scope server credentials and validate legacy relocation coordinates ([#12977](https://github.com/apache/maven/pull/12977)) @gnodet
- Fix version comparison and parsing inconsistencies in maven-artifact ([#12942](https://github.com/apache/maven/pull/12942)) @slachiewicz
- Validate metadata inputs across legacy and new API paths, clone proxies before decryption ([#12945](https://github.com/apache/maven/pull/12945)) @slachiewicz
- [[MNG-8450]](https://issues.apache.org/jira/browse/MNG-8450) - Report BOM import warnings only at declaration sites ([#13003](https://github.com/apache/maven/pull/13003)) @gnodet
- [[MNG-8287]](https://issues.apache.org/jira/browse/MNG-8287) - Fix consumer POM packaging profile resolution ([#12967](https://github.com/apache/maven/pull/12967)) @gnodet
- Fix #12931: do not rewrite unmodified POMs in mvnup apply ([#12972](https://github.com/apache/maven/pull/12972)) @gnodet
- Fix modello velocity phase for concurrent builder compatibility ([#12963](https://github.com/apache/maven/pull/12963)) @gnodet
- Fix #12912: assign FastTerminal before starting the build thread ([#12961](https://github.com/apache/maven/pull/12961)) @gnodet
- Fix #12774: skip consumer POM re-attachment on repeated task segments ([#12916](https://github.com/apache/maven/pull/12916)) @gnodet
- Fix #12660: inherit version from imported BOM when local dep mgmt entry has no version ([#12915](https://github.com/apache/maven/pull/12915)) @gnodet
- Fix #12912: use a dumb fallback terminal for all build-thread reentrant calls ([#12921](https://github.com/apache/maven/pull/12921)) @gnodet
- Fix operator precedence skipping type=bom dependency imports ([#12930](https://github.com/apache/maven/pull/12930)) @gnodet
- Fix: make ProjectIndex thread-safe for parallel builds ([#12926](https://github.com/apache/maven/pull/12926)) @gnodet
- Fix #12602: Preserve Properties defaults in immutable copies ([#12910](https://github.com/apache/maven/pull/12910)) @goutamadwant
- [[MNG-8693]](https://issues.apache.org/jira/browse/MNG-8693) - Avoid resolving unused plugins for direct goals ([#12920](https://github.com/apache/maven/pull/12920)) @gnodet
- Fix: interpolate properties in module/subproject path before filesystem resolution ([#12924](https://github.com/apache/maven/pull/12924)) @gnodet
- Fix #12600: handle missing project index in forked executions ([#12917](https://github.com/apache/maven/pull/12917)) @gnodet
- Re-include macOS JLine native libraries in distribution ([#12888](https://github.com/apache/maven/pull/12888)) @gnodet
- Fix #2520: default maven.mainClass in Java instead of classworlds config ([#12889](https://github.com/apache/maven/pull/12889)) @gnodet
- Fix #12646: project-local-repo clean race condition when root pom has parent ([#12891](https://github.com/apache/maven/pull/12891)) @gnodet
- Wire ModelBuilderRequest.isLocationTracking() to XML parser ([#12905](https://github.com/apache/maven/pull/12905)) @gnodet
- Fix #12590: NPE on null rootDirectory in DefaultModelBuilder ([#12903](https://github.com/apache/maven/pull/12903)) @gnodet
- Fix #12749: suppress false ERROR in mvnsh for successful commands ([#12896](https://github.com/apache/maven/pull/12896)) @gnodet
- Fix #12769: merge duplicate mirror repos to preserve snapshot policies ([#12900](https://github.com/apache/maven/pull/12900)) @gnodet
- Fix #12868: Restore --define as long option for -D ([#12899](https://github.com/apache/maven/pull/12899)) @gnodet
- Fix #12592: use string-based ID for forked execution cycle detection ([#12898](https://github.com/apache/maven/pull/12898)) @gnodet
- Fix #12605: remove synchronized bottleneck in createProjectRealm() ([#12897](https://github.com/apache/maven/pull/12897)) @gnodet
- Fix #12607: prevent session scope leak on MavenChainedWorkspaceReader constructor exception ([#12895](https://github.com/apache/maven/pull/12895)) @gnodet
- Fix #12598: prevent StackOverflow from path representation mismatch in getEnhancedProperties ([#12894](https://github.com/apache/maven/pull/12894)) @gnodet
- Fix field cache to let child class fields take precedence over parent ([#12626](https://github.com/apache/maven/pull/12626)) @gnodet
- Fix #12685: Add tests for war/ear plugin upgrades, extract Quarkus tests ([#12876](https://github.com/apache/maven/pull/12876)) @gnodet
- Preserve coordinates when merging InputLocation ([#12806](https://github.com/apache/maven/pull/12806)) @gnodet
- Fix: synchronize ReflectionValueExtractor class-introspection caches ([#12862](https://github.com/apache/maven/pull/12862)) @gnodet
- Fix duplicate model problem reporting, fixes #12691 ([#12807](https://github.com/apache/maven/pull/12807)) @gnodet
- Fix ArtifactCoordinates.getId() double colon when classifier is empty ([#12855](https://github.com/apache/maven/pull/12855)) @gnodet
- [[MNG-8129]](https://issues.apache.org/jira/browse/MNG-8129) - Handle InvalidPathException for broken relativePath on Windows ([#12849](https://github.com/apache/maven/pull/12849)) @gnodet
- Fix #12761: do not wait for the terminal on the thread building it ([#12854](https://github.com/apache/maven/pull/12854)) @gnodet
- Make run-its profile extract the built distribution ([#12852](https://github.com/apache/maven/pull/12852)) @gnodet
- [[MDEP-590]](https://issues.apache.org/jira/browse/MDEP-590) - Resolve classified POM artifacts from the reactor ([#12831](https://github.com/apache/maven/pull/12831)) @wilx
- [[MNG-8709]](https://issues.apache.org/jira/browse/MNG-8709) - Use active profile properties for consumer POM validation ([#12684](https://github.com/apache/maven/pull/12684)) @goutamadwant
- Fix #12730: quiet mode implies raw streams to avoid stdout decoration ([#12830](https://github.com/apache/maven/pull/12830)) @gnodet
- Fix #12640: BOM consumer POM must not include inherited dependency management ([#12802](https://github.com/apache/maven/pull/12802)) @gnodet
- [[MNG-8129]](https://issues.apache.org/jira/browse/MNG-8129) - Handle InvalidPathException for broken relativePath on Windows ([#12741](https://github.com/apache/maven/pull/12741)) @gnodet
- Make profile activation conditions locale-independent ([#12798](https://github.com/apache/maven/pull/12798)) @gnodet
- Fix parent profile cache matching across activation contexts ([#12801](https://github.com/apache/maven/pull/12801)) @gnodet
- fix(api-core): properly implement Clock.withZone() in MonotonicClock ([#12797](https://github.com/apache/maven/pull/12797)) @gnodet
- Support the logical not operator in profile activation conditions ([#12799](https://github.com/apache/maven/pull/12799)) @gnodet
- Fix BuildPlanExecutor thread pool leak on constructor failure ([#12800](https://github.com/apache/maven/pull/12800)) @gnodet
- fix configuration page generation ([#12657](https://github.com/apache/maven/pull/12657)) @hboutemy
- Fix #12704: Do not report an independent, built module as skipped ([#12706](https://github.com/apache/maven/pull/12706)) @slachiewicz
- Fix SubList.get() accepting an index equal to its own size ([#12656](https://github.com/apache/maven/pull/12656)) @renechoi

## 📝 Documentation updates

- Fix default-bindings doc snippet to reference PluginVersions ([#13140](https://github.com/apache/maven/pull/13140)) @gnodet
- Fix #11504: add replacement guidance to deprecated model and settings APIs ([#12890](https://github.com/apache/maven/pull/12890)) @gnodet
- Port the site documentation from APT to Markdown ([#12711](https://github.com/apache/maven/pull/12711)) @slachiewicz
- consistency on Maven 4 vs 3 vs 2 references ([#12642](https://github.com/apache/maven/pull/12642)) @hboutemy

## 👻 Maintenance

- Fixes #13135: address review on reactor summary (UNKNOWN group, local StringBuilder) ([#13181](https://github.com/apache/maven/pull/13181)) @gnodet
- chore: add toolbox:plugin-versions workflow for lifecycle plugin versions ([#13172](https://github.com/apache/maven/pull/13172)) @gnodet
- Replace static TestSuiteOrdering registry with dynamic class orderer ([#13169](https://github.com/apache/maven/pull/13169)) @gnodet
- Implement getOutputPath() in the site IT plugin ([#13096](https://github.com/apache/maven/pull/13096)) @slachiewicz
- Update NOTICE file in distribution packages ([#13043](https://github.com/apache/maven/pull/13043)) @slawekjaranowski
- Optimize computeLocations() and simplify ModelObjectPool location handling ([#13042](https://github.com/apache/maven/pull/13042)) @gnodet
- deps: Bump Mimir to 0.12.3 (#13048) ([#13049](https://github.com/apache/maven/pull/13049)) @cstamas
- Restrict what a repository-resolved model contributes to the build ([#12943](https://github.com/apache/maven/pull/12943)) @slachiewicz
- Output, transport and consumer POM robustness fixes ([#12946](https://github.com/apache/maven/pull/12946)) @slachiewicz
- Validate coordinates and repository precedence during artifact resolution ([#12944](https://github.com/apache/maven/pull/12944)) @slachiewicz
- Fix flaky FastTerminalReentrancyTest by avoiding PTY creation ([#12971](https://github.com/apache/maven/pull/12971)) @gnodet
- Replace deprecated Terminal.getWidth/getHeight with getColumns/getRows ([#12969](https://github.com/apache/maven/pull/12969)) @gnodet
- [[MNG-8129]](https://issues.apache.org/jira/browse/MNG-8129) - Clarify relativePath validation comment ([#12965](https://github.com/apache/maven/pull/12965)) @gnodet
- Add integration test for #12660: BOM version from imported BOM ([#12940](https://github.com/apache/maven/pull/12940)) @gnodet
- Use try-with-resources for AuthenticationContext ([#12844](https://github.com/apache/maven/pull/12844)) @slachiewicz
- Fix deprecated Maven testing API compatibility ([#12683](https://github.com/apache/maven/pull/12683)) @goutamadwant
- Move the core IT suite off the JUnit 4 assertions ([#12719](https://github.com/apache/maven/pull/12719)) @slachiewicz
- Remove workaround for creating site output directory ([#12715](https://github.com/apache/maven/pull/12715)) @slawekjaranowski
- Build and deploy through the wrapper on Jenkins ([#12677](https://github.com/apache/maven/pull/12677)) @slachiewicz
- Build the wrapper job with Maven 4.0.0-rc-6 ([#12675](https://github.com/apache/maven/pull/12675)) @slachiewicz

## 📦 Dependency updates

- chore: update Maven lifecycle plugin versions ([#13187](https://github.com/apache/maven/pull/13187)) @[github-actions[bot]](https://github.com/apps/github-actions)
- Maven Resolver 2.0.23 ([#13168](https://github.com/apache/maven/pull/13168)) @cstamas
- Bump net.bytebuddy:byte-buddy from 1.18.13 to 1.18.14 ([#13178](https://github.com/apache/maven/pull/13178)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.mojo:build-helper-maven-plugin from 3.6.1 to 3.6.2 ([#13133](https://github.com/apache/maven/pull/13133)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.mojo:exec-maven-plugin from 3.6.3 to 3.6.4 ([#13131](https://github.com/apache/maven/pull/13131)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.mojo:buildnumber-maven-plugin from 3.3.0 to 3.3.1 ([#13128](https://github.com/apache/maven/pull/13128)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.mojo:extra-enforcer-rules from 1.12.0 to 1.12.1 ([#13123](https://github.com/apache/maven/pull/13123)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump jlineVersion from 4.4.2 to 4.4.3 ([#13109](https://github.com/apache/maven/pull/13109)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump actions/setup-java from 6.0.0 to 6.0.1 ([#13108](https://github.com/apache/maven/pull/13108)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.apache.maven.plugin-tools:maven-plugin-annotations from 3.15.2 to 3.16.0 ([#13088](https://github.com/apache/maven/pull/13088)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.apache.maven.plugin-tools:maven-plugin-tools-java from 3.15.2 to 3.16.0 ([#13090](https://github.com/apache/maven/pull/13090)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump slf4jVersion from 2.0.18 to 2.0.19 ([#13073](https://github.com/apache/maven/pull/13073)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump jlineVersion from 4.4.1 to 4.4.2 ([#13063](https://github.com/apache/maven/pull/13063)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.github.siom79.japicmp:japicmp-maven-plugin from 0.26.1 to 0.26.2 ([#13064](https://github.com/apache/maven/pull/13064)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.bytebuddy:byte-buddy from 1.18.12 to 1.18.13 ([#13065](https://github.com/apache/maven/pull/13065)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump jlineVersion from 4.4.0 to 4.4.1 ([#13038](https://github.com/apache/maven/pull/13038)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.modello:modello-maven-plugin from 2.8.0 to 2.8.1 ([#13029](https://github.com/apache/maven/pull/13029)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.sourceforge.pmd:pmd-core from 7.26.0 to 7.27.0 ([#12980](https://github.com/apache/maven/pull/12980)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump jlineVersion from 4.3.1 to 4.4.0 ([#12884](https://github.com/apache/maven/pull/12884)) @gnodet
- Bump actions/setup-java from 5.7.0 to 6.0.0 ([#12859](https://github.com/apache/maven/pull/12859)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-xml from 4.1.1 to 4.2.0 ([#12839](https://github.com/apache/maven/pull/12839)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Deps: Bump to Resolver 2.0.22 ([#12804](https://github.com/apache/maven/pull/12804)) @cstamas
- Bump org.codehaus.plexus:plexus-interactivity-api from 1.5.1 to 1.6.0 ([#12840](https://github.com/apache/maven/pull/12840)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.google.jimfs:jimfs from 1.3.1 to 1.3.2 ([#12838](https://github.com/apache/maven/pull/12838)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-testing from 2.1.0 to 2.2.0 ([#12820](https://github.com/apache/maven/pull/12820)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-interpolation from 1.29 to 1.30.0 ([#12824](https://github.com/apache/maven/pull/12824)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-utils from 3.6.0 to 3.6.2 ([#12826](https://github.com/apache/maven/pull/12826)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-classworlds from 2.12.0 to 2.12.1 ([#12827](https://github.com/apache/maven/pull/12827)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump eu.maveniverse.maven.mimir:testing from 0.12.0 to 0.12.1 ([#12727](https://github.com/apache/maven/pull/12727)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.google.guava:guava from 33.7.0-jre to 33.7.1-jre ([#12790](https://github.com/apache/maven/pull/12790)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-velocity from 2.3.0 to 2.4.0 ([#12788](https://github.com/apache/maven/pull/12788)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-sec-dispatcher from 4.1.0 to 4.2.0 ([#12791](https://github.com/apache/maven/pull/12791)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.modello:modello-maven-plugin from 2.7.0 to 2.8.0 ([#12789](https://github.com/apache/maven/pull/12789)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.google.guava:guava from 33.6.0-jre to 33.7.0-jre ([#12777](https://github.com/apache/maven/pull/12777)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.bytebuddy:byte-buddy from 1.18.11 to 1.18.12 ([#12773](https://github.com/apache/maven/pull/12773)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump eu.maveniverse.maven.plugins:bom-builder3 from 1.3.3 to 1.3.4 ([#12764](https://github.com/apache/maven/pull/12764)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump ch.qos.logback:logback-classic from 1.6.2 to 1.6.3 ([#12758](https://github.com/apache/maven/pull/12758)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump ch.qos.logback:logback-classic from 1.6.1 to 1.6.2 ([#12752](https://github.com/apache/maven/pull/12752)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.fasterxml.woodstox:woodstox-core from 7.2.1 to 7.2.2 ([#12689](https://github.com/apache/maven/pull/12689)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump xmlunitVersion from 2.12.0 to 2.13.0 ([#12672](https://github.com/apache/maven/pull/12672)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump actions/setup-java from 5.6.0 to 5.7.0 ([#12665](https://github.com/apache/maven/pull/12665)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump ch.qos.logback:logback-classic from 1.6.0 to 1.6.1 ([#12580](https://github.com/apache/maven/pull/12580)) @[dependabot[bot]](https://github.com/apps/dependabot)

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-4.0.0-rc-7).
