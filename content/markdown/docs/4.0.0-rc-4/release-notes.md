# Apache Maven 4.0.0-rc-4 Release Notes

Apache Maven 4.0.0-rc-4 is available for download.

Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.

The core release is independent of plugin releases. Further releases of plugins will be made separately.

If you have any questions, please consult:

- the web site: https://maven.apache.org/
- the maven-user mailing list: https://maven.apache.org/mailing-lists.html
- the reference documentation: https://maven.apache.org/ref/4.0.0-rc-4/

## Maven 4.x

Maven 4.x is a major release that includes significant improvements and changes.
Please refer to the [Maven 4.x documentation](https://maven.apache.org/ref/4.0.0-rc-4/) for detailed information about new features and migration guidance.

### Important Notes

- This is a release candidate version intended for testing and feedback
- Please report any issues to the Maven JIRA: https://issues.apache.org/jira/projects/MNG
- For production use, consider the stability and compatibility requirements of your project

## Changelog

Maven 4.0.0-rc-4 aims at being the last release candidate before Maven 4.0.0 GA. We'll focus on fixing critical bugs raised before GA.
Worth mentioning is the new upgrade tool which can be used to fix your `pom.xml` files and make them usable in Maven 4.

## 🚀 New features and improvements

- [[MNG-8765]](https://issues.apache.org/jira/browse/MNG-8765) - Maven Upgrade Tool ([#2407](https://github.com/apache/maven/pull/2407)) @gnodet
- Maven4 "legacy" mode and more ([#2380](https://github.com/apache/maven/pull/2380)) @cstamas
- [[MNG-8759]](https://issues.apache.org/jira/browse/MNG-8759) - Restore toString method in DefaultJavaToolchain ([#2411](https://github.com/apache/maven/pull/2411)) @slawekjaranowski
- [[MNG-8572]](https://issues.apache.org/jira/browse/MNG-8572) - Support DI beans in build extensions ([#2274](https://github.com/apache/maven/pull/2274)) @gnodet
- [[MNG-8717]](https://issues.apache.org/jira/browse/MNG-8717) - Remove maven-plugin-plugin:addPluginArtifactMetadata from default binding ([#2294](https://github.com/apache/maven/pull/2294)) @slawekjaranowski

## 🐛 Bug Fixes

- Deduplicate filtered dependency graph ([#2493](https://github.com/apache/maven/pull/2493)) @alzimmermsft
- User properties are not interpolated for paths ([#2480](https://github.com/apache/maven/pull/2480)) @cstamas
- Move project mutation inside project lock ([#2474](https://github.com/apache/maven/pull/2474)) @cstamas
- [[MNG-8736]](https://issues.apache.org/jira/browse/MNG-8736) - Fix concurrency issue in model building with profile activation ([#2378](https://github.com/apache/maven/pull/2378)) @gnodet
- [[MNG-8767]](https://issues.apache.org/jira/browse/MNG-8767) - Add suppressed exceptions to BatchRequestException in AbstractRequestCache ([#2431](https://github.com/apache/maven/pull/2431)) @gnodet
- [[MNG-8746]](https://issues.apache.org/jira/browse/MNG-8746) - Preserve property insertion order in WrapperProperties ([#2404](https://github.com/apache/maven/pull/2404)) @gnodet
- [[MNG-8764]](https://issues.apache.org/jira/browse/MNG-8764) - Sort injected lists by @Priority annotation ([#2425](https://github.com/apache/maven/pull/2425)) @gnodet
- [[MNG-8761]](https://issues.apache.org/jira/browse/MNG-8761) - Add Maven 3 MavenToolchainsXpp3Reader/Writer ([#2412](https://github.com/apache/maven/pull/2412)) @slawekjaranowski
- [[MNG-8729]](https://issues.apache.org/jira/browse/MNG-8729) - Use correct `outputStream` destination; `request` instead of `path` in `DefaultPluginXmlFactory#write` ([#2312](https://github.com/apache/maven/pull/2312)) @Pankraz76
- [[MNG-8720]](https://issues.apache.org/jira/browse/MNG-8720) - Fix for symlinked project directory ([#2289](https://github.com/apache/maven/pull/2289)) @cstamas
- [[MNG-8653]](https://issues.apache.org/jira/browse/MNG-8653) - Fix 'all' phase and  add 'each' phase ([#2191](https://github.com/apache/maven/pull/2191)) @gnodet
- [[MNG-5668]](https://issues.apache.org/jira/browse/MNG-5668) - Execute after:* phases when build fails ([#2195](https://github.com/apache/maven/pull/2195)) @gnodet
- [[MNG-8629]](https://issues.apache.org/jira/browse/MNG-8629) - bugfix: If POM has parent and repository stanza, it is used un-interpolated ([#2192](https://github.com/apache/maven/pull/2192)) @cstamas
- [[MNG-8645]](https://issues.apache.org/jira/browse/MNG-8645) - Fix dependency management section of consumer POM ([#2183](https://github.com/apache/maven/pull/2183)) @gnodet
- [[MNG-8624]](https://issues.apache.org/jira/browse/MNG-8624) - Fix dependency not being set correctly for unsupported types ([#2153](https://github.com/apache/maven/pull/2153)) @gnodet
- Fix ITs referencing rc-3-SNAPSHOT ([#2164](https://github.com/apache/maven/pull/2164)) @gnodet
- [[MNG-8620]](https://issues.apache.org/jira/browse/MNG-8620) - Fix links in SVG ([#2152](https://github.com/apache/maven/pull/2152)) @kwin

## 📝 Documentation updates

- [[MNG-8731]](https://issues.apache.org/jira/browse/MNG-8731) - Use https for xsi:schemaLocation in generated descriptors ([#2341](https://github.com/apache/maven/pull/2341)) @slawekjaranowski

## 👻 Maintenance

- Fix integration tests to use correct Maven version instead of hardcoded 2.1-SNAPSHOT ([#2476](https://github.com/apache/maven/pull/2476)) @gnodet
- Use local repository in tests ([#2485](https://github.com/apache/maven/pull/2485)) @slawekjaranowski
- Refactor WrapperProperties template to remove caching and simplify implementation ([#2436](https://github.com/apache/maven/pull/2436)) @gnodet
- Ban "plain" Guice; downgrade it to 5.1.0 ([#2472](https://github.com/apache/maven/pull/2472)) @cstamas
- Unfurl CLI ([#2465](https://github.com/apache/maven/pull/2465)) @cstamas
- Don't ignore SVG files ([#2435](https://github.com/apache/maven/pull/2435)) @elharo
- Update CONTRIBUTING after GitHub issues enabled ([#2448](https://github.com/apache/maven/pull/2448)) @slawekjaranowski
- Remove extra variable ([#2398](https://github.com/apache/maven/pull/2398)) @elharo
- Enable Github Issues ([#2413](https://github.com/apache/maven/pull/2413)) @Bukama
- [S1161] Add missing @Override to overriding and implementing methods ([#2402](https://github.com/apache/maven/pull/2402)) @Pankraz76
- use `try-with-resources` statement in `LookupWagonMojo` ([#2426](https://github.com/apache/maven/pull/2426)) @Pankraz76
- Add UT for cache in FilteredProjectDependencyGraphTest ([#2394](https://github.com/apache/maven/pull/2394)) @slawekjaranowski
- [[MNG-8764]](https://issues.apache.org/jira/browse/MNG-8764) - centralize domain comparison logic `Binding#getPriorityComparator` ([#2428](https://github.com/apache/maven/pull/2428)) @Pankraz76
- evolve `Disabled` to `DisabledOnOs(WINDOWS)` ([#2423](https://github.com/apache/maven/pull/2423)) @Pankraz76
- use `try-with-resources` statement in `CatchMojo` ([#2424](https://github.com/apache/maven/pull/2424)) @Pankraz76
- chore: resolve unused `Binding#getDisplayString` ([#2429](https://github.com/apache/maven/pull/2429)) @Pankraz76
- Remove ignored method call ([#2399](https://github.com/apache/maven/pull/2399)) @elharo
- [[MNG-8750]](https://issues.apache.org/jira/browse/MNG-8750) - Delete unused interface ([#2388](https://github.com/apache/maven/pull/2388)) @elharo
- chore: resolve false negative suppression `@SuppressWarnings("checkstyle:UnusedLocalVariable")` ([#2369](https://github.com/apache/maven/pull/2369)) @Pankraz76
- [chore] Modernize codebase with Java improvements - Replace custom null checks with `Objects.requireNonNull` ([#2290](https://github.com/apache/maven/pull/2290)) @Pankraz76
- Symbolic change (no issue) ([#2383](https://github.com/apache/maven/pull/2383)) @cstamas
- [[MNG-8713]](https://issues.apache.org/jira/browse/MNG-8713) - SourceRoot.directory() default value should include the module when present ([#2278](https://github.com/apache/maven/pull/2278)) @desruisseaux
- [[MNG-8727]](https://issues.apache.org/jira/browse/MNG-8727) - Prepare for Java 24 ([#2328](https://github.com/apache/maven/pull/2328)) @cstamas
- [[MNG-8686]](https://issues.apache.org/jira/browse/MNG-8686) - Add `SourceRoot.matcher(boolean)` method ([#2236](https://github.com/apache/maven/pull/2236)) @desruisseaux
- Remove unused private methods ([#2310](https://github.com/apache/maven/pull/2310)) @Pankraz76
- Improve invoker test ([#2308](https://github.com/apache/maven/pull/2308)) @cstamas
- Pull #2282: Add project icon for `IntelliJ IDEA` ([#2283](https://github.com/apache/maven/pull/2283)) @Pankraz76
- [[MNG-8718]](https://issues.apache.org/jira/browse/MNG-8718) - Restore Maven 3 compat: ProjectBuilder is unusable for legacy code ([#2285](https://github.com/apache/maven/pull/2285)) @cstamas
- Test MavenITmng7587Jsr330 should be executed on every JDK ([#2298](https://github.com/apache/maven/pull/2298)) @slawekjaranowski
- [[MNG-8719]](https://issues.apache.org/jira/browse/MNG-8719) - Restore Maven3 compat: model setInherited(boolean) ([#2288](https://github.com/apache/maven/pull/2288)) @cstamas
- [[MNG-8614]](https://issues.apache.org/jira/browse/MNG-8614) - Maven Sisu in Maven 4 same as in Maven 3 ([#2284](https://github.com/apache/maven/pull/2284)) @cstamas
- [[MNG-8712]](https://issues.apache.org/jira/browse/MNG-8712) - dependency version is a requirement, not effective ([#2286](https://github.com/apache/maven/pull/2286)) @hboutemy
- [[MNG-8670]](https://issues.apache.org/jira/browse/MNG-8670) - Fix concurrent builder missing/wrong project events ([#2251](https://github.com/apache/maven/pull/2251)) @oehme
- [[MNG-8674]](https://issues.apache.org/jira/browse/MNG-8674) - Deprecate mergeId in the Maven 3 model and remove it in the new model ([#2233](https://github.com/apache/maven/pull/2233)) @gnodet
- [[MNG-8694]](https://issues.apache.org/jira/browse/MNG-8694) - Fix version interpolation and ternary operator ([#2272](https://github.com/apache/maven/pull/2272)) @kamilkrzywanski
- [[MNGSITE-393]](https://issues.apache.org/jira/browse/MNGSITE-393) - Remove Maven 1 and 2 references ([#2276](https://github.com/apache/maven/pull/2276)) @elharo
- Remove unneeded toString and valueOf calls ([#2230](https://github.com/apache/maven/pull/2230)) @elharo
- Drop unused assembly exclude ([#2266](https://github.com/apache/maven/pull/2266)) @cstamas
- aether repository system isn't used ([#2237](https://github.com/apache/maven/pull/2237)) @elharo
- remove unused field ([#2238](https://github.com/apache/maven/pull/2238)) @elharo
- Cleanup extra semicolons ([#2229](https://github.com/apache/maven/pull/2229)) @elharo
- [[MNG-8676]](https://issues.apache.org/jira/browse/MNG-8676) - Improve model builder error messages ([#2257](https://github.com/apache/maven/pull/2257)) @cstamas
- Avoid warning about unthrown exception ([#2217](https://github.com/apache/maven/pull/2217)) @elharo
- [[MNG-8687]](https://issues.apache.org/jira/browse/MNG-8687) - Restore ability to run on JIMFS ([#2256](https://github.com/apache/maven/pull/2256)) @cstamas
- [[MNG-8673]](https://issues.apache.org/jira/browse/MNG-8673) - SourceRoot includes and excludes should be String ([#2232](https://github.com/apache/maven/pull/2232)) @gnodet
- [[MNG-8675]](https://issues.apache.org/jira/browse/MNG-8675) - Correctly deprecate Resource in the model ([#2234](https://github.com/apache/maven/pull/2234)) @gnodet
- [[MNG-8685]](https://issues.apache.org/jira/browse/MNG-8685) - Better support for CI systems ([#2254](https://github.com/apache/maven/pull/2254)) @cstamas
- Add types for processor annotation path ([#2239](https://github.com/apache/maven/pull/2239)) @desruisseaux
- [[MNG-8679]](https://issues.apache.org/jira/browse/MNG-8679) - Align superpom with mvn3 ([#2246](https://github.com/apache/maven/pull/2246)) @cstamas
- Avoid exposing non-public type outside its scope ([#2220](https://github.com/apache/maven/pull/2220)) @elharo
- Grammar and typo fixes in javadoc and other comments ([#2222](https://github.com/apache/maven/pull/2222)) @elharo
- Remove methods that duplicate superclass methods ([#2211](https://github.com/apache/maven/pull/2211)) @elharo
- [[MNG-8637]](https://issues.apache.org/jira/browse/MNG-8637) - Pull out Standalone API from UTs ([#2226](https://github.com/apache/maven/pull/2226)) @gnodet
- [[MRESOLVER-673]](https://issues.apache.org/jira/browse/MRESOLVER-673) - Improve model validator error messages ([#2227](https://github.com/apache/maven/pull/2227)) @cstamas
- [[MNG-8660]](https://issues.apache.org/jira/browse/MNG-8660) - Decouple legacy prompter from implementation details ([#2196](https://github.com/apache/maven/pull/2196)) @oehme
- Add missing Javadoc to API subprojects ([#2218](https://github.com/apache/maven/pull/2218)) @gnodet
- prefer primitive type ([#2216](https://github.com/apache/maven/pull/2216)) @elharo
- autobox ([#2223](https://github.com/apache/maven/pull/2223)) @elharo
- No catch block needed when rethrowing same exception ([#2210](https://github.com/apache/maven/pull/2210)) @elharo
- [[MNG-3558]](https://issues.apache.org/jira/browse/MNG-3558) - Ensure properties can be escaped ([#2206](https://github.com/apache/maven/pull/2206)) @gnodet
- [[MNG-8669]](https://issues.apache.org/jira/browse/MNG-8669) - Add missing context ([#2224](https://github.com/apache/maven/pull/2224)) @cstamas
- [[MNG-8662]](https://issues.apache.org/jira/browse/MNG-8662) - Add missing methods for removing project source roots ([#2205](https://github.com/apache/maven/pull/2205)) @gnodet
- [[MNG-4559]](https://issues.apache.org/jira/browse/MNG-4559) - Support spaces in jvm.config ([#2213](https://github.com/apache/maven/pull/2213)) @gnodet
- prefer isEmpty ([#2215](https://github.com/apache/maven/pull/2215)) @elharo
- [CI] Disable Mimir JGroups node ([#2212](https://github.com/apache/maven/pull/2212)) @cstamas
- Access static fields in a static way ([#2209](https://github.com/apache/maven/pull/2209)) @elharo
- Make maven-cli module tests use Mimir as well ([#2207](https://github.com/apache/maven/pull/2207)) @cstamas
- Remove a few thousand lines of commented and obsolete code ([#2200](https://github.com/apache/maven/pull/2200)) @elharo
- minor cleanups ([#2199](https://github.com/apache/maven/pull/2199)) @elharo
- [[MNG-8467]](https://issues.apache.org/jira/browse/MNG-8467) - Add links to configuration pages in index.xml ([#2201](https://github.com/apache/maven/pull/2201)) @gnodet
- Add missing headers ([#2202](https://github.com/apache/maven/pull/2202)) @gnodet
- [[MNG-8598]](https://issues.apache.org/jira/browse/MNG-8598) - Add support for MAVEN_PROJECTBASEDIR substitution in jvm.config ([#2194](https://github.com/apache/maven/pull/2194)) @gnodet
- [[MNG-8548]](https://issues.apache.org/jira/browse/MNG-8548) - Add cycle detection to dependency injection ([#2197](https://github.com/apache/maven/pull/2197)) @gnodet
- Remove some unnecessary statements ([#2190](https://github.com/apache/maven/pull/2190)) @elharo
- Add test to suite ([#2198](https://github.com/apache/maven/pull/2198)) @gnodet
- [[MNG-8642]](https://issues.apache.org/jira/browse/MNG-8642) - Integrate the testing framework inside Maven Core ([#2193](https://github.com/apache/maven/pull/2193)) @gnodet
- [[MNG-8648]](https://issues.apache.org/jira/browse/MNG-8648) - Concurrent executor fires wrong ProjectStarted event ([#2189](https://github.com/apache/maven/pull/2189)) @oehme
- [[MNG-8647]](https://issues.apache.org/jira/browse/MNG-8647) - Set the default source directory to `src/${scope}/${lang}` as per documentation ([#2180](https://github.com/apache/maven/pull/2180)) @desruisseaux
- [[MNG-8646]](https://issues.apache.org/jira/browse/MNG-8646) - Make IT tests use "outer" Mimir caching as well ([#2176](https://github.com/apache/maven/pull/2176)) @cstamas
- Remove redundant code ([#2188](https://github.com/apache/maven/pull/2188)) @elharo
- Expected argument comes first in JUnit ([#2187](https://github.com/apache/maven/pull/2187)) @elharo
- Reenable many old style tests ([#2182](https://github.com/apache/maven/pull/2182)) @elharo
- [[MNG-8490]](https://issues.apache.org/jira/browse/MNG-8490) - Add an XmlService and a builder + implementation for XmlNode ([#2150](https://github.com/apache/maven/pull/2150)) @gnodet
- requireNonNull ([#2181](https://github.com/apache/maven/pull/2181)) @elharo
- Remove old symbolic link in ITmng8400 test ([#2178](https://github.com/apache/maven/pull/2178)) @slawekjaranowski
- [[MNG-8612]](https://issues.apache.org/jira/browse/MNG-8612) - Document Node#asString and remove Version#asString and VersionConstraint#asString ([#2172](https://github.com/apache/maven/pull/2172)) @gnodet
- [[MNG-8510]](https://issues.apache.org/jira/browse/MNG-8510) - Avoid duplicate Utils classes ([#2173](https://github.com/apache/maven/pull/2173)) @gnodet
- [[MNG-8639]](https://issues.apache.org/jira/browse/MNG-8639) - Check namespaces when reading XML models ([#2170](https://github.com/apache/maven/pull/2170)) @gnodet
- [[MNG-8511]](https://issues.apache.org/jira/browse/MNG-8511) - Explains the meaning of cling ([#2174](https://github.com/apache/maven/pull/2174)) @gnodet
- [IT] Refactor executor ([#2175](https://github.com/apache/maven/pull/2175)) @cstamas
- [[MNG-8633]](https://issues.apache.org/jira/browse/MNG-8633) - Initialize entities map lazily ([#2165](https://github.com/apache/maven/pull/2165)) @gnodet
- Replace a `NullPointerException` by a more explicit error message ([#2160](https://github.com/apache/maven/pull/2160)) @desruisseaux
- Restore Jenkins build for master ([#2151](https://github.com/apache/maven/pull/2151)) @olamy
- [experiment] Mimir ([#2144](https://github.com/apache/maven/pull/2144)) @cstamas
- [[MNG-8615] [MNG-8616]](https://issues.apache.org/jira/browse/MNG-8615] [MNG-8616) - Maven core extensions handling improvements ([#2147](https://github.com/apache/maven/pull/2147)) @cstamas
- Minor Javadoc cleanup ([#2126](https://github.com/apache/maven/pull/2126)) @elharo
- [[MNG-8613]](https://issues.apache.org/jira/browse/MNG-8613) - Copy edit dependency javadocs ([#2143](https://github.com/apache/maven/pull/2143)) @elharo

## 🔧 Build

- Improve configuration for dependabot in 3.x branch ([#2447](https://github.com/apache/maven/pull/2447)) @slawekjaranowski
- Remove branch after merge ([#2323](https://github.com/apache/maven/pull/2323)) @slawekjaranowski

## 📦 Dependency updates

- Bump xmlunitVersion from 2.10.2 to 2.10.3 ([#2501](https://github.com/apache/maven/pull/2501)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit:junit-bom from 5.13.0 to 5.13.1 ([#2459](https://github.com/apache/maven/pull/2459)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit.jupiter:junit-jupiter from 5.13.0 to 5.13.1 ([#2460](https://github.com/apache/maven/pull/2460)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.mojo:build-helper-maven-plugin from 3.6.0 to 3.6.1 ([#2433](https://github.com/apache/maven/pull/2433)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit.jupiter:junit-jupiter from 5.12.2 to 5.13.0 ([#2420](https://github.com/apache/maven/pull/2420)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.sourceforge.pmd:pmd-core from 7.13.0 to 7.14.0 ([#2421](https://github.com/apache/maven/pull/2421)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit:junit-bom from 5.12.2 to 5.13.0 ([#2422](https://github.com/apache/maven/pull/2422)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Update Mimir to 0.7.8 ([#2416](https://github.com/apache/maven/pull/2416)) @cstamas
- Bump org.codehaus.mojo:exec-maven-plugin from 3.5.0 to 3.5.1 ([#2397](https://github.com/apache/maven/pull/2397)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump jlineVersion from 3.30.3 to 3.30.4 ([#2405](https://github.com/apache/maven/pull/2405)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.fasterxml.woodstox:woodstox-core from 7.1.0 to 7.1.1 ([#2409](https://github.com/apache/maven/pull/2409)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Upgrade Maven Bom Builder Plugin ([#2408](https://github.com/apache/maven/pull/2408)) @cstamas
- [[MNG-8737]](https://issues.apache.org/jira/browse/MNG-8737) - Bump xmlunitVersion from 2.10.1 to 2.10.2 ([#2391](https://github.com/apache/maven/pull/2391)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8740][MNG-8733]](https://issues.apache.org/jira/browse/MNG-8740][MNG-8733) - Bump jlineVersion from 3.30.0 to 3.30.3 ([#2379](https://github.com/apache/maven/pull/2379)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8739]](https://issues.apache.org/jira/browse/MNG-8739) - Bump net.sourceforge.pmd:pmd-core from 7.12.0 to 7.13.0 ([#2335](https://github.com/apache/maven/pull/2335)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8738]](https://issues.apache.org/jira/browse/MNG-8738) - Bump mockitoVersion from 5.17.0 to 5.18.0 ([#2362](https://github.com/apache/maven/pull/2362)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8737]](https://issues.apache.org/jira/browse/MNG-8737) - Bump xmlunitVersion from 2.10.0 to 2.10.1 ([#2355](https://github.com/apache/maven/pull/2355)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8728]](https://issues.apache.org/jira/browse/MNG-8728) - Update Eclipse Sisu to 0.9.0.M4 ([#2358](https://github.com/apache/maven/pull/2358)) @cstamas
- [[MNG-8732]](https://issues.apache.org/jira/browse/MNG-8732) - Bump Resolver to 2.0.9 ([#2352](https://github.com/apache/maven/pull/2352)) @cstamas
- [[MNG-8726]](https://issues.apache.org/jira/browse/MNG-8726) - Bump jlineVersion from 3.29.0 to 3.30.0 ([#2325](https://github.com/apache/maven/pull/2325)) @cstamas
- Bump org.eclipse.jetty:jetty-server from 9.4.55.v20240627 to 9.4.56.v20240826 in /its/core-it-suite ([#2305](https://github.com/apache/maven/pull/2305)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump commons-jxpath:commons-jxpath from 1.3 to 1.4.0 ([#2267](https://github.com/apache/maven/pull/2267)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Update Maveniverse stuff ([#2269](https://github.com/apache/maven/pull/2269)) @cstamas
- Bump com.google.guava:guava from 33.4.7-jre to 33.4.8-jre ([#2265](https://github.com/apache/maven/pull/2265)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-velocity from 2.2.0 to 2.2.1 ([#2259](https://github.com/apache/maven/pull/2259)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit:junit-bom from 5.12.1 to 5.12.2 ([#2260](https://github.com/apache/maven/pull/2260)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump commons-io:commons-io from 2.18.0 to 2.19.0 ([#2261](https://github.com/apache/maven/pull/2261)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit.jupiter:junit-jupiter from 5.11.4 to 5.12.2 ([#2262](https://github.com/apache/maven/pull/2262)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.google.guava:guava from 33.4.6-jre to 33.4.7-jre ([#2263](https://github.com/apache/maven/pull/2263)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8677]](https://issues.apache.org/jira/browse/MNG-8677) - Bump to Resolver 2.0.8 and more ([#2158](https://github.com/apache/maven/pull/2158)) @cstamas
- Bump org.codehaus.plexus:plexus-classworlds from 2.8.0 to 2.9.0 ([#2247](https://github.com/apache/maven/pull/2247)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump io.github.olamy.maven.plugins:jacoco-aggregator-maven-plugin from 1.0.1 to 1.0.2 ([#2248](https://github.com/apache/maven/pull/2248)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-testing from 1.4.0 to 1.5.0 ([#2249](https://github.com/apache/maven/pull/2249)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-interactivity-api from 1.3 to 1.4 ([#2250](https://github.com/apache/maven/pull/2250)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-xml from 4.0.4 to 4.1.0 ([#2242](https://github.com/apache/maven/pull/2242)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump io.github.olamy.maven.plugins:jacoco-aggregator-maven-plugin from 1.0.0 to 1.0.1 ([#2244](https://github.com/apache/maven/pull/2244)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump mockitoVersion from 5.16.1 to 5.17.0 ([#2245](https://github.com/apache/maven/pull/2245)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.codehaus.plexus:plexus-interpolation from 1.27 to 1.28 ([#2243](https://github.com/apache/maven/pull/2243)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.jacoco:jacoco-maven-plugin from 0.8.12 to 0.8.13 ([#2225](https://github.com/apache/maven/pull/2225)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.bytebuddy:byte-buddy from 1.17.4 to 1.17.5 ([#2214](https://github.com/apache/maven/pull/2214)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8663]](https://issues.apache.org/jira/browse/MNG-8663) - Bump asmVersion from 9.7.1 to 9.8 ([#2204](https://github.com/apache/maven/pull/2204)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump Mimir from 0.4.0 to 0.4.1 ([#2203](https://github.com/apache/maven/pull/2203)) @cstamas
- Bump com.google.guava:guava from 33.4.5-jre to 33.4.6-jre ([#2186](https://github.com/apache/maven/pull/2186)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump net.bytebuddy:byte-buddy from 1.17.2 to 1.17.4 ([#2179](https://github.com/apache/maven/pull/2179)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8641]](https://issues.apache.org/jira/browse/MNG-8641) - Bump org.apache.maven:maven-parent from 43 to 44 ([#2162](https://github.com/apache/maven/pull/2162)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump com.google.guava:guava from 33.4.0-jre to 33.4.5-jre ([#2166](https://github.com/apache/maven/pull/2166)) @[dependabot[bot]](https://github.com/apps/dependabot)
- [[MNG-8630]](https://issues.apache.org/jira/browse/MNG-8630) - Bump ch.qos.logback:logback-classic from 1.5.17 to 1.5.18 ([#2161](https://github.com/apache/maven/pull/2161)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump mockitoVersion from 5.16.0 to 5.16.1 ([#2156](https://github.com/apache/maven/pull/2156)) @[dependabot[bot]](https://github.com/apps/dependabot)
- Bump org.junit:junit-bom from 5.12.0 to 5.12.1 ([#2155](https://github.com/apache/maven/pull/2155)) @[dependabot[bot]](https://github.com/apps/dependabot)

## Full changelog

For a full list of changes, please refer to the [GitHub release page](https://github.com/apache/maven/releases/tag/maven-4.0.0-rc-4).
