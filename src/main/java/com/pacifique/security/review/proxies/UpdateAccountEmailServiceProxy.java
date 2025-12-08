package com.pacifique.security.review.proxies;

import com.pacifique.security.review.model.User;
import com.pacifique.security.review.security.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateAccountEmailServiceProxy implements IEmailProxies {
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
            setEmailThreadName("Update account notification");
            log.info("Starting email services: {}", Thread.currentThread().getName());
            AuthUser fromUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("fromUser: {}", fromUser);

//            ResponseEntity<String> response = emailContent.proxyResponse(from,
//                    user,
//                    user.getFirstName() + " " + user.getLastName() + "updated",
//                    apiKey,
//                    restTemplate,
//                    apiUrl,
//                    EmailAction.UPDATE
//            );

//            log.info("response: {} 🤷🏻‍♂️", response.getBody());
            log.info("Email content: {}", emailContent);
            log.info("Sending Email Done ✅");
            log.info("Ended : {} 👌✅", Thread.currentThread().getName());
            return "success"; //            return response.getBody();

        };

        //        submitTaskForExecution(task, executorService);
        try {
            Future<String> future = executorService.submit(task);
            log.info("task executed: {}", future.get());

        } catch (InterruptedException | ExecutionException ex) {
            log.error("Error while sending email: {}", ex.getMessage());
            Thread.currentThread().interrupt();
        }


    }


}
