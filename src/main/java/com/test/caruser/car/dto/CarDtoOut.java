package com.test.caruser.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Информация о машине на выход")
public class CarDtoOut {
    @Schema(description = "id машины")
    private Long id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Цена")
    private Double price;
    @Schema(description = "id владельца")
    private Long userId;

}
