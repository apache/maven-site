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

# Building multi-module sites

When running `mvn site` on a top level project, Maven builds the sites of all modules defined in the `modules` section of the POM individually. Each site is generated in the `target` directory of its own project. As a result, relative links between different modules do NOT work in the build output. They work when you deploy or stage the site.

## Staging and deploying

To preview the whole tree of a multi-module site, use the [`site:stage`](/plugins/maven-site-plugin/stage-mojo.html) goal. It copies the individual sites to their proper place at the staging location.

[`site:deploy`](/plugins/maven-site-plugin/deploy-mojo.html) does the same thing as `site:stage`, but uses the URL defined in the `<distributionManagement>` element of the POM as the deployment location. This is usually a remote URL, but both remote protocols like `scp` and local protocols like `file` are supported.

Finally, [`site:stage-deploy`](/plugins/maven-site-plugin/stage-deploy-mojo.html) does the same thing as `site:deploy` but uses the `stagingDirectory` parameter as the deployment location. This deploys the site to a remote staging area (`site:stage` is always local).

**Notes:**

- If subprojects inherit the (distribution) site URL from a parent POM, they automatically append their _artifactId_ to form their effective deployment location. This goes for both the project URL and the URL defined in the `<distributionManagement>` element of the POM.
- If your multi-module tree does not follow the Maven conventions, or if module directories are named differently than module artifacts, you have to specify the URLs for **each** child project. See also [How does the Site Plugin use the `<url>` element in the POM?](/plugins/maven-site-plugin/faq.html#Use_of_url).
- The `pom.xml` of the topmost project in a multi-module build must define the `distributionManagement` URL element (called "rootURL" hereafter). The rootURL must be the topmost `distributionManagement` URL in the multi-module project, implying that any `distributionManagement` URL defined within another project in a multi-module build must start with the rootURL and append unique paths (to be situated "below" the rootURL).
- All projects in multi-module builds must define unique `distributionManagement` URL elements, below the root `distributionManagement` URL in terms of URL path.
- If the artifactId and module name (the directory name) are not identical for any project within a multi-module build, the `distributionManagement` URL element must be defined in the `pom.xml` file of that project.

## Inheritance

Site descriptors are inherited along the same lines as project descriptors are. If you want the site descriptor of your project to be inherited, you need to attach it to the main artifact of the project. Use the [`site:attach-descriptor`](/plugins/maven-site-plugin/attach-descriptor-mojo.html) goal to attach the site descriptor to the main artifact.

In Maven 3 you must attach the site descriptor yourself, even for projects with packaging set to `pom`.

By default, all the parent descriptor settings are inherited with the only exception of menus, see below. From the body, the links and breadcrumbs accumulate to contain all the links and breadcrumbs of the parent site descriptor. If the parent contains a breadcrumb and the inheriting site descriptor doesn't, a breadcrumb with the name of the current site descriptor is added automatically.

It is possible to inherit menus as well. To do so, use the `inherit` attribute in the site descriptor. This can be either `top` or `bottom`, indicating where the inherited menu is placed. For example:

```xml
<site>
  ...
  <body>
    ...
    <menu name="My Inherited Menu" inherit="top">
      ...
    </menu>
    ...
  </body>
  ...
</site>
```

## Aggregate Reports

Some reports can be run against the sum of all modules: these are aggregate reports. This is the case of maven-javadoc-plugin, maven-jxr-plugin or maven-checkstyle-plugin.

To benefit from aggregate reports, configure a report set in the parent POM, and turn inheritance off so that the aggregate report does not run in the modules. For example, with maven-jxr-plugin:

```xml
<project>
  ...
  <reporting>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jxr-plugin</artifactId>
        <version>3.6.0</version>
        <reportSets>
          <reportSet>
            <id>aggregate</id>
            <inherited>false</inherited>
            <reports>
              <report>aggregate</report>
            </reports>
          </reportSet>
        </reportSets>
      </plugin>
    </plugins>
  </reporting>
  ...
</project>
```
