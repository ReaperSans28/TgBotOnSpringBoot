package com.zoo.TgBotOnSpringBoot.controller;

import com.zoo.TgBotOnSpringBoot.model.Shelter;
import com.zoo.TgBotOnSpringBoot.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Контроллер для управления приютами для животных.
 * Содержит endpoints для операций с данными приютов.
 */
@RestController
@RequestMapping("/api/shelters")
@Tag(name = "Shelter Management", description = "API для управления приютами")
public class ShelterController {
    private final ShelterService shelterService;

    /**
     * Конструктор контроллера приютов.
     *
     * @param shelterService сервис для работы с данными приютов
     */
    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить приют по ID", description = "Возвращает информацию о приюте по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приют найден"),
            @ApiResponse(responseCode = "404", description = "Приют не найден")
    })
    public ResponseEntity<Shelter> getShelterById(@Parameter(description = "ID приюта") @PathVariable Long id) {
        Optional<Shelter> shelter = shelterService.findshelter(id);
        return shelter.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новый приют", description = "Добавляет новый приют в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Приют успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные приюта")
    })
    public ResponseEntity<Shelter> createShelter(@RequestBody Shelter shelter) {
        Shelter savedShelter = shelterService.addshelter(shelter);
        return ResponseEntity.ok(savedShelter);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить приют", description = "Обновляет информацию о существующем приюте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приют успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Приют не найден")
    })
    public ResponseEntity<Shelter> updateShelter(@Parameter(description = "ID приюта") @PathVariable Long id, 
                                                @RequestBody Shelter shelter) {
        shelter.setShelterId(id);
        Shelter updatedShelter = shelterService.editshelter(shelter);
        return ResponseEntity.ok(updatedShelter);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить приют", description = "Удаляет приют из системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Приют успешно удален"),
            @ApiResponse(responseCode = "404", description = "Приют не найден")
    })
    public ResponseEntity<Void> deleteShelter(@Parameter(description = "ID приюта") @PathVariable Long id) {
        shelterService.deleteshelter(id);
        return ResponseEntity.noContent().build();
    }
}