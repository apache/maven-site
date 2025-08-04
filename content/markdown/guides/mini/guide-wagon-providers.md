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

# Guide to Selecting Alternative Wagon Providers

By default, Maven uses the `java.net.URLConnection` (`HttpURLConnection`) classes provided with the JDK to access repositories that use the HTTP/HTTPs protocols. Unfortunately, since this implementation contains certain bugs, Maven users may find themselves unable to connect to servers that demand some configurations. A couple examples of weird behavior include line-wrapping the Authorization header's Base64 value when passwords are long, and using cached values in preemptive authentication for successive connections to the same server.

Maven 2.2.0 attempted to amend this problem by switching over to a Wagon implementation that's based on Apache HttpClient. Unfortunately, it soon became apparent that HttpClient doesn't support NTLM (at least, version 2), which effectively means users behind NTLMv2 proxies cannot use Maven 2.2.0.

To hopefully resolve this once and for all, Maven 2.2.1 will contain support for specifying which Wagon provider you want to use for a given protocol during the build. The provider name will then be appended to the protocol using the format `<protocol>-<provider>` to form the component role-hint for the Wagon.

As of Maven 2.2.1, there are two ways to specify which Wagon provider should be used: via the command line, or in the `<server>` configuration section of the `settings.xml`.

## Command-Line Configuration

To specify the Wagon provider from the command line, simply use the `-Dmaven.wagon.provider.<protocol>=<provider-name>` command-line option, like the following:

```
mvn -Dmaven.wagon.provider.http=httpclient clean install
```

This instructs Maven to use the HttpClient-based Wagon implementation for connections to HTTP repositories.

## `settings.xml` Configuration

To specify which Wagon provider to use for a particular server, modify your `settings.xml` file to add the `<wagonProvider>` configuration to your `<server>` entry, like the following:

```xml
<settings>
  [...]
  <servers>
    <server>
      <id>central</id>
      <configuration>
        <wagonProvider>httpclient</wagonProvider>
        [...]
      </configuration>
    </server>
```

## Available Wagon Providers

Maven 2.2.1 provides two providers for HTTP/HTTPS Wagons: `lightweight` and `httpclient`. If you add a new HTTP Wagon implementation via build extension, you'll need to make sure the extension binds its Wagon components to role-hints of the form `<protocol>-<provider>` in order to allow users to specify your alternative Wagon provider. For instance, the HttpClient HTTP Wagon component definition looks like this:

```xml
<component>
  <role>org.apache.maven.wagon.Wagon</role>
  <role-hint>http-httpclient</role-hint>
  <implementation>org.apache.maven.wagon.providers.http.HttpWagon</implementation>
  <instantiation-strategy>per-lookup</instantiation-strategy>
</component>
```

**NOTE:** The default provider for HTTP/HTTPS Wagons is `lightweight`.

