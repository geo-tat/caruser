package com.test.caruser.car.service;

import com.test.caruser.car.dto.CarDtoIn;
import com.test.caruser.car.dto.CarDtoOut;

public interface CarService {
    CarDtoOut createCar(long userId, CarDtoIn carDtoIn);

    CarDtoOut updateCar(long userId, long carId, CarDtoIn carDtoIn);

    void deleteCarById(long userId, long carId);

    void deleteAllCars(long userId);

    CarDtoOut getById(long userId, long carId);


}
