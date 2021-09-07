package com.cvi.controller.exceptionhandler;

import com.cvi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CVIControllerAdvice {

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage commonException(CommonException e) {
        log.info("CommonException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage notFoundException(NotFoundException e) {
        log.info("NotFoundException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessage unAuthorizedException(UnAuthorizedException e) {
        log.info("UnAuthorizedException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(MappingFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage mappingException(Exception e) {
        log.info("MappingFailureException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage mappingException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage serverException(Exception e) {
        log.error("Internal Server Exception: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }
}
