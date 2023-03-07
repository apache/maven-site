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

Starting with Maven 3.9.0 release, the "default transport" (the default transport used by Maven Resolver)
changed from ancient Wagon to modern resolver "native HTTP" transport.

Take a peek at Resolver [architecture diagram](https://maven.apache.org/resolver/) and the "transport" box
contains several implementations, and this number will increase.

Historically, since Maven 2.x the only transport used by Maven was Maven Wagon, and Resolver did "wrap" it
(in maven-resolver-transport-wagon) module, but Wagon is deeply integrated with old Plexus DI container and
many of not all of it's configuration are bound to be set in Plexus XML, that is not type safe, nor validated
nor future-proof.

Resolver on the other hand contains configuration for many aspects that cover transport as well (see the keys
prefixed with "aether.connector."). They can be found on 
[resolver configuration page](https://maven.apache.org/resolver/configuration.html).

## Switching between transports

The transport used by resolver can be controlled using `-Dmaven.resolver.transport` user property, where accepted
values are `native` (the default), `wagon` (uses legacy Wagon) and `auto` (delegates to resolver to sort
out defaults).

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

It is important to understand that the above approach does not allow you to turn off all of the default HTTP headers; 
nor does it allow you to specify headers on a per-method basis. However, this configuration remains available in all 
transports that support headers, like HTTP transports are.

## The details

Depending on transport you want to use (native, wagon or something else), you will want to refer to corresponding
page(s) for detailed configuration options:

* For Native HTTP transport detailed configuration user properties are [collected on this page](https://maven.apache.org/resolver/configuration.html).
* For Wagon you want to use `-Dmaven.resolver.transport=wagon` user property and [configuration from this page](https://maven.apache.org/guides/mini/guide-http-settings.html).
* For any third party transport, please refer to its own documentation.