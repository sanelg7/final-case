package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class BaseUserController {

    protected final UserService userService;

    @Autowired
    public BaseUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public CustomResponseEntity<User> getUser(@PathVariable UUID id){
        try {
            return new CustomResponseEntity<>(userService.getUserById(id),
                    "Fetched user", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new CustomResponseEntity<>(null, "No such user found", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("id") UUID id){
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No user found to delete", HttpStatus.BAD_REQUEST);
        }
    }
}
