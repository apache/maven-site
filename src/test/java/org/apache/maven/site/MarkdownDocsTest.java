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
package org.apache.maven.site;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MarkdownDocsTest {
    private static final Pattern PARENTHESIZED_DESTINATION = Pattern.compile("^\\([^)\\s]+\\)");

    private static final Parser MARKDOWN_PARSER = Parser.builder(
                    new MutableDataSet().set(Parser.EXTENSIONS, List.of(WikiLinkExtension.create())))
            .build();

    @Test
    void markdownDocsHaveWellFormedLinks() throws IOException {
        Path contentDirectory = Paths.get("content/markdown");
        List<String> violations = new ArrayList<>();

        try (Stream<Path> files = Files.walk(contentDirectory)) {
            List<Path> markdownFiles =
                    files.filter(MarkdownDocsTest::isMarkdownSource).sorted().toList();
            for (Path file : markdownFiles) {
                collectViolations(contentDirectory, file, violations);
            }
        }

        assertTrue(
                violations.isEmpty(),
                () -> "Markdown link labels must escape square brackets. "
                        + "Otherwise the generated link target is the label instead of the URL. "
                        + "For example: [\\[MNG-1\\]](https://issues.apache.org/jira/browse/MNG-1).\n"
                        + String.join("\n", violations));
    }

    private static boolean isMarkdownSource(Path path) {
        String name = path.getFileName().toString();
        return name.endsWith(".md") || name.endsWith(".md.vm");
    }

    private static void collectViolations(Path contentDirectory, Path file, List<String> violations)
            throws IOException {
        String markdown = Files.readString(file, StandardCharsets.UTF_8);
        Node document = MARKDOWN_PARSER.parse(markdown);

        new NodeVisitor(new VisitHandler<>(WikiLink.class, link -> {
                    int end = link.getEndOffset();
                    Matcher destination = PARENTHESIZED_DESTINATION.matcher(markdown.substring(end));
                    if (destination.find()) {
                        violations.add(String.format(
                                "  %s:%d: %s",
                                contentDirectory.relativize(file),
                                lineNumber(markdown, link.getStartOffset()),
                                markdown.substring(link.getStartOffset(), end + destination.end())));
                    }
                }))
                .visit(document);
    }

    private static int lineNumber(String text, int offset) {
        int line = 1;
        for (int i = 0; i < offset; i++) {
            if (text.charAt(i) == '\n') {
                line++;
            }
        }
        return line;
    }
}
