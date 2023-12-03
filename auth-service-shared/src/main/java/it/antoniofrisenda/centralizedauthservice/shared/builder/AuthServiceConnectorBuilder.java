package it.antoniofrisenda.centralizedauthservice.shared.builder;

import it.antoniofrisenda.centralizedauthservice.shared.connector.AuthServiceConnector;
import it.antoniofrisenda.centralizedauthservice.shared.connector.AuthServiceConnectorImpl;

public class AuthServiceConnectorBuilder {

    public static AuthServiceConnector simpleAuthServiceConnector() {
        return new AuthServiceConnectorImpl();
    }

}
