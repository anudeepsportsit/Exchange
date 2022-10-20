package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserLogoutDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer agentId;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    private String loginToken;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}