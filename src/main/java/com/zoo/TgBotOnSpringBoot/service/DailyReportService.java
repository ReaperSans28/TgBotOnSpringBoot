package com.zoo.TgBotOnSpringBoot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoo.TgBotOnSpringBoot.model.DailyReport;
import com.zoo.TgBotOnSpringBoot.repository.DailyReportRepository;

@Service
public class DailyReportService {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    public Optional<DailyReport> findDailyReport(long id) {
        return dailyReportRepository.findById(id);
    }

    public DailyReport addDailyReport(DailyReport dailyReport) {
        return dailyReportRepository.save(dailyReport);
    }

    public DailyReport editDailyReport(DailyReport dailyReport) {
        return dailyReportRepository.save(dailyReport);
    }

    public void deleteDailyReport(long id) {
        dailyReportRepository.deleteById(id);
    }
}
