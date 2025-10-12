package com.zoo.TgBotOnSpringBoot.listener;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Класс UpdateConsumer отвечает за обработку обновлений от Telegram.
 * Реализует интерфейс LongPollingSingleThreadUpdateConsumer для последовательной обработки обновлений в одном потоке.
 */
@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {
    /**
     * Клиент для отправки запросов к Telegram API.
     */
    private final TelegramClient telegramClient;

    /**
     * Конструктор UpdateConsumer.
     * Инициализирует TelegramClient с заданным токеном (Сейчас токен тестовый и нерабочий).
     */
    public UpdateConsumer() {
        this.telegramClient = new OkHttpTelegramClient("8102494568:AAG3m0hym73Asidj6p4o6RSsxzkVIMBWBHU");
    }

    /**
     * Обработка поступившего обновления.
     * При вызове команды /start
     * вызывает метод sendShelters для отправки приветственных сообщений, с клавиатурой выбора приюта.
     *
     * @param update объект обновления от Telegram
     */
    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                sendShelters(chatId);
            } else if (messageText.contains("@") | messageText.matches(".*\\d.*")){
                
            } else if (update.getMessage().hasPhoto()) {
                validateReport(chatId, update.getMessage());
            } else {
                callVolonteer(chatId);
            }
        } else if (update.hasCallbackQuery()) {
                handleCallbackQuery(update.getCallbackQuery());
            }
    }

    private SendMessage sendMessage(Long chatId, String messageText){
        SendMessage message = SendMessage.builder()
                .text(messageText)
                .chatId(chatId)
                .build();
        return message;
    }

    /**
     * Отправка пользователю приветственного сообщения и клавиатуры для выбора приюта.
     * Клавиатура содержит кнопки "Приют для кошек" и "Приют для собак" (На данный момент не работают).
     *
     * @param chatId идентификатор чата, куда отправлять сообщения
     */
    private void sendShelters(Long chatId) {
        SendMessage helloWorld = sendMessage(chatId, "*Приветствие*\nКнопочки нерабочие кстати");
        SendMessage message = sendMessage(chatId, "Выберите приют");

        var butt1 = InlineKeyboardButton.builder()
                .text("Приют для кошек")
                .callbackData("catShelter")
                .build();
        var butt2 = InlineKeyboardButton.builder()
                .text("Приют для собак")
                .callbackData("dogShelter")
                .build();
        var butt3 = InlineKeyboardButton.builder()
                .text("Я - волонтёр")
                .callbackData("volonteerReg")
                .build();
        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(butt1),
                new InlineKeyboardRow(butt2),
                new InlineKeyboardRow(butt3)
        );
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardRows);

        message.setReplyMarkup(keyboard);

        try{
            telegramClient.execute(helloWorld);
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery){
        var data = callbackQuery.getData();
        var chatId = callbackQuery.getFrom().getId();
        var user = callbackQuery.getFrom();
        switch (data) {
            case "volonteerReg" -> registrateVolonteer(chatId, user);
            case "dogShelter" -> dogShelterInfo(chatId);
            case "catShelter" -> catShelterInfo(chatId);
            case "catShelterInfo" -> catShelterDetails(chatId);
            case "dogShelterInfo" -> dogShelterDetails(chatId);
            case "adoptCat" -> adoptCat(chatId);
            case "adoptDog" -> adoptDog(chatId);
            case "report" -> sendMessage(chatId, "Пришлите фотографию животного, его рацион питания, опишите самочувствие и ваши личные заметки про нового друга если таковые имеются");
            default -> callVolonteer(chatId);
        }
        try {
            AnswerCallbackQuery answer = new AnswerCallbackQuery(callbackQuery.getId());
            telegramClient.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void callVolonteer(Long chatId) {
        SendMessage message = sendMessage(chatId, "Я не могу ответить на ваш вопрос.\nВозможно, вы бы хотели написать волонтёру?");
        
        var butt = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
        
        List<InlineKeyboardRow> keyboardRow = List.of(
                new InlineKeyboardRow(butt)
        );
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardRow);
        message.setReplyMarkup(keyboard);
        try{
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

        }
    }

    private void dogShelterInfo(Long chatId){
        SendMessage message = sendMessage(chatId, "*Кратко о приюте для собак*");
        var butt1 = InlineKeyboardButton.builder()
                .text("Узнать побольше о приюте")
                .callbackData("dogShelterInfo")
                .build();
        var butt2 = InlineKeyboardButton.builder()
                .text("Как взять животное?")
                .callbackData("adoptDog")
                .build();
        var butt3 = InlineKeyboardButton.builder()
                .text("Прислать отчёт")
                .callbackData("report")
                .build();
        var butt = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(butt1),
                new InlineKeyboardRow(butt2),
                new InlineKeyboardRow(butt3),
                new InlineKeyboardRow(butt)
        );
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardRows);

        message.setReplyMarkup(keyboard);
        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void dogShelterDetails(Long chatId){
        SendMessage message = sendMessage(chatId, "*Конкретно о приюте, как себя вести там, расписание*");

        new Thread(() -> {
            File photoFile = new File("picture/photo.jpg");
            SendPhoto message2 = SendPhoto.builder()
            .chatId(chatId)
            .photo(new InputFile(photoFile, "photo.jpg"))
            .caption("*Как проехать, контакт охраны и тд*")
            .build();
            var butt = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
            List<InlineKeyboardRow> keyboardRow = List.of(
                new InlineKeyboardRow(butt));
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardRow);

            message2.setReplyMarkup(keyboard);
            try{
                telegramClient.execute(message);
                telegramClient.execute(message2);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
    }).start();
    }

    private void adoptDog(Long chatId) {
        SendMessage message = sendMessage(chatId, "Общая бюрократическая хрень");
        SendMessage message2 = sendMessage(chatId, "Детальные рекомендации о знакомстве с питомцем");
        var butt = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
            List<InlineKeyboardRow> keyboardRow = List.of(
                new InlineKeyboardRow(butt));
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardRow);

            message2.setReplyMarkup(keyboard);
        try{
                telegramClient.execute(message);
                telegramClient.execute(message2);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
    }

    private void catShelterInfo(Long chatId){
        SendMessage message = sendMessage(chatId, "*Кратко о приюте для кошек*");

        var butt1 = InlineKeyboardButton.builder()
                .text("Узнать побольше о приюте")
                .callbackData("catShelterInfo")
                .build();
        var butt2 = InlineKeyboardButton.builder()
                .text("Как взять животное?")
                .callbackData("adoptCat")
                .build();
        var butt3 = InlineKeyboardButton.builder()
                .text("Прислать отчёт")
                .callbackData("report")
                .build();
        var butt = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(butt1),
                new InlineKeyboardRow(butt2),
                new InlineKeyboardRow(butt3),
                new InlineKeyboardRow(butt)
        );
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardRows);

        message.setReplyMarkup(keyboard);
        try{
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void catShelterDetails(Long chatId){
    SendMessage message = sendMessage(chatId, "*Конкретно о приюте, как себя вести там, расписание*");
    new Thread(() -> {
            File photoFile = new File("picture/photo.jpg");
            SendPhoto message2 = SendPhoto.builder()
            .chatId(chatId)
            .photo(new InputFile(photoFile, "photo.jpg"))
            .caption("*Как проехать, контакт охраны и тд*")
            .build();
            var butt = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
            List<InlineKeyboardRow> keyboardRow = List.of(
                new InlineKeyboardRow(butt));
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardRow);

            message2.setReplyMarkup(keyboard);
            try{
                telegramClient.execute(message);
                telegramClient.execute(message2);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
    }).start();
    }

    private void adoptCat(Long chatId) {
        SendMessage message = sendMessage(chatId, "Общая бюрократическая хрень");
        SendMessage message2 = sendMessage(chatId, "Детальные рекомендации о знакомстве с питомцем");
        SendMessage message3 = sendMessage(chatId, "Советы кинолога");
        var butt = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
            List<InlineKeyboardRow> keyboardRow = List.of(
                new InlineKeyboardRow(butt));
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardRow);

            message3.setReplyMarkup(keyboard);
        try{
                telegramClient.execute(message);
                telegramClient.execute(message2);
                telegramClient.execute(message3);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
    }

    private void validateReport(Long chatId, Message message) {
        if (message.getText().contains("Рацион") && message.getText().contains("Самочувствие")) {
            sendMessage(chatId, "Ваш отчёт будет передан на проверку волонтёра, если что-то не так, мы вам сообщим");
        } else {
            sendMessage(chatId, "Вы некорректно заполнили отчёт, минимум необходимо упомянуть о рационе животного и его самочувствии\nПопробуйте ещё раз");
        }
    }
}
