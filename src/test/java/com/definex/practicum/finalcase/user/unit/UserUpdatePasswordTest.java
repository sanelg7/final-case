package com.definex.practicum.finalcase.user.unit;

import com.definex.practicum.finalcase.dto.UserUpdatePasswordDto;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.UserService;
import com.definex.practicum.finalcase.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserUpdatePasswordTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateUserPassword() {
        // create a new user
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setPassword("encoded-password-mock");
        when(userService.existsById(any())).thenReturn(true);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // update the user's password
        UserUpdatePasswordDto updateUserDto = new UserUpdatePasswordDto();
        updateUserDto.setPassword("new-password");
        when(userRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(passwordEncoder.encode(any())).thenReturn("new-encoded-password");
        user = userService.updateUserPassword(user.getId(), updateUserDto);
        assertEquals(passwordEncoder.encode("new-password"), user.getPassword());
    }

}
