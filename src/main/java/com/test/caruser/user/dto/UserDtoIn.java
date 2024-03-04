package com.test.caruser.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
@Schema(description = "Информация о пользователе на вход")
public class UserDtoIn {
    @NotNull(message = "Необходимо указать имя")
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Email(message = "Почтовый адрес указан с ошибкой")
    @NotNull
    @Schema(description = "Почта")
    private String email;
}
