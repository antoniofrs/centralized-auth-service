package it.antoniofrisenda.centralizedauthservice.config;

import it.antoniofrisenda.centralizedauthservice.constants.Authorities;
import it.antoniofrisenda.centralizedauthservice.repository.ApiKeyRepository;
import it.antoniofrisenda.centralizedauthservice.security.JWTProvider;
import it.antoniofrisenda.centralizedauthservice.security.filter.ApiKeyAuthenticationFilter;
import it.antoniofrisenda.centralizedauthservice.security.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final ApiKeyRepository apiKeyRepository;
    private final JWTProvider jwtProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/user/signup", "/api/auth/user/login", "/api/auth/user/verification/**", "/api/auth/token/refresh/**").permitAll()
                .antMatchers("/api/auth/user/**").hasAuthority(Authorities.SIMPLE_USER_AUTHORITY)
                .antMatchers("/api/auth/token/session").hasAuthority(Authorities.SIMPLE_USER_AUTHORITY)
                .antMatchers("/api/administration/**").hasAuthority(Authorities.SUPER_USER_AUTHORITY);

        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new ApiKeyAuthenticationFilter(apiKeyRepository),
                JwtAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
