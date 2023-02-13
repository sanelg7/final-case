package com.definex.practicum.finalcase.service;


import com.definex.practicum.finalcase.model.User;

import java.util.List;

public interface UserService {

    public User findByTckn(String tckn);
    public User save(User user);

    public User update(Long id, User user);

    public List<User> findAll();

    public void delete(Long id);
    User findById(Long id);

    boolean existsById(Long id);
}
