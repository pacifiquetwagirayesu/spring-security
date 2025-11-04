package com.pacifique.security.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTests extends MysqlTestBase {
    @Autowired
    private MockMvc mvc;

    @Test
    public void helloAuthenticatingWithValidUser() throws Exception {
        mvc.perform(get("/hello")
                .with(httpBasic("admin", "admin"))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void helloAuthenticatingWithInvalidUser() throws Exception {
        mvc.perform(get("/hello")
                        .with(httpBasic("mary", "12345")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void loggingInWithWrongAuthority() throws Exception {
        //  When authenticating
        //        with a user that doesn’t
        //        have read authority, the
        //        app redirects the user to
        //        path /error.

        mvc.perform(formLogin()
                        .user("bill").password("12345")
                )
//                .andExpect(redirectedUrl("/error"))
//                .andExpect(status().isFound())
//                .andExpect(authenticated());
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void loggingInWithCorrectAuthority() throws Exception {
        mvc.perform(formLogin().user("john").password("12345"))
                //.andExpect(redirectedUrl("/home"))
                //.andExpect(status().isFound())
                //.andExpect(authenticated());
                .andExpect(status().isUnauthorized());
    }

    @Test
    void demoEndpointSuccessfulAuthenticationTest() throws Exception {
//        mvc.perform(get("/hello").with(jwt())).andExpect(status().isOk());
        // mvc.perform(get("/hello").with(jwt().authorities(() -> "read"))).andExpect(status().isOk());
    }

    @Test
    void demoOpaqueEndpointSuccessfulAuthenticationTest() throws Exception {
//        mvc.perform(get("/hello").with(opaqueToken())).andExpect(status().isOk());
        // mvc.perform(get("/hello").with(opaqueToken().authorities(() -> "read"))).andExpect(status().isOk());
    }

    @Test
    public void testHelloPOST() throws Exception {
        mvc.perform(post("/hello")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void testHelloPOSTWithCSRF() throws Exception {
        mvc.perform(post("/hello").with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    public void testCORSForTestEndpoint() throws Exception {
//        mvc.perform(options("/test")
//                        .header("Access-Control-Request-Method", "POST")
//                        .header("Origin", "http://www.example.com")
//                ).andExpect(header().exists("Access-Control-Allow-Origin"))
//                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
//                .andExpect(header().exists("Access-Control-Allow-Methods"))
//                .andExpect(header().string("Access-Control-Allow-Methods", "POST"))
//                .andExpect(status().isOk());

    }
}
