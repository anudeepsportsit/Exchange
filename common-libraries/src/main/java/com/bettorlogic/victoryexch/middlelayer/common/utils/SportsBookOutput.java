package com.bettorlogic.victoryexch.middlelayer.common.utils;


import java.io.Serializable;

public class SportsBookOutput implements Serializable {
    private Integer statusCode;
    private boolean isSuccess;
    private String description;
    private Object data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public SportsBookOutput setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getDescription() {
        return description;
    }

    public SportsBookOutput setDescription(String description) {
        this.description = description;
        return this;
    }

    public Object getData() {
        return data;
    }

    public SportsBookOutput setData(Object data) {
        this.data = data;
        return this;
    }
}