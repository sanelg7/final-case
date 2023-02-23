package com.definex.practicum.finalcase.service;


import com.definex.practicum.finalcase.dto.CreateUserRequestDto;
import com.definex.practicum.finalcase.dto.RegisterDto;
import com.definex.practicum.finalcase.dto.UpdateUserDto;
import com.definex.practicum.finalcase.exception.EntityCreationException;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.Role;
import com.definex.practicum.finalcase.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User getUserByTckn(String tckn);





    User register(RegisterDto registerDto) throws EntityCreationException;

    User createUser(CreateUserRequestDto createUserRequestDto);

    List<User> getUsers();

    User updateUser(UUID id, UpdateUserDto updateUserDto);

    User updateUserByAdmin(UUID id, User user);

    void deleteUser(UUID id);
    User getUserById(UUID id);

    boolean existsById(UUID id);
}
