package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.TodoPage;

import java.time.Duration;

public class firstTests {

    private WebDriver driver;
    private HomePage homePage;
    private TodoPage todoPage;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.edgedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new EdgeDriver();
        homePage = new HomePage(driver);
        todoPage = new TodoPage(driver);
    }

    @Test
    public void toDoListTest() throws InterruptedException {
        driver.get("https://todomvc.com/");

        homePage.clickLink("React New");

        int todoNum = 4;
        for (int i = 1; i <= todoNum; i++) {
            todoPage.addNewItem("Item " + i);
        }
        int todoCheckNum = 2;
        for (int i = 1; i <= todoCheckNum; i++) {
            todoPage.clickCheckbox("Item " + i);
        }

        todoPage.assertItemsLeft(todoNum - todoCheckNum);
        Thread.sleep(5000);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
