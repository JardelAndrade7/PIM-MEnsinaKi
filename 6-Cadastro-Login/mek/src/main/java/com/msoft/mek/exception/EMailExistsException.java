package com.msoft.mek.exception;

public class EMailExistsException extends Exception {
    public EMailExistsException(String message) {
        super(message);
    }

    private static final Long serialVersionUID = 1L;
}
