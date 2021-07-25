package com.community.communityback.repository;



import java.util.List;

import com.community.communityback.model.BoardFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

    List<BoardFile> findAllByBoardId(Long boardId);

}
