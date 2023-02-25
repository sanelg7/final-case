package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.dto.CreateUserRequestDto;
import com.definex.practicum.finalcase.exception.EntityCreationException;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/admin")
public class UserAdminController extends BaseUserController{


    @Autowired
    public UserAdminController(UserService userService) {
        super(userService);
    }


    @PostMapping
    public CustomResponseEntity<User> createUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        try {
            return new CustomResponseEntity<>(userService.createUser(createUserRequestDto), "User created successfully", HttpStatus.CREATED);
        } catch (EntityCreationException e) {
            //TODO: Check http status
            return new CustomResponseEntity<>(null,e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getUsers();
    }


    // Can update restricted fields and credit related data.
    @PutMapping("{id}")
    public CustomResponseEntity<User> update(@PathVariable UUID id, @RequestBody User user){
        try {
            return new CustomResponseEntity<>(userService.updateUserByAdmin(id, user), "User updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
                return new CustomResponseEntity<>(null,"No user found to update" , HttpStatus.BAD_REQUEST);

        }
    }

}
