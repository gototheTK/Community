package com.community.communityback.repository;

import java.util.List;

import com.community.communityback.model.Reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.repository.query.Param;



public interface ReplyRepository extends JpaRepository<Reply, Long>{

    
    @Modifying
    @Query(value="INSERT INTO Reply(userId, boardId, parentId, stepNum,rankNum, content, createdate) VALUES(?1, ?2, ?3, ?4, ?5, ?6 ,now())",nativeQuery = true)
    public Integer replySave(Long userId, Long boardId, Long parentId, int stepNum, int rankNum,String content );

    @Modifying
    @Query(value="INSERT INTO Reply(userId, boardId, groupId, parentId, stepNum, rankNum,  content, createdate) VALUES(?1, ?2, ?3, ?4,?5, ?6, ?7, now())",nativeQuery = true)
    public Integer subReplySave(Long userId, Long boardId, Long groupId,Long parentId, int stepNum, int rankNum,String content );



    public List<Reply> findAllByGroupIdIsNull();

    public List<Reply> findAllByGroupId(Sort sort,Long groupId);

    @Query(value="SELECT * FROM Reply WHERE boardId = ?1 ORDER BY  groupId, rankNum, stepNum ",nativeQuery = true)
    public Page<Reply> findAllByBoardId(Long boardId, Pageable pageable);

   
    
}
