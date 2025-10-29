package com.pacifique.security.review;

import com.pacifique.security.review.costomerUser.WithCustomUser;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Main Test Class")
public class MainTests extends MysqlTestBase{

    @Autowired
    private MockMvc mvc;
    @Autowired
    private IUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = User.builder().email("marry@gmail.com")
                .password("password")
                .permissions(Role.ADMIN.getPermissions())
                .role(Role.ADMIN.name()).build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("Hello Unauthenticated Test")
    void helloUnauthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(content().json("{\"message\":\"Full authentication is required to access this resource\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Hello Authenticated Test")
    @WithMockUser
    void helloAuthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(content().string("Hello!"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Test A user with a name")
    @WithMockUser(username = "marry")
    void testAUserWithAName() throws Exception {
        mvc.perform(get("/hello/user"))
                .andExpect(content().string("Hello, marry"))
                .andExpect(status().isOk());
    }

        /*
    The difference between using annotations and the RequestPostProcessor to create the
test security environment. When using annotations, the framework sets up the test security environment
first. When using a RequestPostProcessor, the test request is created and then changed to define
other constraints such as the test security environment. In the figure, the points where the framework
applies the test security environment are shaded.

     */

    @Test
    void testAUserWithNameNoMockUser() throws Exception {
        mvc.perform(get("/hello/user")
                        .with(user("marry"))
                ).andExpect(content().string("Hello, marry"))
                .andExpect(status().isOk());
    }

    // annotation with userDetails

    @Test
    @WithUserDetails("marry@gmail.com")
    void testAUserWithWithUserDetails() throws Exception {
        mvc.perform(get("/hello/user"))
                .andExpect(content().string("Hello, marry@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomUser
    void testAUserWithCustomUser() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomUser(username = "marry")
    void testAUserWithCustomUserMerry() throws Exception {
        mvc.perform(get("/hello/user"))
                .andExpect(content().string("Hello, marry"))
                .andExpect(status().isOk());
    }

}
