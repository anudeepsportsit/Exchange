package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.utils;

public interface AdminAgentConstants {

    String ADD_AGENT = "${bm-exchange.uri.adminpanel.add-agent}";
    String ADD_AGENT_MARKETS = "${bm-exchange.uri.adminpanel.add-agent-markets}";
    String GET_AGENT_ADMIN_DOWNLINE_LIST ="${bm-exchange.uri.adminpanel.get-agent-admin-downline-list}";
    String GET_AGENT_MARKETS_LIST ="${bm-exchange.uri.adminpanel.get-agent-markets-list}";
    String GET_AGENT_MARKET_SELECTION_EVENTS="${bm-exchange.uri.adminpanel.get-market-group-sport-wise-event-list}";
    String GET_SPECIAL_MARKET_TEAMS="${bm-exchange.uri.adminpanel.get-special-market-teams-list}";
    String ADD_AGENT_MARKET= "add_agent_markets";
    String ADDAGENT="select * from add_adminagent(?,?,?,?,?,?,?,?,?)";
    String VALIDATE_TOKEN="select user_login_token,roleid from bmeusers where user_login_token=?";
    String GET_AGENTNAME_COUNT="select count(agent_name) agent_name_count from  admin_agent where agent_name=?";

    String GET_AGENT_MARKET_ODDS="${bm-exchange.uri.adminpanel.get-agent-market-odds}";
    String ADD_AGENT_MARKET_ODDS="${bm-exchange.uri.adminpanel.add-agent-market-odds}";
    String UPDATE_AGENT_MARKET_SUSPENDED="${bm-exchange.uri.adminpanel.update-agent-market-suspended}";
    String GET_AGENT_FANCYMARKETS = "select distinct * from get_agent_fancy_market_new(?) order by event_id";

    String AGENT_MARKET_ID="id";
    String AGENTID="agentid";
    String EVENT_ID="event_id";
    String SPORT_ID="sport_id";
    String TEAM_ID="team_id";
    String TEAM_NAME="team_name";
    String BOX_COUNT="box_count";
    String USER_CREATED_KEY = "isCreated";
    String USER_UPDATED_KEY = "isUpdated";
    String REASON_CODE = "reasonCode";
    String REASON = "reason";
    String AGENT_NAME_COUNT="agent_name_count";
    String ROLE_ID="roleid";
    String USER_LOGIN_TOKEN="user_login_token";
    String IS_CREATED="is_inserted";
    String IS_UPDATED="is_updated";

    //Agent down-line list
    String AGENT_NAME = "agent_name";
    String EVENT_NAME = "event_name";
    String SPORT_NAME = "sport_name";
    String MARKET_NAME = "market_name";
    String SUBMARKET_NAME = "submarket_name";
    String MARKET_SELECTION_NAME = "market_selection_name";
    String AVAILABLE = "available";
    String STATUS="status";

    String IS_SUSPEND="is_suspend";

    //Agent Market Odds Details
    String SELECTION_NAME = "provider_outcome";
    String BETFAIR_BACK_ODDS = "back_odds";
    String BETFAIR_LAY_ODDS = "lay_odds";
    String BOOKMAKER_BACK_ODDS = "bookmaker_back_odds";
    String BOOKMAKER_LAY_ODDS = "bookmaker_lay_odds";

    Integer ADMIN_ROLE =1 ;
    String SUSPEND_AGENT = "${bm-exchange.uri.adminpanel.suspend-agent}";
    String AGENT_PASSWORD_CHANGE = "${bm-exchange.uri.adminpanel.change-agent-password}";
    String GET_AGENT_SPORTS = "${bm-exchange.uri.adminpanel.get-agent-sports}";
    String GET_AGENT_SPORTS_LIST = "select distinct am.sport_id,am.sport_name from admin_agent ag join agent_markets am on ag.agent_id = am.agent_id where ag.agent_name=?";
    String GET_AGENT_EVENTS_LIST = "select distinct am.event_id,am.event_name from admin_agent ag join agent_markets am on ag.agent_id = am.agent_id where ag.agent_name=?";
    String GET_AGENT_EVENTS = "${bm-exchange.uri.adminpanel.get-agent-events}";
    String GET_AGENT_MARKETS = "select distinct * from get_agent_market_new(?) order by event_id,provideroutcome";
    String GET_AGENT_MARKET = "${bm-exchange.uri.adminpanel.get-agent-markets}";

    String GET_AGENT_BETDELAY_MARKETS = "${bm-exchange.uri.adminpanel.get-agent-betdelay-markets}";

    String GET_AGENT_STAKE_MARKETS = "${bm-exchange.uri.adminpanel.get-agent-stake-markets}";

    String SAVE_AGENT_STAKE_MARKETS = "${bm-exchange.uri.adminpanel.save-agent-stake-markets}";

    String SAVE_AGENT_BETLAY = "${bm-exchange.uri.adminpanel.save-agent-betdelay-markets}";

    String UPDATE_AGENT_MARKETS = "${bm-exchange.uri.adminpanel.update-agent-markets}";
    String DECLARE_RESULT = "${bm-exchange.uri.adminpanel.declare-result}";
    String SUSPEND_MARKET = "${bm-exchange.uri.adminpanel.suspend-market}";
    Integer OPEN = 1;
    Integer WIN = 2;
    Integer LOSS = 3;
    Integer LAPSED = 4;
    String GET_MIN_STAKE = "${bm-exchange.uri.adminpanel.get-min-stake}";
    String GET_MAX_STAKE = "${bm-exchange.uri.adminpanel.get-max-stake}";
    String GET_AGENT_MARKET_RESULT_DROPDOWN = "${bm-exchange.uri.adminpanel.get-agent-market-result-dropdown}";
    String GET_AGENT_FANCY_MARKETS = "${bm-exchange.uri.adminpanel.get-agent-fancy-markets}";
    String UPDATE_AGENT_FANCY_MARKETS = "${bm-exchange.uri.adminpanel.update-agent-fancy-markets}";
    String OUTCOME = "outcome";
    String BETDELAY = "betdelay";
    String MINSTAKE = "minstake";
    String MAXSTAKE = "maxstake";
}
