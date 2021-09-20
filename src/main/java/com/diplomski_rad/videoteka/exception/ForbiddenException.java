package com.diplomski_rad.videoteka.exception;
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1835870919240521416L;

	public ForbiddenException(final String message) {
        super(message);
    }
}
