package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent;

public class AdminTokenValidateTO {
    private String loginToken;
    private Integer roleId;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
