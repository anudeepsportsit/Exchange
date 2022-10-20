package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

public class EventTypeTO {
    private String id;
    private String name;

    public EventTypeTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "{" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
