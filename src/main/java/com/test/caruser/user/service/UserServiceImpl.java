package com.test.caruser.user.service;

import com.test.caruser.car.repository.CarRepository;
import com.test.caruser.car.model.Car;
import com.test.caruser.car.util.CarMapper;
import com.test.caruser.exception.EmailNotUniqueException;
import com.test.caruser.exception.EntityNotFoundException;
import com.test.caruser.user.UserRepository;
import com.test.caruser.user.dto.UserDtoIn;
import com.test.caruser.user.dto.UserDtoOut;
import com.test.caruser.user.dto.UserDtoWithCar;
import com.test.caruser.user.model.User;
import com.test.caruser.user.util.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final CarRepository carRepository;

    @Override
    @Transactional
    public UserDtoOut createUser(UserDtoIn userDtoIn) {
        if(!isEmailUnique(userDtoIn.getEmail())) {
            throw new EmailNotUniqueException("Пользователь с таким почтовым адресом уже существует");
        }
        User user = UserMapper.toEntity(userDtoIn);
        return UserMapper.toDto(repository.save(user));
    }

    @Override
    public UserDtoOut getUserById(long id) {
        User user = repository.findById(id).stream()
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + id + " не найден"));
        return UserMapper.toDto(user);
    }

    @Override
    public UserDtoOut updateUser(UserDtoIn userDtoIn, Long userId) {
        User userToUpdate = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + userId + " не найден"));
        userToUpdate.setEmail(userDtoIn.getEmail());
        userToUpdate.setFirstName(userDtoIn.getFirstName());
        userToUpdate.setLastName(userDtoIn.getLastName());
        return UserMapper.toDto(repository.save(userToUpdate));
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        carRepository.deleteAllByUserId(id);
        log.info("Удалены машины пользователя ID= {}", id);
        User usertoDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + id + " не найден"));
        repository.deleteById(id);
        log.info("Пользователь c ID=" + id + " удален");
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        repository.deleteAll();
        log.info("Все пользователи удалены");
        carRepository.deleteAll();
        log.info("Все машины удалены");
    }

    @Override
    @Transactional(readOnly = true)
    public UserDtoWithCar getUserCars(long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + id + " не найден"));
        Collection<Car> cars = carRepository.findAllByUserId(id);
        double sumCost = countSumCost(cars);
        UserDtoWithCar userWithCars = UserMapper.toUserDtoWithCar(user);
        userWithCars.setCars(cars.stream().map(CarMapper::toShort).collect(Collectors.toList()));
        userWithCars.setSumCost(sumCost);
        return userWithCars;
    }

    private double countSumCost(Collection<Car> cars) {
        return cars.stream()
                .mapToDouble(Car::getPrice)
                .sum();
    }

    private boolean isEmailUnique(String email) {
        User existingUser = repository.findByEmail(email);
        return existingUser == null;
    }

}
