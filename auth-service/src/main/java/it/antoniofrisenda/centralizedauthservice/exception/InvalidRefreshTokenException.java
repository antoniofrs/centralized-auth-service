package it.antoniofrisenda.centralizedauthservice.exception;

import org.springframework.http.HttpStatus;


public class InvalidRefreshTokenException extends CentralizedAuthServiceException {
    public InvalidRefreshTokenException() {
        super("Provided refresh token is not valid", HttpStatus.BAD_REQUEST);
    }
}
