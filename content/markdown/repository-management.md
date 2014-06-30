## Why do I need a Repository Manager?

Maven Repository managers serve two purposes: they act as highly
configurable proxies between your organization and the public Maven
repositories and they also provide an organization with a deployment
destination for your own generated artifacts.

Proxying a Maven repository brings a number of benefits. Proxying speeds
up builds throughout your organization by installing a local cache for
all artifacts from the Central Maven repository. If a developer in your
organization needs to download version 2.5 of the Spring Framework and
you are using a Maven Repository Manager, the dependencies (and the
dependency's dependencies) only need to be downloaded from the remote
repository once. With a high-speed connection to the Internet this might
seem like a minor concern, but if you are constantly asking your
developers to download hundreds of megabytes of third-party
dependencies, the real cost savings are going to be the time it takes
Maven to check for new versions of dependencies and to download
dependencies. Serving Maven dependencies from a local repository can
save you hundreds of requests over HTTP, and, in very large
multi-project builds, this can shave minutes from a build.

If your project is relying on a number of SNAPSHOT dependencies, Maven
will need to check for updated version of these snapshots. Depending on
the configuration of your remote repositories, Maven will check for
SNAPSHOT updates periodically, or it might be checking for SNAPSHOT
updates on every build. When Maven checks for a snapshot update it needs
to interrogate the remote repository for the latest version of the
SNAPSHOT dependency. Depending on your connection to the public Internet
and the load on the central Maven repository, a SNAPSHOT update can add
seconds to your project's build for each SNAPSHOT update. When you host
a local repository proxy with a repository manager, your repository
manager is going to check for SNAPSHOT updates on a regular schedule,
and your applications will be able to interact with a local repository.
If you develop software with a lot of SNAPSHOT dependencies, using a
local repository manager can often shave minutes from a large
multi-module project build, your 5-10 second SNAPSHOT update checks
against the public central repository are going to execute in hundreds
of milliseconds (or less).

In addition to the simple savings in time and bandwidth, a repository
manager provides an organization with control over what is downloaded by
Maven. You can include or exclude specific artifacts from the public
repository, and having this level of control over what is downloaded
from the central Maven repository is a prerequisite for organizations
which need strict control over what dependencies are used throughout an
organization. An organization which wants to standardize on a specific
version of a dependency like Hibernate or Spring can enforce this
standardization by only providing access to a specific version of an
artifact in a repository manager. Other organizations might be concerned
with making sure that every external dependency has a license compatible
with the legal standards of that organization. If a corporation is
producing a application which is distributed, they might want to make
sure that no one inadvertently adds a dependency on a third-party
library which is covered under a copy-left license like the GPL.
Repository managers provide for the level of control that an
organization needs to make sure that overall architecture and policy can
be enforced.

Aside from the benefits of mediating access to remote repositories, a
repository manager also provides something essential to full adoption of
Maven. Unless you expect every member of your organization to download
and build every single internal project, you will want to provide a
mechanism for developers and departments to share both SNAPSHOT and
releases for internal project artifacts. A Maven repository manager
provides your organization with such a deployment target. Once you
install a Maven repository manager, you can start using Maven to deploy
snapshots and releases to a custom repository managed by the repository
manager. Over time, this central deployment point for internal projects
becomes the fabric for collaboration between different development
teams.

## The List of Repository Managers

The Following is a list of the known Maven repository managers and
listed in alphabetical order:

### [Apache Archiva](http://archiva.apache.org/)

Apache Archiva is an extensible repository management software that
helps taking care of your own personal or enterprise-wide build artifact
repository. It is the perfect companion for build tools such as Maven,
Continuum, and ANT.

Archiva offers several capabilities, amongst which remote repository
proxying, security access management, build artifact storage, delivery,
browsing, indexing and usage reporting, extensible scanning
functionality... and many more!

### [Artifactory](http://www.jfrog.org/sites/artifactory/latest/)

Artifactory is a Maven 2 enterprise repository. It offers advanced
proxying, caching and security facilities to provide a robust,
reproducible and independent build environment when using Maven.
Artifactory is being used by clients ranging from small startup teams to
international corporate teams employing distributed development, thus
improving the development experience for tens of thousands of
developers. Artifactory exposes a robust artifacts management platform
using rich Ajax web UI and can be run out-of-the-box with a simple
"unzip and launch".

### [Sonatype Nexus (previously Proximity)](http://nexus.sonatype.org)

Sonatype Nexus is the repository manager used as the input channel for
the [Central Repository](http://search.maven.org) running the Sonatype
Open Source Repository Hosting OSSRH service. Nexus is available in an
open source as well as a commercial edition and runs [large
enterprise](http://www.sonatype.com/about/customers) as well as open
source forge sites such as
[JBoss](https://repository.jboss.org/nexus/index.html) or
[Apache](https://repository.apache.org/). It provides robust features
and performance with the open source version and scales to the high
demands of large enterprises with the pro edition. Find out more details
on the [website](http://links.sonatype.com/products/nexus/pro/home)} or
by using the [trial
version](http://links.sonatype.com/products/nexus/pro/trial).

### Other resources

-   [Free book from Sonatype - Repository Management with
    Nexus](http://links.sonatype.com/products/nexus/oss/docs)
-   [Maven Concepts
    Repositories](http://docs.codehaus.org/display/MAVENUSER/Maven+Concepts+Repositories)
    from Maven wiki
-   [Continuously updated Feature
    matrix.](http://docs.codehaus.org/display/MAVENUSER/Maven+Repository+Manager+Feature+Matrix)

