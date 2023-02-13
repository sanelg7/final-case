package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.CreditScoreRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CreditScoreServiceImpl implements CreditScoreService{

    private CreditScoreRepository creditScoreRepository;
    private final UserRepository userRepository;


    @Autowired
    public CreditScoreServiceImpl(CreditScoreRepository creditScoreRepository,
                                  UserRepository userRepository){
        this.creditScoreRepository=creditScoreRepository;
        this.userRepository = userRepository;
    }

    // Used for both save and update operations. No hard logic. Saves the generated value.
    @Override
    public CreditScore saveCreditScore(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException(User.class.getName(), userId);
        }
        CreditScore creditScore = new CreditScore();
        creditScore.setCreditScore(generateCreditScore());
        User user = userRepository.findById(userId).get();
        user.setCreditScore(creditScore);
        return user.getCreditScore();
    }

    // User id is not passed as both entities are already related.
    @Override
    public CreditScore updateCreditScore(CreditScore creditScore){
        if(!creditScoreRepository.existsById(creditScore.getId())){
            throw new EntityNotFoundException(CreditScore.class.getName(), creditScore.getId());
        }
        CreditScore updatedCreditScore = creditScoreRepository.findById(creditScore.getId()).get();
        updatedCreditScore.setCreditScore(generateCreditScore());
        return creditScoreRepository.save(updatedCreditScore);
    }

    @Override
    public void deleteCreditScore(Long id) {
        if(!creditScoreRepository.existsById(id)){
            throw new EntityNotFoundException(CreditScore.class.getName(), id);
        }
        creditScoreRepository.deleteById(id);
    }

    @Override
    public CreditScore getCreditScoreById(Long id){
        if(!creditScoreRepository.existsById(id)){
            throw new EntityNotFoundException(CreditScore.class.getName(), id);
        }
        return creditScoreRepository.findById(id).get();
    }


    // Generates a random credit score between 250 and 1300.
    @Override
    public double generateCreditScore() {
        Random random = new Random();
        double score = random.nextDouble((1300 - 250) + 1) + 250;
        return score;
    }


}
