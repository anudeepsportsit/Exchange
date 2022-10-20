package com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.service;


import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.AgentBetSettlement;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementInputTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementOutputTO;

import java.util.List;

public interface BetSettlementService {

    List<BetSettlementOutputTO> getAllBetSettlements();

    String updateBetSettlements(BetSettlementInputTO betSettlementInputTO) throws Exception;

    List<Integer> getWinBetSettlementId(AgentBetSettlement agentBetSettlement);

    List<Integer> getLossBetSettlementId(AgentBetSettlement agentBetSettlement);

    void deleteMarket(AgentBetSettlement agentBetSettlement);

    List<Integer> getLapsedBetSettlementId(AgentBetSettlement agentBetSettlement);
}
