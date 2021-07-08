package com.backjoongwon.cvi.common.exception;

public class ExceptionMessage {

    private String message;

    protected ExceptionMessage() {
    }

    public ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
