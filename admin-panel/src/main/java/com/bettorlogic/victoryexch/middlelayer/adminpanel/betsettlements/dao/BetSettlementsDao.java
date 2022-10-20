package com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.AgentBetSettlement;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.BetSettlementOutputTO;

import java.util.List;

public interface BetSettlementsDao {
    List<BetSettlementOutputTO> getAllBetSettlements();

    String updateBetSettlements(BetSettlementDetailsTO betSettlementInputTO);

    List<Integer> getSettlementId(AgentBetSettlement agentBetSettlement);

    List<Integer> getlossSettlementId(AgentBetSettlement agentBetSettlement);

    void deleteMarkets(AgentBetSettlement agentBetSettlement);

    List<Integer> getLapsedSettlementId(AgentBetSettlement agentBetSettlement);
}
