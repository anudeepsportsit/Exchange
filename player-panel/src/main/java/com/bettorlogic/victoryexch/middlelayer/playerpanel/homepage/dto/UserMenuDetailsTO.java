package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import java.io.Serializable;

public class UserMenuDetailsTO implements Serializable {

    private Integer userMenuId;
    private String userMenuName;
    private String userMenuLinkUrl;

    public Integer getUserMenuId() {
        return userMenuId;
    }

    public void setUserMenuId(Integer userMenuId) {
        this.userMenuId = userMenuId;
    }

    public String getUserMenuName() {
        return userMenuName;
    }

    public void setUserMenuName(String userMenuName) {
        this.userMenuName = userMenuName;
    }

    public String getUserMenuLinkUrl() {
        return userMenuLinkUrl;
    }

    public void setUserMenuLinkUrl(String userMenuLinkUrl) {
        this.userMenuLinkUrl = userMenuLinkUrl;
    }
}
