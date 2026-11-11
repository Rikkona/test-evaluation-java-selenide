# Architecture

Generated stack: Java / Gradle / JUnit 6 / Selenide.
The project contains browser tests against the configured application; no API client, mock server or separate API test layer is generated.

## Source map
- Build/dependencies: [build.gradle](../../build.gradle).
- Test modules: [src/test/java/tests/LoginTest.java](../../src/test/java/tests/LoginTest.java).
- No shared base/fixture file: setup is local to tests or provided by the selected runner. Inspect the linked tests.
- No Page Object layer is generated. Locators/actions remain in the tests; do not invent a new layer for one scenario.
- No shared lifecycle file is generated.
- Data/environment: [test-data.md](test-data.md).
- Test commands: [../testing/run-tests.md](../testing/run-tests.md).
- Checks placement: [../testing/assertions.md](../testing/assertions.md).
- No remote-farm contract is generated for `embedded`.

## Existing scenarios
- Successful login.

Canonical test shape — verbatim from this project:

```java
    @Test
    void successfulLoginTest() {
        open("/login");
        $("[data-testid=login-input]").setValue("user1");
        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();
        $("[data-testid=welcome-message]").shouldHave(text("Welcome, user1!"));
    }
```

## Checks and synchronization
Selectors, browser operations and assertions are inline in test modules.
Reuse Selenide conditions and their retrying shouldHave/shouldBe checks.
Each check is a single terminal retrying assertion on the expected outcome — never prepend weaker precondition checks (e.g. visibility) or append state re-verification after it.
No component-object layer is generated; do not document nonexistent components.

## Configuration and CI
No CI configuration is generated.
Integration files, if present, are templates, not evidence of live TestOps/Jira/Confluence integration.
Do not import the course pack's Gradle wrappers, tags, mock environments or testE2e task: they are not configured in this project.
