## Preparing Component Release Documentation

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

select component category, then type artifact id and version to generate svn commands:

<table>
<tr><td>
<h3>Component category</h3>
<ul>
<li><a href="?core">core</a></li>
<li><a href="?shared">shared components</a></li>
<li><a href="?plugins">plugins</a></li>
<li><a href="?pom">poms</a></li>
<li><a href="?resolver">resolver</a></li>
<li><a href="?skins">skins</a></li>
<li><a href="?doxia">Doxia</a></li>
<li><a href="?doxia-sitetools">Doxia Sitetools</a></li>
<li><a href="?doxia-tools">Doxia Tools</a></li>
<li><a href="?others">others</a></li>
</ul>

</td><td>

<h3>Component information</h3>

<div>directory (~artifact id): <input type="text" name="artifactId" id="artifactId" style="vertical-align: baseline"/></div>
<div>version: <input type="text" name="version" id="version" style="vertical-align: baseline"/></div>
<div id="v4x-box"><b>4.x</b> version suffix: <input type="checkbox" id="v4x" style="vertical-align: baseline"/></div>
<div><br/><button onclick="instructions()">Publish instructions</button></div>

</td></tr>

<tr><td colspan="3">
<h3>instructions to publish component release documentation</h3>
<pre id="svnmucc">svnmucc -m "Publish ${artifactId} ${version} documentation" \
  -U https://svn.apache.org/repos/asf/maven/website/components \
  cp HEAD ${category}-archives/${artifactId}-LATEST${vSuffix} ${category}-archives/${artifactId}-${version} \
  rm ${category}/${artifactId}${vSuffix} \
  cp HEAD ${category}-archives/${artifactId}-LATEST${vSuffix} ${category}/${artifactId}${vSuffix}</pre>
</td></tr>

<tr><td colspan="2"><iframe id="index-page" src="" width="100%" height="300px"></iframe></td>
<td>archives directory<br/>
<iframe id="archives" src="" width="100%" height="300px"></iframe>
</td>
</tr>
</table>

<script type="text/javascript"><![CDATA[
function selectCategory(index, archive) {
  var indexPage = document.getElementById('index-page');
  var linkIndexPage = document.getElementById('link-index-page');
  var archives = document.getElementById('archives');
  var indexUrl = index ? ('https://maven.apache.org/' + index) : '';
  indexPage.setAttribute('src', indexUrl);
  archives.setAttribute('src', 'https://maven.apache.org/' + archive + '?C=M;O=D');
  instructions();
}

function escapeRegExp(string) {
    return string.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
}
function replaceAll(string, find, replace) {
  return string.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}

function instructions() {
  var category = document.location.search.substring(1);
  var artifactId = document.getElementById('artifactId').value;
  var version = document.getElementById('version').value;
  var v4x = document.getElementById('v4x').checked;
  var v4xBox = document.getElementById('v4x-box');
  var svnmucc = svnmuccTemplate;
  if (category == "core") {
    artifactId = "Maven";
    svnmucc = svnmucc.substring(0, svnmucc.indexOf("  rm "));
    svnmucc = replaceAll(svnmucc, '${artifactId}-LATEST', '3-LATEST');
    svnmucc = replaceAll(svnmucc, '${category}-archives', 'ref');
    svnmucc = replaceAll(svnmucc, '${artifactId}-${version} \\', '${version}\n\n');
    v4x = false;
    v4xBox.style.display = 'none';
  }
  if (category.indexOf("doxia") == 0) {
    svnmucc = replaceAll(svnmucc, 'maven/website/components', 'maven/doxia/website/components');
    if (category != "doxia-tools") {
      document.getElementById('artifactId').value = category;
    }
  }
  if (category == "resolver" || category == "others" || category == "doxia" || category == "doxia-sitetools") {
    // category directory is based on artifactId
    svnmucc = replaceAll(svnmucc, '${category}/${artifactId}', '${artifactId}');
    svnmucc = replaceAll(svnmucc, '${category}', '${artifactId}');
  }
  svnmucc = replaceAll(svnmucc, '${category}', category);
  if (artifactId) {
    svnmucc = replaceAll(svnmucc, '${artifactId}', artifactId);
  }
  if (version) {
    svnmucc = replaceAll(svnmucc, '${version}', version);
  }
  if (v4x) {
    svnmucc = replaceAll(svnmucc, '${vSuffix}', '-4.x');
  } else {
    svnmucc = replaceAll(svnmucc, '${vSuffix}', '');
  }
  document.getElementById('svnmucc').innerHTML = svnmucc;
}

var category = document.location.search.substring(1);
var svnmuccTemplate = document.getElementById('svnmucc').innerHTML;

if (category == "core") {
  selectCategory('docs/history.html', 'ref/');
} else if (category.indexOf("doxia") == 0) {
  selectCategory('doxia/' + category + '/', 'doxia/' + category + '-archives/');
} else if (category == "others") {
  selectCategory('', 'components/');
} else if (category != "") {
  selectCategory(category + '/', category+'-archives/');
}
//]]></script>

