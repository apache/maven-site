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

# Common Bugs and Pitfalls

Maven is not the smallest project in terms of source code and has as such already suffered from many bugs. Having a closer look at all the issues revealed some coding problems that had widespread among the various subcomponents. This document lists these commonly occurring anti patterns in order to help the Maven community to prevent rather than fix bugs. Note that the primary focus is on pointing out problems that are subtle in their nature rather than giving a comprehensive guide for Java or Maven development.

<!-- MACRO{toc|section=0|fromDepth=2|toDepth=5} -->

## Reading and Writing Text Files

Textual content is composed of characters while file systems merely store byte streams. A file encoding (aka charset) is used to convert between bytes and characters. The challenge is using the right file encoding.

The JVM has this notion of a default encoding (given by the `file.encoding` property) which it derives from a system's locale. While this might be a convenient feature sometimes, using this default encoding for a project build is in general a bad idea: The build output will depend on the machine/developer who runs the build. As such, usage of the default encoding threatens the dream of a reproducible build.

For example, if developer A has UTF-8 as default encoding while developer B uses ISO-8859-1, text files are very likely to get messed up during resource filtering or similar tasks.

Therefore, developers should avoid any direct or indirect usage of the classes/methods that simply employ the platform's default encoding. For instance, `FileWriter` and `FileReader` should usually be avoided:

```unknown
/*
 * FIXME: This assumes the source file is using the platform's default encoding.
 */
Reader reader = new FileReader( javaFile );
```

Instead, the classes `OutputStreamWriter` and `OutputStreamReader` can be used in combination with an explicit encoding value. This encoding value can be retrieved from a mojo parameter such that the user can configure the plugin to fit his/her needs.

To save the user from configuring each plugin individually, conventions have been established that allow a user to centrally configure the file encoding per POM. Plugin developers should respect these conventions whereever possible:

