import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    public static final String INCOGNITO = "--incognito";
    public static final int TIMEOUT = 8;
    protected WebDriver driver;
    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);
//    public TelegramBotRunner botRunner;

    @BeforeClass
    public void setUp() {
        LOGGER.info("Setting up WebDriver...");
        ChromeOptions options = new ChromeOptions();
//        botRunner = new TelegramBotRunner();
        //options.addArguments(INCOGNITO);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
        driver.get("https://mts.by/");
//        driver.get("https://www.onliner.by/");

        LOGGER.info("Navigated to: " + "baseUrl");
    }



    @AfterClass
    public void tearDown() throws IOException {

        if (driver != null) {
            LOGGER.info("Closing WebDriver...");
            driver.quit();
        }
    }
}
