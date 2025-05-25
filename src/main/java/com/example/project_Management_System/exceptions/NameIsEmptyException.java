package com.example.project_Management_System.exceptions;

public class NameIsEmptyException extends RuntimeException {
    public NameIsEmptyException(String message) {
        super(message);
    }
}
