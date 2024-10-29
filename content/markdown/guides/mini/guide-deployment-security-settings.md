---
title: Guide to Deployment and Security Settings
author: 
  - Jason van Zyl
date: 2005-10-12
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
# Security and Deployment Settings

Repositories to deploy to are defined in a project in the `distributionManagement` section\. However, you cannot put your username, password, or other security settings in that project\. For that reason, you should add a server definition to your own settings with an id that matches that of the deployment repository in the project\.

In addition, some repositories may require authorisation to download from, so the corresponding settings can be specified in a server element in the same way\.

Which settings are required will depend on the type of repository you are deploying to\. As of the first release, only SCP deployments and file deployments are supported by default, so only the following SCP configuration is needed:

```

<settings>
  .
  .
  <servers>
    <server>
      <id>repo1</id>
      <username>repouser</username>
      <!-- other optional elements:
        <password>my_login_password</password>
        <privateKey>/path/to/identity</privateKey> (default is ~/.ssh/id_dsa)
        <passphrase>my_key_passphrase</passphrase>
      -->
    </server>
  </servers>
  .
  .
</settings>
```

To encrypt passwords in these sections, refer to [ Encryption Settings](\./guide\-encryption\.html)\.

**Note**: The settings descriptor documentation can be found on the [Maven Local Settings Model Website](\.\./\.\./maven\-settings/settings\.html)\.

