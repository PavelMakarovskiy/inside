package com.inside.insidetask.tests;

import com.inside.insidetask.user.User;
import com.inside.insidetask.user.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@Transactional
public class TestLoginController {
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void configure() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User user = new User();
        user.setName("Lesia");
        user.setPassword(passwordEncoder.encode("perfect"));
        userRepository.save(user);
    }

    @Test
    public void sendLoginRequestWithCorrectCredentialsThenReturnCorrectResponseWithToken() throws Exception {
        String requestBody = "{\"name\":\"Lesia\", \"password\":\"perfect\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(Matchers.notNullValue()));
    }

    @Test
    public void sendLoginRequestWithInCorrectCredentialsThenReturnBadCredentialsException() throws Exception {
        String requestBody = "{\"name\":\"Lesia\", \"password\":\"general\"}";

        Assertions.assertThrows(BadCredentialsException.class, () -> {
            try {
                mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.token").value(Matchers.notNullValue()));
            } catch (NestedServletException e) {
                throw e.getCause();
            }
        });
    }
}
