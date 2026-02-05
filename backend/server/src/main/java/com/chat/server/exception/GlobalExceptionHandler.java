package com.chat.server.exception;

import com.chat.server.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<ErrorResponse> getError(Exception e,HttpStatus status ){
        return ResponseEntity.status(status.value()).body(ErrorResponse.from(e,status));
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest(BadRequestException e){
        return getError(e,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException e){
        return getError(e,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorized(UnAuthorizedException e){
        return getError(e,HttpStatus.UNAUTHORIZED);
    }
}
