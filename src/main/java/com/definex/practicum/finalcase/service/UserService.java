package com.definex.practicum.finalcase.service;


import com.definex.practicum.finalcase.dto.user.AdminCreateUpdateUserRequestDto;
import com.definex.practicum.finalcase.dto.user.RegisterDto;
import com.definex.practicum.finalcase.dto.user.UserUpdatePasswordDto;
import com.definex.practicum.finalcase.model.User;

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
