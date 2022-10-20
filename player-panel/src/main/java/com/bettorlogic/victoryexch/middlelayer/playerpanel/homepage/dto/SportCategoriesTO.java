package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class SportCategoriesTO implements Serializable {
    @JsonProperty("Sport")
    List<SportDetailsTO> sportsList;
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

    public List<SportDetailsTO> getSportsList() {
        return sportsList;
    }

    public void setSportsList(List<SportDetailsTO> sportsList) {
        this.sportsList = sportsList;
    }
}
