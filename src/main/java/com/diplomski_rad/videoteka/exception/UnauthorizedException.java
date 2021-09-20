package com.diplomski_rad.videoteka.exception;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -4146860850978717726L;

    public UnauthorizedException(final String message) {
        super(message);
    }
}
