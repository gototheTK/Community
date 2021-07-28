package com.community.communityback.service;



import java.util.ArrayList;
import java.util.List;


import com.community.communityback.handler.FileHandler;
import com.community.communityback.model.Board;
import com.community.communityback.model.BoardFile;
import com.community.communityback.model.RecommendBoard;
import com.community.communityback.model.Reply;
import com.community.communityback.model.User;
import com.community.communityback.repository.BoardFileRepository;
import com.community.communityback.repository.BoardRepository;
import com.community.communityback.repository.InterestRepository;
import com.community.communityback.repository.RecommendBoardRepository;
import com.community.communityback.repository.ReplyRepository;
import com.community.communityback.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final RecommendBoardRepository recommendBoardRepository;
    private final FileHandler fileHandler;
    private final InterestRepository interestRepository;

    @Transactional(readOnly=true)
    public Page<Board> 전체글불러오기(Pageable pageable){ 
        return boardRepository.findAll(pageable);
    }


    @Transactional(readOnly=true)
    public Page<Board> 관심사글가져오기(Pageable pageable, Long interestId){ 
        return boardRepository.findAllByInterestId(pageable, interestId);
    }

    @Transactional
    public void 글쓰기(Board board, List<MultipartFile> files) throws Exception{

        System.out.println("글쓰기 서비스시작");
        
        if(files== null){

        }else{
            List<BoardFile> list = fileHandler.parseFileInfo(board, files);
            List<BoardFile> fileBeans = new ArrayList<>();
            for(BoardFile file: list){
                fileBeans.add(boardFileRepository.save(file));
            }
            board.setBoardFiles(fileBeans);
        }

        if(board.getInterest().getId()==null){


            board.setInterest(interestRepository.findById(1L).orElseThrow(()->{
                return new IllegalArgumentException("관심사가 없습니다.");
            }));
        }

        boardRepository.save(board);
    }

    @Transactional
    public void 글수정하기기(Board board, List<MultipartFile> files) throws Exception{

        Board entity = boardRepository.findById(board.getId()).orElseThrow(()->{
            return new IllegalArgumentException("해당하는 게시글이 존재하지 않습니다.");
        });

        if(files== null){

        }else{
            List<BoardFile> list = fileHandler.parseFileInfo(board, files);
            List<BoardFile> fileBeans = board.getBoardFiles();
            for(BoardFile file: list){
                fileBeans.add(boardFileRepository.save(file));
            }
            entity.setBoardFiles(fileBeans);
        }

        entity.setTitle(board.getTitle());
        entity.setContent(board.getContent());


    }

    @Transactional
    public void 글삭제하기(Long id){
        boardRepository.deleteById(id);
    }


    @Transactional
    public Board 글불러오기(Long id){
        
       Board BoardEntity =  boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당하는 글이 없습니다.");
       });

       BoardEntity.setCount(BoardEntity.getCount()+1);

        return BoardEntity;
    }

    @Transactional
    public Page<Board> 글검색하기(Pageable pageable, String keyword){

        return boardRepository.search(pageable, keyword);
    }

    @Transactional
    public Integer 글추천하기(Long id, User user){

        Board BoardEntity =  boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당하는 글이 없습니다.");
       });

       Boolean hasRecommend = recommendBoardRepository.findByBoardIdAndUserId(id, user.getId()).isPresent();

       if(hasRecommend){
        throw new IllegalArgumentException("이미 추천하셨습니다.");
       }
      
        recommendBoardRepository.save(RecommendBoard.builder().board(BoardEntity).user(user).build());
        BoardEntity.setRecommend(BoardEntity.getRecommend()+1);
 
       
        return BoardEntity.getRecommend();
    }


    @Transactional(readOnly=true)
    public Board 수정할글불러오기(Long id){
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당하는 글이 없습니다.");
       });
    }





    @Transactional
    public Integer 댓글쓰기(Long userId, Long boardId, Long parentId, String content ){

       replyRepository.replySave(userId, boardId, parentId, 0, 0,content);


        replyRepository.findAllByGroupIdIsNull().forEach((action)->{
            action.setGroupId(action.getId());
        });
        
        return  201;

    }


    @Transactional
    public Integer 대댓글쓰기(Long userId, Long boardId,Long parentId, String content ){

        


        Reply ReplyEntity = replyRepository.findById(parentId).orElseThrow(()->{
            return new IllegalArgumentException("댓글값이 없습니다.");
        });

          replyRepository.subReplySave(userId, boardId, ReplyEntity.getGroupId(),parentId, ReplyEntity.getStepNum()+1,ReplyEntity.getChildren().size()+ ReplyEntity.getRankNum(), content);

          List<Reply> list = replyRepository.findAllByGroupId(Sort.by("rankNum"),ReplyEntity.getGroupId());

          for(int i =0; i<list.size(); i++){

            list.get(i).setRankNum(i);

          }

                
        return 201;
    }


    @Transactional(readOnly=true)
    public Page<Reply> 댓글가져오기(Long boardId,Pageable pageable){

        

        return replyRepository.findAllByBoardId(boardId, pageable);
    }


   

}
