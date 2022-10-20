package com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CardsData {


    private List<String> playerAList = new ArrayList<>();
    private List<String> playerBList = new ArrayList<>();
    private BigDecimal playerAbackOdds;
    private BigDecimal playerALayOdds;
    private BigDecimal playerBBackOdds;
    private BigDecimal playerBLayOdds;
    private BigInteger id;

    public List<String> getPlayerAList() {
        return playerAList;
    }

    public void setPlayerAList(List<String> playerAList) {
        this.playerAList = playerAList;
    }

    public List<String> getPlayerBList() {
        return playerBList;
    }

    public void setPlayerBList(List<String> playerBList) {
        this.playerBList = playerBList;
    }

    public BigDecimal getPlayerAbackOdds() {
        return playerAbackOdds;
    }

    public void setPlayerAbackOdds(BigDecimal playerAbackOdds) {
        this.playerAbackOdds = playerAbackOdds;
    }

    public BigDecimal getPlayerALayOdds() {
        return playerALayOdds;
    }

    public void setPlayerALayOdds(BigDecimal playerALayOdds) {
        this.playerALayOdds = playerALayOdds;
    }

    public BigDecimal getPlayerBBackOdds() {
        return playerBBackOdds;
    }

    public void setPlayerBBackOdds(BigDecimal playerBBackOdds) {
        this.playerBBackOdds = playerBBackOdds;
    }

    public BigDecimal getPlayerBLayOdds() {
        return playerBLayOdds;
    }

    public void setPlayerBLayOdds(BigDecimal playerBLayOdds) {
        this.playerBLayOdds = playerBLayOdds;
    }

    public BigInteger getId() { return id; }

    public void setId(BigInteger id) { this.id = id; }
}
