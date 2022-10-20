package com.bettorlogic.victoryexch.middlelayer.common.config;

public interface CorsFilterConstants {
    String HEADER_AUTHORIZATION = "Authorization";
    String CORS_ACCESS_CONTROL_ALLOWED_HEADERS = "Accept, Content-Type, Origin, X-Requested-With, "
            + HEADER_AUTHORIZATION;
    String HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    String HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    String HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    String HEADER_ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    String CORS_ACCESS_CONTROL_ALLOWED_ORIGINS = "*";
    String CORS_ACCESS_CONTROL_ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS";

    String CORS_ACCESS_CONTROL_MAX_AGE_IN_SECONDS = "3600";
}