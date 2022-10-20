package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyImmediateUserDetailsTO {
    private Integer id;
    private String name;
    private Integer immediateUserRoleid;
    @NotNull
    private BigDecimal totStake;
    @NotNull
    private BigDecimal totalPlyrprofitlossSport;
    @NotNull
    private BigDecimal totalDwnprofitlosssport;
    @NotNull
    private BigDecimal totalCommisonSport;
    @NotNull
    private BigDecimal totalUpProfitlosssport;

    private List<SportDetailsTO> sportList = new ArrayList<>();

    public BigDecimal getTotStake() {
        return totStake;
    }

    public void setTotStake(BigDecimal totStake) {
        this.totStake = totStake;
    }

    public BigDecimal getTotalPlyrprofitlossSport() {
        return totalPlyrprofitlossSport;
    }

    public void setTotalPlyrprofitlossSport(BigDecimal totalPlyrprofitlossSport) {
        this.totalPlyrprofitlossSport = totalPlyrprofitlossSport;
    }

    public BigDecimal getTotalDwnprofitlosssport() {
        return totalDwnprofitlosssport;
    }

    public void setTotalDwnprofitlosssport(BigDecimal totalDwnprofitlosssport) {
        this.totalDwnprofitlosssport = totalDwnprofitlosssport;
    }

    public BigDecimal getTotalCommisonSport() {
        return totalCommisonSport;
    }

    public void setTotalCommisonSport(BigDecimal totalCommisonSport) {
        this.totalCommisonSport = totalCommisonSport;
    }

    public BigDecimal getTotalUpProfitlosssport() {
        return totalUpProfitlosssport;
    }

    public void setTotalUpProfitlosssport(BigDecimal totalUpProfitlosssport) {
        this.totalUpProfitlosssport = totalUpProfitlosssport;
    }

    public Integer getImmediateUserRoleid() {
        return immediateUserRoleid;
    }

    public void setImmediateUserRoleid(Integer immediateUserRoleid) {
        this.immediateUserRoleid = immediateUserRoleid;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<SportDetailsTO> getSportList() {
        return sportList;
    }

    public void setSportList(List<SportDetailsTO> sportList) {
        this.sportList = sportList;
    }

    @Override
    public String toString() {
        return "MyImmediateUserDetailsTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", immediateUserRoleid=" + immediateUserRoleid +
                ", totStake=" + totStake +
                ", totalPlyrprofitlossSport=" + totalPlyrprofitlossSport +
                ", totalDwnprofitlosssport=" + totalDwnprofitlosssport +
                ", totalCommisonSport=" + totalCommisonSport +
                ", totalUpProfitlosssport=" + totalUpProfitlosssport +
                ", sportList=" + sportList +
                '}';
    }


}