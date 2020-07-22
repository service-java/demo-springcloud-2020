package com.spring.cloud.ms.common.vo;

public class SuccessOrFailureMessage {

    private boolean success = false;
    private String message = null;

    public SuccessOrFailureMessage() {
    }

    public SuccessOrFailureMessage(boolean success,String message ) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
