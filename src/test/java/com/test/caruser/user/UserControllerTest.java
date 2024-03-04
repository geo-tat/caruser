package com.test.caruser.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.caruser.user.controller.UserController;
import com.test.caruser.user.dto.UserDtoIn;
import com.test.caruser.user.dto.UserDtoOut;
import com.test.caruser.user.dto.UserDtoWithCar;
import com.test.caruser.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private UserDtoIn userDtoIn;
    private UserDtoOut userDtoOut;

    @BeforeEach
    void setUp() {
        userDtoIn = UserDtoIn.builder().
                firstName("John")
                .lastName("Watson")
                .email("watson@example.com")
                .build();

        userDtoOut = UserDtoOut.builder()
                .id(1L)
                .firstName("John")
                .lastName("Watson")
                .email("watson@example.com")
                .build();
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(UserDtoIn.class))).thenReturn(userDtoOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDtoIn)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDtoOut.getId()))
                .andExpect(jsonPath("$.firstName").value(userDtoOut.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDtoOut.getLastName()))
                .andExpect(jsonPath("$.email").value(userDtoOut.getEmail()));

        verify(userService, times(1)).createUser(any(UserDtoIn.class));
    }

    @Test
    void createUserInvalidEmailTest() throws Exception {
        UserDtoIn userDtoIn = UserDtoIn.builder()
                .firstName("John")
                .lastName("Watson")
                .email("watsonexamplecom")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("email: Почтовый адрес указан с ошибкой"));
    }

    @Test
    void createUserInvalidNameTest() throws Exception {
        UserDtoIn userDtoIn = UserDtoIn.builder()
                .lastName("Watson")
                .email("watson@example.com")
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("firstName: Необходимо указать имя"));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(userDtoOut);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDtoOut.getId()))
                .andExpect(jsonPath("$.firstName").value(userDtoOut.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDtoOut.getLastName()))
                .andExpect(jsonPath("$.email").value(userDtoOut.getEmail()));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserCars() throws Exception {
        UserDtoWithCar userDtoWithCar = UserDtoWithCar.builder()
                .firstName("John")
                .lastName("Doe")
                .cars(Collections.emptyList())
                .sumCost(0.0)
                .build();

        when(userService.getUserCars(1L)).thenReturn(userDtoWithCar);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(userDtoWithCar.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDtoWithCar.getLastName()))
                .andExpect(jsonPath("$.cars").isEmpty())
                .andExpect(jsonPath("$.sumCost").value(userDtoWithCar.getSumCost()));

        verify(userService, times(1)).getUserCars(1L);
    }
}
