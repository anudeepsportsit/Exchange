package com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentValidations;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.UserIdTokenValidationsTO;

public interface UserIdTokenValidationDao {
    UserIdTokenValidationsTO validateUser(String userToken, Integer userId);

    AgentValidations validateAgent(String userToken);
}