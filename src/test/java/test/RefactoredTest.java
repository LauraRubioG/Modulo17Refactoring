
package test;
// Imports necessary libraries: WebDriverManager for automated driver setup,
// JUnit for the test lifecycle (@Test, @BeforeEach), and Selenium for the browser.
// Notice we do NOT import 'WebElement' or 'By' here, respecting the Page Object Model.
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.UltimateQAPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

// This test class is now much cleaner and easier to understand.
// It is only responsible for directing the test steps and verifying the results.
// All the tedious work of interacting with the HTML has been passed to the
// UltimateQAPage class, adhering to the Single Responsibility Principle.
public class RefactoredTest {
    // We will use a single, controlled global variable for our browser.
    private WebDriver webDriverInstance;

    // The BeforeEach annotation makes this block run automatically
// before each test. Here we prepare the browser only once,
// entirely eliminating the Duplicate Code we had in the other file.
    @BeforeEach
    public void setupWebDriverBeforeEachTest() {
        // Downloads and configures the Chrome executable binaries.
        WebDriverManager.chromedriver().setup();
        // Initializes a new native Chrome browser session.
        webDriverInstance = new ChromeDriver();
        // Maximizes the open window to avoid visibility issues.
        webDriverInstance.manage().window().maximize();
    }

    // The AfterEach annotation always runs at the end of each test,
// regardless of whether it failed or not. It ensures the browser closes properly
// so our computer doesn't run out of memory.
    @AfterEach
    public void teardownWebDriverAfterEachTest() {
        // Verifies that the browser instance is still alive and not null.
        if (webDriverInstance != null) {
            // Completely closes the browser process and frees up memory.
            webDriverInstance.quit();
        }
    }

    // Marks this method as an executable test case.
    @Test
    void fillContactFormAndInteractWithWebElements() {
// We create the page and pass our ready-to-use browser to it.
        // Instantiates the page object by injecting the shared driver.
        UltimateQAPage qaPage = new UltimateQAPage(webDriverInstance);
        // Navigates in an encapsulated way to the base URL.
        qaPage.navigateToWebsite();

// As you can see, this test reads very simply, almost like plain English.
// We ask the page to do business actions (like filling out data),
// completely forgetting about HTML IDs or classes.
        // Completes the form by sending the name and email address.
        qaPage.fillContactForm("Student", "test@clean-code.com");
        // Checks the female gender option in the radio buttons.
        qaPage.selectGender("female");
        // Opens the dropdown menu and chooses the Audi vehicle.
        qaPage.selectVehicle("audi");
        // Performs a simulated click on the bicycle checkbox.
        qaPage.clickBicycleCheckbox();
        // Triggers the click action on the interface's simple button.
        qaPage.clickSimpleButton();

// We ask the page what its current URL is to check if we passed the test.
        // Checks that the active URL string contains the correct path.
        assertTrue(qaPage.getCurrentWebsiteUrl().contains("simple-html-elements-for-automation"));
    }

    // Marks a completely independent test case to verify the page title.
    @Test
    void verifyWebsitePageTitleIsCorrect() {
// We repeat the preparation in a totally independent and fresh test.
        // Creates the representative model of the web page.
        UltimateQAPage qaPage = new UltimateQAPage(webDriverInstance);
        // Orders the loading of the domain on the internet.
        qaPage.navigateToWebsite();

// We verify the tab title in a simple way.
        // Verifies that the tab title text matches the expected string.
        assertTrue(qaPage.getWebsiteTitle().contains("Simple HTML Elements For Automation"));
    }

    // Marks another separate test case to ensure UI elements are visible.
    @Test
    void verifySubmitContactFormButtonIsVisibleAndWebsiteDomainIsCorrect() {
// We use our bridge class again, keeping the code clean.
        // Instantiates the page to begin the status query.
        UltimateQAPage qaPage = new UltimateQAPage(webDriverInstance);
        // Invokes access to the page.
        qaPage.navigateToWebsite();

// We make two assertions or checks validating the interface and navigation.
        // Validates that the graphical button component is visible.
        assertTrue(qaPage.isSubmitButtonVisible());
        // Corroborates that the browser address contains the domain name.
        assertTrue(qaPage.getCurrentWebsiteUrl().contains("ultimateqa"));
    }

}
