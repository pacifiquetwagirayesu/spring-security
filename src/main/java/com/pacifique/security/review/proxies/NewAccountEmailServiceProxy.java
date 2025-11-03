package com.pacifique.security.review.proxies;

import com.pacifique.security.review.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;


@Service
@Slf4j
@RequiredArgsConstructor
public final class NewAccountEmailServiceProxy implements IEmailProxies {
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    private final EmailContent emailContent;

    @Value("${resend.api-key}")
    private String apiKey;
    @Value("${resend.api-url}")
    private String apiUrl;
    @Value("${resend.from}")
    private String from;


    @Override
    public void accountActivityNotification(User user) {
        Callable<String> task = () -> {
            log.info("Sending email...");
            ResponseEntity<String> response = emailContent.proxyResponse(from,
                    user,
                    user.getFirstName() + " " + user.getLastName() + " new account created",
                    apiKey,
                    restTemplate,
                    apiUrl,
                    EmailAction.NEW);

            log.info("Sending Done ✅");
            log.info("Ended : {} 👌✅", Thread.currentThread().getName());
            return response.getBody();

        };

        submitTaskForExecution(task, executorService);

    }


}
