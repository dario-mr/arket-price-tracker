package com.dariom.apt.core.service;

import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final Page page;

    @Value("${scraper.use-playwright}")
    private boolean usePlaywright;

    public String getPageAttribute(String url, String attribute) {
        if (usePlaywright) {
            return getPageAttributeViaPlaywright(url, attribute);
        }

        return getPageAttributeViaJsoup(url, attribute);
    }

    private String getPageAttributeViaPlaywright(String url, String attribute) {
        String pageContent;
        try {
            page.navigate(url);
            pageContent = page.content();
        } catch (Exception ex) {
            var errorMessage = format("Failed to download page [%s].%sError: %s", url, lineSeparator(), ex.getMessage());
            throw new RuntimeException(errorMessage, ex);
        }

        return extractValue(pageContent, attribute);
    }

    private String getPageAttributeViaJsoup(String url, String attribute) {
        Document document;
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .referrer("https://www.google.com")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Connection", "keep-alive")
                    .timeout(60_000)
                    .get();
        } catch (Exception ex) {
            var errorMessage = format("Failed to download page [%s].%sError: %s", url, lineSeparator(), ex.getMessage());
            throw new RuntimeException(errorMessage, ex);
        }

        return extractValue(document, attribute);
    }

    private String extractValue(String htmlContent, String className) {
        var startMarker = "<span class=\"" + className + "\">";
        var endMarker = "</span>";

        return extractValue(htmlContent, startMarker, endMarker);
    }

    private String extractValue(String content, String startMarker, String endMarker) {
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

    private String extractValue(Document document, String className) {
        return document
                .getElementsByClass("producttile-details").getFirst()
                .getElementsByClass(className).getFirst().text();
    }

}
