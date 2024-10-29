---
title: Do you want to become a Maven Committer?
author: 
  - Stephen Connolly
  - Robert Scholte
date: 2012-07-11  
2017-07-21
---

<!-- Licensed to the Apache Software Foundation (ASF) under one-->
<!-- or more contributor license agreements.  See the NOTICE file-->
<!-- distributed with this work for additional information-->
<!-- regarding copyright ownership.  The ASF licenses this file-->
<!-- to you under the Apache License, Version 2.0 (the-->
<!-- "License"); you may not use this file except in compliance-->
<!-- with the License.  You may obtain a copy of the License at-->
<!---->
<!--   http://www.apache.org/licenses/LICENSE-2.0-->
<!---->
<!-- Unless required by applicable law or agreed to in writing,-->
<!-- software distributed under the License is distributed on an-->
<!-- "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY-->
<!-- KIND, either express or implied.  See the License for the-->
<!-- specific language governing permissions and limitations-->
<!-- under the License.-->
<!-- NOTE: For help with the syntax of this file, see:-->
<!-- http://maven.apache.org/doxia/references/apt-format.html-->
<!-- Original post: http://javaadventure.blogspot.nl/2012/07/do-you-want-to-become-maven-committer.html-->
# Do you want to become a Maven Committer?

The Apache Software Foundation is a meritocracy\. By this we mean that you gain status based on the merit of your work and actions\. In fact the status that you gain is a recognition of the merit of your work and actions\.

Maven is an Apache project, that means that we have to follow the Apache rules and way\. One of those rules is that we cannot hand out commit access to anyone who asks for it\.

To gain commit access you must establish your merit by submitting patches that get picked up by existing committers\.

After you have contributed enough patches to establish merit, the project management committee decides whether you can be trusted with commit access\.

_The reality is that &quot;It is what it is&quot;TL;DR To become a Maven committer write good patches and get them applied\._

# What makes a good patch?

A good patch is a patch that applies cleanly and includes tests that cover both the positive and negative case and has documentation where relevant\.

For example, if you were implementing a patch to fix [MNG\-4612](http://issues\.apache\.org/jira/browse/MNG\-4612) you would first need to write a test case that is failing when trying to encrypt

```
{DESede}y+qq...==
```

and a second test case that is passing when trying to encrypt

```
password
```

This is in order to be sure that you have written an effective test case that can pass for good data\. Then you implement the fix and all the tests should pass\. You then take a Subversion compatible† diff of the source code and attach that to the issue in question\.

To understand how your patch gets evaluated, here is how I apply patches:

1. I look at the actual diff, if there is a whole lot of formatting changes irrelevant to the issue being fixed =&gt; **Patch is no good, ask on JIRA for a clean patch**
1. I look at the list of files in the diff, if there are no tests =&gt; **Patch is no good, ask on JIRA for test cases**
1. I look at the issue and if the issue requires documentation be updated and there is no documentation changes in the patch =&gt; **Patch is no good, ask on JIRA for documentation changes in the patch**
1. I take a clean checkout of the source that the patch applies to and try to apply the patch\.\.\. if it does not apply clean =&gt; **Patch is no good, ask on JIRA for an updated patch**
1. I revert the src/main and run the tests\. If the tests all pass, then there are no test cases to catch the bug =&gt; **Patch is no good, ask on JIRA for proper tests**
1. I revert src and run the tests\. If any tests fail, then there is something wrong with the existing code =&gt; **If I have time I might try and fix the issue, otherwise I just move on**
1. I apply the patch a second time and run the tests\. If the tests all pass =&gt; **Patch is good, I commit the patch and mark the JIRA as resolved**

So there you have it, my guide to writing good patches\.\.\. now the next step is getting your patches noticed\.\.\.

# How to get your patches noticed

The simplest way to get your patches noticed is to submit them to the JIRA issue that they fix\.

Remember that the Maven project is run by volunteers in their spare time, so very often we may not notice your patch for a few days\. 

If you are certain that your patch is a good patch, and a week has passed with no comments on JIRA, then you should send _one and only one_ email to the [dev@maven\.apache\.org](mailto:dev@maven\.apache\.org) mailing list to see if your patch can get noticed\.

**Note:** you need to be fairly confident that your patch is a good patch, because if you keep on pestering the Maven developers looking to have non\-good patches applied, your merit will become negative and people will be less inclined to help you get your patches applied\.\.\. also this is why you should send one and _only one_ email about your patch on any specific JIRA issue\.

