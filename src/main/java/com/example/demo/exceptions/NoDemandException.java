package com.example.demo.exceptions;

public class NoDemandException extends RuntimeException {

    public NoDemandException() {

    }

    NoDemandException(String id) {
        super("Could not find employee " + id);
    }
}