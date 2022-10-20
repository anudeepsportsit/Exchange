package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.utils;

public interface DownLineListConstants {
    String DOWN_LINE_LIST_USER_DETAILS = "userDetails";
    String DOWN_LINE_LIST_URL = "{bm-exchange.uri.adminpanel.downline-list.getdownline-list-details}";
    String UPDATE_CREDIT_LIMIT = "{bm-exchange.uri.adminpanel.downline-list.update-credit-limit}";
    String DOWN_LINE_LIST = "${bm-exchange.uri.adminpanel.downline-list}";
    String ADD_USER = "${bm-exchange.uri.adminpanel.downline-list.add-user}";
    String CHANGE_STATUS = "${bm-exchange.uri.adminpanel.downline-list.change-status}";
    String USER_CREATED_KEY = "isCreated";
    String USER_UPDATED_KEY = "isUpdated";
    String REASON_CODE = "reasonCode";
    String REASON = "reason";
    String BET_LIST_LIVE_URL = "${bm-exchange.uri.adminpanel.downline-list.get-betlist-live-sports}";
    String BET_LIST_LIVE_DETAILS = "${bm-exchange.uri.adminpanel.downline-list.get-betlist-live-details}";
    String IS_VALID_PASSWORD = "isValidPassword";
    String GET_DOWNLINE_LIST_DETAILS_SERVICE_NAME = "get-downline-list-details";
    String MY_REPORT_DOWNLIST = "${bm-exchange.uri.adminpanel.my-report-downlist}";
    String IS_VALID_TOKEN = "tokenIsEmpty";
    String WRONG_TOKEN = "wrongToken";
    String CANT_CHANGE_STATUS = "Can't change status other that Admin";

    enum TransactionLimits {
        TRANSACTION_LIMIT_25(1, 25),
        TRANSACTION_LIMIT_50(2, 50),
        TRANSACTION_LIMIT_100(3, 100);

        private int refValue;
        private int transactionLimit;

        TransactionLimits(int refValue, int transactionLimit) {
            this.refValue = refValue;
            this.transactionLimit = transactionLimit;
        }

        public int getRefValue() {
            return refValue;
        }

        public void setRefValue(int refValue) {
            this.refValue = refValue;
        }

        public int getTransactionLimit() {
            return transactionLimit;
        }

        public void setTransactionLimit(int transactionLimit) {
            this.transactionLimit = transactionLimit;
        }
    }
}