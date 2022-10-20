package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

public class UserBetHistoryDetailsTO {
    private Long betSlipId;
    private Integer profileId;
    private Double betSlipTotalStake;
    private String betPlaced;
    private String betSlipStatus;
    private Double betSlipTotalReturns;
    private Double betStake;
    private Double betOdds;
    private String betOddType;
    private String betType;
    private String bMarket;
    private String bSelection;
    private Double totalMatched;
    private String providerMarketGroup;
    private String profitLoss;

    public Double getBetSlipTotalStake() {
        return betSlipTotalStake;
    }

    public void setBetSlipTotalStake(Double betSlipTotalStake) {
        this.betSlipTotalStake = betSlipTotalStake;
    }

    public Double getBetSlipTotalReturns() {
        return betSlipTotalReturns;
    }

    public void setBetSlipTotalReturns(Double betSlipTotalReturns) {
        this.betSlipTotalReturns = betSlipTotalReturns;
    }

    public Double getBetStake() {
        return betStake;
    }

    public void setBetStake(Double betStake) {
        this.betStake = betStake;
    }

    public Double getBetOdds() {
        return betOdds;
    }

    public void setBetOdds(Double betOdds) {
        this.betOdds = betOdds;
    }

    public Long getBetSlipId() {
        return betSlipId;
    }

    public void setBetSlipId(Long betSlipId) {
        this.betSlipId = betSlipId;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getBetPlaced() {
        return betPlaced;
    }

    public void setBetPlaced(String betPlaced) {
        this.betPlaced = betPlaced;
    }

    public String getBetSlipStatus() {
        return betSlipStatus;
    }

    public void setBetSlipStatus(String betSlipStatus) {
        this.betSlipStatus = betSlipStatus;
    }

    public String getBetOddType() {
        return betOddType;
    }

    public void setBetOddType(String betOddType) {
        this.betOddType = betOddType;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public String getbMarket() {
        return bMarket;
    }

    public void setbMarket(String bMarket) {
        this.bMarket = bMarket;
    }

    public String getbSelection() {
        return bSelection;
    }

    public void setbSelection(String bSelection) {
        this.bSelection = bSelection;
    }

    public double getTotalMatched() {
        return totalMatched;
    }

    public void setTotalMatched(Double totalMatched) {
        this.totalMatched = totalMatched;
    }

    public void setTotalMatched(double totalMatched) {
        this.totalMatched = totalMatched;
    }

    public String getProviderMarketGroup() {
        return providerMarketGroup;
    }

    public void setProviderMarketGroup(String providerMarketGroup) {
        this.providerMarketGroup = providerMarketGroup;
    }

    public String getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(String profitLoss) {
        this.profitLoss = profitLoss;
    }


}
