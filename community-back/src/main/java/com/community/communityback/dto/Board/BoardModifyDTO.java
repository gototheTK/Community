package com.community.communityback.dto.Board;

import java.sql.Timestamp;
import java.util.List;

import com.community.communityback.model.Board;
import com.community.communityback.model.BoardFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardModifyDTO<T> {
    private Long id;
    private String title;
    private String content;
    private List<BoardFile> boardFiles;
    

    public Board getBoard(){

        return Board.builder().id(id).title(title).content(content).boardFiles(boardFiles).build();

    }
}
