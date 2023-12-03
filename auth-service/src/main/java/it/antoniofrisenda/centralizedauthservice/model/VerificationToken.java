package it.antoniofrisenda.centralizedauthservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document("verification_token")
public class VerificationToken {

    @Id
    private String id;

    private String token;

    @DBRef
    private User user;

    private Instant expiration;

}
