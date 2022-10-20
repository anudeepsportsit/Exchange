package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;


import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportErrorCode;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportStatus;

import java.util.Date;

public class CancelInstructionReport {
    private InstructionReportStatus status;
    private InstructionReportErrorCode errorCode;
    private CancelInstructionTO instruction;
    private double sizeCancelled;
    private Date cancelledDate;

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

    public CancelInstructionTO getInstruction() {
        return instruction;
    }

    public void setInstruction(CancelInstructionTO instruction) {
        this.instruction = instruction;
    }

    public double getSizeCancelled() {
        return sizeCancelled;
    }

    public void setSizeCancelled(double sizeCancelled) {
        this.sizeCancelled = sizeCancelled;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }
}
