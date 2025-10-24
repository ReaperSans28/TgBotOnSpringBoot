package com.zoo.TgBotOnSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoo.TgBotOnSpringBoot.model.DailyReport;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    
}
