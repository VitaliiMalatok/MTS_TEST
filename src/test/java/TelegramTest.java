import org.example.TelegramBot;

public class TelegramTest {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot();
        bot.sendMessageToTelegram("Test message from Java");
    }
}
