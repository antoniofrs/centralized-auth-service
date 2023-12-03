package it.antoniofrisenda.centralizedauthservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.antoniofrisenda.centralizedauthservice.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = TokenController.class)
@AutoConfigureMockMvc(addFilters = false)
class TokenControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("REFRESH TOKEN - Test should pass if the request was sent correctly")
    void refresh() throws Exception {

        mockMvc.perform(get("/auth/token/refresh/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("SESSION - Test should pass if the request was sent correctly")
    void session() throws Exception {

        mockMvc.perform(get("/auth/token/session")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}