package com.zoo.TgBotOnSpringBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

@Configuration
public class BotConfiguration {
    @Bean
    public OkHttpTelegramClient telegramClient() {
        return new OkHttpTelegramClient("8102494568:AAG3m0hym73Asidj6p4o6RSsxzkVIMBWBHU");
    }
}
