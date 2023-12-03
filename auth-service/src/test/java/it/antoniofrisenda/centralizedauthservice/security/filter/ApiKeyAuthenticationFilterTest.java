package it.antoniofrisenda.centralizedauthservice.security.filter;

import it.antoniofrisenda.centralizedauthservice.model.ApiKey;
import it.antoniofrisenda.centralizedauthservice.repository.ApiKeyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiKeyAuthenticationFilterTest {

    @InjectMocks
    private ApiKeyAuthenticationFilter apiKeyAuthenticationFilter;

    @Mock
    private ApiKeyRepository apiKeyRepository;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;


    @Test
    @DisplayName("Test should pass if not authenticated request not set the security authentication")
    void doFilterInternal() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);
        when(httpServletRequest.getHeader(anyString())).thenReturn(null);

        apiKeyAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());


    }

    @Test
    @DisplayName("Test should pass if not authenticated request not set the security authentication")
    void doFilterInternal2() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);

        when(httpServletRequest.getHeader(anyString())).thenReturn("abc");
        when(apiKeyRepository.findBySecret(anyString())).thenReturn(Optional.of(new ApiKey()));

        apiKeyAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());


    }



}