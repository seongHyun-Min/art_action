package com.example.artaction.exception.artwork;

public class NotFoundArtWorkException extends RuntimeException {
    public NotFoundArtWorkException(String message) {
        super(message);
    }
}
