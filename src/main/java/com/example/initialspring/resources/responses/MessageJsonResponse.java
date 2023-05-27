package com.example.initialspring.resources.responses;

public class MessageJsonResponse {
    private String message;

    public MessageJsonResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
