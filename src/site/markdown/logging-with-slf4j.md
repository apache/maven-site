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
# Maven 3 logging with SLF4J

The standard Maven distribution, from Maven 3.1 onward, uses the [SLF4J API][1] for logging combined with the [SLF4J Simple][1] implementation. Looking at the distribution you will see the following layout where the `simplelogger.properties`, `slf4j-api-1.7.2-jar` and `slf4j-simple-1.7.2.jar` specifically relate to the SLF4J implementation.

<pre>
m2
├── LICENSE.txt
├── NOTICE.txt
├── README.txt
├── bin
│   ├── m2.conf
│   ├── mvn
│   ├── mvn.bat
│   ├── mvnDebug
│   ├── mvnDebug.bat
│   └── mvnyjp
├── boot
│   └── plexus-classworlds-2.4.jar
├── conf
│   ├── logging
│   │   └── simplelogger.properties
│   └── settings.xml
└── lib
    ├── ...
    ├── slf4j-api-1.7.2.jar
    ├── slf4j-simple-1.7.2.jar
    └── ...
</pre>

## Configuring logging 

To configure logging with the [SLF4J Simple][2] you can edit the properties in the `${MAVEN_HOME}/conf/logging/simplelogger.properties` file.

<table class="table">
<tr>
<td><b>org.slf4j.simpleLogger.logFile</b></td>
<td>The output target which can be the path to a file, or the special values "System.out" and "System.err". Default is "System.err".</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.defaultLogLevel</b></td>
<td>Default log level for all instances of SimpleLogger. Must be one of ("trace", "debug", "info", "warn", or "error"). If not specified, defaults to "info".</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.log.a.b.c</b></td>
<td>Logging detail level for a SimpleLogger instance named "a.b.c". Right-side value must be one of "trace", "debug", "info", "warn", or "error". When a SimpleLogger named "a.b.c" is initialized, its level is assigned from this property. If unspecified, the level of nearest parent logger will be used, and if none is set, then the value specified by org.slf4j.simpleLogger.defaultLogLevel will be used.</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.showDateTime</b></td>
<td>Set to true if you want the current date and time to be included in output messages. Default is true</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.dateTimeFormat</b></td>
<td>The date and time format to be used in the output messages. The pattern describing the date and time format is defined by SimpleDateFormat. If the format is not specified or is invalid, the number of milliseconds since start up will be output.</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.showThreadName</b></td>
<td>Set to true if you want to output the current thread name. Defaults to true.</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.showLogName</b></td>
<td>Set to true if you want the Logger instance name to be included in output messages. Defaults to true.</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.showShortLogName</b></td>
<td>Set to true if you want the last component of the name to be included in output messages. Defaults to false.</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.levelInBrackets</b></td>
<td>Should the level string be output in brackets? Defaults to false.</td>
</tr>
<tr>
<td><b>org.slf4j.simpleLogger.warnLevelString</b></td>
<td>The string value output for the warn level. Defaults to WARN.</td>
</tr>
</table>

The default configuration looks like the following:

<pre>
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
</pre>

## Changing the SLF4J implementation

If you want use a different logging implementation it is simply a matter of removing the slf4j-simple JAR from <code>lib</code> directory and replacing it with one of the alternative implementations, like [Log4j2][3] or [Logback][4]. The [m2e][5] project uses [SLF4J][1] with [Logback][4], for example. 

[1]: http://slf4j.org
[2]: http://www.slf4j.org/api/org/slf4j/impl/SimpleLogger.html
[3]: http://logging.apache.org/log4j/2.x/slf4j-impl/
[4]: http://logback.qos.ch
[5]: http://eclipse.org/m2e/
