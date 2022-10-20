package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FooterControlsTO {

    @JsonProperty(SportsBookConstants.ID)
    private Integer footerId;

    @JsonProperty(SportsBookConstants.NAME)
    private String footerName;

    @JsonProperty(SportsBookConstants.URL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String footerUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String logoUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String skyExchangeUrl;

    @JsonProperty(SportsBookConstants.TEXT)
    private String footerText;

    @JsonIgnore
    private String parentFooterName;

    public Integer getFooterId() {
        return footerId;
    }

    public void setFooterId(Integer footerId) {
        this.footerId = footerId;
    }

    public String getFooterName() {
        return footerName;
    }

    public void setFooterName(String footerName) {
        this.footerName = footerName;
    }

    public String getFooterUrl() {
        return footerUrl;
    }

    public void setFooterUrl(String footerUrl) {
        this.footerUrl = footerUrl;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public String getParentFooterName() {
        return parentFooterName;
    }

    public void setParentFooterName(String parentFooterName) {
        this.parentFooterName = parentFooterName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSkyExchangeUrl() {
        return skyExchangeUrl;
    }

    public void setSkyExchangeUrl(String skyExchangeUrl) {
        this.skyExchangeUrl = skyExchangeUrl;
    }
}