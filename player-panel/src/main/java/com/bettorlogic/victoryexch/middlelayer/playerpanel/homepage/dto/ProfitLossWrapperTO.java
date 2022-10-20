package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import java.util.List;

public class ProfitLossWrapperTO {

    List<SelectionDetailsTO> selectionList;

    public List<SelectionDetailsTO> getSelectionList() {
        return selectionList;
    }

    public void setSelectionList(List<SelectionDetailsTO> selectionList) {
        this.selectionList = selectionList;
    }
}
