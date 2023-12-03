package it.antoniofrisenda.centralizedauthservice.security.model;

import it.antoniofrisenda.centralizedauthservice.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JWTPayload {

    private Principal principal;
    private RoleDto roleDto;
    private Long expirationTime;
    private Long creationTime;
    private Long validityTime;
    private String refreshToken;

}
