package development.twomicroservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import development.twomicroservices.dto.LoginDto;
import development.twomicroservices.dto.UserDto;
import development.twomicroservices.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private AuthService authService;

    private final String LOGIN_PATH = "/login";
    private final String LOGOUT_PATH = "/logout";
    private final String LOGIN = "Nikola";
    private final String BAD_LOGIN = "login";
    private final String PASSWORD = "user3";
    private final String validToken = "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJWaWthIiwiaWF0IjoxNzQ2MDk1ODUzL" +
            "CJleHAiOjE3NDYxMDQ0OTN9.80wdQtbIkTnB9Ydnq9zEin51vpC4F7rth1sudyPd47l0Br-Pn_dquvHMy6TrbvXF";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginSuccess() throws Exception {
        LoginDto loginRequest = new LoginDto(LOGIN, PASSWORD);
        String expectedToken = "someJwtToken";
        when(authService.login(any(UserDto.class))).thenReturn(expectedToken);

        mockMvc.perform(post(LOGIN_PATH)
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void loginBadCredentials() throws Exception {
        UserDto loginRequest = new UserDto(BAD_LOGIN, PASSWORD);

        mockMvc.perform(post(LOGIN_PATH)
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void logoutSuccess() throws Exception {
        mockMvc.perform(post(LOGOUT_PATH)
                        .header("auth-token", validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/login?logout"))
                .andExpect(status().is3xxRedirection());
    }
}