package com.bettorlogic.victoryexch.middlelayer.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private SportsBookOutputGenerator sportsBookOutputGenerator;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        SportsBookOutput sportsBookOutput = sportsBookOutputGenerator.getFailureResponse(ex, request.getContextPath());
        return new ResponseEntity<>(sportsBookOutput, HttpStatus.BAD_REQUEST);
    }
}