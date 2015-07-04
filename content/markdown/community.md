## The Maven Community

Maven, like any other opensource project, relies heavily on the efforts
of the entire user community to be ever vigilant for improvements,
logging of defects, communicating use-cases, generating documentation,
and being wary of other users in need. This is a quick guide outlining
what members of the Maven community may do to make the system work
better for everyone.

### Helping With Maven

There is already a comprehensive [Guide to Helping With
Maven](./guides/development/guide-helping.html). That guide focuses upon
beginning as a supporter, with information on how to help the coding
effort.

#### Commit Questions or Answers to the Maven User FAQ

If you find things which are not correct or could be
explained in a better way or you simply miss things
do not hesitate to contact the maven community via
the users mailing list and tell us about it.

#### Help Log Defects in JIRA

Just as any other healthy project requires a quick turn-around on
defects, and a transparent method of users to have their wishes heard,
so too does Maven need your help. Refer to the [Issue
Tracking](./issue-tracking.html) page.

#### Developers

For Maven developers, committers, PMC: there is a [Developers
Guide](./developers/index.html).

### Being a Good Maven Citizen

The concept of a public repository built into the core architecture of
Maven makes it necessarily community-centric. There are a few simple
things that Maven users may do to help keep that community thriving.

#### Be a Kind Public Repository User

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

### User Gathering Spots

These are a few of the watering holes around which Maven users tend to
gather.

#### Mailing Lists

Maven has a number of [Mailing Lists](./mail-lists.html), and the Maven
User List is specifically dedicated to answering questions about all
Maven things.

#### IRC (Internet Relay Chat)

Log into the `#maven` IRC channel on `irc.freenode.net` with your
favorite IRC client or [with web IRC chat](https://webchat.freenode.net/).

