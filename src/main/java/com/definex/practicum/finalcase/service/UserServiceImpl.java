package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByTckn(String tckn) {
        User temp = userRepository.findByTckn(tckn).get();
        return temp;
    }

    @Override
    public User save(User user) {
        User temp = user;
        userRepository.save(temp);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        User temp = userRepository.findById(id).get();
        return temp;
    }
}
