package it.antoniofrisenda.centralizedauthservice.shared.connector;

import it.antoniofrisenda.centralizedauthservice.shared.model.SessionDetails;


public interface AuthServiceConnector {

    SessionDetails session(String authorization);

}
