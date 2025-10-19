package com.zoo.TgBotOnSpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageService {

    @Autowired
    private final OkHttpTelegramClient telegramClient;
    private final KeyboardService keyboardService;

    public MessageService(OkHttpTelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        this.keyboardService = new KeyboardService();
    }

    public void handleMessage(Message message) {
        String messageText = message.getText();
        Long chatId = message.getChatId();

        switch (messageText) {
            case "/start" -> sendShelters(chatId);
        //     case "dogShelter" -> dogShelterInfo(chatId);
        //     case "catShelter" -> catShelterInfo(chatId);
        //     case "catShelterInfo" -> catShelterDetails(chatId);
        //     case "dogShelterInfo" -> dogShelterDetails(chatId);
        //     case "adoptCat" -> adoptCat(chatId);
        //     case "adoptDog" -> adoptDog(chatId);
            default -> callVolonteer(chatId);
        }
    }
    
    public SendMessage sendMessage(Long chatId, String messageText){
        SendMessage message = SendMessage.builder()
                .text(messageText)
                .chatId(chatId)
                .build();
        return message;
    }

    public void sendShelters(Long chatId) {
        SendMessage helloWorld = sendMessage(chatId, "*Приветствие*");
        SendMessage message = sendMessage(chatId, "Выберите приют");
        message.setReplyMarkup(keyboardService.shelterSelectionKeyboard());

        try{
            telegramClient.execute(helloWorld);
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void registrateVolonteer(Long chatId, User user) {
        if (user.getUserName().isEmpty()) {
            SendMessage message = sendMessage(chatId, "Введите ваш номер телефона, чтобы зарегестрироваться как волонтёр");
        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        } else {
            // Тут есть код, просто представить надо как он выглядит
        }
    }

    // Богдан, как сделать, чтобы оно следующее соо после команды обрабатывало((
    private void validateReport(Long chatId, Message message) {
        if (message.getText() != null && message.getText().contains("Рацион") && message.getText().contains("Самочувствие")) {
            sendMessage(chatId, "Ваш отчёт будет передан на проверку волонтёра, если что-то не так, мы вам сообщим");
        } else {
            sendMessage(chatId, "Вы некорректно заполнили отчёт, минимум необходимо упомянуть о рационе животного и его самочувствии\nПопробуйте ещё раз");
        }
    }

    // однажды я его деделаю даже, ходят слухи..
    private void callVolonteer(Long chatId) {
        SendMessage message = sendMessage(chatId, "Я не могу ответить на ваш вопрос.\nВозможно, вы бы хотели написать волонтёру?");
        
        message.setReplyMarkup(keyboardService.emergencyButton());
        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
