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

# Do you want to become a Maven Committer?

_TL;DR To become a Maven committer write good patches and get them applied._

The Apache Software Foundation is a meritocracy.
This means you gain status based on the merit of your work and actions.
In fact the status you gain is a recognition of your merit.

Maven is project of the Apache Software Foundation (ASF), so the team has to follow the ASF rules and way.
One of those rules is that we must not hand out commit access to anyone who asks for it.

To gain commit access you must establish your merit by submitting patches that get picked up by existing committers.
After you have contributed enough patches to establish merit, the project management committee decides whether you can be trusted with commit access.

# What makes a good patch?

A good patch is a patch that applies cleanly and includes tests that cover both the positive and negative case and has documentation where relevant.

## Example

You were implementing a patch to fix [MNG-4612](http://issues.apache.org/jira/browse/MNG-4612), you would first need to write a test case that is failing when trying to encrypt

```
{DESede}y+qq...==
```

and a second test case that is passing when trying to encrypt

```
password
```

This is in order to be sure that you have written an effective test case that can pass for good data. Then you implement the fix and all the tests should pass. You then take a Subversion compatible† diff of the source code and attach that to the issue in question.

To understand how your patch might get evaluated, here is a list how Maven team member Stephen Connolly applies patches:

> 1. I look at the actual diff: if there is a lot of formatting changes irrelevant to the issue being fixed => **Patch is not good, ask for a clean patch**
> 2. I look at the list of files in the diff, if there are no tests => **Patch is not good, ask for test cases**
> 3. I look at the issue and if the issue requires documentation be updated and there is no documentation changes in the patch => **Patch is not good, ask for documentation changes in the patch**
> 4. I take a clean checkout of the source that the patch applies to and try to apply the patch... if it does not apply clean => **Patch is not good, ask for an updated patch**
> 5. I revert the src/main and run the tests. If the tests all pass, then there are no test cases to catch the bug => **Patch is not good, ask for proper tests**
> 6. I revert src and run the tests. If any tests fail, then there is something wrong with the existing code => **If I have time, I might try and fix the issue, otherwise I just move on**
> 7. I apply the patch a second time and run the tests. If the tests all pass => **Patch is good, I commit the patch and mark the issue as resolved**

# How to get your patches noticed

The simplest way to get your patches noticed is to create a pull request and link them to the issue that they fix.

Remember that the Maven project is run by volunteers in their spare time, so very often we may not have the time to respond to your patch for a few days.

If a week has passed with no comments on your pull request, feel free to send _one and only one_ email to the [dev@maven.apache.org](mailto:dev@maven.apache.org) mailing list to see if your patch can get noticed.

**Note:** You need to be fairly confident that your patch is a good patch, because if you keep on pestering the Maven developers looking to have non-good patches applied, your merit will become negative and people will be less inclined to help you get your patches applied.
This is why you should send one and _only one_ email about your patch on any specific issue.

