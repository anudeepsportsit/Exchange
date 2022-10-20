package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dao.MyReportDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.*;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class MyReportDaoImpl implements MyReportDao {

    private static final String PROC_GET_MY_REPORT_MARKET = "select * from get_myreport_downlinelist_sport_market(?,?,?,?)";
    private static final String PROC_GET_MY_REPORT_DOWN_LIST = "select * from get_myreport_downlinelist_sport(?,?,?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public MyReportDetailsTO extractMyReportDownLineList(MyReportDwnLineLoginDetailsTO loginDetails) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_MY_REPORT_DOWN_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, loginDetails.getUserToken());
            cs.setString(2, loginDetails.getFromDate());
            cs.setString(3, loginDetails.getToDate());
            cs.setInt(4, loginDetails.getUserId() != null ? loginDetails.getUserId() : 0);
            return cs;
        }, (CallableStatement cs) -> extractMyReportDetailsDownList(cs.executeQuery()));

    }

    @Override
    public MyReportMarketDetailsTO extractMyReportMarket(MyReportMarketLoginDetailsTO loginDetails) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_MY_REPORT_MARKET,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setString(1, loginDetails.getUserToken());
                    cs.setString(2, loginDetails.getFromDate());
                    cs.setString(3, loginDetails.getToDate());
                    cs.setInt(4, loginDetails.getSportId());
                    return cs;
                },
                (CallableStatement cs) -> extractMyReportMarketList(cs.executeQuery()));
    }

    private MyReportDetailsTO extractMyReportDetailsDownList(ResultSet rs) throws SQLException {
        MyReportDetailsTO reportDetails = new MyReportDetailsTO();
        int lastSubUserId = 0;
        MyImmediateUserDetailsTO myImmediateUserDetailsTo = null;
        SportDetailsTO sport;
        while (rs.next()) {
            reportDetails.setLoginUsrId(rs.getInt(ColumnLabelConstants.LOGINUSRID));
            reportDetails.setLoginEmail(rs.getString(ColumnLabelConstants.LOGIN_USR_EMAIL));
            reportDetails.setRoleId(rs.getInt(ColumnLabelConstants.LOGIN_USR_ROLE_ID));
            if (lastSubUserId == rs.getInt(ColumnLabelConstants.IMMEDIATE_USER_ID)) {
                assert myImmediateUserDetailsTo != null;
                myImmediateUserDetailsTo.setId(rs.getInt(ColumnLabelConstants.USERID));
                myImmediateUserDetailsTo.setName(rs.getString(ColumnLabelConstants.IMMEDIATE_USER_EMAIL));
                myImmediateUserDetailsTo.setImmediateUserRoleid(rs.getInt(ColumnLabelConstants.IMMEDIATE_USER_ROLE_ID));
                sport = new SportDetailsTO();
                sport.setId(rs.getInt(ColumnLabelConstants.SPORTID));
                sport.setName(rs.getString(ColumnLabelConstants.SPORTNAME));
                sport.setStake(rs.getBigDecimal(ColumnLabelConstants.SPORT_STAKE));
                sport.setPlyrPLSprt(rs.getBigDecimal(ColumnLabelConstants.PLAYERPL));
                sport.setDwnPLsprt(rs.getBigDecimal(ColumnLabelConstants.DWNLINEPL));
                sport.setCommSprt(rs.getBigDecimal(ColumnLabelConstants.COMMISSION));
                sport.setUpPLSprt(rs.getBigDecimal(ColumnLabelConstants.UPLINEPL));
                myImmediateUserDetailsTo.getSportList().add(sport);
            } else {
                lastSubUserId = rs.getInt(ColumnLabelConstants.IMMEDIATE_USER_ID);
                rs.previous();
                myImmediateUserDetailsTo = new MyImmediateUserDetailsTO();
                reportDetails.getMyImmediateUserDetailsList().add(myImmediateUserDetailsTo);
            }
        }
        calculateMyReportDetailsDownList(reportDetails);
        return reportDetails;
    }

    private void calculateMyReportDetailsDownList(MyReportDetailsTO myReportDetails) {
        BigDecimal totalStakeLU = new BigDecimal(0.0);
        BigDecimal totalPlyrPlSportLU = new BigDecimal(0.0);
        BigDecimal totalDwnPlSportLU = new BigDecimal(0.0);
        BigDecimal totalCommSportLU = new BigDecimal(0.0);
        BigDecimal totalUpplSportLU = new BigDecimal(0.0);
        for (MyImmediateUserDetailsTO myImmediateUserDetailsTo : myReportDetails.getMyImmediateUserDetailsList()) {
            BigDecimal totalStake = new BigDecimal(0.0);
            BigDecimal totalPlyrPlSport = new BigDecimal(0.0);
            BigDecimal totalDwnPlSport = new BigDecimal(0.0);
            BigDecimal totalCommSport = new BigDecimal(0.0);
            BigDecimal totalUpPlSport = new BigDecimal(0.0);
            for (SportDetailsTO sport : myImmediateUserDetailsTo.getSportList()) {
                totalStake = totalStake.add(sport.getStake());
                totalPlyrPlSport = totalPlyrPlSport.add(sport.getPlyrPLSprt());
                totalDwnPlSport = totalDwnPlSport.add(sport.getDwnPLsprt());
                totalCommSport = totalCommSport.add(sport.getCommSprt());
                totalUpPlSport = totalUpPlSport.add(sport.getUpPLSprt());
            }
            myImmediateUserDetailsTo.setTotStake(totalStake.setScale(2, RoundingMode.CEILING));
            myImmediateUserDetailsTo.setTotalPlyrprofitlossSport(totalPlyrPlSport.setScale(2, RoundingMode.CEILING));
            myImmediateUserDetailsTo.setTotalDwnprofitlosssport(totalDwnPlSport.setScale(2, RoundingMode.CEILING));
            myImmediateUserDetailsTo.setTotalCommisonSport(totalCommSport.setScale(2, RoundingMode.CEILING));
            myImmediateUserDetailsTo.setTotalUpProfitlosssport(totalUpPlSport.setScale(2, RoundingMode.CEILING));
            totalStakeLU = totalStakeLU.add(myImmediateUserDetailsTo.getTotStake());
            totalPlyrPlSportLU = totalPlyrPlSportLU.add(myImmediateUserDetailsTo.getTotalPlyrprofitlossSport());
            totalDwnPlSportLU = totalDwnPlSportLU.add(myImmediateUserDetailsTo.getTotalDwnprofitlosssport());
            totalCommSportLU = totalCommSportLU.add(myImmediateUserDetailsTo.getTotalCommisonSport());
            totalUpplSportLU = totalUpplSportLU.add(myImmediateUserDetailsTo.getTotalUpProfitlosssport());
        }
        myReportDetails.setTotalStakeImmediate(totalStakeLU.setScale(2, RoundingMode.CEILING));
        myReportDetails.setTotalPlyrProfitLossImmediate(totalPlyrPlSportLU.setScale(2, RoundingMode.CEILING));
        myReportDetails.setTotalDwnLineProfitLossImmediate(totalDwnPlSportLU.setScale(2, RoundingMode.CEILING));
        myReportDetails.setTotalCommisionImmediate(totalCommSportLU.setScale(2, RoundingMode.CEILING));
        myReportDetails.setTotalUpLineProfitLossImmediate(totalUpplSportLU.setScale(2, RoundingMode.CEILING));
    }

    private MyReportMarketDetailsTO extractMyReportMarketList(ResultSet rs) throws SQLException {
        int lastEventID = 0;
        MyReportMarketDetailsTO myReportMarketDetailsTO = new MyReportMarketDetailsTO();
        MyReportEventDetailsTO myReportEventDetailsTO = null;
        while (rs.next()) {
            myReportMarketDetailsTO.setUserId(rs.getInt(ColumnLabelConstants.LOGINUSRID));
            if (lastEventID == rs.getInt(ColumnLabelConstants.EVENT_ID)) {
                assert myReportEventDetailsTO != null;
                myReportEventDetailsTO.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
                myReportEventDetailsTO.setSportName(rs.getString(ColumnLabelConstants.SPORTNAME));
                MyReportMarketTO marketDetails = new MyReportMarketTO();
                marketDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
                marketDetails.setStake(rs.getBigDecimal(ColumnLabelConstants.STAKES));
                marketDetails.setDownlineProfitLoss(rs.getBigDecimal(ColumnLabelConstants.DWNLINEPL));
                marketDetails.setPlayerProfitLoss(rs.getBigDecimal(ColumnLabelConstants.PLAYERPL));
                marketDetails.setUplineProfitLoss(rs.getBigDecimal(ColumnLabelConstants.UPLINEPL));
                marketDetails.setCommision(rs.getBigDecimal(ColumnLabelConstants.COMMISSION));
                myReportEventDetailsTO.getMarketDetails().add(marketDetails);
            } else {
                lastEventID = rs.getInt(ColumnLabelConstants.EVENT_ID);
                rs.previous();
                myReportEventDetailsTO = new MyReportEventDetailsTO();
                myReportMarketDetailsTO.getMyReportEventDetails().add(myReportEventDetailsTO);
            }
        }
        calculateMyReportSport(myReportMarketDetailsTO);
        return myReportMarketDetailsTO;
    }

    private void calculateMyReportSport(MyReportMarketDetailsTO myReportMarketDetailsTO) {
        BigDecimal totalEventStake = new BigDecimal(0.0);
        BigDecimal totalEventPlayerPl = new BigDecimal(0.0);
        BigDecimal totalEventDwnLinePl = new BigDecimal(0.0);
        BigDecimal totalEventComm = new BigDecimal(0.0);
        BigDecimal totalEventUpPl = new BigDecimal(0.0);
        for (MyReportEventDetailsTO myReportEventDetailsTO : myReportMarketDetailsTO.getMyReportEventDetails()) {
            BigDecimal totalMarketStake = new BigDecimal(0.0);
            BigDecimal totalMarketPlayerPl = new BigDecimal(0.0);
            BigDecimal totalMarketDwnLinePl = new BigDecimal(0.0);
            BigDecimal totalMarketComm = new BigDecimal(0.0);
            BigDecimal totalMarketUpPl = new BigDecimal(0.0);
            for (MyReportMarketTO myReportMarketTO : myReportEventDetailsTO.getMarketDetails()) {
                totalMarketStake = totalMarketStake.add(myReportMarketTO.getStake());
                totalMarketPlayerPl = totalMarketPlayerPl.add(myReportMarketTO.getPlayerProfitLoss());
                totalMarketDwnLinePl = totalMarketDwnLinePl.add(myReportMarketTO.getDownlineProfitLoss());
                totalMarketComm = totalMarketComm.add(myReportMarketTO.getCommision());
                totalMarketUpPl = totalMarketUpPl.add(myReportMarketTO.getUplineProfitLoss());
            }
            myReportEventDetailsTO.setTotalStake(totalMarketStake.setScale(2, RoundingMode.CEILING));
            myReportEventDetailsTO.setTotalPlyrProfitlossSport(totalMarketPlayerPl.setScale(2, RoundingMode.CEILING));
            myReportEventDetailsTO.setTotalDwnProfitlossSport(totalMarketDwnLinePl.setScale(2, RoundingMode.CEILING));
            myReportEventDetailsTO.setTotalCommisonSport(totalMarketComm.setScale(2, RoundingMode.CEILING));
            myReportEventDetailsTO.setTotalUpProfitlosssport(totalMarketUpPl.setScale(2, RoundingMode.CEILING));
            totalEventStake = totalEventStake.add(myReportEventDetailsTO.getTotalStake());
            totalEventPlayerPl = totalEventPlayerPl.add(myReportEventDetailsTO.getTotalPlyrProfitlossSport());
            totalEventDwnLinePl = totalEventDwnLinePl.add(myReportEventDetailsTO.getTotalDwnProfitlossSport());
            totalEventComm = totalEventComm.add(myReportEventDetailsTO.getTotalCommisonSport());
            totalEventUpPl = totalEventUpPl.add(myReportEventDetailsTO.getTotalUpProfitlosssport());
        }

        myReportMarketDetailsTO.setTotalMarketStake(totalEventStake.setScale(2, RoundingMode.CEILING));
        myReportMarketDetailsTO.setTotalMarketPlyrprofitlossSport(totalEventPlayerPl.setScale(2, RoundingMode.CEILING));
        myReportMarketDetailsTO.setTotalMarketDwnprofitlosssport(totalEventDwnLinePl.setScale(2, RoundingMode.CEILING));
        myReportMarketDetailsTO.setTotalMarketCommisonSport(totalEventComm.setScale(2, RoundingMode.CEILING));
        myReportMarketDetailsTO.setTotalMarketUpProfitlosssport(totalEventUpPl.setScale(2, RoundingMode.CEILING));
    }
}
