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

# About Maven

<!--MACRO{toc|fromDepth=2}-->

## Tell me about the goals of Maven 3.0

Maven 3.0 is an important internal improvement with following goals:

- Backward compatibility
- Performance improvement
- Parallel builds
- Better error and integrity reporting

## Can I get involved?

The Maven project welcomes anyone that wishes to contribute to do so by providing patches to the source code,
participating in design discussions, or to help out on the users mailing list by answering questions.

Frequent contributors recognised by existing committers to the project may be asked if they would like to join the
project.
For instructions on checking out and building Maven 2 and 3,
see [Building Maven](./guides/development/guide-building-maven.html).
For more information, please see [How to Help](https://maven.apache.org/guides/development/guide-helping.html).

## What plugin languages does Maven support? What about [insert language here]?

As of the current release, Maven supports pure Java and Beanshell.
Java is the preferred language for its familiarity and speed.
We get asked a lot whether Maven will support other languages, in particular Groovy.
We will allow the use of Groovy (and virtually any other scripting language - if there is demand), if someone can commit
a small amount of time to implementing a factory for it.
Beanshell is more mature, and we want a language that users will find answers for when they look, and that when they
find bugs, it is clearly defined where they actually are.
We may consider emphasising a different language if there are compelling technical reasons for doing so.
If you have a suggestion, feel free to contact the development list.

## Where do I get help with Maven?

Help for Maven can be obtained by subscribing and posting to
the [Maven Users List](https://maven.apache.org/mailing-lists.html).
