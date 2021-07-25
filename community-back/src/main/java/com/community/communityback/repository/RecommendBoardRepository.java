package com.community.communityback.repository;

import java.util.Optional;

import com.community.communityback.model.RecommendBoard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendBoardRepository extends JpaRepository <RecommendBoard, Long> {
    
    public Optional<RecommendBoard> findByBoardIdAndUserId(Long boardId, Long userId);

}
