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

# Maven GitHub Conventions

This document describes how Maven developers and contributors should use GitHub Issues and Pull Request.

## GitHub Issues, Pull Request - Recommendations.

A GitHub Issue should be created to report a bug or request a new feature.

A GitHub Issue can be created to discuss of change before starting work on it.
The developer mailing list can also be used to discuss proposed work.

When you provide a Pull Request - creating separate issue is not necessary as you can describe your proposition in Pull Request.

When fixing the issue by providing Pull Request, reference the issue in the commit message by `#issue-number`.

A GitHub Issue and Pull Request should have a [label](https://docs.github.com/en/issues/using-labels-and-milestones-to-track-work/managing-labels) with the type, like `bug`, `enhancement` and so on.
Pull Request without labels will be not categorized in Release Notes.

Closed GitHub Issue and Pull Request should have [Milestone](https://docs.github.com/en/issues/using-labels-and-milestones-to-track-work/creating-and-editing-milestones-for-issues-and-pull-requests) in which was resolved.

Pull Request title is used to generate Release Notes - should be similar or the same as merged commit.

We should always provide changes by Pull Request. Direct commits will be not visible in Milestones issues list nor Release Notes.

## Release Notes

Only Pull Requests with status **Merged** will be visible in Release Notes.

We use GitHub [Release Notes](https://docs.github.com/en/repositories/releasing-projects-on-github/managing-releases-in-a-repository).

We use the [Release Drafter Action](https://github.com/marketplace/actions/release-drafter)
to prepare Release Notes.

Default label configurations are in the [maven-gh-actions-shared](https://github.com/apache/maven-gh-actions-shared/blob/main/.github/release-drafter.yml) project.

## How To Use Issue and Pull Request Details?

### Assignee

Committers can assign a GitHub Issue or Pull Request to a specific committer if that person seems
to be well-placed to address it.

### Reviewers

We should invite persons to review for every change, even it is simply one, review can increase code quality.

### Priority

For priority (equivqlent to [Jira Priority](https://confluence.atlassian.com/adminjiraserver/defining-priority-field-values-938847101.html)), we can use GitHub Issues [labels](https://docs.github.com/en/issues/using-labels-and-milestones-to-track-work/managing-labels):

- `priority:trivial`
- `priority:minor`
- `priority:major`
- `priority:critical`
- `priority:blocker`

### Type

For GitHub Issue and Pull Requests we use [labels](https://docs.github.com/en/issues/using-labels-and-milestones-to-track-work/managing-labels), like:

- `bug`
- `dependencies`
- `documentation`
- `enhancement`
- `maintenance`

### Component/s

To assign an issue/PR to component we can use [labels](https://docs.github.com/en/issues/using-labels-and-milestones-to-track-work/managing-labels), like: `component:name`

### Fix Version/s

We use [GitHub Issues `Milestones`](https://docs.github.com/en/issues/using-labels-and-milestones-to-track-work/about-milestones) to assign to fix versions.

[Issues and PR associated to a Milestone](https://docs.github.com/en/issues/using-labels-and-milestones-to-track-work/viewing-your-milestones-progress) are publicly available, so we can use Milestone view during voting.

## Further Links

- [GitHub Pull requests documentation](https://docs.github.com/en/pull-requests)
- [GitHub Issues documentation](https://docs.github.com/en/issues)
- [GitHub Repository releases documentation](https://docs.github.com/en/repositories/releasing-projects-on-github/about-releases)
- [release-drafter](https://github.com/release-drafter/release-drafter)

