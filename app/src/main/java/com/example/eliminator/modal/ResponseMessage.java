package com.example.eliminator.modal;

public class ResponseMessage {
    private String error,message;

    public ResponseMessage(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

