package tests;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationTest extends BaseTest {
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

    @Test
    void shortUsernameTest() {
        open("/register");
        $("[data-testid=register-login-input]").setValue("us");
        $("[data-testid=register-password-input]").setValue("password1");
        $("[data-testid=confirm-password-input]").setValue("password1");
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=register-error-message]").shouldHave(text("Login must be at least 3 characters"));
    }

    @Test
    void shortPasswordTest() {
        String randomUserName = "auto_user_42";
        open("/register");
        $("[data-testid=register-login-input]").setValue(randomUserName);
        $("[data-testid=register-password-input]").setValue("passw");
        $("[data-testid=confirm-password-input]").setValue("passw");
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=register-error-message]").shouldHave(text("Password must be at least 6 characters"));
    }

    @Test
    void emptyFormTest() {
        open("/register");
        $("[data-testid=register-submit-button]").click();
        $("[data-testid=register-error-message]").shouldHave(text("Login and password are required (minimum 3 and 6 characters)"));
    }
}
