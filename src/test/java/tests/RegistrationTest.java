package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://autotests.ai/stack/backend-java-spring/frontend-typescript-react";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = true;
        Configuration.timeout = 5_000;
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }

    @Test
    void successfulRegistrationTest() {
        String username = "qa" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String password = UUID.randomUUID().toString();

        open("/register");
        $("[data-testid=register-login-input]").setValue(username);
        $("[data-testid=register-password-input]").setValue(password);
        $("[data-testid=confirm-password-input]").setValue(password);
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=welcome-message]").shouldHave(text("Welcome, " + username + "!"));
    }
}
