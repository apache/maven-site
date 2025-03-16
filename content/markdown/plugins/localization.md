---

title: Localization of Plugins
author: 
- Dennis Lundberg
- Vincent Siveton
date: 2012-03-12
----------------

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

# Localization of Plugins

Most of the plugins involved with the site generation are fully internationalized. This means that adapting them to another language, a process known as localization, is very easy. All that is needed is to download a couple of properties files and start translating the texts in them. If you want to provide a patch for an unsupported language, there are detailed instructions below.

For the basic site generation there are currently files for three components that needs to be localized to support a new language: Maven Site Plugin, Maven Project Info Reports Plugin and Maven Doxia Tools.

**Note:** The links to SVN below goes to the latest development code. So the files may be newer than the ones included in the latest release.

In the table below you can see our localized plugins and which languages they are available in.

| Plugin                  | ca | cs | da | de | es | fr | gl | hu | it | ja | ko | lt | nl | no | pl | pt | pt\_BR | ru | sk | sv | tr | zh\_CN | zh\_TW | l10n report                                                                | SVN                                                                                                                   |
|:------------------------|:---|:---|:---|:---|:---|:---|:---|:---|:---|:---|:---|:---|:---|:---|:---|:---|:-------|:---|:---|:---|:---|:-------|:-------|:---------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------|
| Changelog               | \- | \- | \- | OK | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \-     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-changelog-plugin/l10n-status.html)            | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-changelog-plugin/src/main/resources/)                |
| Changes                 | \- | \- | \- | OK | \- | OK | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | OK     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-changes-plugin/l10n-status.html)              | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-changes-plugin/src/main/resources/)                  |
| Checkstyle              | \- | \- | \- | OK | \- | OK | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | OK     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-checkstyle-plugin/l10n-status.html)           | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-checkstyle-plugin/src/main/resources/)               |
| Dependency              | \- | \- | \- | OK | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | OK     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-dependency-plugin/l10n-status.html)           | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-dependency-plugin/src/main/resources/)               |
| DOAP                    | \- | \- | \- | OK | OK | OK | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \-     | \- | \- | \- | \- | \-     | \-     | [l10n report](/plugins/maven-doap-plugin/l10n-status.html)                 | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-doap-plugin/src/main/resources/)                     |
| Doxia Integration Tools | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK     | OK | OK | OK | OK | OK     | OK     | [l10n report](/doxia/doxia-tools/doxia-integration-tools/l10n-status.html) | [SVN](https://svn.apache.org/repos/asf/maven/doxia/doxia-sitetools/trunk/doxia-integration-tools/src/main/resources/) |
| Javadoc                 | \- | \- | \- | OK | \- | OK | \- | \- | \- | \- | \- | \- | OK | \- | \- | \- | \-     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-javadoc-plugin/l10n-status.html)              | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-javadoc-plugin/src/main/resources/)                  |
| JXR                     | \- | \- | \- | OK | \- | OK | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \-     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-jxr-plugin/l10n-status.html)                  | [SVN](https://svn.apache.org/repos/asf/maven/jxr/trunk/maven-jxr-plugin/src/main/resources/)                          |
| PMD                     | \- | \- | \- | OK | \- | OK | \- | \- | OK | \- | \- | \- | OK | \- | \- | \- | OK     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-pmd-plugin/l10n-status.html)                  | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-pmd-plugin/src/main/resources/)                      |
| Plugin                  | \- | \- | \- | OK | \- | OK | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \-     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-plugin-plugin/l10n-status.html)               | [SVN](https://svn.apache.org/repos/asf/maven/plugin-tools/trunk/maven-plugin-plugin/src/main/resources/)              |
| Project Info Reports    | \- | OK | \- | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK     | OK | OK | OK | OK | OK     | OK     | [l10n report](/plugins/maven-project-info-reports-plugin/l10n-status.html) | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-project-info-reports-plugin/src/main/resources/)     |
| Surefire report         | \- | \- | \- | OK | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \- | \-     | \- | \- | OK | \- | \-     | \-     | [l10n report](/plugins/maven-surefire-report-plugin/l10n-status.html)      | [SVN](https://svn.apache.org/repos/asf/maven/surefire/trunk/maven-surefire-report-plugin/src/main/resources/)         |
| Site                    | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK | OK     | OK | OK | OK | OK | OK     | OK     | [l10n report](/plugins/maven-site-plugin/l10n-status.html)                 | [SVN](https://svn.apache.org/repos/asf/maven/plugins/trunk/maven-site-plugin/src/main/resources/)                     |

Is your favourite plugin missing a localization for your language? Please help us expand the language support by following the instructions below.

## Localizing a Plugin

- Check out the source code for the plugin you want to add a translation to.
- Find the resource bundles that needs to be translated. They are often found in the `src/main/resources` directory.
- Copy the basefile for the bundle, i.e. the one **without** a language extension. The copy should have the same name as the original file, except for the addition of a language extension. If, for example, you want to translate the Checkstyle Plugin into Spanish, you would copy `src/main/resources/checkstyle-report.properties` to `src/main/resources/checkstyle-report_es.properties`.
- Edit the new file and translate all the properties. Do not change the formating of the file, i.e. keep the current indentation and line breaks. This makes it easy to use a graphical diff tool to find missing keys in the file.
- Convert the new file so that all non-US-ASCII characters are transformed into Unicode escapes, see below for a tool that can help with this.
- Run &quot;mvn install&quot; for the plugin.
- Configure a project to use the latest SNAPSHOT version of the plugin you are working on. Also configure the project to produce a site in the language you are adding a translation for. For Spanish, as we used in the example above, it would look like this:

  ```unknown
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-site-plugin</artifactId>
        <configuration>
          <locales>es</locales>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ```
- Run &quot;mvn site&quot; on that project to test it.
- When you are happy with your translation, create an issue in JIRA for the plugin in question, with a description like this: &quot;Add Spanish translation&quot;. Take note of the issue number.
- Create a patch file that contains your new translation. Use the issue number when you name the file:

  ```
  svn diff > MYISSUE-123.patch
  ```
- Attach your patch file to the issue in JIRA.

## Tools

There is a command line tool called **native2ascii** that can be used to convert a text file to use Unicode-encoded characters instead of native-encoded characters. This is part of the Java SDK and you can [read more about it here](http://java.sun.com/j2se/1.5.0/docs/tooldocs/windows/native2ascii.html). You use it like this:

```
native2ascii checkstyle-report_es.properties checkstyle-report_es-encoded.properties
```

### Tools to find out the charset of a file

- Unix `file` command
- [cpdetector](http://cpdetector.sourceforge.net/)
- [IntelliJ IDEA ShowEncodingPlugin](http://plugins.intellij.net/plugin/?id=24)
- [Notepad++](http://notepad-plus.sourceforge.net/)

### Tools to write a file in a given charset

Any editor like Notepad++, Eclipse, IntelliJ IDEA, ...

### Tools to convert a file from one encoding to another encoding

- Unix `iconv` command
- Notepad++

### IDE plugins

- [Properties Editor Eclipse Plugin](http://propedit.sourceforge.jp/index_en.html)

## References

Please refer to the [Java Internationalization home page](http://java.sun.com/javase/technologies/core/basic/intl/) for an introduction to the topic.

You can also refer to this Sun FAQ: [How Can I Determine the Encoding of a File?](http://developers.sun.com/global/technology/standards/reference/faqs/determining-file-encoding.html)

