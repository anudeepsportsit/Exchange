package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;

public class EmailVerificationDetailsTo {

    private SportsBookConstants.EmailVerificationStatus status;
    private String message;

    public SportsBookConstants.EmailVerificationStatus getStatus() {
        return status;
    }

    public void setStatus(SportsBookConstants.EmailVerificationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
