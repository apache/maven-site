Adding a new release

For 2.0.x: update releasedVersions, current20xVersion property and current20xReleaseDate in pom.xml
For 2.2.x: update releasedVersions, currentStableVersion property and currentStableReleaseDate in pom.xml
For 3.x-alpha/beta: update currentDevelopmentVersion property in pom.xml (don't update releasedVersions)

For all:
- create docs/$version
- populate docs/$version/release-notes.txt from JIRA
- create docs/$version/release-notes.apt.vm (see other versions for an example)

Only deploy once the release is present on the mirrors, and the reference documentation has been deployed to /ref/.
