package com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils;

public interface AdminPanelConstants {
    String ADMIN_PANEL = "${bm-exchange.uri.adminpanel}";
    String MY_ACCOUNT = "${bm-exchange.uri.adminpanel.myaccount}";
    String ACTIVITY_LOG = "activityLog";
    String BANKING = "${bm-exchange.uri.adminpanel.banking}";

    String GET_MATCH_ODDS_MARKETS = "${bm-exchange.uri.get-match-odds-markets}";
    String GET_FANCY_MARKETS = "${bm-exchange.uri.get-fancy-markets}";
    String GET_ALL_BET_SETTLEMENTS = "${bm-exchange.uri.get-all-bet-settlements}";
    String UPDATE_BET_SETTLEMENTS = "${bm-exchange.uri.update-bet-settlements}";
    String RISK_MANAGEMENT = "${bm-exchange.uri.adminpanel.risk-management}";
    String CURRENCY = "${bm-exchange.uri.adminpanel.currency}";
    String INSERT_ADMIN_MATCHODDS = "${bm-exchange.uri.add-match-odds}";
    String BET_SETTLEMENTS = "${bm-exchange.uri.adminpanel.bet-settlements}";

    String GET_BANKING_DETAILS = "${bm-exchange.uri.get-banking-details}";
    String GET_BANKING_LOGS = "${bm-exchange.uri.get-banking-logs}";
    String UPDATE_BANKING_DETAILS = "${bm-exchange.uri.update-banking-details}";
    String UPDATE_EXPOSURE_LIMIT = "${bm-exchange.uri.update-exposure-limit}";
    String ADMIN_DEPOST_DETAILS = "${bm-exchange.uri.get-deposit-details}";
    String ADD_ADMIN_POINTS = "${bm-exchange.uri.add-deposit}";
    String GET_CURRENCY_LIST = "${bm-exchange.uri.get-currency-list}";
    String SAVE_CURRENCY_CONVERSION = "${bm-exchange.uri.save-currency-conversion}";

    String INVALID = "Invalid password.";
    String SUCCESS = "Updated Successfully";

    String GET_FANCY_SPORTS = "${bm-exchange.uri.get-fancy-sports}";
    String ACTIVE_STATUS = "${bm-exchange.uri.update-status}";
    String SUSPEND_STATUS = "${bm-exchange.uri.suspend-status}";
    String INSERT_FANCY = "${bm-exchange.uri.add-fancy-markets}";
    String SUCCESS_MESSAGE = "Fancy Markets added successfully";
    String MESSAGE = "Match Odds added successfully";
    String ID_NOT_NULL = "Id can not be null";
    String USER_TOKEN_NOT_NULL = "User token can not be null";
    String PENDING = "pending";
    String DEPOSIT = "deposit";
    String WITHDRAW = "withdraw";
    String REMARK = "remark";
    String FROM_ACCOUNT = "fromAccount";
    String TO_ACCOUNT = "toAccount";
    String USER_BALANCE = "balance";
    String IS_UPDATED = "isUpdated";
    String UPDATE_BET_SETTLEMENTS_SERVICE = "update-bet-settlements";
    String AGENT_ADMIN="${bm-exchange.uri.adminpanel.agentadmin}";
    String UPDATE_AGENT_BET_SETTLEMENTS = "${bm-exchange.uri.update-agent-bet-settlements}";
    String TIE = "tie";
    String NR = "NR";
    String HOME = "1";
    String AWAY = "2";
    String DRAW = "X";
    String VOIDED = "void";
    String CANCELLED = "Cancelled";
    String SETTLED="Settled";
    String PLACED="placed";
}