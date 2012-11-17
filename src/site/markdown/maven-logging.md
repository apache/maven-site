<!---
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
# Maven 3.1.x logging

We have reached the decision that [SLF4J][1] is the best option for a logging API. SLF4J has reached a certain level of Ubiquity and while SLF4J may not be perfect, it's the de facto standard and it's pointless to try and remake another one. SLF4J is used by many prominent Java OSS projects including 15 Apache projects already.

There are many implementations to choose from, including [Logback][4] and [Log4j2][3]. All the hard work has been done. All the bridges and funnels for other systems function well, which allows others to use whatever logging implementation they like in their components while still being able to have integrated logging.

The standard Maven distribution, from Maven 3.1.0 onward, uses the [SLF4J API][5] for logging combined with the [SLF4J Simple][2] implementation. Looking at the distribution you will see the following layout where the `simplelogger.properties`, `slf4j-api-1.7.2-jar` and `slf4j-simple-1.7.2.jar` specifically relate to the SLF4J implementation.

<div class="source"><pre>
m2
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
    ├── slf4j-api-1.7.2.jar
    ├── slf4j-simple-1.7.2.jar
    └── ...
</pre></div>

## Configuring logging 

To configure logging with the [SLF4J Simple][2], you can edit the properties in the `${MAVEN_HOME}/conf/logging/simplelogger.properties` file.

<table class="table">
<tr>
<td><code>org.slf4j.simpleLogger.<b>logFile</b></code></td>
<td>The output target which can be the path to a file, or the special values "System.out" and "System.err". Default is "System.err".</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>defaultLogLevel</b></code></td>
<td>Default log level for all instances of SimpleLogger. Must be one of ("trace", "debug", "info", "warn", or "error"). If not specified, defaults to "info".</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>log.a.b.c</b></code></td>
<td>Logging detail level for a SimpleLogger instance named "a.b.c". Right-side value must be one of "trace", "debug", "info", "warn", or "error". When a SimpleLogger named "a.b.c" is initialized, its level is assigned from this property. If unspecified, the level of nearest parent logger will be used, and if none is set, then the value specified by org.slf4j.simpleLogger.defaultLogLevel will be used.</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>showDateTime</b></code></td>
<td>Set to true if you want the current date and time to be included in output messages. Default is true</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>dateTimeFormat</b></code></td>
<td>The date and time format to be used in the output messages. The pattern describing the date and time format is defined by SimpleDateFormat. If the format is not specified or is invalid, the number of milliseconds since start up will be output.</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>showThreadName</b></code></td>
<td>Set to true if you want to output the current thread name. Defaults to true.</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>showLogName</b></code></td>
<td>Set to true if you want the Logger instance name to be included in output messages. Defaults to true.</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>showShortLogName</b></code></td>
<td>Set to true if you want the last component of the name to be included in output messages. Defaults to false.</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>levelInBrackets</b></code></td>
<td>Should the level string be output in brackets? Defaults to false.</td>
</tr>
<tr>
<td><code>org.slf4j.simpleLogger.<b>warnLevelString</b></code></td>
<td>The string value output for the warn level. Defaults to WARN.</td>
</tr>
</table>

The default configuration looks like the following:

<div class="source"><pre>
# Default Maven logging configuration
#
org.slf4j.simpleLogger.defaultLogLevel=info
org.slf4j.simpleLogger.showDateTime=false
org.slf4j.simpleLogger.showThreadName=false
org.slf4j.simpleLogger.showLogName=false
org.slf4j.simpleLogger.logFile=System.out
org.slf4j.simpleLogger.levelInBrackets=true
org.slf4j.simpleLogger.log.Sisu=info
org.slf4j.simpleLogger.warnLevelString=WARNING
</pre></div>

## Changing the SLF4J implementation

If you want use a different logging implementation it is simply a matter of removing the slf4j-simple JAR from <code>lib</code> directory and replacing it with one of the alternative implementations, like [Log4j2][3] or [Logback][4]. 

[1]: http://slf4j.org
[2]: http://www.slf4j.org/apidocs/org/slf4j/impl/SimpleLogger.html
[3]: http://logging.apache.org/log4j/2.x/slf4j-impl/
[4]: http://logback.qos.ch
[5]: http://slf4j.org/apidocs/
