package it.antoniofrisenda.centralizedauthservice.mapper;


import it.antoniofrisenda.centralizedauthservice.dto.RegistrationPayload;
import it.antoniofrisenda.centralizedauthservice.dto.UserDto;
import it.antoniofrisenda.centralizedauthservice.security.model.Principal;
import it.antoniofrisenda.centralizedauthservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;

    public User fromRegistrationPayload(RegistrationPayload registrationPayload){

        String encodedPassword = passwordEncoder.encode(registrationPayload.getPassword());

        return User.builder()
                .firstName(registrationPayload.getFirstName())
                .lastName(registrationPayload.getLastName())
                .email(registrationPayload.getEmail())
                .username(registrationPayload.getUsername())
                .password(encodedPassword)
                .creation(Instant.now())
                .enabled(false)
                .build();

    }

    public UserDto fromUser(User user){

        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .creation(Instant.now())
                .enabled(false)
                .build();

    }

    public Principal toPrincipal(User user) {
        return Principal.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
