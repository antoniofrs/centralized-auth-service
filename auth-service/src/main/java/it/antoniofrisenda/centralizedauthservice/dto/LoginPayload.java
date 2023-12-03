package it.antoniofrisenda.centralizedauthservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginPayload {

    @NotEmpty(message = "Username cannot be null or empty")
    private String username;

    @NotEmpty(message = "Password cannot be null or empty")
    private String password;

}
