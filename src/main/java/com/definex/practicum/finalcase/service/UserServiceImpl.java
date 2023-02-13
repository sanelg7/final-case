package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByTckn(String tckn) {
        User temp = userRepository.findByTckn(tckn).get();
        return temp;
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    /* User's TCKN and date of birth can not be changed.
    Admin users can have normal accounts. Difference is admins have separate (non tckn) logins.
    So they would need two accounts in that case, hence ROLE can not be changed either.
    */
    @Override
    public User update(Long id, User user) {
        Optional<User> temp = userRepository.findById(id);
        User updatedUser = temp.get();
        if(updatedUser.getTckn() != user.getTckn() ||
           updatedUser.getRole() != user.getRole() ||
           updatedUser.getDateOfBirth() != user.getDateOfBirth()){

            updatedUser.setGsmNumber(user.getGsmNumber());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            return userRepository.save(updatedUser);
        }
         else {
            throw new UserUpdateException("Can not update tckn, date of birth and role fields. Received : "
                    + user.getTckn() + ", " + user.getDateOfBirth() + ", " + user.getRole() +
                    " For user:" + updatedUser);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findById(Long id) {
        User temp = userRepository.findById(id).get();
        return temp;
    }

    @Override
    public boolean existsById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return true;
        }
        return false;
    }


}
