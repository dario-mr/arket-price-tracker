package com.dariom.apt.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaywrightConfig {

    private Playwright playwright;
    private Browser browser;

    @Bean
    public Page page() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));

        var context = browser.newContext();
        var page = context.newPage();
        page.setDefaultNavigationTimeout(120_000); // 2 minutes timeout

        return page;
    }

    @PreDestroy
    public void closeResources() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
