package com.test.caruser.car;

import com.test.caruser.car.dto.CarDtoIn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class CarDtoTest {

    @Autowired
    private JacksonTester<CarDtoIn> json;

    @Test
    void testCarDtoIn() throws Exception {


        CarDtoIn carDtoIn = CarDtoIn.builder()
                .name("BMW")
                .price(10000.00)
                .build();

        JsonContent<CarDtoIn> result = json.write(carDtoIn);

        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("BMW");
        assertThat(result).extractingJsonPathNumberValue("$.price").isEqualTo(10000.00);
    }
}

