package it.antoniofrisenda.config;

import it.antoniofrisenda.centralizedauthservice.shared.builder.AuthServiceConnectorBuilder;
import it.antoniofrisenda.centralizedauthservice.shared.connector.AuthServiceConnector;
import it.antoniofrisenda.security.filter.SessionFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated();

        http.addFilterBefore(new SessionFilter( authServiceConnector()) , UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthServiceConnector authServiceConnector(){
        return AuthServiceConnectorBuilder.simpleAuthServiceConnector();
    }

}
