package com.zoo.TgBotOnSpringBoot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

import com.zoo.TgBotOnSpringBoot.listener.UpdateConsumer;

@Component
public class ShelterBot implements SpringLongPollingBot {
    private final UpdateConsumer updateConsumer;
    @Value("${telegram.bot.token}")
    private String token;

    public ShelterBot(UpdateConsumer updateConsumer) {
        this.updateConsumer = updateConsumer;
    }

    @Override
    public String getBotToken(){
        return this.token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer(){
        return updateConsumer;
    }
}
