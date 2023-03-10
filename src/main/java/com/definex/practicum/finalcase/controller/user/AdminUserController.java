package com.definex.practicum.finalcase.controller.user;

import com.definex.practicum.finalcase.dto.user.AdminCreateUpdateUserRequestDto;
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
public class AdminUserController extends BaseUserController {


    @Autowired
    public AdminUserController(UserService userService) {
        super(userService);
    }


    @PostMapping

    public CustomResponseEntity<User> createUser(@RequestBody AdminCreateUpdateUserRequestDto adminCreateUpdateUserRequestDto){
        try {
            return new CustomResponseEntity<>(userService.createUser(adminCreateUpdateUserRequestDto), "User created successfully", HttpStatus.CREATED);
        } catch (EntityCreationException e) {
            return new CustomResponseEntity<>(null,e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getUsers();
    }


    // Can update restricted fields and credit related data.
    @PutMapping("{id}")
    public CustomResponseEntity<User> update(@PathVariable UUID id, @RequestBody AdminCreateUpdateUserRequestDto adminCreateUpdateUserRequestDto){
        try {
            return new CustomResponseEntity<>(userService.updateUserByAdmin(id, adminCreateUpdateUserRequestDto), "User updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
                return new CustomResponseEntity<>(null,"No user found to update" , HttpStatus.BAD_REQUEST);

        }
    }

}
