package com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto;

public class UserIdTokenValidationsTO {
    private boolean isValidPlayerToken;
    private boolean isValidManagerToken;
    private boolean isValidPlayerId;
    private boolean isValidManagerId;
    private boolean isValidMasterToken;

    public boolean isValidMasterToken() {
        return isValidMasterToken;
    }

    public void setValidMasterToken(boolean validMasterToken) {
        isValidMasterToken = validMasterToken;
    }

    public boolean isValidPlayerToken() {
        return isValidPlayerToken;
    }

    public void setValidPlayerToken(boolean validPlayerToken) {
        isValidPlayerToken = validPlayerToken;
    }

    public boolean isValidManagerToken() {
        return isValidManagerToken;
    }

    public void setValidManagerToken(boolean validManagerToken) {
        isValidManagerToken = validManagerToken;
    }

    public boolean isValidPlayerId() {
        return isValidPlayerId;
    }

    public void setValidPlayerId(boolean validPlayerId) {
        isValidPlayerId = validPlayerId;
    }

    public boolean isValidManagerId() {
        return isValidManagerId;
    }

    public void setValidManagerId(boolean validManagerId) {
        isValidManagerId = validManagerId;
    }
}
