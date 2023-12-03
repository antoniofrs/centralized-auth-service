package it.antoniofrisenda.centralizedauthservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CentralizedAuthServiceException extends RuntimeException{

    private final HttpStatus httpStatus;

    public CentralizedAuthServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CentralizedAuthServiceException(String message){
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
