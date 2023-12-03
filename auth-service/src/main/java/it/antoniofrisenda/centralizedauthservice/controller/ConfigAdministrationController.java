package it.antoniofrisenda.centralizedauthservice.controller;

import it.antoniofrisenda.centralizedauthservice.model.Config;
import it.antoniofrisenda.centralizedauthservice.service.ConfigService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/administration/config")
public class ConfigAdministrationController {

    private final ConfigService configService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Config getConfig(){
        return configService.getConfig();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Config saveConfig(@RequestBody Config config){
        return configService.saveConfig(config);
    }

}
