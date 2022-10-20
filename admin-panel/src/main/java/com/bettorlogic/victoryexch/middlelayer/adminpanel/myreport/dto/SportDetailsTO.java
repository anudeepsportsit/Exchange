package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SportDetailsTO {

    private Integer id;
    private String name;
    @NotNull
    private BigDecimal stake;
    @NotNull
    private BigDecimal plyrPLSprt;
    @NotNull
    private BigDecimal dwnPLsprt;
    @NotNull
    private BigDecimal commSprt;
    @NotNull
    private BigDecimal upPLSprt;

    public BigDecimal getStake() {
        return stake;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public BigDecimal getPlyrPLSprt() {
        return plyrPLSprt;
    }

    public void setPlyrPLSprt(BigDecimal plyrPLSprt) {
        this.plyrPLSprt = plyrPLSprt;
    }

    public BigDecimal getDwnPLsprt() {
        return dwnPLsprt;
    }

    public void setDwnPLsprt(BigDecimal dwnPLsprt) {
        this.dwnPLsprt = dwnPLsprt;
    }

    public BigDecimal getCommSprt() {
        return commSprt;
    }

    public void setCommSprt(BigDecimal commSprt) {
        this.commSprt = commSprt;
    }

    public BigDecimal getUpPLSprt() {
        return upPLSprt;
    }

    public void setUpPLSprt(BigDecimal upPLSprt) {
        this.upPLSprt = upPLSprt;
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


    @Override
    public String toString() {
        return "Sport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}