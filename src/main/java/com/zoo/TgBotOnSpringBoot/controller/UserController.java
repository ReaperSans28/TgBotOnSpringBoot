package com.zoo.TgBotOnSpringBoot.controller;

import com.zoo.TgBotOnSpringBoot.model.User;
import com.zoo.TgBotOnSpringBoot.service.UserService;
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
 * Контроллер для управления пользователями системы.
 * Предоставляет REST API для работы с пользовательскими данными.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "API для управления пользователями")
public class UserController {
    private final UserService userService;

    /**
     * Конструктор контроллера пользователей.
     *
     * @param userService сервис для работы с пользовательскими данными
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает информацию о пользователе по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<User> getUserById(@Parameter(description = "ID пользователя") @PathVariable Long id) {
        Optional<User> user = userService.findUser(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать нового пользователя", description = "Добавляет нового пользователя в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    })
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя", description = "Обновляет информацию о существующем пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<User> updateUser(@Parameter(description = "ID пользователя") @PathVariable Long id, 
                                         @RequestBody User user) {
        user.setUserId(id);
        User updatedUser = userService.editUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя из системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID пользователя") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/volunteers")
    @Operation(summary = "Получить список волонтеров", description = "Возвращает список всех волонтеров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список волонтеров получен")
    })
    public ResponseEntity<List<User>> getVolunteers() {
        List<User> volunteers = userService.getAllVolonteers();
        return ResponseEntity.ok(volunteers);
    }

    @GetMapping("/telegram/{chatId}")
    @Operation(summary = "Найти пользователя по Telegram ID", description = "Возвращает пользователя по его Telegram chat ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<User> getUserByTelegramId(@Parameter(description = "Telegram chat ID") @PathVariable Long chatId) {
        Optional<User> user = userService.findByUserTgId(chatId);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}