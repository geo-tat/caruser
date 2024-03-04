package com.test.caruser.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Информация о пользователе на выход")
public class UserDtoOut {
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Почта")
    private String email;
    @Schema(description = "Персональный идентификатор")
    private Long id;
}




