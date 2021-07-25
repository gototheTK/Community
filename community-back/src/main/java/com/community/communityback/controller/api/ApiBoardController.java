package com.community.communityback.controller.api;

import java.util.List;

import javax.websocket.server.PathParam;

import com.community.communityback.config.auth.PrincipalDetails;
import com.community.communityback.dto.Board.BoardRequestDTO;
import com.community.communityback.dto.Board.BoardResponseDTO;
import com.community.communityback.dto.Board.ReplyRequestDTO;
import com.community.communityback.dto.Board.ReplyResponseDTO;
import com.community.communityback.model.Board;
import com.community.communityback.model.BoardFile;
import com.community.communityback.model.Reply;
import com.community.communityback.service.BoardService;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiBoardController {

    private final BoardService boardService;

    @PostMapping("/api/board/write")
    public BoardResponseDTO write(@RequestPart("board") BoardRequestDTO<?> boardRequestDTO, @RequestPart(value = "files", required = false) List<MultipartFile> files, @AuthenticationPrincipal PrincipalDetails principal) throws Exception{

            boardService.글쓰기(boardRequestDTO.getBoard(principal.getUser()), files);
        return new BoardResponseDTO(HttpStatus.CREATED.value(), null);
    }

    @PostMapping("/api/board/reply/write")
    public ResponseEntity<?> postReply(@RequestBody ReplyRequestDTO replyRequestDto, @AuthenticationPrincipal PrincipalDetails principal,@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC)Pageable pageable){


 
        return new ResponseEntity<> (boardService.댓글쓰기(principal.getUser().getId(), replyRequestDto.getBoardId(), replyRequestDto.getContent()), HttpStatus.CREATED);
    }

    @GetMapping("/api/board/recommend/{id}")
    public ResponseEntity<?> postReply(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principal){


        return new ResponseEntity<>(boardService.글추천하기(id, principal.getUser()),HttpStatus.OK);

    }



    
    
}
