package com.zoo.TgBotOnSpringBoot.controller;

import com.zoo.TgBotOnSpringBoot.model.Animal;
import com.zoo.TgBotOnSpringBoot.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления животными в приюте.
 * Обеспечивает REST endpoints для операций с животными.
 */
@RestController
@RequestMapping("/api/animals")
@Tag(name = "Animal Management", description = "API для управления животными в приюте")
public class AnimalController {
    private final AnimalService animalService;

    /**
     * Конструктор контроллера животных.
     *
     * @param animalService сервис для работы с данными животных
     */
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить животное по ID", description = "Возвращает информацию о животном по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное найдено"),
            @ApiResponse(responseCode = "404", description = "Животное не найдено")
    })
    public ResponseEntity<Animal> getAnimalById(@Parameter(description = "ID животного") @PathVariable Long id) {
        Optional<Animal> animal = animalService.findAnimal(id);
        return animal.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Добавить новое животное", description = "Добавляет новое животное в приют")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Животное успешно добавлено"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные животного")
    })
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        Animal savedAnimal = animalService.addAnimal(animal);
        return ResponseEntity.ok(savedAnimal);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о животном", description = "Обновляет информацию о существующем животном")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Животное не найдено")
    })
    public ResponseEntity<Animal> updateAnimal(@Parameter(description = "ID животного") @PathVariable Long id, 
                                             @RequestBody Animal animal) {
        animal.setAnimalId(id);
        Animal updatedAnimal = animalService.editAnimal(animal);
        return ResponseEntity.ok(updatedAnimal);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить животное", description = "Удаляет животное из системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Животное успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Животное не найдено")
    })
    public ResponseEntity<Void> deleteAnimal(@Parameter(description = "ID животного") @PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Получить животных по статусу", description = "Возвращает список животных с определенным статусом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список животных получен")
    })
    public ResponseEntity<List<Animal>> getAnimalsByStatus(@Parameter(description = "Статус животного") @PathVariable String status) {
        List<Animal> animals = animalService.getAnimalsByStatus(status);
        return ResponseEntity.ok(animals);
    }
}