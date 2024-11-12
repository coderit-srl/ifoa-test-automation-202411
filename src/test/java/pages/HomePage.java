package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    // Locators


    // Methods
    public void clickLink(String linkText) {
        WebElement link = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
        link.click();
    }
}
