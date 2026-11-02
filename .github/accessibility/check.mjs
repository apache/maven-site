/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Checks every page of the generated site with axe-core against WCAG 2.2 level AA and fails on any violation.
//
//   node check.mjs SITE_DIR
//
// CHROME may name a Chrome or Chromium binary; by default the Chrome installed on the runner is used.
import fs from 'node:fs';
import http from 'node:http';
import path from 'node:path';
import { createRequire } from 'node:module';
import { chromium } from 'playwright-core';

const require = createRequire(import.meta.url);
const axeSource = fs.readFileSync(require.resolve('axe-core/axe.min.js'), 'utf8');

// Faults of the site skin, fixed in Fluido 2.2.0 (link colours, links in lists): remove these once content/site.xml
// uses it.
const SKIN_RULES = ['color-contrast', 'link-in-text-block', 'target-size'];
// The banner comes from the parent POM's site descriptor and the skin.
const EXCLUDE = ['#banner'];
// Faults of the reports that generate a page: remove an entry once the site uses a release with the fix.
const KNOWN = {
  // avatars without alt; maven-project-info-reports-plugin gives them an empty alt after 3.9.0
  'team.html': ['image-alt'],
};

const root = path.resolve(process.argv[2] || 'target/site');
const types = { '.html': 'text/html', '.css': 'text/css', '.js': 'text/javascript', '.svg': 'image/svg+xml' };

const pages = [];
(function walk(dir) {
  for (const e of fs.readdirSync(dir, { withFileTypes: true })) {
    const p = path.join(dir, e.name);
    if (e.isDirectory()) walk(p);
    else if (e.name.endsWith('.html')) pages.push(path.relative(root, p).split(path.sep).join('/'));
  }
})(root);
pages.sort();

const server = http.createServer((req, res) => {
  const file = path.join(root, decodeURIComponent(new URL(req.url, 'http://x').pathname));
  if (!file.startsWith(root) || !fs.existsSync(file) || fs.statSync(file).isDirectory()) {
    res.statusCode = 404;
    return res.end();
  }
  res.setHeader('content-type', types[path.extname(file)] || 'application/octet-stream');
  res.end(fs.readFileSync(file));
});
await new Promise((resolve) => server.listen(0, '127.0.0.1', resolve));
const base = `http://127.0.0.1:${server.address().port}/`;

const browser = await chromium.launch(process.env.CHROME ? { executablePath: process.env.CHROME } : { channel: 'chrome' });
const context = await browser.newContext({ viewport: { width: 1280, height: 900 } });
// only the site itself: no analytics, fonts or other third-party requests
await context.route('**/*', (route) => (route.request().url().startsWith(base) ? route.continue() : route.abort()));

let failures = 0;
for (const pg of pages) {
  const page = await context.newPage();
  await page.goto(base + pg, { waitUntil: 'load' });
  await page.addScriptTag({ content: axeSource });
  const violations = await page.evaluate(
    async ([exclude, disabled]) => {
      const rules = Object.fromEntries(disabled.map((id) => [id, { enabled: false }]));
      const result = await window.axe.run(
        { exclude: exclude.map((s) => [s]) },
        { runOnly: ['wcag2a', 'wcag2aa', 'wcag21a', 'wcag21aa', 'wcag22aa'], rules },
      );
      return result.violations.map((v) => ({ id: v.id, help: v.help, nodes: v.nodes.map((n) => n.target.join(' ')) }));
    },
    [EXCLUDE, SKIN_RULES],
  );
  for (const v of violations.filter((v) => !(KNOWN[pg] || []).includes(v.id))) {
    failures += v.nodes.length;
    console.log(`${pg}: ${v.id} (${v.help})`);
    for (const n of v.nodes.slice(0, 5)) console.log(`    ${n}`);
    if (v.nodes.length > 5) console.log(`    ... and ${v.nodes.length - 5} more`);
  }
  await page.close();
}
await browser.close();
server.close();

console.log(`${pages.length} pages checked, ${failures} accessibility violations`);
process.exit(failures ? 1 : 0);
