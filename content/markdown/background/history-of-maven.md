# History of Maven

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
<!--
Allow front-matter here eventually. Just copy Jekyll
Jason van Zyl
12 October 2005
-->

## History of Maven by Jason van Zyl

Maven began its life in the [Jakarta][1] [Alexandria][2] project. The Alexandria project is now defunct but was the breeding ground for not only Maven, but for the [Gump][3] and [Forrest][4] projects as well. The first import of prototype sources happened in
[August 2001][5]. As of the date of this document (October 2005) Maven was [removed][6] from Alexandria about 3 years, 7 months ago making Maven about 4 years old! Maven spent about 5 months as part of the Alexandria before moving on to its next home in the [Turbine][7] project.

Though Maven started in Alexandria the test bed for its use was the Turbine project. Turbine was in the process of
decoupling its persistence layer, services layer and web layer into separate builds and I got very tired of having
to maintain several different builds which were essentially the same. There was no way to easy template Ant builds
in those days and every ant build appeared to be different and I found this incredibly frustrating and futile. I figured
who really cares how the build works so long as it works and is easy to use. The infrastructure of a project is
incredibly important but the value of a project lies in the application being developed. As such the build is
generally neglected and tends to fall apart when you need it to work most like when you need to prepare a release or when
more then a couple of people are working on the project. In Jakarta land four years ago it was rare that a Ant build
worked out of the box. Mind you many Turbine developers suffered as I tried to get Maven working which is something I regret,
but I figure how do new projects start and survive if someone doesn't suffer. I figured it was for their own good
(I've been known to have an opinion or two) and after much gnashing of teeth I think Maven has finally come of age.
It reminds me of one of my favourite quotes from Ralph Johnson and Don Roberts in Patterns for Evolving Frameworks:

> People develop abstractions by generalizing from concrete examples. Every attempt to determine the correct abstraction
> on paper without actually developing a running system is doomed to failure. No one is that smart. A framework is a
> reusable design, so you develop it by looking at the things it is supposed to be a design of. The more examples
> you look at, the more general your framework will be.

I didn't really know what the final result would look like I just knew there had to be a better way.
But to start with I know I wanted:

- A model for a project so you could look in one place for everything that pertained to the project
- A standard directory structure so you didn't have to go fishing around for libraries, sources and documentation

So started using a model with a simple XML representation and picked what I thought were some decent standards for
a directory structure and that's how it started. I was still using Ant under the covers but I had some standard targets
that could be used in each of the Turbine builds and that made me happy.

As noted above one of the projects in Alexandria at the time was Gump. Sam Ruby tried to convince me that using
the Gump model would be a good idea so I took a look. After taking a look at the descriptors I noted that Gump
pretty much allowed any project to do whatever it wanted in terms of directory structure, use of JARs in CVS,
multiple artifacts per project, documentation splayed everywhere, and several other things that made no sense to me
as Gump was not trying to standardize anything at the time but trying to continuously integrate anything it could
get its hands on. My goals were different and I wanted to make an
[opinionated][8] piece of software and I preferred the notion of convention over configuration. I wanted a project's infrastructure to
look the same and work the same so I continued to pursue my own model for a project and decided to disagree with
Gump's particular tact at project modeling which I thought was too flexible. I wanted to save people time by being
able to find things in the same place. Again the value in a project is the final result: how it is constructed
and built predictable and easy. I fully admit some warts in Maven 1.x sometimes made things harder but that is
par for the course with first generation tools.

The next thing I noticed were all the JARs that we were depending on were stored in CVS. We had many copies
of Xerces laying around which is a waste of space, every time the version of Xerces changed
I had to update the copies of Xerces in each of the projects, but more importantly without some declarative statement of
your dependencies there is no way you could perform any analysis. People tend to miss the point entirely regarding
a declarative dependency use. People say it's so easy just to store their dependencies in an SCM but try decomposing
your big crappy build into components to encourage reuse and ease of maintenance, or try to analyze what you might
need at runtime between all your different apps with commons dependencies in the graph and you're shit out of luck.
The true power of declarative dependencies lies not in the fact that you can save a few bytes of disk space, though
it can really add up if you're not careful, but in the analysis that can be performed. Once you have a decent graph
all sorts of things are possible. Back to history: so now that declarative dependencies existed it needed to
be easier ...

This is when I decided to employ standard Java-like inheritance in the model used and find a way to create a repository
for things you need to build. So I hacked in some inheritance goop and now it was time for the repository. I asked
around Apache to see if it would be possible to host a repository and soon found out that it was not possible to
host non-Apache-like artifacts. So LGPL and GPL artifacts were out which didn't really make for a useful repository.
After a little hunting I found [Ibiblio][9] which is a vast archive of all sorts of neat
stuff including tons of free software. One of the mandates of Ibiblio is to aid in the dispersal of free software.
Sounded perfect to me so I got in contact with John Reuning at Ibiblio and the rest is history. Working with the
folks at Ibiblio has been a pleasure, the admins there are an amazingly helpful and talented bunch. They let us
store whatever free software we want, provide great stats, and will let us host any software we want. Ibiblio
is very cool.

Many people had some problems with Maven 1.x but it generally worked and all tools in their first generation
suffer from many short comings and the only way to overcome that is to forge ahead and try to create something
better the next time around. With all the feed back the Maven developers have received from 1.x users
and during the betas of 2.0 we think we've finally got something to build on. The first version of Maven
was written by myself with lots of help from Bob McWhirter

[1]: http://jakarta.apache.org
[2]: http://jakarta.apache.org/alexandria/legacy/
[3]: http://gump.apache.org
[4]: http://forrest.apache.org
[5]: http://mail-archives.apache.org/mod_mbox/jakarta-alexandria-dev/200108.mbox/%3c20010827163505.53005.qmail@icarus.apache.org%3e
[6]: http://mail-archives.apache.org/mod_mbox/jakarta-alexandria-dev/200202.mbox/%3c20020202153719.50163.qmail@icarus.apache.org%3e
[7]: http://turbine.apache.org/
[8]: http://www.oreillynet.com/pub/a/network/2005/08/30/ruby-rails-david-heinemeier-hansson.html
[9]: http://www.ibiblio.org

