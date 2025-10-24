package com.zoo.TgBotOnSpringBoot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.zoo.TgBotOnSpringBoot.service.AdopterService;

@RestController
public class AdopterController {
    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }
}
