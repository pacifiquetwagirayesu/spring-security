package com.pacifique.security.review.proxies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class AsyncConfig {

    @Bean
    public ExecutorService executorService() {
        return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(2));
    }

}
