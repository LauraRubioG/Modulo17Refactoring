package pages;

// Imports Selenium WebDriver tools. Since this is a Page Object, it is
// completely appropriate and expected to import UI-specific libraries here.
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

// This file implements the Page Object Model (POM).
// Its job is very specific: to act as the bridge between the tests and the web page.
// This way, we centralize all HTML selectors and browser actions in a
// single place. If the visual design of the webpage changes,
// we will only need to modify this file, and not all the tests.
public class UltimateQAPage {
    // We declare the browser as a final variable (that cannot be altered).
// This ensures that we will always work with the same browser window
// throughout the entire test run.
    private final WebDriver browserDriver;
    // The base URL of the specific page we want to test, stored safely in one place.
    private final String websiteUrl = "https://ultimateqa.com/simple-html-elements-for-automation/";

    // We save all web selectors (ids, css) as private constants.
// This prevents other classes from knowing the deep details of the HTML,
// solving the Inappropriate Intimacy smell we had before.
    private final By nameLocator = By.id("et_pb_contact_name_0");
    private final By emailLocator = By.id("et_pb_contact_email_0");
    private final By submitButtonLocator = By.name("et_builder_submit_button");
    private final By bicycleLocator = By.cssSelector("input[value='Bike']");
    private final By simpleButtonLocator = By.id("button1");
    private final By dropdownLocator = By.cssSelector("select");

    // Instead of creating a new, empty browser here, we receive it as a parameter.
// This is called Dependency Injection and it is used to ensure that the test
// and this page work perfectly together on the same live session.
    public UltimateQAPage(WebDriver browserDriver) {
        // Assigns the shared browser instance to the local class driver variable.
        this.browserDriver = browserDriver;
    }

    // We use this method so tests can politely ask us to navigate
// to the web, instead of doing it on their own.
    public void navigateToWebsite() {
        // Directly accesses the internet URL stored in the constant.
        browserDriver.get(websiteUrl);
    }

    // We group the action of filling out the form into a single easy step.
// This removes the need for tests to manually search for inputs,
// thus solving the Feature Envy code smell.
    public void fillContactForm(String studentName, String studentEmail) {
        // Locates the name input and transmits the parameter's characters.
        browserDriver.findElement(nameLocator).sendKeys(studentName);
        // Locates the email input and types the received information.
        browserDriver.findElement(emailLocator).sendKeys(studentEmail);
    }

    // Here we solve the Switch Statements problem.
// By inserting the received text directly into the CSS locator dynamically,
// this method works for any gender without the need for a conditional list.
    public void selectGender(String gender) {
        // Searches via an interactive concatenated CSS and clicks the radio button.
        browserDriver.findElement(By.cssSelector("input[value='" + gender.toLowerCase() + "']")).click();
    }

    // Just like above, we parameterize the vehicle selection to make it dynamic.
// We only tell it which car we want, and the method handles the rest without using switch.
    public void selectVehicle(String vehicle) {
        // Instantiates a Select object anonymously and chooses the value dynamically.
        new Select(browserDriver.findElement(dropdownLocator)).selectByValue(vehicle.toLowerCase());
    }

    // These are simple descriptive methods to perform clicks. External tests
// will only need to invoke the names, ignoring complex locators.
    public void clickBicycleCheckbox() {
        // Clicks exclusively on the static bicycle locator.
        browserDriver.findElement(bicycleLocator).click();
    }

    public void clickSimpleButton() {
        // Issues a click on the basic button's locator.
        browserDriver.findElement(simpleButtonLocator).click();
    }

    // Instead of returning a full web element to the test, we return a simple
// True or False boolean value. This protects our page information.
    public boolean isSubmitButtonVisible() {
        // Returns TRUE if Selenium detects that the button is graphically visible.
        return browserDriver.findElement(submitButtonLocator).isDisplayed();
    }

    // We return useful text strings so the test class can check
// if everything went as expected by the business.
    public String getCurrentWebsiteUrl() {
        // Returns a pure string extracted from the Chrome address bar.
        return browserDriver.getCurrentUrl();
    }

    public String getWebsiteTitle() {
        // Returns the text found in the header (title) of the current tab.
        return browserDriver.getTitle();
    }
}

