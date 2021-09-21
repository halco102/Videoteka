package com.diplomski_rad.videoteka.exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    static final Logger LOGGER = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleException(BadRequestException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(400, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessage handleException(DuplicateException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(409, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorMessage handleException(UnauthorizedException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(401, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorMessage handleException(ForbiddenException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(403, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage handleException(NotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(404, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorMessage handleException(NotAcceptableException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(406, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessage handleException(ConflictException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(409, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.GONE)
    @ResponseBody
    public ErrorMessage handleException(GoneException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(410, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorMessage handleException(UnprocessableEntityException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(422, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.LOCKED)
    @ResponseBody
    public ErrorMessage handleException(LockedException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(423, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage handleException(InternalServerErrorException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(500, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorMessage handleException(ServiceUnavailableException ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ErrorMessage(503, ex.getMessage());
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorMessage {

        @JsonProperty("code")
        private final int code;

        @JsonProperty("message")
        private final String message;

        public ErrorMessage(final int code, final String message) {
            super();
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
