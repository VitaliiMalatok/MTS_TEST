import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.MTSHomePage;
import org.example.model.PaymentFormPage;
import org.example.service.LogParser;
import org.example.service.TelegramBot;
import org.example.service.TestFailureHandler;
import org.example.service.TestReporter;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

@Epic("MTS Online Payment")
@Feature("Payment Form Tests")
public class Test3 extends BaseTest {
    public MTSHomePage homePage;
    public PaymentFormPage paymentFormPage;
    final String PHONE_NUMBER = "297777777";
    final double SUM_PAY_DOUBLE = 100.0;
    final String SUM_PAY_STRING = String.valueOf(SUM_PAY_DOUBLE);
    final String EMAIL = "test@mail.ru";
    private TelegramBot bot;
    private static final Logger LOGGER = LogManager.getLogger(Test3.class);
    private long startTime;
    private long endTime;
    private TestFailureHandler failureHandler;

    @BeforeMethod
    public void setUpPage() {
        homePage = new MTSHomePage(driver);
        bot = new TelegramBot();
        failureHandler = new TestFailureHandler(bot);  // Инициализация обработчика ошибок
        startTime = System.currentTimeMillis();
    }

    @org.testng.annotations.Test
    void TestChecInfo() {
        try {
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class='b-top-logos']")).isDisplayed());

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