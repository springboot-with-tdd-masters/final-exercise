package com.advancejava.finalexercise.controller;

import com.advancejava.finalexercise.service.EmployeeService;
import com.advancejava.finalexercise.service.SkillService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
public class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @MockBean
    private SkillService skillService;// = new SkillServiceImpl();



    @Test
    @DisplayName("getAll")
    public void getAll() throws Exception {

        String request = "{\n" +
                "    \"description\": \"Copy Paste\",\n" +
                "    \"duration\": \"18\",\n" +
                "    \"lastUsed\": \"2022-06-03\"\n" +
                "    \n" +
                "}";
        //act
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/employees/1/skills")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkazVVZWRJVGcwaXJ0elFxeHJyRzZNcW5uMG80Y2pfZC1CWmg1ZFc1bWQ0In0.eyJqdGkiOiJkZjMzODBhOS1kNzAzLTQxMzItOTRmZi1lZjcwYTViZGRkOWQiLCJleHAiOjE2NTQyOTU5NTAsIm5iZiI6MCwiaWF0IjoxNjU0MjU5OTUwLCJpc3MiOiJodHRwOi8vNS4xODEuMjE3LjIwMzo4MDgwL2F1dGgvcmVhbG1zL1NwcmluZ0Jvb3RLZXljbG9hayIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJiZDhjYjZhNy0yNGY2LTRiMmQtOWE5OS1mODM4ZmRlYzk0NWMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJsb2dpbi1hcHAiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiJlMjQ4ZmVhZi0yOTFjLTRkNzUtODRjNS05OTRkOGZiOWZiODciLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6MzAwMC8iXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyMSJ9.S7xy6pbbhSN3rW-o9IDgXnrhf4CnG1SEVMsu1QbvHsTqSu7UV9ofctvb_p-nL2WJpTF3dJ1Xfo_V_-Q4wjMhBJ5YmaEtKht0He32x26UOiI1Pr7TsngUQuB2dfW-D7zVYx0RNW622tjy3p9PfhqvjVpQnPx0xBLuQvj4GmLUmgA8a_0Jn89HwrwG60g5X7p8k4OVHKWbWHgB42V4QP65BSvu5fXLjAupwzEcEnOEs9LXMUJfpDhCN9lISVeCwZfftca9DzpSzhIuupJG93uFTo2sQuc1gZiG-7aLAiCyLTxPHLn-6FqxkvQshCZeccQFqWodWmW8hMXJgtMKxeIF6g"))
                        .andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    public void testAuth(){
        final var principal = mock(Principal.class);
        when(principal.getName()).thenReturn("user");

        final var account = mock(OidcKeycloakAccount.class);
        when(account.getRoles()).thenReturn(Set.of("offline_access", "uma_authorization"));
        when(account.getPrincipal()).thenReturn(principal);

        final var authentication = mock(KeycloakAuthenticationToken.class);
        when(authentication.getAccount()).thenReturn(account);

    }
}
