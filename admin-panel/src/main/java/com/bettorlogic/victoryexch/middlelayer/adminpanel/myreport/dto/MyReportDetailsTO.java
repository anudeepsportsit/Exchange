package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyReportDetailsTO {

    private Integer loginUsrId;
    private Integer roleId;
    private String loginEmail;
    private BigDecimal totalStakeImmediate;
    private BigDecimal totalPlyrProfitLossImmediate;
    private BigDecimal totalDwnLineProfitLossImmediate;
    private BigDecimal totalCommisionImmediate;
    private BigDecimal totalUpLineProfitLossImmediate;
    private List<MyImmediateUserDetailsTO> myImmediateUserDetailsList = new ArrayList<>();

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public BigDecimal getTotalStakeImmediate() {
        return totalStakeImmediate;
    }

    public void setTotalStakeImmediate(BigDecimal totalStakeImmediate) {
        this.totalStakeImmediate = totalStakeImmediate;
    }

    public BigDecimal getTotalPlyrProfitLossImmediate() {
        return totalPlyrProfitLossImmediate;
    }

    public void setTotalPlyrProfitLossImmediate(BigDecimal totalPlyrProfitLossImmediate) {
        this.totalPlyrProfitLossImmediate = totalPlyrProfitLossImmediate;
    }

    public BigDecimal getTotalDwnLineProfitLossImmediate() {
        return totalDwnLineProfitLossImmediate;
    }

    public void setTotalDwnLineProfitLossImmediate(BigDecimal totalDwnLineProfitLossImmediate) {
        this.totalDwnLineProfitLossImmediate = totalDwnLineProfitLossImmediate;
    }

    public BigDecimal getTotalCommisionImmediate() {
        return totalCommisionImmediate;
    }

    public void setTotalCommisionImmediate(BigDecimal totalCommisionImmediate) {
        this.totalCommisionImmediate = totalCommisionImmediate;
    }

    public BigDecimal getTotalUpLineProfitLossImmediate() {
        return totalUpLineProfitLossImmediate;
    }

    public void setTotalUpLineProfitLossImmediate(BigDecimal totalUpLineProfitLossImmediate) {
        this.totalUpLineProfitLossImmediate = totalUpLineProfitLossImmediate;
    }

    public Integer getLoginUsrId() {
        return loginUsrId;
    }

    public void setLoginUsrId(Integer loginUsrId) {
        this.loginUsrId = loginUsrId;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public List<MyImmediateUserDetailsTO> getMyImmediateUserDetailsList() {
        return myImmediateUserDetailsList;
    }

    public void setMyImmediateUserDetailsList(List<MyImmediateUserDetailsTO> myImmediateUserDetailsList) {
        myImmediateUserDetailsList = this.myImmediateUserDetailsList;
    }
}