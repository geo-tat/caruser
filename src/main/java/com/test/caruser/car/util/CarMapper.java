package com.test.caruser.car.util;

import com.test.caruser.car.dto.CarDtoIn;
import com.test.caruser.car.dto.CarDtoOut;
import com.test.caruser.car.dto.CarDtoOutShort;
import com.test.caruser.car.model.Car;

public class CarMapper {

    public static Car toEntity(CarDtoIn carDtoIn) {
        return Car.builder()
                .name(carDtoIn.getName())
                .price(carDtoIn.getPrice())
                .build();
    }

    public static CarDtoOut toDto(Car car) {
        return CarDtoOut.builder()
                .id(car.getId())
                .name(car.getName())
                .price(car.getPrice())
                .userId(car.getUser().getId())
                .build();
    }

    public static CarDtoOutShort toShort(Car car) {
        return CarDtoOutShort.builder()
                .name(car.getName())
                .price(car.getPrice())
                .build();
    }
}
