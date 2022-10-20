package com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dao.BetSettlementsDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.service.BetSettlementService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetSettlementServiceImpl implements BetSettlementService {
    @Autowired
    private BetSettlementsDao betSettlementsDao;

    @Override
    public List<BetSettlementOutputTO> getAllBetSettlements() {
        return betSettlementsDao.getAllBetSettlements();
    }

    public String updateBetSettlements(BetSettlementInputTO betSettlementInputTO) throws Exception {
        StringBuilder bulkData = new StringBuilder();
        List<BetSettlementTO> betSettlements = betSettlementInputTO.getBetSettlementsList();
        BetSettlementDetailsTO betsettlementDetailsTO = new BetSettlementDetailsTO();
        Integer[] betIds = new Integer[betSettlements.size()];
        for (int i = 0; i < betSettlements.size(); i++) {
            BetSettlementTO betSettlement = betSettlements.get(i);
            if (betSettlement.getBetSettlementId() != null && betSettlement.getBetStatusId() != null) {
                betIds[i] = betSettlement.getBetSettlementId();
                bulkData.append(parseBulkData(betSettlement));
            } else {
                throw new Exception(ExceptionConstants.BET_ID_STAUS_ID_NULL);
            }
        }
        betsettlementDetailsTO.setBetSettlementIds(betIds);
        betsettlementDetailsTO.setUserLoginToken(betSettlementInputTO.getUserLoginToken());
        betsettlementDetailsTO.setBetSettlementBulkData(bulkData.substring(0, bulkData.length() - 1));

        String message = betSettlementsDao.updateBetSettlements(betsettlementDetailsTO);
        return message;
    }

    @Override
    public List<Integer> getWinBetSettlementId(AgentBetSettlement agentBetSettlement) {
        return betSettlementsDao.getSettlementId(agentBetSettlement);
    }

    @Override
    public List<Integer> getLossBetSettlementId(AgentBetSettlement agentBetSettlement) {
        return betSettlementsDao.getlossSettlementId(agentBetSettlement);
    }

    @Override
    public void deleteMarket(AgentBetSettlement agentBetSettlement) {
        betSettlementsDao.deleteMarkets(agentBetSettlement);
    }

    @Override
    public List<Integer> getLapsedBetSettlementId(AgentBetSettlement agentBetSettlement) {
        return betSettlementsDao.getLapsedSettlementId(agentBetSettlement);
    }

    private String parseBulkData(BetSettlementTO betSettlement) {
        return "(" + betSettlement.getBetSettlementId() + "," + betSettlement.getBetStatusId() + "),";
    }
}
