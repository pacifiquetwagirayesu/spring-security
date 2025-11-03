package com.pacifique.security.review.proxies;

import com.pacifique.security.review.dto.EmailRequest;
import com.pacifique.security.review.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface IEmailContent {
    String generateEmailContent(EmailAction action, User user);


    default ResponseEntity<String> proxyResponse(String from,
                                                 User user,
                                                 String subject,
                                                 String apiKey,
                                                 RestTemplate restTemplate,
                                                 String apiUrl,
                                                 EmailAction action
    ) {
        EmailRequest emailRequest = new EmailRequest(from, user.getEmail(), subject, generateEmailContent(action, user));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        HttpEntity<EmailRequest> entity = new HttpEntity<>(emailRequest, headers);
        return restTemplate.postForEntity(apiUrl, entity, String.class);
    }

}
