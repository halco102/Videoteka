package com.diplomski_rad.videoteka.exception;

public class DuplicateException extends RuntimeException{

    private static final long serialVersionUID = 1294634607348123805L;

    public DuplicateException(String message) {
        super(message);
    }

}
