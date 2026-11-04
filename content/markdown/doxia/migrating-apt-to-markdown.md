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

# Migrating a project's site from APT to Markdown

`doxia-converter` does most of the mechanical work of turning `src/site/apt` into
`src/site/markdown`, but a handful of differences between the two formats are not mechanical
and will silently damage a page if they go unnoticed. This page collects them, together with
the procedure that catches them.

Nothing here needs a POM change: `maven-site-plugin` already ships `doxia-module-markdown`.

## The one rule that matters

**Build the site before the migration, build it again afterwards, and compare the generated
HTML.** Every problem described below was found that way and none of them is visible by
reading the converted Markdown. A page that looks right can be missing a whole heading.

## Converting

```
doxia-converter -in src/site/apt/index.apt.vm -from apt \
                -out src/site/markdown -to markdown -outEncoding UTF-8
```

Here `doxia-converter` stands for the [Doxia Converter](/doxia/doxia-tools/doxia-converter/) command line tool,
which you run as `java -jar doxia-converter-<version>-jar-with-dependencies.jar`; the jar for each release is
[on Maven Central](https://repo.maven.apache.org/maven2/org/apache/maven/doxia/doxia-converter/).

The converter renames `x.apt` to `x.md` and `x.apt.vm` to `x.md.vm` by itself.

Pass `-outEncoding UTF-8` explicitly. Without it the output follows the autodetected input
encoding, and an ASCII source is detected as ISO-8859-1; an APT escaped space (`\ `, which
becomes U+00A0) is then written as a lone `0xa0` byte.

**Keep the YAML front matter the converter writes.** It carries the title, authors and date
from the APT header, and the skin renders them into `<title>` and `<meta>`. Delete it and the
parser falls back to the first heading for the title, so a page headed *Release Notes* under
project *Modello* silently retitles itself `Modello – Modello`. The front matter must be the
**first bytes of the file** — the parser only looks for it when the source starts with `---`,
so the licence header goes below it. RAT accepts either order.

## Velocity and Markdown disagree about `#`

A Markdown ATX heading below level one starts with `##`, which is also how a Velocity line
comment starts. In a `*.md.vm` file Velocity removes the heading before Doxia ever sees the
document: the line simply disappears from the rendered page, with no error and no warning.
Level one is safe, because a single `#` followed by a space is literal to Velocity.

**In a file that keeps its `.vm` suffix, never write an ATX heading below level one.** This is
the most damaging thing that can go wrong in the migration, and nothing anywhere reports it. It
has been hit twice on different source formats: an APT conversion lost all four of its `<h2>`
headings, and an xdoc conversion lost both of its own. In each case `mvn site` exited zero, the
page rendered, every paragraph of prose was intact, and the `%{toc}` macro that listed those
sections rendered as an empty list.

**A green build is not evidence here, and neither is reading the converted Markdown** — the
`##` looks correct in the source, because it *is* correct Markdown. Only a diff of the generated
HTML shows the headings are gone. This one failure is the reason the before/after comparison at
the top of this page is mandatory rather than advisable.

There are three ways out, in order of preference.

**Drop the `.vm`.** Most `index.apt.vm` pages only use Velocity to interpolate
`${project.name}` into the title. Spell the title out, name the file `index.md`, and the
problem disappears along with the template.

**Use a setext underline for level two.** A title line followed by a line of `-` is a level
two heading and starts with no `#` at all:

```
Configuration
-------------
```

The title line needs a **blank line before it**. Without one it is read as a lazy
continuation of the preceding paragraph or list item and the heading is lost — a different
failure from the one being fixed, and just as quiet.

**Shield deeper headings.** There is no setext form below level two, so wrap the heading in
Velocity's unparsed block:

```
#[[### Advanced options]]#
```

Doxia 2.1.1 and later shield these automatically in the site renderer, but a page that must
build with an older Doxia has to do it itself.

## References the source meant to show, not resolve

APT writes `$\{project.version\}` when a page wants to display `${project.version}` rather
than interpolate it. The parser unescapes that, so the converted `*.vm` holds a live
reference and Velocity resolves it: a page documenting a default value ends up showing
something like `[org.apache.maven.model.ReportPlugin@c754401]`.

Write these as `${esc.d}{project.version}`. `${esc.d}` comes from Velocity's `EscapeTool`,
which the site renderer always provides, and always yields a literal `$`.

Do **not** use a backslash. `\${project.version}` only renders as `${project.version}` when
`project.version` actually resolves in the document's Velocity context; when it does not,
Velocity emits the backslash too and the page shows `\${project.version}`.

The converter warns about every reference that was literal in the source and is live in the
output, so watch its output rather than hunting for these by hand. Releases up to 1.3 do not emit these
warnings yet; they and the directive warning further down are in the development version.

**On a source that was already `*.apt.vm`, read those warnings with suspicion.** The converter
compares what the APT parser saw against what Velocity will see, and it cannot tell a reference
the page wanted to *show* from one it wanted to *resolve* — in a `.vm` source the latter was
already live before the conversion and must stay live. Converting this project's own
`usage.apt.vm` produces

```
WARN "${project.version}" was written literally in the source but is a live Velocity
     reference in "usage.md.vm", so escape it there
```

for a reference that is deliberately live: it renders the current version into the sample
command lines, it did so in the APT source too, and escaping it as the warning suggests would
replace the version with the literal text `${project.version}` on the published page.

The rule the warnings approximate has two halves, and a reference needs escaping only when
**both** hold: the page means to **display** it, *and* the name actually **resolves** in that
document's context.

| | resolves | does not resolve |
|---|---|---|
| **page displays it** | escape — `${esc.d}{…}` | already correct; escaping is harmless insurance |
| **page resolves it** | leave live | broken either way; fix the reference |

Only the top-left cell is a bug, and it is the one that reads as fine in the source. A page
documenting a default value with `${project.build.outputDirectory}` publishes an absolute path
from whichever machine built the site; `${basedir}` or `${surefire.forkNumber}` in the same page
resolve to nothing and pass through literally whatever you do. The APT source tells you which
references were meant to be displayed: they are the ones written `$\{…\}`.

The same conversion warns

```
WARN Velocity directive "# java -jar target/doxia-converter-${project.version}-shaded.jar -h"
     was kept but the parser treated it as ordinary content, so check its placement
```

for a shell prompt inside a code block. A `#` followed by a space is literal to Velocity, so
these are safe; the warning asks you to check, and the check is that the line is prose or a
prompt rather than a directive.

## Do not tidy away a code block's info string

Both source formats have two verbatim forms, one boxed and one not, and the converter already
tells them apart. In APT, `+-----+` is boxed and a bare `-----` is not; in xdoc, `<source>` is
boxed and `<pre>` is not. The boxed one gets an info string of `unknown`, the unboxed one gets a
bare fence:

````
```unknown
String in = "...";
```
````

It reads like a placeholder, and deleting it is the obvious tidy-up. **Do not.** The Markdown
parser treats *any non-empty* info string as the boxed flag, so removing it silently downgrades
the block:

```
with an info string:     <pre><code class="language-java">
with none:               <pre><code class="nohighlight nocode">
```

Skins box and highlight a code block unless it is marked `nohighlight nocode`; Fluido 2, for
example, adds `class="prettyprint linenums"` to every other `<pre>`. The block still renders and
the text is unchanged, so a body-only comparison passes and the build stays green — only the box
and the line numbers are gone.

Replace `unknown` with the language the block actually is, rather than deleting it. That keeps
the box and gets the syntax highlighting the placeholder never had:

````
```java
String in = "...";
```
````

**Substitute only where `unknown` already is.** The same rule run backwards is the opposite
error: adding a language to a fence the converter left bare *boxes a block the source
deliberately left unboxed*. Console transcripts and log samples are usually the unboxed form,
and they are exactly the blocks whose content most tempts you to write ` ```bash ` or
` ```text `. If you are sweeping a tree with an editor macro, key it on the literal `unknown`
and on nothing else.

## Raw HTML does not survive the way it looks like it will

A Markdown page can contain raw HTML, and a converted page often does. It is not passed through
untouched, and the rules below cost anchors and styling on green builds. All of it is invisible
to a text comparison: an anchor contributes no visible text, and an `<a>` with no `href` is not
a link.

**Write the anchor with `id`, not `name` — what `name` does depends on the Doxia version.**
`name` is obsolete in HTML5, and the Markdown module changed how it treats it:

| written in the page | doxia-module-markdown 2.0.0 | 2.1.0 |
|---|---|---|
| `<a name="x"></a>` | *deleted* | `<a id="x"></a>` |
| `<a id="x"></a>` | `<a id="x"></a>` | `<a id="x"></a>` |
| `<a name="x" id="x"></a>` | `<a id="x"></a>` | `<a id="x"></a>` |

On 2.0.0 the attribute is dropped, which leaves an element with no attributes and no content,
and that is discarded — the anchor is gone and every link to it is broken. On 2.1.0 the same
source is rewritten to `id` and the anchor works. The version arrives with the site plugin:
`maven-site-plugin` 3.21.0 brings 2.0.0, 3.22.0 brings 2.1.0. In the Maven repositories that
means the parent — `maven-plugins:48` resolves 3.21.0 and `49` resolves 3.22.0 — so you can tell
which side a project is on without building it.

Placement makes no difference to this: it holds for an anchor on its own line, glued to a raw
`</table>` or `</pre>`, or immediately after a fenced block. Nor is it about anchors as such —
on the older module `<a id>`, `<div id>` and `<span id>` all survive; it is specifically the
`name` attribute that is dropped.

Only `id` behaves the same on both, which is why it is the form to write. On an older toolchain
that is not a matter of taste: the `name` anchors already in the page are not merely
old-fashioned, they are gone from the rendered site, and every link into them is dead.

**Never fold an anchor into a heading's text.** An anchor that survives *inside* a heading
suppresses the id Doxia would otherwise generate for that section, so you trade the section's
own anchor for your hand-written one and every link to the generated id breaks.

This is the one place where the version difference above is a trap rather than a curiosity. Fold
`<a name="x"></a>` into a heading and on 2.0.0 nothing appears to be wrong — the anchor is
deleted before it can do any harm, and the section keeps its generated id. Upgrade the site
plugin to 3.22.0 and the anchor starts surviving, so **the section silently loses its id on a
build where nothing but the plugin version changed.** A page converted today and checked today
can break on somebody else's upgrade months later.

Both attributes behave the same way once the anchor survives:

```
<a id="x"></a>Configuration      ->  <h2><a id="x"></a>Configuration</h2>
-----------------------              (no <a id="Configuration">)

<a id="x"></a>                    ->  <p><a id="x"></a></p>
                                      <section><a id="Configuration"></a>
Configuration                         <h2>Configuration</h2>
-------------
```

**An anchor on its own line is safe.** It costs an empty `<p>`, and the following heading keeps
its generated id. That is the placement to use.

**A closing fence may be followed only by whitespace.** An anchor that an APT or xdoc source
carried at the end of a verbatim block cannot stay there once the block is a fenced block. A
fence with an anchor after it closes nothing, and the remainder of the document — headings and
all — is swallowed into the code block:

````
```
some code
```<a id="x"></a>
````

That failure is at least loud in the rendered page, but the build still exits zero. Put the
anchor on its own line after the fence and accept the empty paragraph; moving it above the fence
would change where a deep link lands.

This applies to *fenced* blocks only. A block that stays raw HTML —
`<pre><code>…</code></pre>` — is not a fence, and an anchor glued to
its closing tag is fine; that is what converted xdoc pages normally carry, and it reproduces the
anchor's original position exactly. Converting such a block to a fence is what takes the
placement away, so keep the two decisions together.

**Raw `<pre>` keeps exactly the markup you write.** Doxia's own boxed block is `<pre><code>`,
and the skin styles it from that shape; a bare `<pre>` with no inner `<code>` in raw HTML stays
unboxed and unstyled. If you are hand-writing a block to match what xdoc `<source>` produced,
write the inner `<code>` yourself — or better, use a fenced block with an info string and let
the parser do it.

There is no attribute syntax for heading ids. `## Heading {#custom}` is not recognised; the
braces stay in the heading text and end up percent-encoded into the generated id.

## Things Markdown cannot express

Some APT constructs have no Markdown equivalent. None of them is a converter defect, but each
changes the rendered page, so decide deliberately:

* **Table captions.** APT puts a caption line after a table and Doxia renders `<caption>`.
  Markdown has no caption syntax; the text becomes a paragraph after the table.
* **Intraword emphasis.** The sink writes `_x_`, which CommonMark does not treat as emphasis
  when it follows a word character, so `Set<i>String</i>` renders as literal `Set_String_`.
* **Typographic substitution.** The Markdown module turns straight quotes into curly quotes
  and `...` into an ellipsis in prose. Code spans and code blocks are left alone.

Tables lose three things, and none of them shows up in a text comparison — the words are all
still there, in the right order. Only the structural pass below sees these.

* **A table with no header row.** APT does not require one; Markdown does. The converter
  therefore writes an *empty* header row above the first row, and it renders as a blank band
  across the top of the table:

  ```
  |   |   |
  |:---:|:---:|
  |Name|Default|
  ```

  Promoting the real first row to the header removes the blank band, at the cost of those cells
  becoming `<th>` rather than `<td>`. That is a content decision — make it deliberately, and say
  so in the commit, because a reviewer reading the Markdown will not see what it fixed.

* **Column alignment.** The converter writes `|:---:|` faithfully, but the Markdown module emits
  no `text-align`, so a centred APT table renders left-aligned like any other.

* **Grid borders.** APT's `+` grid form renders `<table class="bodyTable bodyTableBorder">`. A
  Markdown table is always a plain `bodyTable`, so the borders go.

**The escape hatch, if a table has to keep any of that: leave it as raw HTML.** A `<table>`
written out in the page passes through Doxia untouched — `class`, inline `style` and all — so
`bodyTable bodyTableBorder` and per-cell alignment survive exactly as written, and no `<thead>` is inserted.
It costs you a table that is no longer readable as Markdown, so it is worth it for a page whose
table is the point, and not otherwise.

## Differences that are improvements

Several APT constructs render incorrectly today and come out *better* after conversion. Do
not "fix" these back:

* `{{{#My Anchor}My Anchor}}` renders as `href="#My Anchor"` pointing at `id="My_Anchor"`, so
  the link never resolves. The converted Markdown links to `#My_Anchor`.
* `<<<${basedir}/src>>>` renders as `$<a id="basedir">basedir</a>/src` — APT reads the braces
  as an anchor definition and invents an anchor.
* `<<<\<overlay\>>>>` can end up with an anchor spliced into the middle of the code span.

## Checking the result

Comparing raw HTML is too noisy to be useful: `<b>` becomes `<strong>`, `<i>` becomes `<em>`,
attribute order changes, and `<code>` and `<a>` nest the other way round. Reduce each page to
the parts that carry meaning — its title, author and date metadata, its visible text and its
link targets — and compare that instead. A script that does so ships with the converter as
[`tools/normalize-site-page.py`](https://github.com/apache/maven-doxia-converter/blob/master/tools/normalize-site-page.py):

```
diff <(normalize-site-page.py before/index.html) \
     <(normalize-site-page.py after/index.html)
```

**Compare the `<head>`, not just the body.** An APT header carries the document's title,
authors and date, and the skin turns them into `<title>` and `<meta name="author">` /
`<meta name="date">`. A normaliser that reduces a page to its rendered body will pass a page
that has lost all of it. This is the single easiest way to ship a migration that looks
perfect and is not: the body matches on every page while the metadata quietly disappears.

Two further mistakes produce a reassuring but meaningless result:

* **Check the exit status of `mvn site`.** A failed build leaves the previous output in
  `target/site`, so a comparison against it reports no difference at all.
* **Derive the page list from the built site, not from an inventory of the APT files.** Once
  the sources are converted that inventory is empty, and an empty list compares nothing.

### Know what this comparison cannot see

`normalize-site-page.py` throws away every attribute except `href`, deliberately — that is what
makes it quiet enough to read. It catches a lost heading, lost or reordered text, a changed link
target and lost metadata. It cannot see:

* **whether a code block is boxed.** `<pre …>` becomes `[PRE]`, so
  a `<pre>` the skin styled and a bare `<pre>` compare equal. This is the axis the
  info string sits on, and a tree-wide unboxing passes it silently on every page.
* **anchor ids.** An `<a>` with no `href` is not a link and leaves no token, so a deleted
  `<a name="…">` is invisible.
* **ordered against unordered lists.** Every `<li>` becomes `[ITEM]` whatever encloses it.

None of that is a defect in the script; a normaliser that kept attributes would drown you in
noise. It means one pass is not enough. Add a second, structural pass over the same two
directories — the *sequence* of structural tags per page, and the set of anchor ids:

```
tags() { sed -e 's/></>\n</g' "$1" | grep -o '^<\(pre\|h[1-6]\|table\|thead\|th\|ol\|ul\|dl\)[^>]*>'; }
ids()  { grep -o 'id="[^"]*"' "$1" | sort -u; }

diff <(tags before/index.html) <(tags after/index.html)
diff <(ids  before/index.html) <(ids  after/index.html)
```

Keep the attributes in the tag pass — the whole point is to catch a `<pre>` or `<code>` class
appearing or disappearing. Expect a little noise from the skin and read past it; what you are
looking for is a `<pre>` that lost its classes, a structural tag that vanished, or an id that is
no longer there.

## What not to convert

* Anything under `src/test` or `src/it`. Those APT files are fixtures for Doxia's own tests.
* Anything under `src/main/resources/archetype-resources`. That is template content generated
  into a user's new project, not the project's own documentation.
