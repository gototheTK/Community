package com.community.communityback.dto.Board;

import java.util.List;

import com.community.communityback.model.Board;
import com.community.communityback.model.BoardFile;
import com.community.communityback.model.Interest;
import com.community.communityback.model.User;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRequestDTO<T> {

    
    private String title;
    private String content;
    private Interest interest;
    
    public Board getBoard(User user){

        return Board.builder().title(title).content(content).user(user).count(0L).interest(interest).build();

    }

   

}
