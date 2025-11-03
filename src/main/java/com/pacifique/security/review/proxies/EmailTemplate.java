package com.pacifique.security.review.proxies;

import com.pacifique.security.review.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Component
public class EmailTemplate {
    private final TemplateEngine templateEngine;

    public String renderEmailTemplate(String templateName, User user, String link) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("link", link);
        return templateEngine.process("email/" + templateName, context);
    }
}
