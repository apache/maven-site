# Configuring Apache Maven

The configuration for Apache Maven usage itself and projects built with resides 
in a number of places: 

* `MAVEN_OPTS` environment variable:

This variable contains parameters used to start up the JVM running Maven and can 
be used to supply additional options to globally to Maven. E.g. JVM memory 
settings could be defined with the value `-Xms256m -Xmx512m`.

* `settings.xml` file:

Located in USER_HOME/.m2 the settings files is designed to contain any
configuration for Maven usage across projects.

* `.mvn` folder:

Located with in the projects top level folder, the files `maven.config` and `extensions.xml`
contain project specific configuration for running Maven.


The following guides contain further information to specific configuration aspects:

* [Recommended Best Practice - Using a Repository Manager](./repository-management.html)
* [Documentation for settings.xml](./settings.html)
* [Configuring the logging](./maven-logging.html)
* [Configuring a HTTP Proxy](./guides/mini/guide-proxies.html)
* [Configuring a repository mirror](./guides/mini/guide-mirror-settings.html)
* [Various Tips for Configuring Maven](./guides/mini/guide-configuring-maven.html)
* [Password Encryption](./guides/mini/guide-encryption.html)
