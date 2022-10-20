package com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AgentTO {

    private Integer agentId;
    private String agentName;
    private String password;
    private String remarks;
    private Integer inactiveSessionSuspension;
    private String bookPosition;
    private String betlistLive;
    private String resultDeclation;
    private String available;
    private String status;
    private String token;
    private String encodedPassword;
    private Integer roleId;
    private String agentNewPassword;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer betDelay;

    private Integer eventId;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Integer getBetDelay() {
        return betDelay;
    }

    public void setBetDelay(Integer betDelay) {
        this.betDelay = betDelay;
    }

    public String getAgentNewPassword() {
        return agentNewPassword;
    }

    public void setAgentNewPassword(String agentNewPassword) {
        this.agentNewPassword = agentNewPassword;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getInactiveSessionSuspension() {
        return inactiveSessionSuspension;
    }

    public void setInactiveSessionSuspension(Integer inactiveSessionSuspension) {
        this.inactiveSessionSuspension = inactiveSessionSuspension;
    }

    public String getBookPosition() {
        return bookPosition;
    }

    public void setBookPosition(String bookPosition) {
        this.bookPosition = bookPosition;
    }

    public String getBetlistLive() {
        return betlistLive;
    }

    public void setBetlistLive(String betlistLive) {
        this.betlistLive = betlistLive;
    }

    public String getResultDeclation() {
        return resultDeclation;
    }

    public void setResultDeclation(String resultDeclation) {
        this.resultDeclation = resultDeclation;
    }
}
