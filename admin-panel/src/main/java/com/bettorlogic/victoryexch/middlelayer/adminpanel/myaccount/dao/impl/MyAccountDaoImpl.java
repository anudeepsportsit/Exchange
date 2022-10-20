package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dao.MyAccountDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsInput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory.BettingHistoryDetailsOutput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss.BettingProfitLossOutput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss.ProfitLossBetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss.ProfitLossSubTotalsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss.TotalProfitLossWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.SportDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MyAccountDaoImpl implements MyAccountDao {

    private static final String PROC_GET_ACTIVITY_LOGS = "select * from get_activity_log(?)";
    private static final String PROC_GET_ACCOUNT_STATEMENT_DETAILS = "select * from get_account_statement_details(?,?)";
    private static final String PROC_GET_BETTING_HISTORY_DETAILS = "select * from get_betting_history_details_v1(?, ?, ?, ?, ?, ?)";
    private static final String PROC_GET_BETTING_PROFIT_LOSS_DETAILS = "select * from get_betting_profit_loss_details(?, ?, ?, ?, ?, ?)";
    private static final String PROC_GET_BETTING_PROFIT_LOSS_HEADER_DETAILS = "select * from get_betting_profit_loss_header_details(?, ?, ?, ?, ?, ?)";
    private static final String PROC_GET_ACCOUNT_SUMMARY_DETAILS = "select * from get_account_summary_details(?,?)";
    private static final String PROC_GET_PASSOWRD = "select userpwd from bmeusers where user_login_token= ? ";
    private static final String PROC_UPDATE_PASSWORD = "update bmeusers set userpwd = ? where id = ? ";
    private static final String PROC_UPDATE_EXPOSURE = "update usersettings set exposurelimit=? where bmeuserid=?";
    private static final String PROC_UPDATE_COMMISSION = "update usersettings set commission_charges=? where bmeuserid=?";

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAccountDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserActivityWrapperTO getLogs(String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_ACTIVITY_LOGS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> this.populateActivityLogs(cs.executeQuery()));
    }

    @Override
    public List<AccountStatementTO> getAccountStatement(String userToken,Integer userId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_ACCOUNT_STATEMENT_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setInt(2, userId==null?0:userId);
            return cs;
        }, (CallableStatement cs) -> this.populateAccountStatement(cs.executeQuery()));

    }

    @Override
    public List<BettingHistoryDetailsOutput> getBettingHistoryDetails(
            BettingHistoryDetailsInput bettingHistoryDetails) {
        LOGGER.debug("Fetch data for 'getBettingHistoryDetails' from the stored procedure: "
                + PROC_GET_BETTING_HISTORY_DETAILS);
        List<BettingHistoryDetailsOutput> bettingHistoryDetailsOutputList;
        bettingHistoryDetailsOutputList =
                jdbcTemplate.execute(con -> this.populateBettingDetailsInput(
                        con, PROC_GET_BETTING_HISTORY_DETAILS, bettingHistoryDetails),
                        (CallableStatement cs) -> this.populateBettingHistoryDetails(cs.executeQuery(), bettingHistoryDetails.getBetStatusId()));
        LOGGER.debug("Data for 'getBettingHistoryDetails' has been fetched successfully: " + bettingHistoryDetailsOutputList);
        return bettingHistoryDetailsOutputList;
    }

    private TotalProfitLossWrapperTO getBettingProfitLossBetDetails(BettingHistoryDetailsInput bettingHistoryDetails) {
        LOGGER.debug("Fetch data for 'getBettingHistoryDetails' from the stored procedure: " + PROC_GET_BETTING_PROFIT_LOSS_DETAILS);
        TotalProfitLossWrapperTO totalProfitLossWrapper =
                jdbcTemplate.execute(con -> this.populateBettingDetailsInput(
                        con, PROC_GET_BETTING_PROFIT_LOSS_DETAILS, bettingHistoryDetails)
                        , (CallableStatement cs) -> this.populateBettingProfitLossDetails(cs.executeQuery()));
        LOGGER.debug("Data for 'getBettingHistoryDetails' has been fetched successfully: " + totalProfitLossWrapper.getBettingProfitLossOutputList());

        return totalProfitLossWrapper;
    }

    @Override
    public TotalProfitLossWrapperTO getBettingProfitLossDetails(BettingHistoryDetailsInput bettingHistoryDetails) {
        LOGGER.debug("Fetch data for 'getBettingProfitLossDetails' from the stored procedure: " + PROC_GET_BETTING_PROFIT_LOSS_HEADER_DETAILS);
        bettingHistoryDetails.setSportId(bettingHistoryDetails.getSportId()==null?0:bettingHistoryDetails.getSportId());
        TotalProfitLossWrapperTO totalProfitLossWrapper =
                jdbcTemplate.execute(con -> this.populateBettingDetailsInput(
                        con, PROC_GET_BETTING_PROFIT_LOSS_HEADER_DETAILS, bettingHistoryDetails)
                        , (CallableStatement cs) -> this.populateBettingProfitLossHeaderDetails(cs.executeQuery()));
        assert totalProfitLossWrapper != null;
        TotalProfitLossWrapperTO totalProfitLosDetails = getBettingProfitLossBetDetails(bettingHistoryDetails);
        totalProfitLossWrapper.setBettingProfitLossOutputList(totalProfitLosDetails.getBettingProfitLossOutputList());
        totalProfitLossWrapper.setTotalProfitLoss(totalProfitLosDetails.getTotalProfitLoss());
        LOGGER.debug("Data for 'getBettingProfitLossDetails' has been fetched successfully: " + totalProfitLossWrapper);
        return totalProfitLossWrapper;
    }

    @Override
    public AccountSummaryTO getAccountSummaryDetails(String userToken, Integer userId) {
        LOGGER.info("Fetch data for 'getAccountSummaryDetails' from the stored procedure: " + PROC_GET_ACCOUNT_SUMMARY_DETAILS + " started");
        AccountSummaryTO accountSummary;
        accountSummary = jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_ACCOUNT_SUMMARY_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setInt(2, userId);
            return cs;
        }, (CallableStatement cs) -> this.populateAccountSummaryDetails(cs.executeQuery()));
        LOGGER.info("Fetch data for 'getAccountSummaryDetails' from the stored procedure: " + PROC_GET_ACCOUNT_SUMMARY_DETAILS + " completed");
        return accountSummary;
    }

    private AccountSummaryTO populateAccountSummaryDetails(ResultSet rs) throws SQLException {
        LOGGER.info("Populating data from the result set");
        AccountSummaryTO accountSummary = new AccountSummaryTO();
        while (rs.next()) {
            accountSummary.setAvailableToBet(rs.getBigDecimal(ColumnLabelConstants.USER_BALANCE).toString());
            accountSummary.setFundsAvailableToWithdraw(rs.getBigDecimal(ColumnLabelConstants.USER_BALANCE).toString());
            accountSummary.setCurrentExposure(rs.getBigDecimal(ColumnLabelConstants.USER_EXPOSURE).toString());
            UserProfileDetailsTO userProfileDetails = new UserProfileDetailsTO();
            userProfileDetails.setFirstName(rs.getString(ColumnLabelConstants.FIRST_NAME));
            userProfileDetails.setLastName(rs.getString(ColumnLabelConstants.LAST_NAME));
            userProfileDetails.setBirthday(rs.getString(ColumnLabelConstants.BIRTH_DAY));
            userProfileDetails.setEmailId(rs.getString(ColumnLabelConstants.PROFILE_EMAIL));
            userProfileDetails.setTimeZone(rs.getString(ColumnLabelConstants.TIME_ZONE));
            userProfileDetails.setExposureLimit(rs.getBigDecimal(ColumnLabelConstants.EXPOSURE_LIMIT).toString());
            userProfileDetails.setCommissionChanges(rs.getBigDecimal(ColumnLabelConstants.COMM_CHARGE).toString());
            userProfileDetails.setContactNumber(rs.getString(ColumnLabelConstants.PROFILE_PHONE));
            accountSummary.setUserProfileDetails(userProfileDetails);
        }
        return accountSummary;
    }

    private CallableStatement populateBettingDetailsInput(
            Connection con, String query, BettingHistoryDetailsInput bettingHistoryDetails) throws SQLException {
        CallableStatement cs = con.prepareCall(query,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        cs.setString(1, bettingHistoryDetails.getUserToken());
        cs.setInt(2, bettingHistoryDetails.getPlayerUserId());
        cs.setString(3, bettingHistoryDetails.getFromDate());
        cs.setString(4, bettingHistoryDetails.getToDate());
        cs.setInt(5, bettingHistoryDetails.getTimeInterval());
        if (bettingHistoryDetails.getBetStatusId() != null) {
            cs.setInt(6, bettingHistoryDetails.getBetStatusId());
        }
        if (bettingHistoryDetails.getSportId() != null) {
            cs.setInt(6, bettingHistoryDetails.getSportId());
        }
        return cs;
    }

    private TotalProfitLossWrapperTO populateBettingProfitLossHeaderDetails(ResultSet rs) throws SQLException {
        LOGGER.info("Populating data from the result set");
        TotalProfitLossWrapperTO totalProfitLossWrapper = new TotalProfitLossWrapperTO();
        List<SportDetailsTO> sportDetailsList = new ArrayList<>();
        while (rs.next()) {
            totalProfitLossWrapper.setCurrency(rs.getString(ColumnLabelConstants.CURRENCY));
            totalProfitLossWrapper.setTotalProfitLoss(
                    rs.getBigDecimal(ColumnLabelConstants.TOTAL_PROFIT_LOSS) != null ?
                            rs.getBigDecimal(ColumnLabelConstants.TOTAL_PROFIT_LOSS).toString() : BigDecimal.ZERO.toString());
            totalProfitLossWrapper.setSportProfitLoss(
                    rs.getBigDecimal(ColumnLabelConstants.SPORT_PROFIT_LOSS) != null ?
                            rs.getBigDecimal(ColumnLabelConstants.SPORT_PROFIT_LOSS).toString() : BigDecimal.ZERO.toString());
            SportDetailsTO sportDetails = new SportDetailsTO();
            sportDetails.setId(rs.getInt(ColumnLabelConstants.SPORT_ID));
            sportDetails.setName(rs.getString(ColumnLabelConstants.SPORT_NAME));
            sportDetailsList.add(sportDetails);
        }
        totalProfitLossWrapper.setSportDetailsList(sportDetailsList);
        return totalProfitLossWrapper;
    }

    private TotalProfitLossWrapperTO populateBettingProfitLossDetails(ResultSet rs) throws SQLException {
        LOGGER.info("Populating data from the result set");
        TotalProfitLossWrapperTO totalProfitLossWrapper =new TotalProfitLossWrapperTO();
        List<BettingProfitLossOutput> bettingProfitLossOutputList = new ArrayList<>();
        Float totalProfitLoss= Float.valueOf(0);
        while (rs.next()) {
            BettingProfitLossOutput bettingProfitLossOutput = new BettingProfitLossOutput();
            bettingProfitLossOutput.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
            bettingProfitLossOutput.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
            bettingProfitLossOutput.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
            bettingProfitLossOutput.setStartTime(rs.getString(ColumnLabelConstants.BET_START_TIME));
            bettingProfitLossOutput.setSettledDate(rs.getString(ColumnLabelConstants.BET_SETTLED_DATE));
            bettingProfitLossOutput.setProfitLossBetDetails(this.populateProfitLossBetDetails(rs));
            bettingProfitLossOutputList.add(bettingProfitLossOutput);
            totalProfitLoss=totalProfitLoss+Float.parseFloat(rs.getString(ColumnLabelConstants.PROFIT_LOSS));
        }
        totalProfitLossWrapper.setTotalProfitLoss(totalProfitLoss.toString());
        totalProfitLossWrapper.setBettingProfitLossOutputList(bettingProfitLossOutputList);
        return totalProfitLossWrapper;
    }

    private ProfitLossBetDetailsTO populateProfitLossBetDetails(ResultSet rs) throws SQLException {
        ProfitLossBetDetailsTO profitLossBetDetails = new ProfitLossBetDetailsTO();
        profitLossBetDetails.setBetId(rs.getInt(ColumnLabelConstants.BET_ID));
        profitLossBetDetails.setSelection(rs.getString(ColumnLabelConstants.SELECTION));
        profitLossBetDetails.setBetPlaced(rs.getString(ColumnLabelConstants.BET_PLACED));
        profitLossBetDetails.setStake(rs.getBigDecimal(ColumnLabelConstants.STAKES).toString());
        profitLossBetDetails.setType(rs.getString(ColumnLabelConstants.ODD_TYPE));
        profitLossBetDetails.setOdds(rs.getBigDecimal(ColumnLabelConstants.ODDS).toString());
        profitLossBetDetails.setProfitLoss(rs.getString(ColumnLabelConstants.PROFIT_LOSS));
        profitLossBetDetails.setProfitLossSubTotals(this.populateProfitLossSubTotals(rs));
        return profitLossBetDetails;
    }

    private ProfitLossSubTotalsTO populateProfitLossSubTotals(ResultSet rs) throws SQLException {
        ProfitLossSubTotalsTO profitLossSubTotals = new ProfitLossSubTotalsTO();
        profitLossSubTotals.setTotalStake(rs.getBigDecimal(ColumnLabelConstants.STAKES).toString());
        profitLossSubTotals.setMarketSubTotal(rs.getBigDecimal(ColumnLabelConstants.MARKET_SUB_TOTAL) != null ?
                rs.getBigDecimal(ColumnLabelConstants.MARKET_SUB_TOTAL).toString() : BigDecimal.ZERO.toString());
        BigDecimal commission = BigDecimal.ZERO;
        if (new BigDecimal(profitLossSubTotals.getMarketSubTotal()).compareTo(BigDecimal.ZERO) < 0) {
            profitLossSubTotals.setCommission(rs.getBigDecimal(ColumnLabelConstants.COMMISSION) != null ?
                    rs.getBigDecimal(ColumnLabelConstants.COMMISSION).toString() : BigDecimal.ZERO.toString());
            commission = rs.getBigDecimal(ColumnLabelConstants.COMMISSION);
        }
        BigDecimal netMarketTotal = new BigDecimal(profitLossSubTotals.getMarketSubTotal()).add(commission);
        profitLossSubTotals.setNetMarketTotal(netMarketTotal.toString());
        this.setSubTotalsAccordingToOddType(rs, profitLossSubTotals);
        return profitLossSubTotals;
    }

    private void setSubTotalsAccordingToOddType(ResultSet rs, ProfitLossSubTotalsTO profitLossSubTotals) throws SQLException {
        switch (rs.getString(ColumnLabelConstants.ODD_TYPE).toLowerCase()) {
            case SportsBookConstants.YES_ODD_TYPE:
                profitLossSubTotals.setBackSubTotal(rs.getBigDecimal(ColumnLabelConstants.STAKE_AMT) != null ?
                        rs.getString(ColumnLabelConstants.PROFIT_LOSS) : BigDecimal.ZERO.toString());
                if (rs.getString(ColumnLabelConstants.ODD_TYPE).contains(SportsBookConstants.YES_ODD_TYPE)) {
                    profitLossSubTotals.setBackSubTotal(rs.getBigDecimal(ColumnLabelConstants.STAKE_AMT) != null ?
                            rs.getString(ColumnLabelConstants.PROFIT_LOSS) : BigDecimal.ZERO.toString());
                }
                break;
            case SportsBookConstants.NO_ODD_TYPE:
                profitLossSubTotals.setLaySubTotal(rs.getBigDecimal(ColumnLabelConstants.STAKE_AMT) != null ?
                        rs.getString(ColumnLabelConstants.PROFIT_LOSS) : BigDecimal.ZERO.toString());
                if (rs.getString(ColumnLabelConstants.ODD_TYPE).contains(SportsBookConstants.NO_ODD_TYPE)) {
                    profitLossSubTotals.setLaySubTotal(rs.getBigDecimal(ColumnLabelConstants.STAKE_AMT) != null ?
                            rs.getString(ColumnLabelConstants.PROFIT_LOSS) : BigDecimal.ZERO.toString());
                }
                break;
            case SportsBookConstants.B_ODD_TYPE:
            case SportsBookConstants.BACK_ODD_TYPE:
                profitLossSubTotals.setBackSubTotal(rs.getBigDecimal(ColumnLabelConstants.STAKE_AMT) != null ?
                        rs.getString(ColumnLabelConstants.PROFIT_LOSS) : BigDecimal.ZERO.toString());
                profitLossSubTotals.setBackLay(true);
                break;
            case SportsBookConstants.L_ODD_TYPE:
            case SportsBookConstants.LAY_ODD_TYPE:
                profitLossSubTotals.setLaySubTotal(rs.getBigDecimal(ColumnLabelConstants.STAKE_AMT) != null ?
                        rs.getString(ColumnLabelConstants.PROFIT_LOSS) : BigDecimal.ZERO.toString());
                profitLossSubTotals.setBackLay(true);
                break;
        }
    }

    private List<BettingHistoryDetailsOutput> populateBettingHistoryDetails(ResultSet rs, Integer betStatusId) throws SQLException {
        LOGGER.info("Populating data from the result set");
        List<BettingHistoryDetailsOutput> bettingHistoryDetailsList = new ArrayList<>();
        while (rs.next()) {
            BettingHistoryDetailsOutput bettingHistoryDetails = new BettingHistoryDetailsOutput();
            bettingHistoryDetails.setBetId(rs.getInt(ColumnLabelConstants.BET_ID));
            bettingHistoryDetails.setPlayerId(rs.getString(ColumnLabelConstants.PLAYER_NAME));
            bettingHistoryDetails.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
            bettingHistoryDetails.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
            bettingHistoryDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
            bettingHistoryDetails.setSelection(rs.getString(ColumnLabelConstants.SELECTION));
            bettingHistoryDetails.setBetPlaced(rs.getString(ColumnLabelConstants.BET_PLACED));
            bettingHistoryDetails.setType(rs.getString(ColumnLabelConstants.ODD_TYPE));
            bettingHistoryDetails.setOddReq(rs.getString(ColumnLabelConstants.ODDS_REQUEST));
            bettingHistoryDetails.setStake(rs.getDouble(ColumnLabelConstants.STAKES));
            this.setProfitLossMessages(rs.getInt(ColumnLabelConstants.BET_STATUS_ID), bettingHistoryDetails, rs);
            bettingHistoryDetails.setBetDetails(this.populateBetDetails(rs, bettingHistoryDetails));
            bettingHistoryDetails.setUserCancel(rs.getString(ColumnLabelConstants.USER_CANCEL));
            bettingHistoryDetailsList.add(bettingHistoryDetails);
        }
        return bettingHistoryDetailsList;
    }

    private void setProfitLossMessages(Integer betStatusId, BettingHistoryDetailsOutput bettingHistoryDetails, ResultSet rs) throws SQLException {
        if (rs.getInt(ColumnLabelConstants.IS_MATCHED) == SportsBookConstants.MATCHED) {
            bettingHistoryDetails.setAvgOddsMatched(rs.getDouble(ColumnLabelConstants.AVG_ODDS));
        }
        if (betStatusId.equals(SportsBookConstants.BetStatus.CANCELLED_ID)) {
            bettingHistoryDetails.setProfitLoss(SportsBookConstants.BetStatus.CANCELLED);
        }
        switch (betStatusId) {
            case 4:
                bettingHistoryDetails.setProfitLoss(SportsBookConstants.BetStatus.VOIDED);
                break;
            case 1:
                bettingHistoryDetails.setProfitLoss(SportsBookConstants.BetStatus.NOT_SETTLED);
                break;
            case 8:
                bettingHistoryDetails.setProfitLoss(SportsBookConstants.BetStatus.CANCELLED);
                break;
            case 2:
            case 3:
                bettingHistoryDetails.setProfitLoss(rs.getString(ColumnLabelConstants.PROFIT_LOSS));
                break;
            default:
                bettingHistoryDetails.setProfitLoss(rs.getString(ColumnLabelConstants.PROFIT_LOSS));
                break;

        }
    }

    private BetDetailsTO populateBetDetails(ResultSet rs, BettingHistoryDetailsOutput bettingHistoryDetails) throws SQLException {
        BetDetailsTO betDetails = new BetDetailsTO();
        betDetails.setBetTaken(bettingHistoryDetails.getBetPlaced());
        betDetails.setOddsReq(bettingHistoryDetails.getOddReq());
        betDetails.setStake(bettingHistoryDetails.getStake());
        betDetails.setLiability(rs.getDouble(ColumnLabelConstants.LIABILTY));
        betDetails.setOddsMatched(rs.getString(ColumnLabelConstants.BET_ODDS));
        return betDetails;
    }

    private List<AccountStatementTO> populateAccountStatement(ResultSet rs) throws SQLException {
        List<AccountStatementTO> accountStatementList = new ArrayList<>();
        while (rs.next()) {
            AccountStatementTO accountStatement = new AccountStatementTO();
            accountStatement.setUpdatedDateTime(rs.getString(ColumnLabelConstants.TRANS_DATE));
            accountStatement.setDepositAmount(rs.getDouble(ColumnLabelConstants.DEPSOIT_AMT));
            accountStatement.setWithdrawAmount(rs.getDouble(ColumnLabelConstants.WITHDRAW_AMT));
            accountStatement.setFromAccountName(rs.getString(ColumnLabelConstants.FROM_USER_ID));
            accountStatement.setToAccountName(rs.getString(ColumnLabelConstants.TO_USER_ID));
            accountStatement.setUserRemarks(rs.getString(ColumnLabelConstants.TRANS_REMARKS));
            accountStatement.setUserBalance(rs.getString(ColumnLabelConstants.USER_BALANCE));
            accountStatementList.add(accountStatement);
        }
        return accountStatementList;
    }

    private UserActivityWrapperTO populateActivityLogs(ResultSet rs) throws SQLException {
        UserActivityWrapperTO userActivityWrapperTO = new UserActivityWrapperTO();
        List<UserActivityDetailsTO> activityDetailsTOS = new ArrayList<>();
        while (rs.next()) {
            UserActivityDetailsTO activityDetailsTO = new UserActivityDetailsTO();
            activityDetailsTO.setLoginDateTime(rs.getString(ColumnLabelConstants.LOGIN_DATE));
            activityDetailsTO.setLoginStatus(rs.getString(ColumnLabelConstants.LOGIN_STATUS));
            activityDetailsTO.setIpAddress(rs.getString(ColumnLabelConstants.LOGIN_IP));
            activityDetailsTO.setIsp(rs.getString(ColumnLabelConstants.LOGIN_HOST_NAME));
            activityDetailsTO.setCity(rs.getString(ColumnLabelConstants.LOGIN_HOST_ADDRESS));
            activityDetailsTOS.add(activityDetailsTO);
        }
        userActivityWrapperTO.setActivityLogList(activityDetailsTOS);
        return userActivityWrapperTO;
    }

    @Override
    public String getUserPassword(String loginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_PASSOWRD,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            cs.setString(1, loginToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String password = null;
            while (rs.next()) {
                password = rs.getString(ColumnLabelConstants.USER_PASSWORD);
            }
            return password;
        });
    }

    @Override
    public void updatePassword(String password, Integer userId) {
        jdbcTemplate.update(PROC_UPDATE_PASSWORD, password, userId);
    }

    @Override
    public void updateExposure(UpdateExposureTO exposureAmount) {
        jdbcTemplate.update(PROC_UPDATE_EXPOSURE, exposureAmount.getExposure(), exposureAmount.getUserId());
    }

    @Override
    public void updateCommission(UpdateExposureTO commissionAmount) {
        jdbcTemplate.update(PROC_UPDATE_COMMISSION, commissionAmount.getCommission(), commissionAmount.getUserId());
    }
}
