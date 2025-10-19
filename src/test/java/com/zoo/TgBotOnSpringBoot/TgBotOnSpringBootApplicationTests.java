package com.zoo.TgBotOnSpringBoot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.zoo.TgBotOnSpringBoot.bot.ShelterBot;
import com.zoo.TgBotOnSpringBoot.listener.UpdateConsumer;

import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

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
		UpdateConsumer consumer = new UpdateConsumer();

		TelegramClient telegramClient = mock(TelegramClient.class);
		when(telegramClient.execute(any(SendMessage.class))).thenReturn(null);

		Field clientField = UpdateConsumer.class.getDeclaredField("telegramClient");
		clientField.setAccessible(true);
		clientField.set(consumer, telegramClient);

		Message message = new Message();
		message.setText("/start");

		Chat chat = mock(Chat.class);
		when(chat.getId()).thenReturn(123L);
		message.setChat(chat);

		Update update = new Update();
		update.setMessage(message);

		consumer.consume(update);

		ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
		verify(telegramClient, times(2)).execute(captor.capture());

		List<SendMessage> sent = captor.getAllValues();
		SendMessage hello = sent.get(0);
		SendMessage choose = sent.get(1);

		assertEquals("/start", message.getText());
		assertEquals("123", hello.getChatId()); // Обратите внимание: getChatId() возвращает String
		assertEquals("123", choose.getChatId()); // Обратите внимание: getChatId() возвращает String
		assertEquals("Выберите приют", choose.getText());

		InlineKeyboardMarkup markup = (InlineKeyboardMarkup) choose.getReplyMarkup();
		List<InlineKeyboardRow> rows = markup.getKeyboard();
		assertEquals(2, rows.size());
		InlineKeyboardButton b1 = rows.get(0).get(0);
		InlineKeyboardButton b2 = rows.get(1).get(0);
		assertEquals("Приют для кошек", b1.getText());
		assertEquals("catShelter", b1.getCallbackData());
		assertEquals("Приют для собак", b2.getText());
		assertEquals("dogShelter", b2.getCallbackData());
	}

	private Chat createChat(Long chatId) throws Exception {
		Chat chat = Chat.class.newInstance();

		Field idField = Chat.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(chat, chatId);

		return chat;
	}
}