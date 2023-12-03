package it.antoniofrisenda.centralizedauthservice.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import it.antoniofrisenda.centralizedauthservice.exception.CentralizedAuthServiceException;
import it.antoniofrisenda.centralizedauthservice.mapper.RoleMapper;
import it.antoniofrisenda.centralizedauthservice.model.User;
import it.antoniofrisenda.centralizedauthservice.security.model.JWTPayload;
import it.antoniofrisenda.centralizedauthservice.security.model.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTProvider {

    private KeyStore keyStore;

    private final ObjectMapper objectMapper;
    private final RoleMapper roleMapper;

    @Value("${app.token.expiration-time}")
    private Long tokenValidityTime;

    @Value("${app.token.key-store.password}")
    private String password;

    @Value("${app.token.key-store.alias}")
    private String keyStoreAlias;

    private static final String CLAIM_PAYLOAD = "jwtPayload";

    @PostConstruct
    public void init() {

        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream input = getClass().getResourceAsStream("/keystore.jks");
            keyStore.load(input, password.toCharArray());
        } catch (Exception e) {
            log.error("Cannot load keystore");
        }

    }

    public String generateToken(JWTPayload jwtPayload) {

        Instant currentInstant = Instant.now();
        Date expirationTime = Date.from(currentInstant.plusMillis(tokenValidityTime));

        return Jwts.builder()
                .setSubject(jwtPayload.getPrincipal().getUsername())
                .claim(CLAIM_PAYLOAD, serialize(jwtPayload))
                .signWith(getPrivateKey())
                .setExpiration(expirationTime)
                .compact();

    }

    public JWTPayload generatePayload(User user) {

        Instant currentInstant = Instant.now();
        Date expirationTime = Date.from(currentInstant.plusMillis(tokenValidityTime));

        Principal principal = Principal.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        return JWTPayload.builder()
                .principal(principal)
                .roleDto(roleMapper.toDto(user.getRole()))
                .validityTime(tokenValidityTime)
                .creationTime(currentInstant.getEpochSecond())
                .expirationTime(expirationTime.getTime())
                .refreshToken(user.getRefreshToken())
                .build();
    }


    public JWTPayload getPayload(String jwt) {

        String strPayload = Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .get(CLAIM_PAYLOAD, String.class);

        return deserialize(strPayload);

    }


    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate(keyStoreAlias).getPublicKey();
        } catch (KeyStoreException e) {
            throw new JwtException("Cannot load public key");
        }
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(keyStoreAlias, password.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new JwtException("Cannot load private key");
        }
    }

    private String serialize(JWTPayload principal) {
        try {
            return objectMapper.writeValueAsString(principal);
        } catch (JsonProcessingException e) {
            throw new CentralizedAuthServiceException("Internal");
        }
    }

    private JWTPayload deserialize(String strPrincipal) {
        try {
            return objectMapper.readValue(strPrincipal, JWTPayload.class);
        } catch (JsonProcessingException e) {
            throw new CentralizedAuthServiceException("internal");
        }
    }


    public Long getTokenValidityTime() {
        return tokenValidityTime;
    }
}
