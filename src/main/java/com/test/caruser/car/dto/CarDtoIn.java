package com.test.caruser.car.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Информация о машине на вход")
public class CarDtoIn {
    @NotNull
    @Schema(description = "Название")
    private String name;
    @NotNull
    @Schema(description = "Цена")
    private double price;

}
