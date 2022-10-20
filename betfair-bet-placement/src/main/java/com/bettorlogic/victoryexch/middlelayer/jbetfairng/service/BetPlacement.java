package com.bettorlogic.victoryexch.middlelayer.jbetfairng.service;

import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dao.BetfairClient;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.*;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.ExecutionReportStatus;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.OrderType;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.PersistenceType;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetFairConstants;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetfairServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BetPlacement {

    private static Double price = null;
    @Autowired
    private BetResponseTO response;

    public BetResponseTO placeBets(BetRequestTO betRequestTO, Integer oddsChangeAcceptance) {
        BetfairClient client = new BetfairClient();
        response = new BetResponseTO();

        try {
            client.login(betRequestTO.getAppKey(), betRequestTO.getToken());

            PlaceInstructionTO placeInstruction = new PlaceInstructionTO();
            placeInstruction.setSelectionId(Long.valueOf(betRequestTO.getSelectionId()));
            placeInstruction.setSide(betRequestTO.getType());
            placeInstruction.setHandicap(0);
            placeInstruction.setOrderType(OrderType.LIMIT);
            LimitOrderTO limitOrder = new LimitOrderTO();
            limitOrder.setSize(betRequestTO.getStake());

            limitOrder.setPrice(betRequestTO.getPrice());
            limitOrder.setPersistenceType(PersistenceType.LAPSE);
            placeInstruction.setLimitOrder(limitOrder);
            PlaceExecutionReportTO placeExecutionReport = client.placeOrders(betRequestTO.getMarketId(),
                    Collections.singletonList(placeInstruction)).getResponse();
            if (placeExecutionReport != null) {
                if (ExecutionReportStatus.FAILURE.equals(placeExecutionReport.getStatus())) {
                    response.setMarketId(betRequestTO.getMarketId());
                    response.setBetId(placeExecutionReport.getInstructionReports().get(0).getBetId());
                    response.setBetPlacedDate(String.valueOf(placeExecutionReport.getInstructionReports().get(0).getPlacedDate()));
                    response.setBetStatus(String.valueOf(placeExecutionReport.getInstructionReports().get(0).getStatus()));
                    response.setErrorCode(String.valueOf(placeExecutionReport.getInstructionReports().get(0).getErrorCode()));
                } else {
                    if(placeExecutionReport.getInstructionReports().get(0).getAveragePriceMatched() != 0.0){
                        response.setBetPlacedOdds(placeExecutionReport.getInstructionReports().get(0).getAveragePriceMatched());
                    }else{
                        response.setBetPlacedOdds(betRequestTO.getPrice());
                    }
                    response.setMarketId(betRequestTO.getMarketId());
                    response.setBetId(placeExecutionReport.getInstructionReports().get(0).getBetId());
                    response.setBetPlacedDate(String.valueOf(placeExecutionReport.getInstructionReports().get(0).getPlacedDate().toInstant()));
                    response.setBetStatus(String.valueOf(placeExecutionReport.getInstructionReports().get(0).getStatus()));
                    response.setErrorCode(String.valueOf(placeExecutionReport.getInstructionReports().get(0).getErrorCode()));
                    response.setOrderStatus(String.valueOf(placeExecutionReport.getInstructionReports().get(0).getOrderStatus()));
                }
            }
            if (StringUtils.isEmpty(response.getBetStatus())) {
                response.setErrorCode(BetFairConstants.SELECTION_ERROR);
                response.setBetStatus(BetFairConstants.FAILURE_MESSAGE);
            }
        } catch (Exception e) {
            response.setErrorCode(BetFairConstants.WRONG_APP_KEY);
            response.setBetStatus(BetFairConstants.EXPIRED);
        }
        return response;
    }

    public List<OrdersResponseTO> getBetStatus(OrderDetailsTO orderDetailsTO) {
        BetfairClient client = new BetfairClient();
        List<OrdersResponseTO> responseList = new ArrayList<>();
        client.login(orderDetailsTO.getAppKey(), orderDetailsTO.getSessionToken());
        Set<String> hSet = new HashSet<String>(orderDetailsTO.getBetIds());
        BetfairServerResponse<CurrentOrderSummaryReportTO> currentOrderSummaryReportTO = client.listCurrentOrders(hSet);
        if (currentOrderSummaryReportTO.getResponse() != null) {
            currentOrderSummaryReportTO.getResponse().getCurrentOrders().stream().forEach(currentOrderSummaryTO -> {
                        OrdersResponseTO ordersResponseTO = new OrdersResponseTO();
                        ordersResponseTO.setBetId(currentOrderSummaryTO.getBetId());
                        ordersResponseTO.setMarketId(currentOrderSummaryTO.getMarketId());
                        ordersResponseTO.setStatus(currentOrderSummaryTO.getStatus().toString()
                                .equalsIgnoreCase(BetFairConstants.EXECUTION_COMPLETE)
                                ? BetFairConstants.Matched : BetFairConstants.UNMATCHED);
                        responseList.add(ordersResponseTO);
                    }
            );
        }
        return responseList;
    }

    public BetfairServerResponse<ClearedOrderSummaryReportTO> getSettledBets(OrderDetailsTO orderDetailsTO) {
        BetfairClient client = new BetfairClient();
        client.login(orderDetailsTO.getAppKey(), orderDetailsTO.getSessionToken());
        Set<String> hSet = new HashSet<>(orderDetailsTO.getBetIds());
        return client.listClearedOrders(hSet);
    }

    public ExecutionReportStatus cancelBets(String betId, List<CancelInstructionTO> cancelInstructionTO, String sessionKey) {
        BetfairClient client = new BetfairClient();
        client.login(BetFairConstants.APP_KEY, sessionKey);
        BetfairServerResponse<CancelExecutionReportTO> report = client.cancelOrders(betId, cancelInstructionTO);
        return report.getResponse().getStatus();
    }


    public ReplaceInstructionReportTO updateOpenBets(String marketId, String betfairId, Double odds, String sessionKey) {
        BetfairClient client = new BetfairClient();
        client.login(BetFairConstants.APP_KEY, sessionKey);
        List<ReplaceInstructionTO> betList = new ArrayList<>();
        ReplaceInstructionTO replaceInstructionTO = new ReplaceInstructionTO();
        replaceInstructionTO.setBetId(betfairId);
        replaceInstructionTO.setNewPrice(odds);
        betList.add(replaceInstructionTO);
        BetfairServerResponse<ReplaceExecutionReportTO> report = client.replaceOrders(marketId, betList);
        return report.getResponse().getInstructionReports().get(0);
    }

}
