## Guide for Resolver Transport
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

Historically, since Maven 2.x, the only transport used by Maven was Maven Wagon. Since introduction of Resolver in
Maven 3.x it did "wrap" it (in maven-resolver-transport-wagon) module, and continued to use it. Still, Wagon is 
deeply integrated with old Plexus DI container and many of not all of its configuration are bound to be set in 
Plexus XML, that is not type safe, nor validated nor future-proof.

**Starting with Maven 3.9.0 release, the "default transport" (the default transport used by Maven Resolver)
changed from ancient Wagon to modern transport-http aka "native HTTP" transport.**

Take a peek at Resolver [architecture diagram](https://maven.apache.org/resolver/) and the "transport" box
contains several implementations, and this number will increase.

Resolver contains configuration for many aspects that cover transport as well (see the keys
prefixed with "aether.connector."). They can be found on 
[resolver configuration page](https://maven.apache.org/resolver/configuration.html).

## Switching Between Transports

The transport used by resolver can be controlled using `-Dmaven.resolver.transport` user property, where accepted
values are `native` (the default), `wagon` (uses legacy Wagon) and `auto` (delegates to resolver to sort
out defaults).

The accepted values of `maven.resolver.transport` user property:
* `default` (implied if not present), does `auto` by default
* `native` forces use of transport-http
* `wagon` forces use of transport-wagon
* `auto` delegates to existing resolver match to choose transport (see `aether.priority.<class>` and related properties, or just rely on component priorities).

Note: "forces" means that resolver will use given transport even if other, higher prioritized component is present
on classpath.

Given the priority of `native` is higher than `wagon`, and starting with Maven 3.9.0, both are present on resolver
classpath, `native` is chosen by default. This behaviour preserves existing (pre 3.9.0) behaviour of Maven Resolver,
that allowed third party transports to be added (by copying them to `lib/ext`) that have higher priorities. Still,
fallback is possible with explicit use of `native` or `wagon` values.

## Custom HTTP Headers

In all HTTP transports, you can add your custom HTTP headers like this:

```xml
<settings>
  <servers>
    <server>
      <id>my-server</id>
      <configuration>
        <httpHeaders>
          <property>
            <name>Foo</name>
            <value>Bar</value>
          </property>
        </httpHeaders>
      </configuration>
    </server>
  </servers>
</settings>
```

It is important to understand that the above approach does not allow you to turn off all the default HTTP headers; 
nor does it allow you to specify headers on a per-method basis. However, this configuration remains available in all 
transports that support headers, like HTTP transports are (and works for "native" but also Wagon transport).

## The Devil Is In Details

Depending on transport you use (`native`, `wagon` or something else), you will want to refer to corresponding
page(s) for detailed configuration options:

* For Native HTTP transport detailed configuration user properties are [collected on this page](https://maven.apache.org/resolver/configuration.html).
* For Wagon you want to use `-Dmaven.resolver.transport=wagon` user property and [configuration from this page](https://maven.apache.org/guides/mini/guide-http-settings.html).
* For any third party transport, please refer to its own documentation.