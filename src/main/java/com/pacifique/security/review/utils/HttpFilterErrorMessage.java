package com.pacifique.security.review.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class HttpFilterErrorMessage {

    private HttpFilterErrorMessage() {}

    public static void generateResponse(HttpServletResponse res, HttpServletRequest req, Exception ex) throws IOException {
        res.setContentType("application/json");
        res.getWriter().write(
                "{" +
                        "\"message\" : \"" + ex.getMessage() + "\" ,"
                        + "\"status\"" + ":" +   res.getStatus() + " ,"
                        + "\"path\": \"" + req.getServletPath() + "\" ,"
                        + "\"timestamp\" : \"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\"" +

                        "}"
        );
        res.getWriter().flush();

    }

    @Deprecated
    public static int getStatusCode(HttpServletResponse res,Exception ex) {
        return switch (res.getStatus()) {
            case 404 -> HttpStatus.NOT_FOUND.value();
            case 401 -> HttpStatus.UNAUTHORIZED.value();
            case 403 -> HttpStatus.FORBIDDEN.value();
            case 500 -> HttpStatus.INTERNAL_SERVER_ERROR.value();
            case 400 -> HttpStatus.BAD_REQUEST.value();
            case 405 -> HttpStatus.METHOD_NOT_ALLOWED.value();
            default ->{
                int status = HttpStatus.OK.value();

                if (ex.getMessage().contains("Request method")) {
                    res.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
                    status = HttpStatus.METHOD_NOT_ALLOWED.value();
                }

                if (ex.getMessage().contains("authentication is required")) {
                    res.setStatus(HttpStatus.UNAUTHORIZED.value());
                    status = HttpStatus.UNAUTHORIZED.value();
                }

                if (ex.getMessage().contains("Access Denied") || ex.getMessage().contains("Forbidden")) {
                    res.setStatus(HttpStatus.FORBIDDEN.value());
                    status = HttpStatus.FORBIDDEN.value();
                }

                yield status;
            }
        };
    }
}
