package com.definex.practicum.finalcase.controller.auth;

import com.definex.practicum.finalcase.dto.auth.LoginResponseDto;
import com.definex.practicum.finalcase.dto.auth.LoginDto;
import com.definex.practicum.finalcase.dto.user.RegisterDto;
import com.definex.practicum.finalcase.exception.EntityCreationException;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.security.JwtGenerator;
import com.definex.practicum.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @PostMapping("register")
    public CustomResponseEntity<User> register(@RequestBody RegisterDto registerDto) {
        try{
            User user = userService.register(registerDto);
            return new CustomResponseEntity<>(user, "Registered successfully", HttpStatus.CREATED);
        }catch (EntityCreationException e){
            return new CustomResponseEntity<>(null,e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getTckn(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.getUserByTckn(loginDto.getTckn());
        String token = jwtGenerator.generateToken(authentication,user.getId());
        // Used for debugging with user ID. Converts user id on db to UUID format.


        return new ResponseEntity<>(new LoginResponseDto(token, user.getId()), HttpStatus.OK);

    }

    // TODO: Logout
}
