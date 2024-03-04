package com.test.caruser.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.caruser.car.controller.CarController;
import com.test.caruser.car.dto.CarDtoIn;
import com.test.caruser.car.dto.CarDtoOut;
import com.test.caruser.car.service.CarServiceImpl;
import com.test.caruser.exception.EntityNotFoundException;
import com.test.caruser.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarServiceImpl carService;

    private CarDtoOut carDtoOut;
    private CarDtoIn carDtoIn;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("Test")
                .email("test@test.com")
                .build();

        carDtoOut = CarDtoOut.builder()
                .id(1L)
                .name("Test car")
                .price(10000.0)
                .build();

        carDtoIn = CarDtoIn.builder()
                .name("Test car")
                .price(10000.0)
                .build();
    }

    @Test
    void testGetCarById() throws Exception {
        when(carService.getById(1L, 1L)).thenReturn(carDtoOut);

        mockMvc.perform(get("/users/1/cars/{carId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test car"))
                .andExpect(jsonPath("$.price").value(10000.0));
    }

    @Test
    void testCreateCar() throws Exception {
        when(carService.createCar(anyLong(), any(CarDtoIn.class))).thenReturn(carDtoOut);

        mockMvc.perform(post("/users/1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDtoIn)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test car"))
                .andExpect(jsonPath("$.price").value(10000.0));
    }

    @Test
    void testUpdateCar() throws Exception {
        carDtoIn.setName("Updated car");
        carDtoIn.setPrice(20000.0);

        mockMvc.perform(patch("/users/1/cars/{carId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDtoIn)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(delete("/users/1/cars/{carId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCarById_NotFound() throws Exception {
        when(carService.getById(1L, 1L)).thenThrow(new EntityNotFoundException("Car with id 1 not found"));

        mockMvc.perform(get("/users/1/cars/{carId}", 1L))
                .andExpect(status().isNotFound());
    }
}
