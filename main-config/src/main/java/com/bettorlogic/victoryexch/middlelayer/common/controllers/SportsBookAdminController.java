package com.bettorlogic.victoryexch.middlelayer.common.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.dto.LeaguesTO;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL)
public class SportsBookAdminController {


    private static final Logger LOGGER = LogManager.getLogger(SportsBookAdminController.class);

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private SportsBookService sportsBookService;


    @RequestMapping(value = SportsBookConstants.ADD_ADMIN_LEAGUES, method = RequestMethod.POST)
    public SportsBookOutput addAdminLeagues(@RequestBody @NotNull @Valid LeaguesTO leaguesDetails) {
        try {
            Map<String, Object> result = sportsBookService.addLeagues(leaguesDetails);
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, SportsBookConstants.ADD_ADMIN_LEAGUES);
        }
    }

}