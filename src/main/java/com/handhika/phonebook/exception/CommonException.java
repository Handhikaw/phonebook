package com.handhika.phonebook.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommonException extends Exception{
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    private Exception originException;

    /**
     * Common Exception / exception in handle use case
     *
     * @param status HttpStatus
     * @param message Message want to display
     */
    public CommonException(HttpStatus status, String message){
        super(message);
        this.httpStatus = HttpStatus.valueOf(status.value());
        this.code = String.valueOf(HttpStatus.valueOf(status.value()).value());
        this.message = message;
    }

    /**
     * Other Exception not in handle it
     *
     * @param e Exception
     */
    public CommonException(Exception e) {
        super(e.getMessage());
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = "99";
        this.message = "Unknown Error: " + e.getClass().getSimpleName();
        this.originException = e;
    }
}