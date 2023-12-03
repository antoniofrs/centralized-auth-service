package it.antoniofrisenda.centralizedauthservice.security.filter;

import it.antoniofrisenda.centralizedauthservice.constants.Authorities;
import it.antoniofrisenda.centralizedauthservice.security.JWTProvider;
import it.antoniofrisenda.centralizedauthservice.security.model.JWTPayload;
import it.antoniofrisenda.centralizedauthservice.security.token.JwtAuthenticationToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt)) {

            JWTPayload jwtPayload = jwtProvider.getPayload(jwt);

            JwtAuthenticationToken authenticationToken =
                    new JwtAuthenticationToken(jwtPayload,
                            AuthorityUtils.createAuthorityList(Authorities.SIMPLE_USER_AUTHORITY));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request, response);

    }

    protected String getJwtFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) &&
                bearerToken.toLowerCase().startsWith("bearer ")) {
            return bearerToken.substring(7);
        }

        return bearerToken;

    }
}
