package com.test.caruser.user.controller;

import com.test.caruser.user.dto.UserDtoIn;
import com.test.caruser.user.dto.UserDtoOut;
import com.test.caruser.user.dto.UserDtoWithCar;
import com.test.caruser.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
@Validated
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping
    @Operation(summary = "Регистрация пользователей")
    UserDtoOut createUser(@Valid @RequestBody UserDtoIn userDtoIn) {
        return userService.createUser(userDtoIn);
    }

    @Operation(summary = "Обновление информации о пользователях")
    @PatchMapping("/{id}")
    UserDtoOut updateUser(@Valid @RequestBody UserDtoIn userDtoIn, @PathVariable Long id) {
        return userService.updateUser(userDtoIn, id);
    }

    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping("/{id}")
    UserDtoOut getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @Operation(summary = "Удалить всех пользователей")
    @DeleteMapping
    void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @Operation(summary = "Получить информацию о пользователе и список его машин")
    @GetMapping("/{id}/cars")
    UserDtoWithCar getUserCars(@PathVariable Long id) {
        return userService.getUserCars(id);
    }

}
