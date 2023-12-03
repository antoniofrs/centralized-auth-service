package it.antoniofrisenda.centralizedauthservice.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import io.mongock.runner.springboot.EnableMongock;
import it.antoniofrisenda.centralizedauthservice.model.ApiKey;
import it.antoniofrisenda.centralizedauthservice.model.Config;
import it.antoniofrisenda.centralizedauthservice.model.Role;
import it.antoniofrisenda.centralizedauthservice.repository.ApiKeyRepository;
import it.antoniofrisenda.centralizedauthservice.repository.ConfigRepository;
import it.antoniofrisenda.centralizedauthservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Slf4j
@AllArgsConstructor
@EnableMongock
@Configuration
@ChangeUnit(id = "initializer_001", order = "1", author = "spring")
public class MongoChangelog {

    private final ConfigRepository configRepository;
    private final RoleRepository roleRepository;
    private final ApiKeyRepository apiKeyRepository;

    @Execution
    public void onApplicationReady() {

        apiKeyRepository.save(ApiKey.builder()
                .client("postman")
                .secret("2845c403-0df0-4fe9-ba44-80e21a66e394")
                .build());

        roleRepository.save(Role.builder()
                .name("NOBODY")
                .authorities(Set.of("READ_NOTHING", "WRITE_NOTHING"))
                .build());

        configRepository.save(Config.builder()
                .defaultUserRole("NOBODY")
                .build());

    }

    @RollbackExecution
    public void rollbackExecution() {
        log.error("Cannot initialize mongoDB");
    }


}
