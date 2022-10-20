package com.bettorlogic.victoryexch.middlelayer.common.dto;

public class UserProfileDetailsWrapperTO {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String profileEmail;
    //private String userPwd;
    private String userAddress;
    private String townCity;
    private String postalCode;
    private String timeZone;
    private String profilePhone;
    private String countryName;
    private String currencyTitle;
    private String oddsFormat;
    private Integer commisionCharges;
    private String userLanguage;
    private Integer timeZoneId;

    public Integer getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(Integer timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getOddsFormat() {
        return oddsFormat;
    }

    public void setOddsFormat(String oddsFormat) {
        this.oddsFormat = oddsFormat;
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

    public String getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getTownCity() {
        return townCity;
    }

    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProfilePhone() {
        return profilePhone;
    }

    public void setProfilePhone(String profilePhone) {
        this.profilePhone = profilePhone;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyTitle() {
        return currencyTitle;
    }

    public void setCurrencyTitle(String currencyTitle) {
        this.currencyTitle = currencyTitle;
    }

    public Integer getCommisionCharges() {
        return commisionCharges;
    }

    public void setCommisionCharges(Integer commisionCharges) {
        this.commisionCharges = commisionCharges;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLang(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    @Override
    public String toString() {
        return "UserProfileDetails{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profileEmail='" + profileEmail + '\'' +
                ", profilePhone='" + profilePhone + '\'' +
                '}';
    }
}
