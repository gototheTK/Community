package com.community.communityback.repository;

import java.util.List;
import java.util.Optional;

import com.community.communityback.model.Interest;
import com.community.communityback.model.User;
import com.community.communityback.model.UserInterest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    List<UserInterest> findAllByUserId(Long userId);

    Optional<UserInterest> deleteByUserIdAndInterestId(Long userId, Long interestId);
}
