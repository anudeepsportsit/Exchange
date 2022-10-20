package com.bettorlogic.victoryexch.middlelayer.common.utils;

public interface SportsBookConstants {
    String BASE_URL = "${bm-exchange.uri.base}";
    String EZUGI_TOKEN="ezugiToken";
    String USER_SIGN_UP = "${bm-exchange.uri.user.sign-up}";
    String GET_EZUGI_TOKEN="${bm-exchange.uri.get-ezugi-token}";
    String CONFIRM_ACCOUNT = "${bm-exchange.confirm-account}";
    String GET_EVENT_VIEW_DETAILS = "${bm-exchange.uri.get-event-view-details}";
    String USER_LOGIN = "${bm-exchange.uri.user.login}";
    String GET_FOOTER_CONTROLS = "${bm-exchange.uri.footer-controls}";
    String USER_LOGOUT = "${bm-exchange.uri.user.logout}";
    String SUB_HEADER = "${bm-exchange.uri.subheader}";
    String SAVE_PIN_DETAILS = "${bm-exchange.uri.save-pin-details}";
    String REMOVE_PIN_DETAILS = "${bm-exchange.uri.remove-pin-details}";
    String EMAIL_ID_VALIDATION_CHECK = "${bm-exchange.uri.user.email-id.validation-check}";
    String USER_NAME_VALIDATION_CHECK = "${bm-exchange.uri.user.user-name.validation-check}";
    String GET_ONE_CLICK_DETAILS = "${bm-exchange.uri.one-click-details}";
    String GET_USER_COIN = "${bm-exchange.uri.get-user-coin}";
    String SAVE_BET_SLIP_DETAILS = "${bm-exchange.uri.save-bet-slip-details}";
    String UPDATE_USER_COIN = "${bm-exchange.uri.update-user-coin}";
    String SAVE_USER_COIN_DETAILS = "${bm-exchange.uri.save-user-coin}";
    String UPDATE_USER_ONE_CLICK_COIN = "${bm-exchange.uri.update-one-click}";
    String GET_BET_SLIP_DETAILS = "${bm-exchange.uri.get-bet-slip-details}";
    //    String GET_ONE_CLICK = "${bm-exchange.uri.one-click-details}";
    String GET_USER_TRANSACTION_DETAILS = "${bm-exchange.uri.get-user-trans-details}";
    String GET_SPORTWISE_PROFIT_LOSS = "${bm-exchange.uri.get-sportwise-profit-loss}";
    String GET_USER_TRANSACTION_PROFIT_LOSS_VIEW = "${bm-exchange.uri.get-user-trans-profit-loss-view}";
    //    String UPDATE_ONCLICK_USER_COIN = "${bm-exchange.uri.update-one-click}";
    String GET_USER_PROFILE = "${bm-exchange.uri.get-user-profile}";
    String UPDATE_USER_PASSWORD = "${bm-exchange.uri.update-user-pwd}";
    String UPDATE_FORGET_PASSWORD = "${bm-exchange.uri.update-forget-password}";
    String SEND_EMAIL_FORGET_PASSWORD_CONFIRMATION = "${bm-exchange.uri.send-email-forget-password-confirmation}";
    String ADD_ADMIN_LEAGUES = "add-leagues";
    String GET_FANCY_LEAGUES = "${bm-exchange.uri.get-fancy-leagues}";
    String GET_FANCY_EVENTS = "${bm-exchange.uri.get-fancy-events}";
    String GET_USER_BET_HISTORY = "${bm-exchange.uri.get-user-bet-history}";
    String GET_USER_BALANCE = "${bm-exchange.uri.get-user-balance}";
    String GET_BETTING_HISTORY_DETAILS = "${bm-exchange.uri.adminpanel.myaccount.get-betting-history-details}";
    String GET_BETTING_PROFIT_LOSS_DETAILS = "${bm-exchange.uri.adminpanel.myaccount.get-betting-profit-loss-details}";
    String UPDATE_PASSWORD = "${bm-exchange.uri.adminpanel.myaccount.update-password}";
    String UPDATE_EXPOSURE = "${bm-exchange.uri.adminpanel.myaccount.update-exposure}";
    String UPDATE_COMMISSION = "${bm-exchange.uri.adminpanel.myaccount.update-commission}";
    String GET_SESSION_KEY = "${bm-exchange.uri.get-session-key}";
    String UTC_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String SUCCESS_MESSAGE = "SUCCESS";
    String FAILURE_MESSAGE = "FAILURE";
    String ERROR_CODE = "Bets are not allowed for pre-matches";
    String EXPIRED = "EXPIRED";
    String OUTCOMES = "outcomes";
    String OPEN_BETS = "${bm-exchange.uri.open-bets}";
    String COUNTRIES = "countries";
    String SESSIONTOKEN="sessionToken";
    String GET_BET_DETAILS = "${bm-exchange.uri.get-bet-details}";
    String GET_SETTLED_BET_DETAILS = "${bm-exchange.uri.get-settled-bet-details}";
    String EXECUTABLE = "EXECUTABLE";
    String EVENTS = "events";
    String LEAGUES = "leagues";
    String ID = "id";
    String KICK_OFF_TIME = "kickOffTime";
    String IS_SUSPENDED = "isSuspended";
    String IS_BLOCKED = "isBlocked";
    String MARKETS = "markets";
    String GET_ACTIVITY_LOG = "${bm-exchange.uri.adminpanel.myaccount-activity-log}";
    String GET_ACCOUNT_STATEMENT = "${bm-exchange.uri.adminpanel.myaccount.account-statement}";
    String GET_ACCOUNT_SUMMARY = "${bm-exchange.uri.adminpanel.myaccount.account-summary}";
    String GET_PLAYERS = "${bm-exchange.uri.adminpanel.risk-management-get-players}";
    String GET_MATCH_ODDS = "${bm-exchange.uri.adminpanel.risk-management-get-match-odds}";
    String GET_FANCY_BETS = "${bm-exchange.uri.adminpanel.risk-management-get-fancy-bets}";
    String GET_OTHER_MARKETS = "${bm-exchange.uri.adminpanel.risk-management-get-other-markets}";
    String GET_MATCH_OODS_LIST = "${bm-exchange.uri.adminpanel.risk-management-get-match-odds-list}";
    String GET_FANCY_LIST = "${bm-exchange.uri.adminpanel.risk-management-get-fancy-list}";
    String SAVE_MAKRET_PROFIT_LOSS_DETAILS = "save-market-profit-loss";
    String GET_MAKRET_PROFIT_LOSS_DETAILS = "get-market-profit-loss";
    //    String BASE_PACKAGES = "com.bettorlogic.victoryexch.middlelayer";
//    String CACHE_XML_FILE_PATH = "BmExchangeCacheConfig.xml";
//    String USER_CREATED_KEY = "isCreated";
    String USER_VALIDATION_KEY = "isValidLogin";
    String USER_DUPLICATE_KEY = "isDuplicate";
    //    String REASON_CODE = "reasonCode";
    Integer ONE_CLICK_COINS_SIZE = 4;
    Integer COIN_SIZE = 8;
    String COINS_DUPLICATE = "COINS DUPLICATE";
    String CURRENCY_ID = "currencyId";
    String CURRENCY_CODE = "currencyCode";
    //    String GET_BANNER_SERVICE_NAME = "getBanners";
    String GET_NAVIGATION_BAR = "getNavigationBar";
    //    String GET_HIGHLIGHTS_SPORTS_SERVICE = "getHighlightsSports";
    String REASON = "reason";
    String USER_TOKEN_KEY = "userToken";
    String USER_LOGIN_TOKEN_KEY = "userLoginToken";
    String USER_ACCOUNT_BALANCE = "userAccountBalance";
    String USER_EXPOSURE = "userExposure";
    String URL = "url";
    String TEXT = "text";
    String FOOTER_CONTROLS_LOGO_AND_LICENCING_INFORMATION = "LogoAndLicensingInformation";
    String FOOTER_CONTROLS_COMMUNICATION_DETAILS = "CommunicationDetails";
    String FOOTER_CONTROLS_POINTS = "Points";
    String BET_DETAILS = "betDetails";
    Double DEFAULT_ODDS_AND_SIZE = 0.0;
    //    String GET_HIGHLIGHTS_SERVICE_NAME = "get-highlights-events";
//    String GET_USER_MENU_SERVICE_NAME = "get-user-menu";
    String USER_LOGOUT_STATUS = "userLogoutStatus";
    String USER_ID = "userId";
    String USER_NAME = "userName";
    String ODD_TYPE_BACK = "B";
    String ODD_TYPE_LAY = "L";
    String SUCCESSFULLY_SAVED = "Coin Details successfully saved";
    String SUCCESSFULLY_UPDATED = "One click coin details successfully updated";
    String USER_ID_NOT_EXIST = "User Id does not exist or Expired";
    String SUCCESSFULLY_UPDATED_COIN_SETTINGS = "Coin settings successfully updated";
    String MATCH_ODDS = "matchOdds";
    String MATCH_ODDS_1 = "matchOdds1";
    String FANCY_BETS = "fancyBets";
    String FANCY_BETS_1 = "fancyBets1";
    String POPULAR = "popular";
    String BACK_ODDS = "backOdds";
    String LAY_ODDS = "layOdds";
    String LIVE_SCORE_DETAILS = "liveScoreDetails";
    String GOALS_MARKET_GROUP_NAME = "goals";
    String ROLE_ID = "roleId";
    String PASSWORD_MATCH = "Password should be different from current password";
    String PASSWORD_NOT_MATCHED = "Please enter valid details";
    String PASSWORD_UPDATE = "Password updated successfully";
    String USER_ID_NOT_VALID = "User Id does not exist or Expired";
    String EMAIL_GENERATED = "Email generated successfully";
    String TCHNICAL_SUPPORT = "Please contact technical support";
    String EMAIL_ID_NOT_EXIST = "Email Id does not exist";
    String EMAIL_ID_INVALID = "Invalid Email-Id";
    String USER_TOKEN_NOT_NULL = "user token should not be null!";
    String UPDATE_SUCCESSFULLY = "updated successfully";
    String NOT_MATCHED = "Password not matched";
    String USER_LOGIN_TOKEN_NULL = "Login User Token can not be null";
    String USER_LOGIN_PASSWORD_NULL = "User Password can not be null";
    String BANKING_TRANSACTIONS_LIST_NULL = "Banking transactions can not be null";
    String FANCY_MARKETS = "fancyMarkets";
    //    String SUCCESS = "get transaction detail success";
    String FAILED_GET_TRANS_DETAILS = "trans detail failed";
    String FAILED_GET_SESSION_KEY = "Failed to get betfair Session key";
    String DATE_FORMAT = "yyyy-MM-dd";
    //    String TRANSACTION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";
    String LEAGUE_CREATED_KEY = "isCreated";
    Integer MATCH_ODDS_ID = 1;
    Integer FANCY_ID = 25;
    String TIED_MATCH = "Tied Match";
    String MARKET_GROUP_ID = "marketGroupId";
    String SPORTS = "sports";
    Double DOUBLE = 0.00;
    String EVENTS_DOES_NOT_EXISTS = "Some events no more exists";
    //    String MARKETS__DOES_NOT_EXISTS = "Some markets no more exists";
    String PLUS_SYMBOL = "+";
    Integer BET_FAIR_PROVIDER_ID = 2;
    String BETS = "bets";
    //    String EVENT_ID_NULL = "EventTO Id can not be null";
//    String EVENT_NAME_NULL = "EventTO Name can not be null";
//    String MARKET_NAME_NULL = "Market Name can not be null";
    String BET_STATUS_ID_NULL = "Bet Status Id can not be null";
    //    String USER_NAME_NULL = "loginUserName can not be null";
    String STATUS_ID = "status_id";
    Integer BET_STATUS_PLACED_ID = 1;
    //    Integer BET_STATUS_LOST_ID = 3;
//    Integer BET_STATUS_CANCELLED_ID = 9;
//    String BET_STATUS_CANCELLED = "Cancelled";
//    String BET_STATUS_VOIDED = "Voided";
    String GET_BETTING_HISTORY_DETAILS_SERVICE_NAME = "getBettingHistoryDetails";
    String YES_ODD_TYPE = "yes";
    String NO_ODD_TYPE = "no";
    String B_ODD_TYPE = "b";
    String L_ODD_TYPE = "l";
    String BACK_ODD_TYPE = "back";
    String LAY_ODD_TYPE = "lay";
    String GET_ACCOUNT_SUMMARY_SERVICE_NAME = "get-account-summary";
    String GET_ACCOUNT_STATEMENT_SERVICE_NAME = "get-account-statement";
    Integer MATCHED = 1;
    Integer UN_MATCHED = 0;

