package com.bettorlogic.victoryexch.middlelayer.common.dto;

public class UniqueUserResultsTO {
    private boolean isUniqueEmailId;
    private boolean isUniqueUserName;

    public boolean isUniqueEmailId() {
        return isUniqueEmailId;
    }

    public void setUniqueEmailId(boolean uniqueEmailId) {
        isUniqueEmailId = uniqueEmailId;
    }

    public boolean isUniqueUserName() {
        return isUniqueUserName;
    }

    public void setUniqueUserName(boolean uniqueUserName) {
        isUniqueUserName = uniqueUserName;
    }
}
