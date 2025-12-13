package com.pacifique.security.review.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
@Hidden
public class HomeController {
    @GetMapping
    public String homePage() {
        return "home/index";
    }
}
