package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.exception.EntityCreationException;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // TODO: Maybe refactor into creation by signup / admin
    @Override
    public User createUser(User user) throws EntityCreationException{
        if(userRepository.existsByTckn(user.getTckn())){
            throw new EntityCreationException(User.class.getName(), user.getId());
        }
        return userRepository.save(user);
    }

    // TODO: admin / user separation
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
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

    /* User's TCKN and date of birth can not be changed.
    Admin users can have normal accounts. Difference is admins have separate (non tckn) logins.
    So they would need two accounts in that case, hence ROLE can not be changed either.
    */
    @Override
    public User updateUser(Long id, User user) throws EntityNotFoundException, UserUpdateException{
        if(!existsById(id)){
            throw new EntityNotFoundException(User.class.getName(), id);
        }
        User updatedUser = userRepository.findById(id).get();
        if(goodToUpdate(user, updatedUser)){

            updatedUser.setGsmNumber(user.getGsmNumber());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            return userRepository.save(updatedUser);
        }
         else {
            throw new UserUpdateException(user.getTckn(), user.getDateOfBirth(), user.getGsmNumber());
        }
    }


    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        if(!existsById(id)){
            throw new EntityNotFoundException(User.class.getName(), id);
        }
        userRepository.deleteById(id);
    }


    @Override
    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }


    private boolean goodToUpdate(User user, User requestBody){
        if(user.getTckn().equals(requestBody.getTckn())
        && user.getRole().equals(requestBody.getRole())
        && user.getDateOfBirth().equals(requestBody.getDateOfBirth())){
            return true;
        }
        return false;
    }
}
