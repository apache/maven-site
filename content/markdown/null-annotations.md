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

# Maven Null Annotations

## Which null annotations are used within Maven?

Maven 4 provides its own annotations in [`org.apache.maven.api.annotations`](ref/4-LATEST/api/maven-api-annotations/index.html).

The following annotation types are relevant for dealing with `null` values:

1. [`@Nonnull`](ref/4-LATEST/api/maven-api-annotations/apidocs/org/apache/maven/api/annotations/Nonnull.html) and
2. [`@Nullable`](ref/4-LATEST/api/maven-api-annotations/apidocs/org/apache/maven/api/annotations/Nullable.html)

The purpose of those annotations is twofold:

1. They allow static source code analysis tools to check if developers are missing null checks or passing null for method arguments annotated with `@Nonnull`.
2. They are evaluated at run time by [Maven Dependency Injection](./maven-di.html).

## Which classes are using those annotations yet?

All [Maven 4 API modules](/ref/4-LATEST/api/index.html) are annotated with aforementioned annotations and it is recommended for plugin developers to use those annotations in their custom Maven 4 mojos as well.

## Which compile time tools support those custom annotations

Not all static source code analysis tools support custom annotations (like Maven Null annotations). The following tools/IDEs are known to work:

1. [NullAway](https://github.com/uber/NullAway) does not [require specific configuration](https://github.com/uber/NullAway/wiki/Configuration#custom-nullability-annotations) to support Maven Null annotations.
2. [IntelliJ IDEA](https://www.jetbrains.com/help/idea/annotating-source-code.html#nullability-annotations) requires [explicit configuration of the annotation class names](https://www.jetbrains.com/help/idea/annotating-source-code.html#configure-nullability-annotations).
3. [Eclipse](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftask-using_null_annotations.htm) requires [explicit configuration of the annotation class names](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Fcompiler%2Fref-preferences-errors-warnings.htm&anchor=null_annotation_names).

On the other hand some other tools don't or only partially support custom Null annotations as they rely on some built-in lists of fully qualified names (FQN) for those annotations:

- [SonarQube](https://www.sonarsource.com/products/sonarqube/#modal=sonar-summit) is having a [hardcoded list of FQNs of supported Null annotations](https://github.com/SonarSource/sonar-java/blob/675fcaf6b9f8de46dbee10fcf698b1c9ab69b3e8/java-frontend/src/main/java/org/sonar/java/model/JSymbolMetadataNullabilityHelper.java#L65-L135), however there seem to be partial support for [meta-annotations](https://community.sonarsource.com/t/use-custom-nonnull-nullable-annotations/38800)
- [SpotBugs](https://spotbugs.github.io/) is having a [hardcoded list of FQNs of supported Null annotations](https://github.com/spotbugs/spotbugs/blob/be68f15193e25461c1b815e65afea841e0f9f3ca/spotbugs/src/main/java/edu/umd/cs/findbugs/ba/jsr305/TypeQualifierResolver.java#L57-L84)

## How is Maven evaluating those annotations at run time?

[Maven Dependency Injection](./maven-di.html) is checking for injections having `@Nullable` annotations and in this case won't fail if injection is not possible.
The same applies to [Google Guice](https://github.com/google/guice/wiki/UseNullable#supported-nullable-annotations) leveraged with [Sisu](https://eclipse.dev/sisu/).

## Why is Maven not using standard annotations?

There exist multiple libraries for null annotations, probably most common these days is [JSpecify](https://jspecify.dev/) and also some dormant libraries from the past like [JSR 305](https://jcp.org/en/jsr/detail?id=305).
In order to allow Maven null annotations to coexist with any other standard annotations in any version in the Maven classpath we took the decision to come up with custom annotations in our own namespace.
That way the API does not have any 3rd party dependencies.