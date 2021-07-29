package com.community.communityback.controller;




import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.community.communityback.config.auth.PrincipalDetails;
import com.community.communityback.dto.Board.BoardListDTO;
import com.community.communityback.dto.Board.BoardResponseDTO;
import com.community.communityback.dto.Board.ReplyResponseDTO;
import com.community.communityback.model.Board;
import com.community.communityback.model.Interest;
import com.community.communityback.model.Reply;
import com.community.communityback.model.User;
import com.community.communityback.service.BoardService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public BoardListDTO<?> getBoardList(
        @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value="keyword", required = false) String keyword
    ){

        System.out.println("키워드:"+keyword);
        Page<Board> boards;
        if(keyword!=null){
            System.out.println("키워드:"+keyword);
            boards = boardService.글검색하기(pageable, keyword);
            System.out.println("목록:"+boards.getContent().size());
        }else{
            boards = boardService.전체글불러오기(pageable);
        }

        
            
        List<Integer> pages = new ArrayList<>();
        Integer total = boards.getTotalPages();
        Integer current = pageable.getPageNumber();
        int size = 10;
        // int strat = current - current%size;
        // int end = current + (size-current%size);
        int half = size / 2;
        int strat = 0;
        int end = size;
        int odd = size%2;
        if(current>half){
           strat = current + half< total ? current - half :
        total-size;
            end = current + half<total ? current + (half) + odd : total;
        }

        if(strat<0){
            strat=0;
        }

        for(int i=strat; i<end && i<total; i++){
            pages.add(i);
        }


        return new BoardListDTO<>(HttpStatus.OK.value(), current, total, pages, boards.getContent());
    }

    @PostMapping("/board/list/interest")
    public BoardListDTO<?> searchBoard(@RequestBody Interest interset, @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable){

      
        Page<Board> boards = boardService.관심사글가져오기(pageable, interset.getId());

     

        List<Integer> pages = new ArrayList<>();
        Integer total = boards.getTotalPages();
        Integer current = pageable.getPageNumber();
        int size = 10;
        // int strat = current - current%size;
        // int end = current + (size-current%size);
        int half = size / 2;
        int strat = 0;
        int end = size;
        int odd = size%2;
        if(current>half){
           strat = current + half< total ? current - half :
        total-size;
            end = current + half<total ? current + (half) + odd : total;
        }

        if(strat<0){
            strat=0;
        }

        for(int i=strat; i<end && i<total; i++){
            pages.add(i);
        }


        return new BoardListDTO<>(HttpStatus.OK.value(), current, total, pages, boards.getContent());
    }


    @GetMapping("/board/list/recommend")
    public BoardListDTO<?> getBoardListRecommend(
        @PageableDefault(size=10, sort="recommend", direction = Sort.Direction.DESC) Pageable pageable
    ){

  
        Page<Board> boards = boardService.전체글불러오기(pageable);
 

        
            
        List<Integer> pages = new ArrayList<>();
        Integer total = boards.getTotalPages();
        Integer current = pageable.getPageNumber();
        int size = 10;
        // int strat = current - current%size;
        // int end = current + (size-current%size);
        int half = size / 2;
        int strat = 0;
        int end = size;
        int odd = size%2;
        if(current>half){
           strat = current + half< total ? current - half :
        total-size;
            end = current + half<total ? current + (half) + odd : total;
        }

        if(strat<0){
            strat=0;
        }

        for(int i=strat; i<end && i<total; i++){
            pages.add(i);
        }


        return new BoardListDTO<>(HttpStatus.OK.value(), current, total, pages, boards.getContent());
    }


    @GetMapping("/board/{id}")
    public BoardResponseDTO getBoard(@PathVariable Long id){

        System.out.println("게시글번호:"+id);
        Board BoardEntity = boardService.글불러오기(id);
       
       
        // List<byte[]> files = new ArrayList<>();
        //     BoardEntity.getBoardFiles().forEach((uplodedfile)->{
        //         try{
        //         File file = new File(uplodedfile.getFilePath());
               
        //        files.add(FileCopyUtils.copyToByteArray(file));
                
        //         } catch(IOException e){
        //             e.printStackTrace();
        //         }
        //     });
        return new BoardResponseDTO (HttpStatus.OK.value(), BoardEntity);
    }


    @GetMapping("/board/{id}/reply/list")
    public ReplyResponseDTO<?> getReply(@PathVariable Long id,@PageableDefault(size=10

    ) Pageable pageable){


   
        Page<Reply> replys = boardService.댓글가져오기(id, pageable);
       

        List<Integer> pages = new ArrayList<>();

        Integer total = replys.getTotalPages();
        Integer current = pageable.getPageNumber();
        int size = 10;
        // int strat = current - current%size;
        // int end = current + (size-current%size);
        int half = size / 2;
        int strat = 0;
        int end = size;
        int odd = size%2;
        if(current>half){
           strat = current + half< total ? current - half :
        total-size;
            end = current + half<total ? current + (half) + odd : total;
        }

        if(strat<0){
            strat=0;
        }

        for(int i=strat; i<end && i<total; i++){
            pages.add(i);
        }


        return new ReplyResponseDTO<>(HttpStatus.OK.value(), total-1, pages, replys.getContent());
    }


    @GetMapping("/board/search/{keyword}")
    public BoardListDTO<?> searchBoard(@PathVariable String keyword, @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable){

        System.out.println("키워드:"+keyword);
        Page<Board> BoardEntity = boardService.글검색하기(pageable, keyword);

        Integer total = BoardEntity.getTotalPages();
        Integer current = pageable.getPageNumber();

       
        // List<byte[]> files = new ArrayList<>();
        //     BoardEntity.getBoardFiles().forEach((uplodedfile)->{
        //         try{
        //         File file = new File(uplodedfile.getFilePath());
               
        //        files.add(FileCopyUtils.copyToByteArray(file));
                
        //         } catch(IOException e){
        //             e.printStackTrace();
        //         }
        //     });
        return new BoardListDTO<>(HttpStatus.OK.value(), current, total, null, BoardEntity.getContent());
    }


    

    
    
}
