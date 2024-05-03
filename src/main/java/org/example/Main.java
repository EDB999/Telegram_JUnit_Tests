package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class TG_JUnit_Bot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "JUnit_Test_bot";
    }

    @Override
    public String getBotToken() {
        return "6665563428:AAGMw8nMoKZzi2uKXGMi7yqJ3IzHClxAh6o";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText().trim();
            int statusCode = sendMessage(String.valueOf(chatId), "123");
            String inputFilePath = "/Kot-Load.jpg";
            int statusCodePhoto = sendPhoto(String.valueOf(chatId), inputFilePath);
            System.out.println("Status code: " + statusCode);
//            if (message.equals("/send_photo")) {
//                int statusCode = sendMessage(String.valueOf(chatId), "123");
//                int statusCodePhoto = sendPhoto(String.valueOf(chatId));
//                System.out.println("Status code: " + statusCode);
//            }
        }
    }

    public int sendMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId+"9999999");
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(sendMessage);
            return 200;
        } catch (TelegramApiException e) {
            return ((TelegramApiRequestException) e).getErrorCode();
        }
    }

    public int sendPhoto(String chatId, String inputFilePath) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        BufferedImage photo = convertPhoto(inputFilePath);
        String outputFilePath = System.getProperty("java.io.tmpdir") + File.separator + "photo.png";
        savePhoto(photo, outputFilePath);
        try (InputStream inputStream = new FileInputStream(outputFilePath)) {
            InputFile inputFile = new InputFile(inputStream, "photo.png");
            sendPhoto.setPhoto(inputFile);
            try {
                execute(sendPhoto);
                return 200;
            } catch (TelegramApiException e) {
                return ((TelegramApiRequestException) e).getErrorCode();
            }
        } catch (IOException e) {
            System.err.println("Error reading photo resource: " + e.getMessage());
            e.printStackTrace();
            return 500;
        }
    }

    public static void savePhoto(BufferedImage photo, String outputFilePath) {
        try {
            File outputFile = new File(outputFilePath);
            ImageIO.write(photo, "png", outputFile);
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public BufferedImage convertPhoto(String inputFilePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(inputFilePath);
            BufferedImage inputImage = ImageIO.read(inputStream);
            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = outputImage.createGraphics();
            graphics.drawImage(inputImage, 0, 0, null);
            graphics.dispose();
            return outputImage;
        } catch (Exception e) {
            System.err.println("Error converting image: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(new TG_JUnit_Bot());
        } catch (TelegramApiException e) {
            // Обработка исключения
            e.printStackTrace();
        }
    }
}