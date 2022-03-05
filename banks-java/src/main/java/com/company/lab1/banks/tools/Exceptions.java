package com.company.lab1.banks.tools;

public class Exceptions extends RuntimeException {

    private Integer errorCode;

    public Exceptions(String message) {
        super(message);
    }

    public Exceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}