package com.pacifique.security.review.integration;

import com.pacifique.security.review.config.ConfigDatabase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("TODO: NOT COMPLETED, IT'S FAILING")
public class HomeBrowserTest extends ConfigDatabase {
    private static HtmlUnitDriver browser;
    @LocalServerPort
    private int port;

    @BeforeAll
    static void setBrowser(){
        browser = new HtmlUnitDriver();
        browser.manage().timeouts().implicitlyWait(Duration.of(10, ChronoUnit.SECONDS));
    }
    @AfterAll
    static void close() {
        browser.quit();
    }

    @Test
    @DisplayName("Testing Browser Integration")
    void homePageBrowserTest(){
        browser.get("http://localhost:"+port);
        List<WebElement> techTags = browser.findElements(By.className("tech-tags"));
        assertThat(techTags).hasSize(5);
    }
}
