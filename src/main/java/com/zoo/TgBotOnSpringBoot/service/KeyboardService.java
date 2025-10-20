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
     * Создает клавиатуру для выбора типа приюта
     *
     * @return клавиатура с кнопками выбора приюта для кошек или собак
     */
    public InlineKeyboardMarkup shelterSelectionKeyboard() {
        InlineKeyboardButton catButton = InlineKeyboardButton.builder()
                .text("Приют для кошек")
                .callbackData("catShelter")
                .build();
        InlineKeyboardButton dogButton = InlineKeyboardButton.builder()
                .text("Приют для собак")
                .callbackData("dogShelter")
                .build();
        InlineKeyboardButton volunteerButton = InlineKeyboardButton.builder()
                .text("Я - волонтёр")
                .callbackData("volonteerReg")
                .build();
        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(catButton),
                new InlineKeyboardRow(dogButton),
                new InlineKeyboardRow(volunteerButton)
        );

        return new InlineKeyboardMarkup(keyboardRows);
    }

    /**
     * Создает клавиатуру с опциями для приюта собак
     *
     * @return клавиатура с кнопками информации об приюте для собак
     */
    public InlineKeyboardMarkup dogShelterInfoKeyboard() {
        InlineKeyboardButton infoButton = InlineKeyboardButton.builder()
                .text("Узнать побольше о приюте")
                .callbackData("dogShelterInfo")
                .build();
        InlineKeyboardButton adoptButton = InlineKeyboardButton.builder()
                .text("Как взять животное?")
                .callbackData("adoptDog")
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

    /**
     * Создает клавиатуру с опциями для приюта кошек
     *
     * @return клавиатура с кнопками информации об приюте для кошек
     */
    public InlineKeyboardMarkup catShelterInfoKeyboard() {
        InlineKeyboardButton infoButton = InlineKeyboardButton.builder()
                .text("Узнать побольше о приюте")
                .callbackData("catShelterInfo")
                .build();
        InlineKeyboardButton adoptButton = InlineKeyboardButton.builder()
                .text("Как взять животное?")
                .callbackData("adoptCat")
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