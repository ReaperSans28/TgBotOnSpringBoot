package com.zoo.TgBotOnSpringBoot.controller;

import com.zoo.TgBotOnSpringBoot.model.DailyReport;
import com.zoo.TgBotOnSpringBoot.service.DailyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Контроллер для работы с ежедневными отчетами.
 * Предоставляет API для управления отчетами о животных.
 */
@RestController
@RequestMapping("/api/daily-reports")
@Tag(name = "Daily Report Management", description = "API для управления ежедневными отчетами")
public class DailyReportController {
    private final DailyReportService dailyReportService;

    /**
     * Конструктор контроллера ежедневных отчетов.
     *
     * @param dailyReportService сервис для работы с ежедневными отчетами
     */
    public DailyReportController(DailyReportService dailyReportService) {
        this.dailyReportService = dailyReportService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить отчет по ID", description = "Возвращает информацию об отчете по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отчет найден"),
            @ApiResponse(responseCode = "404", description = "Отчет не найден")
    })
    public ResponseEntity<DailyReport> getDailyReportById(@Parameter(description = "ID отчета") @PathVariable Long id) {
        Optional<DailyReport> report = dailyReportService.findDailyReport(id);
        return report.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новый отчет", description = "Добавляет новый ежедневный отчет в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Отчет успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные отчета")
    })
    public ResponseEntity<DailyReport> createDailyReport(@RequestBody DailyReport dailyReport) {
        DailyReport savedReport = dailyReportService.addDailyReport(dailyReport);
        return ResponseEntity.ok(savedReport);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить отчет", description = "Обновляет информацию о существующем отчете")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отчет успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Отчет не найден")
    })
    public ResponseEntity<DailyReport> updateDailyReport(@Parameter(description = "ID отчета") @PathVariable Long id, 
                                                        @RequestBody DailyReport dailyReport) {
        dailyReport.setReportId(id);
        DailyReport updatedReport = dailyReportService.editDailyReport(dailyReport);
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отчет", description = "Удаляет отчет из системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Отчет успешно удален"),
            @ApiResponse(responseCode = "404", description = "Отчет не найден")
    })
    public ResponseEntity<Void> deleteDailyReport(@Parameter(description = "ID отчета") @PathVariable Long id) {
        dailyReportService.deleteDailyReport(id);
        return ResponseEntity.noContent().build();
    }
}