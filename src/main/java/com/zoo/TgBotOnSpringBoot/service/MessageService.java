package com.zoo.TgBotOnSpringBoot.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.zoo.TgBotOnSpringBoot.model.Animal;
import com.zoo.TgBotOnSpringBoot.model.User;

/**
 * Сервис для обработки текстовых сообщений Telegram бота
 *
 * <p>Обрабатывает команды и текстовые сообщения от пользователей</p>
 */
@Service
public class MessageService {

    private final OkHttpTelegramClient telegramClient;
    private final KeyboardService keyboardService;
    private final UserService userService;
    private final AnimalService animalService;

    List<Long> stateContactDetails = new ArrayList<>();
    List<Long> stateReport = new ArrayList<>();

    public MessageService(OkHttpTelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        this.userService = new UserService();
        this.keyboardService = new KeyboardService();
        this.animalService = new AnimalService();
    }

    /**
     * Обрабатывает входящие текстовые сообщения от пользователей
     *
     * @param message объект сообщения от Telegram
     */
    public void handleMessage(Message message) {
        String messageText = message.getText();
        Long chatId = message.getChatId();
        
        if (this.stateContactDetails.contains(chatId)) {
            validateContactDetails(chatId, message);
        } else if(this.stateReport.contains(chatId)) {
            validateReport(chatId, message);
        } else {
            switch (messageText) {
                case "/start" -> shelterInfo(chatId);
                default -> callVolonteer(chatId);
            }
        }
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getFrom().getId();

        switch (data) {
            case "shelterInfo" -> shelterDetails(chatId);
            case "adopt" -> adopt(chatId);
            case "userContactDetails" -> giveState(stateContactDetails, chatId);
            case "report" -> giveState(stateReport, chatId);
            case "animalList" -> animalsToAdopt(chatId);
            case "callVolonteer" -> volonteerList(chatId);
        }
        try {
            AnswerCallbackQuery answer = new AnswerCallbackQuery(callbackQuery.getId());
            telegramClient.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void volonteerList(Long chatId) {
        List<User> volonteers = userService.getAllVolonteers();
        String messageText = "Наши волонтёры:\n";
        for (User volonteer : volonteers) {
            messageText = messageText.concat(volonteer.getUserContactDetails() + "\n");
        }
        SendMessage message = sendMessage(chatId, messageText);
        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void animalsToAdopt(long chatId) {
        List<Animal> animals = animalService.getAnimalsByStatus("В приюте");
        for (Animal animal : animals) {
            String messageText = animal.getAnimalName() + "\n" + animal.getAnimalAge() + " лет\n" + animal.getAnimalDescription();
            SendMessage message = sendMessage(chatId, messageText);
            try{
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // ну это как в питоне состояние
    private void giveState(List state, long chatId) {
        state.add(chatId);
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
    public void shelterInfo(Long chatId) {
        if (userService.userExists(chatId)) {
                SendMessage helloWorld = sendMessage(chatId, "*Приветствие*");
                try{
                    telegramClient.execute(helloWorld);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
        } else {
            User user = new User(null, chatId, null, false);
            userService.addUser(user);
        }
        SendMessage message = sendMessage(chatId, "*Кратко о приюте*");
        message.setReplyMarkup(keyboardService.shelterInfoKeyboard());

        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Регистрирует пользователя как волонтера
     *
     * @param user объект пользователя Telegram
     */
    public void registrateVolonteer(User user) {
        if (user.getUserContactDetails().isEmpty()) {
            SendMessage message = sendMessage(user.getUserTgId(), "Введите ваш номер телефона, чтобы зарегестрироваться как волонтёр");
            try{
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        giveState(stateContactDetails, user.getUserTgId());
        }
    }

    /**
     * Проверяет корректность отправленного отчета
     *
     * @param chatId идентификатор чата
     * @param message сообщение с отчетом
     */
    public void validateReport(Long chatId, Message message) {
        if (message.hasText() && message.hasPhoto()) {
            sendMessage(chatId, "Ваш отчёт будет передан на проверку волонтёра, если что-то не так, мы вам сообщим");
            stateReport.remove(chatId);
        } else if (!(message.hasText())) {
            sendMessage(chatId, "Автоматическая проверка: в вашем сообщении нет текста, опишите как животное себя чувствует с вами");
        } else {
            sendMessage(chatId, "Автоматическая проверка: в вашем сообщении нет фотографии животного");
        }
    }

    public void validateContactDetails(Long chatId, Message message) {
        if (message.getText().matches("^\\+7-9\\d{2}-\\d{3}-\\d{2}-\\d{2}$")) {
            sendMessage(chatId, "Ваш номер телефона записан, спасибо");
            String contactDetails = message.getText();
            User user = userService.findByUserTgId(message.getChatId()).get();
            user.setUserContactDetails(contactDetails);
            userService.editUser(user);
            stateContactDetails.remove(chatId);
        } else {
            sendMessage(chatId, "Проверьте, не допустили ли вы ошибку в вашем номере\nПопробуйте ещё раз");
        }
    }

    /**
     * Отправляет сообщение с предложением обратиться к волонтеру
     *
     * @param chatId идентификатор чата
     */
    public void callVolonteer(Long chatId) {
        SendMessage message = sendMessage(chatId, "Я не могу ответить на ваш вопрос.\nВозможно, вы бы хотели написать волонтёру?");

        message.setReplyMarkup(keyboardService.emergencyButton());
        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void shelterDetails(Long chatId){
        SendMessage message = sendMessage(chatId, "*Конкретно о приюте, как себя вести там, расписание*");

        File photoFile = new File("picture/photo.jpg");
        SendPhoto message2 = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(photoFile, "photo.jpg"))
                .caption("*Как проехать, контакт охраны и тд*")
                .build();
        message2.setReplyMarkup(keyboardService.aboutShelter());
        try{
            telegramClient.execute(message);
            telegramClient.execute(message2);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void adopt(Long chatId) {
        SendMessage message = sendMessage(chatId, "Общая бюрократическая хрень");
        SendMessage message2 = sendMessage(chatId, "Детальные рекомендации о знакомстве с питомцем");
        SendMessage message3 = sendMessage(chatId, "Советы кинолога");
        message3.setReplyMarkup(keyboardService.HowToAdopt());
        try{
            telegramClient.execute(message);
            telegramClient.execute(message2);
            telegramClient.execute(message3);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}