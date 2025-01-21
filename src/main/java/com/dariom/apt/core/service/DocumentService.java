package com.dariom.apt.core.service;

import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final Page page;

    public String getPage(String url) {
        try {
            page.navigate(url);
            return page.content();
        } catch (Exception ex) {
            var errorMessage = format("Failed to download page [%s].%sError: %s", url, lineSeparator(), ex.getMessage());
            throw new RuntimeException(errorMessage, ex);
        }
    }

    public String extractValue(String htmlContent, String className) {
        var startMarker = "<span class=\"" + className + "\">";
        var endMarker = "</span>";

        return extractValue(htmlContent, startMarker, endMarker);
    }

    private static String extractValue(String content, String startMarker, String endMarker) {
        int startIndex = content.indexOf(startMarker);
        if (startIndex == -1) {
            throw new IllegalArgumentException(startMarker + " not found in content");
        }

        startIndex += startMarker.length();
        int endIndex = content.indexOf(endMarker, startIndex);
        if (endIndex == -1) {
            throw new IllegalArgumentException(endMarker + " not found in content");
        }

        return content.substring(startIndex, endIndex).trim();
    }

}
