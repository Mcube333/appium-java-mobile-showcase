# Appium + Java Mobile Automation Showcase

Android automation for Sauce Labs **My Demo App** (React Native), built with
**Java 17, Appium 2 (UiAutomator2), TestNG, AssertJ, Allure and GitHub Actions**.
The same suite runs on a **local emulator** or **LambdaTest real devices** with one flag.

This mirrors work I did migrating a production mobile suite from Python/Appium on LambdaTest to Java.
The original is proprietary, so this is a clean-room rebuild on a public demo app.

## What it covers

| Area | Tests |
|---|---|
| Login | standard user login + logout, data-driven negative cases (empty username, empty password, wrong credentials, locked user) |
| Catalog | sort by name A-Z / Z-A, sort by price low-high / high-low, product details match catalog card |
| Cart | badge updates on add, quantity carried into badge / item count / total price, remove item shows empty state |
| Checkout | guest redirected to login, address + payment + review, order complete, cart cleared afterwards |

## Design decisions

- **One codebase, two targets.** `DriverFactory` builds `UiAutomator2Options` for a local emulator or adds
  `lt:options` for LambdaTest. Switching is just `-Dsuite=lambdatest`.
- **Thread-local drivers.** Each test thread owns its `AndroidDriver`, so the LambdaTest suite runs in
  parallel on several real devices without sessions leaking into each other.
- **Clean state per test.** A new session with `noReset=false` for every test means no test depends on another.
- **Accessibility-id locators.** The most stable locator strategy on mobile; no XPath.
- **Explicit waits only.** `WebDriverWait` + `ExpectedConditions`; no implicit waits, no `Thread.sleep`.
- **Gesture helpers.** `UiScrollable` scrolling to elements that are below the fold.
- **Failure evidence.** Device screenshot + full UI hierarchy XML attached to Allure on failure;
  LambdaTest sessions are marked passed/failed and record video and device logs.
- **Secrets never in files.** LambdaTest credentials come only from `LT_USERNAME` / `LT_ACCESS_KEY`
  environment variables (GitHub secrets in CI). A PII check fails the build on real-looking personal data.
- **Retry + data-driven tests.** Retry analyzer on every test; DataProvider for negative login cases.

## Project structure

```
src/main/java/com/mudassir/mobile
  config/   ConfigManager           env files + -D overrides + env-var secrets
  driver/   DriverFactory           ThreadLocal AndroidDriver, local or LambdaTest
  screens/  11 screen objects       BaseScreen helpers, HeaderBar component
  data/     demo users, address, card, price parsing
src/test/java/com/mudassir/mobile/tests
  base/       BaseMobileTest        session per test, LambdaTest status
  listeners/  retry + failure evidence
  LoginTest, CatalogTest, CartTest, CheckoutTest
src/test/resources/suites   local.xml, smoke.xml, lambdatest.xml
```

## Running locally

Prerequisites: JDK 17+, Maven, Android SDK + an emulator, Node.js.

```bash
npm install -g appium
appium driver install uiautomator2
appium                                    # start the server on :4723

# Download the Android APK from https://github.com/saucelabs/my-demo-app-rn/releases
# and save it as apps/my-demo-app.apk

mvn test                                  # full suite on emulator-5554
mvn test -Dsuite=smoke                    # smoke only
mvn test -Ddevice.name=Pixel_7 -Dplatform.version=13
mvn allure:serve
```

## Running on LambdaTest

```bash
export LT_USERNAME=your_user
export LT_ACCESS_KEY=your_key
# Upload apps/my-demo-app.apk in the LambdaTest dashboard (or their upload API) to get lt://APP_ID
mvn test -Dsuite=lambdatest -Dlt.app=lt://APP_ID
```

In CI, add `LT_USERNAME` and `LT_ACCESS_KEY` as repository secrets and run the workflow manually with the app id.

## Locator note

Locators are the app's accessibility ids (for example `Username input field`, `store item text`,
`Add To Cart button`). If a new app release renames one, update the constant in the relevant screen class.
Appium Inspector is the quickest way to confirm them.

## Author

Mohammed Mudassir Maniyar, SDET
[LinkedIn](https://linkedin.com/in/mohammed-mudassir03)
