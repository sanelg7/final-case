package com.definex.practicum.finalcase.user.unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.impl.UserServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserGetTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsers() {
        // create some sample users
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        // set up the userRepository mock
        when(userRepository.findAll()).thenReturn(users);

        // call the method being tested
        List<User> returnedUsers = userService.getUsers();

        // assert the result
        assertEquals(users, returnedUsers);
    }

    @Test
    public void testGetUserById() throws EntityNotFoundException {
        // create a sample user
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setId(userId);

        // set up the userRepository mock
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.existsById(userId)).thenReturn(true);

        // call the method being tested
        User returnedUser = userService.getUserById(userId);

        // assert the result
        assertEquals(user, returnedUser);
    }

    @Test
    public void testGetUserByIdWithInvalidId() {
        // set up the userRepository mock
        UUID invalidId = UUID.randomUUID();
        when(userRepository.existsById(invalidId)).thenReturn(false);

        // call the method being tested and assert that it throws EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(invalidId));
    }

    @Test
    public void testGetUserByTckn() throws EntityNotFoundException {
        // create a user with the given TCKN
        String tckn = "12345678901";
        User user = new User(tckn, "John", "Doe", "5551234567", new Date(90, 0, 1));
        when(userRepository.existsByTckn(tckn)).thenReturn(true);
        when(userRepository.findByTckn(tckn)).thenReturn(Optional.of(user));

        // call the method being tested
        User retrievedUser = userService.getUserByTckn(tckn);

        // assert the result
        assertNotNull(retrievedUser);
        assertEquals(tckn, retrievedUser.getTckn());
        assertEquals("John", retrievedUser.getFirstName());
        assertEquals("Doe", retrievedUser.getLastName());
        assertEquals("5551234567", retrievedUser.getGsmNumber());
        assertEquals(new Date(90, 0, 1), retrievedUser.getDateOfBirth());
    }

}
