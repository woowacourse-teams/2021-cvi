package com.backjoongwon.cvi.common.exception;

import lombok.Getter;

@Getter
public class ExceptionMessage {

    private final String message;

    public ExceptionMessage(String message) {
        this.message = message;
    }
}
