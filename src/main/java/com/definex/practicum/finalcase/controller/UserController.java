package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.exception.UserNotFoundException;
import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find")
    public User findById(@RequestParam("id") Long id){
        return userService.findById(id);
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,@RequestBody User user){
        if(!userService.existsById(id)){
            throw new UserNotFoundException(id);
        }
        try {
            User updatedUser = userService.update(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserUpdateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(!userService.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
