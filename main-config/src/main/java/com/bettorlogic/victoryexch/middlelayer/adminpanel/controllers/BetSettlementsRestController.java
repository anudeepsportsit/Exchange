package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.AgentBetSettlement;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementInputTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementOutputTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.service.BetSettlementService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.utils.DownLineListConstants;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL + AdminPanelConstants.BET_SETTLEMENTS)
public class BetSettlementsRestController {

    private static final Logger LOGGER = LogManager.getLogger(BetSettlementsRestController.class);

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private BetSettlementService betSettlementService;

    @RequestMapping(value = AdminPanelConstants.GET_ALL_BET_SETTLEMENTS, method = RequestMethod.GET)
    public SportsBookOutput getAllBetSettlements() {
        try {
            List<BetSettlementOutputTO> betSettlementOutputTOList = betSettlementService.getAllBetSettlements();
            return outputGenerator.getSuccessResponse(betSettlementOutputTOList);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.GET_ALL_BET_SETTLEMENTS);
        }
    }

    @RequestMapping(value = AdminPanelConstants.UPDATE_BET_SETTLEMENTS, method = RequestMethod.POST)
    public SportsBookOutput updateBetSettlements(@RequestBody @Valid @NotNull BetSettlementInputTO betSettlementInputTO) {
        try {
            Map<String, Boolean> result = new HashMap<>();
            String message = betSettlementService.updateBetSettlements(betSettlementInputTO);
            result.put(DownLineListConstants.USER_UPDATED_KEY, message != null);
            return outputGenerator.getSuccessResponse(result);

        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.UPDATE_BET_SETTLEMENTS_SERVICE);
        }
    }

    @RequestMapping(value = AdminPanelConstants.UPDATE_AGENT_BET_SETTLEMENTS, method = RequestMethod.POST)
    public SportsBookOutput declareAgentResult(@RequestBody AgentBetSettlement agentBetSettlement) throws Exception {
        try {
            BetSettlementInputTO betSettlementInputTO = new BetSettlementInputTO();
            BetSettlementTO betSettlementTO = new BetSettlementTO();
            List<BetSettlementTO> settlementTOList = new ArrayList<>();
            String[] teams = agentBetSettlement.getEventName().split("v");
            String homeTeam = teams[0];String awayTeam = teams[1];
            if(agentBetSettlement.getOutcomeName().trim().equalsIgnoreCase(homeTeam.trim())){
                agentBetSettlement.setOutcomeName(AdminPanelConstants.HOME);
            }else if(agentBetSettlement.getOutcomeName().trim().equalsIgnoreCase(awayTeam.trim())){
                agentBetSettlement.setOutcomeName(AdminPanelConstants.AWAY);
            }else if(agentBetSettlement.getOutcomeName().equalsIgnoreCase("To Draw")){
                agentBetSettlement.setOutcomeName(AdminPanelConstants.DRAW);
            }
            if(agentBetSettlement.getOutcomeName().equalsIgnoreCase(AdminPanelConstants.TIE) || agentBetSettlement.getOutcomeName().equalsIgnoreCase(AdminPanelConstants.NR)){
                List<Integer> betSettlementIdList = betSettlementService.getLapsedBetSettlementId(agentBetSettlement);
                betSettlementInputTO.setUserLoginToken(agentBetSettlement.getToken());
                for (int i = 0; i <= betSettlementIdList.size()-1; i++) {
                    betSettlementTO.setBetSettlementId(betSettlementIdList.get(i));
                    betSettlementTO.setBetStatusId(4);
                    settlementTOList.add(betSettlementTO);
                }
                if(!betSettlementIdList.isEmpty()) {
                    betSettlementInputTO.setBetSettlementsList(settlementTOList);
                    betSettlementService.updateBetSettlements(betSettlementInputTO);
                }
                betSettlementService.deleteMarket(agentBetSettlement);
                return outputGenerator.getSuccessResponse("success");
            }
            List<Integer> betSettlementIdList = betSettlementService.getWinBetSettlementId(agentBetSettlement);
            betSettlementInputTO.setUserLoginToken(agentBetSettlement.getToken());
            for (int i = 0; i <= betSettlementIdList.size()-1; i++) {
                betSettlementTO.setBetSettlementId(betSettlementIdList.get(i));
                betSettlementTO.setBetStatusId(2);
                settlementTOList.add(betSettlementTO);
            }
            if(!betSettlementIdList.isEmpty()) {
                betSettlementInputTO.setBetSettlementsList(settlementTOList);
                betSettlementService.updateBetSettlements(betSettlementInputTO);
            }
            betSettlementIdList = betSettlementService.getLossBetSettlementId(agentBetSettlement);
            for (int i = 0; i <= betSettlementIdList.size()-1; i++) {
                betSettlementTO.setBetSettlementId(betSettlementIdList.get(i));
                betSettlementTO.setBetStatusId(3);
                settlementTOList.add(betSettlementTO);
            }
            if(!betSettlementIdList.isEmpty()) {
                betSettlementInputTO.setBetSettlementsList(settlementTOList);
                betSettlementService.updateBetSettlements(betSettlementInputTO);
            }
            betSettlementService.deleteMarket(agentBetSettlement);
            return outputGenerator.getSuccessResponse("success");
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e, AdminPanelConstants.UPDATE_AGENT_BET_SETTLEMENTS);
        }
    }

}