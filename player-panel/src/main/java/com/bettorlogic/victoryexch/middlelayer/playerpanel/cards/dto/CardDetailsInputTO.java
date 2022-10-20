package com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CardDetailsInputTO {
    private Integer id;
    private Integer playerAFirstCard;
    private Integer playerASecondCard;
    private Integer playerAThirdCard;
    private Integer playerBFirstCard;
    private Integer playerBSecondCard;
    private Integer playerBThirdCard;
    private Integer theFirstFlopCard;
    private Integer theSecondFlopCard;
    private Integer theThirdFlopCard;
    private Integer theTurnCard;
    private Integer theRiverCard;
    private BigDecimal playerAbackOdds;
    private BigDecimal playerAlayOdds;
    private BigDecimal playerBbackOdds;
    private BigDecimal playerBlayOdds;
    private String winner;

    public Integer getPlayerAFirstCard() {
        return playerAFirstCard;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPlayerAFirstCard(Integer playerAFirstCard) {
        this.playerAFirstCard = playerAFirstCard;
    }

    public Integer getPlayerASecondCard() {
        return playerASecondCard;
    }

    public void setPlayerASecondCard(Integer playerASecondCard) {
        this.playerASecondCard = playerASecondCard;
    }

    public Integer getPlayerAThirdCard() {
        return playerAThirdCard;
    }

    public void setPlayerAThirdCard(Integer playerAThirdCard) {
        this.playerAThirdCard = playerAThirdCard;
    }

    public Integer getPlayerBFirstCard() {
        return playerBFirstCard;
    }

    public void setPlayerBFirstCard(Integer playerBFirstCard) {
        this.playerBFirstCard = playerBFirstCard;
    }

    public Integer getPlayerBSecondCard() {
        return playerBSecondCard;
    }

    public void setPlayerBSecondCard(Integer playerBSecondCard) {
        this.playerBSecondCard = playerBSecondCard;
    }

    public Integer getPlayerBThirdCard() {
        return playerBThirdCard;
    }

    public void setPlayerBThirdCard(Integer playerBThirdCard) {
        this.playerBThirdCard = playerBThirdCard;
    }

    public Integer getTheFirstFlopCard() {
        return theFirstFlopCard;
    }

    public void setTheFirstFlopCard(Integer theFirstFlopCard) {
        this.theFirstFlopCard = theFirstFlopCard;
    }

    public Integer getTheSecondFlopCard() { return theSecondFlopCard; }

    public void setTheSecondFlopCard(Integer theSecondFlopCard) { this.theSecondFlopCard = theSecondFlopCard; }

    public Integer getTheThirdFlopCard() { return theThirdFlopCard; }

    public void setTheThirdFlopCard(Integer theThirdFlopCard) { this.theThirdFlopCard = theThirdFlopCard; }

    public Integer getTheTurnCard() { return theTurnCard; }

    public void setTheTurnCard(Integer theTurnCard) { this.theTurnCard = theTurnCard; }

    public Integer getTheRiverCard() { return theRiverCard; }

    public void setTheRiverCard(Integer theRiverCard) { this.theRiverCard = theRiverCard; }

    public BigDecimal getPlayerAbackOdds() { return playerAbackOdds; }

    public void setPlayerAbackOdds(BigDecimal playerAbackOdds) {
        this.playerAbackOdds = playerAbackOdds;
    }

    public BigDecimal getPlayerAlayOdds() {
        return playerAlayOdds;
    }

    public void setPlayerAlayOdds(BigDecimal playerAlayOdds) {
        this.playerAlayOdds = playerAlayOdds;
    }

    public BigDecimal getPlayerBbackOdds() {
        return playerBbackOdds;
    }

    public void setPlayerBbackOdds(BigDecimal playerBbackOdds) {
        this.playerBbackOdds = playerBbackOdds;
    }

    public BigDecimal getPlayerBlayOdds() {
        return playerBlayOdds;
    }

    public void setPlayerBlayOdds(BigDecimal playerBlayOdds) {
        this.playerBlayOdds = playerBlayOdds;
    }

    public java.lang.String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {  this.winner = winner;    }
}
