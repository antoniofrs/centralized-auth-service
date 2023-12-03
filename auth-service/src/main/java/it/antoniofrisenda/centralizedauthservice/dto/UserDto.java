package it.antoniofrisenda.centralizedauthservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;

    private String username;

    private String email;
    private String password;

    private RoleDto role;

    private Instant creation;

    private boolean enabled;

}
