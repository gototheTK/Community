package com.community.communityback.dto.Board;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequestDTO {
    private Long BoardId;
    private Integer page;
    private String content;
    
}
