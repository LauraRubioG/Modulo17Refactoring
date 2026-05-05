# Final Project: "The Clean Code & Automation Cycle" - Module 17 **ENTORNOS DE DESAROLLO**

> **💖 Authors:** Marta González González and Laura Rubio Gallardo

Welcome to this repository. In this project, we address a fundamental practice in professional software development: **code cleaning or *Refactoring***, applying clean design principles.

## 🌸 Introduction to the Activity

For this practical assignment, we have focused on software testing automation. The objective has been to validate the elements of a real webpage specifically designed to practice UI automation: [UltimateQA - Simple HTML Elements For Automation](https://ultimateqa.com/simple-html-elements-for-automation/).

The main goal of the activity was to understand and apply the refactoring process. To achieve this, we initially intentionally developed a functional test code that contained multiple poor design practices, known in the industry as **Code Smells**. A *Code Smell* is not a syntax error or a *bug* that causes the program to fail, but rather an indicator of poor design that hinders the readability, maintenance, and scalability of the system.

Our mission consisted of structuring these tests with *smells* to subsequently **refactor** them. Refactoring means restructuring the internal code without changing its external behavior, applying the *Page Object Model (POM)* architectural pattern. Below, we detail each of the implemented *smell* categories and how we solved them.

---

## 💅 Analysis of Code Smells and Implemented Solutions

### 1. 🐋 Bloaters: Long Method or Large Class

**Explanation of the smell:** A "Bloater" is a code segment (a large method or an entire class) that has grown so much in size and responsibilities that it becomes difficult to manage. This occurs when a class handles too many responsibilities, increasing technical debt over time. When a single class assumes all the workflow responsibilities, the system becomes fragile. This directly violates the Single Responsibility Principle (SRP) and the fundamental principles of object-oriented design. It also makes the code more difficult for new team members to understand, increases the risk of merge conflicts, and makes unit testing complex because the code is tightly coupled.

**Example in our code:** Initially, we had a single class called `UltimateQASmellyTest`. This class concentrated all the testing logic in one place: it handled the configuration of the Chrome browser driver, directly located HTML elements using raw selectors (like `By.id`), managed the UI interactions, and evaluated the business logic assertions. This meant the test logic was heavily coupled with the browser's graphical interface.

**The refactored solution:** We divided the responsibilities by applying the **Single Responsibility Principle (SRP)** and the **Page Object Model (POM)** architectural pattern. We created a new class called `UltimateQAPage`, which is exclusively in charge of communicating with the web browser and encapsulating all HTML locators. The `RefactoredTest` file acts as an orchestrator, responsible only for defining the test steps and verifying the expected business results.

### 2. 🎭 Object-Orientation Abusers: Switch Statements and Temporary Fields
This category groups the incorrect use of object-oriented programming principles, resulting in rigid code.

*   **Temporary Fields:**

    *   **Explanation of the smell:** Keeping global variables for momentary use confuses code readability and breaks the statelessness of methods. This often happens when developers try to avoid passing multiple parameters between functions. Storing temporary data at the class level can cause a failed test to leave behind stored data (a corrupted state), causing the subsequent test to read that bad data. This generates unexpected workflow errors that are very difficult to track and makes debugging complicated.

    *   **Example in our code:** We declared class-level variables such as `temporaryEmail` or `temporaryVehicle` that were only populated and used for a brief moment within a specific method.

    *   **The refactored solution:** We removed these state-holding variables completely. Now, we maintain stateless, pure methods by sending the data directly as scoped arguments through our page methods, such as invoking `qaPage.fillContactForm("Student", "test@clean-code.com")`.

*   **Switch Statements:**

    *   **Explanation of the smell:** The use of large `switch` conditional structures makes the code very rigid to future changes or when new features are added. This directly violates the Open/Closed Principle (software entities should be open for extension but closed for modification). Every time a new condition is introduced, developers are forced to modify the core logic, increasing the risk of breaking existing functionality.

    *   **Example in our code:** We used a hardcoded `switch` block based on the `genderOption` variable to decide which radio button to select on the web (`case "male":`, `case "female":`, `case "other":`). If the webpage added a new category, our test framework would fail because it would not recognize the new input.

    *   **The refactored solution:** We removed the conditional structure by replacing it with a dynamic approach based on parameterization. We receive the desired option as a parameter and inject it directly into the dynamic CSS selector using `By.cssSelector("input[value='" + gender.toLowerCase() + "']")`. The method is now universal.

### 3. 🚧 Change Preventers: Divergent Change or Shotgun Surgery

**Explanation of the smell:** This occurs when a minor change in the system requirements forces modifications in multiple files or scattered methods throughout the project (known as "shotgun surgery"). It indicates high coupling and low cohesion. It increases maintenance time and the likelihood of human error. If a developer misses an instance during an update, it leads to bugs that are hard to catch during code reviews.

**Example in our code:** In every test method of the `UltimateQASmellyTest` class, we repeated three identical lines of code (`WebDriverManager.chromedriver().setup(); ...`) to initialize the Chrome browser. If a switch from Chrome to Firefox were needed, developers would have to find and modify every test method individually.

**The refactored solution:** We centralized the browser initialization and configuration lifecycle. We used the JUnit framework's `@BeforeEach` and `@AfterEach` annotations to encapsulate this repetitive setup and teardown code within the `setupWebDriverBeforeEachTest()` and `teardownWebDriverAfterEachTest()` methods. This way, the browser environment is managed from a single place.

### 4. 🗑️ Dispensables: Duplicate Code and Dead Code

**Explanation of the smell:** These are useless or redundant code fragments that add no functional value to the system's execution and hinder its maintenance. Inactive (dead) code acts as unnecessary technical debt and forces developers to analyze logic that will never be executed. Furthermore, duplicate code violates the DRY (Don't Repeat Yourself) principle, meaning any future bug fix would have to be manually applied in multiple places.

