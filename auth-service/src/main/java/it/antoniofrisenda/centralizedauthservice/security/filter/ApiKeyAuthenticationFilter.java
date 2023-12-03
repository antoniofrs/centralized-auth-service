package it.antoniofrisenda.centralizedauthservice.security.filter;

import it.antoniofrisenda.centralizedauthservice.constants.Authorities;
import it.antoniofrisenda.centralizedauthservice.repository.ApiKeyRepository;
import it.antoniofrisenda.centralizedauthservice.security.token.ApiKeyAuthenticationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String X_API_KEY = "X-Api-Key";
    private final ApiKeyRepository apiKeyRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String headerApiKey = request.getHeader(X_API_KEY);
        if (StringUtils.hasText(headerApiKey)) {
            apiKeyRepository.findBySecret(headerApiKey)
                    .ifPresent(apiKey -> {
                        ApiKeyAuthenticationToken apiToken =
                                new ApiKeyAuthenticationToken(apiKey.getClient(), apiKey.getSecret(),
                                        AuthorityUtils.createAuthorityList(Authorities.SUPER_USER_AUTHORITY));
                        SecurityContextHolder.getContext().setAuthentication(apiToken);
                    });
        }

        filterChain.doFilter(request, response);

    }
}
