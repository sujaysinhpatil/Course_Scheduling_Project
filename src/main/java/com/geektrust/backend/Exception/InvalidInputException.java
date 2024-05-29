package com.geektrust.backend.Exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String msg) {
        super(msg);
    }
}
