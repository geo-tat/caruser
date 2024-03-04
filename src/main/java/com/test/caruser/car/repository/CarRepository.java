package com.test.caruser.car.repository;

import com.test.caruser.car.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CarRepository extends JpaRepository<Car, Long> {

    void deleteAllByUserId(long userId);

    Collection<Car> findAllByUserId(long id);
}
