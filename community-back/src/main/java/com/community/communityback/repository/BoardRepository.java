package com.community.communityback.repository;

import java.util.List;
import java.util.Optional;

import com.community.communityback.model.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value="SELECT * FROM Board WHERE title LIKE %:keyword% OR content LIKE %:keyword%",nativeQuery = true)
    public Page<Board>  search(Pageable pageable, @Param("keyword") String keyword);

    
    public Page<Board> findAllByInterestId(Pageable pageable, Long interestId);

}
