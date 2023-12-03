package it.antoniofrisenda.centralizedauthservice.advisor;

import it.antoniofrisenda.centralizedauthservice.exception.CentralizedAuthServiceException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({WebExchangeBindException.class})
    public ResponseEntity<String> handleMethodArgumentNotValid(WebExchangeBindException exception) {

        List<String> errorList = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>("Validation error: " + String.join(", ", errorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CentralizedAuthServiceException.class})
    public ResponseEntity<String> handleNotificationServiceExceptions(CentralizedAuthServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }

}
