package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.generics.BotSession;

import java.io.File;
import java.io.IOException;

public class TelegramBotRunner extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "@Vitali_Malatok_Allure_Report_Bot";
    }

    @Override
    public String getBotToken() {
        return "7904768280:AAHFkKLGinGFpm-0IvbIdSCO8rPcZdjndeY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Received message: " + messageText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            if (messageText.equals("/send_report")) {
                sendAllureReport(chatId);
            }
        }
    }

    public void sendAllureReport(Long chatId) {
        try {
            // Генерируем отчет Allure
            generateAllureReport();

            // Путь к архиву отчета
            File allureReport = new File("target/allure-report.zip");

            // Отправляем отчет
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(String.valueOf(chatId));
            sendDocument.setDocument(new InputFile(allureReport));

            execute(sendDocument);
        } catch (TelegramApiException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void generateAllureReport() throws IOException, InterruptedException {
        // Запускаем команду для генерации отчета Allure
        Process process = Runtime.getRuntime().exec("allure generate target/allure-results -o target/allure-report");
        process.waitFor();

        // Архивируем отчет
        Process zipProcess = Runtime.getRuntime().exec("zip -r target/allure-report.zip target/allure-report");
        zipProcess.waitFor();
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(BotSession.class);
            botsApi.registerBot(new TelegramBotRunner());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}