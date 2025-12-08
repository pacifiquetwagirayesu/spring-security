package com.pacifique.security.review;

import com.pacifique.security.review.config.ConfigDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Deprecated
public class MainTestsReactiveTest extends ConfigDatabase {
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
