package com.community.communityback.service;

import java.sql.SQLException;

import com.community.communityback.model.Interest;
import com.community.communityback.model.User;
import com.community.communityback.model.UserInterest;
import com.community.communityback.repository.InterestRepository;
import com.community.communityback.repository.UserInterestRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;
    

    @Transactional
    public Page<Interest> 관심사리스트(Pageable pageable) throws IllegalArgumentException {

        return interestRepository.findAll(pageable);
    }

    @Transactional
    public Interest 관심사등록(Interest interest) throws IllegalArgumentException {

       return interestRepository.save(interest);
    }


   
    
}