    String BETTING_HISTORY = "bettingHistory";
    //    String BET_STATUS_NOT_SETTLED = "Not Settled";
    String PROFIT_LOSS_DETAILS = "profitLossDetails";
    String IS_FANCY = "isFancy";
    String IS_CREATED = "isCreated";
    String DRAW_PROFIT_LOSS = "drawTeamProfitLoss";
    String INVALID_SESSION = "In-valid Session token/App-key";
    String APP_KEY = "JrRjbg2G9zgKZD94";
    String GET_MARKET_ODDS = "${bm-exchange.uri.get-market_odds}";
    String NAME = "name";

    String LIVE_MARKETBOOK_URL = "https://api.betfair.com/exchange/betting/rest/v1.0/listMarketBook/";
    String SAVE_USER_TIMEZONE = "${bm-exchange.uri.save-user-timezone}";
    String USER_TIME_ZONE = "userTimeZone";
    String AGENTID="agentId";
    String AGENTNAME="agentName";
    String INACTIVE_SESSION="inactiveSessionSuspension";
    String BOOKPOSITION="bookPosition";
    String BETLISTLIVE="betlistLive";
    String RESULTDELARATION="resultDeclation";
    String AVALABLE="available";
    String STATUS="status";
    String TOKEN="token";
    String ENCODEDPASSWORD="encodedPassword";
    String GET_AGENT_USERS_PROFIT_LOSS_DETAILS = "${bm-exchange.uri.get-agent-user-profit-loss}";

