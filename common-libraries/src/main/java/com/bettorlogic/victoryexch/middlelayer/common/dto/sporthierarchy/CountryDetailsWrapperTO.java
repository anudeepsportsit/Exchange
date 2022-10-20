package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CountryDetailsWrapperTO {

    @JsonProperty(SportsBookConstants.COUNTRIES)
    private List<CountryDetailsTO> countriesList = new ArrayList<>();

    public List<CountryDetailsTO> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(List<CountryDetailsTO> countriesList) {
        this.countriesList = countriesList;
    }
}