package com.example.Selenium.Package03;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class CaptchaSolove_bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        String command = update.getMessage().getText();
        SendMessage message = new SendMessage();
        Message messOfuSER = update.getMessage();
        String text = messOfuSER.getText();
        System.out.println(text);
        message.setChatId(update.getMessage().getChatId().toString());
        if (command.equals("/myname")) {
            if (update.getMessage().getFrom() != null) {
                message.setText("dit me cong san viet nam");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return "CaptchaSlove_bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotToken() {
        return "6928830332:AAGmv3fN_k8YdITzJeOyjqtsDQfWuviF308";
    }

    @RequestMapping("/test")
    public String hello() throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId("1159534870"); // Thay thế YourChatId bằng chat ID của bạn
        message.setText("test 123 good good");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return "world";
    }

    @RequestMapping("/photo")
    public String SendPhoto() throws TelegramApiException {
        String save_image = "D:\\New folder\\Captcha\\captcha.png";
        SendPhoto photo = new SendPhoto();
        photo.setChatId("1159534870");
        photo.setPhoto(new InputFile(new File(String.valueOf(save_image))));
        try {
            this.execute(photo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return "Send photo 200ok";
    }

    @RequestMapping("/getTextUser_Reply")
    public void getTextUser_Reply(Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String text = message.getText();
        System.out.println(text);
    }
}

//1159534870