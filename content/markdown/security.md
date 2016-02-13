## Security Vulnerabilities

Please note that binary patches are not produced for individual
vulnerabilities. To obtain the binary fix for a particular vulnerability
you should upgrade to an Apache Maven version where that vulnerability
has been fixed.

For more information about reporting vulnerabilities, see the [Apache
Security Team](https://www.apache.org/security/) page.

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

