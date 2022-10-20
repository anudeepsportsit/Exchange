package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto.*;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.service.AdminService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.utils.AdminAgentConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dao.DownlineListDao;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.PasswordEncoder;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
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
@RequestMapping(SportsBookConstants.BASE_URL+AdminPanelConstants.ADMIN_PANEL+AdminPanelConstants.AGENT_ADMIN)
public class AdminAgentRestController {

    @Autowired
    private DownlineListDao downlineListDao;
    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private SportsBookService sportsBookService;

    @RequestMapping(value=AdminAgentConstants.ADD_AGENT,method = RequestMethod.POST)
    public SportsBookOutput addAdminAgent(@RequestBody @NotNull @Valid AgentTO agentDetails) {
        try {
            Map<String, Object> result = new HashMap<>();

               result = adminService.addAgent(agentDetails);
                return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex,"Fail");
        }
    }

    @RequestMapping(value=AdminAgentConstants.SUSPEND_AGENT,method = RequestMethod.POST)
    public SportsBookOutput suspendAgent(@RequestBody @NotNull @Valid AgentTO agentDetails){
        String result = adminService.suspendAgentAccount(agentDetails);
        return outputGenerator.getSuccessResponse(result);
    }

    @RequestMapping(value=AdminAgentConstants.AGENT_PASSWORD_CHANGE,method = RequestMethod.POST)
    public SportsBookOutput chamgeAgentPassword(@RequestBody @NotNull @Valid AgentTO agentDetails){
        try {
            Boolean validateToken = downlineListDao.getDbToken(agentDetails.getToken());
            if (validateToken) {
                String result = adminService.changePasswordAgent(agentDetails);
                return outputGenerator.getSuccessResponse(result);
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e, AdminAgentConstants.AGENT_PASSWORD_CHANGE);
        }
    }

    @RequestMapping(value=AdminAgentConstants.GET_AGENT_SPORTS,method = RequestMethod.POST)
    public SportsBookOutput getAgentSportList(@RequestBody @NotNull @Valid AgentTO agentDetails){
        try {
            if (!agentDetails.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(agentDetails.getToken());
                if (validateToken) {
                    List<AgentSportsOutputTO> agentSports = adminService.getSports(agentDetails.getAgentName());
                    return outputGenerator.getSuccessResponse(agentSports);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }


    @RequestMapping(value=AdminAgentConstants.GET_AGENT_EVENTS,method = RequestMethod.POST)
    public SportsBookOutput getAgentEventsList(@RequestBody @NotNull @Valid AgentTO agentDetails){
        try {
            if (!agentDetails.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(agentDetails.getToken());
                if (validateToken) {
                    List<AgentEventsOutputTO> agentSports = adminService.getEvents(agentDetails.getAgentName());
                    return outputGenerator.getSuccessResponse(agentSports);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }

    @RequestMapping(value=AdminAgentConstants.GET_AGENT_MARKET,method = RequestMethod.POST)
    public SportsBookOutput getAgentMarketList(@RequestBody @NotNull @Valid AgentTO agentDetails){
        try {
            if (!agentDetails.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(agentDetails.getToken());
                if (validateToken) {
                    List<AgentMarketsOutputTO> agentSports = adminService.getMarkets(agentDetails.getAgentName());
                    return outputGenerator.getSuccessResponse(agentSports);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }


    @RequestMapping(value=AdminAgentConstants.GET_AGENT_BETDELAY_MARKETS,method = RequestMethod.POST)
    public SportsBookOutput getAgentBetdelayMarketList(@RequestBody @NotNull @Valid AgentTO agentDetails){
        try {
            if (!agentDetails.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(agentDetails.getToken());
                if (validateToken) {
                    List<AgentMarketsOutputTO> agentSports = adminService.getFanacyMarkets(agentDetails.getAgentName());
                    return outputGenerator.getSuccessResponse(agentSports);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }


    @RequestMapping(value=AdminAgentConstants.GET_AGENT_STAKE_MARKETS,method = RequestMethod.POST)
    public SportsBookOutput getAgentStakeMarketList(@RequestBody @NotNull @Valid AgentTO agentDetails){
        try {
            if (!agentDetails.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(agentDetails.getToken());
                if (validateToken) {
                    List<AgentMarketsOutputTO> agentSports = adminService.getFanacyStakeMarkets(agentDetails.getAgentName());
                    return outputGenerator.getSuccessResponse(agentSports);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }


    @RequestMapping(value=AdminAgentConstants.SAVE_AGENT_STAKE_MARKETS,method = RequestMethod.POST)
    public SportsBookOutput saveMinMaxStake(@RequestBody @NotNull @Valid MinMaxStakeTO minMaxStakeTO){
        try {
            if (!minMaxStakeTO.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(minMaxStakeTO.getToken());
                if (validateToken) {
                    String response = adminService.saveStake(minMaxStakeTO);
                    return outputGenerator.getSuccessResponse(response);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }



    @RequestMapping(value=AdminAgentConstants.SAVE_AGENT_BETLAY,method = RequestMethod.POST)
    public SportsBookOutput saveAgentBetDelay(@RequestBody @NotNull @Valid AgentTO agentDetails){
        try {
            if (!agentDetails.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(agentDetails.getToken());
                if (validateToken) {
                    String response = adminService.saveBetDelay(agentDetails);
                    return outputGenerator.getSuccessResponse(response);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }


    @RequestMapping(value=AdminAgentConstants.UPDATE_AGENT_MARKETS,method = RequestMethod.POST)
    public SportsBookOutput updateAgentMarketList(@RequestBody @NotNull @Valid AgentUpdateOddsTO updateOddsTO){
        try {
            if (!updateOddsTO.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(updateOddsTO.getToken());
                if (validateToken) {
                    String agentSports = adminService.updateMarkets(updateOddsTO);
                    return outputGenerator.getSuccessResponse(agentSports);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }


    @RequestMapping(value=AdminAgentConstants.DECLARE_RESULT,method = RequestMethod.POST)
    public SportsBookOutput declareResult(@RequestBody AdminDeclareResultTO declareResultTO){
        try {
            if(!declareResultTO.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(declareResultTO.getToken());
                Integer roleId = sportsBookService.getLoginUserRoleId(declareResultTO.getToken());
                if(validateToken) {
                    String result = adminService.resultDeclare(declareResultTO,roleId);
                    return outputGenerator.getSuccessResponse(result);
                }else{
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else{
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }

    @RequestMapping(value=AdminAgentConstants.GET_MIN_STAKE,method = RequestMethod.GET)
    public SportsBookOutput getMinStake(){
        MinMaxStakeTO minMaxStakeTO = new MinMaxStakeTO();
        minMaxStakeTO.setMinStakeList(adminService.getMinStake());
        minMaxStakeTO.setMaxStakeList(adminService.getMaxStake());
        return outputGenerator.getSuccessResponse(minMaxStakeTO);
    }

    @RequestMapping(value=AdminAgentConstants.GET_MAX_STAKE,method = RequestMethod.POST)
    public SportsBookOutput getMaxStake(@RequestBody MinMaxStakeTO minMaxStakeTO){
        //List<Integer> maxStakeList = adminService.getMaxStake();
        return outputGenerator.getSuccessResponse(adminService.updateStake(minMaxStakeTO));
    }

    @RequestMapping(value=AdminAgentConstants.SUSPEND_MARKET,method = RequestMethod.POST)
    public SportsBookOutput suspendMarkets(@RequestBody MarketSuspensionTO marketSuspensionTO){
        try {
            if(!marketSuspensionTO.getToken().isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(marketSuspensionTO.getToken());
                if(validateToken) {
                    String result = adminService.suspendMarket(marketSuspensionTO);
                    return outputGenerator.getSuccessResponse(result);
                }else{
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            }else{
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        }catch (Exception e){
            return outputGenerator.getFailureResponse(e,"Fail");
        }
    }

    @RequestMapping(value=AdminAgentConstants.ADD_AGENT_MARKETS,method = RequestMethod.POST)
    public SportsBookOutput addAgentMarkets(@RequestBody @Valid AgentMarketsInput agentMarketDetails) {
        try {
            Map<String, Object> result = new HashMap<>();
            result = adminService.addAgentMarket(agentMarketDetails);
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, "Fail");
        }
    }

    @RequestMapping(value = AdminAgentConstants.GET_AGENT_ADMIN_DOWNLINE_LIST, method = RequestMethod.GET)
    public SportsBookOutput getAgentsAdminDownlineList(String userLoginToken) {
        try {
            if (!userLoginToken.isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(userLoginToken);
                if (validateToken) {
                    List<AgentsMarketsOutputTO> agentsMarketsList = adminService.getAgentsDownLineList(userLoginToken);
                    return outputGenerator.getSuccessResponse(agentsMarketsList);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminAgentConstants.GET_AGENT_ADMIN_DOWNLINE_LIST);
        }
    }

    @RequestMapping(value = AdminAgentConstants.GET_AGENT_MARKET_SELECTION_EVENTS, method = RequestMethod.GET)
    public SportsBookOutput getMarketGroupSportwiseEvents(String marketGroupName,Integer sportId) {
        try {
            List<MarketGroupEventsOutputTO> events = adminService.getMarketGroupEvents(marketGroupName,sportId);
            return outputGenerator.getSuccessResponse(events);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminAgentConstants.GET_AGENT_MARKET_SELECTION_EVENTS);
        }
    }

    @RequestMapping(value = AdminAgentConstants.GET_SPECIAL_MARKET_TEAMS, method = RequestMethod.GET)
    public SportsBookOutput getSpecialMarketTeams() {
        try {
            List<SpecialMarketTeamsOutputTO> events = adminService.getSpecialMarketTeams();
            return outputGenerator.getSuccessResponse(events);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminAgentConstants.GET_SPECIAL_MARKET_TEAMS);
        }
    }

    @RequestMapping(value = AdminAgentConstants.GET_AGENT_MARKET_ODDS, method = RequestMethod.GET)
    public SportsBookOutput getAgentMarketOdds(Integer agentMarketId, Integer sportId, Integer eventId) {
        try {
            List<AgentsMarketOddsOutputTO> agentsMarketOddsList = adminService.getMarketOdds(agentMarketId, sportId, eventId);
            return outputGenerator.getSuccessResponse(agentsMarketOddsList);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminAgentConstants.GET_AGENT_MARKET_ODDS);
        }
    }


    @RequestMapping(value = AdminAgentConstants.GET_AGENT_MARKET_RESULT_DROPDOWN, method = RequestMethod.GET)
    public SportsBookOutput getAgentMarketResultDropdown(Integer agentMarketId, Integer sportId, Integer eventId) {
        try {
            List agentMarketResultList = adminService.getMarketDropdown(agentMarketId, sportId, eventId);
            return outputGenerator.getSuccessResponse(agentMarketResultList);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminAgentConstants.GET_AGENT_MARKET_RESULT_DROPDOWN);
        }
    }


    @RequestMapping(value = AdminAgentConstants.GET_AGENT_FANCY_MARKETS, method = RequestMethod.GET)
    public SportsBookOutput getAgentFancyMarkets(Integer agentMarketId, Integer sportId, Integer eventId) {
        try {
            List<AgentFancyMarketTO> agentMarketResultList = adminService.getFancyMarkets(agentMarketId, sportId, eventId);
            return outputGenerator.getSuccessResponse(agentMarketResultList);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminAgentConstants.GET_AGENT_FANCY_MARKETS);
        }
    }

    @RequestMapping(value = AdminAgentConstants.UPDATE_AGENT_FANCY_MARKETS, method = RequestMethod.POST)
    public SportsBookOutput updateAgentFancyMarkets(@RequestBody List<UpdateFancyOddsTO> updateFancyOddsTO) {
        try {
            updateFancyOddsTO.stream().forEach(updateFancyOddsTO1 -> {
                adminService.updateFancyMarkets(updateFancyOddsTO1);
            });
            return outputGenerator.getSuccessResponse("Successfully updated");
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminAgentConstants.UPDATE_AGENT_FANCY_MARKETS);
        }
    }


    @RequestMapping(value = AdminAgentConstants.GET_AGENT_MARKETS_LIST, method = RequestMethod.GET)
    public SportsBookOutput getAgentMarketsList(String userLoginToken, Integer agentId) {
        try {
            if (!userLoginToken.isEmpty()) {
                Boolean validateToken = downlineListDao.getDbToken(userLoginToken);
                if (validateToken) {
                    List<AgentMarketListOutputTO> agentsMarketsList = adminService.getAgentMarketsList(userLoginToken, agentId);
                    return outputGenerator.getSuccessResponse(agentsMarketsList);
                } else {
                    throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
                }
            } else {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminAgentConstants.GET_AGENT_MARKETS_LIST);
        }
    }

    @RequestMapping(value = AdminAgentConstants.ADD_AGENT_MARKET_ODDS, method = RequestMethod.POST)
    public SportsBookOutput addAgentMarketOdds(@RequestBody @Valid AgentBookmakerMarketOddsInputTO agentBookmakerMarketOddsDetails) {
        try {
            Map<String, Object> result = new HashMap<>();
            result = adminService.addAgentMarketOdds(agentBookmakerMarketOddsDetails);
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, "Fail");
        }
    }

    @RequestMapping(value = AdminAgentConstants.UPDATE_AGENT_MARKET_SUSPENDED, method = RequestMethod.POST)
    public SportsBookOutput updateAgentMarketSuspended(@RequestBody @Valid AgentMarketSuspendInput agentMarketSuspendDetails) {
        try {
            Map<String, Object> result = new HashMap<>();
            result = adminService.updateAgentMarketSuspended(agentMarketSuspendDetails);
            return outputGenerator.getSuccessResponse(result);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, "Fail");
        }
    }
}
