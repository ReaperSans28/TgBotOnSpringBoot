package com.zoo.TgBotOnSpringBoot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

import com.zoo.TgBotOnSpringBoot.listener.UpdateConsumer;

/**
 * Класс ShelterBot представляет Telegram-бота приюта.
 * Реализует интерфейс SpringLongPollingBot для получения обновлений через Long Polling.
 * Управляет получением обновлений через UpdateConsumer.
 */
@Component
public class ShelterBot implements SpringLongPollingBot {
    private final UpdateConsumer updateConsumer;

    /**
     * Токен бота, загружаемый из настроек приложения (application.properties).
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Конструктор ShelterBot.
     *
     * @param updateConsumer объект для обработки входящих обновлений Telegram
     */
    public ShelterBot(UpdateConsumer updateConsumer) {
        this.updateConsumer = updateConsumer;
    }

    /**
     * Получение токена Telegram-бота.
     * @return токен бота в виде строки
     */
    @Override
    public String getBotToken() {
        return this.token;
    }

    /**
     * Получение обработчика обновлений Telegram.
     *
     * @return объект UpdateConsumer для потребления обновлений
     */
    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}