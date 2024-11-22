package TestCases.BTM55;

import Home.utils;
import config.BaseTest;
import Pages.Login;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Objects;

public class BMT55SessionExpiration extends BaseTest {

    private static final String BASE_URL = "https://parabank.parasoft.com/parabank";
    private static final String LOGIN_URL = BASE_URL + "/index.htm";
    private static final String UPDATE_INFO_URL = BASE_URL + "/updateprofile.htm";

    @BeforeMethod
    public void setUpTest() {
        setup();
        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
        utils util = new utils(driver);
        util.ensureUserExists("chiara", "Prm3672*", "Chiara", "Test");
    }



    @Test
    public void testSessionExpiration() {
        try {
            // Step 1: Login
            Login loginPage = new Login(driver);
            loginPage.login("chiara", "Prm3672*");

            // Step 2: Verify successful login
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.urlContains("overview.htm"));
            Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("overview"),
                    "Login failed: User is not redirected to the overview page.");

            // Step 3: Simulate 20 seconds  of inactivity
            Thread.sleep(  20 * 1000);

            // Step 4: Try to access the Update Info page
            driver.get(UPDATE_INFO_URL);


            // Step 5: Verify redirection to login page
            wait.until(ExpectedConditions.urlContains("login"));
            Assert.assertTrue(driver.getCurrentUrl().contains("login"),
                    "Session expiration failed: User is not redirected to login page after inactivity.");

        } catch (Exception e) {
            Assert.fail("Test encountered an error: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDownTest() {
        tearDown();
    }
}
