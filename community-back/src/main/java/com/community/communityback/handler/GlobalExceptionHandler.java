package com.community.communityback.handler;

import com.community.communityback.dto.Board.ErrorResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public ErrorResponseDTO<String> handleArgumentExceoption(Exception e) {
    


        return new ErrorResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


    

}
