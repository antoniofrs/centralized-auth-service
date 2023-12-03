package it.antoniofrisenda.centralizedauthservice.exception;

import org.springframework.http.HttpStatus;


public class UserAlreadyExistsException extends CentralizedAuthServiceException {
    public UserAlreadyExistsException() {
        super("User already exists", HttpStatus.CONFLICT);
    }
}