- [Source File Encoding](https://cwiki.apache.org/confluence/display/MAVEN/POM+Element+for+Source+File+Encoding)
- [Report Output Encoding](http://cwiki.apache.org/confluence/display/MAVENOLD/Reporting+Encoding+Configuration)

Finally note that XML files require special handling because they are equipped with an encoding declaration in the XML prolog. Reading or writing XML files with an encoding that does not match their XML prolog's `encoding` attribute is a bad idea:

```unknown
/*
 * FIXME: This assumes the XML encoding declaration matches the platform's default encoding.
 */
Writer writer = new FileWriter( xmlFile );
writer.write( xmlContent );
```

To ease the correct processing of XML files, developers are encouraged to use [`ReaderFactory.newXmlReader()`](https://codehaus-plexus.github.io/plexus-utils/apidocs/org/codehaus/plexus/util/ReaderFactory.html#newXmlReader(java.io.File)) and [`WriterFactory.newXmlWriter()`](https://codehaus-plexus.github.io/plexus-utils/apidocs/org/codehaus/plexus/util/WriterFactory.html#newXmlWriter(java.io.File)) from the Plexus Utilities.

## Converting between URLs and Filesystem Paths

URLs and filesystem paths are really two different things and converting between them is not trivial. The main source of problems is that different encoding rules apply for the strings that make up a URL or filesystem path. For example, consider the following code snippet and its associated console output:

```unknown
File file = new File( "foo bar+foo" );
URL url = file.toURI().toURL();

System.out.println( file.toURL() );
> file:/C:/temp/foo bar+foo

System.out.println( url );
> file:/C:/temp/foo%20bar+foo

System.out.println( url.getPath() );
> /C:/temp/foo%20bar+foo

System.out.println( URLDecoder.decode( url.getPath(), "UTF-8" ) );
> /C:/temp/foo bar foo
```

First of all, please note that [`File.toURL()`](http://java.sun.com/javase/6/docs/api/java/io/File.html#toURL()) does not escape the space character (and others). This yields an invalid URL, as per [RFC 2396, section 2.4.3 "Excluded US-ASCII Characters"](http://www.faqs.org/rfcs/rfc2396.html). The class `java.net.URL` will silently accept such invalid URLs, in contrast `java.net.URI` will not (see also [`URL.toURI()`](http://java.sun.com/javase/6/docs/api/java/net/URL.html#toURI())). For this reason, `File.toURL()` has been deprecated and should be replaced with `File.toURI().toURL()`.

Next, [`URL.getPath()`](http://java.sun.com/javase/6/docs/api/java/net/URL.html#getPath()) does in general not return a string that can be used as a filesystem path. It returns a substring of the URL and as such can contain escape sequences. The prominent example is the space character which will show up as "%20". People sometimes hack around this by means of `replace("%20", " ")` but that does simply not cover all cases. It's worth to mention that on the other hand the related method [`URI.getPath()`](http://java.sun.com/javase/6/docs/api/java/net/URI.html#getPath()) does decode escapes but still the result is not a filesystem path (compare the source for the constructor `File(URI)`). To summarize, the following idiom is to be avoided:

```unknown
URL url = new URL( "file:/C:/Program%20Files/Java/bin/java.exe" );

/*
 * FIXME: This does not decode percent encoded characters.
 */
File path = new File( url.getPath() );
```

To decode a URL, people sometimes also choose [`java.net.URLDecoder`](http://java.sun.com/javase/6/docs/api/java/net/URLDecoder.html). The pitfall with this class is that is actually performs HTML form decoding which is yet another encoding and not the same as the URL encoding (compare the last paragraph in class javadoc about [`java.net.URL`](http://java.sun.com/javase/6/docs/api/java/net/URL.html)). For instance, a `URLDecoder` will erroneously convert the character "+" into a space as illustrated by the last sysout in the example above.

In an ideal world, code targetting JRE 1.4+ could easily avoid these problems by using the constructor [`File(URI)`](http://java.sun.com/javase/6/docs/api/java/io/File.html#File(java.net.URI)) as suggested by the following snippet:

```unknown
URL url = new URL( "file:/C:/Documents and Settings/user/.m2/settings.xml" );

/*
 * FIXME: This assumes the URL is fully compliant with RFC 3986.
 */
File path = new File( new URI( url.toExternalForm() ) );
```

The remaining source of frustration is the conversion from `URL` to `URI`. As already said, the `URL` class accepts malformed URLs which will make the constructor of `URI` throw an exception. And indeed, class loaders from Sun JREs up to Java 1.4 will deliver malformed URLs when queried for a resource. Likewise, the class loaders employed by Maven 2.x deliver malformed resource URLs regardless of the JRE version (see [MNG-3607](https://issues.apache.org/jira/browse/MNG-3607)).

For all these reasons, it is recommended to use [`FileUtils.toFile()`](http://commons.apache.org/io/api-release/org/apache/commons/io/FileUtils.html#toFile(java.net.URL)) from Commons IO or [`FileUtils.toFile()`](https://codehaus-plexus.github.io/plexus-utils/apidocs/org/codehaus/plexus/util/FileUtils.html#toFile(java.net.URL)) from a recent Plexus Utilities.

## Handling Strings Case-insensitively

When developers need to compare strings without regard to case or want to realize a map with case-insensitive string keys, they often employ [`String.toLowerCase()`](http://java.sun.com/javase/6/docs/api/java/lang/String.html#toLowerCase()) or [`String.toUpperCase()`](http://java.sun.com/javase/6/docs/api/java/lang/String.html#toUpperCase()) to create a "normalized" string before doing a simple `String.equals()`. Now, the `to*Case()` methods are overloaded: One takes no arguments and one takes a `Locale` object.

The gotcha with the arg-less methods is that their output depends on the default locale of the JVM but the default locale is out of control of the developer. That means the string expected by the developer (who runs/tests his code in a JVM using locale `xy`) does not necessarily match the string seen by another user (that runs a JVM with locale `ab`). For example, the comparison shown in the next code snippet is likely to fail for systems with default locale Turkish because Turkish has unusual casing rules for the characters "i" and "I":

```unknown
/*
 * FIXME: This assumes the casing rules of the current platform
 * match the rules for the English locale.
 */
if ( "info".equals( debugLevel.toLowerCase() ) )
    logger.info( message );
```

For case-insensitive string comparisons which should be locale-insensitive, the method [`String.equalsIgnoreCase()`](http://java.sun.com/javase/6/docs/api/java/lang/String.html#equalsIgnoreCase(java.lang.String)) should be used instead. If only a substring like a prefix/suffix should be compared, the method [`String.regionMatches()`](http://java.sun.com/javase/6/docs/api/java/lang/String.html#regionMatches(boolean,%20int,%20java.lang.String,%20int,%20int)) can be used instead.

If the usage of `String.to*Case()` cannot be avoided, the overloaded version taking a `Locale` object should be used, passing in [`Locale.ENGLISH`](http://java.sun.com/javase/6/docs/api/java/util/Locale.html#ENGLISH). The resulting code will still run on Non-English systems, the parameter only locks down the casing rules used for the string comparison such that the code delivers the same results on all platforms.

## Creating Resource Bundle Families

Especially reporting plugins employ resource bundles to support internationalization. One language (usually English) is provided as the fallback/default language in the base resource bundle. Due to the lookup strategy performed by [`ResourceBundle.getBundle()`](http://java.sun.com/javase/6/docs/api/java/util/ResourceBundle.html#getBundle(java.lang.String,%20java.util.Locale,%20java.lang.ClassLoader)), one must always provide a dedicated resource bundle for this default language, too. This bundle should be empty because it inherits the strings via the parent chain from the base bundle, but it must exist.

The following example illustrates this requirement. Imagine the broken resource bundle family shown below which is intended to provide localization for English, German and French:

```
src/
+- main/
   +- resources/
      +- mymojo-report.properties
      +- mymojo-report_de.properties
      +- mymojo-report_fr.properties
```

Now, if a resource bundle is to be looked up for English on a JVM whose default locale happens to be French, the bundle `mymojo-report_fr.properties` will be loaded instead of the intended bundle `mymojo-report.properties`.

Reporting plugins that suffer from this bug can easily be detected by executing `mvn site -D locales=xy,en` where `xy` denotes any other language code supported by the particular plugin. Specifying `xy` as the first locale will have the Maven Site Plugin change the JVM's default locale to `xy` which in turn causes the lookup for `en` to fail as outlined above unless the plugin has a dedicated resource bundle for English.

## Using System Properties

Maven's command line supports the definition of system properties via arguments of the form `-D key=value`. While these properties are called system properties, plugins should never use [`System.getProperty()`](http://java.sun.com/javase/6/docs/api/java/lang/System.html#getProperty(java.lang.String)) and related methods to query these properties. For example, the following code snippet will not work reliably when Maven is embedded, say into an IDE or a CI server:

```unknown
public MyMojo extends AbstractMojo
{
    public void execute()
    {
        /*
         * FIXME: This prevents proper embedding into IDEs or CI systems.
         */
        String value = System.getProperty( "maven.test.skip" );
    }
}
```

The problem is that the properties managed by the `System` class are global, i.e. shared among all threads in the current JVM. To prevent conflicts with other code running in the same JVM, Maven plugins should instead query the execution properties. These can be obtained from [`MavenSession.getExecutionProperties()`](https://maven.apache.org/ref/current/maven-core/apidocs/org/apache/maven/execution/MavenSession.html#getExecutionProperties()).

## Using Shutdown Hooks

People occasionally employ shutdown hooks to perform cleanup tasks, e.g. to delete temporary files as shown in the example below:

```unknown
public MyMojo extends AbstractMojo
{
    public void execute()
    {
        File tempFile = File.createTempFile( "temp", null );
        /*
         * FIXME: This assumes the JVM terminates soon after
         * the Maven build has finished.
         */
        tempFile.deleteOnExit();
    }
}
```

The problem is that the JVM executing Maven can be running much longer than the actual Maven build. Of course, this does not apply to the standalone invocation of Maven from the command line. However, it affects the embedded usage of Maven in IDEs or CI servers. In those cases, the cleanup tasks will be deferred, too. If the JVM is then executing a bunch of other Maven builds, many such cleanup tasks can sum up, eating up resources of the JVM.

For this reason, plugin developers should avoid usage of shutdown hooks and rather use `try`/`finally` blocks to perform cleanup as soon as the resources are no longer needed.

## Resolving Relative Paths

It is common practice for users of Maven to specify relative paths in the POM, not to mention that the Super POM does so, too. The intention is to resolve such relative paths against the base directory of the current project. In other words, the paths `target/classes` and `${project.basedir}/target/classes` should resolve to the same directory for a given POM.

Unfortunately, the class [`java.io.File`](http://java.sun.com/javase/6/docs/api/java/io/File.html) does not resolve relative paths against the project's base directory. As mentioned in its class javadoc, it resolves relative paths against the current working directory. In plain English: Unless a Maven component has complete control over the current working directory, any usage of `java.io.File` in combination with a relative path is a bug.

At first glance, one might be tempted to argue that the project base directory is equal to the current working directory. However, this assumption is generally not true. Consider the following scenarios:

1. Reactor Builds

   When a child module is build during a reactor build, the current working directory is usually the base directory of the parent project, not the base directory of the current module. That is the most common scenario where users are faced with the bug.

2. Embedded Maven Invocations

   Other tools, most notably IDEs, that run Maven under the hood may have set the current working directory to their installation directory or whatever they like.

3. Maven Invocations using the `-f` Switch

   While it is surely an uncommon use-case, the user is free to invoke Maven from an arbitrary working directory by specifying an absolute path like `mvn -f /home/me/projects/demo/pom.xml`.

Hence this example code is prone to misbehave:

```unknown
public MyMojo extends AbstractMojo
{
    /**
     * @parameter
     */
    private String outputDirectory;

    public void execute()
    {
        /*
         * FIXME: This will resolve relative paths like "target/classes" against
         * the user's working directory instead of the project's base directory.
         */
        File outputDir = new File( outputDirectory ).getAbsoluteFile();
    }
}
```

In order to guarantee reliable builds, Maven and its plugins must manually resolve relative paths against the project's base directory. A simple idiom like the following will do just fine:

```unknown
File file = new File( path );
if ( !file.isAbsolute() )
{
    file = new File( project.getBasedir(), file );
}
```

Many Maven plugins can get this resolution automatically if they declare their affected mojo parameters of type `java.io.File` instead of `java.lang.String`. This subtle difference in parameter types will trigger a feature known as _path translation_, i.e. the Maven core will automatically resolve relative paths when it pumps the XML configuration into a mojo.

## Determining the Output Directory for a Site Report

Most reporting plugins inherit from `AbstractMavenReport`. In doing so, they need to implement the inherited but abstract method `getOutputDirectory()`. To implement this method, plugins usually declare a field named `outputDirectory` which they return in the method. Nothing wrong so far.

Now, some plugins need to create additional files in the report output directory that accompany the report generated via the sink interface. While it is tempting to use either the method `getOutputDirectory()` or the field `outputDirectory` directly in order to setup a path for the output files, this leads most likely to a bug. More precisely, those plugins will not properly output files when run by the Maven Site Plugin as part of the site lifecycle. This is best noticed when the output directory for the site is configured directly in the Maven Site Plugin such that it deviates from the expression `${project.reporting.outputDirectory}` that the plugins use by default. Multi-language site generation is another scenario to exploit this bug which is illustrated below:

```unknown
public MyReportMojo extends AbstractMavenReport
{
    /**
     * @parameter default-value="${project.reporting.outputDirectory}"
     */
    private File outputDirectory;

    protected String getOutputDirectory()
    {
        return outputDirectory.getAbsolutePath();
    }

    public void executeReport( Locale locale )
    {
        /*
         * FIXME: This assumes the mojo parameter reflects the effective
         * output directory as used by the Maven Site Plugin.
         */
        outputDirectory.mkdirs();
    }
}
```

There are in principal two situations in which a report mojo could be invoked. The mojo might be run directly from the command line or the default build lifecycle or it might be run indirectly as part of the site generation along with other report mojos. The glaring difference between these two invocations is the way the output directory is controlled. In the first case, the parameter `outputDirectory` from the mojo itself is used. In the second case however, the Maven Site Plugin takes over control and will set the output directory according to its own configuration by calling `MavenReport.setReportOutputDirectory()` on the reports being generated.

Therefore, developers should always use `MavenReport.getReportOutputDirectory()` if they need to query the effective output directory for the report. The implementation of `AbstractMavenReport.getOutputDirectory()` is only intended as a fallback in case the mojo is not run as part of the site generation.

