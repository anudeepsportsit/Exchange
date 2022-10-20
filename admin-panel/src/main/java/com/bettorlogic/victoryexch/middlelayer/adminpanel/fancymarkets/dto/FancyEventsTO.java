package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

import java.util.List;

public class FancyEventsTO {
    List<FancyEventsDetailsTO> events;

    public List<FancyEventsDetailsTO> getEvents() {
        return events;
    }

    public void setEvents(List<FancyEventsDetailsTO> events) {
        this.events = events;
    }
}
