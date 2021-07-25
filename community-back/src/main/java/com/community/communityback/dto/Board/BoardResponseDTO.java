package com.community.communityback.dto.Board;

import java.sql.Timestamp;
import java.util.List;

import com.community.communityback.model.Board;
import com.community.communityback.model.BoardFile;
import com.community.communityback.model.Interest;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDTO {
    private Integer status;
    private Long id;
    private String username;
    private Integer recommend;
    private String title;
    private String content;
    private Long count;
    private Timestamp createTime;
    private List<BoardFile> boardFiles;
    private String interest;

    

    public BoardResponseDTO (Integer status, Board board){
        this.status = status;
        this.id = board.getId();
        this.username = board.getUser().getUsername();
        this.recommend = board.getRecommend();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.count = board.getCount();
        this.createTime = board.getCreateTime();
        this.boardFiles = board.getBoardFiles();
        this.interest = board.getInterest().getName();
    }

}
