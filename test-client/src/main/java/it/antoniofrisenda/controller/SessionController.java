package it.antoniofrisenda.controller;

import it.antoniofrisenda.centralizedauthservice.shared.model.SessionDetails;
import it.antoniofrisenda.security.token.SessionToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class SessionController {

    @GetMapping("/session")
    @ResponseStatus(HttpStatus.OK)
    public SessionDetails session() {

        SessionToken authentication = (SessionToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getSessionDetails();

    }

}
