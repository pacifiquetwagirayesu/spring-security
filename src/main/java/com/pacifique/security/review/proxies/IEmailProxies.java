package com.pacifique.security.review.proxies;

import com.pacifique.security.review.model.User;

public interface IEmailProxies {
    void accountActivityNotification(User user);

    default void setEmailThreadName(String emailThreadName) {
        Thread.currentThread().setName(emailThreadName + " Thread");
    }
}
