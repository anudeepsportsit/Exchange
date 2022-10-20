package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.AdminTokenValidateTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dao.AdminDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto.AgentMarketsInput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.service.AdminService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.utils.AdminAgentConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.utils.DownLineListConstants;
import com.bettorlogic.victoryexch.middlelayer.common.dao.SportsBookDao;
import com.bettorlogic.victoryexch.middlelayer.common.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SportsBookDao sportsBookDao;

    @Override
    public Map<String, Object> addAgent(AgentTO agent) {
        SportsBookOutput sportsBookOutput = new SportsBookOutput();
        int agentNameCount = adminDao.getAgentNamecount(agent.getAgentName());
        AdminTokenValidateTO adminTokenValidateTO = adminDao.validateToke(agent.getToken());
        Map<String, Object> result = new HashMap<>();
        if (adminTokenValidateTO.getRoleId() == AdminAgentConstants.ADMIN_ROLE && adminTokenValidateTO.getLoginToken().equals(agent.getToken())) {

            if (agentNameCount == 0) {
                Integer agentId = adminDao.addAgent(agent);
                if (agentId != 0) {
                    result.put(AdminAgentConstants.USER_CREATED_KEY, true);
                }
            } else {
                result.put(AdminAgentConstants.USER_CREATED_KEY, false);
                result.put(DownLineListConstants.REASON_CODE, FailureReasonConstants.DUPLICATE_EMAIL_ID.code());
                result.put(AdminAgentConstants.REASON, ExceptionConstants.DUPLICATE_AGENTNAME);
            }
        }
        else
        {
            result.put(AdminAgentConstants.USER_CREATED_KEY, false);
            result.put(DownLineListConstants.REASON_CODE, FailureReasonConstants.DUPLICATE_EMAIL_ID.code());
            result.put(AdminAgentConstants.REASON, ExceptionConstants.AUTHENTICATION_FAIL);
        }

        return result;
    }

    @Override
    public Map<String, Object> addAgentMarket(AgentMarketsInput agentMarketDetails) {
        Map<String, Object> result = new HashMap<>();
        StringBuilder bulkData = new StringBuilder();
        List bookmakerOddsList = new ArrayList();
        AgentBookMakerTO agentBookMakerTO = new AgentBookMakerTO();
        List<AgentBookMakerTO> agentBookMakerTOS = new ArrayList<>();


        List<EventTeamsOutputTO> eventTeamsList=agentMarketDetails.getEventTeamsList();
        Integer agentMarketId=adminDao.addAgentMarket(agentMarketDetails);
        if(agentMarketId!=0){
            if(agentMarketDetails.getSubMarketName().equalsIgnoreCase("Fancy")){
                adminDao.addFancyMarket(agentMarketDetails);
                result.put(AdminAgentConstants.USER_CREATED_KEY,true);
                return result;
            }


            if(agentMarketDetails.getMarketSelection().equalsIgnoreCase("Special Market")){

                adminDao.addSpecialEvent(agentMarketDetails);

            }



            if( agentMarketDetails.getMarketSelection().equalsIgnoreCase("Special Market") && !agentMarketDetails.getSubMarketName().equalsIgnoreCase("Fancy")){
                eventTeamsList.stream().forEach(eventTeamDetailsInput ->
                        bulkData.append(eventTeamsParseBulkData(eventTeamDetailsInput,agentMarketId)));
                String bulkDataInsert = bulkData.substring(0, bulkData.length() - 1);
                adminDao.addAgentMarketTeams(bulkDataInsert);
            }
            if(agentMarketDetails.getSportId() == 1){
                agentBookMakerTO.setEventId(agentMarketDetails.getEventId());
                agentBookMakerTO.setOddDictionaryId(27);
                agentBookMakerTO.setAgentId(agentMarketDetails.getAgentId());
                agentBookMakerTO.setProviderOutcome("1");
                agentBookMakerTO.setBackOdds(0.0);
                agentBookMakerTO.setLayOdds(0.0);
                agentBookMakerTOS.add(agentBookMakerTO);
                agentBookMakerTO = new AgentBookMakerTO();
                agentBookMakerTO.setEventId(agentMarketDetails.getEventId());
                agentBookMakerTO.setOddDictionaryId(28);
                agentBookMakerTO.setAgentId(agentMarketDetails.getAgentId());
                agentBookMakerTO.setProviderOutcome("X");
                agentBookMakerTO.setBackOdds(0.0);
                agentBookMakerTO.setLayOdds(0.0);
                agentBookMakerTOS.add(agentBookMakerTO);
                agentBookMakerTO = new AgentBookMakerTO();
                agentBookMakerTO.setEventId(agentMarketDetails.getEventId());
                agentBookMakerTO.setOddDictionaryId(29);
                agentBookMakerTO.setAgentId(agentMarketDetails.getAgentId());
                agentBookMakerTO.setProviderOutcome("2");
                agentBookMakerTO.setBackOdds(0.0);
                agentBookMakerTO.setLayOdds(0.0);
                agentBookMakerTOS.add(agentBookMakerTO);
                adminDao.addAgentBookMakerMatchOdds(agentBookMakerTOS);
            } else if(agentMarketDetails.getSportId() == 2){
                agentBookMakerTO.setEventId(agentMarketDetails.getEventId());
                agentBookMakerTO.setOddDictionaryId(32);
                agentBookMakerTO.setAgentId(agentMarketDetails.getAgentId());
                agentBookMakerTO.setProviderOutcome("1");
                agentBookMakerTO.setBackOdds(0.0);
                agentBookMakerTO.setLayOdds(0.0);
                agentBookMakerTOS.add(agentBookMakerTO);
                agentBookMakerTO = new AgentBookMakerTO();
                agentBookMakerTO.setEventId(agentMarketDetails.getEventId());
                agentBookMakerTO.setOddDictionaryId(33);
                agentBookMakerTO.setAgentId(agentMarketDetails.getAgentId());
                agentBookMakerTO.setProviderOutcome("2");
                agentBookMakerTO.setBackOdds(0.0);
                agentBookMakerTO.setLayOdds(0.0);
                agentBookMakerTOS.add(agentBookMakerTO);
                adminDao.addAgentBookMakerMatchOdds(agentBookMakerTOS);
            }else if (agentMarketDetails.getSportId() == 4){
                agentBookMakerTO.setEventId(agentMarketDetails.getEventId());
                agentBookMakerTO.setOddDictionaryId(30);
                agentBookMakerTO.setAgentId(agentMarketDetails.getAgentId());
                agentBookMakerTO.setProviderOutcome("1");
                agentBookMakerTO.setBackOdds(0.0);
                agentBookMakerTO.setLayOdds(0.0);
                agentBookMakerTOS.add(agentBookMakerTO);
                agentBookMakerTO = new AgentBookMakerTO();
                agentBookMakerTO.setEventId(agentMarketDetails.getEventId());
                agentBookMakerTO.setOddDictionaryId(31);
                agentBookMakerTO.setAgentId(agentMarketDetails.getAgentId());
                agentBookMakerTO.setProviderOutcome("2");
                agentBookMakerTO.setBackOdds(0.0);
                agentBookMakerTO.setLayOdds(0.0);
                agentBookMakerTOS.add(agentBookMakerTO);
                adminDao.addAgentBookMakerMatchOdds(agentBookMakerTOS);
            }
            result.put(AdminAgentConstants.USER_CREATED_KEY,true);
        }else {
            result.put(AdminAgentConstants.USER_CREATED_KEY,false);
        }
        return result;
    }

    private String eventTeamsParseBulkData(EventTeamsOutputTO eventTeam,Integer agentMarketId) {
        return "(" + eventTeam.getId() + ",'" + eventTeam.getName() + "'," + agentMarketId + "),";
    }

    @Override
    public List<AgentsMarketsOutputTO> getAgentsDownLineList(String userLoginToken){
        return adminDao.getAgentsDownLineList(userLoginToken);
    }
    @Override
    public List<MarketGroupEventsOutputTO> getMarketGroupEvents(String marketGroupName,Integer sportId){
        return adminDao.getMarketGroupEvents(marketGroupName, sportId);
    }

    @Override
    public List<SpecialMarketTeamsOutputTO> getSpecialMarketTeams(){
        return adminDao.getSpecialMarketTeams();
    }

    @Override
    public List<AgentsMarketOddsOutputTO> getMarketOdds(Integer agentMarketId, Integer sportId, Integer eventId){
        return adminDao.getMarketOdds( agentMarketId, sportId, eventId);
    }

    @Override
    public List getMarketDropdown(Integer agentMarketId, Integer sportId, Integer eventId){
        List<AgentsMarketOddsOutputTO> outputTOList = new ArrayList<>();
        outputTOList = adminDao.getMarketOdds( agentMarketId, sportId, eventId);
        List resultList = new ArrayList();
        for (int i = 0; i<=outputTOList.size()-1;i++){
            AgentsMarketOddsOutputTO agentsMarketOddsOutputTO = new AgentsMarketOddsOutputTO();
            agentsMarketOddsOutputTO = outputTOList.get(i);
            resultList.add(agentsMarketOddsOutputTO.getSelectionName());
        }
        resultList.add("Tie");resultList.add("NR");
        return resultList;
    }

    @Override
    public List<AgentMarketListOutputTO> getAgentMarketsList(String userLoginToken,Integer agentId){
        return adminDao.getAgentMarketsList(userLoginToken,agentId);
    }

    @Override
    public Map<String, Object> addAgentMarketOdds(AgentBookmakerMarketOddsInputTO agentBookmakerMarketOddsDetails){
        Map<String, Object> result = new HashMap<>();
        try {
            adminDao.updateAgentBookmakerOdds(agentBookmakerMarketOddsDetails);
            result.put(AdminAgentConstants.USER_CREATED_KEY, "true");
            return result;
        }catch (Exception e){
            result.put(AdminAgentConstants.USER_CREATED_KEY, "false");
            return result;
        }
    }

    private String bookmakerMarketOddsParseBulkData(BookmakerMarketOddsInput bookmakerMarketOdds) {
        return "(" + bookmakerMarketOdds.getAgentMarketId() + ",'" + bookmakerMarketOdds.getSelectionName() + "'," + bookmakerMarketOdds.getBookMakerBackOdds() + "," + bookmakerMarketOdds.getBookMakerLayOdds() + "),";
    }

    @Override
    public Map<String, Object> updateAgentMarketSuspended(AgentMarketSuspendInput agentMarketSuspendDetails){
        Map<String, Object> result = new HashMap<>();
        Boolean isUpdated=adminDao.updateAgentMarketSuspended(agentMarketSuspendDetails);
        result.put(AdminAgentConstants.USER_UPDATED_KEY,isUpdated);
        return result;
    }

    @Override
    public String suspendAgentAccount(AgentTO agentDetails) {
        try {
            adminDao.suspendAccount(agentDetails);
            return "Success";
        }catch (Exception e){
            return "Fail";
        }
    }

    @Override
    public String changePasswordAgent(AgentTO agentDetails) {
        try {
            String email = adminDao.getProfileEmail(agentDetails.getAgentName());
            //UserLoginDetailsTO userLoginDetails = sportsBookDao.getLoginDetails(email);
            String encodedPassword = sportsBookDao.getPassword(agentDetails.getAgentName());
            if(!passwordEncoder.matches(agentDetails.getAgentNewPassword(), encodedPassword)){
                String newPassword = passwordEncoder.encode(agentDetails.getAgentNewPassword());
                adminDao.changePwd(agentDetails,newPassword);
                return "Password changed Successfully";
            }else{
                return SportsBookConstants.PASSWORD_MATCH;
            }
        }catch (Exception e){
            return "Password not changed";
        }
    }

    @Override
    public String getAgentPassword(String agentName) {
        return adminDao.getCurrentPassword(agentName);
    }

    @Override
    public List<AgentSportsOutputTO> getSports(String agentName) {
        return adminDao.getAgentSports(agentName);
    }

    @Override
    public List<AgentEventsOutputTO> getEvents(String agentName) {
        return adminDao.getAgentEvents(agentName);
    }

    @Override
    public List<AgentMarketsOutputTO> getMarkets(String agentName) {

        List<AgentMarketsOutputTO> output = new ArrayList<>();
        if(agentName.equalsIgnoreCase("all")){
            return adminDao.getAgentMarketsAll();
        }
        return adminDao.getAgentMarkets(agentName);
    }

    @Override
    public String resultDeclare(AdminDeclareResultTO declareResultTO, Integer roleId) {
        return adminDao.adminDeclareResult(declareResultTO,roleId);
    }

    @Override
    public String suspendMarket(MarketSuspensionTO marketSuspensionTO) {
        return adminDao.suspend(marketSuspensionTO);
    }


    @Override
    public List<Integer> getMinStake() {
        return adminDao.getMinStakeList();
    }

    @Override
    public List<Integer> getMaxStake() {
        return adminDao.getMaxStakeList();
    }

    @Override
    public List<AgentFancyMarketTO> getFancyMarkets(Integer agentMarketId, Integer sportId, Integer eventId) {
        return adminDao.getAgentFancyMarkets(agentMarketId,sportId,eventId);
    }

    @Override
    public String updateFancyMarkets(UpdateFancyOddsTO updateFancyOddsTO) {
        return adminDao.updateMarket(updateFancyOddsTO);
    }

    @Override
    public String updateMarkets(AgentUpdateOddsTO updateOddsTO) {
        return adminDao.updateAgentMarkets(updateOddsTO);
    }

    @Override
    public List<AgentMarketsOutputTO> getFanacyMarkets(String agentName) {
        return adminDao.getAgentBetdelayMarkets(agentName);
    }

    @Override
    public List<AgentMarketsOutputTO> getFanacyStakeMarkets(String agentName) {
        List<AgentMarketsOutputTO> agentMarketsOutputTOS = adminDao.markets(agentName);
        return agentMarketsOutputTOS;
    }

    @Override
    public String saveBetDelay(AgentTO agentDetails) {
        return adminDao.saveBetDelay(agentDetails);
    }

    @Override
    public String saveStake(MinMaxStakeTO minMaxStakeTO) {
        return adminDao.saveMarketStake(minMaxStakeTO);
    }

    @Override
    public Boolean updateStake(MinMaxStakeTO minMaxStakeTO) {
        return  adminDao.updateMinMaxStake(minMaxStakeTO);
    }
}
