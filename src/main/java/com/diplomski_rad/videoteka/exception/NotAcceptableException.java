package com.diplomski_rad.videoteka.exception;
public class NotAcceptableException extends RuntimeException {

	private static final long serialVersionUID = 6008678986917497181L;

	public NotAcceptableException(final String message) {
        super(message);
    }
}
