package com.community.communityback.controller.api;

import java.util.List;



import com.community.communityback.config.auth.PrincipalDetails;
import com.community.communityback.dto.Board.BoardModifyDTO;
import com.community.communityback.dto.Board.BoardRequestDTO;
import com.community.communityback.dto.Board.BoardResponseDTO;
import com.community.communityback.dto.Board.ReplyRequestDTO;
import com.community.communityback.model.Board;
import com.community.communityback.service.BoardService;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestPart;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


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

    @PutMapping("/api/board/modify")
    public BoardResponseDTO modfiyBoard(@RequestPart("board") BoardModifyDTO<?> boardModifyDTO, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws Exception{

        System.out.println("게시글정보:");

        boardService.글수정하기기(boardModifyDTO.getBoard(),files);
        return new BoardResponseDTO(HttpStatus.OK.value(), null);
    }

    @DeleteMapping("/api/board/delete/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id){

        boardService.글삭제하기(id);
        return new ResponseEntity( "해당하는 글이 삭제되었습니다.", HttpStatus.OK);
    }


    @PostMapping("/api/board/reply/write")
    public ResponseEntity<?> postReply(@RequestBody ReplyRequestDTO replyRequestDto, @AuthenticationPrincipal PrincipalDetails principal,@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC)Pageable pageable){

        boardService.댓글쓰기(principal.getUser().getId(), replyRequestDto.getBoardId(), replyRequestDto.getParentId(),replyRequestDto.getContent());

        return new ResponseEntity<> (200, HttpStatus.CREATED);
    }

    @PostMapping("/api/board/reply/writeto")
    public ResponseEntity<?> postReplyto(@RequestBody ReplyRequestDTO replyRequestDto, @AuthenticationPrincipal PrincipalDetails principal,@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC)Pageable pageable){

        return new ResponseEntity<> (boardService.대댓글쓰기(principal.getUser().getId(), replyRequestDto.getBoardId(),replyRequestDto.getParentId(),replyRequestDto.getContent()), HttpStatus.CREATED);
    }


    @GetMapping("/api/board/recommend/{id}")
    public ResponseEntity<?> postReply(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principal){


        return new ResponseEntity<>(boardService.글추천하기(id, principal.getUser()),HttpStatus.OK);

    }



    
    
}
