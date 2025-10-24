package com.zoo.TgBotOnSpringBoot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.zoo.TgBotOnSpringBoot.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
