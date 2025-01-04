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

# Frequently Asked Questions About Project Metadata

<!--MACRO{toc|fromDepth=2}-->

## Why do I care?

If you're a user of Maven then you are familiar with the Project Object Model (POM) which is the basic unit of
work in Maven.
Maven is a project-centric tool, and so we attempt to capture the essence of a project in the POM.
This includes things like

- what your project is,
- where the project lives,
- where you can find the sources for project,
- who the developers are on the project and how you can get hold of them,
- what you need to build the project,
- the way your project will be built,
- what form your project will be distributed in, and
- where it will be distributed from.

But why would a project not using Maven care?

Whether you want to use Maven or not, users of your project - especially if you provide a framework or
reusable library - may choose to use the [Central Maven repository](/repository/).

The quality of the metadata in the Central Maven repository is important to your users, as they list dependencies on
your metadata and link in the information into their own projects.

Maintaining the metadata for your project is not hard.
You can submit it to the Central Maven repository at release time, and this can be automated.
You just need to describe your project, its location, version, and most importantly its dependencies.
Not doing so, or providing incomplete or invalid information leaves this responsibility to the users of your project.

## How do I make sure my project's dependency metadata is correct?

It is best to get it right at the time of a release to avoid having to make difficult updates later on.
Your information can be submitted to Central Maven repository using the
regular [Central repository upload procedure](/guides/mini/guide-central-repository-upload.html).

The following information is what is best to provide:

- Project name
- Project URL
- License
- Description of the project
- Group and Artifact ID
- Packaging
- Version
- Dependencies

The group ID should resemble the package name, or reverse DNS of your website, and can contain subgroups as
you see fit: for example, `org.apache.maven and org.apache.maven.plugins`.

The artifact ID is specific to each artifact and by convention should be the filename, excluding
extension.

The packaging is the type of your artifact, such as *jar*, *war*, *ear*, *ejb*, *dll*, etc.

Each dependency also contains its group ID and artifact ID, as well as version specification.
In particular, you should ensure that optional dependencies are marked as such, and that runtime and testing
only dependencies are marked with the given scope. Ensure that the dependency exists in the Maven system and matches
first.

See the format of the [project descriptor](/maven-model/maven.html) for more information.
