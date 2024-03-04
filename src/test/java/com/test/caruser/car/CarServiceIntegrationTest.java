package com.test.caruser.car;

import com.test.caruser.CaruserApplication;
import com.test.caruser.car.dto.CarDtoIn;
import com.test.caruser.car.dto.CarDtoOut;
import com.test.caruser.car.model.Car;
import com.test.caruser.car.service.CarServiceImpl;
import com.test.caruser.user.dto.UserDtoIn;
import com.test.caruser.user.service.UserServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringJUnitConfig({CaruserApplication.class, CarServiceImpl.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarServiceIntegrationTest {
    private final EntityManager em;
    private final UserServiceImpl userService;
    private final CarServiceImpl carService;

    private UserDtoIn user;
    private CarDtoIn car;

    @BeforeEach
    void setUp() {
        user = UserDtoIn.builder()
                .firstName("Harrison")
                .lastName("Ford")
                .email("ford@test.com")
                .build();

        car = CarDtoIn.builder()
                .name("Test car")
                .price(10000.0)
                .build();

    }

    @Transactional
    @Test
    void createCar() {

        userService.createUser(user);
        CarDtoOut result = carService.createCar(1L, car);

        TypedQuery<Car> query = em.createQuery("Select c from Car c where c.id = :id", Car.class);
        Car queryCar = query
                .setParameter("id", result.getId())
                .getSingleResult();
        assertEquals(1, queryCar.getId());

    }

    @Test
    @Transactional
    void deleteCar() {
        userService.createUser(user);
        CarDtoOut result = carService.createCar(1L, car);

        TypedQuery<Car> queryBeforeDelete = em.createQuery("SELECT c FROM Car c WHERE c.id = :id", Car.class);
        Car existingCarBeforeDelete = queryBeforeDelete.setParameter("id", result.getId()).getSingleResult();
        assertNotNull(existingCarBeforeDelete);

        carService.deleteCarById(1L, 1L);

        TypedQuery<Car> queryAfterDelete = em.createQuery("SELECT c FROM Car c WHERE c.id = :id", Car.class);
        Car existingCarAfterDelete = queryAfterDelete.setParameter("id", result.getId()).getResultStream().findFirst().orElse(null);
        assertNull(existingCarAfterDelete);


    }

}
