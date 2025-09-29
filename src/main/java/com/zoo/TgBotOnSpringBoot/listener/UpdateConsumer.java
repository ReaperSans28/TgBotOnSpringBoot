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

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public UpdateConsumer(){
        this.telegramClient = new OkHttpTelegramClient("8037617513:AAHJA2LiEpQdHvl6nUzBq3LSXLNwGbJi7gQ"); 
        // рандомный нерабочий токен
    }

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
