package it.antoniofrisenda.centralizedauthservice.exception;

import org.springframework.http.HttpStatus;

public class JwtException extends CentralizedAuthServiceException{

    public JwtException(String message, HttpStatus httpStatus) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
