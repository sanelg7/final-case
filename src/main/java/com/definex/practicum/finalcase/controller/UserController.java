package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.exception.EntityCreationException;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public CustomResponseEntity<User> getUser(@RequestParam("id") Long id){
        try {
            return new CustomResponseEntity<>(userService.getUserById(id),
                    "Fetched user", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new CustomResponseEntity<>(null, "No such user found", HttpStatus.BAD_REQUEST);
        }
    }


    // TODO: for admins
    /*@GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }*/

    @PostMapping
    public CustomResponseEntity<User> createUser(@RequestBody User user){
        try {
            return new CustomResponseEntity<>(userService.createUser(user), "User created successfully", HttpStatus.CREATED);
        } catch (EntityCreationException e) {
            //TODO: Check http status
            return new CustomResponseEntity<>(null,"User created successfully", HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: Maybe add "role" to the not changed list according to where the method was called from.
    @PutMapping
    public CustomResponseEntity<User> update(@RequestParam("id") Long id,@RequestBody User user){
        try {
            return new CustomResponseEntity<>(userService.updateUser(id, user), "User updated successfully", HttpStatus.OK);
        } catch (UserUpdateException | EntityNotFoundException e) {
            if(e instanceof EntityNotFoundException){
                return new CustomResponseEntity<>(null,"No user found to update" , HttpStatus.BAD_REQUEST);
            }else {
                return new CustomResponseEntity<>(null,"TCKN, and date of birth can not be changed" , HttpStatus.BAD_REQUEST);
            }

        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("id") Long id){
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No user found to delete", HttpStatus.BAD_REQUEST);
        }
    }


}
