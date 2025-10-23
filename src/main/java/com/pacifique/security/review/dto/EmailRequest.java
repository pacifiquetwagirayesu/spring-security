package com.pacifique.security.review.dto;

public record EmailRequest(String from, String to, String subject, String html) {
}
