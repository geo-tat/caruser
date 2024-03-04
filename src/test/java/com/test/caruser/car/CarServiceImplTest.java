package com.test.caruser.car;

import com.test.caruser.car.dto.CarDtoIn;
import com.test.caruser.car.dto.CarDtoOut;
import com.test.caruser.car.model.Car;
import com.test.caruser.car.repository.CarRepository;
import com.test.caruser.car.service.CarServiceImpl;
import com.test.caruser.exception.EntityNotFoundException;
import com.test.caruser.user.UserRepository;
import com.test.caruser.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private User user;

    private Car car;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).build();
        car = Car.builder().id(1L).name("Test Car").price(10000.0).user(user).build();
    }


    @Test
    void testCreateCar() {
        CarDtoIn carDtoIn = CarDtoIn.builder().build();
        carDtoIn.setName("Test Car");
        carDtoIn.setPrice(10000.0);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        CarDtoOut result = carService.createCar(1L, carDtoIn);

        assertEquals("Test Car", result.getName());
        assertEquals(10000.0, result.getPrice());
        assertEquals(1L, result.getUserId());

        verify(userRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testCreateCarWithInvalidUserId() {
        CarDtoIn carDtoIn = CarDtoIn.builder().build();
        carDtoIn.setName("Test Car");
        carDtoIn.setPrice(10000.0);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.createCar(1L, carDtoIn));

        verify(userRepository, times(1)).findById(anyLong());
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void testUpdateCar() {

        CarDtoIn carDtoIn = CarDtoIn.builder()
                .name("Updated Car")
                .price(20000.0)
                .build();


        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        CarDtoOut result = carService.updateCar(1L, 1L, carDtoIn);

        assertEquals("Updated Car", result.getName());
        assertEquals(20000.0, result.getPrice());

        verify(carRepository, times(1)).findById(anyLong());

        verify(carRepository, times(1)).save(any(Car.class));
    }


    @Test
    void testDeleteCarById() {


        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        carService.deleteCarById(1L, 1L);

        verify(carRepository, times(1)).findById(anyLong());

        verify(carRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteCarByIdWithInvalidCarId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.deleteCarById(1L, 1L));

        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteAllCars() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        carService.deleteAllCars(1L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).deleteAllByUserId(1L);
    }

    @Test
    void testDeleteAllCarsWithInvalidUserId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.deleteAllCars(1L));

        verify(userRepository, times(1)).findById(anyLong());
        verify(carRepository, never()).deleteAllByUserId(anyLong());
    }

    @Test
    void testGetById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        CarDtoOut result = carService.getById(1L, 1L);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetByIdWithInvalidUserId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.getById(1L, 1L));

        verify(userRepository, times(1)).findById(anyLong());
        verify(carRepository, never()).findById(anyLong());
    }

    @Test
    void testGetByIdWithInvalidCarId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.getById(1L, 1L));

        verify(userRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).findById(anyLong());
    }
}