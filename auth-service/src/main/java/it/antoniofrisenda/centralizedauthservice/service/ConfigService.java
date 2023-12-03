package it.antoniofrisenda.centralizedauthservice.service;

import it.antoniofrisenda.centralizedauthservice.model.Config;
import it.antoniofrisenda.centralizedauthservice.model.Role;
import it.antoniofrisenda.centralizedauthservice.repository.ConfigRepository;
import it.antoniofrisenda.centralizedauthservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "config")
public class ConfigService {

    private final ConfigRepository configRepository;
    private final RoleRepository roleRepository;

    @Cacheable
    public Config getConfig(){
        return configRepository.getConfig();
    }

    @Cacheable
    public Role getDefaultUserRole(){

        String roleName = configRepository.getConfig().getDefaultUserRole();
        return roleRepository.findByName(roleName)
                .orElse(null);
    }

    @CacheEvict(allEntries = true)
    public Config saveConfig(Config config){
        Config found = configRepository.getConfig();
        config.setId(found.getId());
        return configRepository.save(config);
    }

}
