package com.diplomski_rad.videoteka.exception;

public class InternalServerErrorException extends RuntimeException {

    private static final long serialVersionUID = -4146860850978717726L;

    public InternalServerErrorException(final String message) {
        super(message);
    }
}
