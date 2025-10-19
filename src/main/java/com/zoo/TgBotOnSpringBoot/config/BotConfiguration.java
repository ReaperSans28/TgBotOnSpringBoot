package com.zoo.TgBotOnSpringBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

@Configuration
public class BotConfiguration {
    @Bean
    public OkHttpTelegramClient telegramClient() {
        return new OkHttpTelegramClient("8037617513:AAHJA2LiEpQdHvl6nUzBq3LSXLNwGbJi7gQ");
    }
}
