---
title: Doxia Developers Centre
author: 
  - Vincent Siveton
date: 2008-03-02
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

# Create a New Doxia Macro

You need to add the following [plexus-component-metadata plugin](https://codehaus-plexus.github.io/plexus-containers/plexus-component-metadata/) configuration to generate the correct Plexus _component.xml_ file from annotations for the project containing your macro:

```unknown
<project>
  ...
  <build>
    ...
    <plugins>
      <plugin>
        <groupId>org.codehaus.plexus</groupId>
        <artifactId>plexus-component-metadata</artifactId>
        <executions>
          <execution>
            <goals>
              <goal>generate-metadata</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      ...
    </plugins>
  ...
  </build>
  ...
</project>
```

You should implement the _AbstractMacro_ class:

```unknown
import org.apache.maven.doxia.macro.AbstractMacro;

/**
 * @plexus.component role="org.apache.maven.doxia.macro.Macro" role-hint="my"
 */
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

    ```unknown
    %{my|myParam=myValue} <!-- my is the macro name defined by role-hint -->
    ```

- XDoc

    ```unknown
    <macro name="my"> <!-- my is the required macro name defined by role-hint -->
      <param name="myParam" value="myValue"/>
    </macro>
    ```

## <a id="References"></a>References

- [Doxia Modules Guide](../modules/index.html)
- [Doxia Macros Guide](../macros/index.html)
- [Doxia API Reference](../doxia/apidocs/index.html)
- [Doxia Sitetools API Reference](../doxia-sitetools/apidocs/index.html)
- [Upgrading from Plexus Javadoc Tags to Plexus Java Annotations](/plugin-developers/cookbook/plexus-plugin-upgrade.html)
