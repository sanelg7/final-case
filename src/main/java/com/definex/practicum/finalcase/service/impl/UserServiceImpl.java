package com.definex.practicum.finalcase.service.impl;

import com.definex.practicum.finalcase.dto.AdminCreateUpdateUserRequestDto;
import com.definex.practicum.finalcase.dto.RegisterDto;
import com.definex.practicum.finalcase.dto.UserUpdatePasswordDto;
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

import java.util.*;
import java.util.stream.Collectors;


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
        Role roles = roleRepository.findByRoleName("USER");
        user.setRoles(Collections.singletonList(roles));
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        return userRepository.save(user);
    }

    // User creation by admin. Admin can create users with different roles.
    @Transactional
    @Override
    public User createUser(AdminCreateUpdateUserRequestDto adminCreateUpdateUserRequestDto) throws EntityCreationException{
        RegisterDto registerDto = adminCreateUpdateUserRequestDto.getRegisterDto();
        if(userRepository.existsByTckn(registerDto.getTckn())){
            throw new EntityCreationException(User.class.getName(), registerDto.getTckn());
        }
        if (userRepository.existsByGsmNumber(registerDto.getGsmNumber())){
            throw new EntityCreationException(User.class.getName(), registerDto.getGsmNumber());
        }
        List<String> roleNames = adminCreateUpdateUserRequestDto.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        List<Role> createdUserRoles = roleRepository.findByRoleNameIn(roleNames);

        User user = adminCreateUpdateUserRequestDto.buildUserWithoutPasswordAndRoles();
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(createdUserRoles);
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
        if(!userRepository.existsByTckn(tckn)){
            throw new EntityNotFoundException(User.class.getName(), tckn);
        }
        return userRepository.findByTckn(tckn).get();
    }


    //For normal user.
    @Transactional
    @Override
    public User updateUserPassword(UUID id, UserUpdatePasswordDto updateUserDto) throws EntityNotFoundException, UserUpdateException{
        if(!existsById(id)){
            throw new EntityNotFoundException(User.class.getName(), id);
        }
        if (!updateUserDto.hasData()) {
            throw new UserUpdateException();
        }
        User updatedUser = userRepository.findById(id).get();
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        return userRepository.save(updatedUser);

    }

    // For admin use. Imagined an admin panel with each of the users properties available.
    // Does not include credit limit, score and application. Those are handled by a different service.
    @Transactional
    @Override
    public User updateUserByAdmin(UUID id, AdminCreateUpdateUserRequestDto adminCreateUpdateUserRequestDto) throws EntityNotFoundException, UserUpdateException{
        if(!existsById(id)){
            throw new EntityNotFoundException(User.class.getName(), id);
        }

        User updatedUser = userRepository.findById(id).get();

        // Checking if another user has the tckn or gsm number passed.
        if(userRepository.existsByTckn(adminCreateUpdateUserRequestDto.getRegisterDto().getTckn())){
            User receivedUser = userRepository.findByTckn(adminCreateUpdateUserRequestDto.getRegisterDto().getTckn()).get();

            // If that is the case, These users must be the same. If not an error should be thrown.
            if (userRepository.existsByGsmNumber(adminCreateUpdateUserRequestDto.getRegisterDto().getGsmNumber())){
                User receivedUserByGsmNo = userRepository.findByGsmNumber(adminCreateUpdateUserRequestDto.getRegisterDto().getGsmNumber()).get();
                if (!receivedUser.equals(receivedUserByGsmNo)) {
                    throw new UserUpdateException(receivedUser.getTckn(), receivedUserByGsmNo.getGsmNumber());
                }

        }


            // Now checking if it is the same user based on id. Same user can have their tckn or gsm number updated.
           if(updatedUser.getId() == receivedUser.getId()){
               throw new UserUpdateException(receivedUser.getTckn(), receivedUser.getGsmNumber());
           }
        }
        RegisterDto registerDto = adminCreateUpdateUserRequestDto.getRegisterDto();

        List<String> roleNames = adminCreateUpdateUserRequestDto.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        System.out.println(roleNames);
        List<Role> updatedUserRoles = roleRepository.findByRoleNameIn(roleNames);

        System.out.println(updatedUserRoles);

        updatedUser.setRoles(updatedUserRoles);

        updatedUser.setTckn(registerDto.getTckn());
        updatedUser.setFirstName(registerDto.getFirstName());
        updatedUser.setGsmNumber(registerDto.getGsmNumber());
        updatedUser.setLastName(registerDto.getLastName());
        updatedUser.setDateOfBirth(registerDto.getDateOfBirth());
        updatedUser.setCreditApplications(updatedUser.getCreditApplications());
        updatedUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
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
