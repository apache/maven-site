# Maven Security
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
## Security Model

The purpose of Maven is to perform the actions defined in the supplied
`pom.xml`, which commonly includes compiling and running the associated
code and using plugins and dependencies downloaded from the configured
repositories.

As such, the Maven security model assumes you trust the `pom.xml` and
the code, dependencies and repositories that are used in your build.
If you want to use Maven to build untrusted code, it is up to you to
provide the required isolation.

## Security Vulnerabilities

Please note that binary patches are not produced for individual
vulnerabilities. To obtain the binary fix for a particular vulnerability
you should upgrade to an Apache Maven version where that vulnerability
has been fixed.

For more information about reporting vulnerabilities, see the [Apache
Security Team](https://www.apache.org/security/) page.

### CVE-2021-26291 Apache Maven 3.8.1

Severity: Medium

Vendor: The Apache Software Foundation

Versions Affected:

- Apache Maven 3.6.3 and earlier

Description: More and more repositories use HTTPS nowadays, but this hasn't always been the case. This means that Maven Central contains POMs with custom repositories that refer to a URL over HTTP.
This makes downloads via such repository a target for a MITM attack. 
At the same time, developers are probably not aware that for some downloads an insecure URL is being used. 
Because uploaded POMs to Maven Central are immutable, a change for Maven was required.
To solve this, we extended the mirror configuration with `<blocked>` parameter,
and we added a new `external:http:*` mirror selector (like existing `external:*`), meaning "any external URL using HTTP".\
The decision was made to block such external HTTP repositories by default: this is done by providing a mirror in the `conf/settings.xml` blocking insecure HTTP external URLs.
Note: this is a vulnerability in case you're connectioning directly to remote repositories instead of using an artifact repository manager.

### Maven Dependency, EAR, Javadoc, WAR and Plugin Plugins

Severity: Low

Vendor: The Apache Software Foundation

Versions Affected:

- Maven Dependency Plugin 3.1.0 and earlier
- Maven EAR Plugin 3.0.0 and earlier
- Maven Javadoc Plugin 2.5 to 3.0.0
- Maven WAR Plugin 2.1-alpha-1 to 3.2.0
- Maven Plugin Plugin 3.0 to 3.5.1

Description: As part of a broader research, the Snyk Security Research Team discovered
an arbitrary file write generic vulnerability, that can be achieved using a 
specially crafted zip (or bzip2, gzip, tar, xz, war) archive, that holds 
path traversal filenames. So when the filename gets concatenated to the 
target extraction directory, if the extraction tool used does not make 
sufficient checks, the final path ends up outside of the target directory.
The affected plugins use plexus-archiver to unpack dependencies to disk
and have been identified as potential triggers for exposing the vulnerability
if dependencies are compromised.

See [full description](./security-plexus-archiver.html) for more details.

Credit: This issue was identified by the Snyk Security Research Team

### CVE-2013-0253 Apache Maven 3.0.4

Severity: Medium

Vendor: The Apache Software Foundation

Versions Affected:

-   Apache Maven 3.0.4
-   Apache Maven Wagon 2.1, 2.2, 2.3

Description: Apache Maven 3.0.4 (with Apache Maven Wagon 2.1) has
introduced a non-secure SSL mode by default. This mode disables all SSL
certificate checking, including: host name verification , date validity,
and certificate chain. Not validating the certificate introduces the
possibility of a man-in-the-middle attack.

[CVE-2013-0253](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2013-0253)

All users are recommended to upgrade to [Apache Maven
3.0.5](./download.cgi) and Apache Maven Wagon 2.4.

Credit: This issue was identified by Graham Leggett

### CVE-2012-6153 Apache Maven Wagon :: WebDAV Provider

Severity: Medium

Vendor: The Apache Software Foundation

Versions Affected:

-   Apache Maven Wagon WebDAV Provider 2.12 and earlier

Description: http/conn/ssl/AbstractVerifier.java in Apache Commons HttpClient 
before 4.2.3 does not properly verify that the server hostname matches a 
domain name in the subject's Common Name (CN) or subjectAltName field of the 
X.509 certificate, which allows man-in-the-middle attackers to spoof SSL 
servers via a certificate with a subject that specifies a common name in a 
field that is not the CN field.

[CVE-2012-6153](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2012-6153)

Users of this provider are recommended to upgrade to [Apache Maven Wagon :: 
WebDAV Provider 3.0.0](./download.cgi)
