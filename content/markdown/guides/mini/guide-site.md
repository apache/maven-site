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

# Creating a site

## Creating Content

The first step to creating your site is to create some content. In Maven, the site content is separated by format, as there are several available.

```
+- src/
   +- site/
      +- markdown/
      |  +- index.md
      |
      +- apt/
      |  +- content.apt
      |
      +- fml/
      |  +- general.fml
      |  +- faq.fml
      |
      +- xdoc/
      |  +- other.xml
      |
      +- xhtml/
      |  +- xhtml-content.xhtml
      |
      +- site.xml
```

You will notice there is now a `${project.basedir}/src/site` directory within which is contained a `site.xml` site descriptor along with various directories corresponding to the supported document types.

Let's take a look at the examples of the various document types:

- `markdown`: the well known [Markdown](https://en.wikipedia.org/wiki/Markdown) format (supported since Maven Site Plugin 3.3), and the one this page is written in. It is what new documentation should use,
- `apt`: the APT format, "Almost Plain Text", a wiki-like format that predates Maven's support for Markdown. It is still supported, but the Maven project itself has moved its own documentation off it; see the [APT Format](/doxia/references/apt-format.html) reference if you are maintaining an existing page, and [Migrating APT to Markdown](/doxia/migrating-apt-to-markdown.html) if you would like to convert one,
- `fml`: the FML format is the [FAQ format](/doxia/references/fml-format.html),
- `xdoc`: an XML document conforming to a small and simple set of tags, see the [full reference](/doxia/references/xdoc-format.html),
- `xhtml`: plain XHTML documents.

Other formats are available, but at this point the first 4 are the best tested. Any other format for which a Doxia parser exists (see the list of [Doxia Markup Languages](/doxia/references/index.html)) can be used as well, but you need to add the corresponding Doxia module to the dependencies of the Maven Site Plugin.

There are also several possible output formats, but as of Maven Site Plugin, only XHTML5 is available.

Note that all of the above is optional - just one index file is required in one of the input trees. Each of the paths will be merged together to form the root directory of the site. After running `mvn site` on the example above you will end up with this in your `target` directory:

```
+- target/
   +- site/
      +- css/
      |
      +- images/
      |
      +- index.html
      +- content.html
      +- general.html
      +- faq.html
      +- other.html
      +- xhtml-content.html
```

This means that `src/site/apt/content.apt` is available in the site as `/content.html`, whichever directory it comes from. So, in your [site descriptor](/plugins/maven-site-plugin/examples/sitedescriptor.html) you link to `index.html` and `other.html`, where the sources of these two files are `src/site/markdown/index.md` and `src/site/xdoc/other.xml`.

The `css` and `images` directories contain the stylesheets and images of the skin being used.

## Customizing the Look &amp; Feel

If you want to tune the way your site looks, you can use a custom **skin** to provide your own CSS styles. If that is still not enough, you can even tweak the output templates that Maven uses to generate the site documentation.

You can visit the [Skins index](/skins/) to have a look at some of the skins that you can use to change the look of your site.

To build a skin of your own, see [Creating a skin](/skins/#Creating_a_skin).

## Generating the Site

Generating the site is very simple, and fast!

```
mvn site
```

By default, the resulting site will be in `target/site/...`

For more information on the Maven Site Plugin, see the [maven-site-plugin reference](../../plugins/maven-site-plugin/).

## Deploying the Site

### Classical Website deployment

To be able to deploy the site with a classical network protocol (ftp, scp, webdav), you must first declare a location to distribute to in your `pom.xml`, similar to the repository for deployment:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
  ...
  <distributionManagement>
    <site>
      <id>website</id>
      <url>scp://www.mycompany.com/www/docs/project/</url>
    </site>
  </distributionManagement>
  ...
</project>
```

- the `<id>` element identifies the repository, so that you can attach credentials to it in your `settings.xml` file using the [`<servers>` element](../../settings.html#Servers) as you would for any other repository,
- the `<url>` gives the location to deploy to. Currently, only SSH is supported by default, as above which copies to the host `www.mycompany.com` in the path `/www/docs/project/`, but you can [add more protocols as required](/plugins/maven-site-plugin/examples/adding-deploy-protocol.html). If subprojects inherit the site URL from a parent POM, they will automatically get their `<artifactId>` appended to form their effective deployment location.

Once distribution location is configured, deploying the site is done by using the `site-deploy` phase of the site lifecycle.

```
mvn site-deploy
```

### GitHub Pages, Apache svnpubsub/gitpubsub Deployment

When site publication is done with a SCM commit, like with [GitHub Pages](https://pages.github.com/) or [Apache svnpubsub/gitpubsub](https://infra.apache.org/project-site.html#tools), deploying the site will be done with [Maven SCM Publish Plugin](/plugins/maven-scm-publish-plugin/).

For example with a project hosted on GitHub and using GitHub Pages for its site publication:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-scm-publish-plugin</artifactId>
  <version>3.3.0</version>
  <configuration>
    <pubScmUrl>${project.scm.developerConnection}</pubScmUrl>
    <scmBranch>gh-pages</scmBranch>
  </configuration>
</plugin>
```

Deploying the site is done in 2 steps:

1. staging the content by using the `site` phase of the site lifecycle followed by `site:stage`: `mvn site site:stage`
2. publishing the staged site to the SCM: `mvn scm-publish:publish-scm`

## Creating a Site Descriptor

The `site.xml` file is used to describe the structure of the site. A sample is given below:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<site xmlns="http://maven.apache.org/SITE/2.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SITE/2.1.0 https://maven.apache.org/xsd/site-2.1.0.xsd"
  name="Maven">
  <bannerLeft href="https://maven.apache.org/">
    <image src="https://maven.apache.org/images/apache-maven-project.png" alt="Maven"/>
  </bannerLeft>

  <skin>
    <groupId>org.apache.maven.skins</groupId>
    <artifactId>maven-fluido-skin</artifactId>
    <version>2.1.0</version>
  </skin>

  <body>
    <links>
      <item name="Apache" href="https://www.apache.org/"/>
    </links>

    <menu name="Overview">
      <item name="Introduction" href="index.html"/>
      <item name="Download" href="download.html"/>
      <item name="Release Notes" href="release-notes.html"/>
    </menu>

    <menu ref="reports"/>

    ...
  </body>
</site>
```

<!--TODO: deserves more explanation.-->

**Note:** A skin is mandatory. Projects inheriting from the [Apache Parent POM](/pom/asf/) or from another parent that declares one inherit it and need no `<skin>` element of their own.

**Note:** The `<menu ref="reports"/>` element above. When building the site, this is replaced by a menu with links to all the reports that you have configured.

More information about the site descriptor is available at the [dedicated page of Maven Site Plugin](/plugins/maven-site-plugin/examples/sitedescriptor.html).

## Adding Extra Resources

You can add any arbitrary resource to your site by including them in a `resources` directory as shown below. Additional CSS files will be picked up when they are placed in the `css` directory within the `resources` directory.

```
+- src/
   +- site/
      +- resources/
         +- css/
         |  +- site.css
         |
         +- images/
            +- pic1.jpg
```

The file `site.css` will be added to the default XHTML5 output, so it can be used to adjust the default Maven stylesheets if desired.

The file `pic1.jpg` will be available via a relative reference to the `images` directory from any page in your site.

## Using Properties in Content

To add simple logic to your content, for example to filter properties, add a `.vm` extension to the file name (this is valid for all supported formats). [Apache Velocity](https://velocity.apache.org/) is the templating engine.

For example, the Maven website itself has a [`content/xdoc/download.xml.vm`](https://github.com/apache/maven-site/blob/master/content/xdoc/download.xml.vm) file, which uses the expression `${currentStableVersion}` to filter in a property set in the [POM](https://github.com/apache/maven-site/blob/master/pom.xml).

Some properties are defined by default: see [Doxia Site Renderer's Velocity processing reference](/doxia/doxia-sitetools/doxia-site-renderer/#Velocity_processing) for more information. For a complete usage overview, see Velocity's [user guide](https://velocity.apache.org/engine/2.3/user-guide.html).

Since version 3.5 of the Maven Site Plugin, you can save the processed markup by setting [`saveProcessedContent`](/plugins/maven-site-plugin/site-mojo.html#saveProcessedContent) to `true`.

### Common Issues and Workarounds

- **Dotted properties**: Velocity uses a dot notation to traverse beans, so you **cannot** use dotted properties defined in `<properties>` directly. Access them through the `$context` tool instead: for example `$context.get("my.property")`.
- **Markdown conflicts with Velocity on the `##` syntax**: `##` starts a [single line comment](https://velocity.apache.org/engine/2.3/vtl-reference.html#single-line-comments) in Velocity, so Markdown headings written with it are suppressed from the generated content. Use the [unparsed content syntax](https://velocity.apache.org/engine/2.3/vtl-reference.html#unparsed-content), `#[[## Heading]]#`, or the [escape tool](https://velocity.apache.org/tools/3.1/apidocs/org/apache/velocity/tools/generic/EscapeTool.html), `${esc.h}${esc.h} Heading`. The same tool writes a literal `$` as `${esc.d}`.

### Filtering with Maven Resources Plugin

Velocity has drawbacks for plain property interpolation:

- not everyone is familiar with the Velocity syntax,
- the web-based preview (for example on GitHub) does not work,
- Velocity and Markdown both use `##`, for different purposes, and
- the overhead is large for simple replacements.

For simple property interpolation, regular [Maven filtering](./guide-filtering-resources.html) is often more suitable. Create a dedicated source directory that [`maven-resources-plugin:copy-resources`](/plugins/maven-resources-plugin/copy-resources-mojo.html) processes before the site is created. In the following example, the directory `src/site` is copied with filtering to `target/site-src`:

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <!-- prepare site content by filtering ${project.*} values -->
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-resources-plugin</artifactId>
        <executions>
          <execution>
            <id>filter-site</id>
            <phase>pre-site</phase>
            <goals>
              <goal>copy-resources</goal>
            </goals>
            <configuration>
              <outputDirectory>${project.build.directory}/site-src</outputDirectory>
              <resources>
                <resource>
                  <directory>src/site</directory>
                  <filtering>true</filtering>
                </resource>
              </resources>
            </configuration>
          </execution>
        </executions>
      </plugin>
      ...
    </plugins>
  </build>
</project>
```

Then generate the site from the filtered sources by adjusting the [`siteDirectory`](/plugins/maven-site-plugin/site-mojo.html#siteDirectory) of the Maven Site Plugin. Also adjust the [`alternativeSiteSourceDirectories`](/plugins/maven-site-plugin/site-mojo.html#alternativeSiteSourceDirectories), which the edit buttons use:

```xml
<project>
  ...
  <build>
    <plugins>
      ...
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-site-plugin</artifactId>
        <configuration>
          <siteDirectory>${project.build.directory}/site-src</siteDirectory>
          <alternativeSiteSourceDirectories>${basedir}/src/site</alternativeSiteSourceDirectories>
        </configuration>
      </plugin>
      ...
    </plugins>
  </build>
</project>
```

## Multi-module Sites

Running `mvn site` on a multi-module project builds the site of every module individually, and relative links between module sites work only after you stage or deploy. See [Building multi-module sites](./guide-multimodule-site.html) for staging, deployment, site descriptor inheritance and aggregate reports.

## Configuring Reports

Maven has several reports that you can add to your web site to display the current state of the project. These reports take the form of plugins, just like those used to build the project.

There are many standard reports that are available by gleaning information from the POM. Currently what is provided by default are:

- Dependencies Report
- Mailing Lists
- Continuous Integration
- Source Repository
- Issue Tracking
- Project Team
- License

To find out more please refer to the [Project Info Reports Plugin](../../plugins/maven-project-info-reports-plugin/).

To add these reports to your site, you must add the Project Info Reports plugin to a special `<reporting>` section in the POM. The following example shows how to configure the standard project information reports that display information from the POM in a friendly format:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
  ...
  <reporting>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-project-info-reports-plugin</artifactId>
        <version>3.9.0</version><!-- define version here if not already defined in build/plugins or build/pluginManagement -->
      </plugin>
    </plugins>
  </reporting>
  ...
</project>
```

If you have included the appropriate `<menu ref="reports"/>` tag in your `site.xml` descriptor, then when you regenerate the site those items will appear in the menu.

Many other plugins provide reporting goals: look for "R" (Reporting) value in the "Type" column of the [list of plugins](/plugins/). When plugins are both Build and Reporting plugins, defining explicitly the version in the reporting section is usually not necessary since reporting will use the version from `build/plugins` or `build/pluginManagement`. Since Maven Site Plugin 3.4, reporting plugin also get configuration from `build/pluginManagement`.

**Note:** Many report plugins provide a parameter called `outputDirectory` or similar to specify the destination for their report outputs. This parameter is only relevant if the report plugin is run standalone, i.e. by invocation directly from the command line. In contrast, when reports are generated as part of the site, the configuration of the Maven Site Plugin will determine the effective output directory to ensure that all reports end up in a central location.

<!--TODO: explain report sets-->

## Internationalization

Internationalization in Maven is very simple, as long as the reports you are using have that particular locale defined. For an overview of supported languages and instructions on how to add further languages, see [Internationalization](/plugins/localization.html).

To enable multiple locales, add a configuration similar to the following to your POM:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-site-plugin</artifactId>
        <version>3.22.0</version>
        <configuration>
          <locales>default,fr</locales>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ...
</project>
```

This generates both a default (`Locale#ROOT`) and a French version of the site. The default version is at the root of the site, with a copy of the French translation in the `fr/` subdirectory.

To add your own content for that translation instead of using the default, place a subdirectory with that locale name in your site directory and create a new site descriptor with the locale in the file name. For example:

```
+- src/
   +- site/
      +- apt/
      |  +- index.apt     (Default version)
      |
      +- fr/
      |  +- apt/
      |     +- index.apt  (French version)
      |
      +- site.xml         (Default site descriptor)
      +- site_fr.xml      (French site descriptor)
```

With one site descriptor per language, the translated sites can evolve independently.
