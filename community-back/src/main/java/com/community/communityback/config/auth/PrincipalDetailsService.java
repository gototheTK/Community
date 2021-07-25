package com.community.communityback.config.auth;

import com.community.communityback.model.User;
import com.community.communityback.repository.UserRepository;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User UserEntity = userRepository.findByUsername(username).orElseThrow(()->{
            return new IllegalArgumentException("해당하는 아이디가 존재하지 않습니다.");
        });
        System.out.println("권한:"+UserEntity.toString());
        // TODO Auto-generated method stub
        return new PrincipalDetails(UserEntity);
    }
    
}
