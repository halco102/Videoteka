package com.diplomski_rad.videoteka.exception;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1294634607348123805L;

    public BadRequestException(final String message) {
        super(message);
    }
}
