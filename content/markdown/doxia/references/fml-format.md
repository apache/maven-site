---
title: The FML (FAQ Markup Language) format
author: 
  - Lukas Theussl
---

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

# The FML format

## Overview

An &apos;fml&apos; (FAQ Markup Language) is an XML document conforming to a small and simple set of tags. The format was first used in the [Maven 1](http://maven.apache.org/maven-1.x/), version of the [FAQ plugin](http://maven.apache.org/maven-1.x/plugins/faq/). 

## The FML format

Below the root element `faqs` there are one or more `part` elements. Each `part` element has a `title` and contains one or more `faq` elements. Each `faq` element has a `question` and an `answer` element. The contents of `title`, `question` and `answer` are parsed with the [XDoc parser](xdoc-format.html). The full documentation is available in the annotated [fml-1.0.1.xsd](https://maven.apache.org/xsd/fml-1.0.1.xsd) schema. 

## FML Sample

The following is a sample FML document: 

```unknown
<?xml version="1.0" encoding="UTF-8"?>
<faqs xmlns="http://maven.apache.org/FML/1.0.1"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/FML/1.0.1 http://maven.apache.org/xsd/fml-1.0.1.xsd"
  title="Frequently Asked Questions"
  toplink="false">

  <part id="general">
    <title>General</title>

    <faq id="whats-foo">
      <question>
        What is Foo?
      </question>
      <answer>
        <p>some markup goes here</p>

        <source>some source code</source>

        <p>some markup goes here</p>
      </answer>
    </faq>

    <faq id="whats-bar">
      <question>
        What is Bar?
      </question>
      <answer>
        <p>some markup goes here</p>
      </answer>
    </faq>
  </part>

  <part id="install">

    <title>Installation</title>

    <faq id="how-install">
      <question>
        How do I install Foo?
      </question>
      <answer>
        <p>some markup goes here</p>
      </answer>
    </faq>

  </part>

</faqs>
```

## Validation

Doxia is able to validate your fml files as described [here](../doxia/doxia-modules/doxia-module-fml/using-fml-xsd.html). 

