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

    // CreditScore is generated randomly with the help of generateCreditScoreValue(). No value is passed from the controller.
    @Override
    public CreditScore createCreditScore(Long userId) throws EntityNotFoundException{
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException(User.class.getName(), userId);
        }
        CreditScore creditScore = new CreditScore();
        creditScore.setCreditScore(generateCreditScoreValue());
        User user = userRepository.findById(userId).get();
        user.setCreditScore(creditScore);
        return user.getCreditScore();
    }


    @Override
    public CreditScore getCreditScore(Long id) throws EntityNotFoundException{
        if(!creditScoreRepository.existsById(id)){
            throw new EntityNotFoundException(CreditScore.class.getName(), id);
        }
        return creditScoreRepository.findById(id).get();
    }

    // User id is not passed as both entities are already related.
    @Override
    public CreditScore updateCreditScore (Long id, CreditScore creditScore) throws EntityNotFoundException{
        if(!creditScoreRepository.existsById(id)){
            throw new EntityNotFoundException(CreditScore.class.getName(), id);
        }
        CreditScore updatedCreditScore = creditScoreRepository.findById(id).get();
        updatedCreditScore.setCreditScore(generateCreditScoreValue());
        return creditScoreRepository.save(updatedCreditScore);
    }

    @Override
    public void deleteCreditScore(Long id) throws EntityNotFoundException{
        if(!creditScoreRepository.existsById(id)){
            throw new EntityNotFoundException(CreditScore.class.getName(), id);
        }
        creditScoreRepository.deleteById(id);
    }

    // Generates a random credit score between 250 and 1300.
    @Override
    public double generateCreditScoreValue() {
        Random random = new Random();
        double score = random.nextDouble((1300 - 250) + 1) + 250;
        return score;
    }


}
