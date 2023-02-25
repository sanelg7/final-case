package com.definex.practicum.finalcase.service;


import com.definex.practicum.finalcase.dto.AdminCreateUpdateUserRequestDto;
import com.definex.practicum.finalcase.dto.RegisterDto;
import com.definex.practicum.finalcase.dto.UserUpdatePasswordDto;
import com.definex.practicum.finalcase.exception.EntityCreationException;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User getUserByTckn(String tckn);

    User register(RegisterDto registerDto);
    User createUser(AdminCreateUpdateUserRequestDto adminCreateUpdateUserRequestDto);

    List<User> getUsers();

    User updateUserPassword(UUID id, UserUpdatePasswordDto updateUserDto);

    User updateUserByAdmin(UUID id, AdminCreateUpdateUserRequestDto adminCreateUpdateUserRequestDto);

    void deleteUser(UUID id);
    User getUserById(UUID id);

    boolean existsById(UUID id);
}
