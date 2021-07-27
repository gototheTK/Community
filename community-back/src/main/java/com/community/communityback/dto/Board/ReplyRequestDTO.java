package com.community.communityback.dto.Board;


import com.community.communityback.model.Reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequestDTO {
    private Long boardId;
    private Integer page;
    private Long parentId;
    private Long groupId;
    private String content;
}
