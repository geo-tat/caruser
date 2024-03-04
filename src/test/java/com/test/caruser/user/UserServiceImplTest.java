package com.test.caruser.user;

import com.test.caruser.car.model.Car;
import com.test.caruser.car.repository.CarRepository;
import com.test.caruser.exception.EmailNotUniqueException;
import com.test.caruser.exception.EntityNotFoundException;
import com.test.caruser.user.dto.UserDtoIn;
import com.test.caruser.user.dto.UserDtoOut;
import com.test.caruser.user.dto.UserDtoWithCar;
import com.test.caruser.user.model.User;
import com.test.caruser.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    private User user;

    private Car car;
    private Car car2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        car = Car.builder()
                .id(1L)
                .name("Car")
                .price(10000.0)
                .user(user)
                .build();

        car2 = Car.builder()
                .id(2L)
                .name("Car2")
                .price(20000.0)
                .user(user)
                .build();

    }

    @Test
    void testCreateUser() {
        UserDtoIn userDtoIn = UserDtoIn.builder().firstName("John").lastName("Doe").email("john.doe@example.com").build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDtoOut result = service.createUser(userDtoIn);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        User user = User.builder().id(1L).firstName("John").lastName("Doe").email("john.doe@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDtoOut result = service.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getUserById(1L));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        UserDtoIn userDtoIn = UserDtoIn.builder().firstName("John").lastName("Doe").email("john.doe@example.com").build();
        User existingUser = User.builder().id(1L).firstName("John").lastName("Doe").email("john.doe@example.com").build();
        User updatedUser = User.builder().id(1L).firstName("Jane").lastName("Smith").email("jane.smith@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDtoOut result = service.updateUser(userDtoIn, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("jane.smith@example.com", result.getEmail());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUserById() {
        User user = User.builder().id(1L).firstName("John").lastName("Doe").email("john.doe@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        service.deleteUserById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deleteUserById(1L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void testDeleteAllUsers() {
        service.deleteAllUsers();

        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    void testGetUserCars() {

        Collection<Car> cars = new ArrayList<>();
        cars.add(car);
        cars.add(car2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carRepository.findAllByUserId(1L)).thenReturn(cars);

        UserDtoWithCar result = service.getUserCars(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(2, result.getCars().size());
        assertEquals(30000.0, result.getSumCost());

        verify(userRepository, times(1)).findById(1L);
        verify(carRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void emailNotUniqueTest() {
        User existingUser = User.builder()
                .id(1)
                .email("test@test.com")
                .firstName("Ron")
                .build();

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(existingUser);

        assertThrows(EmailNotUniqueException.class, () -> service.createUser(UserDtoIn.builder()
                .email("test@test.com")
                .firstName("Andy")
                .build()));
    }
}