## Preparing Component Release Documentation

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

directory (~artifact id): <input type="text" name="artifactId" id="artifactId"></input><br/>
version: <input type="text" name="version" id="version"></input><br/>
<button onclick="instructions()">Publish instructions</button>

</td></tr>

<tr><td colspan="3">
<h3>instructions to publish component release documentation</h3>
<pre id="svnmucc">svnmucc -m "Publish ${artifactId} ${version} documentation" \
  -U https://svn.apache.org/repos/infra/websites/production/maven/components \
  cp HEAD ${category}-archives/${artifactId}-LATEST ${category}-archives/${artifactId}-${version} \
  rm ${category}/${artifactId} \
  cp HEAD ${category}-archives/${artifactId}-LATEST ${category}/${artifactId}</pre>
</td></tr>

<tr><td colspan="2"><a href="" id="link-index-page" target="_blank" style="display:none">edit category index page through CMS</a><br/>
<iframe id="index-page" src="" width="100%" height="300px"></iframe>
</td>
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
  var indexUrl = index ? ('http://maven.apache.org/' + index) : '';
  indexPage.setAttribute('src', indexUrl);
  linkIndexPage.setAttribute('href', 'https://cms.apache.org/redirect?uri='+escape(indexUrl));
  linkIndexPage.style.display = index ? "initial":"none";
  archives.setAttribute('src', 'http://maven.apache.org/' + archive + '?C=M;O=D');
  instructions();
}

function escapeRegExp(string) {
    return string.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
}
function replaceAll(string, find, replace) {
  return string.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}

function instructions() {
  var category = document.location.search.substr(1);
  var artifactId = document.getElementById('artifactId').value;
  var version = document.getElementById('version').value;
  var svnmucc = svnmuccTemplate;
  if (category == "core") {
    artifactId = "Maven";
    svnmucc = svnmucc.substr(0, svnmucc.indexOf("  rm "))
    svnmucc = replaceAll(svnmucc, '${artifactId}-LATEST', '3-LATEST');
    svnmucc = replaceAll(svnmucc, '${category}-archives', 'ref');
    svnmucc = replaceAll(svnmucc, '${artifactId}-${version} \\', '${version}\n\n');
  }
  if (category.indexOf("doxia") == 0) {
    svnmucc = replaceAll(svnmucc, 'maven/components', 'maven-doxia/content');
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
  document.getElementById('svnmucc').innerHTML = svnmucc;
}

var category = document.location.search.substr(1);
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
