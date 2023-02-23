package com.definex.practicum.finalcase.service.impl;

import com.definex.practicum.finalcase.dto.CreateUserRequestDto;
import com.definex.practicum.finalcase.dto.RegisterDto;
import com.definex.practicum.finalcase.dto.UpdateUserDto;
import com.definex.practicum.finalcase.exception.EntityCreationException;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.Role;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.RoleRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // User creation with signup.
    @Transactional
    @Override
    public User register(RegisterDto registerDto) throws EntityCreationException{
        if(userRepository.existsByTckn(registerDto.getTckn())){
            throw new EntityCreationException(User.class.getName(), registerDto.getTckn());
        }
        if (userRepository.existsByGsmNumber(registerDto.getGsmNumber())){
            throw new EntityCreationException(User.class.getName(), registerDto.getGsmNumber());
        }
        User user = new User(registerDto.getTckn(), registerDto.getFirstName(), registerDto.getLastName(), registerDto.getGsmNumber(), registerDto.getDateOfBirth());

        Role roles = roleRepository.findByRoleName("USER").get();

        user.setRoles(Collections.singletonList(roles));
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        return userRepository.save(user);
    }

    // User creation by admin. Admin can create users with different roles.
    @Transactional
    @Override
    public User createUser(CreateUserRequestDto createUserRequestDto) throws EntityCreationException{
        RegisterDto registerDto = createUserRequestDto.getRegisterDto();
        if(userRepository.existsByTckn(registerDto.getTckn())){
            throw new EntityCreationException(User.class.getName(), registerDto.getTckn());
        }
        if (userRepository.existsByGsmNumber(registerDto.getGsmNumber())){
            throw new EntityCreationException(User.class.getName(), registerDto.getGsmNumber());
        }
        User user = createUserRequestDto.buildUserWithoutPasswordAndRoles();
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(createUserRequestDto.getRoles());
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) throws EntityNotFoundException {
        if(!existsById(id)){
            throw new EntityNotFoundException(User.class.getName(), id);
        }
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByTckn(String tckn) throws EntityNotFoundException {
        if(userRepository.existsByTckn(tckn)){
            throw new EntityNotFoundException(User.class.getName(), tckn);
        }
        return userRepository.findByTckn(tckn).get();
    }

    /* For normal user.
    User's TCKN and date of birth can not be changed.
    Admin users can have normal accounts ie. multiple roles. How that is handled on client side is not a concern here.
    */
    @Transactional
    @Override
    public User updateUser(UUID id, UpdateUserDto updateUserDto) throws EntityNotFoundException, UserUpdateException{
        if(!existsById(id)){
            throw new EntityNotFoundException(User.class.getName(), id);
        }
        if (!updateUserDto.hasNonNullField()) {
            throw new UserUpdateException();
        }
        // No need to check if user can be updated as we are only passing updateUserDto, which does not include restricted fields.
        User updatedUser = userRepository.findById(id).get();
        return userRepository.save(updatedUser);

    }

    // For admin use
    @Transactional
    @Override
    public User updateUserByAdmin(UUID id, User user) throws EntityNotFoundException, UserUpdateException{
        if(!existsById(id)){
            throw new EntityNotFoundException(User.class.getName(), id);
        }
        User updatedUser = userRepository.findById(id).get();
        return userRepository.save(updatedUser);

    }


    @Transactional
    @Override
    public void deleteUser(UUID id) throws EntityNotFoundException {
        if(!existsById(id)){
            throw new EntityNotFoundException(User.class.getName(), id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id){
        return userRepository.existsById(id);
    }


}
