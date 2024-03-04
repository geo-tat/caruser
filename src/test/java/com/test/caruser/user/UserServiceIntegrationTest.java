package com.test.caruser.user;

import com.test.caruser.CaruserApplication;
import com.test.caruser.user.dto.UserDtoIn;
import com.test.caruser.user.dto.UserDtoOut;
import com.test.caruser.user.model.User;
import com.test.caruser.user.service.UserServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringJUnitConfig({CaruserApplication.class, UserServiceImpl.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIntegrationTest {

    private final EntityManager em;
    private final UserServiceImpl userService;


    @Test
    @Transactional
    void createUser() {
        UserDtoIn user = UserDtoIn.builder()
                .firstName("Harrison")
                .lastName("Ford")
                .email("ford@test.com")
                .build();

        UserDtoOut out = userService.createUser(user);

        TypedQuery<User> query = em.createQuery("Select u from User u where u.id = :id", User.class);
        User queryUser = query
                .setParameter("id", out.getId())
                .getSingleResult();
        assertEquals(1, queryUser.getId());

    }


    @Test
    @Transactional
    void testDeleteUser() {
        UserDtoIn user = UserDtoIn.builder()
                .firstName("Harrison")
                .lastName("Ford")
                .email("ford@test.com")
                .build();


        UserDtoOut out = userService.createUser(user);
        em.flush();

        TypedQuery<User> queryBeforeDelete = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        User existingUserBeforeDelete = queryBeforeDelete.setParameter("id", out.getId()).getSingleResult();
        assertNotNull(existingUserBeforeDelete);


        userService.deleteUserById(1L);

        TypedQuery<User> queryAfterDelete = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        User existingUserAfterDelete = queryAfterDelete.setParameter("id", out.getId()).getResultStream().findFirst().orElse(null);
        assertNull(existingUserAfterDelete);
    }

}



