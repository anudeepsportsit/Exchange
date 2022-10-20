package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;


import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportErrorCode;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportStatus;

public class UpdateInstructionReportTO {
    private InstructionReportStatus status;
    private InstructionReportErrorCode errorCode;
    private UpdateInstructionTO instruction;

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

    public UpdateInstructionTO getInstruction() {
        return instruction;
    }

    public void setInstruction(UpdateInstructionTO instruction) {
        this.instruction = instruction;
    }
}
