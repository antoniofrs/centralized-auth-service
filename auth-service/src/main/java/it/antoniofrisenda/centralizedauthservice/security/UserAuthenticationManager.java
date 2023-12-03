package it.antoniofrisenda.centralizedauthservice.security;

import it.antoniofrisenda.centralizedauthservice.constants.Authorities;
import it.antoniofrisenda.centralizedauthservice.model.User;
import it.antoniofrisenda.centralizedauthservice.repository.UserRepository;
import it.antoniofrisenda.centralizedauthservice.security.model.JWTPayload;
import it.antoniofrisenda.centralizedauthservice.security.token.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class UserAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        User user = userRepository.findByUsernameOrEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user") );

        if(! user.isEnabled()){
            throw new UsernameNotFoundException("Bad credentials");
        }

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            throw new UsernameNotFoundException("Bad credentials");
        }

        String refreshToken = UUID.randomUUID().toString();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        JWTPayload jwtPayload = jwtProvider.generatePayload(user);

        return new JwtAuthenticationToken(jwtPayload, AuthorityUtils.createAuthorityList(Authorities.SIMPLE_USER_AUTHORITY));
    }

}
