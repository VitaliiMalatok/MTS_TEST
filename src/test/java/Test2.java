import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.MTSHomePage;
import org.example.model.PaymentFormPage;
import org.example.TelegramBot;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.text.SimpleDateFormat;
import java.util.Date;

@Epic("MTS Online Payment")
@Feature("Payment Form Tests")
public class Test2 extends BaseTest {
    private MTSHomePage homePage;
    private PaymentFormPage paymentFormPage;
    private TelegramBot bot;
    private static final Logger LOGGER = LogManager.getLogger(Test2.class);
    private long startTime;
    private static final String PHONE_NUMBER = "297777777";
    private static final double SUM_PAY_DOUBLE = 100.0;
    private static final String SUM_PAY_STRING = String.valueOf(SUM_PAY_DOUBLE);
    private static final String EMAIL = "test@mail.ru";

    @BeforeMethod
    @Step("Initializing Home Page")
    public void setUpPage() {
        homePage = new MTSHomePage(driver);
        paymentFormPage = new PaymentFormPage(driver);
        bot = new TelegramBot();
        startTime = System.currentTimeMillis();
    }

    private void sendTestReportToTelegram(String testClassName, boolean isSuccess) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String message = String.format("Allure Report for %s\nStart: %s\nEnd: %s\nDuration: %dms\nStatus: %s",
                testClassName, dateFormat.format(new Date(startTime)), dateFormat.format(new Date(endTime)), duration, isSuccess ? "PASSED" : "FAILED");
        bot.sendMessageToTelegram(message);
    }

    @org.testng.annotations.Test
    @Story("Verify Input Placeholders")
    @Severity(SeverityLevel.MINOR)
    @Description("Check that placeholders in input fields match expected values")
    void checkInputText() {
        LOGGER.info("Starting test: checkInputText");
        try {
            SoftAssert softAssert = new SoftAssert();

            softAssert.assertEquals(homePage.getPlaceholderText("phone"), "Номер телефона", "Ошибка на поле 'phone'");
            softAssert.assertEquals(homePage.getPlaceholderText("sum"), "Сумма", "Ошибка на поле 'sum'");
            softAssert.assertEquals(homePage.getPlaceholderText("email"), "E-mail для отправки чека", "Ошибка на поле 'email'");

            softAssert.assertAll();
            sendTestReportToTelegram(getClass().getSimpleName(), true);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage(), e);
            bot.sendMessageToTelegram("Тест зафейлился!");
            sendTestReportToTelegram(getClass().getSimpleName(), false);
            throw e;
        }
    }

    @org.testng.annotations.Test
    @Story("Verify Payment Form")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Fill in payment form and verify displayed values")
    void checkPayForm() {
        LOGGER.info("Starting test: checkPayForm");
        try {
            homePage.acceptCookies();
            paymentFormPage.inputDataInForm(PHONE_NUMBER, SUM_PAY_STRING, EMAIL);
            paymentFormPage.switchToFrame();

            SoftAssert softAssert = new SoftAssert();

            softAssert.assertEquals(paymentFormPage.getDoubleFromWebElement(paymentFormPage.amountOnP, " ", 0), SUM_PAY_DOUBLE, "Сумма в <p> отличается");
            softAssert.assertEquals(paymentFormPage.getDoubleFromWebElement(paymentFormPage.amountOnButton, " ", 1), SUM_PAY_DOUBLE, "Сумма на <button> отличается");

            softAssert.assertAll();
            sendTestReportToTelegram(getClass().getSimpleName(), true);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage(), e);
            bot.sendMessageToTelegram("Тест зафейлился!");
            sendTestReportToTelegram(getClass().getSimpleName(), false);
            throw e;
        }
    }
}
