package com.zoo.TgBotOnSpringBoot.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Сервис для обработки callback запросов от инлайн-клавиатур Telegram бота
 *
 * <p>Обрабатывает нажатия на кнопки и выполняет соответствующие действия</p>
 */
@Service
public class CallbackService {

    private final KeyboardService keyboardService;
    @Autowired
    private final OkHttpTelegramClient telegramClient;
    private final MessageService messageService;

    public CallbackService(OkHttpTelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        this.messageService = new MessageService(this.telegramClient);
        this.keyboardService = new KeyboardService();
    }

    /**
     * Обрабатывает callback запросы от инлайн-клавиатур
     *
     * @param callbackQuery объект callback запроса от Telegram
     */
    public void handleCallbackQuery(CallbackQuery callbackQuery){
        var data = callbackQuery.getData();
        var chatId = callbackQuery.getFrom().getId();
        switch (data) {
            case "dogShelter" -> dogShelterInfo(chatId);
            case "catShelter" -> catShelterInfo(chatId);
            case "catShelterInfo" -> catShelterDetails(chatId);
            case "dogShelterInfo" -> dogShelterDetails(chatId);
            case "adoptCat" -> adoptCat(chatId);
            case "adoptDog" -> adoptDog(chatId);
            case "report" -> { //всё будет однажды, но не сегодня
            }
        }
        try {
            AnswerCallbackQuery answer = new AnswerCallbackQuery(callbackQuery.getId());
            telegramClient.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет информацию о приюте для собак
     *
     * @param chatId идентификатор чата для отправки сообщения
     */
    private void dogShelterInfo(Long chatId){
        SendMessage message = messageService.sendMessage(chatId, "*Кратко о приюте для собак*");
        message.setReplyMarkup(keyboardService.dogShelterInfoKeyboard());
        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет детальную информацию о приюте для собак с фотографией
     *
     * @param chatId идентификатор чата для отправки сообщения
     */
    private void dogShelterDetails(Long chatId){
        SendMessage message = messageService.sendMessage(chatId, "*Конкретно о приюте, как себя вести там, расписание*");

        File photoFile = new File("picture/photo.jpg");
        SendPhoto message2 = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(photoFile, "photo.jpg"))
                .caption("*Как проехать, контакт охраны и тд*")
                .build();
        message2.setReplyMarkup(keyboardService.emergencyButton());
        try{
            telegramClient.execute(message);
            telegramClient.execute(message2);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет информацию о процессе усыновления собаки
     *
     * @param chatId идентификатор чата для отправки сообщения
     */
    private void adoptDog(Long chatId) {
        SendMessage message = messageService.sendMessage(chatId, "Общая бюрократическая хрень");
        SendMessage message2 = messageService.sendMessage(chatId, "Детальные рекомендации о знакомстве с питомцем");
        SendMessage message3 = messageService.sendMessage(chatId, "Советы кинолога");
        message3.setReplyMarkup(keyboardService.emergencyButton());
        try{
            telegramClient.execute(message);
            telegramClient.execute(message2);
            telegramClient.execute(message3);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет информацию о приюте для кошек
     *
     * @param chatId идентификатор чата для отправки сообщения
     */
    private void catShelterInfo(Long chatId){
        SendMessage message = messageService.sendMessage(chatId, "*Кратко о приюте для кошек*");
        message.setReplyMarkup(keyboardService.catShelterInfoKeyboard());
        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет детальную информацию о приюте для кошек с фотографией
     *
     * @param chatId идентификатор чата для отправки сообщения
     */
    private void catShelterDetails(Long chatId){
        SendMessage message = messageService.sendMessage(chatId, "*Конкретно о приюте, как себя вести там, расписание*");
        new Thread(() -> {
            File photoFile = new File("picture/photo.jpg");
            SendPhoto message2 = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(photoFile, "photo.jpg"))
                    .caption("*Как проехать, контакт охраны и тд*")
                    .build();
            message2.setReplyMarkup(keyboardService.emergencyButton());
            try{
                telegramClient.execute(message);
                telegramClient.execute(message2);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * Отправляет информацию о процессе усыновления кошки
     *
     * @param chatId идентификатор чата для отправки сообщения
     */
    private void adoptCat(Long chatId) {
        SendMessage message = messageService.sendMessage(chatId, "Общая бюрократическая хрень");
        SendMessage message2 = messageService.sendMessage(chatId, "Детальные рекомендации о знакомстве с питомцем");
        message2.setReplyMarkup(keyboardService.emergencyButton());
        try{
            telegramClient.execute(message);
            telegramClient.execute(message2);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}