package com.bettorlogic.victoryexch.middlelayer.adminpanel.downlinelist.dto.betlistlive;

import java.util.List;

public class BetListLiveWrapperTO {

    private List<BetListStatusTO> unMatched;


    private List<BetListStatusTO> matched;

    public List<BetListStatusTO> getUnMatched() {
        return unMatched;
    }

    public void setUnMatched(List<BetListStatusTO> unMatched) {
        this.unMatched = unMatched;
    }

    public List<BetListStatusTO> getMatched() {
        return matched;
    }

    public void setMatched(List<BetListStatusTO> matched) {
        this.matched = matched;
    }
}
