import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test1 {
    private static final Logger logger = LogManager.getLogger(Test1.class);

    @org.testng.annotations.Test
    public void testMethod() {
        System.out.println("Log4j configuration file: " + System.getProperty("log4j.configurationFile"));

        logger.info("This is an info message.");
        logger.error("This is an error message.");
        logger.warn("This is a warning message.");
    }
}