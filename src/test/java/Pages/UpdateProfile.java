package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UpdateProfile {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By firstNameField = By.id("firstName");
    private final By saveButton = By.cssSelector("input[value='Update Profile']");
    private final By confirmationMessage = By.id("updateProfileResult");

    // Constructor
    public UpdateProfile(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    // Actions
    public void updateFirstName(String newFirstName) {
        // Wait until the first name field is visible, then clear and update it
        WebElement firstNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        firstNameElement.clear();
        firstNameElement.sendKeys(newFirstName);

        // Click save button
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveBtn.click();
    }

    public boolean isFirstNameUpdated(String expectedFirstName) {
        // Reload the page or go to a specific section if needed to confirm the updated name
        WebElement firstNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        String actualFirstName = firstNameElement.getAttribute("value");
        return actualFirstName.equals(expectedFirstName);
    }

    public String getConfirmationMessage() {
        // Wait for the confirmation message to appear and then return its text
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage));
        return messageElement.getText();
    }
}
