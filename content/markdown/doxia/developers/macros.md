---
title: Doxia Developers Centre
author: 
  - Vincent Siveton
date: 2008-03-02
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

# Create a New Doxia Macro

A macro is a JSR-330 component named after the macro. Set up the project that contains it as described in [How to use JSR-330](/maven-jsr330.html#How_to_use_JSR-330): a `javax.inject` dependency and the `sisu-maven-plugin` index.

You should implement the _AbstractMacro_ class:

```java
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;

@Singleton
@Named("my")
public class MyMacro
    extends AbstractMacro
{
...
    public void execute( Sink sink, MacroRequest request )
        throws MacroExecutionException
    {
        String myValue = (String) request.getParameter( "myParam" );
...
    }
...
}
```

To use it, you need to write the following markups:

- APT

    ```text
    %{my|myParam=myValue} <!-- my is the macro name set by @Named -->
    ```

- XDoc

    ```xml
    <macro name="my"> <!-- my is the required macro name set by @Named -->
      <param name="myParam" value="myValue"/>
    </macro>
    ```

## <a id="References"></a>References

- [Doxia Modules Guide](../modules/index.html)
- [Doxia Macros Guide](../macros/index.html)
- [Doxia API Reference](../doxia/apidocs/index.html)
- [Doxia Sitetools API Reference](../doxia-sitetools/apidocs/index.html)
- [Maven & JSR-330](/maven-jsr330.html)
