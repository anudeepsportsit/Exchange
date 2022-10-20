package com.bettorlogic.victoryexch.middlelayer.common.dto;

import javax.validation.constraints.NotNull;

public class ForgetPasswordInputTO {
    @NotNull
    private String emailConfirmationToken;
    @NotNull
    private String newPassword;

    public String getEmailConfirmationToken() {
        return emailConfirmationToken;
    }

    public void setEmailConfirmationToken(String emailConfirmationToken) {
        this.emailConfirmationToken = emailConfirmationToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
