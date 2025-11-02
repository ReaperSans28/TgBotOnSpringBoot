package com.zoo.TgBotOnSpringBoot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import com.zoo.TgBotOnSpringBoot.bot.ShelterBot;
import com.zoo.TgBotOnSpringBoot.listener.UpdateConsumer;
import com.zoo.TgBotOnSpringBoot.service.MessageService;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

class TgBotOnSpringBootApplicationTests {

    @Test
    void shelterBot_returnsTokenAndConsumer() throws Exception {
        UpdateConsumer consumerMock = mock(UpdateConsumer.class);
        ShelterBot bot = new ShelterBot(consumerMock);

        Field tokenField = ShelterBot.class.getDeclaredField("token");
        tokenField.setAccessible(true);
        tokenField.set(bot, "test-token-123");

        assertEquals("test-token-123", bot.getBotToken());
        assertEquals(consumerMock, bot.getUpdatesConsumer());
    }

    @Test
    void updateConsumer_onStart_sendsHelloAndKeyboard() throws Exception {
        OkHttpTelegramClient telegramClient = mock(OkHttpTelegramClient.class);
        MessageService messageService = mock(MessageService.class);

        UpdateConsumer consumer = new UpdateConsumer(telegramClient, messageService);

        Message message = new Message();
        message.setText("/start");

        Chat chat = mock(Chat.class);
        when(chat.getId()).thenReturn(123L);
        message.setChat(chat);

        Update update = new Update();
        update.setMessage(message);

        consumer.consume(update);

        verify(messageService, times(1)).handleMessage(message);
        verify(messageService, never()).handleCallbackQuery(any());
    }

    @Test
    void updateConsumer_onCallbackQuery_handlesCallback() throws Exception {
        OkHttpTelegramClient telegramClient = mock(OkHttpTelegramClient.class);
        MessageService messageService = mock(MessageService.class);

        UpdateConsumer consumer = new UpdateConsumer(telegramClient, messageService);

        org.telegram.telegrambots.meta.api.objects.CallbackQuery callbackQuery =
                mock(org.telegram.telegrambots.meta.api.objects.CallbackQuery.class);

        Update update = new Update();
        update.setCallbackQuery(callbackQuery);

        consumer.consume(update);

        verify(messageService, times(1)).handleCallbackQuery(callbackQuery);
        verify(messageService, never()).handleMessage(any());
    }

    @Test
    void updateConsumer_constructorWithTelegramClientOnly() throws Exception {
        OkHttpTelegramClient telegramClient = mock(OkHttpTelegramClient.class);

        UpdateConsumer consumer = new UpdateConsumer(telegramClient);
    }
}