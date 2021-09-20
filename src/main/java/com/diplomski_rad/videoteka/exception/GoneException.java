package com.diplomski_rad.videoteka.exception;
public class GoneException extends RuntimeException {

    private static final long serialVersionUID = 6008678986917497181L;

    public GoneException(final String message) {
        super(message);
    }
}
