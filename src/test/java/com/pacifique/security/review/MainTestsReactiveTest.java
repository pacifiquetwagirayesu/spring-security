package com.pacifique.security.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
//@AutoConfigureWebTestClient
@Deprecated
public class MainTestsReactiveTest {
//    @Autowired
//    private WebTestClient client;

    @Test
    //@WithMockUser
    void testCallHelloWithValidUser() throws Exception {
//        client.get().uri("/hello").exchange()
//                .expectStatus().isOk();
    }

    @Test
    void testCallHelloWithValidUserWithMockUser() {
        // Before executing the GET
        //        request, mutates the call
        //        to use a mock user

       /*

       client.mutateWith(mockUser())
                .get()
                .uri("/hello")
                .exchange()
                .expectStatus().isOk();

        client.mutateWith(csrf())
                .post()
                .uri("/hello")
                .exchange()
                .expectStatus().isOk();

                */

    }
}
