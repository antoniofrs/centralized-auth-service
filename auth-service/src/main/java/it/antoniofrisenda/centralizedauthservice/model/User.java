package it.antoniofrisenda.centralizedauthservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    private String username;

    private String email;
    private String password;

    private String refreshToken;

    @DBRef
    private Role role;

    private Instant creation;

    private boolean enabled;

}
