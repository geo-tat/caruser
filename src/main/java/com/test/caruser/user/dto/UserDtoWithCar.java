package com.test.caruser.user.dto;

import com.test.caruser.car.dto.CarDtoOutShort;
import com.test.caruser.car.model.Car;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Информация о пользователе со списком машин, их суммарной стоимостью")
public class UserDtoWithCar {
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Список машин")
    private List<CarDtoOutShort> cars;
    @Schema(description = "Суммарная стоимость машин")
    private Double sumCost;
}
