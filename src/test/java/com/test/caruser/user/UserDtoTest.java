package com.test.caruser.user;

import com.test.caruser.user.dto.UserDtoIn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class UserDtoTest {
    @Autowired
    private JacksonTester<UserDtoIn> json;

    @Test
    void testUserDtoIn() throws Exception {
        UserDtoIn dto = UserDtoIn.builder()
                .firstName("Alex")
                .lastName("Crow")
                .email("test@test.com")
                .build();

        JsonContent<UserDtoIn> result = json.write(dto);

        assertThat(result).extractingJsonPathStringValue("$.firstName").isEqualTo("Alex");
        assertThat(result).extractingJsonPathStringValue("$.lastName").isEqualTo("Crow");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("test@test.com");


    }
}
