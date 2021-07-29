package com.community.communityback.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.community.communityback.model.Interest;
import com.community.communityback.model.ProviderType;
import com.community.communityback.model.RoleType;
import com.community.communityback.model.User;
import com.community.communityback.model.UserInterest;
import com.community.communityback.repository.InterestRepository;
import com.community.communityback.repository.UserInterestRepository;
import com.community.communityback.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Integer 회원가입(User user)
    {

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
         return 200;
        }else{
            String EcodedPassword = encoder.encode(user.getPassword());
        user.setPassword(EcodedPassword);
        return 201;
        }
        
    }

    @Transactional
    public boolean 로그인(User user){


        User UserEntity = userRepository.findByUsername(user.getUsername()).orElseThrow(()->{
            return new IllegalArgumentException("해당하는 아이디가 존재하지 않습니다.");
        });

        String rawPassword = user.getPassword();
        String ecodedPassword = UserEntity.getPassword();

        boolean match = encoder.matches(rawPassword,ecodedPassword);
        

        if(match){
            return true;
        }
        
        return false;
    
    }


    @Transactional
    public Interest 유저관심사등록하기(User user, Interest interest){


       return userInterestRepository.save(UserInterest.builder().user(user).interest(interest).build()).getInterest();
     
    }


    @Transactional
    public List<Interest> 유저관심사가져오기(User user){
        List<Interest> interests = new ArrayList<>();
       userInterestRepository.findAllByUserId(user.getId()).iterator().forEachRemaining((interest)->{
        interests.add(interest.getInterest());
       });

       return interests;
     
    }

    @Transactional
    public void 유저관심사삭제(User user,Interest interest) {

        userInterestRepository.deleteByUserIdAndInterestId(user.getId(),interest.getId()).orElseThrow(()->{
            return new IllegalArgumentException("유저나 관심사를 잘못입력하셨습니다.");});


     }

}
