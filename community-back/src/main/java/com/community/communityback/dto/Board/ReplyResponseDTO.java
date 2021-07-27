package com.community.communityback.dto.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyResponseDTO<T> {
    private Integer status;
    private Integer page;
    private T pages;
    private T replys;



}
