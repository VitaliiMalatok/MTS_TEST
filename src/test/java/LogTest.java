import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {
    private static final Logger LOGGER = LogManager.getLogger(LogTest.class);

    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");

        LOGGER.info("Test log to Telegram!");
    }
}