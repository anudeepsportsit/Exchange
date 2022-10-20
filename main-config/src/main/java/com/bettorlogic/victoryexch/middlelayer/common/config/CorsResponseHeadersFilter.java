package com.bettorlogic.victoryexch.middlelayer.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsResponseHeadersFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        addCorsHeaders(response);

        filterChain.doFilter(request, response);
    }

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader(
                CorsFilterConstants.HEADER_ACCESS_CONTROL_ALLOW_ORIGIN,
                CorsFilterConstants.CORS_ACCESS_CONTROL_ALLOWED_ORIGINS);
        response.setHeader(
                CorsFilterConstants.HEADER_ACCESS_CONTROL_ALLOW_METHODS,
                CorsFilterConstants.CORS_ACCESS_CONTROL_ALLOWED_METHODS);
        response.setHeader(
                CorsFilterConstants.HEADER_ACCESS_CONTROL_ALLOW_HEADERS,
                CorsFilterConstants.CORS_ACCESS_CONTROL_ALLOWED_HEADERS);
        response.setHeader(
                CorsFilterConstants.HEADER_ACCESS_CONTROL_MAX_AGE,
                CorsFilterConstants.CORS_ACCESS_CONTROL_MAX_AGE_IN_SECONDS);
    }
}