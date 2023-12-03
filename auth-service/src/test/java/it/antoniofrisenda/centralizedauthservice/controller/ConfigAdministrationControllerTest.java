package it.antoniofrisenda.centralizedauthservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.antoniofrisenda.centralizedauthservice.model.Config;
import it.antoniofrisenda.centralizedauthservice.service.ConfigService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ConfigAdministrationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ConfigAdministrationControllerTest {

    @MockBean
    private ConfigService configService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectWriter objectWriter;


    private ConfigAdministrationControllerTest() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    @DisplayName("Test should pass if the config is retrieved correctly")
    void getConfig() throws Exception {

        mockMvc.perform(get("/administration/config")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Test should pass if the config is set correctly")
    void saveConfig() throws Exception {

        Config config = Config.builder()
                .defaultUserRole("ADMIN")
                .build();

        String requestJson = objectWriter.writeValueAsString(config);

        mockMvc.perform(post("/administration/config")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

    }
}