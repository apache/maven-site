# Maven 3.1.x logging
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
Maven uses [Plexus logging API][6] with basic Maven implementation writing
to stdout.

We have reached the decision that [SLF4J][1] is the best option for a logging API:
SLF4J has reached a certain level of ubiquity and while SLF4J may not be perfect,
it\'s the de facto standard and it\'s pointless to try and remake another one.
There are many implementations to choose from, including [Logback][4] and [Log4j2][3].
All the hard work has been done. All the bridges and funnels for other systems function well,
which allows others to use whatever logging implementation they like in their components,
while still being able to have integrated logging.

The standard Maven distribution, from Maven 3.1.0 onward, uses the [SLF4J API][5] for logging
combined with the [SLF4J Simple][2] implementation (or [a customized version since 3.5.0][10]). Future versions may use a more advanced
implementation, but we chose to start simple.

Looking at the distribution you will
see the following layout where the `simplelogger.properties`, `slf4j-api-x.y.z-jar` and
`slf4j-simple-x.y.z.jar` (or `maven-slf4j-provider-3.x.y.jar` since 3.5.0) specifically relate to the SLF4J implementation:

<div class="source"><pre>
apache-maven-3.x.y
├── LICENSE.txt
├── NOTICE.txt
├── README.txt
├── bin
│   └── ...
├── boot
│   └── ...
├── conf
│   ├── logging
│   │   └── simplelogger.properties
│   └── settings.xml
└── lib
    ├── ...
    ├── slf4j-api-x.y.z.jar
    ├── slf4j-simple-x.y.z.jar or maven-slf4j-provider-3.x.y.jar
    └── ...
</pre></div>

## Configuring logging

To configure logging with the [SLF4J Simple][2], you can edit the properties in the
`${maven.home}/conf/logging/simplelogger.properties` file. Please see the linked reference documentation
for details.

Every entry in this file can be overridden via Maven's JVM system properties by using the `-D` flag in [MAVEN_OPTS][9], for example:

- `MAVEN_OPTS=-Dorg.slf4j.simpleLogger.showThreadName=true mvn <goals>` will add the thread name to every logging line,
- `MAVEN_OPTS=-Dorg.slf4j.simpleLogger.showLogName=true mvn <goals>` will add the logger name to every logging line,

The default SLF4J configuration for Maven is listed [here][8], where you can see other useful property names to customise logging.

## Changing the SLF4J implementation

If you want use a different logging implementation it is simply a matter of removing the slf4j-simple JAR
from the `lib` directory and replacing it with one of the alternative implementations, like [Log4j2][3] or [Logback][4].

See SLF4J documentation for more details on [swapping "SLF4J bindings"][7].

Note: with the introduction of colors in Maven 3.5.0, perhaps an equivalent work to [`maven-slf4j-provider`][10] may be required or you'll loose colors.

## Maven implemenation details

[More technical details](/ref/current/maven-embedder/logging.html) can be found in Maven release reference documentation.

[1]: http://slf4j.org
[2]: https://www.slf4j.org/apidocs/org/slf4j/impl/SimpleLogger.html
[3]: https://logging.apache.org/log4j/2.x/log4j-slf4j-impl/
[4]: http://logback.qos.ch
[5]: https://www.slf4j.org/apidocs/
[6]: https://codehaus-plexus.github.io/plexus-containers/plexus-container-default/apidocs/org/codehaus/plexus/logging/package-summary.html
[7]: https://www.slf4j.org/manual.html#swapping
[8]: https://github.com/apache/maven/blob/master/apache-maven/src/assembly/maven/conf/logging/simplelogger.properties
[9]: https://maven.apache.org/configure.html
[10]: https://maven.apache.org/ref/current/maven-slf4j-provider/
