package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto;

public class UserDetailsTO {

    private String userName;
    private String password;
    private String emailId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Double exposure;
    private Double commission;
    private Integer roleId;
    private Integer timezoneId;
    private String userToken;
    private Integer currencyId;
    private Double fancyCommission;
    private Double depositedAmount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Double getExposure() {
        return exposure;
    }

    public void setExposure(Double exposure) {
        this.exposure = exposure;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(Integer timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Double getFancyCommission() {
        return fancyCommission;
    }

    public void setFancyCommission(Double fancyCommission) {
        this.fancyCommission = fancyCommission;
    }

    public Double getDepositedAmount() {
        return depositedAmount;
    }

    public void setDepositedAmount(Double depositedAmount) {
        this.depositedAmount = depositedAmount;
    }
}
