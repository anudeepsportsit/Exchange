package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.AdminTokenValidateTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentTO;

import java.util.List;


public interface AdminDao {

    Integer addAgent(AgentTO agent);

    Integer getAgentNamecount(String aganetName);

    Integer addAgentMarket(AgentMarketsInput agentMarketDetails);

    List<AgentsMarketsOutputTO> getAgentsDownLineList(String userLoginToken);

    List<MarketGroupEventsOutputTO> getMarketGroupEvents(String marketGroupName,Integer sportId);

    List<SpecialMarketTeamsOutputTO> getSpecialMarketTeams();

    AdminTokenValidateTO validateToke(String token);

    List<AgentsMarketOddsOutputTO> getMarketOdds(Integer agentMarketId, Integer sportId, Integer eventId);

    List<AgentMarketListOutputTO> getAgentMarketsList(String userLoginToken,Integer agentId);

    Boolean addAgentMarketOdds(String userLoginToken,Integer agentMarketId,String bulkDataInsert);

    Boolean updateAgentMarketSuspended(AgentMarketSuspendInput agentMarketSuspendDetails);

    void suspendAccount(AgentTO agentDetails);

    String getCurrentPassword(String agentName);

    void changePwd(AgentTO agentDetails, String newPassword);

    String getProfileEmail(String agentName);

    List<AgentSportsOutputTO> getAgentSports(String agentName);

    List<AgentEventsOutputTO> getAgentEvents(String agentName);

    List<AgentMarketsOutputTO> getAgentMarkets(String agentName);

    String adminDeclareResult(AdminDeclareResultTO declareResultTO, Integer roleId);

    String suspend(MarketSuspensionTO marketSuspensionTO);

    void addAgentMarketTeams(String bulkDataInsert);

    List<Integer> getMinStakeList();

    List<Integer> getMaxStakeList();

    List<AgentFancyMarketTO> getAgentFancyMarkets(Integer agentMarketId, Integer sportId, Integer eventId);

    String updateMarket(UpdateFancyOddsTO updateFancyOddsTO);

    String updateAgentMarkets(AgentUpdateOddsTO updateOddsTO);

    List<AgentMarketsOutputTO> getAgentBetdelayMarkets(String agentName);

    List<AgentMarketsOutputTO> markets(String agentName);

    String saveBetDelay(AgentTO agentDetails);

    String saveMarketStake(MinMaxStakeTO minMaxStakeTO);

    List<AgentMarketsOutputTO> getAgentMarketsAll();

    void addFancyMarket(AgentMarketsInput agentMarketDetails);

    void addSpecialEvent(AgentMarketsInput agentMarketDetails);

    void addAgentBookMakerMatchOdds(List<AgentBookMakerTO> agentBookMakerTOS);

    void updateAgentBookmakerOdds(AgentBookmakerMarketOddsInputTO agentBookmakerMarketOddsDetails);

    Boolean updateMinMaxStake(MinMaxStakeTO minMaxStakeTO);
}
