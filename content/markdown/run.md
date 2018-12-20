# Running Apache Maven

The syntax for running Maven is as follows:

    mvn [options] [<goal(s)>] [<phase(s)>]

All available options are documented in the built in help that you can access with

    mvn -h

The typical invocation for building a Maven project uses a Maven life cycle phase. E.g.  

    mvn package

The built in life cycles and their phases are in order are:

* clean - pre-clean, clean, post-clean

* default - validate, initialize, generate-sources, process-sources, generate-resources, 
process-resources, compile, process-classes, generate-test-sources, process-test-sources, 
generate-test-resources, process-test-resources, test-compile, process-test-classes, 
test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, 
verify, install, deploy

* site - pre-site, site, post-site, site-deploy

A fresh build of a project generating all packaged outputs and the documentation site
and deploying it to a repository manager could be done with

    mvn clean deploy site-deploy

Just creating the package and installing it in the local repository for re-use from other projects can be done with

    mvn verify

This is the most common build invocation for a Maven project.

When not working with a project, and in some other use cases, you might want to invoke
a specific task implemented by a part of Maven - this is called a goal of a plugin.
E.g.:

    mvn archetype:generate

or

    mvn checkstyle:check

There are many different plugins available and they all implement different goals.


Further resources: 

* [Building a Project with Maven](./run-maven/index.html)
