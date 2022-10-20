package com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dao.UserIdTokenValidationDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentValidations;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.UserIdTokenValidationsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserIdTokenValidationDaoImpl implements UserIdTokenValidationDao {

    private static final String PROC_VALIDATE_USER = "select * from validate_user(?,?)";
    private static final String PROC_VALIDATE_AGENT = "select * from validate_admin_login(?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserIdTokenValidationsTO validateUser(String userToken, Integer userId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_VALIDATE_USER,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setInt(2, userId != null ? userId : 0);
            return cs;
        }, (CallableStatement cs) -> this.populateValidatePlayerTokenDetails(cs.executeQuery()));
    }

    private UserIdTokenValidationsTO populateValidatePlayerTokenDetails(ResultSet rs) throws SQLException {
        UserIdTokenValidationsTO userIdTokenValidations = new UserIdTokenValidationsTO();
        while (rs.next()) {
            userIdTokenValidations.setValidPlayerId(rs.getBoolean(ColumnLabelConstants.IS_VALID_PLAYER_ID));
            userIdTokenValidations.setValidManagerId(rs.getBoolean(ColumnLabelConstants.IS_VALID_MANAGER_ID));
            userIdTokenValidations.setValidManagerToken(rs.getBoolean(ColumnLabelConstants.IS_VALID_MANAGER_TOKEN));
            userIdTokenValidations.setValidPlayerToken(rs.getBoolean(ColumnLabelConstants.IS_VALID_PLAYER_TOKEN));
        }
        return userIdTokenValidations;
    }

    @Override
    public AgentValidations validateAgent(String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_VALIDATE_AGENT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> this.populateAgent(cs.executeQuery()));
    }

    private AgentValidations populateAgent(ResultSet rs) throws SQLException {
        AgentValidations agentValidations =  new AgentValidations();
        while (rs.next()){
            agentValidations.setUserId(rs.getInt(ColumnLabelConstants.ID));
            agentValidations.setUserName(rs.getString(ColumnLabelConstants.BM_USER_ID));
            agentValidations.setMarketName(rs.getString(ColumnLabelConstants.MARKETNAME));
        }
        return agentValidations;
    }
}