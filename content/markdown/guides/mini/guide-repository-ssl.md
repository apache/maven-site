---

title: Remote repository access through authenticated HTTPS
author: 
- Arnaud Bailly
date: 2005-11-11
----------------

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

# Guide to Remote repository access through authenticated HTTPS

This document describes how to configure Maven to access a remote repository that sits behind an HTTPS server which requires client authentication with certificates.

## The problem

There is a maven repository at `https://my.server.com/maven`. This server only serves clients authenticated through SSL protocol by a valid certificate signed by an approved certificate authority&apos;s certificate which we call the `CACert`. In the simplest case where the server is used internally by an identified community of users \(e.g. corporate intranet\), the server&apos;s certificate is the certificate authority as the server is used only internally.

So we assume that we have access to the trusted certificate in X.509 format stored in a file named:

```
/somewhere/in/filesystem/CACert.cert
```

The client&apos;s certificate has been issued by some means not described in this document in PKCS#12 format, which is the format that is accepted by browsers \(at least Firefox and Internet Explorer\) for import into their keystore. This file is named:

```
/home/directory/mycertificate.p12
```

and we assume it is accessible when launching maven. This file contains the client&apos;s private key which may be very sensitive information so it is secured by a password:

```
CeRtPwD
```

The remote repository is referenced either through the `pom.xml` file:

```
maven.repo.remote=https://my.server.com/maven,http://www.ibiblio.org/maven
```

## The solution

For maven to use this repository, we should take the following steps:

1. Create a store to hold the server&apos;s certificate usings Oracle&apos;s [keytool](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/keytool.html),
2. Define properties to be used by HttpClient for finding keys and certificate

### Storing certificate

The following command line imports the certififcate authority&apos;s certificate into a JKS formatted key store named `trust.jks`, the _trust store_.

```
$> keytool -v -alias mavensrv -import \
     -file /somewhere/in/filesystem/CACert.cert\
      -keystore trust.jks
Enter keystore password:
Owner: ....
Issuer: ....
Serial number: ....
Valid from: Mon Feb 21 22:34:25 CET 2005 until: Thu Feb 19 22:34:25 CET 2015
Certificate fingerprints:
         MD5:  .......
         SHA1: .....
Trust this certificate? [no]:  yes
Certificate was added to keystore
[Storing trust.jks]
$>
```

Note that it should be possible to import a full chain of certificates with only one root certificate being trusted but the author did not test it.

### Setting properties

The following properties must be set at start of maven to be accessible when HttpClient starts up.

<dl>
<dt>javax.net.ssl.trustStore</dt>
<dd>the path to the keystore where trusted certificates are stored</dd>
<dt>javax.net.ssl.trustStoreType</dt>
<dd>the type of storage for this store, maybe either <code>jks</code> (default) or <code>pkcs12</code></dd>
<dt>javax.net.ssl.trustStorePassword</dt>
<dd>the password protecting the store</dd>
<dt>javax.net.ssl.keyStore</dt>
<dd>the path to the keystore where user&apos;s private key is stored</dd>
<dt>javax.net.ssl.keyStoreType</dt>
<dd>the type of storage for this store, maybe either <code>jks</code> (default) or <code>pkcs12</code></dd>
<dt>javax.net.ssl.keyStorePassword</dt>
<dd>the password protecting the store</dd>
</dl>

Not all the properties must be set depending of your precise settings: type of store may left to default, password may be empty.

They may be set either on maven&apos;s command-line, in `.mavenrc` file or in `MAVEN_OPTS` environment variable. For the setting defined in this document, here is an example `.mavenrc` file:

```
MAVEN_OPTS="-Xmx512m -Djavax.net.ssl.trustStore=trust.jks \
                     -Djavax.net.ssl.trustStorePassword=  \
                     -Djavax.net.ssl.keyStore=/home/directory/mycertificate.p12 \
                     -Djavax.net.ssl.keyStoreType=pkcs12 \
                     -Djavax.net.ssl.keyStorePassword=XXXXXX"
```

## Links

The following links may be useful in understanding SSL infrastructure management in Java:

- [HttpClient](http://hc.apache.org/httpclient-3.x/sslguide.html)&apos;s SSL guide

