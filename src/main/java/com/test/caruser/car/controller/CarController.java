package com.test.caruser.car.controller;

import com.test.caruser.car.dto.CarDtoIn;
import com.test.caruser.car.dto.CarDtoOut;
import com.test.caruser.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users/{userId}/cars")
@AllArgsConstructor
@Tag(name = "Машины", description = "Взаимодействие с машинами")
public class CarController {
    private final CarService service;

    @Operation(summary = "Регистрация машины")
    @PostMapping
    CarDtoOut createCar(@PathVariable Long userId, @Valid @RequestBody CarDtoIn carDtoIn) {
        return service.createCar(userId, carDtoIn);
    }

    @Operation(summary = "Обновление информации о машине")
    @PatchMapping("/{carId}")
    CarDtoOut updateCar(@PathVariable Long userId, @Valid @RequestBody CarDtoIn carDtoIn, @PathVariable Long carId) {
        return service.updateCar(userId, carId, carDtoIn);
    }

    @Operation(summary = "Получить информацию о машине по Id")
    @GetMapping("/{carId}")
    CarDtoOut getCarById(@PathVariable Long userId, @PathVariable Long carId) {
        return service.getById(userId, carId);
    }

    @Operation(summary = "Удалить информацию о машине")
    @DeleteMapping("/{carId}")
    void deleteCar(@PathVariable Long userId, @PathVariable Long carId) {
        service.deleteCarById(userId, carId);
    }

    @Operation(summary = "Удалить всю информацию о машинах по Id пользователя")
    @DeleteMapping
    void deleteAllCars(@PathVariable Long userId) {
        service.deleteAllCars(userId);
    }
}
