package com.zoo.TgBotOnSpringBoot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

@Service
public class KeyboardService {
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

    public InlineKeyboardMarkup emergencyButton() {
        InlineKeyboardButton volunteerButton = InlineKeyboardButton.builder()
                .text("Позвать волонтёра")
                .callbackData("callVolonteer")
                .build();
                
        List<InlineKeyboardRow> keyboardRow = List.of(new InlineKeyboardRow(volunteerButton));
        return new InlineKeyboardMarkup(keyboardRow);
    }
}
