import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.MTSHomePage;
import org.example.PaymentFormPage;
import org.example.TelegramBot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Epic("MTS Online Payment")
@Feature("Payment Form Tests")
public class Test extends BaseTest {
    public MTSHomePage homePage;
    public PaymentFormPage paymentFormPage;
    final String PHONE_NUMBER = "297777777";
    final double SUM_PAY_DOUBLE = 100.0;
    final String SUM_PAY_STRING = String.valueOf(SUM_PAY_DOUBLE);
    final String EMAIL = "test@mail.ru";
    private TelegramBot bot;
    private static final Logger LOGGER = LogManager.getLogger(Test.class);
    private long startTime;
    private long endTime;
    private TestFailureHandler failureHandler;


    @BeforeMethod
    @Step("Initializing Home Page")
    public void setUpPage() {
        homePage = new MTSHomePage(driver);
        bot = new TelegramBot();
        failureHandler = new TestFailureHandler(bot);  // Инициализация обработчика ошибок
        startTime = System.currentTimeMillis();
    }

    @org.testng.annotations.Test
    @Story("Verify Input Placeholders")
    @Severity(SeverityLevel.MINOR)
    @Description("Check that placeholders in input fields match expected values")
    void checkInputText() {
        LOGGER.info("123");
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(homePage.getPlaceholderText("phone"), "Номер телефона", "Ошибка на поле 'phone'");
            softAssert.assertEquals(homePage.getPlaceholderText("sum"), "Сумма", "Ошибка на поле 'sum'");
            softAssert.assertEquals(homePage.getPlaceholderText("email"), "E-mail для отправки чека", "Ошибка на поле 'email'");
            softAssert.assertEquals(homePage.getPlaceholderText("internetPhone"), "Номер абонента", "Ошибка на поле 'internetPhone'");
            softAssert.assertEquals(homePage.getPlaceholderText("internetSum"), "Сумма", "Ошибка на поле 'internetSum'");
            softAssert.assertEquals(homePage.getPlaceholderText("internetEmail"), "E-mail для отправки чека", "Ошибка на поле 'internetEmail'");
            softAssert.assertEquals(homePage.getPlaceholderText("scoreInstalment"), "Номер счета на 44", "Ошибка на поле 'scoreInstalment'");
            softAssert.assertEquals(homePage.getPlaceholderText("instalmentSum"), "Сумма", "Ошибка на поле 'instalmentSum'");
            softAssert.assertEquals(homePage.getPlaceholderText("instalmentEmail"), "E-mail для отправки чека", "Ошибка на поле 'instalmentEmail'");
            softAssert.assertEquals(homePage.getPlaceholderText("arrears"), "Номер счета на 2073", "Ошибка на поле 'arrears'");
            softAssert.assertEquals(homePage.getPlaceholderText("arrearsSum"), "Сумма", "Ошибка на поле 'arrearsSum'");
            softAssert.assertEquals(homePage.getPlaceholderText("arrearsEmail"), "E-mail для отправки чека", "Ошибка на поле 'arrearsEmail'");
            // Проверка всех ошибок
            softAssert.assertAll();
            endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String logs = LogParser.readLastLogLines(LogParser.getLatestLogFile("logs/"), 20);
            String message = TestReporter.generateSuccessMessage(startTime, endTime, duration, logs);
            bot.sendMessageToTelegram(message);
        } catch (AssertionError e) {
            failureHandler.handleTestFailure("Тест зафейлился!", e);
            throw e;
        } catch (Exception e) {
            failureHandler.handleTestFailure("Тест упал из-за ошибки: " + e.getMessage(), e);
            throw e;
        }
    }

    @org.testng.annotations.Test
    @Story("Verify Payment Form")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Fill in payment form and verify displayed values")
    void checkPayForm() {
        try {
            Allure.step("Accepting cookies");
            homePage.acceptCookies();
            Allure.step("Initializing Payment Form Page");
            paymentFormPage = new PaymentFormPage(driver);
            Allure.step("Filling in payment form");
            paymentFormPage.inputDataInForm(PHONE_NUMBER, SUM_PAY_STRING, EMAIL);
            Allure.step("Switching to payment iframe");
            paymentFormPage.switchToFrame();
            Allure.step("Fetching payment details from UI");
            Double actualAmountOnP = paymentFormPage.getDoubleFromWebElement(
                    paymentFormPage.amountOnP, " ", 0);
            Double actualSumOnButton = paymentFormPage.getDoubleFromWebElement(
                    paymentFormPage.amountOnButton, " ", 1);
            String actualPhoneNumber = paymentFormPage.getTextFromWebElement(paymentFormPage.phoneNumberLabel)
                    .replaceAll(".*Номер:", "").trim();
            String actualTextOfC_C_Number = paymentFormPage.getTextFromWebElement(paymentFormPage.ccNumberLabel);
            String actualTextOfC_C_Exp = paymentFormPage.getTextFromWebElement(paymentFormPage.ccExpLabel);
            String actualTextOfC_C_V = paymentFormPage.getTextFromWebElement(paymentFormPage.ccVLabel);
            String actualTextOnPersonName = paymentFormPage.getTextFromWebElement(paymentFormPage.ccNameLabel);
            List<WebElement> actualLogos = paymentFormPage.getLogos();

            Allure.step("Validating payment details");
            SoftAssert softAssert = new SoftAssert();

            // Ввод значений с проверкой
            softAssert.assertEquals(actualAmountOnP, SUM_PAY_DOUBLE, "Сумма в <p> отличается");
            softAssert.assertEquals(actualSumOnButton, SUM_PAY_DOUBLE, "Сумма на <button> отличается");
            softAssert.assertEquals(actualPhoneNumber, "375297777777", "Номер отличается");
            softAssert.assertEquals(actualTextOfC_C_Number, "Номер карты", "Отличие Номер карты");
            softAssert.assertEquals(actualTextOfC_C_Exp, "Срок действия", "Отличие Срок действия");
            softAssert.assertEquals(actualTextOfC_C_V, "CVC", "Отличие CVC");
            softAssert.assertEquals(actualTextOnPersonName, "Имя держателя (как на карте)", "Отличие Имя держателя");

            // Проверка логотипов
            endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String logs = LogParser.readLastLogLines(LogParser.getLatestLogFile("logs/"), 20);
            String message = TestReporter.generateSuccessMessage(startTime, endTime, duration, logs);
            bot.sendMessageToTelegram(message);
        } catch (AssertionError e) {
            failureHandler.handleTestFailure("Тест зафейлился!", e);
            throw e;
        } catch (Exception e) {
            failureHandler.handleTestFailure("Тест упал из-за ошибки: " + e.getMessage(), e);
            throw e;
        }
    }
}
