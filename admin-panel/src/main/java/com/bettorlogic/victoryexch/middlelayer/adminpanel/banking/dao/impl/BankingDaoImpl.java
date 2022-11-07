package com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dao.BankingDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.banking.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BankingDaoImpl implements BankingDao {

    private static final String PROC_GET_BANKING_DETAILS = "select * from get_banking_details(?)";
    private static final String GET_PROFILE_NAME = "select up.profilename as user_name from bmeusers bu join userprofile up on up.profileid=bu.profileid where bu.id = ?";
    private static final String PROC_GET_BANKING_LOGS = "select * from get_banking_logs(?)";
    private static final String PROC_UPDATE_BANKING_DETAILS = "select * from update_banking_transactions(?,?)";
    private static final String GET_ROLE_ID = "select roleid from bmeusers where user_login_token=?";
    private static final String VALIDATE_USER_ID = "select * from validate_user_id(?)";
    private static final String VALIDATE_USER_TOKEN = "select * from validate_user_token(?,?)";
    private static final String PROC_GET_ADMIN_BANKING_DETAILS = "select * from get_admin_banking_details(?)";
    private static final String PROC_GET_ADMIN_DEPOSITS = "select * from get_admin_deposit_points(?,?,?)";
    private static final String ADD_DEPOSIT = "select * from save_admin_deposit_points(?,?,?,?,?)";
    private static final String UPDATE_EXPOSURE_LIMIT = "UPDATE usersettings SET exposurelimit=? WHERE bmeuserid=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BankingDetailsOutput> getBankingDetails(String userLoginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_BANKING_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userLoginToken);
            return cs;
        }, (CallableStatement cs) -> this.extractBankingDetails(cs.executeQuery()));
    }

    @Override
    public List<BankingDetailsOutput> getAdminBankingDetails(String userLoginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_ADMIN_BANKING_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userLoginToken);
            return cs;
        }, (CallableStatement cs) -> this.extractAdminBankingDetails(cs.executeQuery()));
    }

    @Override
    public List<AdminDepositDetailsTO> getAdminDeposits(AdminDepositInputTO adminDepositInputTO) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_ADMIN_DEPOSITS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, adminDepositInputTO.getUserLoginToken());
            cs.setString(2, adminDepositInputTO.getFromDate());
            cs.setString(3, adminDepositInputTO.getToDate());
            return cs;
        }, (CallableStatement cs) -> this.extractAdminDeposits(cs.executeQuery()));
    }

    @Override
    public String addAdminDeposit(AdminDepositPointsTO adminDepositPointsTO) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(ADD_DEPOSIT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, adminDepositPointsTO.getUserLoginToken());
            cs.setBigDecimal(2, adminDepositPointsTO.getAmount());
            cs.setBigDecimal(3, adminDepositPointsTO.getConversionRate());
            cs.setBigDecimal(4, adminDepositPointsTO.getTransPoints());
            cs.setString(5, adminDepositPointsTO.getReference());
            return cs;
        }, (CallableStatement cs) -> this.extractDepositDetails(cs.executeQuery()));
    }

    private String extractDepositDetails(ResultSet executeQuery) throws SQLException {
        String res = null;
        while (executeQuery.next()) {
            res = executeQuery.getString(ColumnLabelConstants.SAVE_ADMIN_DEPOSIT_POINTS);
        }
        return res;
    }

    private List<AdminDepositDetailsTO> extractAdminDeposits(ResultSet executeQuery) throws SQLException {
        List<AdminDepositDetailsTO> depositList = new ArrayList<>();
        while (executeQuery.next()) {
            AdminDepositDetailsTO adminDepositDetails = new AdminDepositDetailsTO();
            adminDepositDetails.setCreatedDate(executeQuery.getString(ColumnLabelConstants.CREATED_DATE));
            adminDepositDetails.setDepositAmount(executeQuery.getDouble(ColumnLabelConstants.DEPOSIT_AMOUNT));
            adminDepositDetails.setConversionRate(executeQuery.getDouble(ColumnLabelConstants.CONVERSION_RATES));
            adminDepositDetails.setAmountPoints(executeQuery.getDouble(ColumnLabelConstants.AMOUNT_POINTS));
            adminDepositDetails.setBalancePoints(executeQuery.getDouble(ColumnLabelConstants.BALANCE_POINTS));
            adminDepositDetails.setReference(executeQuery.getString(ColumnLabelConstants.REFERENCE));
            depositList.add(adminDepositDetails);
        }
        return depositList;
    }

    private List<BankingDetailsOutput> extractAdminBankingDetails(ResultSet rs) throws SQLException {
        List<BankingDetailsOutput> bankingList = new ArrayList<>();
        BankingDetailsOutput banking;
        while (rs.next()) {
            banking = new BankingDetailsOutput();
            banking.setUserId(rs.getInt(AdminPanelColumnLabelConstants.BANKING_UID));
            banking.setName(rs.getString(AdminPanelColumnLabelConstants.BANKING_NAME));
            banking.setCurrency(rs.getString(ColumnLabelConstants.CURRENCY_NAME));
            banking.setAdminBalanceConversion(rs.getBigDecimal(ColumnLabelConstants.ADMIN_CONVERSION).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setBalance(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_BALANCE).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setAvailableDW(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_AVAILABLE_BALANCE).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setExposure(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_USER_EXPOSURE).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setExposureLimit(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_EXPOSURE_LIMIT).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setCreditLimit(BigDecimal.valueOf(10000));
            bankingList.add(banking);
        }
        return bankingList;
    }

    private List<BankingDetailsOutput> extractBankingDetails(ResultSet rs) throws SQLException {
        List<BankingDetailsOutput> bankingList = new ArrayList<>();
        BankingDetailsOutput banking;
        while (rs.next()) {
            banking = new BankingDetailsOutput();
            banking.setUserId(rs.getInt(AdminPanelColumnLabelConstants.BANKING_UID));
            banking.setName(rs.getString(AdminPanelColumnLabelConstants.BANKING_NAME));
            banking.setBalance(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_BALANCE).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setAvailableDW(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_AVAILABLE_BALANCE).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setExposure(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_USER_EXPOSURE).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setExposureLimit(new BigDecimal(rs.getDouble(AdminPanelColumnLabelConstants.EXPOSURE_LIMIT)).setScale(2, BigDecimal.ROUND_FLOOR));
            banking.setDeposited(rs.getBoolean(AdminPanelColumnLabelConstants.IS_DEPOSITED));
            bankingList.add(banking);
        }
        return bankingList;
    }

    @Override
    public List<BankingDetailsTO> getBankingLogs(Integer userId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_BANKING_LOGS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, userId);
            return cs;
        }, (CallableStatement cs) -> this.extractBankingLogs(cs.executeQuery()));
    }

    private List<BankingDetailsTO> extractBankingLogs(ResultSet rs) throws SQLException {
        List<BankingDetailsTO> bankingList = new ArrayList<>();
        BankingDetailsTO banking;
        while (rs.next()) {
            banking = new BankingDetailsTO();
            Date logCreatedDate = new Date();
            logCreatedDate = rs.getTimestamp(AdminPanelColumnLabelConstants.BANKING_CREATED_DATE);
            banking.setCreatedDate(String.valueOf(logCreatedDate));
            banking.setDeposit(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_DEPOSIT) != null ? rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_DEPOSIT).setScale(2, BigDecimal.ROUND_HALF_UP) : null);
            banking.setWithdraw(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_WITHDRAW) != null ? rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_WITHDRAW).setScale(2, BigDecimal.ROUND_HALF_UP) : null);
            banking.setBalance(rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_BALANCE) != null ? rs.getBigDecimal(AdminPanelColumnLabelConstants.BANKING_BALANCE).setScale(2, BigDecimal.ROUND_HALF_UP) : null);
            banking.setRemark(rs.getString(AdminPanelColumnLabelConstants.BANKING_REMARK));
            banking.setFrom(rs.getString(AdminPanelColumnLabelConstants.BANKING_LOG_FROM));
            banking.setTo(rs.getString(AdminPanelColumnLabelConstants.BANKING_LOG_TO));
            bankingList.add(banking);
        }
        return bankingList;
    }

    @Override
    public String getUserName(Integer userId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_PROFILE_NAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, userId);
            return cs;
        }, (CallableStatement cs) -> this.getUser(cs.executeQuery()));
    }

    private String getUser(ResultSet rs) throws SQLException {
        String userName = null;
        while (rs.next()) {
            userName = rs.getString(ColumnLabelConstants.USER_NAME);
        }
        return userName;
    }

    @Override
    public BankingTansactionsOutputTO updateBankingDetails(String userLoginToken, String bulkDataInsert) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_UPDATE_BANKING_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userLoginToken);
            cs.setString(2, bulkDataInsert);
            return cs;
        }, (CallableStatement cs) -> this.extractBankingMessage(cs.executeQuery()));
    }

    @Override
    public Boolean updateExposureLimit(BankingUserExposureInputTO bankingUserExposureInputTO) {
        if (bankingUserExposureInputTO.getExposureLimit() != null) {
            jdbcTemplate.update(UPDATE_EXPOSURE_LIMIT, bankingUserExposureInputTO.getExposureLimit(), bankingUserExposureInputTO.getUserId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer getRoleId(String userLoginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_ROLE_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userLoginToken);
            return cs;
        }, (CallableStatement cs) -> this.extractRoleId(cs.executeQuery()));
    }

    private Integer extractRoleId(ResultSet rs) throws SQLException {
        Integer roleId = 0;
        while (rs.next()) {
            roleId = rs.getInt(ColumnLabelConstants.ROLEID);
        }
        return roleId;
    }

    @Override
    public Boolean getValidUserId(Integer userId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(VALIDATE_USER_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, userId);
            return cs;
        }, (CallableStatement cs) -> this.extractValidUser(cs.executeQuery()));
    }

    @Override
    public Boolean getValidateUserToken(String userLoginToken, Integer UserId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(VALIDATE_USER_TOKEN,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userLoginToken);
            cs.setInt(2, UserId);
            return cs;
        }, (CallableStatement cs) -> this.extractValidUser(cs.executeQuery()));
    }

    private Boolean extractValidUser(ResultSet rs) throws SQLException {
        Boolean userId = false;
        while (rs.next()) {
            userId = rs.getBoolean(ColumnLabelConstants.USER_DB_ID);
        }
        return userId;
    }

    private BankingTansactionsOutputTO extractBankingMessage(ResultSet rs) throws SQLException {
        BankingTansactionsOutputTO bankingTansactionsDetails=new BankingTansactionsOutputTO();
        List<BankingUsersOutput> successUserTrasactionList=new ArrayList<>();
        List<BankingUsersOutput> failureUserTrasactionList=new ArrayList<>();

        while (rs.next()) {
            BankingUsersOutput successUserTrasaction=new BankingUsersOutput();
            BankingUsersOutput failureUserTrasaction=new BankingUsersOutput();
            Boolean isSuccess=rs.getBoolean((AdminPanelColumnLabelConstants.IS_SUCCESS));
            if(isSuccess){
                successUserTrasaction.setUserid(rs.getInt(AdminPanelColumnLabelConstants.USER_ID));
                successUserTrasaction.setUserName(rs.getString(AdminPanelColumnLabelConstants.USER_NAME));
                successUserTrasaction.setMessage(rs.getString(AdminPanelColumnLabelConstants.STATUS));
                successUserTrasactionList.add(successUserTrasaction);
            }else{

                failureUserTrasaction.setUserid(rs.getInt(AdminPanelColumnLabelConstants.USER_ID));
                failureUserTrasaction.setUserName(rs.getString(AdminPanelColumnLabelConstants.USER_NAME));
                failureUserTrasaction.setMessage(rs.getString(AdminPanelColumnLabelConstants.STATUS));
                failureUserTrasactionList.add(failureUserTrasaction);
            }
        }
        bankingTansactionsDetails.setFailedUser(failureUserTrasactionList);
        bankingTansactionsDetails.setSuccessUser(successUserTrasactionList);
        return bankingTansactionsDetails;
    }
}
