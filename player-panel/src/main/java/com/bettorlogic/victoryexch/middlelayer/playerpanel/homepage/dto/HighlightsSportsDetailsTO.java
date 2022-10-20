package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import java.io.Serializable;
import java.util.List;

public class HighlightsSportsDetailsTO implements Serializable {

    private List<SportDetailsTO> sportDetailsList;

    public List<SportDetailsTO> getSportDetailsList() {
        return sportDetailsList;
    }

    public void setSportDetailsList(List<SportDetailsTO> sportDetailsList) {
        this.sportDetailsList = sportDetailsList;
    }


}
