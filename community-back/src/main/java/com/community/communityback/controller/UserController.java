package com.community.communityback.controller;

import com.community.communityback.config.auth.PrincipalDetails;
import com.community.communityback.dto.User.UserDto;
import com.community.communityback.model.Interest;
import com.community.communityback.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserDto userDto) {

        if(userService.회원가입(userDto.defaultJoin())!=null){
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    

}
