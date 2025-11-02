package com.zoo.TgBotOnSpringBoot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

/**
 * Сервис для создания инлайн-клавиатур Telegram бота
 *
 * <p>Создает различные варианты клавиатур для взаимодействия с пользователем</p>
 */
@Service
public class KeyboardService {
        /**
        * Создает клавиатуру с опциями для приюта собак
        *
        * @return клавиатура с кнопками информации об приюте для собак
        */
        public InlineKeyboardMarkup shelterInfoKeyboard() {
        InlineKeyboardButton infoButton = InlineKeyboardButton.builder()
                .text("Узнать побольше о приюте")
                .callbackData("shelterInfo")
                .build();
        InlineKeyboardButton adoptButton = InlineKeyboardButton.builder()
                .text("Как взять животное?")
                .callbackData("adopt")
                .build();
        InlineKeyboardButton reportButton = InlineKeyboardButton.builder()
                .text("Прислать отчёт")
                .callbackData("report")
                .build();
        InlineKeyboardButton volunteerButton = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();

        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(infoButton),
                new InlineKeyboardRow(adoptButton),
                new InlineKeyboardRow(reportButton),
                new InlineKeyboardRow(volunteerButton)
        );

        return new InlineKeyboardMarkup(keyboardRows);
        }

        public InlineKeyboardMarkup HowToAdopt() {
                InlineKeyboardButton animalList = InlineKeyboardButton.builder()
                .text("Список доступных для усыновления животных")
                .callbackData("animalList")
                .build();
        InlineKeyboardButton userContactDetails = InlineKeyboardButton.builder()
                .text("Записать мои контактные данные")
                .callbackData("userContactDetails")
                .build();
        InlineKeyboardButton volunteerButton = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();

        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(animalList),
                new InlineKeyboardRow(userContactDetails),
                new InlineKeyboardRow(volunteerButton)
        );

        return new InlineKeyboardMarkup(keyboardRows);
        }

        public InlineKeyboardMarkup aboutShelter() {
        InlineKeyboardButton userContactDetails = InlineKeyboardButton.builder()
                .text("Записать мои контактные данные")
                .callbackData("userContactDetails")
                .build();
        InlineKeyboardButton volunteerButton = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
        
        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(userContactDetails),
                new InlineKeyboardRow(volunteerButton)
        );

        return new InlineKeyboardMarkup(keyboardRows);
        }

        /**
        * Создает кнопку для экстренного вызова волонтера
        *
        * @return клавиатура с одной кнопкой вызова волонтера
        */
        public InlineKeyboardMarkup emergencyButton() {
        InlineKeyboardButton volunteerButton = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();

        List<InlineKeyboardRow> keyboardRow = List.of(new InlineKeyboardRow(volunteerButton));
        return new InlineKeyboardMarkup(keyboardRow);
        }
}