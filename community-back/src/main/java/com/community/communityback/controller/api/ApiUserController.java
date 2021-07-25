package com.community.communityback.controller.api;

import com.community.communityback.config.auth.PrincipalDetails;
import com.community.communityback.model.Interest;
import com.community.communityback.model.User;
import com.community.communityback.repository.UserInterestRepository;
import com.community.communityback.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiUserController {

    private final UserService userService;
    

    @GetMapping("/api/user/interest/list")
    public ResponseEntity<?> userIntrestList(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return new ResponseEntity<>(userService.유저관심사가져오기(principalDetails.getUser()), HttpStatus.CREATED);
    }

    @PostMapping("/api/user/interest/write")
    public ResponseEntity<?> userIntrestWrite(@AuthenticationPrincipal PrincipalDetails principalDetails,@RequestBody Interest interest){
        System.out.println("관심사"+interest);
        return new ResponseEntity<>(userService.유저관심사등록하기(principalDetails.getUser(), interest), HttpStatus.CREATED);
    }

    @PutMapping("/api/user/interest/delete")
    public ResponseEntity<?> deleteInterest(@RequestBody Interest interest, @AuthenticationPrincipal PrincipalDetails principal) {

        userService.유저관심사삭제(principal.getUser(),interest);

            return new ResponseEntity<>(null, HttpStatus.OK);
    }

    
}
