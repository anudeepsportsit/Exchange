package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dao.DownlineListDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.UserBetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.CancelInstructionTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.service.BetPlacement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class DownlineListDaoImpl implements DownlineListDao {

    private static final String PROC_GET_USER_DOWNLINE_LIST_DETAILS = "select * from get_user_downline_list_details(?,?)";
    private static final String PROC_GET_USER_DOWNLINE_LIST_USER_BALANCE_DETAILS = "select * from get_user_downline_list_user_balance_details_v1(?,?)";
    private static final String PROC_SAVE_USER = "select * from save_user_details_v2(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String PROC_UPDATE_STATUS = "select * from update_user_status_v2(?,?,?,?)";
    private static final String PROC_GET_EMAIL_ID_COUNT = "select * from get_emailid_count(?)";
    private static final String PROC_GET_USERNAME_COUNT = "select * from get_username_count(?)";
    private static final String PROC_GET_BET_LIST_LIVE = "select * from get_betlist_live(?)";
    private static final String PROC_GET_BET_LIST_LIVE_DETAILS = "select * from get_betlist_live_details(?,?,?,?,?,?)";
    private static final String PROC_GET_BET_USER_PASSWORD = "select * from get_user_password(?)";
    private static final String PROC_GET_MY_REPORT_DOWNLIST = "select * from get_myreport_downlinelist_sport(?,?,?,?)";
    private static final String PROC_GET_DB_TOKEN = "select * from validate_token(?)";
    private static final String PROC_GET_DB_ROLE_ID = "select * from get_role(?)";
    private static final String PROC_GET_DB_ROLE_ID_SUB_ROLE_ID = "select * from get_role(?,?)";
    private static final String PROC_GET_USERBALANCE = "select * from get_userbalance(?)";
    private static final String INTEGER_TYPE = "integer";
    private static final String QUERY_GET_SESSION_TOKEN = "select name from betfairodds.betfair_key";



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BetPlacement betPlacement;


    @Override
    public Boolean getDbToken(String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_DB_TOKEN,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> this.getDbToken(cs.executeQuery()));
    }

    @Override
    public Integer getDbRole(String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_DB_ROLE_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);

            return cs;
        }, (CallableStatement cs) -> this.getDbRole(cs.executeQuery()));
    }


    @Override
    public ChangeStatusRoleIdTo getChangeStatusRoleIds(ChangeStatusTO changeStatus) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_DB_ROLE_ID_SUB_ROLE_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, changeStatus.getUserToken());
            cs.setInt(2, changeStatus.getUserId());

            return cs;
        }, (CallableStatement cs) -> this.getChangeStatusRoles(cs.executeQuery()));
    }

    @Override
    public Double getBalance(String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_USERBALANCE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);


            return cs;
        }, (CallableStatement cs) -> this.getUserBalance(cs.executeQuery()));
    }

    @Override
    public String updateCredit(List<CreditLimitTO> creditLimitTO) {
        try {
            String SQL = "update userbalance set creditlimit = ? where userid = ?";
            creditLimitTO.stream().forEach(creditLimitTO1 ->
            jdbcTemplate.update(SQL, creditLimitTO1.getCreditLimit(), creditLimitTO1.getUserId()));
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }

    @Override
    public boolean isChangedByAdmin(Integer userId) {
        try {
            String SQL = "select status_changed_by_admin from bmeusers where id=?";
            return jdbcTemplate.queryForObject(SQL, new Object[]{userId}, Boolean.class);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String getUser(Integer userId) {
        try {
            String SQL = "select userid from bmeusers where id=?";
            return jdbcTemplate.queryForObject(SQL, new Object[]{userId}, String.class);
        }catch (Exception e){
            return "";
        }
    }


    public Double getUserBalance(ResultSet rs) throws SQLException {
        Double balance = 0.0;
        while (rs.next()) {
            balance = rs.getDouble(ColumnLabelConstants.USER_BALANCE);
        }
        return balance;


    }

    public List<DownLineUserDetailsTO> getDownlineListDetails(String userToken, Integer userId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_USER_DOWNLINE_LIST_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setInt(2, userId != null ? userId : 0);
            return cs;
        }, (CallableStatement cs) -> this.populateDownlineListDetails(cs.executeQuery()));
    }


    @Override
    public DownlineListDetailsWrapperTO getDownListUserBalanceDetails(String userToken, Integer userId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_USER_DOWNLINE_LIST_USER_BALANCE_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setInt(2, userId != null ? userId : 0);
            return cs;
        }, (CallableStatement cs) -> this.populateDownlineListUserBalances(cs.executeQuery()));
    }

    @Override
    public Integer processSaveUser(UserDetailsTO registrationDetails, String encodedPassword, String emailConfirmationToken) {

        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_SAVE_USER,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, registrationDetails.getUserName());
            cs.setString(2, registrationDetails.getEmailId());
            cs.setString(3, encodedPassword);
            cs.setString(4, registrationDetails.getPhoneNumber());
            cs.setString(5, registrationDetails.getFirstName());
            cs.setString(6, registrationDetails.getLastName());
            cs.setDouble(7, registrationDetails.getCommission());
            cs.setInt(8, registrationDetails.getRoleId());
            cs.setInt(9, registrationDetails.getTimezoneId());
            cs.setString(10, registrationDetails.getUserToken());
            cs.setInt(11, registrationDetails.getCurrencyId());
            cs.setDouble(12, registrationDetails.getFancyCommission());
            cs.setDouble(13, registrationDetails.getDepositedAmount());
            cs.setDouble(14, registrationDetails.getExposure());

            return cs;
        }, (CallableStatement cs) -> registerAndGetId(cs.executeQuery(), ColumnLabelConstants.PROFILE_ID));


    }

    private DownlineListDetailsWrapperTO populateDownlineListUserBalances(ResultSet rs) throws SQLException {
        DownlineListDetailsWrapperTO downlineListDetailsWrapper = new DownlineListDetailsWrapperTO();
        while (rs.next()) {
            downlineListDetailsWrapper.setBalance(rs.getDouble(ColumnLabelConstants.USER_BALANCE));
            downlineListDetailsWrapper.setTotalExposure(rs.getDouble(ColumnLabelConstants.TOTAL_EXPOSURE));
            downlineListDetailsWrapper.setTotalAvailableBalance(rs.getDouble(ColumnLabelConstants.TOTAL_AVAILABLE_BALANCE));
            downlineListDetailsWrapper.setCreditLimmit(0.0);
        }
        return downlineListDetailsWrapper;
    }

    private DownlineListDetailsWrapperTO populateDownlineListCreditUserBalances(ResultSet rs) throws SQLException {
        DownlineListDetailsWrapperTO downlineListDetailsWrapper = new DownlineListDetailsWrapperTO();
        while (rs.next()) {
            downlineListDetailsWrapper.setBalance(rs.getDouble(ColumnLabelConstants.USER_BALANCE));
            downlineListDetailsWrapper.setTotalExposure(rs.getDouble(ColumnLabelConstants.TOTAL_EXPOSURE));
            downlineListDetailsWrapper.setTotalAvailableBalance(rs.getDouble(ColumnLabelConstants.TOTAL_AVAILABLE_BALANCE));
            downlineListDetailsWrapper.setCreditLimmit(rs.getDouble(ColumnLabelConstants.CREDIT_LIMIT));
        }
        return downlineListDetailsWrapper;
    }

    private List<DownLineUserDetailsTO> populateDownlineListDetails(ResultSet rs) throws SQLException {
        List<DownLineUserDetailsTO> downLineUserDetailsList = new ArrayList<>();
        DownLineUserDetailsTO downLineUserDetails = null;
        int lastUserId = 0;
        while (rs.next()) {
            int userId = rs.getInt(ColumnLabelConstants.USER_ID);
            if (userId == lastUserId) {
                downLineUserDetails.setUserId(rs.getInt(ColumnLabelConstants.USER_ID));
                downLineUserDetails.setAccount(rs.getString(ColumnLabelConstants.USER_ACCOUNT));
                downLineUserDetails.setAvailableBalance(rs.getDouble(ColumnLabelConstants.AVAILABLE_BALANCE));
                downLineUserDetails.setPlayerBalance(rs.getDouble(ColumnLabelConstants.PLAYER_BALANCE));
                downLineUserDetails.setCreditLimmit(1d);
                downLineUserDetails.setExposure(rs.getDouble(ColumnLabelConstants.EXPOSURE));
                downLineUserDetails.setExposureLimit(rs.getDouble(ColumnLabelConstants.EXPOSURE_LIMIT));
                downLineUserDetails.setRoleId(rs.getInt(ColumnLabelConstants.ROLE_ID));
                downLineUserDetails.setRefProfitLoss(downLineUserDetails.getAvailableBalance() + downLineUserDetails.getExposure());
                downLineUserDetails.setStatusId(rs.getInt(ColumnLabelConstants.STATUS));
                downLineUserDetails.setBalance(downLineUserDetails.getAvailableBalance() + downLineUserDetails.getExposure());
            } else {
                lastUserId = rs.getInt(ColumnLabelConstants.USER_ID);
                rs.previous();
                downLineUserDetails = new DownLineUserDetailsTO();
                downLineUserDetailsList.add(downLineUserDetails);
            }
        }
        return downLineUserDetailsList;
    }


    private List<DownLineUserDetailsTO> populateDownlineListCreditDetails(ResultSet rs) throws SQLException {
        List<DownLineUserDetailsTO> downLineUserDetailsList = new ArrayList<>();
        DownLineUserDetailsTO downLineUserDetails = null;
        int lastUserId = 0;
        while (rs.next()) {
            int userId = rs.getInt(ColumnLabelConstants.USER_ID);
            if (userId == lastUserId) {
                downLineUserDetails.setUserId(rs.getInt(ColumnLabelConstants.USER_ID));
                downLineUserDetails.setAccount(rs.getString(ColumnLabelConstants.USER_ACCOUNT));
                downLineUserDetails.setAvailableBalance(rs.getDouble(ColumnLabelConstants.AVAILABLE_BALANCE));
                downLineUserDetails.setCreditLimmit(rs.getDouble(ColumnLabelConstants.CREDIT_LIMIT));
                downLineUserDetails.setPlayerBalance(rs.getDouble(ColumnLabelConstants.PLAYER_BALANCE));
                downLineUserDetails.setExposure(rs.getDouble(ColumnLabelConstants.EXPOSURE));
                downLineUserDetails.setExposureLimit(rs.getDouble(ColumnLabelConstants.EXPOSURE_LIMIT));
                downLineUserDetails.setRoleId(rs.getInt(ColumnLabelConstants.ROLE_ID));
                downLineUserDetails.setRefProfitLoss(downLineUserDetails.getAvailableBalance() + downLineUserDetails.getExposure());
                downLineUserDetails.setStatusId(rs.getInt(ColumnLabelConstants.STATUS));
                downLineUserDetails.setBalance(downLineUserDetails.getAvailableBalance() + downLineUserDetails.getExposure());
            } else {
                lastUserId = rs.getInt(ColumnLabelConstants.USER_ID);
                rs.previous();
                downLineUserDetails = new DownLineUserDetailsTO();
                downLineUserDetailsList.add(downLineUserDetails);
            }
        }
        return downLineUserDetailsList;
    }

    private Integer registerAndGetId(ResultSet rs, String idType) throws SQLException {
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(idType);
        }
        return id;
    }

    @Override
    public Integer getEmailIdCount(String emailId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_EMAIL_ID_COUNT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, emailId);
            return cs;
        }, (CallableStatement cs) -> registerAndGetId(cs.executeQuery(), ColumnLabelConstants.EMAIL_ID_COUNT));
    }

    @Override
    public Integer getUserNameCount(String userName) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_USERNAME_COUNT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userName);
            return cs;
        }, (CallableStatement cs) -> registerAndGetId(cs.executeQuery(), ColumnLabelConstants.USERNAME_COUNT));
    }

    @Override
    public Integer updateStatus(ChangeStatusTO changeStatusTO, UserBetDetailsTO userBetDetailsTO) {
        String SQL = "";
        if(userBetDetailsTO.getRoleId() == 1) {
            SQL = "update bmeusers set statusid=?, status_changed_by=?, status_changed_by_admin='true' where id=?";
        }else{
            SQL = "update bmeusers set statusid=?, status_changed_by=?, status_changed_by_admin='false' where id=?";
        }
        jdbcTemplate.update(SQL, new Object[] { changeStatusTO.getStatusCode(), userBetDetailsTO.getUserId(), changeStatusTO.getUserId()});

        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_UPDATE_STATUS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, changeStatusTO.getUserToken());
            cs.setInt(2, changeStatusTO.getStatusCode());
            cs.setInt(3, changeStatusTO.getUserId());
            cs.setString(4, changeStatusTO.getUserPassword());
            return cs;
        }, (CallableStatement cs) -> registerAndGetBetIds(cs.executeQuery(), ColumnLabelConstants.PROFILE_ID));
    }

    private Integer registerAndGetBetIds(ResultSet rs, String profileId) throws SQLException {
        try {
            while (rs.next()) {
                List<CancelInstructionTO> betIds = new ArrayList<>();
                CancelInstructionTO cancelInstructionTO = new CancelInstructionTO();
                String marketId = rs.getString(ColumnLabelConstants.MARKET_ID);
                cancelInstructionTO.setBetId(String.valueOf(rs.getLong(ColumnLabelConstants.BET_ID)));
                betIds.add(cancelInstructionTO);
                betPlacement.cancelBets(marketId, betIds, jdbcTemplate.queryForObject(QUERY_GET_SESSION_TOKEN, String.class));
            }
            return 1;
        }catch (Exception e){
            return 1;
        }
    }

    @Override
    public List<BetListLiveTO> getDownlineBetListLive(String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_BET_LIST_LIVE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);

            return cs;
        }, (CallableStatement cs) -> this.populateBetListLive(cs.executeQuery()));
    }

    @Override
    public BetListLiveWrapperTO getDownlineBetListLiveDetails(BetListLiveDetailsInputTO betListLiveDetailsInput) {

        return jdbcTemplate.execute(con -> {

            CallableStatement cs = con.prepareCall(PROC_GET_BET_LIST_LIVE_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, betListLiveDetailsInput.getUserToken());
            cs.setInt(2, betListLiveDetailsInput.getOrderOfDisplay());
            cs.setInt(3, betListLiveDetailsInput.getOrder());
            cs.setInt(4, betListLiveDetailsInput.getBetStatus());
            cs.setArray(5, con.createArrayOf(INTEGER_TYPE, betListLiveDetailsInput.getSportId()));
            cs.setInt(6, betListLiveDetailsInput.getTransactionLimit());

            return cs;
        }, (CallableStatement cs) -> this.populateBetListLiveDetails(cs.executeQuery(), betListLiveDetailsInput));
    }

    @Override
    public String getUserDBPassword(String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_BET_USER_PASSWORD,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);

            return cs;
        }, (CallableStatement cs) -> this.getPassword(cs.executeQuery()));
    }


    private String getPassword(ResultSet rs) throws SQLException {

        String password = null;
        while (rs.next()) {
            password = rs.getString(ColumnLabelConstants.USER_DB_PASSWORD);

        }
        return password != null ? password : "";
    }

    private Boolean getDbToken(ResultSet rs) throws SQLException {

        Boolean token = null;
        while (rs.next()) {
            token = rs.getBoolean(ColumnLabelConstants.USER_DB_TOKEN);
        }
        return token;
    }

    private Integer getDbRole(ResultSet rs) throws SQLException {

        Integer dbRole = 0;
        while (rs.next()) {
            dbRole = rs.getInt(ColumnLabelConstants.ROLE_ID);
        }
        return dbRole;
    }


    private ChangeStatusRoleIdTo getChangeStatusRoles(ResultSet rs) throws SQLException {

        ChangeStatusRoleIdTo changeStatusRoles = new ChangeStatusRoleIdTo();
        while (rs.next()) {
            changeStatusRoles.setRoleId(rs.getInt(ColumnLabelConstants.ROLE_ID));
            changeStatusRoles.setSubRoleId(rs.getInt(ColumnLabelConstants.SUB_ROLE_ID));

        }
        return changeStatusRoles;
    }

    private List<BetListLiveTO> populateBetListLive(ResultSet rs) throws SQLException {

        List<BetListLiveTO> list = new ArrayList<>();
        while (rs.next()) {
            BetListLiveTO betListLiveTO = new BetListLiveTO();
            betListLiveTO.setId(rs.getInt(ColumnLabelConstants.BET_LIST_LIVE_ID));
            betListLiveTO.setName(rs.getString(ColumnLabelConstants.BET_LIST_LIVE_NAME));
            list.add(betListLiveTO);
        }
        return list;
    }

    private BetListLiveWrapperTO populateBetListLiveDetails(ResultSet rs, BetListLiveDetailsInputTO betListLiveDetailsInput) throws SQLException {
        BetListLiveWrapperTO list = new BetListLiveWrapperTO();

        List<BetListStatusTO> betListLiveDetailsTO = new ArrayList<>();
        while (rs.next()) {
            BetListStatusTO betListLiveDetails = new BetListStatusTO();
            betListLiveDetails.setSuperId(rs.getString(ColumnLabelConstants.SUPER_NAME));
            betListLiveDetails.setMasterId(rs.getString(ColumnLabelConstants.MASTER_NAME));
            betListLiveDetails.setPlayerId(rs.getString(ColumnLabelConstants.PLAYER_NAME));
            betListLiveDetails.setBetId(rs.getInt(ColumnLabelConstants.BET_ID));
            betListLiveDetails.setBetPlaced(rs.getString(ColumnLabelConstants.BET_PLACED));
            betListLiveDetails.setIpAddress(rs.getString(ColumnLabelConstants.IP_ADDRESS));
            betListLiveDetails.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
            betListLiveDetails.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
            betListLiveDetails.setMarket(rs.getString(ColumnLabelConstants.MARKET_NAME));
            betListLiveDetails.setSelection(rs.getString(ColumnLabelConstants.SELECTION));
            betListLiveDetails.setType(rs.getString(ColumnLabelConstants.ODD_TYPE));
            betListLiveDetails.setOddsRequest(rs.getDouble(ColumnLabelConstants.ODD_REQUEST));
            betListLiveDetails.setStake(rs.getDouble(ColumnLabelConstants.BETLIST_STAKE));
            betListLiveDetails.setLiability(rs.getDouble(ColumnLabelConstants.LIABILTY));
            betListLiveDetails.setIsMatched(rs.getInt(ColumnLabelConstants.BET_LIST_LIVE_ISMATCHED));
            betListLiveDetails.setBetStatus(rs.getString(ColumnLabelConstants.BET_STATUS));
            betListLiveDetailsTO.add(betListLiveDetails);

        }

        Iterator<BetListStatusTO> BetListStatus = betListLiveDetailsTO.iterator();

        List<BetListStatusTO> matchedList = new ArrayList<>();
        List<BetListStatusTO> unMatchedList = new ArrayList<>();

        while (BetListStatus.hasNext()) {

            BetListStatusTO betListStatusTO = BetListStatus.next();
            if (betListStatusTO.getIsMatched() != null && (betListStatusTO.getIsMatched() == 1||(betListStatusTO.getBetStatus().equalsIgnoreCase(AdminPanelConstants.PLACED)))) {
                BetListStatusTO matched = new BetListStatusTO();
                matched.setSuperId(betListStatusTO.getSuperId());
                matched.setMasterId(betListStatusTO.getMasterId());
                matched.setPlayerId(betListStatusTO.getPlayerId());
                matched.setBetId(betListStatusTO.getBetId());
                matched.setBetPlaced(betListStatusTO.getBetPlaced());
                matched.setIpAddress(betListStatusTO.getIpAddress());
                matched.setSportName(betListStatusTO.getSportName());
                matched.setEventName(betListStatusTO.getEventName());
                matched.setMarket(betListStatusTO.getMarket());
                matched.setSelection(betListStatusTO.getSelection());
                matched.setType(betListStatusTO.getType());
                matched.setOddsRequest(betListStatusTO.getOddsRequest());
                matched.setStake(betListStatusTO.getStake());
                matched.setBetStatus(betListStatusTO.getBetStatus());
                matched.setLiability(betListStatusTO.getLiability());
                matched.setIsMatched(betListStatusTO.getIsMatched());

                matchedList.add(matched);

            } else if (betListStatusTO.getIsMatched() != null && (betListStatusTO.getIsMatched() == 0||(betListStatusTO.getBetStatus().equalsIgnoreCase(AdminPanelConstants.PLACED)))) {
                BetListStatusTO unMatched = new BetListStatusTO();
                unMatched.setSuperId(betListStatusTO.getSuperId());
                unMatched.setMasterId(betListStatusTO.getMasterId());
                unMatched.setPlayerId(betListStatusTO.getPlayerId());
                unMatched.setBetId(betListStatusTO.getBetId());
                unMatched.setBetPlaced(betListStatusTO.getBetPlaced());
                unMatched.setIpAddress(betListStatusTO.getIpAddress());
                unMatched.setSportName(betListStatusTO.getSportName());
                unMatched.setEventName(betListStatusTO.getEventName());
                unMatched.setMarket(betListStatusTO.getMarket());
                unMatched.setSelection(betListStatusTO.getSelection());
                unMatched.setType(betListStatusTO.getType());
                unMatched.setOddsRequest(betListStatusTO.getOddsRequest());
                unMatched.setStake(betListStatusTO.getStake());
                unMatched.setBetStatus(betListStatusTO.getBetStatus());
                unMatched.setLiability(betListStatusTO.getLiability());
                unMatched.setIsMatched(betListStatusTO.getIsMatched());
                unMatchedList.add(unMatched);
            }



        }
        if (betListLiveDetailsInput.getBetStatus() == 5) {
            list.setMatched(matchedList);
        } else if (betListLiveDetailsInput.getBetStatus() == 6) {
            list.setUnMatched(unMatchedList);
        }

        return list;
    }
}