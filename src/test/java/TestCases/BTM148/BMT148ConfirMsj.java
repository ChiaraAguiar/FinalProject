package TestCases.BTM148;

import Home.utils;
import config.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.Login;
import Pages.UpdateProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BMT148ConfirMsj extends BaseTest {

    private static final String BASE_URL = "https://parabank.parasoft.com/parabank";
    private static final String LOGIN_URL = BASE_URL + "/index.htm";
    private static final String UPDATE_INFO_URL = BASE_URL + "/updateprofile.htm";
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        setup();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.get(LOGIN_URL);
        utils util = new utils(driver);
        util.ensureUserExists("chiara", "Prm3672*", "Chiara", "Test");
    }

    @Test
    public void verifyUpdateConfirmationAndData() {
        // Step 1: Log in
        Login loginPage = new Login(driver);
        loginPage.login("chiara", "Prm3672*");

        // Wait for the login page to confirm authentication
        wait.until(ExpectedConditions.urlContains("/overview.htm"));

        // Step 2: Update first name
        driver.get(UPDATE_INFO_URL);
        UpdateProfile updateProfile = new UpdateProfile(driver);
        updateProfile.updateFirstName("NewFirstName");


        // Step 3: Validate confirmation message
        String actualMessage = updateProfile.getConfirmationMessage();
        Assert.assertTrue(actualMessage.contains("updated"),
                "The confirmation message is incorrect.");

        // Step 4: Validate that the first name was actually updated
        Assert.assertTrue(updateProfile.isFirstNameUpdated("NewFirstName"),
                "The first name was not updated correctly.");
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}

