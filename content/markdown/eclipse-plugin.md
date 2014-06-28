# Maven 2.x Integration for Eclipse

## M2E

  [The Maven Integration for Eclipse][m2e] is the official Eclipse project aimed at integrating Maven within the Eclipse IDE. It is released under the EPL 1.0 license.
  
Features include:

- Launching Maven builds from within Eclipse
- Dependency management for Eclipse build path based on Maven's pom.xml
- Resolving Maven dependencies from the Eclipse workspace without installing to local Maven repository
- Automatic downloading of the required dependencies and sources from the remote Maven repositories
- Wizards for creating new Maven projects, pom.xml and to enable Maven support on existing projects
- Quick search for dependencies in remote Maven repositories
- Quick fixes in the Java editor for looking up required dependencies/jars by the class or package name
- Integration with other Eclipse tools, such as WTP, AJDT, Mylyn, Subclipse and others.

M2E dynamically integrates with your Maven projects with Eclipse while you make changes in the IDE. As you change dependencies, or configurations of Maven plugins in your POMs M2E, will synchronize the Eclipse workspace with those changes.

## Maven Eclipse Plugin (m-e-p)

The [maven-eclipse-plugin][mep] can be run from the command line to produce a static Eclipse configuration. If you make changes to your Maven POMs then you need to generate your Eclipse project files again. Note that M2E and m-e-p are not compatible, and the M2E team specifically looks like m-e-p generated files and will disable M2E support for those projects.

Eclipse is a trademark of The [Eclipse Foundation][eclipse]  

[m2e]: http://www.eclipse.org/m2e/
[eclipse]: {http://www.eclipse.org
[mep]: http://maven.apache.org/plugins/maven-eclipse-plugin/

