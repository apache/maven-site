# Password Encryption (Maven 4)

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
<!--MACRO{toc|fromDepth=2}-->

## Introduction

Maven 4 introduces an enhanced encryption providing more secure handling of password than provided by [the one from Maven 3](guide-encryption.html).
It ships with multiple pluggable providers (internally referred to as dispatchers) which use different approaches for decrypting secret values from `settings.xml`.

## Dispatchers

### Master

The `master` dispatcher still sticks to the principle of keeping the *symmetrically encrypted values* directly within the `settings.xml` (which was used in Maven2/3).

A single master key is used for both decrypting and encrypting and therefore should be always kept at a safe place. *Everyone with that key can decrypt all encrypted values from your `settings.xml`.*
The master key may be provided from different sources, therefore this dispatcher requires the configuration of a master `source`.

Currently the only cipher used is `AES/GCM/NoPadding` with `PBKDF2WithHmacSHA512` hashes as key (this may change in the future though).

### Master Source Lookup Dispatcher

The `masterSourceLookup` dispatcher (since Maven 4 rc3) does no actually perform any crypto operations. It merely looks up a secret value references from one of Master Key Sources.
Instead of using an actually encrypted value one leverages master key source configurations within the `settings.xml`.
The placeholder to be used within the `settings.xml` should be created with `mvnenc encrypt` as outlined in section *Create encrypted values*.
It has the format `{[name=masterSourceLookup,version=4.0]<master source config>}`. The `

### Legacy Dispatcher

This is provided as compatibility layer with the [Maven 3 encryption](guide-encryption.html) and should only be used if the same `settings.xml` is supposed to be used with Maven < 4 and above. It is the same implementation as in Maven 3.

## Master Key Sources

There are different master key source implementations shipping with Maven with which the master key may be retrieved. Each of them have a unique prefix used
in the dispatcher's `source` property of the  `settings-security4.xml` (for the Master Dispatcher) or the referenced used in the `settings.xml` (for the Master Source Lookup Dispatcher)

1. Plain file (`file:<filepath>`), the filepath must be absolute. Only considered secure if located on a protected file system.
2. Environment variable (`env:<variable-name>`), the environment variable must be externally populated via some secure means.
3. Java System Properties (`system-property:<property-name>`), the Java system property must be externally populated via some secure means.
4. GnuPG Agent (`gpg-agent:<agentSocketPath>(?non-interactive)`), requires one manual input per user session.
5. PinEntry (`pinentry-prompt:path`), requires manual input of the password every time a decryption is required.
6. 1Password CLI (`onepassword:<1Password Secret Reference URI>`), looks up secret values from a 1Password vault leveraging the [1Password CLI](https://developer.1password.com/docs/cli/get-started). The [1Password Secret Reference URI](https://developer.1password.com/docs/cli/secret-reference-syntax) always starts with `op://`.

One has to pick a suitable source depending on the security and ease-of-use requirements.

## Setup

There is a dedicated tool named `mvnenc` shipping with Maven 4 which helps both with the setup and encryption of values, as well as diagnosis in case of issues.
Start by executing `mvnenc init` and then select the desired dispatcher.

Depending on this choice there may be multiple subsequent configuration steps.

Afterwards check with `mvnenc diag` if the configuration is valid.

## Create encrypted values

Execute `mvnenc encrypt` and afterwards enter the value to encrypt via the CLI. The tool emits the encrypted value in the format

```
{[name=<dispatcher name>,<dispatcher-specific attributes>,version=4.0]<the encrypted value>}
```

which can be just used as is in the `settings.xml`.

## Implementation

The implementation relies on the [Plexus Security Dispatcher Component](https://github.com/codehaus-plexus/plexus-sec-dispatcher) which also provides an SPI to implement your own source of the master password or other dispatcher implementations.

### Configuration File

The Maven 4 security configuration is stored in the `<maven.user.conf>/settings-security4.xml` (where `<maven.user.conf>` is by default`~/.m2`). The path can be overwritten with config `maven.settings.security`. The XML must adhere to the format defined by
It is recommended to only write the configuration file with the aforementioned `mvnenc` CLI tool.
