package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentTO;

import java.util.List;
import java.util.Map;

public interface AdminService {

    Map<String, Object> addAgent(AgentTO agentTO);

    Map<String, Object> addAgentMarket(AgentMarketsInput agentMarketDetails);

    List<AgentsMarketsOutputTO> getAgentsDownLineList(String userLoginToken);

    List<MarketGroupEventsOutputTO> getMarketGroupEvents(String marketGroupName,Integer sportId);

    List<SpecialMarketTeamsOutputTO> getSpecialMarketTeams();

    List<AgentsMarketOddsOutputTO> getMarketOdds(Integer agentMarketId, Integer sportId, Integer eventId);

    List<AgentsMarketOddsOutputTO> getMarketDropdown(Integer agentMarketId, Integer sportId, Integer eventId);

    List<AgentMarketListOutputTO> getAgentMarketsList(String userLoginToken,Integer agentId);

    Map<String, Object> addAgentMarketOdds(AgentBookmakerMarketOddsInputTO agentBookmakerMarketOddsDetails);

    Map<String, Object> updateAgentMarketSuspended(AgentMarketSuspendInput agentMarketSuspendDetails);

    String suspendAgentAccount(AgentTO agentDetails);

    String changePasswordAgent(AgentTO agentDetails);

    String getAgentPassword(String agentName);

    List<AgentSportsOutputTO> getSports(String agentName);

    List<AgentEventsOutputTO> getEvents(String agentName);

    List<AgentMarketsOutputTO> getMarkets(String agentName);

    String resultDeclare(AdminDeclareResultTO declareResultTO, Integer roleId);

    String suspendMarket(MarketSuspensionTO marketSuspensionTO);

    List<Integer> getMinStake();

    List<Integer> getMaxStake();

    List<AgentFancyMarketTO> getFancyMarkets(Integer agentMarketId, Integer sportId, Integer eventId);

    String updateFancyMarkets(UpdateFancyOddsTO updateFancyOddsTO);

    String updateMarkets(AgentUpdateOddsTO updateOddsTO);

    List<AgentMarketsOutputTO> getFanacyMarkets(String agentName);

    List<AgentMarketsOutputTO> getFanacyStakeMarkets(String agentName);

    String saveBetDelay(AgentTO agentDetails);

    String saveStake(MinMaxStakeTO minMaxStakeTO);

    Boolean updateStake(MinMaxStakeTO minMaxStakeTO);
}
