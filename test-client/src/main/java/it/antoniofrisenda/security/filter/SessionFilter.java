package it.antoniofrisenda.security.filter;

import it.antoniofrisenda.centralizedauthservice.shared.connector.AuthServiceConnector;
import it.antoniofrisenda.centralizedauthservice.shared.model.SessionDetails;
import it.antoniofrisenda.security.token.SessionToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class SessionFilter extends OncePerRequestFilter {

    private final AuthServiceConnector authServiceConnector;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        try {
            SessionDetails session = authServiceConnector.session(authorization);

            if(session != null) {
                SessionToken sessionToken = new SessionToken(session);
                SecurityContextHolder.getContext().setAuthentication(sessionToken);
            }
        }
        catch (Exception e){
            logger.debug("Not Authorized");
        }


        filterChain.doFilter(request,response);


    }
}
