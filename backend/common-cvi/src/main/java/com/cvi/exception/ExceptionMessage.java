package com.cvi.exception;

import lombok.Getter;

@Getter
public class ExceptionMessage {

    private final String message;

    public ExceptionMessage(String message) {
        this.message = message;
    }
}
