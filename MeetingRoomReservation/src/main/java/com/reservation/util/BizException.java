package com.reservation.util;

public class BizException extends Exception {

    private String errorMessage;

    public BizException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
