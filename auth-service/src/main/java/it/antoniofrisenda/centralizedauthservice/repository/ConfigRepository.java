package it.antoniofrisenda.centralizedauthservice.repository;

import it.antoniofrisenda.centralizedauthservice.model.Config;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<Config, String> {

    default Config getConfig(){
        return findAll().get(0);
    }

}
