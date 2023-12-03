package it.antoniofrisenda.centralizedauthservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CentralizedAuthServiceException {

    public UserNotFoundException() {
        super("User not found", HttpStatus.BAD_REQUEST);
    }
}
