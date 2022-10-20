package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

public class SearchEventDeatilsTO {

    private Integer id;
    private String name;
    private String kickofftime;
    private Integer sportId;
    private String sportName;

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

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

    public String getKickofftime() {
        return kickofftime;
    }

    public void setKickofftime(String kickofftime) {
        this.kickofftime = kickofftime;
    }
}
