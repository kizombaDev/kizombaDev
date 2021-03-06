/*
 * Marcel Swoboda
 * Copyright (C) 2017
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package org.kizombadev.markdownparser.integration;

import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.kizombadev.markdownparser.Program;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.contentOf;

public class IntegrationTest {
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testBasic() throws URISyntaxException {
        executeIntegrationTest("basic");
    }

    @Test
    public void testQuotation() throws URISyntaxException {
        executeIntegrationTest("quotation");
    }

    @Test
    public void testHeadline() throws URISyntaxException {
        executeIntegrationTest("headline");
    }

    @Test
    @Ignore
    public void testUnorderedList() throws URISyntaxException {
        executeIntegrationTest("unorderedList");
    }

    @Test
    @Ignore
    public void testParagraph() throws URISyntaxException {
        executeIntegrationTest("paragraph");
    }

    private void executeIntegrationTest(String testcase) throws URISyntaxException {
        String integrationTestPath = "/org/kizombadev/markdownparser/integrationtest";
        File markdownFile = new File(IntegrationTest.class.getResource(String.format("%s/%s.md", integrationTestPath, testcase)).toURI());
        File expectedHtmlFile = new File(IntegrationTest.class.getResource(String.format("%s/%s.html", integrationTestPath, testcase)).toURI());

        File generatedHtmlFile = Paths.get(folder.getRoot().getPath(), "output.html").toFile();

        assertThat(generatedHtmlFile).doesNotExist();
        assertThat(markdownFile).exists();
        assertThat(expectedHtmlFile).exists();

        String[] args = {"-i", markdownFile.getPath(), "-o", generatedHtmlFile.getPath()};
        Program.main(args);

        assertThat(generatedHtmlFile).exists();
        assertThat(contentOf(generatedHtmlFile)).isEqualTo(replaceNewLineChars(contentOf(expectedHtmlFile)));
    }

    @NotNull
    private String replaceNewLineChars(String content) {
        return content.replace("\n\r", "").replace("\n", "").replace(System.lineSeparator(), "");
    }
}
