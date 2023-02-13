package com.definex.practicum.finalcase.service;


import com.definex.practicum.finalcase.model.User;

import java.util.List;

public interface UserService {

    User findByTckn(String tckn);
    User save(User user);

    User update(Long id, User user);

    List<User> findAll();

    void delete(Long id);
    User findById(Long id);

    boolean existsById(Long id);
}
