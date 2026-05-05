package test;

// Imports required libraries. Notice the high number of Selenium UI-specific imports
// (By, WebElement, Select). In a clean architecture, these should not be in the test class.
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import static org.junit.jupiter.api.Assertions.assertTrue;

// SMELL 1 - Bloaters: Large Class
// This class tries to do too many things at once. It is responsible for
// setting up the browser, finding buttons on the web page,
// and also deciding if the test passed or failed. When a class does everything,
// it becomes huge and any small change in the project will force us
// to modify this file, making it very difficult to maintain.
public class UltimateQASmellyTest {
    // SMELL 2 - Object-Orientation Abusers: Temporary Fields
// We have created variables at the top of the class that are actually
// only used at very specific moments in some methods.
// This is confusing because we don't know if the variable holds useful data or not.
    private String temporaryEmail;

    // More examples of Temporary Fields.
// The serious problem here is that if a test saves data in "temporaryVehicle"
// and then fails, the next test will read that old and erroneous data,
// causing phantom errors that are very hard to track down.
    private String temporaryVehicle;
    // A boolean flag that is declared but never initialized or used, adding useless noise.
    private boolean temporaryFlag;
    // Another unused primitive variable that adds unnecessary cognitive load to the reader.
    private int retryLimit;

    // SMELL 3 - Couplers: Inappropriate Intimacy
// We define configuration data and leave it completely public.
// This is dangerous because any other class could change the URL
// in the middle of the tests without us noticing. We should always keep
// sensitive data private and access it through methods.
    public static class InternalConfig {
        // Hardcoded target URL exposed publicly, prone to accidental external modification.
        public String baseWebsiteUrl = "https://ultimateqa.com/simple-html-elements-for-automation/";
        // An arbitrary wait time parameter that is defined but never even used.
        public int implicitWaitSeconds = 10;
    }
}
