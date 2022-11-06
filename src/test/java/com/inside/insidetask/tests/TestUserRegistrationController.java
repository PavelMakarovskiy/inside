package com.inside.insidetask.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@Transactional
public class TestUserRegistrationController {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void configure() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addUserWithCorrectParametersThenRevertSuccessfulAddingStatus() throws Exception {
        String requestBody = "{\"name\":\"Lesia\", \"password\":\"perfect\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void addUserWithCorrectParametersThenRevertSuccessfulAddingMessage() throws Exception {
        String requestBody = "{\"name\":\"Lesia\", \"password\":\"perfect\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("User Lesia added successfully."));
    }

    @Test
    public void addUserWithIncorrectParametersThenRevertException() throws Exception {
        String requestBody = "{\"name\":\"Lesia\", \"message\":\"perfect\"}";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            try {
                mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));
            } catch (NestedServletException e) {
                throw e.getCause();
            }
        });
    }

}
