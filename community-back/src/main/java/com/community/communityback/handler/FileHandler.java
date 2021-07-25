package com.community.communityback.handler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.community.communityback.model.Board;
import com.community.communityback.model.BoardFile;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileHandler {
    
    public List<BoardFile> parseFileInfo(Board board, List<MultipartFile> files) throws Exception{

        List<BoardFile> fileList = new ArrayList<>();

        if(files.isEmpty()){
            return fileList;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        String absolutePath = new File("").getAbsolutePath() + "/";

        String path = "images/" + current_date;
        File file = new File(path);

        if(!file.exists()){
            file.mkdirs();
        }

        for(MultipartFile multipartFile : files){

            if(!multipartFile.isEmpty()){

                String contentType = multipartFile.getContentType();
                String originalFileExtension;

                if(ObjectUtils.isEmpty(contentType)){
                    break;
                }else{
                    if(contentType.contains("image/jpeg")){
                        originalFileExtension = ".jpg";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtension = ".png";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExtension = ".gif";
                    }
                    // 다른 파일 명이면 아무 일 하지 않는다
                    else{
                        break;
                    }
                }

                String new_file_name = Long.toString(System.nanoTime()) +originalFileExtension;

                BoardFile boardFile = BoardFile.builder().board(board).fileName(multipartFile.getOriginalFilename()).filePath(path+"/"+new_file_name).fileSize(multipartFile.getSize()).build();
            fileList.add(boardFile);

            // 저장된 파일로 변경하여 이를 보여주기 위함
            file = new File(absolutePath + path + "/" + new_file_name);
            multipartFile.transferTo(file);

            }

        }


        return fileList;

    }

}
