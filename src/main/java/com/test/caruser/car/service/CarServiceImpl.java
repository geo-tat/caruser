package com.test.caruser.car.service;

import com.test.caruser.car.repository.CarRepository;
import com.test.caruser.car.dto.CarDtoIn;
import com.test.caruser.car.dto.CarDtoOut;
import com.test.caruser.car.model.Car;
import com.test.caruser.car.util.CarMapper;
import com.test.caruser.exception.EntityNotFoundException;
import com.test.caruser.user.UserRepository;
import com.test.caruser.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository repository;
    private final UserRepository userRepository;

    @Override
    public CarDtoOut createCar(long userId, CarDtoIn carDtoIn) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + userId + " не найден"));
        Car car = CarMapper.toEntity(carDtoIn);
        car.setUser(user);
        return CarMapper.toDto(repository.save(car));
    }

    @Override
    public CarDtoOut updateCar(long userId, long carId, CarDtoIn carDtoIn) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + userId + " не найден"));
        Car carToUpdate = repository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Машина c ID=" + carId + " не найдена"));
        carToUpdate.setName(carDtoIn.getName());
        carToUpdate.setPrice(carDtoIn.getPrice());
        return CarMapper.toDto(repository.save(carToUpdate));
    }

    @Override
    public void deleteCarById(long userId, long carId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + userId + " не найден"));
        Car carToDelete = repository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Машина c ID=" + carId + " не найдена"));

        repository.deleteById(carId);

    }

    @Override
    @Transactional
    public void deleteAllCars(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + userId + " не найден"));
        repository.deleteAllByUserId(userId);
    }

    @Override
    public CarDtoOut getById(long userId, long carId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь c ID=" + userId + " не найден"));
        Car car = repository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Машина c ID=" + carId + " не найдена"));
        return CarMapper.toDto(car);
    }

}
