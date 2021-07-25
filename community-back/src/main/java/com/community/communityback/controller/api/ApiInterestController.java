package com.community.communityback.controller.api;

import java.sql.SQLException;

import com.community.communityback.config.auth.PrincipalDetails;
import com.community.communityback.model.Interest;
import com.community.communityback.service.InterestService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class ApiInterestController {

    private final InterestService interestService;
    
    @GetMapping("/interest/list")
    public ResponseEntity<?> getInterests(@PageableDefault(size=20, sort="counts", direction = Sort.Direction.ASC) Pageable pageable) {

            return new ResponseEntity<>(interestService.관심사리스트(pageable).getContent(), HttpStatus.CREATED);


    }

   

    
    @PostMapping("/api/interest/wirte")
    public ResponseEntity<?> postInterest(@RequestBody String name, @AuthenticationPrincipal PrincipalDetails principal) {

        interestService.관심사등록(Interest.builder().founder(principal.getUser().getUsername()).founderId(principal.getUser().getId()).name(name).counts(0L).build());

            return new ResponseEntity<>(name, HttpStatus.CREATED);


    }


    
    
}
