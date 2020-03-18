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

*NOTE:* _This page contains drafts of user contributed FAQ entries. The content you see here might not be fully fool-proof or might not comply with the best practices promoted by Maven. What is only guaranteed is that they have worked once for some members. It is best to treat these items as "works in progress" until they have been reviewed and promoted to the main Maven documentation site._

Please follow the format that is being used because it will help in our automated extraction of material which can then be incorporated into the main site.

This page serves as a collection of questions *with* answers. If you have a frequently asked question that doesn't yet have an answer, please list that question on [the other page](FAQs].

# Answered Questions (Index)
### Reports & Site Docs

[How do I merge a list of configuration items in a parent POM with those in a child POM?](#How do I merge a list of configuration items in a parent POM with those in a child POM?)
[Why do I not get an index.html page generated for my project website?](#Why do I not get an index.html page generated for my project website?)
[How do I include/exclude the other modules in the navigation menu in the parent site?](#How do I include/exclude the other modules in the navigation menu in the parent site?)
[How do I create a report that does not require Doxia's Sink interface?](#How do I create a report that does not require Doxia's Sink interface?)
[How do I generate Maven plug-in sites, with pages that include an overview of the goals and parameters for each plug-in?](#How do I generate Maven plug-in sites, with pages that include an overview of the goals and parameters for each plug-in?)
[Is there a site.xml DTD?](#Is there a site.xml DTD?)
[How do I integrate static (x)html into a Maven site?](#How do I integrate static (x)html into a Maven site?)
[Where do I configure report plug-ins, like javadoc?](#Where do I configure report plug-ins, like javadoc?)
[Is there a way to get Maven to report the number of compile errors found?](#Is there a way to get Maven to report the number of compile errors found?)
[How do I add a description to the welcome page of the generated site when I execute mvn site?](#How do I add a description to the welcome page of the generated site when I execute mvn site?]
[Which part in pom.xml the javadoc plugin should be configured?](#Which part in pom.xml the javadoc plugin should be configured?]

### Eclipse

[How do I specify which output folders the Eclipse plugin puts into the .classpath file?](#How do I specify which output folders the Eclipse plugin puts into the .classpath file?]
[Where can I get the Maven plugin for Eclipse?](#Where can I get the Maven plugin for Eclipse?]
[I issued\- mvn \-Declipse.downloadSources=true eclipse eclipse goal. It created .classpath and .project for both modules, and in my local repository it downloaded sources Ho do I access them in eclipse?](#I issued- mvn -Declipse.downloadSources=true eclipse eclipse goal. It created .classpath and .project for both modules, and in my local repository it downloaded sources Ho do I access them in eclipse?]
[Is it possible that if I do mvn eclipse eclipse goal that my project would get disconnected from the subversion repository?](#Is it possible that if I do mvn eclipse eclipse goal that my project would get disconnected from the subversion repository?]
[Does it matter if the project's directory name is not the same as the artifactId?](#Does it matter if the project's directory name is not the same as the artifactId?]

### Plugins

[How do I resolve the " < plugin name > does not exist or no valid version " error?](#no-valid-version]
[How do I list available plugins?](#How do I list available plugins?]
[How do I get a plug-in's dependencies from a Mojo?](#How do I get a plug-in's dependencies from a Mojo?]
[How do I locate a required plug-in?](#How do I locate a required plug-in?]
[How do I determine what version of a plugin I am using?](#How do I determine what version of a plugin I am using?]
[How do I use SNAPSHOT versions of plug-ins?](#How do I use SNAPSHOT versions of plug-ins?]
[I've just created a maven plugin. Is there a sample plugin integration test I can use?](#I've just created a maven plugin. Is there a sample plugin integration test I can use?]
[The snapshot version of the plugin is not updated in the snapshot repo, What should I do to update my copy of the plugin?](#The snapshot version of the plugin is not updated in the snapshot repo, What should I do to update my copy of the plugin?]
[How to list all goals available for a certain plugin?](#How to list all goals available for a certain plugin?]
[What does the 'You cannot have two plugin executions with the same (or missing) <id/> elements' message mean?](#What does the 'You cannot have two plugin executions with the same (or missing) <id/> elements' message mean?]
[#How do I execute the assembly plugin with different configurations]
[Where is the plugin-registry.xml?](#Where is the plugin-registry.xml?]
[How do I know which phase a plug-in is associated with?](#How do I know which phase a plug-in is associated with?]

### POM

[#How do I install a file in my local repository along with a generic POM]
[How do I install a file in my local repository along with my customized POM?](#How do I install a file in my local repository along with my customized POM?]
[Does the v4.0.0 POM include a < versions/ > element?](#Does the v4.0.0 POM include a < versions/ > element?]
[How do I read the version from the pom.xml and then using Java display the version on my application?](#How do I read the version from the pom.xml and then using Java display the version on my application?]
[How do I determine which POM contains missing transitive dependency?](#How do I determine which POM contains missing transitive dependency?]
[Does a POM inherit its resources?](#Does a POM inherit its resources?]
[Is it possible to specify multiple(s) in a POM at a greater depth than 1 level?](#Is it possible to specify multiple(s) in a POM at a greater depth than 1 level?]
[Is there a way to read and examine the contents of a pom.xml file from inside an application?](#Is there a way to read and examine the contents of a pom.xml file from inside an application?]
[Is there a way to use the current date in the POM?](#Is there a way to use the current date in the POM?]
[Where are the Maven XSD schemas?](#Where are the Maven XSD schemas?]
[Where can I find a complete list of properties available in the pom?](MavenPropertiesGuide]

### Dependencies

[How to make a war artifact as a dependency?](#How to make a war artifact as a dependency?]
[Can I disable transitive dependencies?](#Can I disable transitive dependencies?]
[Is there a preferred way to communicate dependencies in documentation?](#Is there a preferred way to communicate dependencies in documentation?]
[Are there any recommendations on how to handle dependencies, which aren't always required?](#Are there any recommendations on how to handle dependencies, which aren't always required?]
[How do I determine my project's transitive dependencies, and if needed, exclude a particular  transitive dependency?](#How do I determine my project's transitive dependencies, and if needed, exclude a particular  transitive dependency?]
[If two versions of the same dependency are at the same depth, how do you know or predict which version will be used?](#If two versions of the same dependency are at the same depth, how do you know or predict which version will be used?]

### Inheritance

[How can I have a child project not inherit a goal (like install) from the parent?](#How can I have a child project not inherit a goal (like install) from the parent?]
[What is Maven's order of inheritance?](#What is Maven's order of inheritance?]
[How do I specify that all web modules will inherit the group's common files from a parent web module?](#How do I specify that all web modules will inherit the group's common files from a parent web module?]

### Repository

[How do I prevent verification warnings with custom repositories?](#How do I prevent verification warnings with custom repositories?]
[How do I access artifacts if Ibiblio is down?](#How do I access artifacts if Ibiblio is down?]
[How do I specify my remote repo in Maven?](#How do I specify my remote repo in Maven?]
[How do I install artifacts to a remote repository?](#How do I install artifacts to a remote repository?]
[#Repository precedence]
[#List of available maven mirrors.]
[How do I change the default remote repository?](#How do I change the default remote repository?]
[How to remove the artifact in the local repository?](#How to remove the artifact in the local repository?]
[What is the purpose of remote repository (other than ibiblio)?](#What is the purpose of remote repository (other than ibiblio)?]

### Errors

[#Why do I get java.lang.NoClassDefFoundError: org/codehaus/classworlds/Launcher when I try to execute Maven?]
[What does the "ERROR Cannot override read-only parameter < parameter_name>" message, when running mean?](#What does the "ERROR Cannot override read-only parameter < parameter_name>" message, when running mean?]
[What does the FATAL ERROR with the message \*'Class org.apache.commons.logging.impl.Jdk14Logger does not implement Log'\* when using the maven-checkstyle-plugin mean?](#What does the FATAL ERROR with the message *'Class org.apache.commons.logging.impl.Jdk14Logger does not implement Log'* when using the maven-checkstyle-plugin mean?]
[Unsupported Protocol Error when deploying a 3rd party jar. What should I do?](#Unsupported Protocol Error when deploying a 3rd party jar. What should I do?]
[I have my web.xml in my customed directory layout for my webapp, but why am I getting the error "Deployment descriptor < Path >\WEB-INF\web.xml does not exist"?](#I have my web.xml in my customed directory layout for my webapp, but why am I getting the error "Deployment descriptor < Path >\WEB-INF\web.xml does not exist"?]
[How can I stop this "WARNING While downloading artifactId-artifactId-version This artifact has been relocated to groupId-artifactId-version"?](#How can I stop this "WARNING While downloading artifactId-artifactId-version This artifact has been relocated to groupId-artifactId-version"?]

### Maven Comparisons

[What is the difference between Maven and Ivy?](#What is the difference between Maven and Ivy?]

### Ant

[How can I use Ant tasks in Maven?](#How can I use Ant tasks in Maven?]
[How do I run an ant task twice, against two different phases?](#How do I run an ant task twice, against two different phases?]
[Can I define the antrun plug-in to be executed on demand?](#Can I define the antrun plug-in to be executed on demand?]
[How do I generate sources with the antrun plug-in?](#How do I generate sources with the antrun plug-in?]
[How do I setup the classpath of my antrun plugin to use the classpath from maven?](#How do I setup the classpath of my antrun plugin to use the classpath from maven?]

### Mojo

[How do I get the project's sources from a Mojo?](#How do I get the project's sources from a Mojo?]
[How do I properly populate variables, when extending a mojo from another plugin?](#How do I properly populate variables, when extending a mojo from another plugin?]
[How do I determine the stale resources in a Mojo to avoid reprocessing them?](#How do I determine the stale resources in a Mojo to avoid reprocessing them?]
[How do I create a command line parameter (i.e., \-Dname=value ) in my mojo?](#How do I create a command line parameter (i.e., -Dname=value ) in my mojo?]
[What does aggregator mean in mojo?](#What does aggregator mean in mojo?]
[What would it take for the MOJO one to get out of the sandbox?](#What would it take for the MOJO one to get out of the sandbox?]
[How do I indicate array types in a MOJO configuration?](#How do I indicate array types in a MOJO configuration?]

### Deploy

[How do I deploy my binary during the deploy phase?](#How do I deploy my binary during the deploy phase?]
[Is there a way to use the deploy phase to perform some tasks without maven trying to install the artifact to a maven repository?](#Is there a way to use the deploy phase to perform some tasks without maven trying to install the artifact to a maven repository?]
[Is maven 'deploy' goal and actually copying of a dependency or artifact jar to remote repository same?](#Is maven 'deploy' goal and actually copying of a dependency or artifact jar to remote repository same?]

### Release

[#When I run mvn release:prepare, I get a build failure saying "Unable to tag SCM, File (...) already exists". However, the tag does not exist. What is wrong?]

### Profiles

[Can a profile inherit the configuration of a "sibling" profile?](#Can a profile inherit the configuration of a "sibling" profile?]

### Testing

[How do I prevent tests from running twice, after adding a configuration for the surefire plugin?](#How do I prevent tests from running twice, after adding a configuration for the surefire plugin?]
[How do I skip unit tests when building a project?](#How do I skip unit tests when building a project?]
[Is there a setting for testing, where I can add a directory to the classpath, which will allow the tests to access the files?](#Is there a setting for testing, where I can add a directory to the classpath, which will allow the tests to access the files?]
[Why does Maven compile my test classes but don't run them?](#Why does Maven compile my test classes but don't run them?]

### Compile

[How do I compile 1.3 java sources with a 1.4.x or 1.5.x JDK?](#How do I compile 1.3 java sources with a 1.4.x or 1.5.x JDK?]
[#Building modules only when they have changed]

### Adding or Excluding

[Can I add a java source to my war package?](#Can I add a java source to my war package?]
[How do I filter which classes should be put inside the packaged jar?](#How do I filter which classes should be put inside the packaged jar?]
[Is it possible to exclude a package from the generated jar file?](#Is it possible to exclude a package from the generated jar file?]
[How not to include all jar files from parent pom?](#How not to include all jar files from parent pom?]
[How do I filter resources in the war?](#How do I filter resources in the war?]
[How do I prevent including JARs in WEB-INF/lib? I need a "compile only" scope&#33;](#How do I prevent including JARs in WEB-INF/lib? I need a "compile only" scope!]

### Changing Locations

[How can I change the default location of the generated jar when I command "mvn package"?](#How can I change the default location of the generated jar when I command "mvn package"?]
[Is there a way to specify a different output directory without having to edit the pom or configuration file each time I do a build?](#Is there a way to specify a different output directory without having to edit the pom or configuration file each time I do a build?]
[How do I set the base directory for creating the packages created by assembly?](#How do I set the base directory for creating the packages created by assembly?]
[Is it possible to create my own directory structure?](#Is it possible to create my own directory structure?]

### JDK

[#I would like clarification on what version of the JDK is required for m2 -- particularly with respect to creating Plugins.]
[#i'm wondering what a "snapshot" actually is.]
[How do I configure a project to use a specific version of a JDK?](#How do I configure a project to use a specific version of a JDK?]

### Other

[Maven doesn't work, how do I get help?](#Maven doesn't work, how do I get help?]
[How do I get a list of archetypes?](#How do I get a list of archetypes?]
[How do I add main class in a generated jar's manifest?](#How do I add main class in a generated jar's manifest?]
[How do I run a build/package/deploy process without waiting for reports or unit tests, so that I can quickly deploy to an integration box?](#How do I run a build/package/deploy process without waiting for reports or unit tests, so that I can quickly deploy to an integration box?]
[Where is the source code? I couldn't seem to find a link anywhere on the Maven site.](#Where is the source code? I couldn't seem to find a link anywhere on the Maven site.]
[How do I add my generated sources to the compile path of Maven, when using modello?](#How do I add my generated sources to the compile path of Maven, when using modello?]
[Where can I get offline documentation for Maven?](#Where can I get offline documentation for Maven?]
[What is the purpose of displaying read-only, plug-in fields in user documentation, if they are not configurable in the project descriptor?](#What is the purpose of displaying read-only, plug-in fields in user documentation, if they are not configurable in the project descriptor?]
[How can I disable the timestamp appended in my deployed artifact?](#How can I disable the timestamp appended in my deployed artifact?]
[How to run a java program from Maven?](#How to run a java program from Maven?]
[Where to find the source code for org.apache.maven.model package?](#Where to find the source code for org.apache.maven.model package?]
[How can I reference windows or unix environment variables in my POM?](#How can I reference windows or unix environment variables in my POM?]
[Where to get sun.jdk-tools-jar-1.4.0 on MacOSX?](#Where to get sun.jdk-tools-jar-1.4.0 on MacOSX?]
[How can I make the war plugin produces an exploded war instead of .war file?](#How can I make the war plugin produces an exploded war instead of .war file?]
[Is there any variable to determine what version of an artifact was deployed after the deploy step runs?](#Is there any variable to determine what version of an artifact was deployed after the deploy step runs?]
[How do I get the top line of a table to be "headers" for that column in APT?](#How do I get the top line of a table to be "headers" for that column in APT?]
[What is the suggested way to download a remote file?](#What is the suggested way to download a remote file?]
[How do I install the package generated by the assembly plugin to be installed in the local repository?](#How do I install the package generated by the assembly plugin to be installed in the local repository?]
[Is it possible to use HashMap as configurable parameter in a plugin? How do I configure that in pom.xml?](#Is it possible to use HashMap as configurable parameter in a plugin? How do I configure that in pom.xml?]
[Which plugins have StarTeam (SCM) support? How do they differ?](#Which plugins have StarTeam (SCM) support? How do they differ?]
[How should I point a path for Maven to use a certain version of JDK when I have different versions of JDK installed on my PC and my JAVA_HOME already set?](#How should I point a path for Maven to use a certain version of JDK when I have different versions of JDK installed on my PC and my JAVA_HOME already set?]
[Why does release prepare goal requires the project to be released be a snapshot? Is it possible to do a release prepare from a parent project? What about from a sub-project?](#Why does release prepare goal requires the project to be released be a snapshot? Is it possible to do a release prepare from a parent project? What about from a sub-project?]
[How can I create an archetype with resources mapped to the class files directory?](#How can I create an archetype with resources mapped to the class files directory?]
[What does (f) and (s) debug output mean?](#What does (f) and (s) debug output mean?]
[How to resolve problems with jtaxxx.jar?](#How to resolve problems with jtaxxx.jar?]
[How to make Continuum work in service mode when an Error 1067 is reported?](#How to make Continuum work in service mode when an Error 1067 is reported?]
[Using xdoclet, how come "<fileset dir="src/main"><include name="/beans//*Bean.java"/></fileset>" does not work?](#Using xdoclet, how come "<fileset dir="src/main"><include name="/beans//*Bean.java"/></fileset>" does not work?]
[I don't have a domain name and I don't want use my employer's domain name. What should I name my plugin package?](#I don't have a domain name and I don't want use my employer's domain name. What should I name my plugin package?]
[pom.xml or settings.xml? What is the best practice configuration usage for these files?](#pom.xml or settings.xml? What is the best practice configuration usage for these files?]
[What is reactorProjects? executedProject?](#What is reactorProjects? executedProject?]
[What is a Snapshot?](#What is a Snapshot?]
[Whenever a file is modified in a maven project how is the SNAPSHOT jar updated in the remote repository?](#Whenever a file is modified in a maven project how is the SNAPSHOT jar updated in the remote repository?]
[Does maven support automated build and test from non-Java applications?](#Does maven support automated build and test from non-Java applications?]
[What would be the appropriate way to enable inheritance of classes in the test hierarchy across modules during the test-compile phase of a multi-pom project?](#What would be the appropriate way to enable inheritance of classes in the test hierarchy across modules during the test-compile phase of a multi-pom project?]
[Is Julia Antonova/Tumlare out of the office?](#Is Julia Antonova/Tumlare out of the office?]
[How to encrypt a secure password that includes an Ampersand](#How to encrypt a secure password that includes an Ampersand]

# Answered Questions

### How do I merge a list of configuration items in a parent POM with those in a child POM?

Since plugin configurations are handled as XML DOM instances by the core, there is an ever-present struggle between when to merge child elements' values with one another (when the child's `<source>` overrides the parent's, for example), and when you want to append new child elements to the existing list (as in the example above, or any time you have a list of items to aggregate during inheritance).

We've solved this problem using an attribute, `combine.children`. You simply specify this attribute, with the value of `append` at the parent element where such aggregation should occur, and Maven will switch its merge behavior for that element's children.

NOTE: I fixed this behavior in Maven's trunk; previously, it had been putting the parent POM's sub-elements AFTER those of the child. This is inconsistent with other inheritance functions, so I've reversed the ordering to truly append the child's elements.

So, to recap:

parent:
```
<configuration>
  <items>
      <item>one</item>
      <item>two</item>
  </items>
</configuration>
```
child:
```
<configuration>
  <items combine.children="append">
    <item>three</item>
  </items>
</configuration>
```
result:
```
<configuration>
  <items>
    <item>one</item>
    <item>two</item>
    <item>three</item>
  </items>
</configuration>
```
(If you're using an earlier version of Maven than trunk (revId 545315), the order above will be three, one, two.)

### Why do I not get an index.html page generated for my project website?

The usual cause of this is configuring maven-project-info-reports plugin and leaving out the 'index' report.

```
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-project-info-reports-plugin</artifactId>
        <reportSets>
           <reportSet>
              <reports>
                 <report>index</report>                <--------- here!
                 <report>dependencies</report>
                 <report>cim</report>
                 <report>scm-list</report>
                 <report>issue-tracking</report>
              </reports>
           </reportSet>
        </reportSets>
      </plugin>
```

### How do I resolve the "< plugin name > does not exist or no valid version" error?

This error indicates that Maven is either unable to access the required plug-in from your local repository, or unable to access the official or `central` Maven plug-in repository.

To resolve this error:
- If you are behind a http proxy, please check the Maven2 [proxy settings guide](http://maven.apache.org/guides/mini/guide-proxies.html).
- If you are upgrading Maven from an older version, try running with \-U. This will force an update check on all plug-ins.

If the error persists, browse [archived discussions](http://www.mail-archive.com/users@maven.apache.org/), post to the Maven user list, or log a [ticket](http://jira.codehaus.org/browse/MNG) describing your problem, if you think it is a bug. Tickets may also be issued for feature enhancement requests, and other tasks.

_Errors, Dependencies, Plugins_

### How do I install a file in my local repository along with a generic POM?

You may do this by typing this command (please take note that this is a single line only).

```
mvn install:install-file
      -DgroupId=<group-id>
      -DartifactId=<artifact-id>
      -Dversion=<version>
      -Dfile=<path-to-file>
      -Dpackaging=<packaging> (i.e. jar)
      -DgeneratePom=true
```

This command installs the jar in your local repository with the generated generic pom.
{{Well, this doesn't work in Maven 2.0.2. It just gives the message "Cannot execute mojo: install-file. It requires a project with an existing pom.xml, but the build is not using one." \--kreiger@imcode.com}}

 _Repositories_

### How do I install a file in my local repository along with my customized POM?

The solution requires the `-DpomFile=<path-to-pom>` parameter just like the sample below.
```
mvn install:install-file
      -DgroupId=<group-id>
      -DartifactId=<artifact-id>
      -Dversion=<version>
      -Dfile=<path-to-file>
      -Dpackaging=<packaging> (i.e. jar)
      -DpomFile=<path-to-pom>
```
This command will install the file in your local repository along with your customed pom.
_Repositories_

### How do I include/exclude the other modules in the navigation menu in the parent site?

[MNG-661](http://jira.codehaus.org/browse/MNG-661), provides a simple patch which provides parent and module links using the project URLs which as you
correctly point out only work when the site is deployed.

_Plugins & Lifecycle_

_General_

### What is the difference between Maven and Ivy?

For a comparison of Maven's features vs Ivy's you can refer to our [feature comparison](http://docs.codehaus.org/display/MAVEN/Feature+Comparisons)

_General_

### How do I get a plug-in's dependencies from a Mojo?

```
public class MyMojo
    extends AbstractMojo
{
    /**
     * @parameter expression="${plugin.artifacts}"
     * @required
     */
    private List pluginArtifacts;

    public void execute()
        throws MojoExecutionException
    {
        ...
        for ( Iterator i = pluginArtifacts.iterator(); i.hasNext(); )
        {
            Artifact pluginArtifact = (Artifact) i.next();
        }
        ...
    }
}
```

### How do I get the project's sources from a Mojo?

```
public class MyMojo
    extends AbstractMojo
{
    /**
     * @parameter expression="${project.compileSourceRoots}"
     * @required
     */
    private List sourceRoots;

    public void execute()
        throws MojoExecutionException
    {
        ...
        for ( Iterator i = sourceRoots.iterator(); i.hasNext(); )
        {
            String sourceRoot = (String) i.next();

           // Do what you want with these directories
        }
    }
}
``` 

### Does the v4.0.0 POM include a < versions/ > element?

The POM does not inlcude a `<versions/>` element. The POM reflects the current state of a project's build only, not a historical build log. The POM is not designed to track historical information, as it would be difficult for developers to always add this information manually, i.e., to always remember to add it or to ensure that the correct information is added when multiple branches are involved.

However, if the SCM tag of the `<scm>` section of the POM is populated, the repository records build versions, enabling developers to reconstruct the information for each released build.

_POM, General_

### How do I create a report that does not require Doxia's Sink interface?

Make it a report and override the `isExternalReport()` method to return true.

_Sites & Reporting_

### How do I prevent verification warnings with custom repositories?

Warnings from custom repositories (usually located within the organization's network, or even on the same workstation) are triggered when Maven tries to verify the integrity of the files in the repository. This verification is done via the SHA1 or MD5 sum of the file. If these sum files do not exist, then a warning appears.

Support for downloading the security sum files is not yet included in the Maven2 distribution. There are free command-line utilities on the Internet that generate these sums. Below is an example of a bash script (use [Cygwin](http://cygwin.com) if you are using a windows machine) that generates sha1sum for all jar, xml and pom files contained in the directory where it is executed:
```
#!/usr/bin/bash

gensum(){
   shaname=$1.sha1

   sum=`sha1sum $1 ]( cut -f1 -d" "`
   echo $sum > $shaname
}

processFile(){
   while read oneline
   do
      gensum $oneline


   done < "$1"
}

tmpFile=$TMP/shagen.list

echo "Generating sha1 sums for XML files"
find . -name "*.xml" > "$tmpFile"

processFile "$tmpFile"

echo "Generating sha1 sums for POM files"
find . -name "*.pom" > "$tmpFile"

processFile "$tmpFile"

echo "Generating sha1 sums for JAR files"
find . -name "*.jar" > "$tmpFile"

processFile "$tmpFile"

rm "$tmpFile"
```

The script above has been tested on [Cygwin](http://cygwin.com) and is provided "as-is" and with no guarantee.
_Errors, Repositories_

### What does the "ERROR: Cannot override read-only parameter: `<parameter_name>` " message, when running <plugin_name>: mean?

This means that the parameter being overriden in the pom.xml is read-only. Hence, it is not possible to override this parameter.

_Errors, POM_

### How do I generate Maven plug-in sites, with pages that include an overview of the goals and parameters for each plug-in?

Include maven-plugin-plugin as a report.

_Plugin Requests, Sites & Reporting_

### Can I define the antrun plugin to be executed on demand like "mvn antrun:run"?

The antrun plugin can be executed on demand only if:
- the top level configuration of the plugin contains all the information
- a variable is passed from the command line, eg `-Dtarget=foo antrun:run`
- the appropriate target in the script is executed based on the variable

However, it is recommended that developers write plugins for their goals (Ant support for
plugins will be available soon, currently you must write them in java or beanshell).

_Plugins and Lifecycle, Ant-related_

### How do I properly populate variables, when extending a mojo from another plugin?

When creating plugins, the field metadata is read from source files, so it is not available when the original source is not available. While the metadata is available in the plugin in `META-INF/maven/plugin.xml`, currently, there is no way to incorporate it when building a new plugin.

It is currently recommended that plug-ins be built using composition, instead of inheritence.

_Plugin Requests, Design, Patterns & Best Practices_

### How do I access artifacts if Ibiblio is down?

To access artifacts if Ibiblio is down, use any of its mirror sites.

[Guide mirror settings](http://maven.apache.org/guides/mini/guide-mirror-settings.html)

_Repositories, General_

### How do I get a list of archetypes?

To get a list of archetypes, refer to the following page [http://svn.apache.org/viewcvs.cgi/maven/archetype/trunk/maven-archetypes)

### How do I specify my remote repo in Maven?

To specify a remote repo in Maven, add the `<repositories>` element to the POM:

```
<repositories>
  <repository>
    <id>my-repo2</id>
    <name>your custom repo</name>
    <url>http://jarsm2.dyndns.dk</url>
  </repository>
</repositories>
```
Or, refer to the following page [http://maven.apache.org/guides/mini/guide-multiple-repositories.html)
_Repositories_

### How do I specify which output folders the Eclipse plugin puts into the .classpath file?

```
<build>
...
  <pluginManagement>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-eclipse-plugin</artifactId>
        <configuration>
          <outputDirectory>target-eclipse</outputDirectory>
        </configuration>
      </plugin>
    </plugins>
  </pluginManagement>
...
</build>
```
_Plugins and Lifecycle, IDEs_

_General, Plugins and Lifecycle_

### Can a profile inherit the configuration of a "sibling" profile?

No. Profiles merge when their ID's match - so you can inherit them from a parent POM (but you can't inherit profiles from the same POM).

_Inheritence and Interpolation, Plugins and Lifecycle, POM_

### How do I run an ant task twice, against two different phases?

You can specify multiple execution elements under the executions tag, giving each a different id and binding them at different phases.

```
<plugin>
       <artifactId>maven-antrun-plugin</artifactId>
       <executions>
         <execution>
            <id>one</id>
           <phase>generate-sources</phase>
           <configuration>
             <tasks>
               <echo message="generate-sources!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"/>
             </tasks>
           </configuration>
           <goals>
             <goal>run</goal>
           </goals>
         </execution>

         <execution>
           <id>two</id>
           <phase>package</phase>
           <configuration>
             <tasks>
                <echo message="package!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"/>
             </tasks>
           </configuration>
           <goals>
             <goal>run</goal>
           </goals>
         </execution>
      </executions>
     </plugin>
```

_Ant-related_

### How do I prevent tests from running twice, after adding a configuration for the surefire plugin?

Declare the configuration outside of the executions tag of the plugin.
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.0</version>
    <configuration>
        <systemProperties>
            <property>
                <name>net.sourceforge.cobertura.datafile</name>
                    <value>C:\cobertura.ser</value>
            </property>
        </systemProperties>
    </configuration>
</plugin>
```
_Plugins and Lifecycle, Sites & Reporting_

### How do I integrate static (x)html into my Maven site?

You can integrate your static pages in this several steps,
* Put your static pages in the resources directory, $\{basedir\}/src/site/resources.
* Create your site.xml and put it in $\{basedir\}/src/site. An example below:
```
<project name="Maven War Plugin">
  <bannerLeft>
    <name>Maven War Plugin</name>
    <src>http://maven.apache.org/images/apache-maven-project.png</src>
    <href>http://maven.apache.org/</href>
  </bannerLeft>
  <bannerRight>
    <src>http://maven.apache.org/images/maven-small.gif</src>
  </bannerRight>
  <body>
    <links>
      <item name="Maven 2" xhref="http://maven.apache.org/maven2/"/>
    </links>

    <menu name="Overview">
      <item name="Introduction" xhref="introduction.html"/>
      <item name="How to Use" xhref="howto.html"/>
    </menu>
    ${reports}
  </body>
</project>
```

* Link the static pages by modifying the `<menu>` section, create items and map it with the filename of the static pages.

```
<menu name="Overview">
  <item name="Introduction" xhref="introduction.html"/>
  <item name="How to Use" xhref="howto.html"/>
  <item name="<put-name-here>" xhref="<filename-of-the-static-page>"/>
</menu>
```
&nbsp;_Sites & Reporting_

### How do I specify that all web modules will inherit the group's common files from a parent web module?

maven-war-plugin 2.0-beta-3 and later supports merging of wars. Just reference the common WAR project from another WAR project as a dependency of `<type>war</type>` and it will automatically be merged.

See [http://jira.codehaus.org/browse/MWAR-8)

_Inheritence and Interpolation_

### How do I determine which POM contains missing transitive dependency?

run `mvn -X`

_POM, Dependencies_

_General, POM, Plugins and Lifecycle, Command Line_

### How do I determine the stale resources in a Mojo to avoid reprocessing them?

This can be done using the following piece of code:
```
// Imports needed
import org.codehaus.plexus.compiler.util.scan.InclusionScanException;
import org.codehaus.plexus.compiler.util.scan.StaleSourceScanner;
import org.codehaus.plexus.compiler.util.scan.mapping.SuffixMapping;

// At some point of your code
    StaleSourceScanner scanner = new StaleSourceScanner( 0, Collections.singleton( "**/*.xml" ), Collections.EMPTY_SET );
    scanner.addSourceMapping( new SuffixMapping( ".xml", ".html" ) );
    Set<File> staleFiles = (Set<File>) scanner.getIncludedSources( this.sourceDirectory, this.targetDirectory );
```
The second parameter to the StaleSourceScanner is the set of includes, while the third parameter is the set of excludes. You must add a source mapping to the scanner (second line). In this case we're telling the scanner what is the extension of the result file (.html) for each source file extension (.xml). Finally we get the stale files as a `Set<File>` calling the `getIncludedSources` method, passing as parameters the source and target directories (of type File). The Maven API doesn't support generics, but you may cast it that way if you're using them.
In order to use this API you must include the following dependency in your pom:
```
<dependencies>
  <dependency>
    <groupId>org.codehaus.plexus</groupId>
    <artifactId>plexus-compiler-api</artifactId>
    <version>1.5.1</version>
  </dependency>
</dependencies>
```

_POM, Plugin API_

### What does the FATAL ERROR with the message *"Class org.apache.commons.logging.impl.Jdk14Logger does not implement Log"* when using the maven-checkstyle-plugin mean?

Checkstyle uses commons-logging, which has classloader problems when initialized within a Maven plugin's container. This results in the above message - if you run with '-e', you'll see something like the following:

---
Caused by: org.apache.commons.logging.LogConfigurationException: org.apache.commons.logging.LogConfigurationException: Class org.apache.commons.logging.impl.Jdk14Logger does not implement Log
---
buried deep in the stacktrace.

The only workaround we currently have for this problem is to include another commons-logging Log implementation in the plugin itself. So, you can solve the problem by adding the following to your plugin declaration in your POM:
```
<project>
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
        <artifactId>maven-checkstyle-plugin</artifactId>
        <dependencies>
          <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.12</version>
          </dependency>
        </dependencies>
      </plugin>
    </plugins>
  </build>
  ...
  <reporting>
    ...
    <plugins>
      <!-- your checkstyle report is registered here, according to Maven documentation -->
    </plugins>
  </reporting>
</project>
```
While this may seem a counter-intuitive way of configuring a report, it's important to remember that Maven plugins can have a mix of reports and normal mojos. When a POM has to configure extra dependencies for a plugin, it should do so in the normal plugins section.
We will probably try to fix this problem before the next release of the checkstyle plugin.

 *UPDATE:* This problem has been fixed in the SVN trunk version of the checkstyle plugin, which should be released very soon.
 _Plugins and Lifecycle, Sites & Reporting, Errors_

### Where do I configure report plug-ins, like javadoc?

Generally, you should configure reporting in the `<reporting>` (vs. `<build>`) section of the POM. Configuration there applies to both the site, and when run on the command line, and adding the plugin there adds the report to the generated site.

Configuration in the build section is only used during the normal lifecycle or the command line invocation (eg `javadoc:javadoc`).

Configuration should go there if you do not want the report on the site, or the configuration differs from what is on the site (eg, some plugins have a fail build option).

_Sites & Reporting, Plugins and Lifecycle, Command Line_

### How do I deploy my binary during the deploy phase?

```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-assembly-plugin</artifactId>
  <executions>
    <execution>
      <phase>install</phase>
      <goals>
        <goal>assembly</goal>
      </goals>
      <configuration>
        <!-- put your config here -->
      </configuration>
    </execution>
  </executions>
</plugin>
```
Then run `mvn deploy`.
_Deployment_

### How do I add main class in a generated jar's manifest?

Configure the maven-jar-plugin and add your main class.
```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jar-plugin</artifactId>
  <configuration>
    <archive>
      <manifest>
   <mainClass>com.mycompany.app.App</mainClass>
      </manifest>
    </archive>
  </configuration>
</plugin>
``` 

### How do I install artifacts to a remote repository?

You need at least 2.1-SNAPSHOT version of maven-deploy-plugin to make this work, current workaround to use this is to copy the wagon provider jar (ie. wagon-ftp-1.0-alpha-3.jar) in your `%M2_HOME%/lib` and execute the command:

```
mvn deploy:deploy-file
    -DgroupId=<groupId>
    -DartifactId=<artifactId>
    -Dversion=<version>
    -Dpackaging=<packaging>
    -Dfile=<path-to-file>
    -DrepositoryId=<id-to-map-on-server>
    -Durl=<url-of-remote-repo>
```

_Repositories, Command Line_

### Does a POM inherit its resources?

Yes, resources are inherited, but only if the child pom does not define any resources. If it does, then the project just uses those resources defined in its pom and the parents resources are overridden.

_POM, Inheritence and Interpolation_

### How do I use SNAPSHOT versions of plug-ins?

[http://maven.apache.org/guides/development/guide-testing-development-plugins.html]

### How do I run a build/package/deploy process without waiting for reports or unit tests, in order to quickly deploy to an integration box?

You could create a specific profile that skips the reporting and test phases.

Also refer to [How do I skip unit tests when building a project?](How do I skip unit tests when building a project?)

### How do I execute the assembly plugin with different configurations?

Add this to your pom,
```
<build>
  ...
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-assembly-plugin</artifactId>
      <executions>
        <execution>
          <id>1</id>
          <phase>install</phase>
          <goals>
            <goal>assembly</goal>
          </goals>
          <configuration>
            <descriptor>src/main/descriptors/bin.xml</descriptor>
            <finalName>${project.build.finalName}-bin</finalName>
          </configuration>
        </execution>

        <execution>
          <id>2</id>
          <phase>install</phase>
          <goals>
            <goal>assembly</goal>
          </goals>
          <configuration>
            <descriptor>src/main/descriptors/src.xml</descriptor>
            <finalName>${project.build.finalName}-src</finalName>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
  ...

</build>
```
and run `mvn install`, this will execute the assembly plugin twice with different config.


### What is Maven's order of inheritance?

- parent pom
- project pom
- settings
- CLI parameters

where the last overrides the previous.

_General, Inheritence and Interpolation, Design, Patterns & Best Practices_

### How do I add my generated sources to the compile path of Maven, when using modello?

Modello generate the sources in the generate-sources phase and automatically adds the source directory for compilation in maven. So you don't have to copy the generated sources.

You have to declare the modello-plugin in the build of your plugin for source generation (in that way the sources are generated each time).

_Plugins and Lifecycle_

### Can I add a java source to my war package?

You can't, but you can use the assembly plugin to create a source distribution and a binary distribution for your project

Please refer to these sites for more info
[http://maven.apache.org/guides/mini/guide-assemblies.html]
[http://maven.apache.org/plugins/maven-assembly-plugin/howto.html]

_Deployments, Plugins and Lifecycle_

### What does the "You cannot have two plugin executions with the same (or missing) `<id/>` elements" message mean?

It means that you have executed a plugin multiple times with the same `<id>`. Provide each `<execution>` with a unique `<id>` then it would be ok.

_Errors, Plugins and Lifecycle_

### Can I disable transitive dependencies?

No you can't, but you may exclude the dependencies you dont want to include in your project.

Following is a sample on how to exclude transitive dependencies.
```
<project>
  ...
  <dependency>
    <groupId><!-- group id --></groupId>
    <artifactId><!-- artifact id --></artifactId>
    <version><!-- version --></version>
    <scope><!-- scope --></scope>
    <exclusions>
      <exclusion>
        <groupId><!-- groupId-of-the-artifact --></groupId>
        <artifactId><!-- artifactId-of-the-artifact --></artifactId>
      </exclusion>
    </exclusions>
  </dependency>
</project>
```

_Dependencies, Design, Patterns & Best Practices_

### Where can I get offline documentation for Maven?

Check it out from [http://svn.apache.org/repos/asf/maven/site/trunk] and run mvn site:site

_General, Sites & Reporting_

### How do I skip unit tests when building a project?

Run the mvn command with `-Dmaven.test.skip=true` argument.

Also see [How do I run a build/package/deploy process without waiting for reports or unit tests, so that I can quickly deploy to an integration box?]

_Sites & Reporting, General_

### How do I create a command line parameter (i.e., \-Dname=value ) in my mojo?

In your mojo, put `expression=$\{<exp>\}` in your parameter field

```
/**
 * @parameter expression="${expression.name}"
 */
private String exp;
```
You may now able to pass parameter values to the command line.
`mvn \-Dexpression.name=value install`

_Command Line_

### What is the purpose of displaying read-only, plug-in fields in user documentation, if they are not configurable in the project descriptor?

Often, parameters are specified as read-only to indicate that its value should be changed indirectly, rather than in the plugins `<configuration/>` section. For instance, I may have a plugin that declares a parameter as such:
```
/**
   * @parameter default-value="${project.build.directory}"
   * @required
   * @readonly
   */
  private File buildDir;
```
In this case, my plugin wants to output something to the project's build directory. If this were configured directly on the plugin, it might not be cleaned up when the user issued *'mvn clean'*, so instead I mark it as `@readonly`. This tells the user that she should modify the structure referenced by *default-value*(i.e. `<project><build><directory/>` in the POM) instead, which will allow this plugin to be a good citizen in the build process.


### I've just created a maven plugin. Is there a sample plugin integration test I can use?

Each integration test is a separate project. For a plugin, you may want to create a project that will use your plugin and probably put it inside src/test/projects like maven-antrun-plugin, maven-eclipse-plugin, maven-javadoc-plugin and several others. These plugins can be found here: [https://svn.apache.org/repos/asf/maven/plugins/trunk]

_Plugins and Lifecycle, Sites & Reporting, Integration tests_

### The snapshot version of the plugin is not updated in the snapshot repo, What should I do to update my copy of the plugin?

If the plugin in the snapshot repo ([http://snapshots.maven.codehaus.org/maven2]) is not yet updated. The only way to update it is to check out the source from SVN and build it.

### Is there a way to get Maven to report the number of compile errors found?

Currently, this type of summary information is not built into the compiler plugin, but it would be possible to add. If this feature is important to you, add your vote to [MNG-1854](http://jira.codehaus.org/browse/MNG-1854).

_Sites & Reporting_

### In a multi-module project, is there any way for Maven to build only those modules that have changed from the previous build and leave the unchanged modules alone (i.e. not build them)?

Currently, this is not possible. The main reason is that it's non-trivial to determine whether an entire project's build is stale (the project here being one of the modules). It will be dependent on the phase being called, and the packaging of the particular module.

_Design, Patterns & Best Practices_

### Where can I get the Maven plugin for Eclipse?

[http://maven.apache.org/eclipse-plugin.html]

There are some flash demos to show the user how to use the plugin.

_IDEs_

### Handle special characters in site

A solution with eclipse and solaris

In eclipse.ini :
Adding \-Dfile.encoding=ISO-8859-1

If using xmlbuddy with eclipse to edit xdoc files set the encoding
Honor encoding
Default to ISO-8859-1

Configuration in pom
```
<plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-site-plugin</artifactId>
        <configuration>
          <outputEncoding>UTF-8</outputEncoding>
        </configuration>
      </plugin>
```
On the solaris machine
In `$HOME/.profile`
`MAVEN_OPTS="-Xmx512m -Xms512m -Dfile.encoding=ISO-8859-1` (mx/ms not mandatory for m2 but for m1).
`LANG=en_US.ISO8859-15`
  _Sites & Reporting, IDEs_

### How do I generate sources with the antrun plug-in?

For instance to generate sources add the following to your plugins section
NOTE: this may only work in the latest plugin version in SVN
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-antrun-plugin</artifactId>
    <dependencies>
      <!-- add dependencies needed in your ant script -->
    </dependencies>
    <executions>
        <execution>
            <id>generate-sources</id>
            <phase>generate-sources</phase>
            <configuration>
                <tasks>
                    <!-- this will delegate to an ant build.xml, you could embed here your ant tasks -->
                    <path id="classpath">
                        <path refid="maven.compile.classpath"/>
                        <path refid="maven.plugin.classpath"/>
                    </path>
                    <ant antfile="${basedir}/build.xml" dir="${basedir}" inheritRefs="true">
                        <target name="generate-sources"/>
                    </ant>
                </tasks>
            </configuration>
            <goals>
                <goal>run</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
 _Ant-related_&nbsp;


### Where is the plugin-registry.xml?

From the settings.xml, you may enable it by setting `<usePluginRegistry/>` to true and the file will be in `~/.m2/plugin-registry.xml`

_General, Plugins and Lifecycle_

### Is there a way to specify a different output directory without having to edit the pom or configuration file each time I do a build?

Yes. You can make use of the pom's `<properties>` element to accomplish this.

To do so, simply add the following fragment to your pom:
```
<project>
...
  <build>
    <directory>${directory}</directory>
    <outputDirectory>${directory}/classes</outputDirectory>
    <testOutputDirectory>${directory}/test-classes</testOutputDirectory>
  </build>
  <properties>
    <directory>target</directory>     <!-- will serve as the default -->
  </properties>
...
</project>
```
Now, to specify a different output directory at runtime simply use the directory property as a mvn command line parameter;
{code}mvn -Ddirectory=tmp package
```
This will send the build's output files to the $\{basedir}/tmp directory.

_POM, Command Line_

### How do I compile 1.3 java sources with a 1.4.x or 1.5.x JDK?

You need to specify the <compilerVersion> element to 1.3

i.e.
```<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <verbose>true</verbose>
          <fork>true</fork>
          <executable><!-- path-to-javac --></executable>
          <compilerVersion>1.3</compilerVersion>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ...
</project>
``` 

### How do I change the default remote repository?

Define in your POM a repository with "central" as the repository id.

```
<repositories>
    <repository>
      :
      <id>central</id>
      <name>any name</name>
      <url>http://your.remote.repo.url.org</url>
      :
    </repository>
  </repositories>
```

 _Repositories_

### I have my web.xml in my customed directory layout for my webapp, but why am I getting the error "Deployment descriptor `<Path>\WEB-INF\web.xml` does not exist"?

You may specify the path of your web.xml in your webapp by configuring maven-war-plugin.
```
<build>
   ...
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <configuration>
          <webXml><!-- path-to-your-webxml --></webXml>
        </configuration>
      </plugin>
    </plugins>
</build>
```
_Errors, Deployment_&nbsp;


### Is it possible to specify multiple `<module>`(s) in a POM at a greater depth than 1 level?

Yes. This can be done in two ways: 1. add the extra path in the top level POM, or 2. add extra parent poms at different levels, if approriate.

For example, you have the following structure:
/A/pom.xml (multi-module POM)
/A/B/C/pom.xml
/A/B/C/D/pom.xml

And you want the modules at level C and D built when the multi-module POM is built.

For the first solution:
Add the following at the top level POM:

```
<modules>
  <module>A/B</module>
  ...
<modules>
```
For the second solution:
Add the following at the top level POM:
```
<modules>
  <module>B</module>
<modules>
```
And in directory A/B/, add an extra parent POM and add the following:
```
<modules>
  <module>C</module>
<modules>
```
Both ways are effectively the same, but if you have that inheritance structure, the second gives a more natural grouping (eg, you can cd
into "B" and build all its subprojects only).
If you do the first solution, the children poms should have the following hint in the parent element:
```<parent>
  ...
  <artifactId>A</artifactId>
  <relativePath>../../pom.xml</relativePath>
</parent>
```
The repository is still used if ../../pom.xml is not found or the versions don't match, but the hint makes it easier to use local modifications without installing the parent.&nbsp;
_POM_

### How to list all goals available for a certain plugin?

We can use the describe goal of maven-projecthelp-plugin to list the goals available, see sample syntax below.
```
mvn projecthelp:describe -Dplugin=org.apache.maven.plugins:maven-eclipse-plugin -Dfull=true
```
This would display all the goals and descriptions of the parameters used by maven-eclipse-plugin.
\\
To get a quick overview about available mojos you can use the 'help' mojo which automatically gets generated in newer plugins.
```
mvn [pluginname]:help
e.g. mvn eclipse:help
```
_Plugins and Lifecycle, IDEs_

### What does aggregator mean in mojo?

When a Mojo has a `@aggregator` expression, it means that It can only build the parent project of your multi-module-project, the one who has the packaging of pom. It can also give you values for the expression $\{reactorProjects\} where reactorProjects are the MavenProject references to the parent pom modules.

### Why there are no dependency properties in Maven?

They were removed because they aren't reliable in a transitive environment. It implies that the dependency knows something about the
environment of the dependee, which is back to front. In most cases, granted, the value for war bundle will be the same for a particular
dependency - but that relies on the dependency specifying it.

In the end, we give control to the actual POM doing the building, trying to use sensible defaults that minimise what needs to be
specified, and allowing the use of artifact filters in the configuration of plugins.

### How do I prevent including JARs in WEB-INF/lib? I need a "compile only" scope

The scope you should use for this is provided. This indicates to Maven that the dependency will be provided at run time by its container or the JDK, for example.

Dependencies with this scope will not be passed on transitively, nor will they be bundled in an package such as a WAR, or included in the runtime classpath.

### How do I list available plugins?

The "Available Plugins" page lists them, and provides additional information to browse the Maven repository. See [http://maven.apache.org/plugins]

### How do I determine what version of a plugin I am using?

You can use the Maven Help Plugin's describe goal. For example, to find out the version of the install plugin:
{noformat}mvn -Dplugin=install help:describe
{noformat}Note that you must give the plugin prefix as the argument to plugin, not it's artifact ID.

### How can I use Ant tasks in Maven?

There are currently 2 alternatives:
* For use in a plugin written in Java, Beanshell or other Java-like scripting language, you can construct the Ant tasks [using the instructions given in the Ant documentation](http://ant.apache.org/manual/antexternal.html)
* If you have very small amounts of Ant script specific to your project, you can use the [AntRun plugin](http://maven.apache.org/plugins/maven-antrun-plugin/index.html).

### Is it possible to create my own directory structure?

Absolutely yes!

By configuring `<sourceDirectory>`, `<resources>` and other elements of the `<build>` section.

In addition, you may need to change the plugin configuration if you are not using plugin defaults for their files/directories.

### Where is the source code? I couldn't seem to find a link anywhere on the Maven site.

The source code can be found in subversion: [http://svn.apache.org/repos/asf/maven/components/trunk].

For more information, see [Building Maven](http://maven.apache.org/guides/development/guide-building-m2.html)

### Why does Maven compile my test classes but doesn't run them?

Tests are run by the surefire plugin. The surefire plugin can be configured to run certain test classes and you may have unintentionally done so by specifying a value to $
{test}. Check your settings.xml and pom.xml for a property named "test" which would like this:
```
...
  <properties>
    <property>
      <name>test</name>
      <value>some-value</value>
    </property>
 </properties>
  ...
```
or
```
...
  <properties>
    <test>some-value</test>
 </properties>
  ...
```
### Where are the Maven XSD schemas?

The Maven XSD is located here and the Maven Settings XSD is located here.
Your favorite IDE probably supports XSD schema's for pom.xml and settings.xml editing. You need to specify the following:
```
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/maven-v4_0_0.xsd">
  ...
</project>
```
```
<settings xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">
  ...
</settings>
```

### Maven doesn't work, how do I get help?

We have compiled a list of available resources on the [getting help page](http://maven.apache.org/users/getting-help.html)

(#Dependencies, Design, Patterns & Best Practices)

### Where to find the source code for org.apache.maven.model package?

The source for the model package is generated by modello. From your maven-model source, build it and you should able to see tha java files inside /target/generated-sources directory.

### List of available Maven mirrors.

Here is the list of available mirrors you can use, just use one of the following mirror entries in your settings.xml

```
<settings>

  <mirrors>
    <mirror>
      <id>dotsrc.org</id>
      <url>http://mirrors.dotsrc.org/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
    <mirror>
      <id>ggi-project.org</id>
      <url>http://ftp.ggi-project.org/pub/packages/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
    <mirror>
      <id>sunsite.dk</id>
      <url>http://mirrors.sunsite.dk/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
    <mirror>
      <id>planetmirror.com</id>
      <url>http://public.planetmirror.com/pub/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
    <mirror>
      <id>lsu.edu</id>
      <url>http://ibiblio.lsu.edu/main/pub/packages/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
    <mirror>
      <id>ibiblio.net</id>
      <url>http://www.ibiblio.net/pub/packages/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>

</settings>
```

### Unsupported Protocol Error when deploying a 3rd party jar. What should I do?

When using deploy-deploy-file goal and encountered this error

"Error deploying artifact: Unsupported Protocol: 'ftp': Cannot find wagon which supports the requested protocol: ftp"

You only have to place the appropriate wagon provider to your `%M2_HOME%/lib`
In this case the provider needed is ftp, so we have to place the wagon-ftp jar to the lib of your m2 installation.

If the error description is something like

"Error deploying artifact: Unsupported Protocol: 'ftp': Cannot find wagon which supports the requested protocol: ftp org/apache/commons/net/ftp/FTP"

Place also the commons-net jar to %M2_HOME%/lib.

### How can I have a child project not inherit a goal (like install) from the parent?

Use the `inherited` property. Set it to `false` in the plugin configuration. So for example, if you want your parent project to be installed but not your child, configure the install plugin like so:
```
<plugin>
   <artifactId>maven-install-plugin</artifactId>
   <configuration>
      <inherited>false</inherited>
   </configuration>
</plugin>
```

### How can I reference windows or unix environment variables in my POM?

Starting in maven *2.0.1*, you can reference windows and unix environment variables inside your pom.xml or settings.xml using an expression of the form:
`$\{env.VARNAME\}`
So, if you wanted to reference your home directory environment variable, you might use: `$\{env.HOME\}`

### How do I know which phase a plug-in is associated with?

Open the plugin's Mojo class source code and look for @phase. This tells which phase the plugin is associated.

### Where to get sun.jdk-tools-jar-1.4.0 on MacOSX?

There are no tools.jar on a mac. The classes are included in the normal java runtime
( /System/Library/Frameworks/ JavaVM.framework/Classes/classes.jar.
Refer to this link [http://lists.apple.com/archives/java-dev/2002/Jun/msg00901.html] )

You only have to modify the `<systemPath>` pointing to your classes.jar on MacOSX.
```
<dependency>
            <groupId>sun.jdk</groupId>
            <artifactId>tools</artifactId>
            <version>1.5.0</version>
            <systemPath>/System/Library/Frameworks/JavaVM.framework/Versions/ 1.5/Classes/classes.jar</systemPath>
            <scope>system</scope>
        </dependency>
```

### How do I include tools.jar in my dependencies?

The following code includes tools.jar on Sun JDKs (it is already included in the runtime for Mac OS X and some free JDKs).
```
...
  <profiles>
    <profile>
      <id>default-tools.jar</id>
      <activation>
        <property>
          <name>java.vendor</name>
          <value>Sun Microsystems Inc.</value>
       </property>
     </activation>
      <dependencies>
        <dependency>
          <groupId>com.sun</groupId>
          <artifactId>tools</artifactId>
          <version>1.4.2</version>
          <scope>system</scope>
          <systemPath>${java.home}/../lib/tools.jar</systemPath>
       </dependency>
     </dependencies>
   </profile>
 </profiles>
  ...
```

### How to remove the artifact in the local repository?

As of now, There is no tool for it. You have to manually delete them in your local repository. However,
there is already some discussion about this, Please refer to this links for more info.

[http://jira.codehaus.org/browse/MNG-233]
[http://jira.codehaus.org/browse/MRELEASE-68]

### How to make a war artifact as a dependency?

When specifying a war as dependency, make sure that you have set the `<type>` to war.
```
<dependency>
        <groupId><!-- groupId of the war --></groupId>
        <artifactId><!-- artifactId of the war --></artifactId>
       <version><! -- version of the war --></version>
         <type>war</type>
        </dependency>
```

### How can I change the default location of the generated jar when I command "mvn package"?

By default, the location of the generated jar is in $\{project.build.directory\} or in your target directory.
We can change this by configuring the outputDirectory of maven-jar-plugin.
```
<plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <configuration>
                <outputDirectory>${project.build.directory}/<!-- directory --></outputDirectory>
            </configuration>
        </plugin>
```

### How can I disable the timestamp appended in my deployed artifact?

When your deployed artifact is appended by timestamps it means that the artifact version is still in SNAPSHOT,
meaning it is still in the state of development. There are some ways the append process of the timestamp,

1. Change the version of your artifact.
For example, From 1.0-SNAPSHOT to 1.0

2. Use the maven-release-plugin to prepare and create the official RELEASE version of your project artifact.
Please see [http://maven.apache.org/guides/mini/guide-releasing.html] for more reference.

### How to run a java program from Maven?

You may use the exec-maven-plugin for this. [http://mojo.codehaus.org/exec-maven-plugin/usage.html]

### How can I make the war plugin produces an exploded war instead of .war file?

You may use war:exploded goal for this.

### Is there any variable to determine what version of an artifact was deployed after the deploy step runs?

Use `$project.artifact.resolvedVersion`.

### How do I get the top line of a table to be "headers" for that column in APT?

With the snapshot you can do:
```
](]( header 1 ](]( header 2 ](]( header 3 ](](
```
Example:
```
*----------------+----------------*--------------------------+
](]( header 1 ](](   ]( ](]( header 2 ](]( ]( ](]( header 2 ](](
*----------------+----------------*--------------------------+
cell1            ]( cell1          ](  cell3
*----------------+----------------*--------------------------+
```

### What is the suggested way to download a remote file?

Wagon is really for repository interaction, though it could be used for this.

To get the wagon:
```
/* @component roleHint="http" */
Wagon wagon;
```

### How do I set the base directory for creating the packages created by assembly?

The assembly plugin, by default, saves the packages to your project.build.directory folder from your pom or
```
<project>
...
<build>
  <directory>path-here</directory
  ...
</build>
...
</project
```
Also, you can have assembly plugin use a different directory by setting the plugin parameter `outputDirectory` to your desired directory.
More info about the assembly plugin can be found here: [http://maven.apache.org/plugins/maven-assembly-plugin/introduction.html]

### How do I filter which classes should be put inside the packaged jar?

All compiled classes are always put into the packaged jar. However, you can configure the compiler plugin to exclude compiling some of the java sources using the compiler parameter `excludes` as follows:
```
<project>
 ...
 <build>
   ...
   <plugins>
     <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-compiler-plugin</artifactId>
       <configuration>
         <excludes>
           <exclude>**/NotNeeded*.java</exclude>
         </excludes>
       </configuration>
     </plugin>
   </plugins>
   ...
 </build>
</project>
```

### How do I install the package generated by the assembly plugin to be installed in the local repository?

Use the assembly plugin goal {{assembly:attach}} to install the generated package into the local repository. However, this feature is still in SVN. Please see [Guide to Testing Development Plugins](http://maven.apache.org/guides/development/guide-testing-development-plugins.html) for more info on how to use it.

### Is it possible to use HashMap as configurable parameter in a plugin? How do I configure that in pom.xml?

Yes. Its possible to use a HashMap field as a parameter in your plugin. To use it, your pom configuration should look like this:
```
<myMap>
    <yourkey>yourvalue</yourkey>
    .....
 </myMap>
```

### How do I setup the classpath of my antrun plugin to use the classpath from maven?

The maven classpaths are available as ant references when running your ant script. The ant reference names and some examples can be found here: [maven-antrun-plugin](http://maven.apache.org/plugins/maven-antrun-plugin/classpaths.html)

### Is there a way to read and examine the contents of a pom.xml file from inside an application?

Yes. You can use the org/apache/maven/model/io/xpp3/MavenXpp3Reader class from maven model to read a pom.

### Which plugins have StarTeam (SCM) support? How do they differ?

maven-scm-plugin and maven-release-plugin support via maven-scm's API and maven-scm-provider-starteam. Release plugin is used to cut a new release of your project (label, build, assembly, deploy, etc.). SCM plugin, on the other hand, is for scm tasks.

### Is there a way to use the deploy phase to perform some tasks without maven trying to install the artifact to a maven repository?

If you run 'mvn deploy', the 'maven-deploy-plugin' kicks in. There's no flag that tells it not to upload the file. So you'd have to make a new lifecycle mapping/packaging for each `<type>` of artifact in your project. There's no way to 'delete' mojo's from the default lifecycle. What you could do is specify the remote repo as [file:///tmp]or something. Usually people want to 'upload' a war to the tomcat webapps dir, also known as deploying. That kind of deploying is not something m2 has a phase for.

What you can do is create a profile in the pom (or super pom), and add a task/mojo to the 'install' phase. For instance, you can have the antrun plugin copy the artifact to the tomcat directory (its location specified in settings.xml). The profile is named 'dev', so whenever you want to deploy a war, you just type 'mvn install \-Pdev'. Don't bind to the deploy phase, but to the install or package phase.

### Repository precedence

The repositories are searched through based in the order they are defined in your pom. The inherited repositories are always searched last.
Note: You don't have to define the central repo (i.e. ibiblio).

### Is it possible to exclude a package from the generated jar file?

You can configure maven-compiler-plugin to exclude your unwanted packages or files to be compiled in the first place. But you will not be able to prevent javac to compile those files if they are referenced by other packages within the source tree. To prevent that, you will need to use antrun plugin ( or write your own custom plugin), bind it to compile phase, and remove unwanted classes in $\{project.build.directory\}/classes. If possible, just move those pacakges/files to another source tree to become another project.

### How should I point a path for Maven to use a certain version of JDK when I have different versions of JDK installed on my PC and my JAVA_HOME already set?

If you don't want to change your system JAVA_HOME, set it in maven script instead.

### Why does release prepare goal requires the project to be released be a snapshot? Is it possible to do a release prepare from a parent project? What about from a sub-project?

The release:prepare requires the project to be released be a snapshot because it follows the maven development process where: - during development, everyone works on snapshots
- at release time, the snapshot got changed to release version, checked back into SCM, labelled and then built.
- the version is then incremented with snapshot and checked into SCM again.

It is possible to do a release:prepare from a parent project, but both the parent project and its sub-project must be in snapshot states.

When performing release:prepare in a sub project, the parent cannot be in snapshot state. The key here is that we want to be able to reproduce the build with a label. Any snapshot state from parent or dependencies will prevent that from happening.

### How can I create an archetype with resources mapped to the class files directory?

Specify the resources to be sources as shown below:
```
<sources>
 <source>src/main/resources/sampleXml.xml</source>
 <source>src/main/resources/sampleProperties.properties</source>
</sources>
```

### How do I add a description to the welcome page of the generated site when I execute mvn site?
Fille up the `<description>` in the pom.xml as shown below:
```
<project>
...
<description> put your description here</description>
....
</project>
```

### What does (f) and (s) debug output mean?

(f) is for field injection while (s) is for setter injection.

### Is there a preferred way to communicate dependencies in documentation?

Same format used in Maven error reporting: `groupId:artifactId:version`
This should be easy to understand by Maven users.

### Are there any recommendations on how to handle dependencies, which aren't always required?

Create a profile and include the dependency within it.
Related document: [http://maven.apache.org/maven-model/maven.html#class_profile]

### How to resolve problems with jtaxxx.jar?

Refer to [http://maven.apache.org/guides/mini/guide-coping-with-sun-jars.html]

One trick is to use apache gerinomo jars instead of installing everything manually.

### How not to include all jar files from parent pom?

Use the right scope. If you don't want them to be included, specify the scope of your dependency as provided.

### How to make Continuum work in service mode when an Error 1067 is reported?

When using WinXP, Continuum works in command line but not in service mode and reports an "Error 1067". This is due to the system path including spaces (e.g. "c:Program Files"). To fix this remove the %PATH% references in wrapper.conf.

### Which part in pom.xml the javadoc plugin should be configured?

It can be done in the plugins section of the pom or in the plugins section of the report section in the pom.

Generally, you should configure it in the reporting section. Configuration there applies to both the site, and when run on the command line, and adding the plugin there adds the report to the generated site.

Configuration in the build section is only used during the normal lifecycle or the command line invocation (e.g. javadoc:javadoc). Configuration should go there if you do not want the report on the site, or the configuration differs from what is on the site (e.g., some plugins have a fail build option).

### What would it take for the MOJO one to get out of the sandbox?

A full release, with active developers.

### Using xdoclet, how come "<fileset dir="src/main"><include name="\**/beans/*\*/*Bean.java"/></fileset>" does not work?

For an example, let's say you have a Java source at src/main/java/com/junk/JunkBean.java. For it to compile correctly, it needs to have a "package com.junk;" in it. When XDoclet walks the source starting at "src/main", it find a source file that identifies itself as being in package "com.junk" but it findsthe source in package "java.com.junk". Since the two don't match, XDoclet assumes that there is a serious problem with the source file and ignores it.

### How do I indicate array types in a MOJO configuration?

```
<tags>
  <tag>value1</tag>
  <tag>value2</tag>
</tags>
```


### I don't have a domain name and I don't want use my employer's domain name. What should I name my plugin package?

How will the plugin be licensed? Is it intended to just be a binary distribution or will the source be available? Where will be the documentation be? How will people find out about it?
I think your choice is probably influenced by these questions. One option, of course, is to propose it to mojo.codehaus.org if you want to and can share the code.

### I issued `mvn -Declipse.downloadSources=true eclipse eclipse` goal. It created .classpath and .project for both modules, and in my local repository it downloaded sources Ho do I access them in eclipse?

```
<build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-eclipse-plugin</artifactId>
                    <version>2.0</version>
                    <configuration>
                        <downloadSources>true</downloadSources>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
```
Make sure the generated .classpath actually contains sourcepath attributes, like this:
```
<classpathentry kind="var"
                path="M2_REPO/javax/servlet/servlet-api/ 2.4/servlet-api-2.4.jar"
                sourcepath="M2_REPO/javax/servlet/servlet- api/2.4/servlet-api-2.4-sources.jar"/>
```

### Is there a setting for testing, where I can add a directory to the classpath, which will allow the tests to access the files?

```
<testResources>
         <testResource>
            <targetPath>org/apache/struts/resources</targetPath>
            <directory>conf/java</directory>
            <includes>
               <include>**/*.xml</include>
               <include>**/*.dtd</include>
            </includes>
         </testResource>
         <testResource>
            <directory>src/test</directory>
            <includes>
               <include>**/*.xml</include>
            </includes>
         </testResource>
      </testResources>
```


### How can I stop this "WARNING While downloading artifactId-artifactId-version This artifact has been relocated to groupId-artifactId-version"?

It's probably because some other dependency has specified the dependency on artifactId:artifactId. It will only go away when that declaration is fixed in that POM.

### I would like clarification on what version of the JDK is required for m2 - particularly with respect to creating Plugins.

1.4 is required to run m2 there're problems when using 1.5 features in plugins. People tried and failed because qdox (used for some mojo stuff doesn't support new 1.5 language)

### i'm wondering what a "snapshot" actually is.

A snapshot is a development version. e.g, 2.0-SNAPSHOT is thestill-in-development future 2.0.If you want to use a snapshot, just use `<version>` , e.g. `<version>2.0-SNAPSHOT</version>` . But first you must ensure that you have access to the repository containing this version.

### Is it possible that if I do mvn eclipse eclipse goal that my project would get disconnected from the subversion repository?

1.4 is required to run m2 there're problems when using 1.5 features in plugins. People tried and failed because qdox (used for some mojo stuff doesn't support new 1.5 language)

### Does it matter if the project's directory name is not the same as the artifactId?

It does not really matter. It'll work, some defaults might have to be overridden (eg SCM inheritance, though that's hopefully going to change), and the parent path if not.

### pom.xml or settings.xml? What is the best practice configuration usage for these files?

The best practice guideline between settings.xml and pom.xml is that configurations in settings.xml must be specific to the current user and that pom.xml configurations are specific to the project.

For example, `<repositories>` in pom.xml would tell all users of the project to use the `<repositories>` specified in the pom.xml. However, some users may prefer to use a mirror instead, so they'll put `<mirrors>` in their settings.xml so they can choose a faster repository server.

so there you go:
```
settings.xml > user scope
pom.xml > project scope
```
### What is reactorProjects? executedProject?

$\{reactorProjects} are the projects that the current mvn command are going to be built. This will include the parent project and all its children while $\{executedProject} is the project where you typed your mvn command.

### What is a Snapshot?

A snapshot is a development version. e.g, 2.0-SNAPSHOT is the still-in-development future 2.0.
If you want to use a snapshot, juste use `<version>` , e.g. `<version>2.0-SNAPSHOT</version>`. But first you must ensure that you have access to
the repository containing this version. For example, for Maven snapshots as stated below, you could use :
```
<repositories>
  <repository>
    <id>maven-snapshots</id>
    <url>http://snapshots.maven.codehaus.org/</url>
  </repository>
</repositories>
```
or, for plugins :
```
<pluginRepositories>
  <pluginRepository>
    <id>maven-snapshots</id>
    <url> http://snapshots.maven.codehaus.org/</url>
  </pluginRepository>
</pluginRepositories>
```
Please refer to the following links
[http://maven.apache.org/guides/getting-started/index.html#How%20do%20I%20make%20my%20first%20Maven%20project?]
[http://maven.apache.org/maven-model/maven.html#class_repository]

### How do I determine my project's transitive dependencies, and if needed, exclude a particular  transitive dependency?

To find out which of your dependencies is causing the problem, use \-X
on the mvn command line.  If necessary, you can redirect the output
to a file with '> filename.txt' .

Then add an exclusion to that dependency in your pom.xml.  Here's a
post with some examples of exclusions:
[http://article.gmane.org/gmane.comp.jakarta.tapestry.devel/9103]

### If two versions of the same dependency are at the same depth, how do you know or predict which version will be used?

The order in which dependencies are declared effectively becomes the tie-breaker when two dependency versions are declared at the same depth...so if:
```
A -> B, C
B -> D (v2)
C -> D (v3)
and A declares the following:
<dependencies>
  <dependency>
    [...]
    <artifactId>B</artifactId>
  </dependency>
  <dependency>
    [...]
    <artifactId>C</artifactId>
  </dependency>
</dependencies>
```
Maven should choose D (v2) because B is declared first in the POM.


### How do I configure a project to use a specific version of a JDK?

Use the following plugin:
```
<plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-compiler-plugin</artifactId>
       <configuration>
         <verbose>true</verbose>
         <fork>true</fork>
         <executable><!-- path-to-javac --></executable>
         <compilerVersion>1.3</compilerVersion>
       </configuration>
  </plugin>
```

### Is there a way to use the current date in the POM?

Take a look at the buildnumber plugin. It can be used to generate a build date each time I do a build, as follows:
```<plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>maven-buildnumber-plugin</artifactId>
        <version>0.9.4</version>
        <configuration>
          <format>{0,date,yyyy-MM-dd HH:mm:ss}</format>
          <items>
            <item>timestamp</item>
          </items>
          <doCheck>false</doCheck>
          <doUpdate>false</doUpdate>
        </configuration>
        <executions>
          <execution>
            <phase>validate</phase>
            <goals>
              <goal>create</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```
The build date is available as $buildNumber in my POMs and resource files.


### What is the purpose of remote repository (other than ibilbilo)?

To store stuff that is not in ibiblio. An example of this is your own stuff.

### Whenever a file is modified in a maven project how is the SNAPSHOT jar updated in the remote repository?

Using mvn deploy, after inserting proper <distributionManagement/> section into your POM

### Is maven 'deploy' goal and actually copying of a dependency or artifact jar to remote repository same?

Actually, simply copying the artifact to the repository is not the same as using deploy. The deploy goal will update various metadata files, create the md5 and sha1 checksum files, and can optionally create missing POM files etc along with actually copying the artifact file.
So there is a significant difference between the copying the file and using deploy.


### When I run mvn release:prepare, I get a build failure saying "Unable to tag SCM, File (...) already exists". However, the tag does not exist. What is wrong?

The full failure will look something like this:
'/stuff/tags/example/pom.xml'
```
[INFO\] Tagging release with the label stuff-1.0.0...
[INFO\] Executing: svn \--non-interactive copy \--file C:\DOCUME~1\G980143\LOCALS~1\Temp\maven-scm-1259783654.commit . http://www.example.com/subversion/repo/example/tags/stuff-1.0.0
[INFO\] Working directory: C:\projects\stuff\branches\1.0.x
[INFO\] \-----------------------------------------------------------------------\-
[ERROR\] BUILD FAILURE
[INFO\] \-----------------------------------------------------------------------\-
[INFO\] Unable to tag SCM
Provider message:
The svn tag command failed.
Command output:
svn: Commit failed (details follow):
svn: File 'subversion/repo/example/tags/stuff-1.0.0/pom.xml' already exists
```
This will only happen in Subversion 1.5.x, and is due to a "changed behavior" in Subversion 1.5.0 and upwards. Maven and Subversion people have at the time og writing not agreed upon who should fix it. A workaround is to downgrade Subversion to version 1.4.4 and use that. Another is to manually copy the trunk/release branch to the tags directory, commit the change and then edit the release.properties file to reflect the fact that tagging has been done.

### How do I filter resources in the war?

The best resource for filtering resources in the war can be found here:
[http://maven.apache.org/plugins/maven-war-plugin/examples/adding-filtering-webresources.html]

### Does maven support automated build and test from non-Java applications?

Maven does support non-Java applications such as C but with a limited capacity.

### What would be the appropriate way to enable inheritance of classes in the test hierarchy across modules during the test-compile phase of a multi-pom project?

For information on this topic please visit: [http://maven.apache.org/guides/mini/guide-attached-tests.html]

### How do I read the version from the pom.xml and then using Java display the version on my application?

If you want to read a pom file, you can use the MavenXpp3Reader#read(... ).
But if you simply want to get the version of your currently running maven project inside your pom ( which is usually the case ), you can simply do:
```
/**
* @parameter expression="${project.version}"
*/
private String version;
```
Also, if you have a maven project (similar to the contents of its pom except that inheritance is already applied ) and want to get its version, you can use MavenProject#getVersion().


### How can I add two different source-directories to a project?

You can do this by using the maven-buildhelper-plugin. It allows you to add additional source directories.

### How do I add properties to a pom?

```
<properties>
  <myproperty>propertyvalue</myproperty>
</properties>
```
Then `$myproperty=propertyvalue`

### How do replace `<attainGoal>` from m1 in m2?

I want to build a plugin with multiple goal, some goal might be aliases and call other goals. How do i do that in m2?

### Maven Open Source! How do I download the source code?

I've seen the link to the source code: [http://svn.apache.org/repos/asf/maven/components/trunk/]

but what is the easy way to download the source code. I can't find all the answers I need to understand in the documeentation (need more! so it will be usefull to get the source code locally to be able to do search etc...

I've just found the answer svn anonymous access: [http://maven.apache.org/source-repository.html];

### Why do I get java.lang.NoClassDefFoundError: org/codehaus/classworlds/Launcher when I try to execute Maven?

This sometimes happens when $M2_HOME is not the same as your $PATH.  That is, when 'which mvn' does not match $M2_HOME/bin/mvn.

### How to encrypt a secure password that includes an Ampersand

If you want to `mvn --encrypt-password` a password with an ampersand you will get an error, e.g. `mvn --encrypt-password test&Password`
On the one hand the ampersand has to be encoded as entity with &amp;. On the other hand the ampersand has to be escaped for the command line.
Result would be `mvn --encrypt-password test &Password`
Also a dollar sign $ has to be escaped for the command line.