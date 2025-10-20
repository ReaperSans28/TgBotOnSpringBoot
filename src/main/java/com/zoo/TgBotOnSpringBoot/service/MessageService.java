package com.zoo.TgBotOnSpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Сервис для обработки текстовых сообщений Telegram бота
 *
 * <p>Обрабатывает команды и текстовые сообщения от пользователей</p>
 */
@Service
public class MessageService {

    @Autowired
    private final OkHttpTelegramClient telegramClient;
    private final KeyboardService keyboardService;

    public MessageService(OkHttpTelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        this.keyboardService = new KeyboardService();
    }

    /**
     * Обрабатывает входящие текстовые сообщения от пользователей
     *
     * @param message объект сообщения от Telegram
     */
    public void handleMessage(Message message) {
        String messageText = message.getText();
        Long chatId = message.getChatId();

        switch (messageText) {
            case "/start" -> sendShelters(chatId);
            default -> callVolonteer(chatId);
        }
    }

    /**
     * Создает объект сообщения для отправки
     *
     * @param chatId идентификатор чата получателя
     * @param messageText текст сообщения
     * @return объект SendMessage для отправки через Telegram API
     */
    public SendMessage sendMessage(Long chatId, String messageText){
        SendMessage message = SendMessage.builder()
                .text(messageText)
                .chatId(chatId)
                .build();
        return message;
    }

    /**
     * Отправляет приветственное сообщение и клавиатуру выбора приюта
     *
     * @param chatId идентификатор чата для отправки сообщений
     */
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

    /**
     * Регистрирует пользователя как волонтера
     *
     * @param chatId идентификатор чата
     * @param user объект пользователя Telegram
     */
    private void registrateVolonteer(Long chatId, User user) {
        if (user.getUserName().isEmpty()) {
            SendMessage message = sendMessage(chatId, "Введите ваш номер телефона, чтобы зарегестрироваться как волонтёр");
            try{
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Проверяет корректность отправленного отчета
     *
     * @param chatId идентификатор чата
     * @param message сообщение с отчетом
     */
    private void validateReport(Long chatId, Message message) {
        if (message.getText() != null && message.getText().contains("Рацион") && message.getText().contains("Самочувствие")) {
            sendMessage(chatId, "Ваш отчёт будет передан на проверку волонтёра, если что-то не так, мы вам сообщим");
        } else {
            sendMessage(chatId, "Вы некорректно заполнили отчёт, минимум необходимо упомянуть о рационе животного и его самочувствии\nПопробуйте ещё раз");
        }
    }

    /**
     * Отправляет сообщение с предложением обратиться к волонтеру
     *
     * @param chatId идентификатор чата
     */
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