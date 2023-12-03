package it.antoniofrisenda.centralizedauthservice.service;

import it.antoniofrisenda.centralizedauthservice.dto.AuthenticationResponse;
import it.antoniofrisenda.centralizedauthservice.dto.LoginPayload;
import it.antoniofrisenda.centralizedauthservice.dto.NotificationEmail;
import it.antoniofrisenda.centralizedauthservice.dto.RegistrationPayload;
import it.antoniofrisenda.centralizedauthservice.exception.InvalidRefreshTokenException;
import it.antoniofrisenda.centralizedauthservice.exception.InvalidTokenException;
import it.antoniofrisenda.centralizedauthservice.exception.UserAlreadyExistsException;
import it.antoniofrisenda.centralizedauthservice.mapper.UserMapper;
import it.antoniofrisenda.centralizedauthservice.model.User;
import it.antoniofrisenda.centralizedauthservice.model.VerificationToken;
import it.antoniofrisenda.centralizedauthservice.repository.UserRepository;
import it.antoniofrisenda.centralizedauthservice.repository.VerificationTokenRepository;
import it.antoniofrisenda.centralizedauthservice.security.JWTProvider;
import it.antoniofrisenda.centralizedauthservice.security.model.JWTPayload;
import it.antoniofrisenda.centralizedauthservice.security.token.JwtAuthenticationToken;
import it.antoniofrisenda.centralizedauthservice.shared.model.SessionDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final ConfigService configService;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final JWTProvider jwtProvider;

    public AuthenticationResponse login(LoginPayload loginPayload) {

        JwtAuthenticationToken authentication = (JwtAuthenticationToken) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginPayload.getUsername(), loginPayload.getPassword())
        );

        JWTPayload jwtPayload =  authentication.getJwtPayload();
        String token = jwtProvider.generateToken(jwtPayload);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(jwtPayload.getRefreshToken())
                .build();

    }

    @Transactional
    public void signup(RegistrationPayload registrationPayload) {

        String username = registrationPayload.getUsername();
        String email = registrationPayload.getEmail();

        userRepository.findByUsernameOrEmail(username, email)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });

        if (!StringUtils.hasText(username)) {
            username = String.join("#", email.split("@")[0],
                    Long.toString(Instant.now().getEpochSecond()));
            registrationPayload.setUsername(username);
        }

        User user = userMapper.fromRegistrationPayload(registrationPayload);
        user.setRole(configService.getDefaultUserRole());

        userRepository.save(user);

        // GENERATE VERIFICATION TOKEN
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiration(Instant.now().plus(30, ChronoUnit.MINUTES))
                .build();

        verificationTokenRepository.save(verificationToken);

        // SEND NOTIFICATION EMAIL
        NotificationEmail notificationEmail = NotificationEmail.builder()
                .subject("Verification email")
                .recipient(user.getEmail())
                .body("Click here to activate your account: " +
                        "http://localhost:8080/api/auth/user/verification/" + token)
                .build();

        mailService.sendMail(notificationEmail);

    }

    public void verifyAccount(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);

        User user = userRepository.findById(verificationToken.getUser().getId())
                .orElseThrow(InvalidTokenException::new);

        user.setEnabled(true);

        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);

    }

    public AuthenticationResponse refresh(String refreshToken) {

        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);

        refreshToken = UUID.randomUUID().toString();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        
        JWTPayload jwtPayload = jwtProvider.generatePayload(user);
        String token = jwtProvider.generateToken(jwtPayload);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();

    }

    public void logout(String refreshToken) {
        User found = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);
        found.setRefreshToken(null);
        userRepository.save(found);
    }

    public SessionDetails session() {

        JwtAuthenticationToken authentication = (JwtAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        JWTPayload jwtPayload = authentication.getJwtPayload();

        return SessionDetails.builder()
                .username(jwtPayload.getPrincipal().getUsername())
                .email(jwtPayload.getPrincipal().getEmail())
                .role(jwtPayload.getRoleDto().getName())
                .authorities(jwtPayload.getRoleDto().getAuthorities())
                .expirationTime(jwtPayload.getExpirationTime())
                .creationTime(jwtPayload.getCreationTime())
                .validityTime(jwtPayload.getValidityTime())
                .refreshToken(jwtPayload.getRefreshToken())
                .build();

    }
}
