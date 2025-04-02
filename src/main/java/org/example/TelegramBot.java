package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {
    private final String token = "7904768280:AAHFkKLGinGFpm-0IvbIdSCO8rPcZdjndeY";
    private final String chatId = "646030568";

    @Override
    public String getBotUsername() {
        return "@Vitali_Malatok_Allure_Report_Bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Этот метод может быть не нужен для вашего случая
    }

    public void sendMessageToTelegram(String messageText) {
        // Создаем объект SendMessage
        SendMessage message = new SendMessage();
        message.setChatId(chatId); // Устанавливаем ID чата
        message.setText(messageText); // Устанавливаем текст сообщения

        try {
            // Отправляем сообщение через execute()
            execute(message);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
