package com.pacifique.security.review.utils;

public final class ConstantsFields {

    public static  final String READ = "read";
    public static  final String WRITE = "write";
    public static  final String DELETE = "delete";
    public static  final String ARCHIVE = "archive";
    public static  final String PREFIX = "ROLE_";

    public static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/api-docs"
    };
}
