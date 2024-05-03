package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TGJUnitBotPhotoTest {

    private TG_JUnit_Bot bot;

    @BeforeEach
    public void setUp() {
        bot = new TG_JUnit_Bot();
    }

    @Test
    public void testSendPhoto_success() throws TelegramApiException, IOException {
        String chatId = "-4196292782";
        String inputFilePath = "/Kot-Load.jpg";
        int statusCode = bot.sendPhoto(chatId, inputFilePath);
        assertEquals(200, statusCode);
    }

    @Test
    public void testSendPhoto_errorReadingFile() throws TelegramApiException {
        String chatId = "-4196292782";
        String inputFilePath = "Kot-load.jpg";
        try {
            bot.convertPhoto(inputFilePath);
        } catch (Exception e) {
            System.out.println("converting failed succesfully");
        }
    }

    @Test
    public void testConvertPhoto_errorExecuting() throws TelegramApiException, IOException {
        String chatId = "-4196292782";
        String inputFilePath = "/test.txt";
        try {
            bot.convertPhoto(inputFilePath);
        } catch (Exception e) {
            System.out.println("converting failed succesfully");
        }
    }

    @Test
    public void testSendMessage() {
        String chatId = "-4196292782";
        String text = "Hello, World!";

        int statusCode = bot.sendMessage(chatId, text);

        assertEquals(200, statusCode);
    }
}

