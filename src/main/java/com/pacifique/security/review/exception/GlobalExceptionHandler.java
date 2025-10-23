package com.pacifique.security.review.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            BeanInstantiationException.class,
            InvalidDataAccessResourceUsageException.class,
            DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class,
            InvalidToken.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpMessage exceptionHandler(Exception ex, HttpServletRequest req) {
        return new HttpMessage(ex.getMessage(), HttpServletResponse.SC_BAD_REQUEST, req.getServletPath());
    }

    @ExceptionHandler({
            NoResourceFoundException.class,
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpMessage exceptionHandler(ServletException ex, HttpServletRequest req) {
        return new HttpMessage(ex.getMessage(), HttpServletResponse.SC_NOT_FOUND, req.getServletPath());
    }

    @ExceptionHandler({
            ClassCastException.class,
            SpelParseException.class,
            EntityNotFoundException.class,
            HttpMessageConversionException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpMessage exceptionHandler(RuntimeException ex, HttpServletRequest req) {
        return new HttpMessage(ex.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, req.getServletPath());
    }

    @ExceptionHandler({
            InsufficientAuthenticationException.class,
            AuthorizationDeniedException.class,
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HttpMessage exceptionHandler(AuthorizationDeniedException ex, HttpServletRequest req) {
        return new HttpMessage(ex.getMessage(), HttpServletResponse.SC_FORBIDDEN, req.getServletPath());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public HttpMessage exceptionHandler(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        return new HttpMessage(ex.getMessage(), HttpServletResponse.SC_METHOD_NOT_ALLOWED, req.getServletPath());
    }


}
