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

    // Tells JUnit that this method is a test case.
    @Test
    // Throwing 'InterruptedException' here is a byproduct of using the rigid Thread.sleep(),
    // which is a bad practice.
    void fillFormAndInteractWithElements() throws InterruptedException {
// SMELL 4 - Dispensables: Duplicate Code y Change Preventers: Shotgun Surgery
// These three lines prepare the browser, and we are copying and pasting them
// at the beginning of each test. If tomorrow we decide to use Firefox instead
// of Chrome, we will have to search through the entire project file by file
// to change it. It is repetitive work that should be centralized.
        // Downloads the Chrome driver.
        WebDriverManager.chromedriver().setup();
        // Initializes the Chrome browser.
        WebDriver browserDriver = new ChromeDriver();
        // Maximizes the open window.
        browserDriver.manage().window().maximize();

// Inappropriate Intimacy is evident here: we read the variable directly from the class.
        // Instantiates the exposed configuration.
        InternalConfig internalConfig = new InternalConfig();
        // Navigates by taking the property publicly.
        browserDriver.get(internalConfig.baseWebsiteUrl);

// SMELL 5 - Couplers: Feature Envy
// The test should focus on validating business logic (like "submit payment"),
// but here it is obsessed with knowing the names and IDs of the HTML code.
// By relying so heavily on the HTML, if the web developer changes a simple
// aesthetic identifier, our test will fail completely.
        // Locates the element by ID and types "Student".
        browserDriver.findElement(By.id("et_pb_contact_name_0")).sendKeys("Student");
        // Modifies the temporary global variable.
        temporaryEmail = "test@clean-code.com";
        // Sends the email using that global variable.
        browserDriver.findElement(By.id("et_pb_contact_email_0")).sendKeys(temporaryEmail);
        // Clicks the submit button located by name.
        browserDriver.findElement(By.name("et_builder_submit_button")).click();

// Forcing the program to stop for exactly 2 seconds is a bad practice.
// If the page loads in half a second, we still keep waiting,
// making our tests very slow in the long run.
        // Forcibly freezes the test execution.
        Thread.sleep(2000);

// SMELL 6 - Object-Orientation Abusers: Switch Statements
// Using "switch" statements to decide which page element to touch
// makes the code very rigid. If the page adds a new gender in the future,
// the test won't recognize it and we would have to manually modify
// this block by adding another "case".
        // Manually assigns a gender.
        String genderOption = "female";
        // Opens the decision tree.
        switch (genderOption) {
            case "male":
                // Clicks the male option.
                browserDriver.findElement(By.cssSelector("input[value='male']")).click();
                break;
            case "female":
                // Clicks the female option.
                browserDriver.findElement(By.cssSelector("input[value='female']")).click();
                break;
            case "other":
                // Clicks the alternative option.
                browserDriver.findElement(By.cssSelector("input[value='other']")).click();
                break;
        }

// SMELL 6 - Object-Orientation Abusers: Switch Statements
// We make the same mistake with rigid options, and to make matters worse,
// we repeat the code to create the "Select" option multiple times.
        // Applies a value to the global car variable.
        temporaryVehicle = "audi";
        // Repeats the selection structure.
        switch (temporaryVehicle) {
            case "volvo":
                // Selects Volvo.
                new Select(browserDriver.findElement(By.cssSelector("select"))).selectByValue("volvo");
                break;
            case "saab":
                // Selects Saab.
                new Select(browserDriver.findElement(By.cssSelector("select"))).selectByValue("saab");
                break;
            case "opel":
                // Selects Opel.
                new Select(browserDriver.findElement(By.cssSelector("select"))).selectByValue("opel");
                break;
            case "audi":
                // Selects Audi.
                new Select(browserDriver.findElement(By.cssSelector("select"))).selectByValue("audi");
                break;
        }

        // Extracts the bicycle element into a variable.
        WebElement bicycleCheckbox = browserDriver.findElement(By.cssSelector("input[value='Bike']"));
        // Interacts by clicking the element.
        bicycleCheckbox.click();

// SMELL 7 - Dispensables: Dead Code
// We declare variables, populate them with data, but then never use them.
// This confuses people reading the code because they will try to understand
// what they are for, when in reality it is just garbage code that was forgotten.
        // Empty string lost in memory.
        // This is a classic example of Dead Code. Variables that
        // are declared but never used add unnecessary noise. Developers might waste time
        // figuring out if this locator is needed anywhere. It should be removed.
        String unusedLocator = "id-that-does-not-exist";
        // Useless integer that is never altered.
        // This counter serves no purpose and just takes up memory.
        // Variables like this violate clean code principles and should be cleaned up.
        int unusedCounter = 0;

// SMELL 8 - Change Preventers: Divergent Change
// Interacting directly with the page causes this test class to change
// for reasons that don't belong to it, like a simple web redesign.
        // Gets common button by ID.
        WebElement simpleButton = browserDriver.findElement(By.id("button1"));
        // Presses that button.
        simpleButton.click();

// We check if the web address we reached is correct.
        // Evaluates if the URL contains the correct block of text.
        assertTrue(browserDriver.getCurrentUrl().contains("simple-html-elements-for-automation"));

// We close the browser manually, a block we will have to duplicate.
        // Closes the browser execution.
        browserDriver.quit();
    }
}
