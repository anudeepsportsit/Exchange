package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FooterControlsWrapperTO {

    @JsonProperty(SportsBookConstants.FOOTER_CONTROLS_COMMUNICATION_DETAILS)
    private List<FooterControlsTO> commDetailsList = new ArrayList<>();

    @JsonProperty(SportsBookConstants.FOOTER_CONTROLS_POINTS)
    private List<FooterControlsTO> pointsList = new ArrayList<>();

    @JsonProperty(SportsBookConstants.FOOTER_CONTROLS_LOGO_AND_LICENCING_INFORMATION)
    private List<FooterControlsTO> companyInfoList = new ArrayList<>();

    public List<FooterControlsTO> getCommDetailsList() {
        return commDetailsList;
    }

    public void setCommDetailsList(List<FooterControlsTO> commDetailsList) {
        this.commDetailsList = commDetailsList;
    }

    public List<FooterControlsTO> getPointsList() {
        return pointsList;
    }

    public void setPointsList(List<FooterControlsTO> pointsList) {
        this.pointsList = pointsList;
    }

    public List<FooterControlsTO> getCompanyInfoList() {
        return companyInfoList;
    }

    public void setCompanyInfoList(List<FooterControlsTO> companyInfoList) {
        this.companyInfoList = companyInfoList;
    }
}