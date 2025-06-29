# Apache Maven concepts

The following terms and concepts are central to understanding and use of Apache Maven:

* POM
* Lifecycle
* Phases
* Goals
* Plugins
* Standard Directory Layout

## POM

POM stands for "Project Object Model". It is an XML representation of a Maven project held in a file named `pom.xml`. A POM is the fundamental unit of work in Maven. It contains information about the project and configuration details used by Maven to build the project. 

## Lifecycle

Maven is based around the central concept of a build lifecycle. What this means is that the process for building and distributing a particular artifact (project) is clearly defined. There are three built-in build lifecycles: `default`, `clean` and `site`. The default lifecycle handles your project deployment, the clean lifecycle handles project cleaning, while the site lifecycle handles the creation of your project's web site.

## Phases

Each of these build lifecycles is defined by a different list of build phases, wherein a build phase represents a stage in the lifecycle. These lifecycle phases (plus the other lifecycle phases not shown here) are executed sequentially to complete the lifecycle.

## Goals

A plugin goal represents a specific task (finer than a build phase) which contributes to the building and managing of a project. It may be bound to zero or more build phases. A goal not bound to any build phase could be executed outside of the build lifecycle by direct invocation.

## Plugins

Apache Maven is just a core framework for a collection of Maven Plugins. In other words, plugins are where much of the real action is performed, plugins are used to: create jar files, create war files, compile code, unit test code, create project documentation, and on and on. Almost any action that you can think of performing on a project is implemented as a Maven plugin.

## Standard Directory Layout

Having a common directory layout allows users familiar with one Maven project to immediately feel at home in another Maven project. At the top level, files descriptive of the project: a `pom.xml` file. There are just two subdirectories of this structure: `src` and `target`. The target directory is used to house all output of the build. The src directory contains all of the source material for building the project, its site and so on. It contains a subdirectory for each type: main for the main build artifact, test for the unit test code and resources, site and so on.
