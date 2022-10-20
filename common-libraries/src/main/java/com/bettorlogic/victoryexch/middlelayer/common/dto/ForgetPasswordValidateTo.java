package com.bettorlogic.victoryexch.middlelayer.common.dto;

public class ForgetPasswordValidateTo {
    private Boolean validEmailId;
    private Boolean validEmailConfirmationToken;

    public Boolean getValidEmailId() {
        return validEmailId;
    }

    public void setValidEmailId(Boolean validEmailId) {
        this.validEmailId = validEmailId;
    }

    public Boolean getValidEmailConfirmationToken() {
        return validEmailConfirmationToken;
    }

    public void setValidEmailConfirmationToken(Boolean validEmailConfirmationToken) {
        this.validEmailConfirmationToken = validEmailConfirmationToken;
    }
}
