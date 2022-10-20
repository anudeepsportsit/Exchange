package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveDetailsInputTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.BetListLiveWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.Timezones;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.UserBetDetailsTO;

import java.util.List;

public interface DownlineListDao {
    List<DownLineUserDetailsTO> getDownlineListDetails(String userToken, Integer userId);

    DownlineListDetailsWrapperTO getDownListUserBalanceDetails(String userToken, Integer userId);

    Integer processSaveUser(UserDetailsTO registrationDetails, String encodedPassword, String emailConfirmationToken);

    Integer getEmailIdCount(String emailId);

    Integer getUserNameCount(String emailId);

    Integer updateStatus(ChangeStatusTO changeStatusTO, UserBetDetailsTO userBetDetailsTO);

    List<BetListLiveTO> getDownlineBetListLive(String userToken);

    BetListLiveWrapperTO getDownlineBetListLiveDetails(BetListLiveDetailsInputTO betListLiveDetailsInput);

    String getUserDBPassword(String userToken);

    Boolean getDbToken(String userToken);

    Integer getDbRole(String userToken);

    ChangeStatusRoleIdTo getChangeStatusRoleIds(ChangeStatusTO changeStatus);

    Double getBalance(String userToken);

    String updateCredit(List<CreditLimitTO> creditLimitTO);

    boolean isChangedByAdmin(Integer userId);

    String getUser(Integer userId);
}
