package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AgentBookmakerMarketOddsInputTO {

    @NotNull(message = SportsBookConstants.USER_LOGIN_TOKEN_NULL)
    private String userLoginToken;

    @NotNull
    @NotEmpty
    private List<BookmakerMarketOddsInput> bookmakerMarketOddsInputList;

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public List<BookmakerMarketOddsInput> getBookmakerMarketOddsInputList() {
        return bookmakerMarketOddsInputList;
    }

    public void setBookmakerMarketOddsInputList(List<BookmakerMarketOddsInput> bookmakerMarketOddsInputList) {
        this.bookmakerMarketOddsInputList = bookmakerMarketOddsInputList;
    }
}