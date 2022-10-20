package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BettingHistoryDetailsInput {

    @NotBlank(message = ExceptionConstants.INVALID_USER)
    private String userToken;

    @NotNull(message = ExceptionConstants.INVALID_USER)
    private Integer playerUserId;

    @NotBlank(message = ExceptionConstants.INVALID_DATE)
    private String fromDate;

    @NotBlank(message = ExceptionConstants.INVALID_DATE)
    private String toDate;

    //    @NotNull(message = ExceptionConstants.INVALID_SPORT_ID)
    private Integer sportId;

    private Integer betStatusId;
    private Integer timeInterval;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getPlayerUserId() {
        return playerUserId;
    }

    public void setPlayerUserId(Integer playerUserId) {
        this.playerUserId = playerUserId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getBetStatusId() {
        return betStatusId;
    }

    public void setBetStatusId(Integer betStatusId) {
        this.betStatusId = betStatusId;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    @Override
    public String toString() {
        return "BettingHistoryDetailsInput{" +
                "userToken='" + userToken + '\'' +
                ", playerUserId=" + playerUserId +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", sportId='" + sportId + '\'' +
                ", betStatusId=" + betStatusId +
                ", timeInterval=" + timeInterval +
                '}';
    }
}
