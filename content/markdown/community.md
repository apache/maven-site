# The Maven Community
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
Maven, like any other open source project, relies heavily on the efforts
of the entire user community to contribute improvements,
report defects, communicate use cases, write documentation,
and assist other users in need. This is a quick guide outlining
what members of the Maven community can do to make the system work
better for everyone.

## Helping With Maven

There is already a comprehensive [Guide to Helping With
Maven](./guides/development/guide-helping.html). That guide focuses upon
beginning as a supporter, with information on how to help the coding
effort.

### Commit Questions or Answers to the Maven User FAQ

If you find things which are not correct or could be
explained in a better way or you simply miss things
do not hesitate to contact the maven community via
the users mailing list and tell us about it.

### Help Log Defects in JIRA

Just as any other healthy project requires a quick turn-around on
defects, and a transparent method of users to have their wishes heard,
so too does Maven need your help. Refer to the [Issue
Management](./issue-management.html) page.

### Developers

For Maven developers, committers, PMC: there is a [Developers
Guide](./developers/index.html).

## Being a Good Maven Citizen

The concept of a public repository built into the core architecture of
Maven makes it necessarily community-centric. There are a few simple
things that Maven users may do to help keep that community thriving.

### Be a Kind Public Repository User

The best thing that a user can do is to set up their own remote
repository mirror containing the projects needed: this is called a
[repository manager](.//repository-management.html). This reduces strain
on the Maven central repository, and allows new users to get acquainted
with Maven easier and quicker. This is especially important for
power-users and corporations. The incentive behind this is, controlling
your own servers can give you desired level of security and more control
over uptime, resulting in a better experience for your users. With that
said, keep the following sentiment in mind:

*DO NOT wget THE ENTIRE REPOSITORY!*

Please take only the jars you need. We understand this is may entail
more work, but grabbing more than 1,7 TiB of binaries really kills our
servers.

## User Gathering Spots

These are a few of the watering holes around which Maven users tend to
gather.

### Mailing Lists

Maven has a number of [Mailing Lists](./mailing-lists.html), and the Maven
User List is specifically dedicated to answering questions about all
Maven things.

### Slack

For people actively contributing to Maven, especially committers, there
is [the ASF Slack workspace](https://infra.apache.org) available to discuss
issues, solve problems and build community in real-time.
