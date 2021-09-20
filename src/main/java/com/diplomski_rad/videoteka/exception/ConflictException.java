package com.diplomski_rad.videoteka.exception;
public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1294634607348123805L;

	public ConflictException(final String message) {
        super(message);
    }
}