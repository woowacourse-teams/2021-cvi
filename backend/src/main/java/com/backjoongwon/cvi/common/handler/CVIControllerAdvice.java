package com.backjoongwon.cvi.common.handler;

import com.backjoongwon.cvi.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CVIControllerAdvice {

    private final static Logger LOG = LoggerFactory.getLogger(CVIControllerAdvice.class);

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage commonException(CommonException e) {
        LOG.info("CommonException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage notFoundException(NotFoundException e) {
        LOG.error("NotFoundException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessage unAuthorizedException(UnAuthorizedException e) {
        LOG.info("UnAuthorizedException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage serverException(Exception e) {
        LOG.error("Internal Server Exception: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(MappingFailureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage mappingException(Exception e) {
        LOG.error("Internal Server Exception: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage mappingException(MethodArgumentNotValidException e) {
        LOG.error("MethodArgumentNotValidException: {}", e.getMessage(), e);
        return new ExceptionMessage(e.getMessage());
    }
}
