---
title: Guide to using Modello
author: 
  - Jason van Zyl
  - Hervé Boutemy
date: 2009-03-01
---

<!-- Licensed to the Apache Software Foundation (ASF) under one-->
<!-- or more contributor license agreements.  See the NOTICE file-->
<!-- distributed with this work for additional information-->
<!-- regarding copyright ownership.  The ASF licenses this file-->
<!-- to you under the Apache License, Version 2.0 (the-->
<!-- "License"); you may not use this file except in compliance-->
<!-- with the License.  You may obtain a copy of the License at-->
<!---->
<!--   http://www.apache.org/licenses/LICENSE-2.0-->
<!---->
<!-- Unless required by applicable law or agreed to in writing,-->
<!-- software distributed under the License is distributed on an-->
<!-- "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY-->
<!-- KIND, either express or implied.  See the License for the-->
<!-- specific language governing permissions and limitations-->
<!-- under the License.-->
<!-- NOTE: For help with the syntax of this file, see:-->
<!-- http://maven.apache.org/doxia/references/apt-format.html-->
# Guide to using Modello

[Modello](https://codehaus\-plexus\.github\.io/modello/) is a tool for generating resources from a simple model\. From a [simple model](https://codehaus\-plexus\.github\.io/modello/modello\.html) you can generate things like:

- Java sources
- XML serialization code for the model
- XML deserialization code for model
- Model documentation
- XSD

A typical modello model looks like the following:

```
<model xmlns="http://codehaus-plexus.github.io/MODELLO/2.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://codehaus-plexus.github.io/MODELLO/2.0.0 https://codehaus-plexus.github.io/modello/xsd/modello-2.0.0.xsd"
       xml.namespace="https://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/${version}"
       xml.schemaLocation="https://maven.apache.org/xsd/archetype-descriptor-${version}.xsd">
  <id>archetype-descriptor</id>
  <name>ArchetypeDescriptor</name>
  <description>
    <![CDATA[
    <p>This is a reference for the Archetype descriptor used to describe archetypes's metadata.</p>
    <p>The metadata about an archetype is stored in the <code>archetype-metadata.xml</code> file located
    in the <code>META-INF/maven</code> directory of its jar file.</p>]]>
  </description>

  <defaults>
    <default>
      <key>package</key>
      <value>org.apache.maven.archetype.metadata</value>
    </default>
  </defaults>

  <classes>
    <class rootElement="true" xml.tagName="archetype-descriptor">
      <name>ArchetypeDescriptor</name>
      <version>1.0.0+</version>
      <superClass>AbstractArchetypeDescriptor</superClass>
      <fields>
        <field xml.attribute="true">
          <name>name</name>
          <version>1.0.0+</version>
          <type>String</type>
          <required>true</required>
          <description>Name of the Archetype, that will be displayed to the user when choosing an archetype.</description>
        </field>
        <field xml.attribute="true">
          <name>partial</name>
          <version>1.0.0+</version>
          <type>boolean</type>
          <required>false</required>
          <description>Is this archetype representing a full Maven project or only parts?</description>
        </field>
        <field>
          <name>requiredProperties</name>
          <version>1.0.0+</version>
          <description>List of required properties to generate a project from this archetype.</description>
          <association>
            <type>RequiredProperty</type>
            <multiplicity>*</multiplicity>
          </association>
        </field>
      </fields>
    </class>

    <class>
      <name>ModuleDescriptor</name>
      <version>1.0.0+</version>
      <superClass>AbstractArchetypeDescriptor</superClass>
      <fields>
        <field xml.attribute="true">
          <name>id</name>
          <version>1.0.0+</version>
          <type>String</type>
          <required>true</required>
          <description>The module's artifactId.</description>
        </field>
        <field xml.attribute="true">
          <name>dir</name>
          <version>1.0.0+</version>
          <type>String</type>
          <required>true</required>
          <description>The module's directory.</description>
        </field>
        <field xml.attribute="true">
          <name>name</name>
          <version>1.0.0+</version>
          <type>String</type>
          <required>true</required>
          <description>The module's name.</description>
        </field>
      </fields>
    </class>

    <class>
      <name>AbstractArchetypeDescriptor</name>
      <version>1.0.0+</version>
      <fields>
        <field xdoc.separator="blank">
          <name>fileSets</name>
          <version>1.0.0+</version>
          <association>
            <type>FileSet</type>
            <multiplicity>*</multiplicity>
          </association>
          <required>true</required>
          <description>File sets definition.</description>
        </field>
        <field xdoc.separator="blank">
          <name>modules</name>
          <version>1.0.0+</version>
          <association>
            <type>ModuleDescriptor</type>
            <multiplicity>*</multiplicity>
          </association>
          <required>false</required>
          <description>Modules definition.</description>
        </field>
      </fields>
    </class>

    <class>
      <name>FileSet</name>
      <version>1.0.0+</version>
      <description><![CDATA[
        A fileset defines the way the project's files located in the jar file are used by the Archetype Plugin to generate a project.
        If file or directory name contains <code>__<i>property</i>__</code> pattern, it is replaced with corresponding property value.
        ]]></description>
      <fields>
        <field xml.attribute="true">
          <name>filtered</name>
          <version>1.0.0+</version>
          <type>boolean</type>
          <required>false</required>
          <description><![CDATA[
            Filesets can be filtered, which means the selected files will be used as
            <a href="https://velocity.apache.org/engine/1.5/user-guide.html">Velocity templates</a>.
            They can be non-filtered, which means the selected files will be copied without modification.
          ]]></description>
        </field>
        <field xml.attribute="true">
          <name>packaged</name>
          <version>1.0.0+</version>
          <type>boolean</type>
          <required>false</required>
          <description>Filesets can be packaged, which means the selected files will be generated/copied in a directory
           structure that is prepended by the package property. They can be non-packaged, which means that the selected
           files will be generated/copied without that prepend.</description>
        </field>
        <field xml.attribute="true">
          <name>encoding</name>
          <version>1.0.0+</version>
          <type>String</type>
          <required>false</required>
          <description>Encoding to use when filtering content.</description>
        </field>
        <field>
          <name>directory</name>
          <version>1.0.0+</version>
          <type>String</type>
          <required>false</required>
          <defaultValue/>
          <description>The directory where the files will be searched for, which is also the directory where the
           project's files will be generated.</description>
        </field>
        <field>
          <name>includes</name>
          <version>1.0.0+</version>
          <association>
            <type>String</type>
            <multiplicity>*</multiplicity>
          </association>
          <required>false</required>
          <description>Inclusion definition "à la" Ant.</description>
        </field>
        <field>
          <name>excludes</name>
          <version>1.0.0+</version>
          <association>
            <type>String</type>
            <multiplicity>*</multiplicity>
          </association>
          <required>false</required>
          <description>Exclusion definition "à la" Ant.</description>
        </field>
      </fields>
      <codeSegments>
        <codeSegment>
          <code><![CDATA[
    public String toString()
    {
        return
            getDirectory() + " ("
                + ( isFiltered() ? "Filtered" : "Copied" )
                + "-"
                + ( isPackaged() ? "Packaged" : "Flat" )
            + ") ["
                + org.codehaus.plexus.util.StringUtils.join( getIncludes().iterator(), ", " )
                + " -- "
                + org.codehaus.plexus.util.StringUtils.join( getExcludes().iterator(), ", " )
            + "]";

    }
                    ]]></code>
        </codeSegment>
      </codeSegments>
    </class>

    <class>
      <name>RequiredProperty</name>
      <version>1.0.0+</version>
      <description>Definition of a property required when generating a project from this archetype.</description>
      <fields>
        <field xml.attribute="true">
          <name>key</name>
          <version>1.0.0+</version>
          <type>String</type>
          <required>true</required>
          <description>Key value of the property.</description>
        </field>
        <field>
          <name>defaultValue</name>
          <type>String</type>
          <required>false</required>
          <description>Default value of the property.</description>
        </field>
        <field>
          <name>validationRegex</name>
          <version>1.1.0+</version>
          <type>String</type>
          <required>false</required>
          <description>A regular expression used to validate the property.</description>
        </field>
      </fields>
    </class>
  </classes>
</model>
```

To utilize Modello, you would configure the `modello-maven-plugin` something like the following where you want to generate the Java sources for the model, the xpp3 serialization code and the xpp3 deserialization code \(see [modello\-plugin\-xpp3](https://codehaus\-plexus\.github\.io/modello/modello\-plugins/modello\-plugin\-xpp3/) for more details\):

```

<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.codehaus.modello</groupId>
        <artifactId>modello-maven-plugin</artifactId>
        <version>1.8.3</version>
        <executions>
          <execution>
            <goals>
              <!-- Generate the xpp3 reader code -->
              <goal>xpp3-reader</goal>
              <!-- Generate the xpp3 writer code -->
              <goal>xpp3-writer</goal>
              <!-- Generate the Java sources for the model itself -->
              <goal>java</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <models>
            <model>src/main/mdo/archetype-descriptor.mdo</model>
          </models>
          <version>1.0.0</version>
          <useJava5>true</useJava5>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ...
</project>
```