**Example in our code:** We had multiple class-level variables declared that were never initialized or used (`temporaryFlag`, `retryLimit`). We also had duplicated code in the browser setup. Additionally, we used hardcoded time pauses like `Thread.sleep(2000)`, which inflated the test execution time.

**The refactored solution:** We performed a thorough cleanup of the file, removing all dead code. We removed all unused variables and deleted the `Thread.sleep()` instructions, which were slowing down the automated tests. The codebase is now more efficient and only contains necessary logic.

### 5. 🔗 Couplers: Feature Envy and Inappropriate Intimacy
This category points out system components that are too tightly coupled or that know excessively internal details of other dependencies, losing their independence.

*   **Inappropriate Intimacy:**

    *   **Explanation of the smell:** Exposing critical configuration allows other parts of the system to mistakenly overwrite data during execution, breaking encapsulation rules. Classes should communicate through strictly defined interfaces or methods rather than directly accessing each other's internal fields. Proper encapsulation ensures that internal changes in one class do not break the rest of the system.

    *   **Example in our code:** The internal configuration class `InternalConfig` left critical variables publicly exposed, such as `public String baseWebsiteUrl`. Any external class could accidentally change the URL mid-test, causing the tests to fail.

    *   **The refactored solution:** In the `UltimateQAPage` class, we declared all sensitive properties and DOM selectors with the `private final` access modifiers. By doing this, we transformed them into secure constants. No external class can modify these values.

*   **Feature Envy:**

    *   **Explanation of the smell:** A test orchestrator should not know how the HTML DOM is programmed; its role is to dictate business actions. "Feature Envy" happens when a class relies too much on the data or methods of another class. In test automation, tests should focus purely on the actions, while delegating the complex DOM interactions to the Page Object.

    *   **Example in our code:** Our initial test class was highly dependent on the webpage's structure. It made direct calls to the page's HTML identifiers, such as searching for `By.id("et_pb_contact_name_0")` inside the test logic.

    *   **The refactored solution:** We abstracted the HTML knowledge by encapsulating it within the Page Object pattern. The `RefactoredTest` class now only invokes semantic methods like `qaPage.clickBicycleCheckbox();`. This makes the code more readable and ensures a proper separation of concerns.

---

## 📸 Execution Evidence: Validated Tests 🎀

> *Below, we attach a screenshot demonstrating that, after applying the refactoring, the tests are executed and validated successfully.*

<div align="center">

<img src="https://github.com/user-attachments/assets/f643247a-68c1-445a-afda-8087eaef1dda" alt="Validated Tests Screenshot" style="border: 5px solid #ff69b4; border-radius: 20px; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);" width="800"/>
<img src="https://github.com/user-attachments/assets/cff46d40-0bc0-4ce3-91e6-8f04c7a204b4" alt="Validated Tests Screenshot" style="border: 5px solid #ff69b4; border-radius: 20px; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);" width="800"/>

<br><br>
*All automated tests pass successfully on the clean, refactored code.*
</div>

---

## 💻 Technologies and Languages Used 🎀

The professional tools used for the development of this practice were:

<div align="center">

![Java](https://img.shields.io/badge/Java-FF69B4?style=for-the-badge&logo=java&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-FF69B4?style=for-the-badge&logo=selenium&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5-FF69B4?style=for-the-badge&logo=junit5&logoColor=white)
![Clean Code](https://img.shields.io/badge/Clean_Code-FF69B4?style=for-the-badge)

</div>

<br>

