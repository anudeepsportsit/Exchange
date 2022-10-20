package com.bettorlogic.victoryexch.middlelayer.adminpanel.common.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dao.UserIdTokenValidationDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentValidations;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.UserIdTokenValidationsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.service.UserIdTokenValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserIdTokenValidationServiceImpl implements UserIdTokenValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserIdTokenValidationServiceImpl.class);
    @Autowired
    private UserIdTokenValidationDao validationDao;

    @Override
    public UserIdTokenValidationsTO validateUser(String userToken, Integer userId) {
        LOGGER.debug("Validating user");
        return validationDao.validateUser(userToken, userId);
    }

    @Override
    public AgentValidations validateAgent(String userToken) {
        return validationDao.validateAgent(userToken);
    }
}