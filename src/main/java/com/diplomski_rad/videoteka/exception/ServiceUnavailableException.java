package com.diplomski_rad.videoteka.exception;
public class ServiceUnavailableException extends RuntimeException {

    private static final long serialVersionUID = -4146860850978717726L;

    public ServiceUnavailableException(final String message) {
        super(message);
    }
}
