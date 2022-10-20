package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto;

public class AccountSummaryTO {
    private String availableToBet;
    private String fundsAvailableToWithdraw;
    private String currentExposure;
    private UserProfileDetailsTO userProfileDetails;

    public String getAvailableToBet() {
        return availableToBet;
    }

    public void setAvailableToBet(String availableToBet) {
        this.availableToBet = availableToBet;
    }

    public String getFundsAvailableToWithdraw() {
        return fundsAvailableToWithdraw;
    }

    public void setFundsAvailableToWithdraw(String fundsAvailableToWithdraw) {
        this.fundsAvailableToWithdraw = fundsAvailableToWithdraw;
    }

    public String getCurrentExposure() {
        return currentExposure;
    }

    public void setCurrentExposure(String currentExposure) {
        this.currentExposure = currentExposure;
    }

    public UserProfileDetailsTO getUserProfileDetails() {
        return userProfileDetails;
    }

    public void setUserProfileDetails(UserProfileDetailsTO userProfileDetails) {
        this.userProfileDetails = userProfileDetails;
    }
}