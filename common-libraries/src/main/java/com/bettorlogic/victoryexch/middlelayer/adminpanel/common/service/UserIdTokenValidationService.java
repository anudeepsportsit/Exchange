package com.bettorlogic.victoryexch.middlelayer.adminpanel.common.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentValidations;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.UserIdTokenValidationsTO;

public interface UserIdTokenValidationService {
    UserIdTokenValidationsTO validateUser(String userToken, Integer userId);

    AgentValidations validateAgent(String token);
}
