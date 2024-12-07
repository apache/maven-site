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

A GitHub Issue and Pull Request should have a label with the type, like `bug`, `enhancement` and so on.
Pull Request without labels will be not categorized in Release Notes.

Closed GitHub Issue and Pull Request should have milestone in which was resolved.

Pull Request title is used to generate Release Notes - should be similar or the same as merged commit.

We should always provide changes by Pull Request. Direct commits will be not visible in Release Notes.

## Release Notes

Only Pull Requests with status **Merged** will be visible in Release Notes.

We use GitHub Release Notes.

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

For priority, we can use labels:

- `priority:low`
- `priority:medium`
- `priority:high`
- `priority:critical`

### Type

For GitHub Issue and Pull Requests we use labels, like:

- `bug`
- `dependencies`
- `documentation`
- `enhancement`
- `maintenance`

### Component/s

To assign an issue/PR to component we can use labels, like: `component:name`

### Fix Version/s

We use `Milestones` to assign to fix versions.

Milestones are public available so we can use it during voting. 

## Further Links

- [GitHUb Pull requests documentation](https://docs.github.com/en/pull-requests)
- [GitHub Issues documentation](https://docs.github.com/en/issues)
- [release-drafter](https://github.com/release-drafter/release-drafter)