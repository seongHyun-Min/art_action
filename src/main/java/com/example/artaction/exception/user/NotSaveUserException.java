package com.example.artaction.exception.user;

public class NotSaveUserException extends RuntimeException{

    public NotSaveUserException(String message) {
        super(message);
    }
}
