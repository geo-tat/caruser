package com.test.caruser.user.service;

import com.test.caruser.user.dto.UserDtoIn;
import com.test.caruser.user.dto.UserDtoOut;
import com.test.caruser.user.dto.UserDtoWithCar;


public interface UserService {

    UserDtoOut createUser(UserDtoIn userDtoIn);

    UserDtoOut getUserById(long id);

    UserDtoOut updateUser(UserDtoIn userDtoIn, Long userId);

    void deleteUserById(long id);

    void deleteAllUsers();

    UserDtoWithCar getUserCars(long id);
}
