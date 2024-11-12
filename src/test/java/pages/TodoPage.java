package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TodoPage {

    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;

    public TodoPage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    // Locators
    private By todoInput = By.id("todo-input");

    // Methods
    public void addNewItem(String item) {
        WebElement textInput = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(todoInput));
        textInput.click();
        textInput.sendKeys(item);
        textInput.sendKeys(Keys.ENTER);
    }


    public void clickCheckbox(String itemText) {
        String xpathLocator = String.format("//label[text()='%s']/preceding-sibling::input", itemText);
        WebElement list = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("todo-list")));
        List<WebElement> items = list.findElements(By.tagName("li"));
        for (WebElement item : items) {
            WebElement itemCheckbox = item.findElement(By.className("toggle"));
            WebElement itemLabel = item.findElement(By.tagName("label"));
            if (itemLabel.getText().equals(itemText)) {
                actions.click(itemCheckbox).perform();
                break;
            }
        }
    }

    public void assertItemsLeft(Integer expectedCount) {
        WebElement itemsCount = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("todo-count")));
        List<String> items = List.of(itemsCount.getText().split(" "));
        Assertions.assertEquals(expectedCount, Integer.parseInt(items.get(0)), "Wrong items count");
    }
}
