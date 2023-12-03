package it.antoniofrisenda.centralizedauthservice.shared.connector;

import it.antoniofrisenda.centralizedauthservice.shared.model.SessionDetails;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthServiceConnectorImpl implements AuthServiceConnector{

    private final RestTemplate restTemplate;

    public AuthServiceConnectorImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public SessionDetails session(String authorization) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION , authorization);

        HttpEntity<Object> entity = new HttpEntity<>(null, headers);

        // BUILD URL WITH PARAMETERS
        String url = "http://localhost:8080/api/auth/token/session";

        ResponseEntity<SessionDetails> sessionDetails = null;
        try {
            sessionDetails = restTemplate.exchange(url, HttpMethod.GET, entity, SessionDetails.class);
        }
        catch (Exception ignored){
        }

        return sessionDetails != null ? sessionDetails.getBody() : null;

    }

}
