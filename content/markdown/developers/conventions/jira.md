# Maven JIRA Conventions
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
This document describes how Maven developers should use JIRA, our issue management system.

## When To Create a JIRA Issue?

This section discusses when to create a JIRA issue versus just committing a change in Git (eventually through a PR).

- **Minor changes** such as code reformatting, documentation fixes, etc. that aren't going to impact other users
can be committed without a JIRA issue.

- **Larger changes** such as bug fixes, API changes, significant refactoring, new classes, and pretty much any change
of more than 100 lines, should have JIRA tickets.

Creating a JIRA issue and referring it in the commit comments simplifies tracking the changes that happen in a release,
using JIRA automatic release notes creation.

## How To Use Issue Details?

This section presents some conventions about the issue fields.

### Priority

Committers have the responsibility to realign priority by editing the issue.

*Reasoning*: having a correct release note

### Assignee

Committers can assign an issue to a specific committer that person seems to
be well placed to address it.

### Component/s

Committers have the responsibility to specify the correct component by editing the issue.

*Reasoning*: having a correct release note.

### Affects Version/s

By default, the Maven team considers that an issue which affects a given version also affects preceding versions. For example, an issue
that affects Maven 3.6.3 also affects 3.6.0, 3.6.1, 3.6.2.
If it is a regression, the committers should specify the affected versions.

*Reasoning*: having a correct release note.

### Fix Version/s

Update to correct version after merging to master.

### Time Tracking

The Maven team doesn't use this. Committers can if it helps them.

## Further Links

- [JIRA Documentation](https://confluence.atlassian.com/jira064/jira-documentation-720411693.html)
- [What is an Issue?](https://confluence.atlassian.com/jira064/what-is-an-issue-720416138.html)
- [What is a project?](https://confluence.atlassian.com/jira064/what-is-a-project-720416135.html)