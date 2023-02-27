package com.definex.practicum.finalcase.user.integration;

import com.definex.practicum.finalcase.dto.AdminCreateUpdateUserRequestDto;
import com.definex.practicum.finalcase.dto.RegisterDto;
import com.definex.practicum.finalcase.exception.EntityCreationException;
import com.definex.practicum.finalcase.model.Role;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.RoleRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserCreateTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testRegisterNewUser() {
        // create a new register dto
        RegisterDto registerDto = new RegisterDto();
        registerDto.setTckn("99999999999");
        registerDto.setFirstName("John");
        registerDto.setLastName("Doe");
        registerDto.setGsmNumber("8888888888");
        registerDto.setDateOfBirth(new Date(90, 0, 1));
        registerDto.setPassword("password");

        // call the method being tested
        User registeredUser = userService.register(registerDto);

        // assert the result
        assertNotNull(registeredUser);
        assertEquals("99999999999", registeredUser.getTckn());
        assertEquals("John", registeredUser.getFirstName());
        assertEquals("Doe", registeredUser.getLastName());
        assertEquals("8888888888", registeredUser.getGsmNumber());
        assertTrue(registeredUser.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()).contains("USER"));
        assertEquals(new Date(90, 0, 1), registeredUser.getDateOfBirth());
        assertTrue(passwordEncoder.matches(registerDto.getPassword(), registeredUser.getPassword()));
    }

    @Test
    public void testCreateNewUser() throws EntityCreationException {
        // Create a RegisterDto object
        RegisterDto registerDto = new RegisterDto();
        registerDto.setTckn("99999999999");
        registerDto.setFirstName("John");
        registerDto.setLastName("Doe");
        registerDto.setGsmNumber("8888888888");
        registerDto.setDateOfBirth(new Date(90, 0, 1));
        registerDto.setPassword("password");

        // Create a Role object for the creation.
        Role role = new Role();
        role.setRoleName("USER");
        roleRepository.save(role);

        // Create an AdminCreateUpdateUserRequestDto object with the RegisterDto and Role objects
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        AdminCreateUpdateUserRequestDto adminCreateUpdateUserRequestDto = new AdminCreateUpdateUserRequestDto(registerDto, roles);

        // Call the method being tested
        User createdUser = userService.createUser(adminCreateUpdateUserRequestDto);

        // Assert the result
        assertNotNull(createdUser);
        assertEquals("99999999999", createdUser.getTckn());
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("8888888888", createdUser.getGsmNumber());
        assertEquals(new Date(90, 0, 1), createdUser.getDateOfBirth());
        assertTrue(passwordEncoder.matches(registerDto.getPassword(), createdUser.getPassword()));
    }
}

