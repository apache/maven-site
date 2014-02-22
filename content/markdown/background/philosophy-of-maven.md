# Philosophy of Maven 

<!--
 Allow front-matter here eventually. Just copy Jekyll
 Jason van Zyl
 12 October 2005
-->

Maven is generally considered by many to be a build tool. Many people who come to Maven initially are familiar
with Ant so it's a natural association but Maven is not just a build tool, and not just a replacement for Ant.
Maven is an entirely different creature from Ant. Ant is simply a toolbox whereas Maven is about the
application of patterns in order to achieve an infrastructure which displays the characteristics of
visibility, reusability, maintainability, and comprehensibility.

Without these characteristics it is highly improbable that multiple individuals will work productively together
on a project. Without visibility it is unlikely an individual will know what another has accomplished and as such
there is a very good chance useful code will not be reused. When code is not reused it is very hard to create
a maintainable system. When everyone is constantly rooting around trying to figure out where all these different
bits and pieces are that make up your project there is very little chance anyone is going to comprehend the
project as a whole. As a result you end up with the silo effect, a decay of shared knowledge along with
the commensurate degree of frustration among team members. A natural effect when processes don't work
in the same way for everyone.

Maven was born of the very practical desire to make several projects at Apache work in the same way. So that developers
could freely move between these projects, knowing clearly how they all worked by understanding how one of them
worked. If a developer spent time understanding how one project built it was intended that they would not have
to go through this process again when they moved on to the next project. The same idea extends to testing,
generating documentation, generating metrics and reports, testing and deploying. All projects share
enough of the same characteristics, an understanding of which Maven tries to harness in its general approach
to project management. On a very high level all projects need to be built, tested, packaged, documented
and deployed. Of course there is infinite variation in each of the above mentioned steps, but this variation
still occurs within the confines of a well defined path and it is this path that Maven attempts to present to
everyone in a clear way. The easiest way to make a path clear is to provide people with a set of patterns that
can be shared by anyone involved in a project.
