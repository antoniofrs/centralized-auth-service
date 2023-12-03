package it.antoniofrisenda.centralizedauthservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.antoniofrisenda.centralizedauthservice.dto.RoleDto;
import it.antoniofrisenda.centralizedauthservice.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserAdministrationController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserAdministrationControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectWriter objectWriter;

    private UserAdministrationControllerTest() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    @DisplayName("FIND - Test should pass if the request was sent correctly")
    void find() throws Exception {

        mockMvc.perform(get("/administration/user/username/username-here")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("ADD ROLE - Test should pass if the request was sent correctly")
    void addRole() throws Exception {

        RoleDto roleDto = RoleDto.builder()
                .authorities(Set.of("a", "b", "c"))
                .name("name")
                .build();

        String strRoleDto = objectWriter.writeValueAsString(roleDto);

        mockMvc.perform(post("/administration/user/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(strRoleDto))
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("FIND ALL ROLES - Test should pass if the request was sent correctly")
    void findAllRoles() throws Exception {

        mockMvc.perform(get("/administration/user/role")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("FIND ROLE - Test should pass if the request was sent correctly")
    void findRole() throws Exception {

        mockMvc.perform(get("/administration/user/role/role-name-here")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}