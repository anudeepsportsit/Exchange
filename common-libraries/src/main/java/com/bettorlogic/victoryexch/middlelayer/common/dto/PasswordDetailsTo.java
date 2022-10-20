package com.bettorlogic.victoryexch.middlelayer.common.dto;

public class PasswordDetailsTo {
    private String message;
    private Boolean isUpdated;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }
}
