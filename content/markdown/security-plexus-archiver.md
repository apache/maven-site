# Zip Slip Vulnerability

As part of [a broader research](https://snyk.io/research/zip-slip-vulnerability),
the Snyk Security Research Team discovered
an arbitrary file write generic vulnerability, that can be achieved using a 
specially crafted zip (or bzip2, gzip, tar, xz, war) archive, that holds 
path traversal filenames. So when the filename gets concatenated to the 
target extraction directory, if the extraction tool used does not make 
sufficient checks, the final path ends up outside of the target folder.

The Apache Maven team has been informed because the plexus-archiver library 
did not make sufficient checks and it is a library used by most of the 
packaging plugins.
Affected versions of plexus-archiver are [,3.4]+[3.5], fixed versions are 3.4.1 & 3.6.0,
with issue management [plexus-archiver #87](https://github.com/codehaus-plexus/plexus-archiver/pull/87) and
Snyk vulnerability report [SNYK-JAVA-ORGCODEHAUSPLEXUS-31680](https://snyk.io/vuln/SNYK-JAVA-ORGCODEHAUSPLEXUS-31680)


## What parts of Maven are vulnerable?

Apache Maven itself is not vulnerable, since Maven doesn't unpack by itself:
unpacking actions are done by plugins.

Reading malicious archives in memory is also not an issue, only when 
unpacking such archives to disk may cause issues, however in general 
Maven plugins don't unpack archives.

The issue is regarding malicious artifacts and we haven't been able to create
such artifacts with our Maven plugins, hence there's no additional action 
required to prevent the creation of malicious archives at this level.

The following plugins use plexus-archiver to unpack dependencies to disk
and have been identified as potential triggers for exposing the vulnerability.
The matrix contains the name of the component/plugin, which versions are affected,
which versions contain the fixed and the link to the corresponding issue.

<table>
 <tr>
   <th>plugin</th>
   <th>affected versions</th>
   <th>fixed version</th>
   <th>issue</th>
   <th>details</th>
 </tr>
 <tr>
   <td>maven-dependency-plugin</td>
   <td>(,3.1.0]</td>
   <td>3.1.1</td>
   <td><a href="https://issues.apache.org/jira/browse/MDEP-611">MDEP-611</a></td>
   <td>unpack and unpack-dependencies goals (not used unless configured explicitly)</td>
 </tr>
 <tr>
   <td>maven-ear-plugin</td>
   <td>(,3.0.0]</td> 
   <td>3.0.1</td>
   <td><a href="https://issues.apache.org/jira/browse/MEAR-268">MEAR-268</a></td>
   <td>EAR <a href="/plugins/maven-ear-plugin/modules.html">modules</a> feature (not used unless configured explicitly)</td>
 </tr>
 <tr>
   <td>maven-javadoc-plugin</td>
   <td>[2.5,3.0.0]</td>
   <td>3.0.1</td>
   <td><a href="https://issues.apache.org/jira/browse/MJAVADOC-520">MJAVADOC-520</a></td>
   <td>resourcesArtifacts and includeDependencySources features (not used unless configured explicitly)</td>
 </tr>
 <tr>
   <td>maven-war-plugin</td>
   <td>[2.1-alpha-1,3.2.0]</td>
   <td>3.2.1</td>
   <td><a href="https://issues.apache.org/jira/browse/MWAR-416">MWAR-416</a></td>
   <td><a href="/plugins/maven-war-plugin/overlays.html">overlay</a> feature (active by default when packaging=war on dependencies with type=war)</td>
  </tr>
  <tr>
    <td>maven-plugin-plugin</td>
    <td>[3.0,3.5.1]</td>
    <td>3.5.2</td>
    <td><a href="https://issues.apache.org/jira/browse/MPLUGIN-338">MPLUGIN-338</a></td>
    <td>scan dependencies sources for javadoc annotations (active by default when packaging=maven-plugin)</td>
  </tr>
</table>


Apache Ant up to 1.9.11 has the same issue with its unzip task: it will be fixed in 1.9.12 <a href="https://github.com/apache/ant/commit/857095da5153fd18504b46f276d84f1e76a66970">857095da5153fd18504b46f276d84f1e76a66970</a>.

Then following Maven plugins using Ant are affected:

<table>
 <tr>
   <th>plugin</th>
   <th>affected versions</th>
   <th>fixed version</th>
   <th>issue</th>
   <th>details</th>
 </tr>
  <tr>
    <td>maven-antrun-plugin</td>
    <td>(,1.8]</td>
    <td></td>
    <td><a href="https://issues.apache.org/jira/browse/MANTRUN-214">MANTRUN-214</a></td>
	<td></td>
  </tr>
  <tr>
    <td>maven-plugin-plugin:maven-script-ant</td>
    <td>[2.0,3.5.2]</td>
    <td></td>
    <td><a href="https://issues.apache.org/jira/browse/MPLUGIN-340">MPLUGIN-340</a></td>
	<td><a href="/plugin-tools/maven-plugin-plugin/examples/ant-mojo.html">Ant Mojo Wrapper</a></td>
  </tr>
</table>
	 
Following plugins use plexus-archiver but are not affected since they only _create_ archives:

- maven-acr-plugin
- maven-assembly-plugin
- maven-ejb-plugin
- maven-jar-plugin
- maven-jlink-plugin
- maven-rar-plugin
- maven-repository-plugin
- maven-site-plugin
- maven-source-plugin

## When are you affected by this vulnerability?

The vulnerability is like a Trojan Horse, the malicious archive must first enter the system, normally achieved
with a downloaded of a dependency. Once downloaded there's no direct danger, the user must take some specific 
actions before becoming a victim. This only happens when all of the following criteria are met:

- There's an archive available in a repository that is malicious.
- Your Maven project or one of its dependencies must find the malicious jar interesting enough to add it as a dependency.
- There must be a reason to unpack, as defined in the matrix above.
- The OS must allow the unpack process to put the malicious file at the specified location.
- The malicious file must be executed or used.

The cause can be described as a breach in the chain of trust. Adding a dependency does imply that you trust it, but also
that you trust all of the indirect dependencies. If there's no trust, you better not add that dependency.
