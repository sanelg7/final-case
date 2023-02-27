package com.definex.practicum.finalcase.user.integration;

import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@Transactional
public class UserDeleteTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testDeleteUser() {
        // create a new user
        User user = userRepository.findFirstByOrderById();

        // delete the user
        userService.deleteUser(user.getId());

        // assert the result
        assertFalse(userRepository.existsById(user.getId()));
    }
}
