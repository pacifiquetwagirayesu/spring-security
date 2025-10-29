package com.pacifique.security.review.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated
public class MainController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello!";
    }

    @PostMapping(value = "/hello")
    public String postHello() {
        return "Hello!";
    }

    @GetMapping("/hello/user")
    public String helloUser(Authentication authentication) {
        return "Hello, " + authentication.getName();
    }
}
