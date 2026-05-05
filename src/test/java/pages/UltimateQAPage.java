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

}
