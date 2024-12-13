package com.msoft.mek.exception;

public class CryptoExistsException extends Exception {
    public CryptoExistsException(String message) {
        super(message);
    }

    private static final Long serialVersionUID = 1L;
}