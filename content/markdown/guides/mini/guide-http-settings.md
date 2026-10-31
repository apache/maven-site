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

# Advanced configuration to HttpClient HTTP Wagon

**This page contains Wagon specific information, but the default transport in Maven 3.9.0+ is NOT Wagon anymore. Make sure to check the [Resolver Transport guide](./guide-resolver-transport.html) first, as things have changed.**

<!-- MACRO{toc} -->

Maven bundles `wagon-http` for HTTP and HTTPS; see the [Guide to Wagon Providers](./guide-wagon-providers.html). With Maven 3.9.0 and later, Wagon is used when you select it with `-Dmaven.resolver.transport=wagon`. The native HTTP transport reads only the `<all>` block of `httpConfiguration` (`get`, `head` and `put` are ignored) and warns about the deprecation, so the per-method configuration below applies to Wagon only.

This page documents the `<configuration>` block that
[wagon-http](https://maven.apache.org/wagon/wagon-providers/wagon-http/) reads — headers, request parameters,
timeouts, preemptive authentication and authentication scope — and how each setting
maps onto Apache HttpClient 4.5. It describes Wagon 3.5.3, the version Maven 3.9.x and
3.10.x ship with. Four behaviours marked "Wagon 3.5.3" below change in later Wagon
releases, so check the release notes of the Wagon version you use.

Everything here comes from `AbstractHttpClientWagon` in `wagon-http-shared`, so it
applies equally to [wagon-webdav-jackrabbit](https://maven.apache.org/wagon/wagon-providers/wagon-webdav-jackrabbit/),
which extends the same class. It does **not** apply to
[wagon-http-lightweight](https://maven.apache.org/wagon/wagon-providers/wagon-http-lightweight/), which is a
different implementation built on `java.net.HttpURLConnection`.

Settings that are global rather than per-server are exposed as system properties
instead; see [System properties](#System_properties) at the end.

## Where configuration goes

In a Maven build, per-server configuration lives in `settings.xml`, inside the
`<server>` whose `<id>` matches the repository or `distributionManagement` id:

```xml
<settings>
  <servers>
    <server>
      <id>my-repository</id>
      <username>deploy</username>
      <password>secret</password>
      <configuration>
        <!-- everything on this page goes here -->
      </configuration>
    </server>
  </servers>
</settings>
```

The elements inside `<configuration>` are mapped onto the Wagon instance as Plexus
component configuration: element names correspond to the fields and setters of the
provider class. Programmatically the same settings are the `setHttpConfiguration`,
`setHttpHeaders`, `setBasicAuthScope`, `setProxyBasicAuthScope` and
`setInitialBackoffSeconds` methods of `AbstractHttpClientWagon`.

## `httpConfiguration`

`httpConfiguration` is the main entry point. It is an
[`HttpConfiguration`](https://maven.apache.org/wagon/apidocs/org/apache/maven/wagon/shared/http/HttpConfiguration.html)
with five optional children, one for "every method" plus one per HTTP method the
providers issue:

| Element | Applies to |
| --- | --- |
| `all` | every request |
| `get` | `GET` — downloads |
| `put` | `PUT` — uploads |
| `head` | `HEAD` — `resourceExists` |
| `mkcol` | `MKCOL` — WebDAV collection creation |

Each is an
[`HttpMethodConfiguration`](https://maven.apache.org/wagon/apidocs/org/apache/maven/wagon/shared/http/HttpMethodConfiguration.html)
with the same six properties:

| Property | Type | Default |
| --- | --- | --- |
| `useDefaultHeaders` | boolean | `true` |
| `headers` | `Properties` | empty |
| `params` | `Properties` | empty |
| `connectionTimeout` | int, milliseconds | 60000 |
| `readTimeout` | int, milliseconds | 1800000, or the value of the `maven.wagon.rto` system property |
| `usePreemptive` | boolean | `false` |

```xml
<configuration>
  <httpConfiguration>
    <all>
      <connectionTimeout>10000</connectionTimeout>
      <readTimeout>60000</readTimeout>
      <headers>
        <User-Agent>my-build/1.0</User-Agent>
      </headers>
      <params>
        <http.protocol.max-redirects>5</http.protocol.max-redirects>
      </params>
    </all>
    <put>
      <readTimeout>600000</readTimeout>
    </put>
  </httpConfiguration>
</configuration>
```

### How `all` and the per-method block are combined

For each request, `HttpConfiguration.getMethodConfiguration(HttpUriRequest)` looks at
the method name and merges `all` with the matching block. Anything that is not `GET`,
`PUT`, `HEAD` or `MKCOL` gets `all` alone.

The merge, in `ConfigurationUtils.merge(base, local)`:

- if only one of the two is present, it is used as-is;
- otherwise `all` is copied and then the per-method block is layered on top:
  `headers` and `params` are merged key by key (the per-method value wins);
  `useDefaultHeaders` is taken from the per-method block if it was set explicitly;
  `connectionTimeout` and `readTimeout` are taken from the per-method block only if
  they differ from `Wagon.DEFAULT_CONNECTION_TIMEOUT` and `Wagon.DEFAULT_READ_TIMEOUT`
  respectively.

That last rule has a consequence worth knowing in Wagon 3.5.3: you cannot use a per-method block to
set a timeout *back* to the default value. Writing `<readTimeout>1800000</readTimeout>`
inside `<get>` is indistinguishable from not writing it at all, and the value from
`<all>` survives.

**Wagon 3.5.3: `usePreemptive` is not carried through the merge at all.** The copy step does not
copy it and the merge step does not apply it, so if both `<all>` and the block for
the method in question are present, the effective `usePreemptive` is always `false`.
Set it in `<all>` and leave that method's own block out, or set it only in the
method block and leave `<all>` out. This is what
`HttpWagonPreemptiveTest` does — it configures nothing but
`setAll(new HttpMethodConfiguration().setUsePreemptive(true))`.

### Writing `headers` and `params`

Both are `java.util.Properties`, and Plexus accepts two spellings for those. The
short form uses the key as the element name:

```xml
<headers>
  <User-Agent>my-build/1.0</User-Agent>
</headers>
```

The long form spells out each entry and works with every Plexus component
configurator:

```xml
<headers>
  <property>
    <name>User-Agent</name>
    <value>my-build/1.0</value>
  </property>
</headers>
```

Use the long form for keys that are not valid XML element names — most of the `params`
keys below contain dots, which is fine, but nothing stops a header name from
containing characters that are not.

### `headers`

Headers from the configuration are applied with `setHeader`, so they replace any
header of the same name that the provider had already added.

`User-Agent` gets special handling: `getUserAgent(HttpUriRequest)` looks it up in
`httpHeaders` first, then in the merged method configuration's `headers`, and the
result is set on the request. If nothing supplies one, no `User-Agent` is added by
Wagon.

### `useDefaultHeaders`

When `true` — the default — every request gets

```
Cache-control: no-cache
Pragma: no-cache
```

before your own headers are applied. Set it to `false` to suppress them.

### `params`

`<params>` is an escape hatch onto HttpClient's `RequestConfig`. Only the keys below
are recognised; anything else is silently ignored. Values may optionally be prefixed
with a legacy type coercion of the form `%type,value` — for example
`%Integer,3` — in which case only the part after the comma is used.

| Parameter | `RequestConfig` setting |
| --- | --- |
| `http.socket.timeout` | `setSocketTimeout(int)` |
| `http.connection.timeout` | `setConnectTimeout(int)` |
| `http.connection.stalecheck` | `setStaleConnectionCheckEnabled(boolean)` |
| `http.conn-manager.timeout` | `setConnectionRequestTimeout(int)` |
| `http.protocol.expect-continue` | `setExpectContinueEnabled(boolean)` |
| `http.protocol.handle-authentication` | `setAuthenticationEnabled(boolean)` |
| `http.protocol.handle-redirects` | `setRedirectsEnabled(boolean)` |
| `http.protocol.max-redirects` | `setMaxRedirects(int)` |
| `http.protocol.allow-circular-redirects` | `setCircularRedirectsAllowed(boolean)` |
| `http.protocol.reject-relative-redirect` | `setRelativeRedirectsAllowed(!value)` |
| `http.protocol.handle-content-compression` | `setContentCompressionEnabled(boolean)` |
| `http.protocol.handle-uri-normalization` | `setNormalizeUri(boolean)` |
| `http.protocol.cookie-policy` | `setCookieSpec(String)` |
| `http.route.default-proxy` | `setProxy(HttpHost.create(value))` |
| `http.route.local-address` | `setLocalAddress(InetAddress)`; an unresolvable name is ignored |
| `http.auth.target-scheme-pref` | `setTargetPreferredAuthSchemes(...)`, comma-separated |
| `http.auth.proxy-scheme-pref` | `setProxyPreferredAuthSchemes(...)`, comma-separated |

Two of these interact with settings applied elsewhere. `params` are applied *after*
the request has already been given the Wagon's own connect and read timeouts, so
`http.socket.timeout` and `http.connection.timeout` take precedence over
`connectionTimeout`/`readTimeout` and over `setTimeout`/`setReadTimeout`. Also, the
cookie policy is set to `CookieSpecs.BROWSER_COMPATIBILITY` before `params` are
applied, so `http.protocol.cookie-policy` overrides that default.

`Expect: 100-continue` is enabled unconditionally for `PUT` requests, before `params`
are applied; `http.protocol.expect-continue` can therefore turn it off. It is
deliberately not enabled for `MKCOL`, which has no body.

### Ignoring cookies

Some repositories send session cookies, often by mistake, alongside HTTP redirection. The
cookie's `Path` can then disagree with the path the client uses, and requests fail. If you
know you do not need the session cookie, ignore cookies for the server:

```xml
<configuration>
  <httpConfiguration>
    <all>
      <params>
        <http.protocol.cookie-policy>ignore</http.protocol.cookie-policy>
      </params>
    </all>
  </httpConfiguration>
</configuration>
```

## Authentication

### Target server credentials

At `connect` time, if the `AuthenticationInfo` has a non-empty user name *and* a
non-empty password, a `UsernamePasswordCredentials` is registered in the
`CredentialsProvider` under an `AuthScope` derived from the repository's host and
port. A user name without a password registers nothing.

The client is built with an auth scheme registry containing exactly three schemes:

| Scheme | Factory |
| --- | --- |
| Basic | `BasicSchemeFactory`, UTF-8 |
| Digest | `DigestSchemeFactory`, UTF-8 |
| NTLM | `NTLMSchemeFactory` |

Basic and Digest both work against the target server with a plain user name and
password, negotiated in response to the server's challenge. Use
`http.auth.target-scheme-pref` if you need to force the order in which schemes are
tried.

**NTLM against the target server is not supported.** The target credentials are
always `UsernamePasswordCredentials`, never `NTCredentials`, so the registered NTLM
scheme has nothing usable to offer. NTLM is only wired up for proxies — see below.

### Preemptive authentication

By default HttpClient waits for a `401` challenge before sending credentials. With
`usePreemptive` set, a `BasicScheme` is placed in the `AuthCache` for the target host
before the request goes out, provided credentials for that scope exist — so the first
request already carries an `Authorization: Basic` header:

```xml
<configuration>
  <httpConfiguration>
    <all>
      <usePreemptive>true</usePreemptive>
    </all>
  </httpConfiguration>
</configuration>
```

Note the merge caveat above: adding a `<get>` block alongside this would switch
preemptive authentication back off.

Without `Expect: 100-continue`, a `PUT` that needs authentication would send its whole payload before the server issues the challenge, and then send it again with credentials: twice the bandwidth and time per artifact. Preemptive authentication avoids that by sending the credentials with the first request, at a price. An unused `<server>` entry with a wrong user name or password can make some servers answer `401 Unauthorized` even for requests that need no authentication, and sending credentials blindly can leak them to paths the server does not protect.

Preemptive Basic authentication is only ever Basic — the cache entry is a
`BasicScheme`. It has no effect on Digest or NTLM.

`PUT` is a special case in Wagon 3.5.3: `AbstractHttpClientWagon.put` primes the `AuthCache` with a
`BasicScheme` for the target host whenever credentials exist, regardless of
`usePreemptive`. Uploads are therefore preemptively authenticated by default. The
source marks this as a known wart ("FIXME Perform only when preemptive has been
configured").

### `basicAuth` and `proxyAuth` — overriding the authentication scope

By default the `AuthScope` for the target is built from the repository's own host and
port with `AuthScope.ANY_REALM`, and a port of `-1` becomes `AuthScope.ANY_PORT`.
[`BasicAuthScope`](https://maven.apache.org/wagon/apidocs/org/apache/maven/wagon/shared/http/BasicAuthScope.html)
lets you override `host`, `port` and `realm`.

`host` and `port` accept the literal string `ANY` to mean "match anything", and
setting `host`, `port` and `realm` all to `ANY` yields `AuthScope.ANY`.

`realm` does not work that way, and it is worth being explicit about it because the
asymmetry is easy to trip over in Wagon 3.5.3. Omitting `realm` gives you `AuthScope.ANY_REALM`,
which is what "match any realm" means. Setting it to any value — *including* `ANY`,
unless `host` and `port` are `ANY` too — matches that string verbatim, so
`<realm>ANY</realm>` on its own produces a scope that only matches a server whose
realm is literally `ANY`. Leave `realm` out unless you mean to pin it.

This is the setting to reach for when credentials are not being sent because the
server's realm or port does not match what Wagon derived from the URL — for instance
a repository fronted by a redirect to another host, or one that delegates authentication
to an external system that challenges from a different host.

The two scopes are held in fields named `basicAuth` (for the target) and `proxyAuth`
(for the proxy), reachable through `setBasicAuthScope` and `setProxyBasicAuthScope`.

```xml
<configuration>
  <basicAuth>
    <host>ANY</host>
    <port>ANY</port>
    <realm>ANY</realm>
  </basicAuth>
</configuration>
```

That is the all-`ANY` case, which short-circuits to `AuthScope.ANY` and so does match
any realm. To widen only the host and port, omit `realm` rather than setting it to
`ANY`:

```xml
<configuration>
  <basicAuth>
    <host>ANY</host>
    <port>ANY</port>
  </basicAuth>
</configuration>
```

## Proxies

Proxy settings come from Maven's `<proxy>` configuration rather than from
`<server><configuration>`; Wagon receives them as a
[`ProxyInfo`](https://maven.apache.org/wagon/apidocs/org/apache/maven/wagon/proxy/ProxyInfo.html). What the HTTP
provider does with them:

- The proxy is only used when the `ProxyInfo`'s `type` matches the repository's
  protocol and the target host does not match `nonProxyHosts`. See
  [Configuring a proxy](./guide-proxies.html).
- If the `ProxyInfo` carries a user name and a password, credentials are registered
  under a scope derived from the proxy host and port, overridable through `proxyAuth`
  exactly as `basicAuth` overrides the target scope.
- **NTLM.** If either `ntlmHost` or `ntlmDomain` is set on the `ProxyInfo`, the proxy
  credentials become `NTCredentials(username, password, ntlmHost, ntlmDomain)`
  instead of `UsernamePasswordCredentials`, and the registered `NTLMSchemeFactory`
  handles the challenge. Use `http.auth.proxy-scheme-pref` to control which scheme is
  preferred when the proxy offers several.
- Proxy authentication is always primed preemptively: whenever proxy credentials
  exist, a `BasicScheme` with `ChallengeState.PROXY` is put in the `AuthCache` for the
  proxy host.

## Connection timeout

Every Wagon that extends `AbstractWagon` (SCP, HTTP, FTP and others) accepts a flat
connection timeout in milliseconds, which applies to all methods:

```xml
<configuration>
  <timeout>6000</timeout>
</configuration>
```

`connectionTimeout` inside `httpConfiguration` is the per-method alternative. The `params`
`http.socket.timeout` and `http.connection.timeout` still take precedence over both.

## Deprecated: `httpHeaders`

```xml
<configuration>
  <httpHeaders>
    <property>
      <name>User-Agent</name>
      <value>my-build/1.0</value>
    </property>
  </httpHeaders>
</configuration>
```

`httpHeaders` is a flat `Properties` of headers applied to every request. It predates
`httpConfiguration` and is marked deprecated in the source in favour of it. It is
still honoured, and it is applied *before* the `httpConfiguration` headers, so a
header set in both places takes its value from `httpConfiguration`. The one exception
is `User-Agent` lookup, which consults `httpHeaders` first.

## Backoff on 429

When the server answers `429 Too Many Requests`, the provider sleeps and retries,
doubling the wait each time until it would reach the maximum, at which point it
throws `TransferFailedException`. The initial wait is 5 seconds and the maximum is
180 seconds; both are system properties (below), and the initial value is also
settable per server:

```xml
<configuration>
  <initialBackoffSeconds>10</initialBackoffSeconds>
</configuration>
```

## System properties

These are JVM-wide, not per-server. Several are read into `static final` fields when
`AbstractHttpClientWagon` is first loaded, so they must be set before any transfer
starts — on the command line, or in `MAVEN_OPTS`.

### Connection pool

| Property | Default | Effect |
| --- | --- | --- |
| `maven.wagon.http.pool` | `true` | enable the pooling connection manager; when `false`, total connections are capped at 1 and idle connections are closed on disconnect |
| `maven.wagon.httpconnectionManager.maxPerRoute` | `20` | max connections per route |
| `maven.wagon.httpconnectionManager.maxTotal` | `40` | max connections overall |
| `maven.wagon.httpconnectionManager.ttlSeconds` | `300` | connection time-to-live; `-1` keeps connections indefinitely |

### Timeouts

| Property | Default | Effect |
| --- | --- | --- |
| `maven.wagon.rto` | `1800000` | default read timeout in milliseconds |

### TLS

| Property | Default | Effect |
| --- | --- | --- |
| `maven.wagon.http.ssl.insecure` | `false` | accept any certificate chain whose dates are valid |
| `maven.wagon.http.ssl.allowall` | `false` | with `insecure`, skip hostname verification |
| `maven.wagon.http.ssl.ignore.validity.dates` | `false` | with `insecure`, also ignore certificate dates |
| `https.protocols` | — | comma-separated list of enabled TLS protocols |
| `https.cipherSuites` | — | comma-separated list of enabled cipher suites |

Without `maven.wagon.http.ssl.insecure`, the default JSSE socket factory is used with
HttpClient's browser-compatible hostname verifier.

### Retries

| Property | Default | Effect |
| --- | --- | --- |
| `maven.wagon.http.retryHandler.class` | `standard` | `default`, `standard`, or a fully qualified `HttpRequestRetryHandler` with a no-arg constructor |
| `maven.wagon.http.retryHandler.requestSentEnabled` | `false` | retry requests that were already sent (`default` and `standard` only) |
| `maven.wagon.http.retryHandler.count` | `3` | retry count (`default` and `standard` only) |
| `maven.wagon.http.retryHandler.nonRetryableClasses` | — | comma-separated exception class names to never retry (`default` only) |
| `maven.wagon.http.serviceUnavailableRetryStrategy.class` | `none` | `none`, `default`, `standard`, or a fully qualified `ServiceUnavailableRetryStrategy` with a no-arg constructor |
| `maven.wagon.http.serviceUnavailableRetryStrategy.retryInterval` | `1000` | milliseconds between retries |
| `maven.wagon.http.serviceUnavailableRetryStrategy.maxRetries` | `5` | maximum retries |

A retry handler only reacts to failures while sending the request and reading the
response head. It cannot recover a response body that fails part-way through.

### Backoff

| Property | Default | Effect |
| --- | --- | --- |
| `maven.wagon.httpconnectionManager.backoffSeconds` | `5` | initial wait after a `429` |
| `maven.wagon.httpconnectionManager.maxBackoffSeconds` | `180` | give up once the next wait would reach this |

## Worked example

A server that needs preemptive Basic authentication, a custom user agent, no
redirects, and a generous read timeout for uploads:

```xml
<server>
  <id>internal-releases</id>
  <username>deploy</username>
  <password>secret</password>
  <configuration>
    <httpConfiguration>
      <all>
        <usePreemptive>true</usePreemptive>
        <connectionTimeout>10000</connectionTimeout>
        <headers>
          <User-Agent>acme-build/2.1</User-Agent>
        </headers>
        <params>
          <http.protocol.handle-redirects>false</http.protocol.handle-redirects>
          <http.socket.timeout>600000</http.socket.timeout>
        </params>
      </all>
    </httpConfiguration>
  </configuration>
</server>
```

Everything is in `<all>` on purpose: adding a `<get>` or `<put>` block would drop
`usePreemptive`. Where a per-method setting is unavoidable, use `params` — those do
merge correctly.

## Resources

1. [HttpClient website](https://hc.apache.org/httpcomponents-client-4.5.x/)
2. [HttpClient preference architecture and configuration guide](https://hc.apache.org/httpclient-3.x/preference-api.html)
3. [Guide to Wagon Providers](./guide-wagon-providers.html)
4. [Wagon HTTP provider](https://maven.apache.org/wagon/wagon-providers/wagon-http/)
