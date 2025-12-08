package com.pacifique.security.review.proxies;

import com.pacifique.security.review.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.pacifique.security.review.utils.ConstantsFields.BASE_LINK;
import static com.pacifique.security.review.utils.ConstantsFields.DELETE_ACCOUNT;
import static com.pacifique.security.review.utils.ConstantsFields.NEW_ACCOUNT;
import static com.pacifique.security.review.utils.ConstantsFields.UPDATE_ACCOUNT;

@Component
@RequiredArgsConstructor
public class EmailContent implements IEmailContent {
    private final EmailTemplate emailTemplate;

    @Override
    public String generateEmailContent(EmailAction action, User user) {
        return switch (action) {
            case NEW -> emailTemplate.renderEmailTemplate(NEW_ACCOUNT, user, BASE_LINK);
            case UPDATE -> emailTemplate.renderEmailTemplate(UPDATE_ACCOUNT, user, BASE_LINK);
            case DELETE -> emailTemplate.renderEmailTemplate(DELETE_ACCOUNT, user, BASE_LINK);
        };
    }

    @Override
    public String toString() {
        return "EmailContent{" +
                "emailTemplate=" + emailTemplate +
                '}';
    }
}
