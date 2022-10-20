package com.bettorlogic.victoryexch.middlelayer.common.utils;

public enum FailureReasonConstants {
    DUPLICATE_EMAIL_ID(1, "Duplicate email-id entered");

    private final int code;
    private final String description;

    FailureReasonConstants(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String description() {
        return description;
    }

    public int code() {
        return code;
    }
}