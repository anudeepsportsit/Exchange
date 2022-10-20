package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegistrationOutput {
    private boolean isCreated;
    private String reason;

    @JsonProperty(SportsBookConstants.IS_CREATED)
    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
