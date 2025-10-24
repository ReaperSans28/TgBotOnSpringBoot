package com.zoo.TgBotOnSpringBoot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.zoo.TgBotOnSpringBoot.service.AnimalService;

@RestController
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }
}
