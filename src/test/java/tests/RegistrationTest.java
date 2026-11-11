package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        String randomUserName = "auto_user_42";
        open("/register");
        $("[data-testid=register-login-input]").setValue(randomUserName);
        $("[data-testid=register-password-input]").setValue("password1");
        $("[data-testid=confirm-password-input]").setValue("password1");
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=welcome-message]").shouldHave(text("Welcome, " + randomUserName + "!"));
    }

    @Test
    void duplicateRegistrationTest() {
        String randomUserName = "auto_user_42";
        open("/register");
        $("[data-testid=register-login-input]").setValue(randomUserName);
        $("[data-testid=register-password-input]").setValue("password1");
        $("[data-testid=confirm-password-input]").setValue("password1");
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=welcome-message]").shouldHave(text("Welcome, " + randomUserName + "!"));
        open("/register");
        $("[data-testid=register-login-input]").setValue(randomUserName);
        $("[data-testid=register-password-input]").setValue("password1");
        $("[data-testid=confirm-password-input]").setValue("password1");
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=register-error-message]").shouldHave(text("Username already taken"));
    }

    @Test
    void passwordMismatchTest() {
        String randomUserName = "auto_user_42";
        open("/register");
        $("[data-testid=register-login-input]").setValue(randomUserName);
        $("[data-testid=register-password-input]").setValue("password1");
        $("[data-testid=confirm-password-input]").setValue("password2");
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=register-error-message]").shouldHave(text("Passwords do not match"));
    }

    @Test
    void missingPasswordTest() {
        String randomUserName = "auto_user_42";
        open("/register");
        $("[data-testid=register-login-input]").setValue(randomUserName);
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=register-error-message]").shouldHave(text("Password is required (minimum 6 characters)"));
    }

    @Test
    void missingUsernameTest() {
        open("/register");
        $("[data-testid=register-password-input]").setValue("password1");
        $("[data-testid=confirm-password-input]").setValue("password1");
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=register-error-message]").shouldHave(text("Login is required (minimum 3 characters)"));
    }
}
