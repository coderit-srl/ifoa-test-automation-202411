package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class testFirst {

    private WebDriver driver;
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 5;
    private WebDriverWait webDriverWait;
    private Actions actions;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.edgedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new EdgeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        actions = new Actions(driver);
    }

    @Test
    public void toDoListTest() throws InterruptedException {
        driver.get("https://todomvc.com/");
        WebElement link = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("React New")));
        link.click();
        WebElement textInput = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("todo-input")));
        int todoNum = 4;
        for (int i = 1; i <= todoNum; i++) {
            addNewItem(textInput, "Item " + i);
        }
        int todoCheckNum = 2;
        for (int i = 1; i <= todoCheckNum; i++) {
            checkItem("Item " + i);
        }
        WebElement todoCount = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("todo-count")));
        Assertions.assertEquals(todoNum - todoCheckNum, Integer.parseInt(todoCount.getText().split(" ")[0]));
        Thread.sleep(5000);
    }

    private void addNewItem(WebElement input, String item) {
        input.click();
        input.sendKeys(item);
        input.sendKeys(Keys.ENTER);
    }

    private void checkItem(String item) {
        driver.findElement(By.xpath(String.format("//label[text()='%s']/preceding-sibling::input", item))).click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
