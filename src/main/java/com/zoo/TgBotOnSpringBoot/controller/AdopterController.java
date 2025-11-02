package com.zoo.TgBotOnSpringBoot.controller;

import com.zoo.TgBotOnSpringBoot.model.Adopter;
import com.zoo.TgBotOnSpringBoot.service.AdopterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Контроллер для управления усыновителями животных.
 * Предоставляет REST API для операций с данными усыновителей.
 */
@RestController
@RequestMapping("/api/adopters")
@Tag(name = "Adopter Management", description = "API для управления усыновителями")
public class AdopterController {
    private final AdopterService adopterService;

    /**
     * Конструктор контроллера усыновителей.
     *
     * @param adopterService сервис для работы с данными усыновителей
     */
    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить усыновителя по ID", description = "Возвращает информацию об усыновителе по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Усыновитель найден"),
            @ApiResponse(responseCode = "404", description = "Усыновитель не найден")
    })
    public ResponseEntity<Adopter> getAdopterById(@Parameter(description = "ID усыновителя") @PathVariable Long id) {
        Optional<Adopter> adopter = adopterService.findAdopter(id);
        return adopter.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать нового усыновителя", description = "Добавляет нового усыновителя в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Усыновитель успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные усыновителя")
    })
    public ResponseEntity<Adopter> createAdopter(@RequestBody Adopter adopter) {
        Adopter savedAdopter = adopterService.addAdopter(adopter);
        return ResponseEntity.ok(savedAdopter);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить усыновителя", description = "Обновляет информацию о существующем усыновителе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Усыновитель успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Усыновитель не найден")
    })
    public ResponseEntity<Adopter> updateAdopter(@Parameter(description = "ID усыновителя") @PathVariable Long id, 
                                                @RequestBody Adopter adopter) {
        adopter.setAdopterId(id);
        Adopter updatedAdopter = adopterService.editAdopter(adopter);
        return ResponseEntity.ok(updatedAdopter);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить усыновителя", description = "Удаляет усыновителя из системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Усыновитель успешно удален"),
            @ApiResponse(responseCode = "404", description = "Усыновитель не найден")
    })
    public ResponseEntity<Void> deleteAdopter(@Parameter(description = "ID усыновителя") @PathVariable Long id) {
        adopterService.deleteAdopter(id);
        return ResponseEntity.noContent().build();
    }
}