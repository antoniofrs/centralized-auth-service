package it.antoniofrisenda.centralizedauthservice.repository;

import it.antoniofrisenda.centralizedauthservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsernameOrEmail(String username, String mail);

    Optional<User> findByUsername(String username);

    Optional<User> findByRefreshToken(String refreshToken);

    default Optional<User> findByUsernameOrEmail(String principal){
        return findByUsernameOrEmail(principal,principal);
    }

}
