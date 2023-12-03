package it.antoniofrisenda.centralizedauthservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CentralizedAuthServiceException {
    public InvalidTokenException() {
        super("Provided  token is not valid", HttpStatus.BAD_REQUEST);
    }
}
