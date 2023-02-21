package com.definex.practicum.finalcase.service;


import com.definex.practicum.finalcase.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User getUserByTckn(String tckn);
    User createUser(User user);

    User updateUser(UUID id, User user);

    List<User> getUsers();

    void deleteUser(UUID id);
    User getUserById(UUID id);

    boolean existsById(UUID id);
}
