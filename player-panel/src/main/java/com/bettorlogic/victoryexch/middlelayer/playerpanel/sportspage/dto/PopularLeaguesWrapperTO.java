package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto;

import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.LeagueDetailsTO;

import java.util.List;

public class PopularLeaguesWrapperTO {

    private List<LeagueDetailsTO> popularLeagues;

    public List<LeagueDetailsTO> getPopularLeagues() {
        return popularLeagues;
    }

    public void setPopularLeagues(List<LeagueDetailsTO> popularLeagues) {
        this.popularLeagues = popularLeagues;
    }
}
