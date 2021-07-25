package com.community.communityback.repository;

import java.util.List;

import com.community.communityback.model.Reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface ReplyRepository extends JpaRepository<Reply, Long>{

    @Modifying
    @Query(value="INSERT INTO Reply(userId, boardId, content, createdate) VALUES(?1, ?2, ?3, now())",nativeQuery = true)
    public int replySave(Long userId, Long boardId, String content);

    public Page<Reply> findAllByBoardId(Long boardId, Pageable pageable);
    
}
