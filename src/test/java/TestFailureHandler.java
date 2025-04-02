import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.TelegramBot;

public class TestFailureHandler {
    private static final Logger LOGGER = LogManager.getLogger(TestFailureHandler.class);
    private final TelegramBot bot;

    public TestFailureHandler(TelegramBot bot) {
        this.bot = bot;
    }

    public void handleTestFailure(String errorMessage, Throwable e) {
        String logs = LogParser.readLastLogLines(LogParser.getLatestLogFile("logs/"), 10);
        String message = TestReporter.generateFailureMessage(errorMessage, logs);
        bot.sendMessageToTelegram(message);
        LOGGER.error(errorMessage, e);
    }
}
