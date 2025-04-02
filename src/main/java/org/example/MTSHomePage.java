package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class MTSHomePage {
    private final WebDriver driver;
    private final By acceptCookiesButton = By.xpath("//button[contains(text(), 'Принять')]");


    public MTSHomePage(WebDriver driver) {
        this.driver = driver;
    }

    private static final Map<String, By> inputFields = new HashMap<>();

    static {
        inputFields.put("phone", By.id("connection-phone"));
        inputFields.put("sum", By.id("connection-sum"));
        inputFields.put("email", By.id("connection-email"));
        inputFields.put("internetPhone", By.id("internet-phone"));
        inputFields.put("internetSum", By.id("internet-sum"));
        inputFields.put("internetEmail", By.id("internet-email"));
        inputFields.put("scoreInstalment", By.id("score-instalment"));
        inputFields.put("instalmentSum", By.id("instalment-sum"));
        inputFields.put("instalmentEmail", By.id("instalment-email"));
        inputFields.put("arrears", By.id("score-arrears"));
        inputFields.put("arrearsSum", By.id("arrears-sum"));
        inputFields.put("arrearsEmail", By.id("arrears-email"));
    }

    public String getPlaceholderText(String fieldKey) {
        By locator = inputFields.get(fieldKey);
        if (locator == null) {
            throw new IllegalArgumentException("Invalid field key: " + fieldKey);
        }
        return driver.findElement(locator).getAttribute("placeholder");
    }

    public void acceptCookies() {
        try {
            WebElement button = driver.findElement(acceptCookiesButton);
            button.click();
        } catch (Exception e) {

        }
    }
}
