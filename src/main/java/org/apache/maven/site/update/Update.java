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
package org.apache.maven.site.update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Updater for /plugins/index.apt and /shared/index.apt release numbers against effective site content.
 */
public class Update {
    public static void main(String[] args) throws IOException {
        for (String dir : args) {
            new Update().doUpdate(Paths.get("content/apt/" + dir + "/index.apt"));
        }
    }

    private void doUpdate(Path index) throws IOException {
        System.out.println("updating " + index);
        List<String> lines = Files.readAllLines(index);

        lines = lines.stream()
                .map(line -> line.startsWith("| {{{/") ? update(line) : line)
                .collect(Collectors.toList());

        Files.write(index, lines);
    }

    private String update(String line) {
        String[] cols = line.split("\\|");

        String description = cols[1];
        String url = "https://maven.apache.org/" + description.substring(5, description.indexOf('}'));
        String component = description.substring(description.indexOf("<<<") + 3, description.indexOf(">>>"));

        int column = 2;
        String versionCol = cols[2];
        String version = versionCol.trim();
        if ("".equals(version)) {
            return line;
        } else if (!Character.isDigit(version.charAt(0))) {
            // plugin index has an additional column (build or report)
            column++;
            versionCol = cols[column];
            version = versionCol.trim();
        }

        System.out.print("  " + component + "  " + version + " => checking against " + url + "\33[2K\r");
        return line;
    }
}
