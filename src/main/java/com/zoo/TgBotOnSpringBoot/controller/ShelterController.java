package com.zoo.TgBotOnSpringBoot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.zoo.TgBotOnSpringBoot.service.ShelterService;

@RestController
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }
}
