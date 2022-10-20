package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportErrorCode;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportStatus;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.OrderStatus;

import java.util.Date;

public class PlaceInstructionReport {
    private InstructionReportStatus status;
    private InstructionReportErrorCode errorCode;
    private PlaceInstructionTO instruction;
    private String betId;
    private Date placedDate;
    private double averagePriceMatched;
    private double sizeMatched;
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public InstructionReportStatus getStatus() {
        return status;
    }

    public void setStatus(InstructionReportStatus status) {
        this.status = status;
    }

    public InstructionReportErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(InstructionReportErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public PlaceInstructionTO getInstruction() {
        return instruction;
    }

    public void setInstruction(PlaceInstructionTO instruction) {
        this.instruction = instruction;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public Date getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Date placedDate) {
        this.placedDate = placedDate;
    }

    public double getAveragePriceMatched() {
        return averagePriceMatched;
    }

    public void setAveragePriceMatched(double averagePriceMatched) {
        this.averagePriceMatched = averagePriceMatched;
    }

    public double getSizeMatched() {
        return sizeMatched;
    }

    public void setSizeMatched(double sizeMatched) {
        this.sizeMatched = sizeMatched;
    }

}
