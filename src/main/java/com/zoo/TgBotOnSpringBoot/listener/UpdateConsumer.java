package com.zoo.TgBotOnSpringBoot.listener;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.zoo.TgBotOnSpringBoot.service.MessageService;

/**
 * Класс UpdateConsumer отвечает за обработку обновлений от Telegram.
 * Реализует интерфейс LongPollingSingleThreadUpdateConsumer для последовательной обработки обновлений в одном потоке.
 */
@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {
    /**
     * Клиент для отправки запросов к Telegram API.
     */
    private final OkHttpTelegramClient telegramClient;
    private final MessageService messageService;

    /**
     * Конструктор UpdateConsumer.
     * Инициализирует TelegramClient с заданным токеном (Сейчас токен тестовый и нерабочий).
     */
    public UpdateConsumer() {
        this.telegramClient = new OkHttpTelegramClient("8037617513:AAHJA2LiEpQdHvl6nUzBq3LSXLNwGbJi7gQ");
        this.messageService = new MessageService(this.telegramClient);
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
            messageService.handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            messageService.handleCallbackQuery(update.getCallbackQuery());
        }
    }
}
