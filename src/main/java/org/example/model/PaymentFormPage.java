package org.example.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PaymentFormPage {
    private final WebDriver driver;

    public PaymentFormPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    private final By phoneInput = By.id("connection-phone");
    private final By sumInput = By.id("connection-sum");
    private final By emailInput = By.id("connection-email");
    private final By submitButton = By.xpath("//form[@id='pay-connection']/button");
    private final By iframeLocator = By.xpath("//iframe[@class='bepaid-iframe']");
    public final By amountOnP = By.xpath("//div[@class='pay-description__cost']");
    public final By amountOnButton = By.xpath("//div[@class='card-page__card']/button");
    public final By phoneNumberLabel = By.xpath("//div[@class='pay-description__text']");
    public final By ccNumberLabel = By.xpath("//input[@id='cc-number']/..");
    public final By ccExpLabel = By.xpath("//input[@autocomplete='cc-exp']/..");
    public final By ccVLabel = By.xpath("//input[@name='verification_value']/..");
    public final By ccNameLabel = By.xpath("//input[@autocomplete='cc-name']/..");
    private final By logos = By.xpath("//app-card-input");

    public void inputDataInForm(String phone, String sum, String email) {
        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(sumInput).sendKeys(sum);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(submitButton).click();
    }

    public void switchToFrame() {
        WebElement iframe = driver.findElement(iframeLocator);
        driver.switchTo().frame(iframe);
    }

    public String getTextFromWebElement(By locator) {
        return driver.findElement(locator).getAttribute("innerText");
    }

    public Double getDoubleFromWebElement(By locator, String regex, int index) {
        return Double.parseDouble(getTextFromWebElement(locator).split(regex)[index]);
    }

    public List<WebElement> getLogos() {
        return driver.findElements(logos);
    }
}
