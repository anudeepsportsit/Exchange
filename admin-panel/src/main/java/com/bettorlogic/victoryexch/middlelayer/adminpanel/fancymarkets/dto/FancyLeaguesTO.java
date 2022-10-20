package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

import java.util.List;

public class FancyLeaguesTO {
    List<FancyLeaguesDetailsTO> leagues;

    public List<FancyLeaguesDetailsTO> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<FancyLeaguesDetailsTO> leagues) {
        this.leagues = leagues;
    }
}
