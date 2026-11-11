package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
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
    void successfulLoginTest() {
        open("/login");
        $("[data-testid=login-input]").setValue("user1");
        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();
        $("[data-testid=welcome-message]").shouldHave(text("Welcome, user1!"));
    }

    @Test
    void wrongPasswordTest() {
        open("/login");
        $("[data-testid=login-input]").setValue("user1");
        $("[data-testid=password-input]").setValue("wrong-password");
        $("[data-testid=submit-button]").click();
        $("[data-testid=error-message]").shouldHave(text("Wrong login or password"));
    }

    @Test
    void wrongUsernameTest() {
        open("/login");
        $("[data-testid=login-input]").setValue("unknownuser7f3d9a");
        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();
        $("[data-testid=error-message]").shouldHave(text("Wrong login or password"));
    }

    @Test
    void missingPasswordTest() {
        open("/login");
        $("[data-testid=login-input]").setValue("user1");
        $("[data-testid=submit-button]").click();
        $("[data-testid=error-message]").shouldHave(text("Password is required (minimum 6 characters)"));
    }

    @Test
    void missingUsernameTest() {
        open("/login");
        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();
        $("[data-testid=error-message]").shouldHave(text("Login is required (minimum 3 characters)"));
    }

    @Test
    void missingCredentialsTest() {
        open("/login");
        $("[data-testid=submit-button]").click();
        $("[data-testid=error-message]").shouldHave(text("Login and password are required (minimum 3 and 6 characters)"));
    }

    @Test
    void shortUsernameTest() {
        open("/login");
        $("[data-testid=login-input]").setValue("us");
        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();
        $("[data-testid=error-message]").shouldHave(text("Login must be at least 3 characters"));
    }
}
