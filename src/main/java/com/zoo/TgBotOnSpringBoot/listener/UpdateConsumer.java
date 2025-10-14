package com.zoo.TgBotOnSpringBoot.listener;

import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
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
        this.telegramClient = new OkHttpTelegramClient("8037617513:AAHJA2LiEpQdHvl6nUzBq3LSXLNwGbJi7gQ");
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
            }
        }
    }

    /**
     * Отправка пользователю приветственного сообщения и клавиатуры для выбора приюта.
     * Клавиатура содержит кнопки "Приют для кошек" и "Приют для собак" (На данный момент не работают).
     *
     * @param chatId идентификатор чата, куда отправлять сообщения
     */
    private void sendShelters(Long chatId) {
        SendMessage helloWorld = SendMessage.builder()
                .text("*Приветствие*\nКнопочки нерабочие кстати")
                .chatId(chatId)
                .build();
        SendMessage message = SendMessage.builder()
                .text("Выберите приют")
                .chatId(chatId)
                .build();

        var butt1 = InlineKeyboardButton.builder()
                .text("Приют для кошек")
                .callbackData("catShelter")
                .build();
        var butt2 = InlineKeyboardButton.builder()
                .text("Приют для собак")
                .callbackData("dogShelter")
                .build();
        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(butt1),
                new InlineKeyboardRow(butt2)
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
}
