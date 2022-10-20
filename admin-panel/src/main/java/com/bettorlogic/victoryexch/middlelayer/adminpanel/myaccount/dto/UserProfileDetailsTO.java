package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto;

public class UserProfileDetailsTO {
    private String firstName;
    private String lastName;
    private String birthday;
    private String emailId;
    private String timeZone;
    private String contactNumber;
    private String exposureLimit;
    private String commissionChanges;

    public String getCommissionChanges() {
        return commissionChanges;
    }

    public void setCommissionChanges(String commissionChanges) {
        this.commissionChanges = commissionChanges;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getExposureLimit() {
        return exposureLimit;
    }

    public void setExposureLimit(String exposureLimit) {
        this.exposureLimit = exposureLimit;
    }
}