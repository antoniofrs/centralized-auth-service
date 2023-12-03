package it.antoniofrisenda.centralizedauthservice.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends CentralizedAuthServiceException{

    public RoleNotFoundException(String roleName) {
        super("Role with name '" + roleName +"' not found", HttpStatus.BAD_REQUEST);
    }

}
