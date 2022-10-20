package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

public class FavouriteEventDetailsTO {

    @NotNull(message = ExceptionConstants.SESSION_EXPIRED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userToken;

    @NotNull(message = ExceptionConstants.INVALID_EVENT_ID_ENTERED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer eventId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketGroupId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorDetails;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean pinDetailsSaved;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean pinDetailsDeleted;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subMarket;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer providerId;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public Boolean getPinDetailsSaved() {
        return pinDetailsSaved;
    }

    public void setPinDetailsSaved(Boolean pinDetailsSaved) {
        this.pinDetailsSaved = pinDetailsSaved;
    }

    public Boolean getPinDetailsDeleted() {
        return pinDetailsDeleted;
    }

    public void setPinDetailsDeleted(Boolean pinDetailsDeleted) {
        this.pinDetailsDeleted = pinDetailsDeleted;
    }

    public Integer getMarketGroupId() {
        return marketGroupId;
    }

    public void setMarketGroupId(Integer marketGroupId) {
        this.marketGroupId = marketGroupId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getSubMarket() {
        return subMarket;
    }

    public void setSubMarket(String subMarket) {
        this.subMarket = subMarket;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
}
