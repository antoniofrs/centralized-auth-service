package it.antoniofrisenda.centralizedauthservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.antoniofrisenda.centralizedauthservice.dto.LoginPayload;
import it.antoniofrisenda.centralizedauthservice.dto.RegistrationPayload;
import it.antoniofrisenda.centralizedauthservice.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    private final ObjectWriter objectWriter;


    private AuthenticationControllerTest() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    @DisplayName("LOGIN - The test should pass if the request was sent successfully")
    void login() throws Exception {

        LoginPayload loginPayload = LoginPayload.builder()
                .username("username")
                .password("password")
                .build();

        String requestJson = objectWriter.writeValueAsString(loginPayload);

        mockMvc.perform(get("/auth/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("REGISTRATION - The test should pass if the request was sent successfully")
    void signup() throws Exception {

        RegistrationPayload registrationPayload = RegistrationPayload.builder()
                .email("a").username("b")
                .firstName("c").lastName("d")
                .password("e")
                .build();

        String requestJson = objectWriter.writeValueAsString(registrationPayload);

        mockMvc.perform(post("/auth/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("LOGOUT - The test should pass if the request was sent successfully")
    void logout() throws Exception {

        String refreshToken = UUID.randomUUID().toString();

        mockMvc.perform(get("/auth/user/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("refreshToken", refreshToken))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("EMAIL VERIFICATION - The test should pass if the request was sent successfully")
    void emailVerification() throws Exception {

        String verificationToken = UUID.randomUUID().toString();

        mockMvc.perform(get("/auth/user/verification/" + verificationToken))
                .andExpect(status().isOk());

    }

}