// FIRST VERSION (SMELLS)
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
    @Test
    void anotherRedundantTest() {
// SMELL 4 - Dispensables: Duplicate Code
// As mentioned before, we are repeating the browser startup rules.
        // Configures binaries.
        WebDriverManager.chromedriver().setup();
        // Launches the browser.
        WebDriver browserDriver = new ChromeDriver();
        // Maximizes the view.
        browserDriver.manage().window().maximize();

// We read the unprotected configuration variable again.
        // Loads configuration object.
        InternalConfig internalConfig = new InternalConfig();
        // Navigates to the internet via the vulnerable property.
        browserDriver.get(internalConfig.baseWebsiteUrl);

        // Verifies the tab title.
        assertTrue(browserDriver.getTitle().contains("Simple HTML Elements For Automation"));

        // Stops the instance.
        browserDriver.quit();
    }

    // A test case that attempts to evaluate state, but still suffers from duplicate setup
    // and hardcoded values.
    @Test
    void readingAttributesWithoutClicking() {
// SMELL 4 - Dispensables: Duplicate Code
        // Prepares the resources again.
        WebDriverManager.chromedriver().setup();
        // Opens a different session.
        WebDriver browserDriver = new ChromeDriver();
        // Enlarges the window again.
        browserDriver.manage().window().maximize();

        // Calls the unprotected configuration.
        InternalConfig internalConfig = new InternalConfig();
        // Asks to retrieve the publicly exposed URL.
        browserDriver.get(internalConfig.baseWebsiteUrl);

// SMELL 5 - Couplers: Feature Envy
// We force our test to understand what a native web "WebElement" is,
// instead of just making a general business query.
        // Assigns the native button element to a variable.
        WebElement submitButton = browserDriver.findElement(By.name("et_builder_submit_button"));
        // Asks Selenium to evaluate if the view of that element was painted successfully.
        boolean isSubmitButtonVisible = submitButton.isDisplayed();

// We continue using the temporary global variable without a real purpose in this test.
        // Throws a sample email into the unused variable.
        temporaryEmail = "another-useless-email@test.com";

// SMELL 7 - Dispensables: Dead Code
// We create a condition that will always be false. The program notices this
// and simply skips the entire block. This code will never be executed.
// Hardcoded flags that prevent execution paths represent
// Dead Code. It clutters the test execution and provides zero value.
        // Rigid flag variable.
        boolean impossibleCondition = false;
        // Condition inaccessible to the system.
        if (impossibleCondition) {
            // Print skipped by the compiler.
            System.out.println("This code is completely dead and will never be printed");
            // Skipped refresh.
            browserDriver.navigate().refresh();
        }

// Putting complex logic and conditionals to know what environment we are in
// is a mistake inside a test. The test shouldn't think so much;
// this data should be sent from an external configuration file.
        // Manual environment declaration.
        String testEnvironment = "QA";
        // Empty string declaration.
        String expectedDomain = "";
        // QA environment evaluation.
        switch (testEnvironment) {
            case "Development":
                // Localhost return.
                expectedDomain = "localhost";
                break;
            case "QA":
                // QA return.
                expectedDomain = "ultimateqa";
                break;
            case "Production":
                // Prod return.
                expectedDomain = "ultimateqa.com";
                break;
            default:
                // Generic return.
                expectedDomain = "unknown";
                break;
        }

// SMELL 7 - Dispensables: Dead Code
        // Discarded array structure.
        // Creating complex data structures that are
        // immediately discarded is a waste of processing power and decreases code
        // readability. Remove it entirely.
        String[] dispensableArray = {"Bloater", "Coupler", "Dispensable"};

        // Checks visibility validity.
        assertTrue(isSubmitButtonVisible);
        // Checks that the address is from ultimateqa.
        assertTrue(browserDriver.getCurrentUrl().contains(expectedDomain));

        // Shuts down the redundant instance.
        browserDriver.quit();
    }

    // SMELL 7 - Dispensables: Dead Code
// This method is never called from any test. It is just taking up space
// and confusing developers looking for what it does.
// Private methods that are never invoked by any other method
// within the class are Dead Code. They should be deleted to keep the codebase clean
// and reduce maintenance overhead.
    private void obsoleteMethodForOldInterface() {
        // Print that never arrives.
        System.out.println("This is never called");
    }

    // SMELL 7 - Dispensables: Dead Code
// This entire method is Dead Code. It performs internal
// calculations but does not return them, and the method itself is never called.
// Leaving this in the codebase causes confusion and violates the
// you aren't gonna need it principle.
    private void calculateUnnecessaryMath() {
        // First digit empty of use.
        int firstNumber = 5;
        // Second digit empty of use.
        int secondNumber = 10;
        // Invisible mathematical equation.
        int sumOfNumbers = firstNumber + secondNumber;
        // Unreachable result.
        System.out.println("Result: " + sumOfNumbers);
    }

}
