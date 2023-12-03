package it.antoniofrisenda.centralizedauthservice.repository;

import it.antoniofrisenda.centralizedauthservice.model.ApiKey;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {

    Optional<ApiKey> findBySecret(String secret);

}
