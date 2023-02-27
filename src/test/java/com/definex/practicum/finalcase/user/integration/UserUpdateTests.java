package com.definex.practicum.finalcase.user.integration;

import com.definex.practicum.finalcase.dto.AdminCreateUpdateUserRequestDto;
import com.definex.practicum.finalcase.dto.RegisterDto;
import com.definex.practicum.finalcase.model.Role;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserUpdateTests {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void testUpdateUserByAdmin() {
        // retrieve a user from db
        User user = userRepository.findByTckn("12345678901").get();

        // create a new admin update dto
        RegisterDto registerDto = new RegisterDto();
        registerDto.setTckn("12345678912");
        registerDto.setFirstName("Jane");
        registerDto.setLastName("Doe");
        registerDto.setGsmNumber("0000000124");
        registerDto.setDateOfBirth(new Date(90, 0, 2));
        registerDto.setPassword("newpassword");

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");
        roles.add(role);

        AdminCreateUpdateUserRequestDto adminCreateUpdateUserRequestDto = new AdminCreateUpdateUserRequestDto(registerDto, roles);

        // update the user by admin
        User updatedUser = userService.updateUserByAdmin(user.getId(), adminCreateUpdateUserRequestDto);

        // assert the result
        assertNotNull(updatedUser);
        assertEquals(registerDto.getTckn(), updatedUser.getTckn());
        assertEquals(registerDto.getFirstName(), updatedUser.getFirstName());
        assertEquals(registerDto.getLastName(), updatedUser.getLastName());
        assertEquals(registerDto.getGsmNumber(), updatedUser.getGsmNumber());
        assertEquals(registerDto.getDateOfBirth(), updatedUser.getDateOfBirth());
        assertTrue(passwordEncoder.matches(registerDto.getPassword(), updatedUser.getPassword()));
    }


}
