package com.pacifique.security.review.controller;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.pacifique.security.review.security.JwtAuthenticationFilter;
import com.pacifique.security.review.security.SuperAdminAuthFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = HomeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtAuthenticationFilter authenticationFilter;
    @MockitoBean
    private SuperAdminAuthFilter superAdminAuthFilter;

    @Test
    @DisplayName("Testing Home Page")
    void testHomePageTest() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(content().string(containsString("Spring Boot Advanced Security")));
    }
}
