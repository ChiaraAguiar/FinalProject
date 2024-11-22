package Home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class utils {
    private final WebDriver driver;

    // Constructor
    public utils(WebDriver driver) {
        this.driver = driver;
    }

    // Register a new user
    public void registerNewUser(String username, String password, String firstName, String lastName) {
        driver.get("https://parabank.parasoft.com/parabank/register.htm");

        // Fill out the registration form
        driver.findElement(By.id("customer.firstName")).sendKeys(firstName);
        driver.findElement(By.id("customer.lastName")).sendKeys(lastName);
        driver.findElement(By.id("customer.address.street")).sendKeys("123 Main St");
        driver.findElement(By.id("customer.address.city")).sendKeys("Test City");
        driver.findElement(By.id("customer.address.state")).sendKeys("Test State");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("12345");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("1234567890");
        driver.findElement(By.id("customer.ssn")).sendKeys("123-45-6789");
        driver.findElement(By.id("customer.username")).sendKeys(username);
        driver.findElement(By.id("customer.password")).sendKeys(password);
        driver.findElement(By.id("repeatedPassword")).sendKeys(password);

        // Submit the form
        driver.findElement(By.cssSelector("input[value='Register']")).click();
    }

    public void ensureUserExists(String username, String password, String firstName, String lastName) {
        try {

            driver.get("https://parabank.parasoft.com/parabank/index.htm");
            driver.findElement(By.name("username")).sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.cssSelector("input[value='Log In']")).click();


            if (!driver.getCurrentUrl().contains("overview.htm")) {
                throw new Exception("User invalid or not registered");
            }
        } catch (Exception e) {

            System.out.println("user not found, please register...");
            registerNewUser(username, password, firstName, lastName);
        }
    }

}
