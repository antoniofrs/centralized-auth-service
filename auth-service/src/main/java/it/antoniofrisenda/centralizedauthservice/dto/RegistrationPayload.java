package it.antoniofrisenda.centralizedauthservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationPayload {

    @NotEmpty(message = "firstName cannot be null or empty")
    private String firstName;

    @NotEmpty(message = "lastName cannot be null or empty")
    private String lastName;

    @NotEmpty(message = "email cannot be null or empty")
    private String email;
    private String username;

    @NotEmpty(message = "password cannot be null or empty")
    private String password;


}
