package com.test.caruser.user.util;

import com.test.caruser.user.dto.UserDtoIn;
import com.test.caruser.user.dto.UserDtoOut;
import com.test.caruser.user.dto.UserDtoWithCar;
import com.test.caruser.user.model.User;

public class UserMapper {

    public static User toEntity(UserDtoIn userDtoIn) {
        return User.builder()
                .firstName(userDtoIn.getFirstName())
                .lastName(userDtoIn.getLastName())
                .email(userDtoIn.getEmail())
                .build();
    }

    public static UserDtoOut toDto(User user) {
        return UserDtoOut.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public static UserDtoWithCar toUserDtoWithCar(User user) {
        return UserDtoWithCar.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
