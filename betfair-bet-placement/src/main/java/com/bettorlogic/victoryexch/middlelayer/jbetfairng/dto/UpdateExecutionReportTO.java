package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;


import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportErrorCode;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.InstructionReportStatus;

import java.util.List;

public class UpdateExecutionReportTO {
    private String customerRef;
    private InstructionReportStatus status;
    private InstructionReportErrorCode errorCode;
    private List<UpdateInstructionReportTO> instructionReports;

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
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

    public List<UpdateInstructionReportTO> getInstructionReports() {
        return instructionReports;
    }

    public void setInstructionReports(List<UpdateInstructionReportTO> instructionReports) {
        this.instructionReports = instructionReports;
    }
}
