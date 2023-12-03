package it.antoniofrisenda.centralizedauthservice.service;

import it.antoniofrisenda.centralizedauthservice.model.User;
import it.antoniofrisenda.centralizedauthservice.model.VerificationToken;
import it.antoniofrisenda.centralizedauthservice.repository.UserRepository;
import it.antoniofrisenda.centralizedauthservice.repository.VerificationTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Test should pass if the account is verified")
    void verifyAccount() {

        // GIVEN

        VerificationToken verificationToken = VerificationToken.builder()
                .user(User.builder().id("id").build())
                .build();

        User user = new User();

        // WHEN
        when(verificationTokenRepository.findByToken(anyString()))
                .thenReturn(Optional.of(verificationToken));
        when(userRepository.findById(anyString()))
                .thenReturn(Optional.of(user));

        // THEN
        authenticationService.verifyAccount("1234");

        assertTrue(user.isEnabled());

    }
}