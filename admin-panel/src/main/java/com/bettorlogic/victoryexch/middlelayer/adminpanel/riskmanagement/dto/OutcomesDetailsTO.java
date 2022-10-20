package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import java.util.List;

public class OutcomesDetailsTO {
    List<OddsDetailsTO> backOdds;
    List<OddsDetailsTO> layOdds;
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OddsDetailsTO> getBackOdds() {
        return backOdds;
    }

    public void setBackOdds(List<OddsDetailsTO> backOdds) {
        this.backOdds = backOdds;
    }

    public List<OddsDetailsTO> getLayOdds() {
        return layOdds;
    }

    public void setLayOdds(List<OddsDetailsTO> layOdds) {
        this.layOdds = layOdds;
    }
}
