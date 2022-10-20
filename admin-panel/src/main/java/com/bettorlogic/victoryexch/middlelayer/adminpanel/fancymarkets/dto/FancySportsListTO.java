package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

import java.util.List;

public class FancySportsListTO {
    List<FancySportsDetailsTO> sports;

    public List<FancySportsDetailsTO> getSports() {
        return sports;
    }

    public void setSports(List<FancySportsDetailsTO> sports) {
        this.sports = sports;
    }
}
