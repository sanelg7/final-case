package com.definex.practicum.finalcase.service;


import com.definex.practicum.finalcase.model.User;

import java.util.List;

public interface UserService {

    User getUserByTckn(String tckn);
    User createUser(User user);

    User updateUser(Long id, User user);

    List<User> getUsers();

    void deleteUser(Long id);
    User getUserById(Long id);

    boolean existsById(Long id);
}
