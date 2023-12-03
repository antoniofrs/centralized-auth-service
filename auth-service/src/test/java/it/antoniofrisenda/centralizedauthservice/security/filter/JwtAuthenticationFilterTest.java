package it.antoniofrisenda.centralizedauthservice.security.filter;


import it.antoniofrisenda.centralizedauthservice.security.JWTProvider;
import it.antoniofrisenda.centralizedauthservice.security.model.JWTPayload;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private JWTProvider jwtProvider;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;

    @Test
    @DisplayName("Test should pass if the JWT is retrieved correctly")
    void getJwtFromRequest() {

        when(httpServletRequest.getHeader(anyString())).thenReturn("Bearer ABC");
        String jwtFromRequest = jwtAuthenticationFilter.getJwtFromRequest(httpServletRequest);
        assertEquals("ABC", jwtFromRequest);

    }

    @Test
    @DisplayName("Test should pass if method return null if the request is not authenticated")
    void getJwtFromRequest2() {

        when(httpServletRequest.getHeader(anyString())).thenReturn(null);
        String jwtFromRequest = jwtAuthenticationFilter.getJwtFromRequest(httpServletRequest);
        assertNull(jwtFromRequest);

    }

    @Test
    @DisplayName("Test should if a request without authentication header is not authenticated")
    void doFilterInternal() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);
        when(httpServletRequest.getHeader(anyString())).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse , filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());

    }

    @Test
    @DisplayName("Test should if a request without authentication header is not authenticated")
    void doFilterInternal2() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);
        when(httpServletRequest.getHeader(anyString())).thenReturn("bearer abc");
        when(jwtProvider.getPayload(anyString())).thenReturn(new JWTPayload());

        jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse , filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

    }

}