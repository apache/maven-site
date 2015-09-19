## Preparing Component Release Documentation

<!-- TODO add javascript to make this dynamic:
1. select a component category to display index page, archives and list content (sorted by 
2. select a component to update instructions to publish release documentation
--> 

<table>
<tr><td>
<h2>Component category</h2>
<ul>
<li><a href="#core">core</a></li>
<li><a href="#shared">shared components</a></li>
<li><a href="#plugins">plugins</a></li>
<li><a href="#pom">poms</a></li>
<li><a href="#skins">skins</a></li>
<li><a href="#others">others</a></li>
</ul>
 
</td><td>

<h2>plugins content</h2>

<ul>
<li>maven-javadoc-plugin: <a href="/plugins/maven-javadoc-plugin/">current</a> / <a href="/plugins-archives/maven-javadoc-plugin-LATEST/">LATEST</a></li>
<li>...</li>
</ul>
</td></tr>

<tr><td colspan="2">
instructions to publish maven-javadoc-plugin 1.9 release documentation
<pre>svnmucc -m "Publish maven-javadoc-plugin 1.9 documentation" \
  -U https://svn.apache.org/repos/infra/websites/production/maven/components \
  cp HEAD plugins-archives/maven-javadoc-plugin-LATEST plugins-archives/maven-javadoc-plugin-1.9 \
  rm plugins/maven-javadoc-plugin \
  cp HEAD plugins-archives/maven-javadoc-plugin-LATEST plugins/maven-javadoc-plugin</pre>
</td></tr>

<tr><td>category index page<br/>
<iframe src="/plugins/" width="100%" height="300px"></iframe>
</td>
<td>archives directory<br/>
<iframe src="/plugins-archives/?C=M;O=D" width="100%" height="300px"></iframe>
</td>
</tr>
</table>
