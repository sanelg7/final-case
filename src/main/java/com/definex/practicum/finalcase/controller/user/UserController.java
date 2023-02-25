package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.aop.annotations.RequiresUserRolePermission;
import com.definex.practicum.finalcase.dto.user.UserUpdatePasswordDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController extends BaseUserController {


    @Autowired
    public UserController(UserService userService) {
        super(userService);
    }


    // Restricted update
    @PutMapping("{id}")
    @RequiresUserRolePermission
    public CustomResponseEntity<User> update(@PathVariable UUID id, @RequestBody UserUpdatePasswordDto updateUserDto){
        try {
            return new CustomResponseEntity<>(userService.updateUserPassword(id, updateUserDto), "User updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException | UserUpdateException e) {
            if(e instanceof EntityNotFoundException){
                return new CustomResponseEntity<>(null,"No user found to update" , HttpStatus.BAD_REQUEST);
            }else {
                return new CustomResponseEntity<>(null,e.getMessage(), HttpStatus.BAD_REQUEST);

            }
        }
    }


}