    enum PasswordMessages {
        PASSWORD_UPDATE, PASSWORD_NOT_MATCHED, PASSWORD_MATCH
    }

    enum EmailVerificationStatus {
        VERIFIED, EMAIL_ALREADY_VERIFIED, TOKEN_EXPIRED
    }

    interface BetStatus {
        String VOIDED = "Voided";
        String CANCELLED = "Cancelled";
        String NOT_SETTLED = "Not Settled";
        String BET_SETTLED = "Bet Settled";
        Integer CANCELLED_ID = 9;
    }

    interface SportId {
        Integer CRICKET = 4;
    }

    interface BetSlipConstants {
        String MARKET_SUSPENDED = "The market has been suspended";
        String ODDS_CHANGED = "Odds have been changed. Kindly accept odd changes";
        String INVALID_USER = "Invalid user or token";
        String USER_NOT_PLAYER = "User is not a player";
        String SUSPENDED_LOCKED_USER = "If you have unmatched bets it would be cancelled as your account has been suspended. Please contact your up-line";
        String INSUFFICIENT_BALANCE = "Insufficient balance in your account";
        String INVALID_EVENT = "The event is no more valid";
        String INVALID_MARKET = "The market is no more valid";
        String INVALID_OUTCOME = "The selection is no more valid";
        String BET_SLIP_TRANSACTION_TYPE_ID = "BET-SLIP:";
        String BET_SLIP_TRANSACTION_TYPE = "BETSLIP";
        String ODD_TYPE = "B";
        String BET_SLIP_FROM_TO = "bet-slip";
        String BET_PLACED = "OPEN";
        String EXCEEDED_EXPOSURE_LIMIT = "You have exceeded your exposure limit. Please contact your up-line";
        String EXPOSURE_ZERO = "Exposure is 0. Please contact your up-line";
        String EXCEEDED_EXPOSURE = "Estimated Profit is more than exposure limit";;
    }

    interface OpenBetsConstants {
        String OPEN_BETS_FROM_TO = "open-bets";
        String OPEN_BETS_TRANSACTION_TYPE_ID = "OPEN-BETS:";
        String OPEN_BETS_TRANS_TYPE = "OPENBETS";
    }

    interface EmailVerificationConstants {
        String VERIFIED = " Email Verified please login into Gamex : http://www.gamex.group/";
        String ALREADY_VERIFIED = "Email already verified please login into Gamex : http://www.gamex.group/";
        String TOKEN_EXPIRED = "Email address is not verified. Link Expired please sign up again to verify into Gamex : http://www.gamex.group/";
        String NOT_VERIFIED = "Email is not verified. Please check entered email at the time of registration to verify email. Thanks";
        String EMAIL_VERIFICATION = "Gamex Email Verification";
        String CONTACT = " To confirm your account, please click here : ";
        String CREATE_PASSWORD = " To create new password, please click here : ";
    }

    interface OutcomeNames {
        String ONE = "1";
        String X = "X";
        String TWO = "2";
        String TO_DRAW = "To Draw";
//        String YES = "YES";
//        String NO = "NO";
    }
}