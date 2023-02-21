package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.CreditScoreRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
public class CreditScoreServiceImpl implements CreditScoreService{

    private final CreditScoreRepository creditScoreRepository;
    private final UserRepository userRepository;


    @Autowired
    public CreditScoreServiceImpl(CreditScoreRepository creditScoreRepository,
                                  UserRepository userRepository){
        this.creditScoreRepository=creditScoreRepository;
        this.userRepository = userRepository;
    }

    // CreditScore is generated randomly with the help of generateCreditScoreValue(). No value is passed from the controller.
    @Transactional
    @Override
    public CreditScore createCreditScore(UUID userId) throws EntityNotFoundException{
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException(User.class.getName(), userId);
        }
        /*User user = userRepository.findById(userId).get();
        if (user.getCreditScore() != null){
            user.setCreditScore(null);
            userRepository.save(user);
        }*/
        CreditScore creditScore = new CreditScore();
        creditScore.setCreditScoreValue(generateCreditScoreValue());
        User user = userRepository.findById(userId).get();
        user.setCreditScore(creditScore);
        creditScore.setUser(user);

        return creditScoreRepository.save(creditScore);
    }

    @Transactional(readOnly = true)
    @Override
    public CreditScore getCreditScore(UUID id) throws EntityNotFoundException{
        if(!creditScoreRepository.existsById(id)){
            throw new EntityNotFoundException(CreditScore.class.getName(), id);
        }
        return creditScoreRepository.findById(id).get();
    }

    // User id is not passed as both entities are already related. Also, for admin.
    @Transactional
    @Override
    public CreditScore updateCreditScore (UUID id, CreditScore creditScore) throws EntityNotFoundException{
        if(!creditScoreRepository.existsById(id)){
            throw new EntityNotFoundException(CreditScore.class.getName(), id);
        }
        CreditScore updatedCreditScore = creditScoreRepository.findById(id).get();
        updatedCreditScore.setCreditScoreValue(creditScore.getCreditScoreValue());
        return creditScoreRepository.save(updatedCreditScore);
    }


    @Transactional
    @Override
    public void deleteCreditScore(UUID id) throws EntityNotFoundException{
        if(!creditScoreRepository.existsById(id)){
            throw new EntityNotFoundException(CreditScore.class.getName(), id);
        }
        CreditScore creditScore = creditScoreRepository.findById(id).get();
        creditScoreRepository.delete(creditScore);
        //creditScoreRepository.deleteById(id);
        System.out.println(creditScoreRepository.findById(id));
    }

    // Generates a random credit score between 250 and 1300.
    @Override
    public double generateCreditScoreValue() {
        Random random = new Random();
        double score = random.nextDouble((1300 - 250) + 1) + 250;
        return score;
    }

    @Override
    public boolean existsByUser_Tckn(String userTckn){
        return creditScoreRepository.existsByUser_Tckn(userTckn);
    }


}
