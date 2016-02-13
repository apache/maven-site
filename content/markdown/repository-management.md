## Best Practice - Using a Repository Manager

A repository manager is a dedicated server application designed to manage
repositories of binary components. The usage of a repository manager is 
considered an essential best practice for any significant usage of Maven.


### Purpose

A repository manager serves these essential purposes:

* act as dedicated proxy server for public Maven repositories
* provide repositories as a deployment destination for your Maven project 
outputs

### Benefits and Features

Using a repository manager provides the following benefits and features:

* significantly reduced number of downloads off remote repositories, saving time
and bandwidth resulting in increased build performance
* improved build stability due to reduced reliance on external repositories
* increased performance for interaction with remote SNAPSHOT repositories
* potential for control of consumed and provided artifacts
* creates a central storage and access to artifacts and meta data about them 
exposing build outputs to consumer such as other projects and developers, but 
also QA or operations teams or even customers 
* provides an effective platform for exchanging binary artifacts within 
your organization and beyond without the need for building artifact from source

### Available Repository Managers

The following list (alphabetical order) of open source and commercial repository
 managers are known to support the repository format used by Maven. Please refer to the respective linked web sites for further information about repository management in general 
and the features provided by these products.

* <a href="https://archiva.apache.org/" target="_blank" rel="nofollow">Apache Archiva</a> (open source)
* <a href="https://www.jfrog.com/open-source" target="_blank" rel="nofollow">JFrog Artifactory Open Source</a> (open source)
* <a href="https://www.jfrog.com/artifactory/" target="_blank" rel="nofollow">JFrog Artifactory Pro</a> (commercial)
* <a href="https://www.sonatype.org/nexus/go/" target="_blank" rel="nofollow">Sonatype Nexus OSS</a> (open source)
* <a href="https://links.sonatype.com/products/nexus/pro/home" target="_blank" rel="nofollow">Sonatype Nexus Pro</a> (commercial)

