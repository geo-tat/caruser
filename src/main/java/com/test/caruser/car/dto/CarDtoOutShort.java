package com.test.caruser.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Минимальная информация о машине")
public class CarDtoOutShort {
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Цена")
    private Double price;
}
