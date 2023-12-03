package it.antoniofrisenda.centralizedauthservice.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessionDetails {

    private String username;
    private String email;
    private String role;
    private Set<String> authorities;
    private Long expirationTime;
    private Long creationTime;
    private Long validityTime;
    private String refreshToken;

}
