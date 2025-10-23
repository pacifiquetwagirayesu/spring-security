package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.EmailRequest;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.security.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public final class EmailService {
    private final RestTemplate restTemplate;

    @Value("${resend.api-key}")
    private String apiKey;
    @Value("${resend.api-url}")
    private String apiUrl;
    @Value("${resend.from}")
    private String from;
    @Value("${resend.subject}")
    private String subject;


    public void sendEmail(User to, String action) {
        Runnable task = () -> {
            Thread.currentThread().setName("Email Service Thread");
            log.info("Sending email...");
            log.info("Starting email service: {}", Thread.currentThread().getName());

            if (!action.equals("new user created")) {
                AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                log.info("Security user is {}", user);
            }
            EmailRequest emailRequest = new EmailRequest(from, to.getEmail(), subject, generateEmailContent(action, to));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<EmailRequest> entity = new HttpEntity<>(emailRequest, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            log.info("response: {} 🤷🏻‍♂️", response.getBody());
            log.info("Sending Done ✅");
            log.info("Ended : {} 👌✅", Thread.currentThread().getName());

        };

        ExecutorService exs = Executors.newFixedThreadPool(1);
        exs = new DelegatingSecurityContextExecutorService(exs);

        try {
            exs.submit(task);
        } catch (RejectedExecutionException e) {
            log.info("Rejected execution");
        } finally {
            exs.shutdown();
        }


    }


    private String generateEmailContent(String action, User to) {
        return switch (action) {
            case "update": {
                yield String.format(
                        """
                                <div style="
                                  background-color: #ffffff;
                                  border-radius: 8px;
                                  padding: 24px;
                                  font-family: Arial, Helvetica, sans-serif;
                                  color: #333333;
                                  line-height: 1.6;
                                  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
                                ">
                                  <h2 style="color: #111827; font-size: 20px; margin-bottom: 12px;">Hello, %s 👋</h2>
                                
                                  <p style="margin: 0 0 16px 0;">
                                    We're excited to have you on board! Here are a few quick steps to get started:
                                  </p>
                                
                                  <ul style="margin: 0 0 16px 20px; padding: 0;">
                                    <li>Complete your profile</li>
                                    <li>Explore our dashboard</li>
                                    <li>Start your first project</li>
                                  </ul>
                                
                                  <a href="%s" style="
                                    display: inline-block;
                                    padding: 12px 20px;
                                    background-color: #2563eb;
                                    color: #ffffff;
                                    text-decoration: none;
                                    border-radius: 6px;
                                    font-weight: bold;
                                  ">
                                    %s
                                  </a>
                                
                                  <p style="margin-top: 24px; font-size: 13px; color: #6b7280;">
                                    If the button doesn't work, copy and paste this link into your browser:<br>
                                    <a href="%s" style="color: #2563eb; text-decoration: none;">%s</a>
                                  </p>
                                </div>
                                
                                """
                        , (to.getFirstName() + " " + to.getLastName()), "http://localhost:8080", "Finish", "http://localhost:8080", "Link");
            }
            case "new user created": {
                yield String.format("""
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Welcome to Our Platform</title>
                        </head>
                        <body style="margin:0; padding:0; background-color:#f5f6fa; font-family:Arial, Helvetica, sans-serif;">
                            <div style="
                                max-width:600px; 
                                margin:40px auto; 
                                background-color:#ffffff; 
                                border-radius:8px; 
                                padding:30px; 
                                box-shadow:0 2px 6px rgba(0,0,0,0.08);
                            ">
                                <h2 style="color:#2c3e50; text-align:center;">Welcome, %s 🎉</h2>
                                <p style="color:#34495e; font-size:15px; line-height:1.6;">
                                    We’re thrilled to have you join us! You’ve successfully created your account and are now part of our community.
                                </p>
                                <p style="color:#34495e; font-size:15px; line-height:1.6;">
                                    To get started, click the button below to explore your new dashboard and customize your profile.
                                </p>
                                <div style="text-align:center; margin:30px 0;">
                                    <a href="%s" style="
                                        background-color:#2563eb;
                                        color:#ffffff;
                                        text-decoration:none;
                                        padding:12px 20px;
                                        border-radius:6px;
                                        font-weight:bold;
                                        display:inline-block;
                                    ">Get Started</a>
                                </div>
                                <p style="color:#6b7280; font-size:13px; text-align:center;">
                                    If the button doesn’t work, copy and paste this link into your browser:<br>
                                    <a href="%s" style="color:#2563eb;">%s</a>
                                </p>
                                <hr style="border:none; border-top:1px solid #e5e7eb; margin:30px 0;">
                                <p style="font-size:13px; color:#9ca3af; text-align:center;">
                                    © 2025 Your Company. All rights reserved.<br>
                                    You received this email because you signed up for our service.
                                </p>
                            </div>
                        </body>
                        </html>
                        """, (to.getFirstName() + " " + to.getLastName()), "http://localhost", "http://localhost", "http://localhost");
            }
            case "delete": {
                yield "delete your account";
            }
            default:
                throw new IllegalArgumentException("Invalid action");
        };
    }
}
