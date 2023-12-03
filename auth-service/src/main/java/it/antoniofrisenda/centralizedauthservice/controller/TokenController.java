package it.antoniofrisenda.centralizedauthservice.controller;

import it.antoniofrisenda.centralizedauthservice.dto.AuthenticationResponse;
import it.antoniofrisenda.centralizedauthservice.service.AuthenticationService;
import it.antoniofrisenda.centralizedauthservice.shared.model.SessionDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/token")
public class TokenController {

    private final AuthenticationService authenticationService;

    @GetMapping("/refresh/{refreshToken}")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse refresh(@PathVariable String refreshToken){
        return authenticationService.refresh(refreshToken);
    }

    @GetMapping("/session")
    @ResponseStatus(HttpStatus.OK)
    public SessionDetails session() {
        return authenticationService.session();
    }

}
