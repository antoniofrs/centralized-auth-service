package it.antoniofrisenda.centralizedauthservice.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Principal {

    private String username;
    private String email;

}
