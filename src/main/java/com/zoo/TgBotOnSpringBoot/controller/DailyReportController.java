package com.zoo.TgBotOnSpringBoot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.zoo.TgBotOnSpringBoot.service.DailyReportService;

@RestController
public class DailyReportController {
    private final DailyReportService dailyReportService;

    public DailyReportController(DailyReportService dailyReportService) {
        this.dailyReportService = dailyReportService;
    }
}
