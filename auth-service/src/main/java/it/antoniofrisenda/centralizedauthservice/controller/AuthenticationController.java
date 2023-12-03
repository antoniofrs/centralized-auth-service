package it.antoniofrisenda.centralizedauthservice.controller;

import it.antoniofrisenda.centralizedauthservice.dto.AuthenticationResponse;
import it.antoniofrisenda.centralizedauthservice.dto.LoginPayload;
import it.antoniofrisenda.centralizedauthservice.dto.RegistrationPayload;
import it.antoniofrisenda.centralizedauthservice.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/user")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody @Valid LoginPayload loginPayload) {
        return authenticationService.login(loginPayload);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid RegistrationPayload registrationPayload) {
        authenticationService.signup(registrationPayload);
    }

    @GetMapping("/verification/{verificationToken}")
    @ResponseStatus(HttpStatus.OK)
    public void emailVerification(@PathVariable String verificationToken) {
        authenticationService.verifyAccount(verificationToken);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestParam String refreshToken) {
        authenticationService.logout(refreshToken);
    }

}
