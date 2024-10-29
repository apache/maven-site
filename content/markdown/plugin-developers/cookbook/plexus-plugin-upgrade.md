---
title: Cookbook - How To Upgrade from Plexus Javadoc Tags to Plexus Java Annotations
author: 
  - Hervé Boutemy
date: 2012-06-02
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
# Cookbook: How To Upgrade from Plexus Javadoc Tags to Plexus Java Annotations?

## Summary

This recipe describes how to upgrade from Plexus Javadoc Tags to Plexus Java Annotations when defining a Plexus component\.

This is done in 2 steps:

1. replace the [deprecated `plexus-maven-plugin`](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/), which only supports Plexus Javadoc Tags, with its [successor: `plexus-component-metadata`](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-metadata/), which support both Plexus Javadoc Tags and Plexus Java Annotations,
1. update sources with [Java Annotations for Plexus](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-annotations/)\.

**Notice**: if you&apos;re targeting components for Maven 3\.1\.0\+, using [`@Named`/`@Inject` JSR\-330 annotations](/maven\-jsr330\.html) instead of `@Component`/`@Requirement` Plexus Java Annotations may be a good next step

## Prerequisite Plugins

Here is the list of the plugins used:

|**Plugin**|**Version**|
|---|---|
|[`plexus-maven-plugin`](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/)|1\.3\.8|
|[`plexus-component-metadata`](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-metadata/)|1\.7\.1|
## Equivalence Table

||**plexus\-maven\-plugin**|**plexus\-component\-metadata**|
|:---:|:---:|:---:|
||[project](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/) / [plugin info](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/plugin\-info\.html)|[project](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-metadata/) / [plugin info](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-metadata/plugin\-info\.html)|
|latest|1\.3\.8|1\.7\.1|
|phase|process\-sources|process\-classes|
|goals|[`descriptor`](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/descriptor\-mojo\.html)|[`generate-metadata`](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-metadata/generate\-metadata\-mojo\.html)|
||[`merge-descriptors`](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/merge\-descriptors\-mojo\.html)|see `staticMetadataDirectory` parameter    
default: `${project.basedir}/src/main/resources/META-INF/plexus`|
||[`test-descriptor`](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/test\-descriptor\-mojo\.html)|[`generate-test-metadata`](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-metadata/generate\-test\-metadata\-mojo\.html)|
||[`test-merge-descriptors`](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/test\-merge\-descriptors\-mojo\.html)|see `testStaticMetadataDirectory` parameter    
default: `${project.basedir}/src/test/resources/META-INF/plexus`|
||[`components-report`](https://codehaus\-plexus\.github\.io/plexus\-maven\-plugin/components\-report\-mojo\.html)|reporting feature not available|
|source annotations|javadoc tags:    
`@plexus.component`, `@plexus.requirement`, `@plexus.configuration`  |javadoc tags \+ [`plexus-component-annotations`](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-annotations/) Java 5 annotations:   
[`@Component`](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-annotations/apidocs/org/codehaus/plexus/component/annotations/Component\.html), [`@Requirement`](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-annotations/apidocs/org/codehaus/plexus/component/annotations/Requirement\.html), [`@Configuration`](https://codehaus\-plexus\.github\.io/plexus\-containers/plexus\-component\-annotations/apidocs/org/codehaus/plexus/component/annotations/Configuration\.html),|
## Recipe

### Plugin Configuration

In your `pom.xml`, replace `plexus-maven-plugin` configuration:

```
<project>
  <build>
    <plugins>
      <plugin>
        <groupId>org.codehaus.plexus</groupId>
        <artifactId>plexus-maven-plugin</artifactId>
        <version>1.3.8</version>
        <executions>
          <execution>
            <goals>
              <goal>descriptor</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
  </build>
</project>
```

with corresponding `plexus-component-metadata` configuration:

```
<project>
  <build>
    <plugins>
      <plugin>
        <groupId>org.codehaus.plexus</groupId>
        <artifactId>plexus-component-metadata</artifactId>
        <version>1.7.1</version>
        <executions>
          <execution>
            <goals>
              <goal>generate-metadata</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
  </build>
</project>
```

If `merge-descriptors` is used, move the handwritten xml file to `${project.basedir}/src/main/resources/META-INF/plexus`\.

### Replacing Plexus Javadoc Tags with Plexus Java 5 Annotations

In your `pom.xml`, add `plexus-component-annotations` dependency:

```
<project>
  <dependencies>
    <dependency>
      <groupId>org.codehaus.plexus</groupId>
      <artifactId>plexus-component-annotations</artifactId>
      <version>1.7.1</version>
    </dependency>
  </dependencies>
</project>
```

In your java sources, replace javadoc tags:

```
/**
 * @plexus.component role="foo.MyComponent" role-hint="hint-value"
 */
public class MyComponentImplementation
    implements MyComponent
{
    /**
     * @plexus.requirement
     */
    private InjectedComponent;
}
```

with corresponding Java 5 annotations

```
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component( role = MyComponent.class, hint = "hint-value" )
public class MyComponentImplementation
    implements MyComponent
{
    @Requirement
    private InjectedComponent;
}
```

